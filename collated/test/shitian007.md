# shitian007
###### \java\seedu\room\logic\AutoCompleteTest.java
``` java
package seedu.room.logic;

import static org.junit.Assert.assertTrue;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;
import seedu.room.model.person.ReadOnlyPerson;

public class AutoCompleteTest {

    private AutoComplete autoComplete;
    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Before
    public void setUp() {
        autoComplete = new AutoComplete(model);
    }

    @Test
    public void assert_baseCommandsMatchUponCreation_success() {
        String[] actualBaseCommands = { "add", "addEvent", "addImage", "backup", "edit", "select", "delete",
            "deleteByTag", "deleteEvent", "deleteImage", "deleteTag", "clear", "find", "list", "highlight", "history",
            "import", "exit", "help", "undo", "redo", "sort", "swaproom"
        };
        String[] baseCommands = autoComplete.getAutoCompleteList();
        assertTrue(Arrays.equals(actualBaseCommands, baseCommands));
    }

    @Test
    public void assert_autoCompleteListUpdate_success() {
        List<ReadOnlyPerson> persons = model.getFilteredPersonList();
        autoComplete.updateAutoCompleteList("find");
        String[] updatedList = autoComplete.getAutoCompleteList();
        for (int i = 0; i < persons.size(); i++) {
            String findPersonString = "find " + persons.get(i).getName().toString();
            assertTrue(findPersonString.equals(updatedList[i]));
        }
    }

    @Test
    public void assert_resetAutoCompleteListMatchBaseCommands_success() {
        String[] actualBaseCommands = { "add", "addEvent", "addImage", "backup", "edit", "select", "delete",
            "deleteByTag", "deleteEvent", "deleteImage", "deleteTag", "clear", "find", "list", "highlight", "history",
            "import", "exit", "help", "undo", "redo", "sort", "swaproom"
        };
        autoComplete.updateAutoCompleteList("");
        String[] baseCommands = autoComplete.getAutoCompleteList();
        assertTrue(Arrays.equals(actualBaseCommands, baseCommands));
    }
}
```
###### \java\seedu\room\logic\commands\AddCommandTest.java
``` java
        @Override
        public void updateFilteredPersonListPicture(Predicate<ReadOnlyPerson> predicate, Person editedPerson) {
            fail("This method should not be called.");
        }
```
###### \java\seedu\room\logic\commands\AddCommandTest.java
``` java
        @Override
        public void updateHighlightStatus(String highlightTag) throws TagNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void resetHighlightStatus() throws NoneHighlightedException {
            fail("This method should not be called.");
        }
```
###### \java\seedu\room\logic\commands\AddImageCommandTest.java
``` java
package seedu.room.logic.commands;

import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import org.junit.Test;

import seedu.room.commons.core.Messages;
import seedu.room.commons.core.index.Index;
import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.ResidentBook;
import seedu.room.model.UserPrefs;
import seedu.room.model.person.Person;
import seedu.room.model.person.Picture;

public class AddImageCommandTest {

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void execute_imageUrlValid_success() throws Exception {
        final String validUrl = Picture.PLACEHOLDER_IMAGE;
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        editedPerson.getPicture().setPictureUrl(validUrl);
        AddImageCommand addImageCommand = prepareCommand(INDEX_FIRST_PERSON, validUrl);

        String expectedMessage = String.format(AddImageCommand.MESSAGE_ADD_IMAGE_SUCCESS,
                editedPerson.getName().toString());

        Model expectedModel = new ModelManager(new ResidentBook(model.getResidentBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.updateFilteredPersonListPicture(Model.PREDICATE_SHOW_ALL_PERSONS, editedPerson);

        assertCommandSuccess(addImageCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() throws Exception {
        final String validUrl = "Invalid Image Url";
        final Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        AddImageCommand addImageCommand = prepareCommand(invalidIndex, validUrl);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                editedPerson.getName().toString());

        assertCommandFailure(addImageCommand, model, expectedMessage);
    }

    @Test
    public void execute_invalidImageUrlValid_failure() throws Exception {
        final String validUrl = "Invalid Image Url";
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        AddImageCommand addImageCommand = prepareCommand(INDEX_FIRST_PERSON, validUrl);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_IMAGE_URL,
                editedPerson.getName().toString());

        assertCommandFailure(addImageCommand, model, expectedMessage);
    }

    /**
     * Returns an {@code AddImageCommand} with parameters {@code index} and {@code imageURL}
     */
    private AddImageCommand prepareCommand(Index index, String imageUrl) {
        AddImageCommand addImageCommand = new AddImageCommand(index, imageUrl);
        addImageCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return addImageCommand;
    }
}
```
###### \java\seedu\room\logic\commands\DeleteImageCommandTest.java
``` java
package seedu.room.logic.commands;

import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import org.junit.Test;

import seedu.room.commons.core.Messages;
import seedu.room.commons.core.index.Index;
import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.ResidentBook;
import seedu.room.model.UserPrefs;
import seedu.room.model.person.Person;
import seedu.room.model.person.Picture;

public class DeleteImageCommandTest {

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void execute_validPersonIndex_success() throws Exception {
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        editedPerson.getPicture().setPictureUrl(Picture.PLACEHOLDER_IMAGE);
        DeleteImageCommand deleteImageCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteImageCommand.MESSAGE_RESET_IMAGE_SUCCESS,
                editedPerson.getName().toString());

        Model expectedModel = new ModelManager(new ResidentBook(model.getResidentBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);
        expectedModel.updateFilteredPersonListPicture(Model.PREDICATE_SHOW_ALL_PERSONS, editedPerson);

        assertCommandSuccess(deleteImageCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_failure() throws Exception {
        Person editedPerson = (Person) model.getFilteredPersonList().get(0);
        final Index invalidIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        DeleteImageCommand deleteImageCommand = prepareCommand(invalidIndex);

        String expectedMessage = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                editedPerson.getName().toString());

        assertCommandFailure(deleteImageCommand, model, expectedMessage);
    }

    private DeleteImageCommand prepareCommand(Index index) {
        DeleteImageCommand deleteImageCommand = new DeleteImageCommand(index);
        deleteImageCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteImageCommand;
    }
}
```
###### \java\seedu\room\logic\commands\HighlightCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SwaproomCommand}.
 */
