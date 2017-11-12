# Jemereny
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        assertParseFailure(parser, "1" + INVALID_WEBSITE_DESC, Website.MESSAGE_WEBSITE_CONSTRAINTS); // invalid website
```
###### \java\seedu\address\logic\parser\ThemeCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ThemeCommand;

public class ThemeCommandParserTest {

    public static final String INPUT_LIGHT = "light";
    public static final String INPUT_DARK = "dark";

    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void execute_themeChangeSuccess() {
        // Testing for light theme
        ThemeCommand expectedCommand = new ThemeCommand(ThemeCommand.LIGHT_THEME);
        assertParseSuccess(parser, INPUT_LIGHT, expectedCommand);

        // Testing for dark theme
        expectedCommand = new ThemeCommand(ThemeCommand.DARK_THEME);
        assertParseSuccess(parser, INPUT_DARK, expectedCommand);

        // Message for failure
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE);

        // Test for fail
        assertParseFailure(parser, "pink", expectedMessage);

    }
}
```
###### \java\seedu\address\model\person\PictureTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
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
```
###### \java\seedu\address\model\person\WebsiteTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class WebsiteTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void isValidWebsite() {
        // invalid Websites
        assertFalse(Website.isValidWebsite("")); // empty string
        assertFalse(Website.isValidWebsite(" ")); // spaces only
        assertFalse(Website.isValidWebsite("www.yahoo.com")); // no http protocol
        assertFalse(Website.isValidWebsite("http://YAHOO.com")); // non-numeric
        assertFalse(Website.isValidWebsite("http://www.YAHOO.com")); // lower-case
        assertFalse(Website.isValidWebsite("9312 1534")); // no digits

        // valid Websites
        assertTrue(Website.isValidWebsite(null));
        assertTrue(Website.isValidWebsite("http://www.yahoo.com")); // empty string
        assertTrue(Website.isValidWebsite("https://www.yahoo.com")); // exactly 3 numbers
        assertTrue(Website.isValidWebsite("https://ivle.nus.edu.sg")); // multiple domains
    }
}
```
###### \java\seedu\address\testutil\PictureUtil.java
``` java
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
```
