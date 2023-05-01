package dev.remine.guilder.commons.api.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.core.DefaultDockerClientConfig;
import com.github.dockerjava.core.DockerClientConfig;
import com.github.dockerjava.httpclient5.ApacheDockerHttpClient;
import com.github.dockerjava.transport.DockerHttpClient;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import dev.remine.guilder.commons.api.config.DockerConfigProvider;
import lombok.Getter;

import java.io.IOException;
import java.util.logging.Logger;

@Singleton
public class DockerClientImpl implements DockerService {

    private final DockerConfigProvider dockerConfigProvider;
    private final Logger logger;

    @Getter
    private DockerHttpClient dockerHttpClient;

    @Getter
    private DockerClient dockerClient;

    @Inject
    public DockerClientImpl(DockerConfigProvider dockerConfigProvider, Logger logger)
    {
        this.dockerConfigProvider = dockerConfigProvider;
        this.logger = logger;
    }

    @Override
    public void startDocker() {

        logger.info("[Docker] *** Starting Docker Engine Client ***");

        DockerClientConfig dockerClientConfig = DefaultDockerClientConfig.createDefaultConfigBuilder()
                .withDockerHost(dockerConfigProvider.getDockerHost())
                .withDockerTlsVerify(dockerConfigProvider.verifyDockerTls())
                .withDockerCertPath(dockerConfigProvider.getDockerCertPath())
                .build();
        try {
            dockerHttpClient = new ApacheDockerHttpClient.Builder()
                    .dockerHost(dockerClientConfig.getDockerHost())
                    .sslConfig(dockerClientConfig.getSSLConfig())
                    .build();
            dockerClient = com.github.dockerjava.core.DockerClientImpl.getInstance(dockerClientConfig, dockerHttpClient);
        } catch (Exception exception)
        {
            logger.warning("[Docker] -> Error Starting Docker Engine Client");
            exception.printStackTrace();
        }
        logger.info("[Docker] *** Docker Engine Client Started ***");

        Runtime.getRuntime().addShutdownHook(new Thread(this::stopDocker));
    }

    @Override
    public void stopDocker() {
        logger.info("[Docker] *** Docker Engine Client shut down in process ***");
        try {
            dockerClient.close();
            dockerHttpClient.close();
            logger.info("[Docker] ** Client shut down correctly ***");
        } catch (IOException e) {
            logger.warning("[Docker] -> Error shutting down docker client");
            throw new RuntimeException(e);
        }
    }
}
