package dev.remine.guilder.api.loadbalancer;


import com.google.inject.Guice;
import dev.remine.guilder.api.loadbalancer.controllers.c2s.HandshakeC2S;
import dev.remine.guilder.api.loadbalancer.repository.RepositoryModule;
import dev.remine.guilder.commons.api.config.ConfigProviderModule;
import dev.remine.guilder.commons.api.database.DatabaseModule;
import dev.remine.guilder.commons.api.database.DatabaseService;
import dev.remine.guilder.commons.api.docker.DockerModule;
import dev.remine.guilder.commons.api.docker.DockerService;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import io.grpc.ServerInterceptors;
import pl.morgwai.base.grpc.scopes.GrpcModule;

import java.io.IOException;
import java.util.logging.Logger;

public class LoadBalancer {

    final Server loadBalancerServer;

    LoadBalancer() throws IOException {
        final var grpcModule = new GrpcModule();

        final var injector = Guice.createInjector(grpcModule,
                new ConfigProviderModule(),
                new DockerModule(),
                new DatabaseModule(),
                new RepositoryModule());

        injector.getInstance(DockerService.class).startDocker();
        injector.getInstance(DatabaseService.class).startDatabase();

        final var gRPCConfigProvider = injector.getInstance(dev.remine.guilder.commons.api.config.gRPCConfigProvider.class);
        final var service = injector.getInstance(HandshakeC2S.class);
        loadBalancerServer = Grpc.newServerBuilderForPort(
                gRPCConfigProvider.getPort(), InsecureServerCredentials.create())
                .addService(ServerInterceptors.intercept(service, grpcModule.serverInterceptor))
                .build();
        loadBalancerServer.start();

        log.info("[gRPC] Server started, listening on: " + gRPCConfigProvider.getPort());

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            log.info("[gRPC] *** shutting down gRPC server since JVM is shutting down ***");
            loadBalancerServer.shutdown();
            log.info("[gRPC] *** server shut down correctly ***");
        }));


    }

    public static void main(String[] args) throws IOException, InterruptedException {
        new LoadBalancer()
                .loadBalancerServer.awaitTermination();
    }

    static final Logger log = Logger.getLogger(LoadBalancer.class.getName());

}