package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FavouriteTest {

    @Test
    public void equals() {
        Favourite favourite = new Favourite();

        // same object -> returns true
        assertTrue(favourite.equals(favourite));

        // same values -> returns true
        Favourite favouriteCopy = new Favourite();
        assertTrue(favourite.equals(favouriteCopy));

        // different types -> returns false
        assertFalse(favourite.equals(1));

        // null -> returns false
        assertFalse(favourite.equals(null));

        // different values -> returns false
        Favourite differentFavourite = new Favourite();
        differentFavourite.setFavourite();
        assertFalse(favourite.equals(differentFavourite));
    }
}
