package application_package;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class MySQLMain {

    public static void main(String[] args) {
        // Initialize MySQLSolution class instance
        MySQLSolution mySQLSolution = null;
        try {
            mySQLSolution = new MySQLSolution("jdbc:mysql://localhost:3306/social_media_db", "root", "");

            // Generate sample user data
            List<UserSQL> sampleUsers = generateSampleUserData(1000);

            // Test CRUD operations and measure performance, this method will print the results on the terminal
            createTableAndQuery(mySQLSolution, sampleUsers);

            // Get the comments content and userid of all users and measure performance, this method will print the results on the terminal
            retrieveAndPrintUserComments(mySQLSolution);

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            // Close MySQL connection
            if (mySQLSolution != null) {
                try {
                    mySQLSolution.closeConnection();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    // Generates sample user data for testing
    private static List<UserSQL> generateSampleUserData(int count) {
        List<UserSQL> users = new ArrayList<>();
        for (int i = 1; i <= count; i++) { // Adjusted condition to include count
            String userId = String.valueOf(i);
            String username = "user" + i;
            String email = "user" + i + "@example.com";
            String password = "password" + i;
            String[] comments = {"comment" + i}; // Adding random comment
            UserSQL user = new UserSQL(userId, username, email, password, comments);
            users.add(user);
        }
        return users;
    }

    // Creates users in the database and measures the elapsed time, prints it in console
    private static void createTableAndQuery(MySQLSolution mySQLSolution, List<UserSQL> users) {
        long startTime, endTime, totalTime;

        // Create users
        startTime = System.currentTimeMillis();
        for (UserSQL user : users) {
            try {
                mySQLSolution.createUser(user.getUsername(), user.getEmail(), user.getPassword(), user.getComments());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        endTime = System.currentTimeMillis();
        totalTime = endTime - startTime;
        System.out.println("Time taken to create users table and comments table: " + totalTime + " milliseconds");
    }

    // Retrieves and prints comments for all users and measures the elapsed time
    private static void retrieveAndPrintUserComments(MySQLSolution mySQLSolution) {
        try {
            long startTime = System.currentTimeMillis();
            // Retrieve and print comments for all users
            for (int i = 1; i <= 1000; i++) {
                String userId = String.valueOf(i);
                ResultSet resultSet = mySQLSolution.getUserCommentsById(userId);
                System.out.println("Comments for User " + userId + ":");
                while (resultSet.next()) {
                    String comment = resultSet.getString("comment_text");
                    System.out.println(comment);
                }
                System.out.println();
            }
            long endTime = System.currentTimeMillis();
            long totalTime = endTime - startTime;
            System.out.println("Time taken to retrieve and print comments for all users: " + totalTime + " milliseconds");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}