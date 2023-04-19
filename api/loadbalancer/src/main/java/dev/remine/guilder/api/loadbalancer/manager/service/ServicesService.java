package dev.remine.guilder.api.loadbalancer.manager.service;

import dev.remine.guilder.rpc.types.Services;

import java.util.List;

public interface ServicesService {

    /**
     *
     * @param serviceType The Type od service you want to retrieve (Ex: PLAYERS)
     * @return The service data once its starting. It will be useful in case you need to use that data.
     * It will return null in case there is an error in the process TODO: throw a custom exception
     */
    Services.Service startServiceByType(Services.ServiceType serviceType);

    /**
     * The recommended services are the services with a "normal" amount of clients. This amount can be modified
     * in the Administration panel, so if the infrastructure suffers with certain amount it can be easily modified.
     * TODO: TOKEN + PARAM DESCRIPTION
     */
    List<Services.Service> getRecommendedServicesByToken();

    /**
     * @return A list of all the currently running services.
     * It will return an empty list if there is no running services. (Unlikely anyways, I hope)
     */
    List<Services.Service> getServices();

    /**
     * @return A list of services based on the level of access of the token.
     * It will return an empty list if the token is invalid or doesn't have internal access.
     * TODO: throw a custom exception
     */
    List<Services.Service> getServicesByToken(); //TODO: ADD TOKEN

    /**
     *
     * @param serviceType The Type od service you want to retrieve (Ex: PLAYERS)
     * @return The first service from the Repository.
     * It will return null in case there is no service available.
     * TODO: throw a custom exception
     */
    Services.Service getServiceByType(Services.ServiceType serviceType);

    /**
     *
     * @param serviceType The Type od service you want to retrieve (Ex: PLAYERS)
     * @return A list of services of the type selected.
     * It will return an empty list in case there is no available services.
     * TODO: throw a custom exception
     */
    List<Services.Service> getServicesByType(Services.ServiceType serviceType);

    /**
     * It attempts to call docker in order to shut down the desired service.
     * If there is any client connected the connection will drop.
     * @param id Id of the Service.
     */
    void stopServiceById(String id);

    /**
     * It attempts to call docker in order to shut down the desired service,
     * but in case there is clients connected it will search for other services of the same time
     * in order to move those clients, so they can keep working.
     * if there is no other services of the same time available it won't close the service.
     * @param id The uniqueId of the desired service
     * @return true when the service closes correctly, false when it doesn't
     */
    boolean safeStopServiceById(String id);

    /**
     * It attempts to stop all the services by that type.
     * If there is any client connected the connection will drop.
     * @param serviceType The Type od service you want to retrieve (Ex: PLAYERS)
     */
    void stopServicesByType(Services.ServiceType serviceType);


}
