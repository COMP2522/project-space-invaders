package org.space.invader;


import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.ServerApi;
import com.mongodb.ServerApiVersion;
import com.mongodb.client.*;
import com.mongodb.client.model.Sorts;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.bson.Document;

/**
 * DatabaseHandler class that handles the database.
 *
 * @author Bingdi Zhou and Tae Hyung Lee
 */
public class DatabaseHandler implements Iterable<Document> {
  private final MongoDatabase database;
  private final MongoCollection<Document> collection;

  /**
   * Constructor for DatabaseHandler.
   *
   * @param databaseName   as String
   * @param collectionName as String
   */
  public DatabaseHandler(String databaseName, String collectionName) {
    ConnectionString connectionString = new ConnectionString("mongodb+srv://bzhou26:"
            + Password.password
            + "@cluster0.scdg1au.mongodb.net/?retryWrites=true&w=majority");
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


  /**
   * Insert a document into the collection.
   *
   * @param document as a Document object
   */
  public void insertDocument(Document document) {
    try {
      collection.insertOne(document);
      System.out.println("Document inserted successfully.");
    } catch (Exception e) {
      System.err.println("Error while inserting document: " + e.getMessage());
    }
  }

  /**
   * create a document for player.
   *
   * @param playerName as String
   * @param score      as int
   * @return player as Document
   */
  public static Document createPlayerDocument(String playerName, int score) {
    Document player = new Document();
    player.append("playerName", playerName);
    player.append("score", score);
    return player;
  }

  /**
   * get the iterator of the collection.
   *
   * @return latestGameState as Document
   */
  public Document getLatest() {
    Document latestGameState = null;
    String mongoDBUri = "mongodb+srv://bzhou26:" + Password.password
            + "@cluster0.scdg1au.mongodb.net/?retryWrites=true&w=majority";
    String databaseName = "test";
    String collectionName = "game_state";
    try (MongoClient mongoClient = MongoClients.create(mongoDBUri)) {
      MongoDatabase database = mongoClient.getDatabase(databaseName);
      MongoCollection<Document> collection = database.getCollection(collectionName);

      FindIterable<Document> findIterable = collection
              .find().sort(new Document("_id", -1)).limit(1);
      latestGameState = findIterable.first();
    } catch (Exception e) {
      System.err.println("Error while retrieving the latest game state: " + e.getMessage());
    }
    return latestGameState;
  }

  /**
   * get the top players.
   *
   * @param limit as int
   * @return topPlayers as List of Document
   */
  public List<Document> getTopPlayers(int limit) {
    String collectionName = "players";
    List<Document> topPlayers = new ArrayList<>();
    MongoCollection<Document> collection = database.getCollection(collectionName);
    Iterator<Document> iterator = collection.find()
            .sort(Sorts.descending("score")).limit(limit).iterator();

    while (iterator.hasNext()) {
      topPlayers.add(iterator.next());
    }
    return topPlayers;
  }

  /**
   * delete all documents in the collection.
   */
  public void deleteAllDocuments() {
    collection.deleteMany(new Document());
  }

  /**
   * get the iterator of the collection.
   *
   * @return iterator as Iterator of Document
   */
  @Override
  public Iterator<Document> iterator() {
    return collection.find().iterator();
  }
}