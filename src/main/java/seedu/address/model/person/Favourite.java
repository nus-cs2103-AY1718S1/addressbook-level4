package seedu.address.model.person;

//@@author nassy93
/**
 * Stores a person's current "Favourite" status
 *
 */
public class Favourite {
    public final Boolean value;

    public Favourite(Boolean val) {
        value = val;
    }

    public boolean getStatus() {
        return value;
    }

}
