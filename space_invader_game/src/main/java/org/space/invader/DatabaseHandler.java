package org.space.invader;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;

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
  public Document getLatest() {
    Document latestGameState = null;
    String mongoDBUri = "mongodb+srv://bzhou26:" + Password.password + "@cluster0.scdg1au.mongodb.net/?retryWrites=true&w=majority";
    String databaseName = "space_invaders";
    String collectionName = "game_state";
    try (MongoClient mongoClient = MongoClients.create(mongoDBUri)) {
      MongoDatabase database = mongoClient.getDatabase(databaseName);
      MongoCollection<Document> collection = database.getCollection(collectionName);

      FindIterable<Document> findIterable = collection.find().sort(new Document("_id", -1)).limit(1);
      latestGameState = findIterable.first();
    } catch (Exception e) {
      System.err.println("Error while retrieving the latest game state: " + e.getMessage());
    }
    return latestGameState;
  }


  public void deleteAllDocuments() {
    collection.deleteMany(new Document());
  }

}