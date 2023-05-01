package dev.remine.guilder.commons.api.database;

import com.google.inject.Binder;
import com.google.inject.Module;
import com.google.inject.Scopes;

public class DatabaseModule implements Module {

    @Override
    public void configure(Binder binder)
    {
        binder.bind(DatabaseService.class).to(MongoDatabaseImpl.class).in(Scopes.SINGLETON);
    }

}
