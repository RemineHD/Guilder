package dev.remine.guilder.commons.api.config;

import javax.annotation.Nonnull;
import java.util.Objects;

public class DbConfigProviderImpl implements DbConfigProvider{

    @Nonnull
    @Override
    public String getMongoAddress() {
        return Objects.requireNonNullElse(
                System.getenv("DB_ADDRESS"),
                "127.0.0.1");
    }

    @Override
    public Integer getMongoPort() {
        return Objects.requireNonNullElse(
                Integer.getInteger(System.getenv("DB_PORT")),
                        270170);
    }

    @Nonnull
    @Override
    public String getMongoUser() {
        return Objects.requireNonNullElse(
                System.getenv("DB_USER"),
                "guilder");
    }

    @Nonnull
    @Override
    public String getMongoPassword() {
        return Objects.requireNonNullElse(
                System.getenv("DB_PASSWORD"),
                "GuilderDBSuperSecurePassword"
        );
    }

    @Override
    public String getMongoDatabase() {
        return System.getenv("DB_DATABASE");
    }
}
