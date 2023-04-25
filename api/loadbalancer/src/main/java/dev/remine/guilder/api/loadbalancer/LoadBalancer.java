package dev.remine.guilder.api.loadbalancer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.remine.guilder.api.loadbalancer.database.DatabaseModule;
import dev.remine.guilder.api.loadbalancer.imprv.manager.client.ClientModule;
import dev.remine.guilder.api.loadbalancer.imprv.manager.service.ServiceModule;
import dev.remine.guilder.api.loadbalancer.server.ServerModule;
import dev.remine.guilder.api.loadbalancer.server.ServerService;
import lombok.Getter;

import java.io.IOException;
import java.util.logging.Logger;

public class LoadBalancer {

    private static final Logger logger = Logger.getLogger(LoadBalancer.class.getName());

    @Getter
    private int grpcPort = 50051;

    @Getter
    private Injector injector;

    /**
     * Starts the gRPC Server which will keep the program running.
     * @param injector Google Guice Injector.
     * @throws IOException In case the server is unable to start correctly.
     */
    public void start(Injector injector) throws IOException {
        this.injector = injector;
        getInjector().getInstance(ServerService.class).start(getGrpcPort());
    }

    public static void main(String[] args) {

        logger.info("[LoadBalancer] *** Starting modules ***");

        Injector injector = Guice.createInjector(
                new ServerModule(),
                new ClientModule(),
                new DatabaseModule(),
                new ServiceModule()
        );

        try {
            injector.getInstance(LoadBalancer.class).start(injector);
        } catch (IOException ioException)
        {
            ioException.printStackTrace(System.err);
        }

    }

}