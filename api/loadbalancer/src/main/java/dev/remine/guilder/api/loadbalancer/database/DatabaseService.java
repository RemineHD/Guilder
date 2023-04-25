package dev.remine.guilder.api.loadbalancer.database;

import com.mongodb.client.MongoClient;

public interface DatabaseService {

    void startDatabase();

    void stopDatabase();

    MongoClient getDatabaseClient();


}
