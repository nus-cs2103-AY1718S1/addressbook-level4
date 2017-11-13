# yuheng222
###### /java/seedu/address/logic/commands/AddCommandTest.java
``` java
        @Override
        public void sort() {
            fail("This method should not be called.");
        }

        @Override
        public ArrayList<String> getThemesList() {
            fail("This method should not be called");
            return null;
        }
```
###### /java/seedu/address/logic/commands/SelectThemeCommandTest.java
``` java

package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_THEME;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_THEME;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_THEME;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalThemes.NAME_FIRST_THEME;
import static seedu.address.testutil.TypicalThemes.NAME_SECOND_THEME;
import static seedu.address.testutil.TypicalThemes.NAME_THIRD_THEME;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.SelectThemeRequestEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SelectThemeCommand}.
 */
public class SelectThemeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexThemesList_success() {
        Index lastThemeIndex = Index.fromOneBased(model.getThemesList().size());
        String lastThemeName = (model.getThemesList().get(model.getThemesList().size() - 1));

        assertExecutionSuccess(INDEX_FIRST_THEME, NAME_FIRST_THEME);
        assertExecutionSuccess(INDEX_SECOND_THEME, NAME_SECOND_THEME);
        assertExecutionSuccess(INDEX_THIRD_THEME, NAME_THIRD_THEME);
        assertExecutionSuccess(lastThemeIndex, lastThemeName);
    }

    @Test
    public void execute_invalidIndexThemesList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getThemesList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_THEME_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SelectThemeCommand selectFirstThemeCommand = new SelectThemeCommand(INDEX_FIRST_THEME);
        SelectThemeCommand selectSecondThemeCommand = new SelectThemeCommand(INDEX_SECOND_THEME);

        // same object -> returns true
        assertTrue(selectFirstThemeCommand.equals(selectFirstThemeCommand));

        // same values -> returns true
        SelectThemeCommand selectFirstThemeCommandCopy = new SelectThemeCommand(INDEX_FIRST_THEME);
        assertTrue(selectFirstThemeCommand.equals(selectFirstThemeCommandCopy));

        // different types -> returns false
        assertFalse(selectFirstThemeCommand.equals(1));

        // null -> returns false
        assertFalse(selectFirstThemeCommand.equals(null));

        // different theme -> returns false
        assertFalse(selectFirstThemeCommand.equals(selectSecondThemeCommand));
    }

    /**
     * Executes a {@code SelectThemeCommand} with the given {@code index}, and checks that
     * {@code SelectThemeRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, String themeName) {
        SelectThemeCommand selectThemeCommand = prepareCommand(index);

        try {
            CommandResult commandResult = selectThemeCommand.execute();
            assertEquals(String.format(SelectThemeCommand.MESSAGE_SWITCH_THEME_SUCCESS,
                    model.getThemesList().get(index.getOneBased() - 1)),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        SelectThemeRequestEvent lastEvent = (SelectThemeRequestEvent)
                eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(themeName, lastEvent.theme);
    }

    /**
     * Executes a {@code SelectThemeCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SelectThemeCommand selectThemeCommand = prepareCommand(index);

        try {
            selectThemeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SelectThemeCommand} with parameters {@code index}.
     */
    private SelectThemeCommand prepareCommand(Index index) {
        SelectThemeCommand selectThemeCommand = new SelectThemeCommand(index);
        selectThemeCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return selectThemeCommand;
    }
}
```
###### /java/seedu/address/logic/commands/SortCommandTest.java
``` java

package seedu.address.logic.commands;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
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
    public void execute_sortEmptyAddressBook_success() {
        Model model = new ModelManager();
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = command.executeUndoableCommand();
        assertEquals(result.feedbackToUser, SortCommand.MESSAGE_SUCCESS);
    }

    @Test
    public void execute_sortNonEmptyAddressBook_success() {
        Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        CommandResult result = command.executeUndoableCommand();
        assertEquals(result.feedbackToUser, SortCommand.MESSAGE_SUCCESS);
    }
}
```
###### /java/seedu/address/logic/LogicManagerTest.java
``` java
    @Test
    public void execute_selectThemeCommandExecutionError_throwsCommandException() {
        String selectThemeCommand = "theme 9";
        assertCommandException(selectThemeCommand, MESSAGE_INVALID_THEME_DISPLAYED_INDEX);
        assertHistoryCorrect(selectThemeCommand);
    }
```
###### /java/seedu/address/logic/parser/SelectThemeCommandParserTest.java
``` java

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_THEME;

import org.junit.Test;

import seedu.address.logic.commands.SelectThemeCommand;

/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class SelectThemeCommandParserTest {

    private SelectThemeCommandParser parser = new SelectThemeCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new SelectThemeCommand(INDEX_FIRST_THEME));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectThemeCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/testutil/EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Avatar} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withAvatar(String avatar) {
        try {
            descriptor.setAvatar(ParserUtil.parseAvatar(avatar));
        } catch (IllegalValueException | IOException ive) {
            throw new IllegalArgumentException("avatar is expected to be unique.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/PersonBuilder.java
``` java
    /**
     * Sets the {@code Avatar} of the {@code Person} that we are building.
     */
    public PersonBuilder withAvatar(String path) {
        try {
            this.person.setAvatar(new Avatar(path));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("avatar is expected to be unique.");
        } catch (IOException e) {
            throw new IllegalArgumentException("filepath is invalid.");
        }
        return this;
    }
