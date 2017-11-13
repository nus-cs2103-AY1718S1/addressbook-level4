package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;

//@@author JunQuann
public class DisplayPicTest {

    @Test
    public void isValidDisplayPic() throws IllegalValueException {
        //invalid file path
        assertFalse(DisplayPic.isValidPicPath(" "));
        assertFalse(DisplayPic.isValidPicPath("invalid file path"));
        assertFalse(DisplayPic.isValidPicPath("/filePath/does/not/exists"));

        //valid file path but not picture file
        assertFalse(DisplayPic.isValidPicPath("./src/test/"));
        assertFalse(DisplayPic.isValidPicPath("./src/test/data/"));
        assertFalse(DisplayPic.isValidPicPath("./src/test/data/ImageFileStorageTest"));

        //valid picture extension but invalid file path
        assertFalse(DisplayPic.isValidPicPath("./invalid.png"));
        assertFalse(DisplayPic.isValidPicPath("./invalid.jpeg"));

        //valid file and is picture
        assertTrue(DisplayPic.isValidPicPath("./src/test/data/ImageFileStorageTest/testImage.png"));
    }
}
