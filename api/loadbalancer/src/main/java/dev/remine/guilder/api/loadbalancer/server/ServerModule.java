package dev.remine.guilder.api.loadbalancer.server;

import com.google.inject.AbstractModule;

public class ServerModule extends AbstractModule {

    @Override
    public void configure() {
        bind(ServerService.class).to(gRPCServerImpl.class);
    }
}
