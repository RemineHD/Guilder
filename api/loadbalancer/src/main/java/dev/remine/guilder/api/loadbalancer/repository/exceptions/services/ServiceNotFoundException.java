package dev.remine.guilder.api.loadbalancer.repository.exceptions.services;

import dev.remine.guilder.api.loadbalancer.repository.exceptions.RepositoryException;

public class ServiceNotFoundException extends RepositoryException {

    public ServiceNotFoundException(String Id)
    {
        super("Unable to find the Service: " + Id);
    }

}
