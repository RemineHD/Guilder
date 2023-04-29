package dev.remine.guilder.api.loadbalancer;

import com.google.inject.Guice;
import com.google.inject.Injector;
import dev.remine.guilder.commons.api.config.ConfigProviderModule;
import dev.remine.guilder.commons.api.database.DatabaseModule;
import dev.remine.guilder.api.loadbalancer.server.ServerModule;
import dev.remine.guilder.api.loadbalancer.server.ServerService;
import dev.remine.guilder.commons.api.database.DatabaseService;
import dev.remine.guilder.commons.api.docker.DockerModule;
import dev.remine.guilder.commons.api.docker.DockerService;
import lombok.Getter;

import java.io.IOException;
import java.util.logging.Logger;

public class LoadBalancer {

    private static final Logger logger = Logger.getLogger(LoadBalancer.class.getName());

    @Getter
    private Injector injector;

    /**
     * Starts the gRPC Server which will keep the program running.
     * @param injector Google Guice Injector.
     * @throws IOException In case the server is unable to start correctly.
     */
    public void start(Injector injector) throws IOException, InterruptedException {
        this.injector = injector;
        getInjector().getInstance(DatabaseService.class).startDatabase();
        getInjector().getInstance(DockerService.class).startDocker();
        getInjector().getInstance(ServerService.class).start();
    }

    public static void main(String[] args) {

        logger.info("[LoadBalancer] *** Starting modules ***");

        Injector injector = Guice.createInjector(
                new ConfigProviderModule(),
                new DockerModule(),
                new ServerModule(),
                new DatabaseModule()
        );

        try {
            injector.getInstance(LoadBalancer.class).start(injector);
        } catch (IOException | InterruptedException exception)
        {
            exception.printStackTrace(System.err);
        }

    }

}