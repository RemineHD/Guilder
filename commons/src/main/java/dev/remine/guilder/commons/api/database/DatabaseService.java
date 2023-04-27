package dev.remine.guilder.commons.api.database;

import com.mongodb.client.MongoClient;

public interface DatabaseService {

    void startDatabase();

    void stopDatabase();

    MongoClient getDatabaseClient();


}
