package seedu.address.model.person;

import static junit.framework.TestCase.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.ImageInit;

//@@author wenzongteo
public class PhotoTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @BeforeClass
    public static void setUp() {
        ImageInit.checkDirectories();
        ImageInit.initPictures();
    }

    @AfterClass
    public static void recovery() {
        ImageInit.deleteEditedFiles();
        ImageInit.deleteImagesFiles();
    }

    @Test
    public void isValidPhoto() {
        // invalid photos
        assertFalse(Photo.isValidPhoto("")); // empty string
        assertFalse(Photo.isValidPhoto(" ")); // spaces only
        assertFalse(Photo.isValidPhoto("91")); // digits only
        assertFalse(Photo.isValidPhoto("Photo")); // alphabet only
        assertFalse(Photo.isValidPhoto("9011p041")); // alphabets within digits
        assertFalse(Photo.isValidPhoto("9312 1534")); // spaces between digits
        assertFalse(Photo.isValidPhoto("abc abc")); // spaces between words
        assertFalse(Photo.isValidPhoto("data/default")); //No file type.
        assertFalse(Photo.isValidPhoto("data/default.mp3")); //Invalid file type
        assertFalse(Photo.isValidPhoto(".jpeg")); //No file name

        // valid Photo
        assertTrue(Photo.isValidPhoto("docs/images/default.jpeg")); // end with .jpeg file extension
        assertTrue(Photo.isValidPhoto("docs/images/ritchie.jpg")); // end with .jpg file extension
        assertTrue(Photo.isValidPhoto("docs/images/yl_coder.jpg")); // file name with special character _
        assertTrue(Photo.isValidPhoto("docs/images/m133225.jpg")); // file name with digits
        assertTrue(Photo.isValidPhoto("docs/images/prof damith.jpg")); //file name with space in between
    }

    @Test
    public void photoDoesNotExist() {
        try {
            new Photo("doesnotexist.jpg");
        } catch (IllegalValueException ioe) {
            assertEquals(Photo.MESSAGE_PHOTO_NOT_FOUND , ioe.getMessage());
        }
        try {
            new Photo("doesnotexist.jpg", 0);
        } catch (AssertionError ae) {
            assertEquals("Photo should already exist!", ae.getMessage());
        }
    }

    @Test
    public void photoFailCheck() {
        try {
            new Photo("wrong format");
        } catch (IllegalValueException ive) {
            assertEquals(Photo.MESSAGE_PHOTO_CONSTRAINTS, ive.getMessage());
        }
    }

    @Test
    public void photoHashCode() {
        try {
            Photo photo1 = new Photo("default.jpeg", 0);
            Photo photo2 = new Photo("data/images/alice@example.com.jpg");

            //hashcode matches for same email --> return true
            assertTrue(photo1.hashCode() == photo1.hashCode());

            //hashcode don't match for different email --> return false
            assertFalse(photo1.hashCode() == photo2.hashCode());

        } catch (IllegalValueException e) {
            assert false : "shouldn't hit this case at all";
        }
    }

    @Test
    public void compareHash() {
        try {
            Photo photo1 = new Photo("default.jpeg");
            Photo photo2 = new Photo("docs/images/wz.jpg", 0);

            //Compare hash of the same photo --> return true
            assertTrue(photo1.getHash().equals(photo1.getHash()));

            //Compare hash of different photos --> return false
            assertFalse(photo1.getHash().equals(photo2.getHash()));

        } catch (IllegalValueException ive) {
            assert false : "Not possible";
        }
    }

    @Test
    public void compareString() {
        try {
            Photo photo1 = new Photo("default.jpeg");
            Photo photo2 = new Photo("docs/images/wz.jpg", 0);

            //Compare String value of the same photo --> return true
            assertTrue(photo1.toString().equals(photo1.toString()));

            //Compare String value of different photos --> return false
            assertFalse(photo1.toString().equals(photo2.toString()));
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
        }
    }

    @Test
    public void equals() {
        try {
            Photo photo1 = new Photo("default.jpeg");
            Photo photo2 = new Photo("docs/images/wz.jpg", 0);

            //Compare String value of the same photo --> return true
            assertTrue(photo1.equals(photo1));

            //Compare String value of different photos --> return false
            assertFalse(photo1.equals(photo2));
        } catch (IllegalValueException ive) {
            assert false : "Not possible";
        }
    }
}
