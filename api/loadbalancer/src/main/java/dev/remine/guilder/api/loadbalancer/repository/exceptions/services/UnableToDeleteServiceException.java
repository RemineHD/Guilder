package dev.remine.guilder.api.loadbalancer.repository.exceptions.services;

import dev.remine.guilder.api.loadbalancer.repository.exceptions.RepositoryException;
import dev.remine.guilder.rpc.types.Services;

public class UnableToDeleteServiceException extends RepositoryException {

    public UnableToDeleteServiceException(String Id, Exception exception)
    {
        super("Unable to delete the service with Id: " + Id, exception);
    }

    public UnableToDeleteServiceException(Services.ServiceType serviceType, Exception exception)
    {
        super("Unable to delete services of Type: " + serviceType, exception);
    }



}
