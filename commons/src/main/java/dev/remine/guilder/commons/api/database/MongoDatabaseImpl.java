package dev.remine.guilder.commons.api.database;

import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import dev.remine.guilder.commons.api.config.DbConfigProvider;
import org.bson.codecs.configuration.CodecProvider;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;

import java.util.Objects;
import java.util.logging.Logger;

import static com.mongodb.MongoClientSettings.getDefaultCodecRegistry;
import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Singleton
public class MongoDatabaseImpl implements DatabaseService {

    private final DbConfigProvider dbConfigProvider;
    private final Logger logger;

    private MongoClient mongoClient;

    private CodecRegistry pojoCodecRegistry;

    @Inject
    public MongoDatabaseImpl(DbConfigProvider dbConfigProvider, Logger logger)
    {
        this.dbConfigProvider = dbConfigProvider;
        this.logger = logger;
    }

    @Override
    public void startDatabase() {


        CodecProvider pojoCodecProvider = PojoCodecProvider.builder().automatic(true).build();
        pojoCodecRegistry = fromRegistries(getDefaultCodecRegistry(), fromProviders(pojoCodecProvider));

        logger.info("[MongoDB] Starting MongoDB Client.");

        this.mongoClient = MongoClients.create(dbConfigProvider.getMongoURI());

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

    @Override
    public MongoDatabase getDatabase() {
        return mongoClient.getDatabase(Objects.requireNonNullElse(dbConfigProvider.getMongoDatabase(), "guilder")).withCodecRegistry(pojoCodecRegistry);
    }

}
