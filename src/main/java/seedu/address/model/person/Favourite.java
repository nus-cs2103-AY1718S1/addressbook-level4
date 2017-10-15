package seedu.address.model.person;

/**
 *  Represents whether a Person is a favourite contact or not
 */
public class Favourite {

    public String status;

    public Favourite() {
        this.status = "False"; // default state
    }

    public void setFavourite() {
        this.status = "True";
    }

    @Override
    public String toString() {
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favourite) // instanceof handles nulls
                && this.status.equals(((Favourite) other).status); // state check
    }

    @Override
    public int hashCode() {
        return status.hashCode();
    }
}
