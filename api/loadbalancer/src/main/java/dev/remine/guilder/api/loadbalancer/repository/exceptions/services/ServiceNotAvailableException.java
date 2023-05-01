package dev.remine.guilder.api.loadbalancer.repository.exceptions.services;

import dev.remine.guilder.api.loadbalancer.repository.exceptions.RepositoryException;
import dev.remine.guilder.rpc.types.Services;

public class ServiceNotAvailableException extends RepositoryException {

    public ServiceNotAvailableException(Services.ServiceType serviceType, Exception exception)
    {
        super("Unable to find any available service for type: " + serviceType, exception);
    }

}
