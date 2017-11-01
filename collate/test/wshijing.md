# wshijing
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void sortPersonList() {
            fail("This method should not be called.");
        }
    }
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
package seedu.address.logic.commands;

import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit test for SortCommand.
 */
public class SortCommandTest {
    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_sortList_showsSortedList() {
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_sortList_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
    }
```
###### \java\seedu\address\model\person\PhotoTest.java
``` java
package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class PhotoTest {

    @Test
    public void isValidPhoto() {
        // blank photo
        assertFalse(Photo.isValidPhoto("")); // empty string
        assertFalse(Photo.isValidPhoto(" ")); // spaces only

        // invalid parts
        assertFalse(Photo.isValidPhoto(" .jpg")); // white space as first character of file name
        assertFalse(Photo.isValidPhoto(" .png")); // white space as first character of file name
        assertFalse(Photo.isValidPhoto(" .gif")); // white space as first character of file name
        assertFalse(Photo.isValidPhoto(" .bmp")); // white space as first character of file name
        assertFalse(Photo.isValidPhoto("ashleysimpsons.txt")); // invalid file extension
        assertFalse(Photo.isValidPhoto("ashleysimpsons.exe")); // invalid file extension
        assertFalse(Photo.isValidPhoto("ashleysimpsons.mp3")); // invalid file extension


        // missing parts
        assertFalse(Photo.isValidPhoto(".jpg")); // missing file name
        assertFalse(Photo.isValidPhoto(".png")); // missing file name
        assertFalse(Photo.isValidPhoto(".gif")); // missing file name
        assertFalse(Photo.isValidPhoto(".bmp")); // missing file name
        assertFalse(Photo.isValidPhoto("AshleySimpsons")); // missing file extension
        assertFalse(Photo.isValidPhoto("AshleySimpsonsjpg")); // missing "." for file extension

        // valid photo
        assertTrue(Photo.isValidPhoto("AshleySimpsons.jpg"));
        assertTrue(Photo.isValidPhoto("a.jpg")); // minimal
        assertTrue(Photo.isValidPhoto("..png")); // special character as file name
        assertTrue(Photo.isValidPhoto("ASHLEYSIMPSONS.png")); // upper case letters for file name
        assertTrue(Photo.isValidPhoto("AshleySimpsons.GIF")); // upper case letters for file extension
        assertTrue(Photo.isValidPhoto("AshleySimpsons.BmP")); // camel case for file extension
        assertTrue(Photo.isValidPhoto("Ash_leySimp_sons.jpg")); // underscores
        assertTrue(Photo.isValidPhoto("123.jpg")); // numeric file name
        assertTrue(Photo.isValidPhoto("jpg.jpg")); // file name is file extension name
        assertTrue(Photo.isValidPhoto("Ashley_Simpsons_Is_My_Best_Friend.png")); // long file name
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Photo} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withPhoto(String photo) {
        try {
            ParserUtil.parsePhoto(Optional.of(photo)).ifPresent(descriptor::setPhoto);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("photo is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Photo} of the {@code Person} that we are building.
     */
    public PersonBuilder withPhoto(String photo) {
        try {
            this.person.setPhoto(new Photo(photo));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("photo is expected to be unique.");
        }
        return this;
    }
```
