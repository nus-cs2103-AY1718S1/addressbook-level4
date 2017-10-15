package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FavoriteTest {

    @Test
    public void isValidFavoriteStatus() {
        // invalid favorite statuses
        assertFalse(Favorite.isValidFavoriteStatus(""));    // empty string
        assertFalse(Favorite.isValidFavoriteStatus(" "));   // spaces only
        assertFalse(Favorite.isValidFavoriteStatus("y"));   // short-forms not allowed
        assertFalse(Favorite.isValidFavoriteStatus("n"));   // short-forms not allowed
        assertFalse(Favorite.isValidFavoriteStatus("-"));   // special characters not allowed
        assertFalse(Favorite.isValidFavoriteStatus("YES")); // case-sensitive, capitalised not allowed
        assertFalse(Favorite.isValidFavoriteStatus("NO"));  // case-sensitive, capitalised not allowed

        // valid favorite statuses
        assertTrue(Favorite.isValidFavoriteStatus("yes")); // yes
        assertTrue(Favorite.isValidFavoriteStatus("no"));  // no
    }
}
