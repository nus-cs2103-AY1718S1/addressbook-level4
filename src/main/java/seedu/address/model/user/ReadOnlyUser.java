package seedu.address.model.user;

//@@author quanle1994

/**
 * A read-only immutable interface for a user in the Account.
 * Implementations should guarantee: details are present and not null, field values are validated.
 */
public interface ReadOnlyUser {
    String getUserId();

    void setUserId(String userId);

    String getSalt();

    String getPassword();

    void setPassword(String password);

    /**
     * Returns true if both have the same username. (interfaces cannot override .equals)
     */
    default boolean isSameStateAs(ReadOnlyUser other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getUserId().equals(this.getUserId())); // state checks here onwards
    }

    /**
     * Returns true if both have the same username and password
     */
    default boolean isSameUserAs(ReadOnlyUser other) {
        return other == this // short circuit if same object
                || (other != null // this is first to avoid NPE below
                && other.getUserId().equals(this.getUserId())
                && other.getPassword().equals(this.getPassword())); // state checks here onwards
    }
}
