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

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favourite // instanceof handles nulls
                && this.value.equals(((Favourite) other).value)); // state check
    }
}
