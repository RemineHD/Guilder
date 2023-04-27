package dev.remine.guilder.commons.api.config;

import com.google.inject.AbstractModule;

public class ConfigProviderModule extends AbstractModule {

    /**
     * Config Providers are simple services
     * that provides all the information you may need to run
     * a microservice. This way if we have to change the
     * way we get the information/credentials we don't
     * have to change half of the project.
     */
    @Override
    public void configure()
    {
        bind(DbConfigProvider.class).to(DbConfigProviderImpl.class);
        bind(DockerConfigProvider.class).to(DockerConfigProviderImpl.class);
        bind(gRPCConfigProvider.class).to(gRPCConfigProviderImpl.class);
        bind(ServiceConfigProvider.class).to(ServiceConfigProviderImpl.class);
    }

}
