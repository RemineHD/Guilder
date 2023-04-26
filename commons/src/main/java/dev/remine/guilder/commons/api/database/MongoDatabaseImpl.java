package dev.remine.guilder.commons.api.database;

import com.google.inject.Inject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.logging.Logger;

public class MongoDatabaseImpl implements DatabaseService {

    private final Logger logger;

    private MongoClient mongoClient;

    @Inject
    public MongoDatabaseImpl(Logger logger)
    {
        this.logger = logger;
    }

    @Override
    public void startDatabase(String uri) {

        logger.info("[MongoDB] Starting MongoDB Client.");

        if (uri == null)
        {
            logger.warning("[MongoDB] ERROR: uri is null. Can't start database connection.");
            return;
        }

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
