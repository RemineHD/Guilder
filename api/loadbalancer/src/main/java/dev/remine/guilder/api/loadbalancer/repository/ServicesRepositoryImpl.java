package dev.remine.guilder.api.loadbalancer.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import dev.remine.guilder.api.loadbalancer.repository.exceptions.services.ServiceNotAvailableException;
import dev.remine.guilder.api.loadbalancer.repository.exceptions.services.ServiceNotFoundException;
import dev.remine.guilder.api.loadbalancer.repository.exceptions.services.UnableToUpdateServiceException;
import dev.remine.guilder.commons.api.models.Service;
import dev.remine.guilder.commons.api.database.DatabaseService;
import dev.remine.guilder.rpc.types.Services;
import dev.remine.guilder.rpc.types.State;
import es.zaroz.AsyncTask.ValueTask;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;

@Singleton
public class ServicesRepositoryImpl implements ServicesRepository {

    private final List<Service> servicesCache;

    private final DatabaseService databaseService;
    private final Logger logger;

    @Inject
    public ServicesRepositoryImpl(DatabaseService databaseService, Logger logger)
    {
        this.databaseService = databaseService;
        this.logger = logger;
        this.servicesCache = new ArrayList<>();
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {

                logger.info("[Services Repository] clearing cache");
                servicesCache.clear();
            }
        }, 0, 300000L);

    }

    private MongoCollection<Service> getCollection()
    {
        return databaseService.getDatabase().getCollection("services", Service.class);
    }

    @Override
    public void registerService(Service service) {
        try {
            InsertOneResult insertResult = getCollection().insertOne(service);
            servicesCache.add(service);
            logger.info("[Services Repository] new MicroService registered with Insert Id: " + insertResult.getInsertedId() + " and Service Id: " + service.getId());
        } catch (MongoException mongoException)
        {
            logger.info("[Services Repository] error registering new MicroService with Id: " + service.getId());
            mongoException.printStackTrace();
        }
    }

    @Override
    public List<Service> getCachedServices() {
        return new ArrayList<>(servicesCache);
    }

    @Override
    public ValueTask<Service> getServiceById(@NonNull String Id) {
            return ValueTask.run(() -> {
                try {
                    /*
                     * First, we check if the service is stored in cache.
                     */
                    for (Service service : getCachedServices())
                    {
                        if (service.getId().equalsIgnoreCase(Id))
                            return service;
                    }
                    //If it is not, we ask mongo to see if it's in there
                    Service service = getCollection().find(eq("_id", Id)).first();

                    //We double-check the service is not null, and we add it to cache for future operations.
                    if (service != null)
                        servicesCache.add(service);
                    return service;
                } catch (Exception exception)
                {
                  exception.printStackTrace();
                  throw new ServiceNotFoundException(Id);
                }
            });
    }

    @Override
    public ValueTask<List<Service>> getServicesByType(@NonNull Services.ServiceType serviceType) {
        return ValueTask.run(() -> {
            try {
                List<Service> result = new ArrayList<>();
                /*
                First we check if there's any data in cache.
                You may ask, well maybe there is data in cache, and we skip newer data
                from Mongo.
                Well this is true, but, because the services collection is not expected to
                have millions nor even thousands of documents we will clear and fetch all the data from
                cache each X minutes, so it can be as updated as we can.
                 */
                for (Service service : getCachedServices())
                {
                    if (service.getServiceType() == serviceType)
                        result.add(service);
                }
                if (!result.isEmpty())
                    return result;
                /*
                If it is not we ask Mongo if there is still no data null will be returned.
                 */
                for (Service service : getCollection().find(eq("serviceType", serviceType)))
                {
                    servicesCache.add(service);
                    result.add(service);
                }
                return result;
            } catch (Exception exception)
            {
                logger.warning("[Services Repository] error finding services by type: " + serviceType);
                throw new ServiceNotAvailableException(serviceType, exception);
            }
        });
    }

    @Override
    public ValueTask<Service> getRecommendedServiceByType(@NonNull Services.ServiceType serviceType) {
        return ValueTask.run(() -> {
            try {
                List<Service> result = new ArrayList<>();
                for (Service service : getServicesByType(serviceType).getResult()) //We use the fetching logic we already have to avoid repeated code.
                {
                    if (service.getServiceState() == State.RUNNING)
                        result.add(service);
                }
                result.sort(Service.instanceNumberComparator);
                return result.get(0);
            } catch (Exception exception)
            {
                logger.warning("[Services Repository] error getting recommended service by type: " + serviceType);
                throw new ServiceNotAvailableException(serviceType, exception);
            }
        });
    }

    @Override
    public void updateService(@NonNull Service service) throws UnableToUpdateServiceException {
        try {
            getCollection().findOneAndReplace(eq("_id", service.getId()), service); //We search for a document matching the Id and then we replace it by the updated one.
            servicesCache.removeIf(cached -> cached.getId().equalsIgnoreCase(service.getId())); //If we have a document with the same Id in cache we will remove it
            servicesCache.add(service); //And add it again later with the updated data.
        } catch (Exception exception)
        {
            logger.warning("[Services Repository] error updating a MicroService with Id: " + service.getId());
            throw new UnableToUpdateServiceException(service, exception);
        }
        logger.info("[Services Repository] updated MicroService with Id: " + service.getId());
    }

    @Override
    public void deleteServiceById(@NonNull String Id) {
        try {
            //https://www.mongodb.com/docs/drivers/java/sync/current/usage-examples/deleteOne/
            getCollection().deleteOne(eq("_id", Id));
            servicesCache.removeIf(service -> service.getId().equalsIgnoreCase(Id));
        } catch (Exception exception)
        {
            logger.warning("[Services Repository] error deleting a MicroService with Id: " + Id);
            exception.printStackTrace();
        }
        logger.info("[Services Repository] deleted a MicroService with Id: " + Id);
    }

    @Override
    public void deleteServicesByType(@NonNull Services.ServiceType serviceType) {
        try {
            //https://www.mongodb.com/docs/drivers/java/sync/current/usage-examples/deleteMany/
            getCollection().deleteMany(eq("serviceType", serviceType));
            servicesCache.removeIf(cached -> cached.getServiceType() == serviceType);
        } catch (Exception exception)
        {
            logger.warning("[Services Repository] error deleting services of type: " + serviceType);
            exception.printStackTrace();
        }
        logger.info("[Services Repository] deleted all services of type: " + serviceType);
    }

}
