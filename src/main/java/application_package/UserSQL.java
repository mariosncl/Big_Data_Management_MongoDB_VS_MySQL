//This class is used as a "User" "Template" in order to create a list of this kind of class to create random users for the database

package application_package;

public class UserSQL {
    private String id;
    private String username;
    private String email;
    private String password;
    private String[] comments;

    public UserSQL(String id, String username, String email, String password, String[] comments) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.password = password;
        this.comments = comments;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public String[] getComments() {
        return comments;
    }

    public void setComments(String[] comments) {
        this.comments = comments;
    }
}