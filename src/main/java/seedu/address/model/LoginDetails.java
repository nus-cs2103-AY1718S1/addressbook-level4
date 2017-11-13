package seedu.address.model;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;


/**
 *  Model class for Login
 */
public class LoginDetails {
    private final StringProperty username;
    private final StringProperty password;

    /**
     * Default constructor.
     */
    public LoginDetails() {
        this(null, null);
    }

    /**
     * Constructor with some initial data.
     */
    public LoginDetails(String username, String password) {
        this.username = new SimpleStringProperty(username);
        this.password = new SimpleStringProperty(password);
    }

    public String getUsername() {
        return username.get();
    }

    public void setUsername(String username) {
        this.username.set(username);
    }

    public StringProperty usernameProperty() {
        return username;
    }

    public String getPassword() {
        return password.get();
    }

    public void setPassword(String password) {
        this.password.set(password);
    }

    public StringProperty passwordProperty() {
        return password;
    }

}
