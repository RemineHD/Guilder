package dev.remine.guilder.commons.api.config;

import javax.annotation.Nonnull;
import java.util.Objects;

public class gRPCConfigProviderImpl implements gRPCConfigProvider {
    @Nonnull
    @Override
    public String getAddress() {
        return Objects.requireNonNullElse(
                System.getenv("GRPC_ADDRESS"),
                "127.0.0.1");
    }

    @Nonnull
    @Override
    public Integer getPort() {
        return Objects.requireNonNullElse(
                Integer.getInteger(System.getenv("GRPC_PORT")),
                50051);
    }

    @Override
    public String certChainFilePath() {
        return Objects.requireNonNullElse(
                System.getenv("GRPC_CERT"),
                "./grpc-cert"
        );
    }

    @Override
    public String privateKeyFilePath() {
        return Objects.requireNonNullElse(
                System.getenv("GRPC_PRIVATE_KEY"),
                "./grpc-key"
        );
    }
}
