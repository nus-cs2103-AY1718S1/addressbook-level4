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
    public int hashCode() {
        return fullName.hashCode();
    }
}
