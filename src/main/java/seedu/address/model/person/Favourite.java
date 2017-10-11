package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;

public class Favourite {

    public static boolean favourite;


    /**
     Constructs a Favourite object and instantiate it to either true or false false.
     */
    public Favourite(boolean toFavourite) {
        if (toFavourite) {
            this.favourite = true;
        }
    }

    public Favourite() {
        this.favourite = false;
    }

    public boolean checkFavourite() { return favourite; }

}
