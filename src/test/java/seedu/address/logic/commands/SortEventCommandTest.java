package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalEvents.FIRST;
import static seedu.address.testutil.TypicalEvents.FOURTH;
import static seedu.address.testutil.TypicalEvents.SECOND;
import static seedu.address.testutil.TypicalEvents.THIRD;
import static seedu.address.testutil.TypicalEvents.getEmptyEventList;
import static seedu.address.testutil.TypicalEvents.getUnsortedEventList;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.testutil.TypicalPersons.getEmptyAddressBook;
import static seedu.address.testutil.TypicalPersons.getUnsortedAddressBook;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyPerson;

//@@author HouDenghao
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortEventCommand.
 */
public class SortEventCommandTest {

    private Model filledModel;
    private Model emptyModel;
    private Model eventModel;

    @Before
    public void setUp() {
        filledModel = new ModelManager(getUnsortedAddressBook(), getUnsortedEventList(), new UserPrefs());
        emptyModel = new ModelManager(getEmptyAddressBook(), getEmptyEventList(), new UserPrefs());
        eventModel = new ModelManager(getEmptyAddressBook(), getUnsortedEventList(), new UserPrefs());
    }

    @Test
    public void executeEmptyListShowEmptylist() {
        SortEventCommand command = prepareCommand(emptyModel);
        assertSuccess(command, SortEventCommand.MESSAGE_SUCCESS, Collections.emptyList(),
                Collections.emptyList(), emptyModel);
    }

    @Test
    public void executeAllFilledListShowSortedList() {
        SortEventCommand command = prepareCommand(filledModel);
        assertSuccess(command, SortEventCommand.MESSAGE_SUCCESS, Arrays.asList(CARL, ALICE, BENSON, DANIEL),
                Arrays.asList(FIRST, SECOND, THIRD, FOURTH), filledModel);
    }

    @Test
    public void executeOnlyOneFilledListShowOneSortedList() {
        SortEventCommand commandTwo = prepareCommand(eventModel);
        assertSuccess(commandTwo, SortEventCommand.MESSAGE_SUCCESS, Collections.emptyList(),
                Arrays.asList(FIRST, SECOND, THIRD, FOURTH), eventModel);
    }

    /**
     * Prepares for {@code SortEventCommand} .
     */
    private SortEventCommand prepareCommand(Model model) {
        SortEventCommand command = new SortEventCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code c} is successfully executed, and<br>
     *     - the command feedback is equal to {@code msg}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code p}<br>
     *     - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code e}<br>
     */
    private void assertSuccess(SortEventCommand c, String msg, List<ReadOnlyPerson> p, List<ReadOnlyEvent> e, Model m) {
        CommandResult commandResult = c.execute();
        assertEquals(msg, commandResult.feedbackToUser);
        assertEquals(p, m.getFilteredPersonList());
        assertEquals(e, m.getFilteredEventList());
    }

}
