package application_package;
import java.sql.*;

public class MySQLSolution {

    private Connection connection;

    public MySQLSolution(String url, String username, String password) throws SQLException {
        this.connection = DriverManager.getConnection(url, username, password);
    }

    public void closeConnection() throws SQLException {
        if (this.connection != null) {
            this.connection.close();
        }
    }
    // Method to create tables if they don't exist
    private void createTables() throws SQLException {
        try (Statement statement = connection.createStatement()) {
            // Create users table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "username VARCHAR(50) NOT NULL," +
                    "email VARCHAR(100) NOT NULL UNIQUE," +
                    "password VARCHAR(100) NOT NULL" +
                    ")");

            // Create comment table
            statement.executeUpdate("CREATE TABLE IF NOT EXISTS comment (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "user_id INT," +
                    "comment_text TEXT," +
                    "FOREIGN KEY (user_id) REFERENCES users(id)" +
                    ")");
        }
    }
    // Create user
    public void createUser(String username, String email, String password, String[] comments) throws SQLException {

        // Create the tables if they don't exist
        createTables();

        String insertUserQuery = "INSERT INTO users (username, email, password) VALUES (?, ?, ?)";
        String insertCommentQuery = "INSERT INTO comment (user_id, comment_text) VALUES (?, ?)";

        try (PreparedStatement userStatement = connection.prepareStatement(insertUserQuery, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement commentStatement = connection.prepareStatement(insertCommentQuery)) {

            // Insert user
            userStatement.setString(1, username);
            userStatement.setString(2, email);
            userStatement.setString(3, password);
            userStatement.executeUpdate();

            // Get generated user ID
            ResultSet generatedKeys = userStatement.getGeneratedKeys();
            int userId = -1;
            if (generatedKeys.next()) {
                userId = generatedKeys.getInt(1);
            }

            // Insert comments with user ID
            for (String comment : comments) {
                commentStatement.setInt(1, userId);
                commentStatement.setString(2, comment);
                commentStatement.executeUpdate();
            }
        }
    }

    // Get comments of user by ID
    public ResultSet getUserCommentsById(String id) throws SQLException {
        String query = "SELECT comment_text FROM comment WHERE user_id = ?"; // the actual query
        PreparedStatement statement = connection.prepareStatement(query);
        statement.setString(1, id);
        return statement.executeQuery();
    }

}