package dev.remine.guilder.api.loadbalancer.repository.exceptions;

public class RepositoryException extends Exception {

    public RepositoryException(String message) { super(message); }
    public RepositoryException(String message, Exception exception) { super(message, exception); }

}
