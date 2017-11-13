package seedu.address.logic.commands;
//@@author jeffreygohkw
import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_TASK;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelNavigateEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Location;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code NavigateCommand}.
 */
public class NavigateCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_fromAddress_success() throws IllegalValueException {
        Location from = new Location("NUS");
        Location to = new Location("Sentosa");
        NavigateCommand navi = prepareCommand(from, to, null, null, false, false);
        assertExecutionSuccess(navi);
    }

    @Test
    public void execute_fromPersons_success() throws IllegalValueException {
        NavigateCommand navi = prepareCommand(null, null,
                INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, false, false);

        assertExecutionSuccess(navi);
    }

    @Test
    public void execute_fromTasks_success() throws IllegalValueException {
        NavigateCommand navi = prepareCommand(null, null,
                INDEX_THIRD_TASK, INDEX_FIRST_TASK, true, true);

        assertExecutionSuccess(navi);
    }


    @Test
    public void execute_invalidArgs_failure() throws IllegalValueException {
        Location from = new Location("NUS");
        Location to = new Location("Sentosa");

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(NavigateCommand.MESSAGE_MULTIPLE_FROM_ERROR);
        NavigateCommand navi = prepareCommand(from, to,
                INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, false, false);

        thrown.expect(IllegalArgumentException.class);
        thrown.expectMessage(NavigateCommand.MESSAGE_MULTIPLE_TO_ERROR);
        NavigateCommand naviOne = prepareCommand(from, to,
                null, INDEX_SECOND_PERSON, false, false);
    }

    /**
     * Executes the input (@code NavigateCommand) and checks that (@code BrowserPanelNavigateEvent) is raised with
     * the correct locations
     */
    private void assertExecutionSuccess(NavigateCommand navi) throws IllegalValueException {
        Location from;
        Location to;
        try {

            if (navi.getLocationFrom() != null) {
                from = navi.getLocationFrom();
            } else if (navi.isFromIsTask()) {
                from = new Location(model.getFilteredTaskList().get(navi.getFromIndex().getZeroBased())
                        .getTaskAddress().toString());
            } else {
                from = new Location(model.getFilteredPersonList().get(navi.getFromIndex().getZeroBased())
                        .getAddress().toString());
            }

            if (navi.getLocationTo() != null) {
                to = navi.getLocationTo();
            } else if (navi.isToIsTask()) {
                to = new Location(model.getFilteredTaskList().get(navi.getToIndex().getZeroBased())
                        .getTaskAddress().toString());
            } else {
                to = new Location(model.getFilteredPersonList().get(navi.getToIndex().getZeroBased())
                        .getAddress().toString());
            }
            CommandResult commandResult = navi.execute();
            assertEquals(String.format(NavigateCommand.MESSAGE_NAVIGATE_SUCCESS,
                    from, to),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        BrowserPanelNavigateEvent lastEvent =
                (BrowserPanelNavigateEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(from, lastEvent.getFromLocation());
        assertEquals(to, lastEvent.getToLocation());
    }
    /**
     * Returns a {@code NavigateCommand} based on input parameters.
     */
    private NavigateCommand prepareCommand(Location locationFrom, Location locationTo, Index fromIndex, Index toIndex,
                                           boolean fromIsTask, boolean toIsTask) throws IllegalValueException {
        NavigateCommand navi = new NavigateCommand(locationFrom, locationTo, fromIndex, toIndex, fromIsTask, toIsTask);
        navi.setData(model, new CommandHistory(), new UndoRedoStack());
        return navi;
    }
}