public class HighlightCommandTest {

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    public void execute_validTag_success() throws Exception {
        List<Tag> listOfTags = model.getResidentBook().getTagList();
        String highlightTag = listOfTags.get(0).getTagName();
        HighlightCommand highlightCommand = prepareCommand(highlightTag);

        String expectedMessage = HighlightCommand.MESSAGE_PERSONS_HIGHLIGHTED_SUCCESS + highlightTag;

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.updateHighlightStatus(highlightTag);

        assertCommandSuccess(highlightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidTag_throwsCommandException() throws Exception {
        String nonExistentTag = getNonExistentTag();
        HighlightCommand highlightCommand = prepareCommand(nonExistentTag);

        assertCommandFailure(highlightCommand, model, HighlightCommand.MESSAGE_TAG_NOT_FOUND + nonExistentTag);
    }

    @Test
    public void execute_noTag_throwsCommandException() {
        String emptyTag = "";
        HighlightCommand highlightCommand = prepareCommand(emptyTag);

        assertCommandFailure(highlightCommand, model, HighlightCommand.MESSAGE_TAG_NOT_FOUND + emptyTag);
    }

    @Test
    public void execute_removeHighlight_success() {
        String removeHighlight = "-";
        HighlightCommand highlightCommand = prepareCommand(removeHighlight);

        String expectedMessage = HighlightCommand.MESSAGE_RESET_HIGHLIGHT;

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        List<Tag> listOfTags = model.getResidentBook().getTagList();
        String highlightTag = listOfTags.get(0).getTagName();
        model.updateHighlightStatus(highlightTag);

        assertCommandSuccess(highlightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_removeHighlightNoneHighlighted_throwsNoneHighlightedException() {
        String removeHighlight = "-";
        HighlightCommand highlightCommand = prepareCommand(removeHighlight);

        String expectedMessage = HighlightCommand.MESSAGE_NONE_HIGHLIGHTED;

        assertCommandFailure(highlightCommand, model, expectedMessage);
    }

    public String getNonExistentTag() {
        String nonExistentTag = "No such tag exists";
        try {
            List<Tag> listOfTags = model.getResidentBook().getTagList();
            while (listOfTags.contains(new Tag(nonExistentTag))) {
                System.out.println("while");
                nonExistentTag += nonExistentTag;
            }
            return nonExistentTag;
        } catch (IllegalValueException e) {
            System.out.println("");
        }
        return nonExistentTag;
    }

    @Test
    public void equals() {
        String testTag = "test";
        String otherTestTag = "other test";
        HighlightCommand highlightCommandFirst = new HighlightCommand(testTag);
        HighlightCommand highlightCommandSecond = new HighlightCommand(otherTestTag);

        // same object -> returns true
        assertTrue(highlightCommandFirst.equals(highlightCommandFirst));

        // different argument -> returns false
        assertFalse(highlightCommandFirst.equals(highlightCommandSecond));

        // different values -> returns false
        assertFalse(highlightCommandFirst.equals(1));

        // null -> returns false
        assertFalse(highlightCommandFirst.equals(null));
    }

    /**
     * Returns a {@code HighlightCommand} with the parameter {@code highlightTag}.
     */
    private HighlightCommand prepareCommand(String highlightTag) {
        HighlightCommand highlightCommand = new HighlightCommand(highlightTag);
        highlightCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return highlightCommand;
    }
}

```
###### \java\seedu\room\logic\parser\AddImageCommandParserTest.java
``` java
public class AddImageCommandParserTest {

    private AddImageCommandParser parser = new AddImageCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        String validInput = " " + INDEX_FIRST_PERSON.getOneBased() + " " + Picture.PLACEHOLDER_IMAGE;
        AddImageCommand expectedCommand = new AddImageCommand(INDEX_FIRST_PERSON, Picture.PLACEHOLDER_IMAGE);

        assertParseSuccess(parser, validInput, expectedCommand);
    }

    @Test
    public void parse_indexNonInteger_failure() {
        String invalidIndexArgs = "one " + Picture.PLACEHOLDER_IMAGE;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddImageCommand.MESSAGE_USAGE);
        assertParseFailure(parser, invalidIndexArgs, expectedMessage);
    }

    @Test
    public void parse_invalidArgNumber_failure() {
        String invalidArgs = "1";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddImageCommand.MESSAGE_USAGE);
        assertParseFailure(parser, invalidArgs, expectedMessage);
    }
}
```
###### \java\seedu\room\logic\parser\DeleteImageCommandParserTest.java
``` java
public class DeleteImageCommandParserTest {