```
###### /java/seedu/address/testutil/TypicalThemes.java
``` java

package seedu.address.testutil;

/**
 * A utility class containing a list of {@code Themes} to be used in tests.
 */
public class TypicalThemes {
    public static final String NAME_FIRST_THEME = "MidnightTheme.css";
    public static final String NAME_SECOND_THEME = "SummerTheme.css";
    public static final String NAME_THIRD_THEME = "CoffeeTheme.css";
}
```
###### /java/systemtests/SelectThemeCommandSystemTest.java
``` java

package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_THEME_DISPLAYED_INDEX;
import static seedu.address.logic.commands.SelectThemeCommand.MESSAGE_SWITCH_THEME_SUCCESS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_THEME;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectThemeCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;

public class SelectThemeCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void selectTheme() {
        /* Case: select the first theme in the themes list, command with leading spaces and trailing spaces
         * -> selected
         */
        String command = "   " + SelectThemeCommand.COMMAND_WORD + " " + INDEX_FIRST_THEME.getOneBased() + "   ";
        Model expectedModel = getModel();
        assertCommandSuccess(command, expectedModel);


        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last theme in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getThemesList().size() + 1;
        assertCommandFailure(SelectThemeCommand.COMMAND_WORD + " " + invalidIndex,
                MESSAGE_INVALID_THEME_DISPLAYED_INDEX);

        /* Case: themes list, select index within bounds of address book and themes list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assert validIndex.getZeroBased() < getModel().getThemesList().size();
        command = SelectThemeCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, getModel());

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectThemeCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectThemeCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectThemeCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectThemeCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectThemeCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectThemeCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectThemeCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectThemeCommand.MESSAGE_USAGE));
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code Messages#MESSAGE_SWITCH_THEME_SUCCESS} with the number of themes in the theme list,
     * and the model related components equal to {@code expectedModel}.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remains unchanged, and the command box has the default style class.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, Model expectedModel) {
        String expectedResultMessage = String.format(
                MESSAGE_SWITCH_THEME_SUCCESS, expectedModel.getThemesList().get(INDEX_FIRST_THEME.getOneBased() - 1));

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### /java/systemtests/SortCommandSystemTest.java
``` java

package systemtests;

import org.junit.Test;

import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.model.Model;


public class SortCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void sort() {
        Model model = getModel();

        /* Case: sort -> list will be sorted alphabetically by name */
        Model modelBeforeSort = getModel();
        String command = SortCommand.COMMAND_WORD;
        String expectedResultMessage = String.format(SortCommand.MESSAGE_SUCCESS);
        model.sort();
        assertCommandSuccess(command, expectedResultMessage, model);


        /* Case: undo the sorting of list -> list will revert back to the state before sort */
        Model modelBeforeUndo = getModel();
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, modelBeforeSort);

        /* Case: redo the sorting of list -> list will be sorted alphabetically by name */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, expectedResultMessage, modelBeforeUndo);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model expectedModel) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }
}
```
