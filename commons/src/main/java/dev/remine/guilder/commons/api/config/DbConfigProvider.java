package dev.remine.guilder.commons.api.config;

import javax.annotation.Nonnull;

public interface DbConfigProvider {

    /**
     * @return the IP Address of the Mongo Server, in case is not provided a default one will be
     */
    @Nonnull
    String getMongoAddress();

    /**
     * @return The Port of the Mongo Server
     */
    Integer getMongoPort();

    /**
     * @return the User for the Mongo Server
     * if the user is not specified a default one will be provided
     */
    @Nonnull
    String getMongoUser();

    /**
     * @return the Password for the Mongo Server
     * if the password is not specified a default one will be provided
     */
    @Nonnull
    String getMongoPassword();

    /**
     * @return the Database where the service will be operating.
     * because the database may variate depending on the service is
     * using it, in case the database is not provided this will
     * return Null
     */
    String getMongoDB();

}
