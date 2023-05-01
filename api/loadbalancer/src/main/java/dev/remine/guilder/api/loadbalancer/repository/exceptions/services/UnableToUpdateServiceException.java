package dev.remine.guilder.api.loadbalancer.repository.exceptions.services;

import dev.remine.guilder.api.loadbalancer.repository.exceptions.RepositoryException;
import dev.remine.guilder.commons.api.models.Service;

public class UnableToUpdateServiceException extends RepositoryException {

    public UnableToUpdateServiceException(Service service, Exception exception)
    {
        super("Unable to update the service with Id: " + service.getId(), exception);
    }

}
