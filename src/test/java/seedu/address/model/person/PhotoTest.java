//@@author wishingmaid
package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.person.Photo.URL_VALIDATION;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class PhotoTest {
    @Rule
    public ExpectedException throwException = ExpectedException.none();
    @Test
    public void equals() {
        Photo photo = new Photo("");

        // same object -> returns true
        assertTrue(photo.equals(photo));

        // same value -> returns true
        Photo photoCopy = new Photo("");
        assertTrue(photo.equals(photoCopy));

        // different types -> returns false
        assertFalse(photo.equals(1));

        // null -> returns false;
        assertFalse(photo.equals(null));

        //different filepath -> returns false;
        Photo differentPhoto = new Photo("src/main/resources/images/noPhoto.png");
        assertFalse(photo.equals(differentPhoto));
    }
    @Test
    public void readFile() {
        try {
            Photo photo = new Photo("C:/something/picture.png");
        } catch (IllegalArgumentException e) {
            assertEquals(e.getMessage(), URL_VALIDATION);
        }
    }
}
