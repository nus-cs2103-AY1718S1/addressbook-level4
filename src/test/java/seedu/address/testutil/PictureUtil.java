//@@author Jemereny
package seedu.address.testutil;

import java.io.File;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Picture;

/**
 * Utility class for picture
 */
public class PictureUtil {

    private static final String DEFAULT_PICTURE_LOCATION = "src/main/resources/images/";
    private static final String TEST_FILENAME = "test.png";

    public static File getValidFileSrc() {
        return new File(DEFAULT_PICTURE_LOCATION + Picture.DEFAULT_ALEX);
    }

    public static File getValidFileDst() {
        return new File(Picture.PICTURE_SAVE_LOCATION);
    }

    public static File getValidFileDstWithFilename() {
        return new File(Picture.PICTURE_SAVE_LOCATION + TEST_FILENAME);
    }

    public static File getInvalidFileSrc() {
        return new File("");
    }

    public static String getValidFilename() {
        return TEST_FILENAME;
    }

    /**
     * Returns a default picture from resource folder
     * which should IS valid
     */
    public static String getValidPictureString() {
        return DEFAULT_PICTURE_LOCATION + Picture.DEFAULT_ALEX;
    }

    public static Picture getNullPicture() throws IllegalValueException {
        return new Picture(null);
    }

    public static Picture getPictureWithInvalidLocation() throws IllegalValueException {
        return new Picture("");
    }

    public static Picture getPictureWithValidLocation() throws IllegalValueException {
        return new Picture(Picture.DEFAULT_PICTURE_LOCATION + Picture.DEFAULT_ALEX);
    }
}
