package dev.remine.guilder.api.loadbalancer.server;

import io.grpc.Server;

import java.io.IOException;

public interface ServerService {

    /**
     * Attempts to start a gRPC server in order to allow clients and services to
     * communicate with the MicroService
     * @throws IOException in case the server didn't start correctly
     */
    void start(int port) throws IOException;


    /**
     * Attempts to shut down the gRPC server.
     * @throws InterruptedException in case the server didn't shut down correctly
     */
    void stop() throws InterruptedException;

    Server getServer();

}
