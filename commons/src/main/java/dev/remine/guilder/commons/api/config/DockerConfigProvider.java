package dev.remine.guilder.commons.api.config;

import javax.annotation.Nonnull;

public interface DockerConfigProvider {

    /**
     * The Docker Host is a URL used to connect to a
     * Docker Engine.
     * @return The URL of the Main Node (Swarm Master)
     */
    @Nonnull
    String getDockerHost();

    /**
     * Docker uses TLS to establish secure connections between
     * api clients and the Docker Engine
     * @return true in case we want to use TLS, false otherwise.
     */
    boolean verifyDockerTls();

    /**
     * The Docker Cert is used to establish the secure connections
     * when we are verifying TLS.
     * @return the path to the Docker Cert
     */
    String getDockerCertPath();

    /**
     * The Docker Registry allow us to create and distribute Docker
     * images.
     * This Strings will return null if registry is not setup.
     */
    String getRegistryUsername();

    String getRegistryPassword();

    String getRegistryEmail();

    String getRegistryUrl();

}
