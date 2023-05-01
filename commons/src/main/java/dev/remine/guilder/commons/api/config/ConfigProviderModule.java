package dev.remine.guilder.commons.api.config;

import com.google.inject.AbstractModule;
import com.google.inject.Binder;
import com.google.inject.Module;

public class ConfigProviderModule implements Module {

    /**
     * Config Providers are simple services
     * that provides all the information you may need to run
     * a microservice. This way if we have to change the
     * way we get the information/credentials we don't
     * have to change half of the project.
     */
    @Override
    public void configure(Binder binder)
    {
        binder.bind(DbConfigProvider.class).to(DbConfigProviderImpl.class).asEagerSingleton();
        binder.bind(DockerConfigProvider.class).to(DockerConfigProviderImpl.class).asEagerSingleton();
        binder.bind(gRPCConfigProvider.class).to(gRPCConfigProviderImpl.class).asEagerSingleton();
        binder.bind(ServiceConfigProvider.class).to(ServiceConfigProviderImpl.class).asEagerSingleton();
    }

}
