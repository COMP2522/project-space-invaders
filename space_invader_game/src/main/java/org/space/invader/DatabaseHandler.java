//package org.space.invader;
//
//
//import com.mongodb.ConnectionString;
//import com.mongodb.MongoClientSettings;
//import com.mongodb.ServerApi;
//import com.mongodb.ServerApiVersion;
//import com.mongodb.client.MongoClient;
//import com.mongodb.client.MongoClients;
//import com.mongodb.client.MongoDatabase;
//
//import org.bson.Document;
//import static com.mongodb.client.model.Filters.eq;
//
//
//public class DatabaseHandler {
//  MongoDatabase database;
//  public void put(String key, String val){
//    Document doc = new Document();
//    doc.append(key, val);
//    new Thread (() -> database.getCollection("players").insertOne(doc)).start();
////    database.getCollection("players").insertOne(doc);
//  }
//
//  //constructor
//  public DatabaseHandler() {
//    ConnectionString connectionString = new ConnectionString("mongodb+srv://bzhou26:1234567890@cluster0.scdg1au.mongodb.net/?retryWrites=true&w=majority");
//    MongoClientSettings settings = MongoClientSettings.builder()
//            .applyConnectionString(connectionString)
//            .serverApi(ServerApi.builder()
//                    .version(ServerApiVersion.V1)
//                    .build())
//            .build();
//    MongoClient mongoClient = MongoClients.create(settings);
//    MongoDatabase database = mongoClient.getDatabase("test");
//  }
//  public static void main(String[] args){
//
//    DatabaseHandler db = new DatabaseHandler();
//    db.put("hello", "world");
//
//
////    database.createCollection("players");
////    Document document = new Document();
////    document.append("name", "Ram");
////    document.append("age", 26);
////    document.append("city", "Hyderabad");
////
////    database.getCollection("players").insertOne(document);
////    Document find = database
////            .getCollection("players")
////            .find(eq("name", "Ram"))
////            .first();
////
////    System.out.println(find);
//  }
//}
