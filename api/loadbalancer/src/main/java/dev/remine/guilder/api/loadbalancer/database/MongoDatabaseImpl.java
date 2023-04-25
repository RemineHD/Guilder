package dev.remine.guilder.api.loadbalancer.database;

import com.google.inject.Inject;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;

import java.util.logging.Logger;

public class MongoDatabaseImpl implements DatabaseService {

    private final Logger logger;
    private final String uri;

    private MongoClient mongoClient;

    @Inject
    public MongoDatabaseImpl(Logger logger, String uri)
    {
        this.logger = logger;
        this.uri = uri;
    }

    @Override
    public void startDatabase() {

        if (uri == null)
        {
            logger.warning("[MongoDB] ERROR: uri is null. Can't start database connection.");
            return;
        }

        mongoClient = MongoClients.create(uri);
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
