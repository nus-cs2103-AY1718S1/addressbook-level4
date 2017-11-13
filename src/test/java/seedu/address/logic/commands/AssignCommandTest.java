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

public class AssignCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_assignOnePerson_success() throws Exception {
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON);
        ReadOnlyTask assignedTask = model.getFilteredTaskList().get(0);
        ReadOnlyPerson assignedPerson = model.getFilteredPersonList().get(0);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(assignedPerson);

        AssignCommand assignCommand = prepareCommand(toAssign, INDEX_FIRST_TASK);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.assignToTask(persons, assignedTask);
        String expectedMessage = String.format(AssignCommand.MESSAGE_SUCCESS, toAssign.size(),
                assignedTask);
        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_assignManyPersons_success() throws Exception {
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        ReadOnlyTask assignedTask = model.getFilteredTaskList().get(0);
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(0);
        ReadOnlyPerson secondPerson = model.getFilteredPersonList().get(1);
        ReadOnlyPerson thirdPerson = model.getFilteredPersonList().get(2);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(firstPerson);
        persons.add(secondPerson);
        persons.add(thirdPerson);

        AssignCommand assignCommand = prepareCommand(toAssign, INDEX_FIRST_TASK);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.assignToTask(persons, assignedTask);
        String expectedMessage = String.format(AssignCommand.MESSAGE_SUCCESS, toAssign.size(),
                assignedTask);
        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_assignDuplicates_success() throws Exception {
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON, INDEX_FIRST_PERSON, INDEX_FIRST_PERSON);
        ReadOnlyTask assignedTask = model.getFilteredTaskList().get(0);
        ReadOnlyPerson assignedPerson = model.getFilteredPersonList().get(0);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(assignedPerson);

        AssignCommand assignCommand = prepareCommand(toAssign, INDEX_FIRST_TASK);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        String expectedMessage = String.format(AssignCommand.MESSAGE_SUCCESS, 1, assignedTask);
        expectedModel.assignToTask(persons, assignedTask);
        assertCommandSuccess(assignCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndex_throwsCommandException() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredPersonList().size());
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON, outOfRangeIndex, INDEX_SECOND_PERSON);

        AssignCommand assignCommand = prepareCommand(toAssign, INDEX_FIRST_TASK);
        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_invalidTaskIndex_throwsCommandException() throws Exception {
        Index outOfRangeIndex = Index.fromZeroBased(model.getFilteredTaskList().size());
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON);

        AssignCommand assignCommand = prepareCommand(toAssign, outOfRangeIndex);
        assertCommandFailure(assignCommand, model, Messages.MESSAGE_INVALID_TASK_DISPLAYED_INDEX);
    }

    @Test
    public void execute_allPersonsAlreadyAssigned_throwsCommandException() throws Exception {
        List<Index> toAssign = Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        ReadOnlyTask assignedTask = model.getFilteredTaskList().get(0);
        ReadOnlyPerson firstPerson = model.getFilteredPersonList().get(0);
        ReadOnlyPerson secondPerson = model.getFilteredPersonList().get(1);
        ReadOnlyPerson thirdPerson = model.getFilteredPersonList().get(2);
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(firstPerson);
        persons.add(secondPerson);
        persons.add(thirdPerson);

        AssignCommand assignCommand = prepareCommand(toAssign, INDEX_FIRST_TASK);
        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.assignToTask(persons, assignedTask);
        assignCommand.setData(expectedModel, new CommandHistory(), new UndoRedoStack());
        assertCommandFailure(assignCommand, expectedModel, AssignCommand.MESSAGE_NONE_ASSIGNED);
    }

    @Test
    public void equals() {
        ArrayList<Index> assignFirstThree = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, INDEX_THIRD_PERSON));
        ArrayList<Index> assignFirstTwo = new ArrayList<>(Arrays.asList(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
        AssignCommand assignTwoToFirst = new AssignCommand(assignFirstTwo, INDEX_FIRST_TASK);
        AssignCommand assignThreeToFirst = new AssignCommand(assignFirstThree, INDEX_FIRST_TASK);
        AssignCommand assignTwoToSecond = new AssignCommand(assignFirstTwo, INDEX_SECOND_TASK);

        // same object -> returns true
        assertTrue(assignTwoToFirst.equals(assignTwoToFirst));

        // same values -> returns true
        AssignCommand assignTwoToFirstCopy = new AssignCommand(assignFirstTwo, INDEX_FIRST_TASK);
        assertTrue(assignTwoToFirst.equals(assignTwoToFirstCopy));

        // different types -> returns false
        assertFalse(assignTwoToFirst.equals(1));

        // null -> returns false
        assertFalse(assignTwoToFirst.equals(null));

        // different person/task indexes -> returns false
        assertFalse(assignTwoToFirst.equals(assignThreeToFirst));
        assertFalse(assignTwoToFirst.equals(assignTwoToSecond));
    }
    /**
     * Generates a new AssignCommand with the specified targets.
     */
    private AssignCommand prepareCommand(List<Index> personsToAssign, Index taskIndex) {
        ArrayList<Index> listIndexes = new ArrayList<>(personsToAssign);
        AssignCommand command = new AssignCommand(listIndexes, taskIndex);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
