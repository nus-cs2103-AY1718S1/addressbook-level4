package seedu.address.model.meeting;

/**
 * Store the person who user is meeting in Meeting class
 */
public class PersonToMeet {
    public final String fullName;

    public PersonToMeet(String name) {
        this.fullName = name;
    }

    @Override
    public String toString() {
        return fullName;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PersonToMeet // instanceof handles nulls
                && this.fullName.equals(((PersonToMeet) other).fullName)); // state check
    }

    @Override
    public int hashCode() {
        return fullName.hashCode();
    }
}
