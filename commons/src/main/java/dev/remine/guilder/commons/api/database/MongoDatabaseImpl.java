package dev.remine.guilder.commons.api.database;

import com.google.inject.Inject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import dev.remine.guilder.commons.api.config.DbConfigProvider;

import java.util.logging.Logger;

public class MongoDatabaseImpl implements DatabaseService {

    private final DbConfigProvider dbConfigProvider;
    private final Logger logger;

    private MongoClient mongoClient;

    @Inject
    public MongoDatabaseImpl(DbConfigProvider dbConfigProvider, Logger logger)
    {
        this.dbConfigProvider = dbConfigProvider;
        this.logger = logger;
    }

    @Override
    public void startDatabase() {

        logger.info("[MongoDB] Starting MongoDB Client.");

        String uri = "mongodb://" + dbConfigProvider.getMongoUser()
                + ":" +
                dbConfigProvider.getMongoPassword()
                + "@" +
                dbConfigProvider.getMongoAddress()
                + ":" +
                dbConfigProvider.getMongoPort();

        mongoClient = MongoClients.create(uri);

        logger.info("[MongoDB] Server started.");

        Runtime.getRuntime().addShutdownHook(new Thread(() ->
        {
            logger.warning("[MongoDB] *** shutting down MongoDB Client since JVM is shutting down ***");
            try {
                stopDatabase();
                logger.info("[MongoDB] *** MongoDB shut down correctly ***");
            } catch (Exception exception)
            {
                exception.printStackTrace(System.err);
            }
        }));
    }

    @Override
    public void stopDatabase() {
        if (mongoClient != null)
        {
            mongoClient.close();
        }
    }

    @Override
    public MongoClient getDatabaseClient() {
        if (mongoClient != null)
            return mongoClient;
        return null;
    }

}
