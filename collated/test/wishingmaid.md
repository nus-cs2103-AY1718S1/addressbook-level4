# wishingmaid
###### \java\seedu\address\logic\commands\AddPhotoCommandTest.java
``` java
public class AddPhotoCommandTest {
    private static final String INVALID_FILETYPE = "docs/AboutUs.adoc";
    private static final String VALID_FILEPATH = "src/main/resources/images/noPhoto.png";
    private static final String VALID_ALT_FILEPATH = "src/main/resources/images/fail.png";
    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    @Test
    public void equals() {
        AddPhotoCommand standardCommand = new AddPhotoCommand(INDEX_FIRST_PERSON, new Photo(""));
        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));
        // null -> returns false
        assertFalse(standardCommand.equals(null));
        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));
        // different index -> returns false
        assertFalse(standardCommand.equals(new AddPhotoCommand(INDEX_SECOND_PERSON, new Photo(VALID_ALT_FILEPATH))));
    }
    @Test
    public void execute_addPhoto_success() throws PersonNotFoundException, DuplicatePersonException, CommandException {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withPhoto(VALID_FILEPATH).build();
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getPhoto().getFilePath());
        String expectedMessage = String.format(AddPhotoCommand.MESSAGE_ADD_PHOTO_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_deletePhoto_success() throws Exception {
        Person editedPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setPhoto(new Photo(""));
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getPhoto().getFilePath()
                .toString());
        String expectedMessage = String.format(AddPhotoCommand.MESSAGE_DELETE_PHOTO_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }
    @Test
    public void execute_addPhoto_failure() throws Exception {
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withPhoto(INVALID_FILETYPE).build();
        try {
            AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getPhoto().getFilePath());
        } catch (CommandException e) {
            assertEquals(e.getMessage(), PhotoStorage.WRITE_FAILURE_MESSAGE);
        }
    }
    @Test
    public void constructor_nullPhoto_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddPhotoCommand(null, null);
    }
    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        AddPhotoCommand addPhotoCommand = prepareCommand(outOfBoundIndex, VALID_FILEPATH);
        assertCommandFailure(addPhotoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        AddPhotoCommand addPhotoCommand = prepareCommand(outOfBoundIndex, VALID_FILEPATH);
        assertCommandFailure(addPhotoCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    private AddPhotoCommand prepareCommand(Index index, String filepath) throws CommandException {
        AddPhotoCommand addPhotoCommand = new AddPhotoCommand(index, new Photo(filepath));
        addPhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPhotoCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddPhotoCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddPhotoCommand;

public class AddPhotoCommandParserTest {
    private AddPhotoCommandParser parser = new AddPhotoCommandParser();
    @Test
    public void parse_missingFields_failure() throws Exception {
        //fails because there is no index and prefix specified
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE);
        assertParseFailure(parser, AddPhotoCommand.COMMAND_WORD, expectedMessage);
        //fails because there is no index specified
        String expectedMessageNoIndex = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE);
        String userInput = AddPhotoCommand.COMMAND_WORD + " " + PREFIX_FILEPATH.toString()
                + "src/main/resources/images/address.png";
        assertParseFailure(parser, userInput, expectedMessageNoIndex);
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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.storage.PhotoStorage.WRITE_FAILURE_MESSAGE;

import java.io.File;
import java.io.IOException;

import org.junit.Test;

import seedu.address.commons.util.ExtensionCheckerUtil;

public class PhotoStorageTest {
    private String[] allowedExt =  new String[]{"jpg", "png", "JPEG"};
    @Test
    public void writeSuccess() throws IOException {
        String imageSource = "src/test/resources/images/noPhoto.png";
        PhotoStorage testStorage = new PhotoStorage(imageSource);
        String newFilePath = testStorage.setNewFilePath();
        File imageFile = new File(newFilePath);
        assertTrue(imageFile.exists());
    }
    @Test
    public void writeFailureWithNonImageType() {
        String userFilePath = "C:/Users/pigir/Desktop/addPhoto.txt";
        try {
            PhotoStorage storage = new PhotoStorage(userFilePath);
            storage.setNewFilePath();
        } catch (IOException e) {
            assertEquals(e.getMessage(), WRITE_FAILURE_MESSAGE);
        }
    }
    @Test
    public void writeFailureWithImageType() {
        String userFilePath = "C:/Users/pigir/Desktop/sfsf.gif";
        try {
            PhotoStorage storage = new PhotoStorage(userFilePath);
            storage.setNewFilePath();
        } catch (IOException e) {
            assertEquals(e.getMessage(), WRITE_FAILURE_MESSAGE);
        }
    }
    @Test
    public void getExtensionSuccess() {
        String filePathJpg = "C:/Users/pigir/Desktop/images/saltbae.jpg";
        String extJpg = ExtensionCheckerUtil.getExtension(filePathJpg);
        assertEquals(extJpg, "jpg");

        String filePathPng = "C:/Users/pigir/Desktop/images/saltbae.png";
        String extPng = ExtensionCheckerUtil.getExtension(filePathPng);
        assertEquals(extPng, "png");

        String filePathJpeg = "C:/Users/pigir/Desktop/images/saltbae.JPEG";
        String extJpeg = ExtensionCheckerUtil.getExtension(filePathJpeg);
        assertEquals(extJpeg, "JPEG");
    }
    @Test
    public void checkExtension() {
        String filePathJpg = "C:/Users/pigir/Desktop/images/saltbae.jpg";
        String extJpg = ExtensionCheckerUtil.getExtension(filePathJpg);
        assertTrue(ExtensionCheckerUtil.isOfType(extJpg, allowedExt));

        String filePathGif = "C:/Users/pigir/Desktop/images/saltbae.gif";
        String extGif = ExtensionCheckerUtil.getExtension(filePathGif);
        assertFalse(ExtensionCheckerUtil.isOfType(extGif, allowedExt));
    }
}
```
