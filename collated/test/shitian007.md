# shitian007
###### /java/seedu/room/logic/AutoCompleteTest.java
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
    public void getBaseCommands() {
        String[] actualBaseCommands = { "add", "edit", "select", "delete", "clear",
            "backup", "find", "list", "history", "exit", "help", "undo", "redo" };
        String[] baseCommands = autoComplete.getAutoCompleteList();
        assertTrue(Arrays.equals(actualBaseCommands, baseCommands));
    }

    @Test
    public void updateAutoCompleteList() {
        List<ReadOnlyPerson> persons = model.getFilteredPersonList();
        autoComplete.updateAutoCompleteList("find");
        String[] updatedList = autoComplete.getAutoCompleteList();
        for (int i = 0; i < persons.size(); i++) {
            String findPersonString = "find " + persons.get(i).getName().toString();
            assertTrue(findPersonString.equals(updatedList[i]));
        }
    }

    @Test
    public void resetAutoCompleteList() {
        String[] actualBaseCommands = { "add", "edit", "select", "delete", "clear",
            "backup", "find", "list", "history", "exit", "help", "undo", "redo" };
        autoComplete.updateAutoCompleteList("");
        String[] baseCommands = autoComplete.getAutoCompleteList();
        assertTrue(Arrays.equals(actualBaseCommands, baseCommands));
    }
}
```
###### /java/seedu/room/logic/parser/HighlightCommandParserTest.java
``` java
package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.room.logic.commands.HighlightCommand;

public class HighlightCommandParserTest {

    private HighlightCommandParser parser = new HighlightCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        String emptyArg = "";
        assertParseFailure(parser, emptyArg,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, HighlightCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/room/logic/commands/HighlightCommandTest.java
``` java
package seedu.room.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.room.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.room.testutil.TypicalPersons.getTypicalResidentBook;

import java.util.List;

import org.junit.Test;

import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.CommandHistory;
import seedu.room.logic.UndoRedoStack;
import seedu.room.model.Model;
import seedu.room.model.ModelManager;
import seedu.room.model.UserPrefs;
import seedu.room.model.tag.Tag;

/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code SwaproomCommand}.
 */
public class HighlightCommandTest {

    private Model model = new ModelManager(getTypicalResidentBook(), new UserPrefs());

    @Test
    /**
     * Successful operation of highlight tag
     */
    public void execute_validTag_success() throws Exception {
        List<Tag> listOfTags = model.getResidentBook().getTagList();
        String highlightTag = listOfTags.get(0).getTagName();
        HighlightCommand highlightCommand = prepareCommand(highlightTag);

        String expectedMessage = HighlightCommand.MESSAGE_SUCCESS + highlightTag;

        ModelManager expectedModel = new ModelManager(model.getResidentBook(), new UserPrefs());
        expectedModel.updateHighlightStatus(highlightTag);

        assertCommandSuccess(highlightCommand, model, expectedMessage, expectedModel);
    }

    @Test
    /**
     * Non-existent Tag should throw TagNotFoundException
     */
    public void execute_invalidTag_throwsCommandException() throws Exception {
        String nonExistentTag = getNonExistentTag();
        HighlightCommand highlightCommand = prepareCommand(nonExistentTag);

        assertCommandFailure(highlightCommand, model, HighlightCommand.MESSAGE_TAG_NOT_FOUND + nonExistentTag);
    }

    /**
     * Empty tag parameter should throw TagNotFoundException
     */
    @Test
    public void execute_noTag_throwsCommandException() {
        String emptyTag = "";
        HighlightCommand highlightCommand = prepareCommand(emptyTag);

        assertCommandFailure(highlightCommand, model, HighlightCommand.MESSAGE_TAG_NOT_FOUND + emptyTag);
    }

    /**
     * Get non-existent tag to test TagNotFoundException
     */
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
     * Returns a {@code SwaproomCommand} with the parameter {@code index}.
     */
    private HighlightCommand prepareCommand(String highlightTag) {
        HighlightCommand highlightCommand = new HighlightCommand(highlightTag);
        highlightCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return highlightCommand;
    }
}

```