    private DeleteImageCommandParser parser = new DeleteImageCommandParser();

    @Test
    public void parse_validIndex_success() {
        String validInput = " " + INDEX_FIRST_PERSON.getOneBased();
        DeleteImageCommand expectedCommand = new DeleteImageCommand(INDEX_FIRST_PERSON);

        assertParseSuccess(parser, validInput, expectedCommand);
    }

    @Test
    public void parse_indexNonInteger_failure() {
        String invalidIndexArgs = "one ";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteImageCommand.MESSAGE_USAGE);

        assertParseFailure(parser, invalidIndexArgs, expectedMessage);
    }

}

```
###### \java\seedu\room\logic\parser\HighlightCommandParserTest.java
``` java
public class HighlightCommandParserTest {

    private HighlightCommandParser parser = new HighlightCommandParser();

    @Test
    public void parse_validTag_success() {
        assertParseSuccess(parser, " RA", new HighlightCommand("RA"));
    }

    @Test
    public void parse_validUnhighlight_success() {
        assertParseSuccess(parser, " -", new HighlightCommand("-"));
    }

    @Test
    public void parse_invalidArgs_failure() {
        String emptyArg = "";
        assertParseFailure(parser, emptyArg,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HighlightCommand.MESSAGE_USAGE));
    }

}
```
###### \java\seedu\room\model\ModelManagerTest.java
``` java
    @Test
    public void updatePersonPictureTest() throws IllegalValueException, PersonNotFoundException {
        ResidentBook residentBook = new ResidentBookBuilder().withPerson(TEMPORARY_JOE).build();
        UserPrefs userPrefs = new UserPrefs();
        ModelManager modelManager = new ModelManager(residentBook, userPrefs);

        //modelManager has nobody in it -> returns false
        assertFalse(modelManager.equals(null));

        Person editedPerson = (Person) modelManager.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        editedPerson.getPicture().setPictureUrl("TestUrl");

        modelManager.updateFilteredPersonListPicture(PREDICATE_SHOW_ALL_PERSONS, editedPerson);
        assertTrue(modelManager.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()).equals(editedPerson));

    }
```
###### \java\seedu\room\model\person\PictureTest.java
``` java
public class PictureTest {

    @Test
    public void isValidUrl() {
        // Invalid picture urls
        assertFalse(Picture.isValidImageUrl("")); // empty string
        assertFalse(Picture.isValidImageUrl(" ")); // spaces only
        assertFalse(Picture.isValidImageUrl("folder//folder/image.jpg")); // Double slash invalid file url

        // Valid picture numbers
        assertTrue(Picture.isValidImageUrl("folder1/folder2/image.jpg"));
        assertTrue(Picture.isValidImageUrl("folder1/folder2/image.png"));

        // Default picture url
        Picture defaultPicture = new Picture();
        assertTrue(defaultPicture.getPictureUrl().equals(Picture.PLACEHOLDER_IMAGE));
    }
}
```
