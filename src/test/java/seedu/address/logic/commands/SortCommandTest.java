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
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model filledModel;
    private Model emptyModel;
    private Model personModel;


    @Before
    public void setUp() {
        filledModel = new ModelManager(getUnsortedAddressBook(), getUnsortedEventList(), new UserPrefs());
        emptyModel = new ModelManager(getEmptyAddressBook(), getEmptyEventList(), new UserPrefs());
        personModel = new ModelManager(getUnsortedAddressBook(), getEmptyEventList(), new UserPrefs());
    }

    @Test
    public void executeEmptyListShowEmptylist() {
        SortCommand command = prepareCommand(emptyModel);
        assertSuccess(command, SortCommand.MESSAGE_SUCCESS, Collections.emptyList(),
                Collections.emptyList(), emptyModel);
    }

    @Test
    public void executeAllFilledListShowSortedList() {
        SortCommand command = prepareCommand(filledModel);
        assertSuccess(command, SortCommand.MESSAGE_SUCCESS, Arrays.asList(ALICE, BENSON, CARL, DANIEL),
                Arrays.asList(FOURTH, SECOND, FIRST, THIRD), filledModel);
    }

    @Test
    public void executeOnlyOneFilledListShowOneSortedList() {
        SortCommand commandOne = prepareCommand(personModel);
        assertSuccess(commandOne, SortCommand.MESSAGE_SUCCESS, Arrays.asList(ALICE, BENSON, CARL, DANIEL),
                Collections.emptyList(), personModel);
    }

    /**
     * Prepares for {@code SortCommand} .
     */
    private SortCommand prepareCommand(Model model) {
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code c} is successfully executed, and<br>
     *     - the command feedback is equal to {@code msg}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code p}<br>
     *     - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code e}<br>
     */
    private void assertSuccess(SortCommand c, String msg, List<ReadOnlyPerson> p, List<ReadOnlyEvent> e, Model m) {
        CommandResult commandResult = c.execute();
        assertEquals(msg, commandResult.feedbackToUser);
        assertEquals(p, m.getFilteredPersonList());
        assertEquals(e, m.getFilteredEventList());
    }

}
