package dev.remine.guilder.api.loadbalancer.repository;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.MongoException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Projections;
import com.mongodb.client.result.InsertOneResult;
import dev.remine.guilder.api.loadbalancer.repository.exceptions.services.ServiceNotAvailableException;
import dev.remine.guilder.api.loadbalancer.repository.exceptions.services.ServiceNotFoundException;
import dev.remine.guilder.api.loadbalancer.repository.exceptions.services.UnableToUpdateServiceException;
import dev.remine.guilder.commons.api.models.Service;
import dev.remine.guilder.commons.api.database.DatabaseService;
import dev.remine.guilder.rpc.types.Services;
import es.zaroz.AsyncTask.ValueTask;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Sorts.descending;

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
                    for (Service service : getCachedServices())
                    {
                        if (service.getId().equalsIgnoreCase(Id))
                            return service;
                    }
                    Service service = getCollection().find(eq("_id", Id)).first();
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
                for (Service service : getCachedServices())
                {
                    if (service.getServiceType() == serviceType)
                        result.add(service);
                }
                if (!result.isEmpty())
                    return result;
                for (Service service : getCollection().find(eq("serviceType", serviceType))) result.add(service);
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
                for (Service service : getCachedServices())
                {
                    if (service.getServiceType() == serviceType && service.getServiceState() == Services.ServiceState.RUNNING)
                        result.add(service);
                }
                if (!result.isEmpty()) {
                    result.sort(Service.clientsNumberComparator);
                    return result.get(0);
                }
                return getCollection().find(eq("serviceType", serviceType))
                        .projection(Projections.include("serviceState", Services.ServiceState.RUNNING.toString()))
                        .sort(descending("clients"))
                        .first();
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
            getCollection().findOneAndReplace(eq("Id", service.getId()), service);
            servicesCache.removeIf(cached -> cached.getId().equalsIgnoreCase(service.getId()));
            servicesCache.add(service);
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
            getCollection().deleteOne(eq("Id", Id));
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