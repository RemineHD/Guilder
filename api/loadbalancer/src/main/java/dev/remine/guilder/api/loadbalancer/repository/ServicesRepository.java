package dev.remine.guilder.api.loadbalancer.repository;

import dev.remine.guilder.api.loadbalancer.repository.exceptions.services.ServiceNotAvailableException;
import dev.remine.guilder.api.loadbalancer.repository.exceptions.services.ServiceNotFoundException;
import dev.remine.guilder.api.loadbalancer.repository.exceptions.services.UnableToDeleteServiceException;
import dev.remine.guilder.api.loadbalancer.repository.exceptions.services.UnableToUpdateServiceException;
import dev.remine.guilder.commons.api.models.Service;
import dev.remine.guilder.rpc.types.Services;
import es.zaroz.AsyncTask.ValueTask;

import java.util.List;

public interface ServicesRepository {

    /**
     * Used by the ServiceFactory to register the service in
     * cache and in the database.
     * @param service The data of the registered service.
     */

    void registerService(Service service);

    /**
     * It will return a new ArrayList to make it thread safe.
     * @return all the current cached services.
     */
    List<Service> getCachedServices();

    /**
     *
     * @param Id required service Id.
     * @return the required service either from cache or database. It will
     * @throws ServiceNotFoundException if the service can't be founded.
     */
    ValueTask<Service> getServiceById(String Id) throws ServiceNotFoundException;

    /**
     * It will return the first service EVEN if this service is not RUNNING!
     * @param serviceType the service type you want to get.
     * @return the first service of that type founded either in the cache or database.
     * @throws ServiceNotAvailableException in case there is not available services of that type.
     */
    ValueTask<List<Service>> getServicesByType(Services.ServiceType serviceType) throws ServiceNotAvailableException;

    /**
     * Will return the recommended service by type.
     * Recommended service in this case will be the RUNNING service with the least amount of instances.
     * @param serviceType the service type you want to get.
     * @return the recommended service based on the state and the instances connected to it.
     * @throws ServiceNotAvailableException in case there is not available services of that type.
     */
    ValueTask<Service> getRecommendedServiceByType(Services.ServiceType serviceType) throws ServiceNotAvailableException;

    /**
     * Updates the data of the service both into cache and database.
     * @param service the service you want to update with the modified data.
     *                Keep in mind that the Id should be always the same.
     */
    void updateService(Service service) throws UnableToUpdateServiceException;

    /**
     * Deletes the service by Id (Both Cache and Database)
     * @param Id the Id of the service you want to delete.
     */
    void deleteServiceById(String Id) throws UnableToDeleteServiceException;

    /**
     * Deletes the service by Type (Both Cache and Database)
     * @param serviceType the Type of the service you want to delete.
     */
    void deleteServicesByType(Services.ServiceType serviceType) throws UnableToDeleteServiceException;


}
