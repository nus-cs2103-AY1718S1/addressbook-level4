package seedu.address.model.user;

//@@author quanle1994

/**
 * Represents a User in the account.
 * Guarantees: details are present and not null, field values are validated.
 */
public class User implements ReadOnlyUser {
    private String userId;
    private String salt = "";
    private String password = "";

    public User() {
    }

    public User(String userId, String salt, String password) {
        this.userId = userId;
        this.salt = salt;
        this.password = password;
    }

    public User(String userId) {
        this.userId = userId;
    }

    /**
     * Creates a copy of the given ReadOnlyUser.
     */
    public User(ReadOnlyUser source) {
        this(source.getUserId(), source.getSalt(), source.getPassword());
    }

    @Override
    public String getUserId() {
        return userId;
    }

    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getSalt() {
        return salt;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyUser // instanceof handles nulls
                && this.isSameStateAs((ReadOnlyUser) other));
    }

    /**
     * Check if the users have the same userName and password
     */
    public boolean sameAs(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ReadOnlyUser // instanceof handles nulls
                && this.isSameUserAs((ReadOnlyUser) other));
    }
}
