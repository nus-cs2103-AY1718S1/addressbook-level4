package seedu.address.model.person;

/**
 Shows the status of whether a person is a favourite contact.
 */
public class Favourite {

    public final boolean favourite;

    /**
     Constructs a Favourite object and instantiate it to either true or false false.
     */
    public Favourite(boolean toFavourite) {

        this.favourite = toFavourite;
    }

    public Favourite() {
        this.favourite = false;
    }

    public boolean checkFavourite() { return favourite; }

    public String toString() {
        return (true) ? "true" : "false";
    }

}
