package dev.remine.guilder.commons.api.database;

import com.mongodb.client.MongoClient;

public interface DatabaseService {

    void startDatabase(String url);

    void stopDatabase();

    MongoClient getDatabaseClient();


}
