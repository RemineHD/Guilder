package dev.remine.guilder.commons.api.config;

import javax.annotation.Nonnull;
import java.util.Objects;

public class DbConfigProviderImpl implements DbConfigProvider{

    @Nonnull
    @Override
    public String getMongoURI() {
        return Objects.requireNonNullElse(
                System.getenv("MONGODB_URL"),
                "mongodb://localhost:27017");
    }


    @Override
    public String getMongoDatabase() {
        return System.getenv("MONGODB_DATABASE");
    }
}
