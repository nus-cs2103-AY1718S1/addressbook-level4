//@@author Houjisan
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FavouriteStatusTest {

    @Test
    public void equals() {
        FavouriteStatus favouriteStatus = new FavouriteStatus(false);

        // same object -> returns true
        assertTrue(favouriteStatus.equals(favouriteStatus));

        // same values -> returns true
        FavouriteStatus favouriteStatusCopy = new FavouriteStatus(favouriteStatus.isFavourite);
        assertTrue(favouriteStatus.equals(favouriteStatusCopy));

        // different types -> returns false
        assertFalse(favouriteStatus.equals(1));

        // null -> returns false
        assertFalse(favouriteStatus.equals(null));

        // different favourite status -> returns false
        FavouriteStatus differentFavouriteStatus = new FavouriteStatus(true);
        assertFalse(favouriteStatus.equals(differentFavouriteStatus));
    }
}
