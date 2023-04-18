package dev.remine.guilder.api.loadbalancer.manager;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.remine.guilder.api.loadbalancer.manager.client.ClientModule;
import dev.remine.guilder.api.loadbalancer.manager.server.ServerModule;
import lombok.Getter;

public class ManagerModule extends AbstractModule {

    @Getter
    Injector injector;

    @Override
    public void configure()
    {

        injector = Guice.createInjector(
                new ServerModule(),
                new ClientModule()
        );
    }

}
