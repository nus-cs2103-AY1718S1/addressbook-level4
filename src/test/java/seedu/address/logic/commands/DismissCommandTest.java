package seedu.address.logic.commands;
//@@author Esilocke
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalAddressBook.getTypicalAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.task.ReadOnlyTask;

public class DismissCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());


    @Test
    public void execute_dismissOnePerson_success() throws Exception {
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON);
        ReadOnlyTask dismissedTask = model.getFilteredTaskList().get(5);
        ReadOnlyPerson dismissedPerson = model.getFilteredPersonList().get(0);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(dismissedPerson);

        DismissCommand dismissCommand = prepareCommand(toDismiss, Index.fromZeroBased(5));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.dismissFromTask(persons, dismissedTask);
        String expectedMessage = String.format(DismissCommand.MESSAGE_SUCCESS, toDismiss.size(),
                dismissedTask);
        assertCommandSuccess(dismissCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_dismissManyPersons_success() throws Exception {
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        ReadOnlyTask dismissedTask = model.getFilteredTaskList().get(5);
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(0);
        ReadOnlyPerson secondPerson = model.getFilteredPersonList().get(1);
        ReadOnlyPerson thirdPerson = model.getFilteredPersonList().get(2);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(firstPerson);
        persons.add(secondPerson);
        persons.add(thirdPerson);

        DismissCommand dismissCommand = prepareCommand(toDismiss, Index.fromZeroBased(5));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.dismissFromTask(persons, dismissedTask);
        String expectedMessage = String.format(DismissCommand.MESSAGE_SUCCESS, toDismiss.size(),
                dismissedTask);
        assertCommandSuccess(dismissCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_dismissDuplicates_success() throws Exception {
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);
        ReadOnlyTask dismissedTask = model.getFilteredTaskList().get(5);
        ReadOnlyPerson dismissedPerson = model.getFilteredPersonList().get(0);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(dismissedPerson);

        DismissCommand dismissCommand = prepareCommand(toDismiss, Index.fromZeroBased(5));
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String expectedMessage = String.format(DismissCommand.MESSAGE_SUCCESS, 1, dismissedTask);
        expectedModel.dismissFromTask(persons, dismissedTask);
        assertCommandSuccess(dismissCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredPersonList().size());
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON, outOfRangeIndex, INDEX_SECOND_PERSON);

        DismissCommand dismissCommand = prepareCommand(toDismiss, INDEX_FIRST_TASK);
        assertCommandFailure(dismissCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredTaskList().size());
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        DismissCommand dismissCommand = prepareCommand(toDismiss, outOfRangeIndex);
        assertCommandFailure(dismissCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }


    @Test
    public void execute_noneDismissed_throwsCommandException() throws Exception {
        List<Index> toDismiss = Arrays.asList(INDEX_FIRST_PERSON);
        ReadOnlyPerson dismissedPerson = model.getFilteredPersonList().get(0);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(dismissedPerson);

        DismissCommand dismissCommand = prepareCommand(toDismiss, INDEX_FIRST_TASK);
        assertCommandFailure(dismissCommand, model, DismissCommand.MESSAGE_NONE_ASSIGNED);
    }

    @Test
    public void equals() {
        ArrayList<Index> dismissFirstThree = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON));
        ArrayList<Index> dismissFirstTwo = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        DismissCommand dismissTwoToFirst = new DismissCommand(dismissFirstTwo, INDEX_FIRST_TASK);
        DismissCommand dismissThreeToFirst = new DismissCommand(dismissFirstThree, INDEX_FIRST_TASK);
        DismissCommand dismissTwoToSecond = new DismissCommand(dismissFirstTwo, INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(dismissTwoToFirst.equals(dismissTwoToFirst));

        // same values -> returns true
        DismissCommand dismissTwoToFirstCopy = new DismissCommand(dismissFirstTwo, INDEX_FIRST_TASK);
        assertTrue(dismissTwoToFirst.equals(dismissTwoToFirstCopy));

        // different types -> returns false
        assertFalse(dismissTwoToFirst.equals(1));

        // null -> returns false
        assertFalse(dismissTwoToFirst.equals(null));

        // different person/task indexes -> returns false
        assertFalse(dismissTwoToFirst.equals(dismissThreeToFirst));
        assertFalse(dismissTwoToFirst.equals(dismissTwoToSecond));
    }
    /**
     * Generates a new DismissCommand with the specified targets.
     */
    private DismissCommand prepareCommand(List<Index> personsToDismiss, Index taskIndex) {
        ArrayList<Index> listIndexes = new ArrayList<>(personsToDismiss);
        DismissCommand command = new DismissCommand(listIndexes, taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
