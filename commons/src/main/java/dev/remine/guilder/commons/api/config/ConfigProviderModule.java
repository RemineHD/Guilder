package dev.remine.guilder.commons.api.config;

import com.google.inject.AbstractModule;

public class ConfigProviderModule extends AbstractModule {

    @Override
    public void configure()
    {
        bind(DbConfigProvider.class).to(DbConfigProviderImpl.class);
        bind(DockerConfigProvider.class).to(DockerConfigProviderImpl.class);
        bind(gRPCConfigProvider.class).to(gRPCConfigProviderImpl.class);
        bind(ServiceConfigProvider.class).to(ServiceConfigProviderImpl.class);
    }

}
