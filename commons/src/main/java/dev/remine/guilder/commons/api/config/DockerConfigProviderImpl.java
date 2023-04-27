package dev.remine.guilder.commons.api.config;

import javax.annotation.Nonnull;
import java.util.Objects;

public class DockerConfigProviderImpl implements DockerConfigProvider {

    @Nonnull
    @Override
    public String getDockerHost() {
        return Objects.requireNonNullElse(
                System.getenv("DOCKER_HOST"),
                "tcp://localhost:2376"); //Default Docker URL
    }

    @Override
    public boolean verifyDockerTls() {
        return Objects.requireNonNullElse(
                Boolean.parseBoolean(System.getenv("DOCKER_TLS")),
                false);
    }

    @Override
    public String getDockerCertPath() {
        return Objects.requireNonNullElse(System.getenv("DOCKER_CERT"),
                "./docker-cert");
    }

    @Override
    public String getRegistryUsername() {
        return System.getenv("DOCKER_REGISTRY_USER");
    }

    @Override
    public String getRegistryPassword() {
        return System.getenv("DOCKER_REGISTRY_PASSWORD");
    }

    @Override
    public String getRegistryEmail() {
        return System.getenv("DOCKER_REGISTRY_EMAIL");
    }

    @Override
    public String getRegistryUrl() {
        return System.getenv("DOCKER_REGISTRY_URL");
    }
}

