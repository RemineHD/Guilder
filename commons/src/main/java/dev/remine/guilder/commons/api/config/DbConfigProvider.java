package dev.remine.guilder.commons.api.config;

import javax.annotation.Nonnull;

public interface DbConfigProvider {

    /**
     * @return the IP Address of the Mongo Server, in case is not provided a default one will be
     */
    @Nonnull
    String getMongoURI();

    /**
     * @return the Database where the service will be operating.
     * because the database may variate depending on the service is
     * using it, in case the database is not provided this will
     * return Null
     */
    String getMongoDatabase();

}
