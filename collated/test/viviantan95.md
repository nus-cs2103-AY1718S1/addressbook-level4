# viviantan95
###### /java/seedu/address/logic/commands/AddPhotoCommandTest.java
``` java
package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_PHOTO_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_PHOTO_URL;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBookPersons;

import org.junit.Before;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;

public class AddPhotoCommandTest {
    private Model model;
    private Model expectedModel;
    private String expectedMessage;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBookPersons(), new UserPrefs());
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
    }

    @Test
    public void execute_localPhotoUrlFilteredList_success() throws Exception {
        ReadOnlyPerson updatedPhotoPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person addedPhotoPerson = new Person(updatedPhotoPerson);
        Photo photo = new Photo(VALID_LOCAL_PHOTO_URL);
        addedPhotoPerson.setPhoto(photo);
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        expectedMessage = String.format(AddPhotoCommand.MESSAGE_ADDPHOTO_SUCCESS, addedPhotoPerson);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPhotoPerson);

        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_webPhotoUrlFilteredList_success() throws Exception {
        ReadOnlyPerson updatedPhotoPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person addedPhotoPerson = new Person(updatedPhotoPerson);
        Photo photo = new Photo(VALID_WEB_PHOTO_URL);
        addedPhotoPerson.setPhoto(photo);
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        expectedMessage = String.format(AddPhotoCommand.MESSAGE_ADDPHOTO_SUCCESS, updatedPhotoPerson);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), addedPhotoPerson);

        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_defaultPhotoFilteredList_success() throws Exception {
        ReadOnlyPerson updatedPhotoPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Photo photo = new Photo();
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        expectedMessage = String.format(AddPhotoCommand.MESSAGE_ADDPHOTO_SUCCESS, updatedPhotoPerson);
        updatedPhotoPerson.getPhoto();
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPhotoPerson);

        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_defaultPhotoUrlUnfilteredList_success() throws Exception {
        showFirstPersonOnly(model);
        ReadOnlyPerson updatedPhotoPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Photo photo = new Photo();
        AddPhotoCommand addPhotoCommand = prepareCommand(INDEX_FIRST_PERSON, photo);

        expectedMessage = String.format(AddPhotoCommand.MESSAGE_ADDPHOTO_SUCCESS, updatedPhotoPerson);
        expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), updatedPhotoPerson);

        assertCommandSuccess(addPhotoCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndex_failure () throws Exception {
        Index indexOutOfBound = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Photo photo = new Photo();
        AddPhotoCommand addPhotoCommand = prepareCommand(indexOutOfBound, photo);

        expectedMessage = Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
        assertCommandFailure(addPhotoCommand, model, expectedMessage);
    }

    @Test
    public void equals() {
        Photo photo = new Photo();
        final AddPhotoCommand command = new AddPhotoCommand(INDEX_FIRST_PERSON, photo);

        //same object -> returns true
        assertTrue(command.equals(command));

        //same values -> returns true
        AddPhotoCommand sameValueCommand = new AddPhotoCommand(INDEX_FIRST_PERSON, photo);
        assertTrue(command.equals(sameValueCommand));

        //different types -> return false
        assertFalse(command.equals(new ClearCommand()));

        // null -> returns false
        assertFalse(command.equals(null));

        //different person
        AddPhotoCommand diffPersonCommand = new AddPhotoCommand(INDEX_SECOND_PERSON, photo);
        assertFalse(command.equals(diffPersonCommand));

        //different index -> returns false
        assertFalse(command.equals(new AddPhotoCommand(INDEX_SECOND_PERSON, photo)));

        //different types -> returns false
        assertFalse(command.equals(new ClearCommand()));
    }

    //Returns an object of AddPhotoCommand with {@param index} and {@param photo}
    private AddPhotoCommand prepareCommand(Index index, Photo photo) {
        AddPhotoCommand addPhotoCommand = new AddPhotoCommand(index, photo);
        addPhotoCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addPhotoCommand;
    }

}
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String VALID_BIRTHDAY_AMY = "11/11/1991";
    public static final String VALID_BIRTHDAY_BOB = "22/22/1992";
    public static final String VALID_BIRTHDAY_CARRIE = "33/33/1993";
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String VALID_WEB_PHOTO_URL = "https://cdn.images.express.co.uk/img/dynamic/36/590x"
            + "/secondary/CHRIS-EVANS-865133.jpg";
    public static final String VALID_LOCAL_PHOTO_URL = "images/defaultPhoto.png";
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "11/01/19955";
    // more than 8 numbers
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String INVALID_URL_DESC = " " + PREFIX_PHOTO + "images/defaultPhoto1.png"; //Not a valid URL
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
        // multiple birthdays - last birthday accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_AMY + PHONE_DESC_BOB
                + BIRTHDAY_DESC_AMY + BIRTHDAY_DESC_BOB + EMAIL_DESC_AMY + EMAIL_DESC_BOB + ADDRESS_DESC_BOB
                        + MODULE_DESC_CS2101, new AddCommand(expectedPerson));
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
        // missing birthday prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + VALID_BIRTHDAY_BOB + EMAIL_DESC_BOB + ADDRESS_DESC_BOB, expectedMessage);
```
###### /java/seedu/address/logic/parser/AddCommandParserTest.java
``` java
        // invalid birthday
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + BIRTHDAY_DESC_BOB
                        + EMAIL_DESC_BOB + ADDRESS_DESC_BOB + INVALID_BIRTHDAY_DESC + MODULE_DESC_GER1000
                        + MODULE_DESC_CS2101, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### /java/seedu/address/logic/parser/AddPhotoCommandParserTest.java
``` java
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_URL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.URL_DESC_LOCAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddPhotoCommand;
import seedu.address.model.person.Photo;

