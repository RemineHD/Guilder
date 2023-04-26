package dev.remine.guilder.commons.api.database;

import com.google.inject.AbstractModule;

public class DatabaseModule extends AbstractModule {

    @Override
    public void configure()
    {
        bind(DatabaseService.class).to(MongoDatabaseImpl.class);
    }

}
