//This class is used as a "User" "Template" in order to create a list of this kind of class to create random users for the database
package application_package;

import org.bson.types.ObjectId;
import java.util.Arrays;

public class User {
    private ObjectId id;
    private String username;
    private String email;
    private String password;
    private String[] comments;

    public User(String username, String email, String password, String[] comments) {
        this.username = username;
        this.email = email;
        this.password = password;
        this.comments = comments;
    }

    public ObjectId getId() {
        return id;
    }

    public void setId(ObjectId id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String[] getComments() {
        return comments;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", comments=" + Arrays.toString(comments) +
                '}';
    }
}