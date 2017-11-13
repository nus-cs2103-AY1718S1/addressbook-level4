//@@author yuheng222

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
