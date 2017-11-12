//@@author Jemereny
package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.io.File;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.testutil.PictureUtil;

public class PictureTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorTest() throws Exception {
        thrown.expect(IllegalValueException.class);

        // Valid picture
        Picture picture = new Picture(PictureUtil.getValidPictureString());
        assertNotNull(picture.value);

        // Invalid picture
        picture = new Picture("invalidPhoto.jpg");
        assertNotNull(picture.value);
    }

    @Test
    public void isValidPicture() {
        // invalid pictures
        assertFalse(Picture.isValidPicture("default_profile"));
        assertFalse(Picture.isValidPicture(""));

        // Sample data
        assertTrue(Picture.isValidPicture(Picture.DEFAULT_ALEX));

        // valid pictures
        assertTrue(Picture.isValidPicture(null));
        assertTrue(Picture.isValidPicture(PictureUtil.getValidPictureString()));
    }

    @Test
    public void copyImageTest() throws Exception {
        thrown.expect(IllegalValueException.class);

        // Valid src location
        File src = PictureUtil.getValidFileSrc();
        File dst = PictureUtil.getValidFileDstWithFilename();

        Picture.copyImage(src, dst);

        // Illegal src location
        src = PictureUtil.getInvalidFileSrc();

        Picture.copyImage(src, dst);
    }

    @Test
    public void resizeAndSaveImageTest() throws Exception {
        thrown.expect(IllegalValueException.class);

        // Valid file to read
        File file = PictureUtil.getValidFileSrc();
        String newFileName = PictureUtil.getValidFilename();

        Picture.resizeAndSaveImage(file, newFileName);

        // Invalid file to read
        file = PictureUtil.getInvalidFileSrc();
        Picture.resizeAndSaveImage(file, newFileName);
    }

    @Test
    public void getPictureLocationTest() throws Exception {
        // Null test: return default photo
        Picture picture = new Picture(null);
        assertPictureLocationTrue(Picture.DEFAULT_PICTURE_LOCATION + Picture.DEFAULT_PICTURE,
                picture.getPictureLocation());

        // Sample data test
        picture = new Picture(Picture.DEFAULT_ALEX);
        assertPictureLocationTrue(Picture.DEFAULT_PICTURE_LOCATION + Picture.DEFAULT_ALEX,
                picture.getPictureLocation());
    }

    /**
     * Asserts true if expected string is equals actual string
     */
    public static void assertPictureLocationTrue(String expected, String actual) {
        assertTrue(expected.equals(actual));
    }
}
