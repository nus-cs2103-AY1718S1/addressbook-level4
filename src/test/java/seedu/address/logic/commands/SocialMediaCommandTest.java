package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeBrowserPanelUrlEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author kenpaxtonlim
public class SocialMediaCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_validIndexValidType_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_FACEBOOK);
        assertExecutionSuccess(INDEX_THIRD_PERSON, SocialMediaCommand.TYPE_TWITTER);
        assertExecutionSuccess(lastPersonIndex, SocialMediaCommand.TYPE_INSTAGRAM);
    }

    @Test
    public void execute_invalidIndexValidType_success() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, SocialMediaCommand.TYPE_FACEBOOK,
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexInvalidType_success() {
        assertExecutionFailure(INDEX_FIRST_PERSON, "abc", SocialMediaCommand.MESSAGE_INVALID_TYPE);
    }

    @Test
    public void execute_invalidIndexinvalidType_success() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, "abc",
                Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        SocialMediaCommand command = new SocialMediaCommand(INDEX_FIRST_PERSON, SocialMediaCommand.TYPE_FACEBOOK);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        SocialMediaCommand firstCommandCopy = new SocialMediaCommand(INDEX_FIRST_PERSON,
                SocialMediaCommand.TYPE_FACEBOOK);
        assertTrue(command.equals(firstCommandCopy));

        // different types -> returns false
        assertFalse(command.equals(1));

        // null -> returns false
        assertFalse(command.equals(null));

        // different person, same social media type -> returns false
        SocialMediaCommand differentPersonCommand = new SocialMediaCommand(INDEX_SECOND_PERSON,
                SocialMediaCommand.TYPE_FACEBOOK);
        assertFalse(command.equals(differentPersonCommand));

        // same person, different social media typ -> return false
        SocialMediaCommand differentTypeCommand = new SocialMediaCommand(INDEX_FIRST_PERSON,
                SocialMediaCommand.TYPE_TWITTER);
        assertFalse(command.equals(differentTypeCommand));
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index, String type) {
        SocialMediaCommand command = prepareCommand(index, type);

        try {
            CommandResult commandResult = command.execute();
            assertEquals(SocialMediaCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        String url = "";
        switch (type) {
        case SocialMediaCommand.TYPE_FACEBOOK:
            url = SocialMediaCommand.URL_FACEBOOK
                    + model.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().facebook;
            break;
        case SocialMediaCommand.TYPE_TWITTER:
            url = SocialMediaCommand.URL_TWITTER
                    + model.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().twitter;
            break;
        case SocialMediaCommand.TYPE_INSTAGRAM:
            url = SocialMediaCommand.URL_INSTAGRAM
                    + model.getFilteredPersonList().get(index.getZeroBased()).getSocialMedia().instagram;
            break;
        default:
            throw new AssertionError();
        }

        ChangeBrowserPanelUrlEvent lastEvent =
                (ChangeBrowserPanelUrlEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(url, lastEvent.url);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String type, String expectedMessage) {
        SocialMediaCommand command = prepareCommand(index, type);

        try {
            command.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code SocialMediaCommand} with parameters {@code index} and {@code type}.
     */
    private SocialMediaCommand prepareCommand(Index index, String type) {
        SocialMediaCommand command = new SocialMediaCommand(index, type);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
