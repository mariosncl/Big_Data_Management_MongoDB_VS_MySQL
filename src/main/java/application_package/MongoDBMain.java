package application_package;

import java.util.ArrayList;
import java.util.List;

public class MongoDBMain {
    public static long time1;
    public static long time2;

    public static void main(String[] args) {
        //Initializing MongoDBSolution instance, using the class constructor, passing the connection string, db name. db collection name
        MongoDBSolution mongoDBSolution = new MongoDBSolution("mongodb://localhost:27017", "social_media_db", "users");


        //generateSampleUserData method will pass the geenrated List to the sampleUsers list
        List<User> sampleUsers = generateSampleUserData(1000);

        // Create the documents and perform retrieve comment operation to measure performance
        testOperationsAndPerformance(mongoDBSolution, sampleUsers);

        // print elapsed times
        System.out.println("Time taken to create users: " + time1 + " milliseconds");
        System.out.println("Time taken to get the comment content of all 1000 users by using userID: " + time2 + " milliseconds");

        // Close MongoDB connection
        mongoDBSolution.closeConnection();
    }

    private static List<User> generateSampleUserData(int count) {
        List<User> users = new ArrayList<>();// creating an array list of User.class to generate users
        for (int i = 0; i < count; i++) {
            String username = "user" + i;
            String email = "user" + i + "@example.com";
            String password = "password" + i;
            String[] comments = {"comment" + i}; // Adding random comment
            users.add(new User(username, email, password, comments));// passing the data to the User.class constructor and adding it to the users List.
        }
        return users;
    }

    // This method will Create and populate the documents using the generated data, and print the comments of each user, and measure performance (elapsed time for gboth operations)
    private static void testOperationsAndPerformance(MongoDBSolution mongoDBSolution, List<User> users) {
        long startTime, endTime, totalTime;

        // Create users
        startTime = System.currentTimeMillis();
        for (User user : users) {
            mongoDBSolution.createUser(user.getUsername(), user.getEmail(), user.getPassword(), user.getComments());
        }
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        time1 = totalTime;// store elapsed time to print in main

        // Get comments of all users
        startTime = System.currentTimeMillis();
        mongoDBSolution.printCommentsOfUsers(mongoDBSolution.getUsersCollection());
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        time2 = totalTime;// store elapsed time to print in main
    }
}