package seedu.address.model.person;

import static java.util.Objects.requireNonNull;

/**
 * Represents a Person's favourite status in the address book.
 * Guarantees: immutable; is always valid
 */
public class FavouriteStatus {

    public boolean isFavourite;
    
    public FavouriteStatus(boolean isFavourite) {
        requireNonNull(isFavourite);
        this.isFavourite = isFavourite;
    }
    
    public boolean getStatus() { 
        return isFavourite; 
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteStatus // instanceof handles nulls
                && this.isFavourite == ((FavouriteStatus) other).isFavourite); // state check
    }

}
