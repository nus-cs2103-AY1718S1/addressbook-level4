//@@author A0143832J
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class FavoriteTest {

    @Test
    public void testEquals() {
        // equal Favorite objects
        assertTrue(new Favorite(true).equals(new Favorite(true)));
        assertTrue(new Favorite(false).equals(new Favorite(false)));

        //unequal Favorite objects
        assertFalse(new Favorite(true).equals(new Favorite(false)));
        assertFalse(new Favorite(false).equals(new Favorite(true)));
    }
}
//@@author
