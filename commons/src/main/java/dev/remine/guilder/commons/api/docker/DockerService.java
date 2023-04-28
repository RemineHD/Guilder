package dev.remine.guilder.commons.api.docker;

import com.github.dockerjava.api.DockerClient;
import com.github.dockerjava.transport.DockerHttpClient;

public interface DockerService {

    /**
     * Starts the Docker Engine Client API.
     * It will maintain the connection opened until
     * the program closes
     */
    void startDocker();

    /**
     * Stops Docker Engine Client API.
     * This could be used if we need to restart the connection
     * or if we are not going to use Docker anymore.
     */
    void stopDocker();

    /**
     * @return returns the Docker Client. Refer to this "documentation": <a href="https://github.com/docker-java/docker-java/tree/main/docs"/>
     */
    DockerClient getDockerClient();

    /**
     * @return returns the Apache 5 Http Client used by DockerClient
     * It can be used to do some things that are not implemented in
     * the Docker Client.
     */
    DockerHttpClient getDockerHttpClient();

}
