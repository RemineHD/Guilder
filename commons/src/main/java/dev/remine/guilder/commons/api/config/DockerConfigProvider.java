package dev.remine.guilder.commons.api.config;

public interface DockerConfigProvider {

    
    String getDockerHost();

    boolean verifyDockerTls();

    String getDockerCertPath();

    String getRegistryUsername();

    String getRegistryPassword();

    String getRegistryEmail();

    String getRegistryUrl();

}
