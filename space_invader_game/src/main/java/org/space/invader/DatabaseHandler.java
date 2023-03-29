package org.space.invader;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import org.bson.Document;
import static com.mongodb.client.model.Filters.eq;


public class DatabaseHandler {
  private MongoDatabase database;
  private MongoCollection<Document> collection;

  public DatabaseHandler(String databaseName, String collectionName) {
    ConnectionString connectionString = new ConnectionString("mongodb+srv://bzhou26:" + Password.password + "@cluster0.scdg1au.mongodb.net/?retryWrites=true&w=majority");
    MongoClientSettings settings = MongoClientSettings.builder()
            .applyConnectionString(connectionString)
            .serverApi(ServerApi.builder()
                    .version(ServerApiVersion.V1)
                    .build())
            .build();
    MongoClient mongoClient = MongoClients.create(settings);
    database = mongoClient.getDatabase(databaseName);
    collection = database.getCollection(collectionName);
  }

  public void insertDocument(Document document) {
    collection.insertOne(document);
  }

  public Document findDocument(String key, String value) {
    return collection.find(eq(key, value)).first();
  }

  public static Document createPlayerDocument(String playerName, int score) {
    Document player = new Document();
    player.append("playerName", playerName);
    player.append("score", score);
    return player;
  }

}