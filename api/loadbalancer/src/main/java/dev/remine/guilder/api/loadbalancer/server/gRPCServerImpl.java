package dev.remine.guilder.api.loadbalancer.server;

import dev.remine.guilder.api.loadbalancer.auth.AuthServerInterceptor;
import dev.remine.guilder.api.loadbalancer.handshake.HandshakeImpl;
import io.grpc.Grpc;
import io.grpc.InsecureServerCredentials;
import io.grpc.Server;
import lombok.Getter;

import java.io.IOException;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

public class gRPCServerImpl implements ServerService {

    static Logger logger = Logger.getLogger(ServerModule.class.getName());

    int port;

    @Getter
    Server server;

    @Override
    public void start(int port) throws IOException {

        this.port = port;

        /**
         * Here we start the gRPC server. All the Guilder MicroServices will use the exact same configuration.
         */
        server = Grpc.newServerBuilderForPort(port, InsecureServerCredentials.create())
                .addService(new HandshakeImpl())
                .intercept(new AuthServerInterceptor())
                .build()
                .start();
        logger.info("[gRPC] Server started, listening on: " + port);

        /**
         * This Runtime Hook will be called on shutdown, so the gRPC Server can shut down correctly
         * we don't want "leaks" in our program.
         */
        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            logger.warning("[gRPC] *** shutting down gRPC server since JVM is shutting down ***");
            try {
                this.stop();
                logger.info("[gRPC] *** server shut down correctly ***");
            } catch (InterruptedException interruptedException)
            {
                interruptedException.printStackTrace(System.err);
            }
        }));

        try {
            blockUntilShutdown();
        } catch (InterruptedException interruptedException)
        {
            interruptedException.printStackTrace(System.err);
        }

    }

    private void blockUntilShutdown() throws InterruptedException
    {
        if (server != null)
            server.awaitTermination();
    }

    @Override
    public void stop() throws InterruptedException {
        if (server != null)
            server.shutdown().awaitTermination(30, TimeUnit.SECONDS);
    }
}
