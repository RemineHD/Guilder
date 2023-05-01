package dev.remine.guilder.commons.api.database;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoDatabase;

public interface DatabaseService {

    void startDatabase();

    void stopDatabase();

    MongoClient getDatabaseClient();

    MongoDatabase getDatabase();

}
