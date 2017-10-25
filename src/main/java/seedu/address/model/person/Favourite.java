package seedu.address.model.person;

/**
 *  Represents whether a Person is a favourite contact or not
 */
public class Favourite {

    private boolean favourite;
    private String status;

    /**
     * Default constructor
     * if no parameter is passed in, the favourite value is initialised to false
     */
    public Favourite() {
        this.favourite = false;
        this.status = "False";
    }

    public Favourite(boolean favourite) {
        this.favourite = favourite;
        this.status = favourite ? "True" : "False";
    }

    private void setFavouriteStatus() {
        status = favourite ? "True" : "False";
    }

    public void toggleFavourite() {
        favourite = !favourite;
        setFavouriteStatus();
    }

    public boolean getFavourite() {
        return favourite;
    }

    public String getStatus() {
        setFavouriteStatus(); // Ensure the status is in sync with favourite
        return status;
    }

    @Override
    public String toString() {
        setFavouriteStatus(); // Ensure the status is in sync with favourite
        return status;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Favourite) // instanceof handles nulls
                && this.favourite == ((Favourite) other).favourite; // state check
    }

    @Override
    public int hashCode() {
        setFavouriteStatus();
        return status.hashCode();
    }
}
