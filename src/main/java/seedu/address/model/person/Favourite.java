package seedu.address.model.person;

//@@author six3oo
/**
 * Represents a Person's favourites status.
 * Guarantees: immutable
 */
public class Favourite {

    private boolean favourite;

    public Favourite(String faveState) {
        if (faveState.equals("true")) {
            this.favourite = true;
        } else {
            this.favourite = false;
        }
    }

    @Override
    public String toString() {
        return String.valueOf(favourite);
    }
}