public class AddPhotoCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE);

    private AddPhotoCommandParser parser = new AddPhotoCommandParser();

    @Test
    public void parse_invalidInfo_failure() {
        //zero index
        assertParseFailure(parser, "0" + URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        //negative index
        assertParseFailure(parser, "-1" + URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        //index containing chars
        assertParseFailure(parser, "test" + URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        //invalid Url
        assertParseFailure(parser, "1" + INVALID_URL_DESC, Photo.MESSAGE_PHOTO_CONSTRAINTS);
    }


    //Missing information when using addPhoto command -> failure
    @Test
    public void parse_missingInfo_failure() {
        //missing index
        assertParseFailure(parser, URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);


        //missing both index and URL
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

}
```
###### /java/seedu/address/model/person/PhotoTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_PHOTO_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_PHOTO_URL;

import org.junit.Test;

public class PhotoTest {
    @Test
    public void isValidPhotoUrl() {
        //valid URL
        assertTrue(Photo.isValidPhoto(VALID_LOCAL_PHOTO_URL));
        assertTrue(Photo.isValidPhoto(VALID_WEB_PHOTO_URL));

        //invalid URL
        assertFalse(Photo.isValidPhoto("www.google.com")); //invalid Photo URL
    }
}
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
        descriptor.setBirthday(person.getBirthday());
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBirthday(String birthday) {
        try {
            ParserUtil.parseBirthday(Optional.of(birthday)).ifPresent(descriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    public static final String DEFAULT_BIRTHDAY = "25/09/1990";
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
            Birthday defaultBirthday = new Birthday(DEFAULT_BIRTHDAY);
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
            Photo defaultPhoto = new Photo();
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code Birthday} that we are building.
     */
    public PersonBuilder withBirthday(String birthday) {
        try {
            this.person.setBirthday(new Birthday(birthday));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/PersonUtil.java
``` java
        sb.append(PREFIX_BIRTHDAY + person.getBirthday().value + " ");
```
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
        commandBoxForTest = commandBox;
```
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    @Test
    public void handleKeyPress_escape() {
        //empty command box
        guiRobot.push(KeyCode.ESCAPE);
        assertTrue("".equals(commandBoxHandle.getInput()));

        //enter text in command box
        guiRobot.write("Test");
        //check if command box has correct input
        assertTrue("Test".equals(commandBoxHandle.getInput()));
        //push ESCAPE and check if command box is empty
        guiRobot.push(KeyCode.ESCAPE);
        assertFalse("Test".equals(commandBoxHandle.getInput()));
        assertTrue("".equals(commandBoxHandle.getInput()));
    }

    @Test
    public void handleKeyPress_alt() {
        //gets text field
        TextField textField = commandBoxForTest.getCommandTextField();
        //insert text for testing
        guiRobot.write("Test");
        assertTrue("Test".equals(textField.getText()));

        //check if myTextField text cursor is same length as text in command box
        assertTrue(textField.getCaretPosition() == commandBoxHandle.getInput().length());

        //check if text cursor is flushed to the right after move left once and alt is pushed
        guiRobot.push(KeyCode.LEFT);
        guiRobot.push(KeyCode.ALT);
        assertFalse(textField.getCaretPosition() == 3);
        assertTrue(textField.getCaretPosition() == 4);

        //check if text cursor is flushed to the right if initial text cursor is to the extreme left
        textField.positionCaret(0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(textField.getCaretPosition() == 4);
        assertFalse(textField.getCaretPosition() == 0);
    }

    @Test
    public void handleKeyPress_delete() {
        TextField textField = commandBoxForTest.getCommandTextField();

        //empty command box
        assertTrue(textField.getCaretPosition() == 0);
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("".equals(commandBoxHandle.getInput()));

        //caret at extreme left but with chunk of words on the right
        guiRobot.write("Test");
        textField.positionCaret(0);
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("Test".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //caret at extreme left but with chunk of spaces on the right
        guiRobot.write("    ");
        textField.positionCaret(0);
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("    ".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //chunk of words on the left of text cursor
        guiRobot.write("Test");
        textField.positionCaret(textField.getLength());
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("".equals(commandBoxHandle.getInput()));

        //chunk of spaces on the left of text cursor
        guiRobot.write("    ");
        textField.positionCaret(textField.getLength());
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("".equals(commandBoxHandle.getInput()));

        //chunk of space before chunk of words on the left of text cursor
        guiRobot.write("    "); //4 spaces
        guiRobot.write("Test"); //4 letters
        textField.positionCaret(textField.getLength());
        guiRobot.push(KeyCode.DELETE);
        assertNotNull(textField.getCaretPosition());
        assertTrue(textField.getCaretPosition() == commandBoxHandle.getInput().length());
        assertTrue("    ".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //chunk of words before chunk of spaces on the left of text cursor
        guiRobot.write("Test"); //4 letters
        guiRobot.write("    "); //4 spaces
        textField.positionCaret(8);
        guiRobot.push(KeyCode.DELETE);
        assertNotNull(textField.getCaretPosition());
        assertTrue(textField.getCaretPosition() == commandBoxHandle.getInput().length());
        assertTrue("Test".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //test to remove words between 2 chunks of spaces
        guiRobot.write("    "); //4 spaces
        guiRobot.write("Test"); //4 letters
        guiRobot.write("    "); //4 spaces
        textField.positionCaret(8);
        guiRobot.push(KeyCode.DELETE);
        assertNotNull(textField.getCaretPosition());
        assertFalse(textField.getCaretPosition() == commandBoxHandle.getInput().length());
        assertTrue(textField.getCaretPosition() == 4);
        assertTrue("        ".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //test to remove spaces between 2 chunks of words
        guiRobot.write("Test"); //4 letters
        guiRobot.write("    "); //4 spaces
        guiRobot.write("Test"); //4 letters
        textField.positionCaret(8);
        guiRobot.push(KeyCode.DELETE);
        assertNotNull(textField.getCaretPosition());
        assertFalse(textField.getCaretPosition() == commandBoxHandle.getInput().length());
        assertTrue(textField.getCaretPosition() == 4);
        assertTrue("TestTest".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //test for text cursor in between chunk of words
        guiRobot.write("Test");
        textField.positionCaret(2);
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("st".equals(commandBoxHandle.getInput()));

        //clear command box
        guiRobot.push(KeyCode.ESCAPE);

        //test for text cursor in between chunk of spaces
        guiRobot.write("    ");
        textField.positionCaret(2);
        guiRobot.push(KeyCode.DELETE);
        assertTrue(textField.getCaretPosition() == 0);
        assertFalse(textField.getCaretPosition() > 0);
        assertTrue("  ".equals(commandBoxHandle.getInput()));
    }

    @Test
    public void handleKeyPress_insert() {
        TextField textField = commandBoxForTest.getCommandTextField();
        String correctTextField = "add " + PREFIX_NAME + " " + PREFIX_PHONE + " " + PREFIX_BIRTHDAY + " "
                + PREFIX_EMAIL + " " + PREFIX_ADDRESS + " " + PREFIX_MODULE;

        //empty command box
        guiRobot.push(KeyCode.INSERT);
        assertTrue(correctTextField.equals(textField.getText()));

        //with text in command box
        guiRobot.write("Test");
        //push INSERT and checks if command box is replaced with the correct text field of AddCommand shortcut
        guiRobot.push(KeyCode.INSERT);
        assertTrue(correctTextField.equals(textField.getText()));
    }
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhones(VALID_PHONE_AMY)
                .withBirthday(VALID_BIRTHDAY_BOB).withEmails(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withModules(VALID_MODULE_CS2101).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + BIRTHDAY_DESC_BOB + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY + MODULE_DESC_CS2101;
        assertCommandSuccess(command, toAdd);
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: missing birthday -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE));
```
###### /java/systemtests/AddCommandSystemTest.java
``` java
        /* Case: invalid birthday -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + INVALID_BIRTHDAY_DESC + EMAIL_DESC_AMY
                + ADDRESS_DESC_AMY;
        assertCommandFailure(command, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### /java/systemtests/EditCommandSystemTest.java
``` java
        /* Case: invalid birthday -> rejected */
        assertCommandFailure(EditCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased()
                    + INVALID_BIRTHDAY_DESC, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
