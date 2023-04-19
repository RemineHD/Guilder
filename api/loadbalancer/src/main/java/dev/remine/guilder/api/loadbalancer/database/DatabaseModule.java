package dev.remine.guilder.api.loadbalancer.database;

import com.google.inject.AbstractModule;

public class DatabaseModule extends AbstractModule {

    @Override
    public void configure()
    {
        bind(DatabaseService.class).to(MongoDatabaseImpl.class);
    }

}
