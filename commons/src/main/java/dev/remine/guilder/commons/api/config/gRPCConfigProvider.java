package dev.remine.guilder.commons.api.config;

import javax.annotation.Nonnull;


public interface gRPCConfigProvider {

    /**
     * @return the IP Address that the gRPC server should use.
     * This data should come from the Environment Vars
     * in case the environment vars are null a default port
     * will be provided
     */
    @Nonnull
    String getAddress();

    /**
     * @return the Port that the gRPC server should use.
     * This data should come from the Environment Vars
     * in case the environment vars are null a default port
     * will be provided
     */
    @Nonnull
    Integer getPort();


    /**
     * The Cert Chain File is used by the TlsServerCredentials in order
     * to build secure credentials for the gRPC Server.
     * In our use case all the services will be in the same private
     * network, so a tls connection may not be needed, but we will have
     * the code here "just in case"
     * @return the path were the cert chain is located.
     */
    String certChainFilePath();

    /**
     * The Private Key is used another requirement for the TlsServerCredentials
     * For more info read the cerChainFilePath() comments.
     * @return the path were the private key is located.
     */
    String privateKeyFilePath();

}
