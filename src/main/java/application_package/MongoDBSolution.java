package application_package;

import com.mongodb.client.*;
import org.bson.Document;
import java.util.Arrays;
import java.util.List;

public class MongoDBSolution {

    private MongoClient mongoClient;
    private MongoDatabase database;
    private MongoCollection<Document> usersCollection;

    //intializing and creating the db and the db collection, this is the class constructor with parameters the db connection string, db name and db collection name
    public MongoDBSolution(String connectionString, String databaseName, String collectionName) {
        this.mongoClient = MongoClients.create(connectionString);
        this.database = mongoClient.getDatabase(databaseName);
        this.usersCollection = database.getCollection(collectionName);
    }

    public MongoDatabase getDatabase() {
        return this.database;
    }

    public MongoCollection<Document> getUsersCollection() {
        return this.usersCollection;
    }

    //method to check if connection is open (not null) and closes it
    public void closeConnection() {
        if (this.mongoClient != null) {
            this.mongoClient.close();
        }
    }

    // Create user method using the generated info, used as parameters.
    public void createUser(String username, String email, String password, String[] comments) {
        // Create a new document representing the user
        Document newUser = new Document("username", username)
                .append("email", email)
                .append("password", password)
                .append("comments", Arrays.asList(comments)); // Adding comments array to the document

        // Insert the document into the users collection
        usersCollection.insertOne(newUser);
    }
    //This method will search and print the comment string array of all users in the collection
    public void printCommentsOfUsers(MongoCollection<Document> userCollection) {
        // Query the collection to retrieve the first 1000 users
        FindIterable<Document> cursor = userCollection.find().limit(1000);

        // Iterate over the cursor and print comments for each user
        for (Document userDoc : cursor) {
            // Extract comments from the user document
            List<String> comments = userDoc.getList("comments", String.class);

            // Print comments for the current user
            System.out.println("Comments for user " + userDoc.get("username") + ": " + comments);
        }
    }


}