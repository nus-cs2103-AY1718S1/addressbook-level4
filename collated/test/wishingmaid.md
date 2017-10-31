# wishingmaid
###### \java\seedu\address\logic\parser\AddPhotoCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddPhotoCommand;

public class AddPhotoCommandParserTest {
    private AddPhotoCommandParser parser = new AddPhotoCommandParser();

    /*@Test
    public void parse_indexSpecified_failure() throws Exception {
        // Has no filepath, picture is default picture
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_FILEPATH.toString();
        AddPhotoCommand expectedCommand = new AddPhotoCommand(INDEX_FIRST_PERSON, new Photo(""));
        assertParseSuccess(parser, userInput, expectedCommand);
        //Has a filepath that that is input from user
        Index newTargetIndex = INDEX_FIRST_PERSON;
        String newUserInput = newTargetIndex.getOneBased() + "C/Users/pictures/pic.png"
                + PREFIX_FILEPATH.toString();
        Photo photo = new Photo("");
        photo.resetFilePath("C/Users/pictures/pic.png");
        AddPhotoCommand newExpectedCommand = new AddPhotoCommand(INDEX_FIRST_PERSON, photo);
        assertParseSuccess(parser, newUserInput, newExpectedCommand);
    }*/

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE);
        assertParseFailure(parser, AddPhotoCommand.COMMAND_WORD, expectedMessage);
    }

}

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_photo() throws Exception {
        final Photo photo = new Photo("");
        AddPhotoCommand command = (AddPhotoCommand) parser.parseCommand(AddPhotoCommand.COMMAND_WORD + " "
                + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_FILEPATH + " " + photo.getFilePath());
        assertTrue(command instanceof AddPhotoCommand);
    }
```
###### \java\seedu\address\model\person\PhotoTest.java
``` java
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
```
###### \java\seedu\address\storage\PhotoStorageTest.java
``` java
package seedu.address.storage;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.PhotoStorage.WRITE_FAILURE_MESSAGE;

import java.io.IOException;

import org.junit.Test;

public class PhotoStorageTest {
    @Test
    public void correctHash() {
        String userFilePath = "C:/Users/pigir/Desktop/saltbae.jpg";
        String expectedUniqueFileName = "-694293159";
        Integer userFilePathHashed = userFilePath.hashCode();
        assertEquals(userFilePathHashed.toString(), expectedUniqueFileName);
    }
    @Test
    public void writeSuccess() {
        String imageSource = "src/test/resources/images/noPhoto.png";
    }
    @Test
    public void writeFailure() {
        String userFilePath = "C:/Users/pigir/Desktop/addPhoto.txt";
        int userFilePathHashed = userFilePath.hashCode();
        try {
            PhotoStorage storage = new PhotoStorage(userFilePath, userFilePathHashed);
            storage.setNewFilePath();
        } catch (IOException e) {
            assertEquals(e.getMessage(), WRITE_FAILURE_MESSAGE);
        }
    }
}
```
