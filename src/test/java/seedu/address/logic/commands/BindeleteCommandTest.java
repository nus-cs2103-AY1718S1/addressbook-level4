package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstBinPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;
import static seedu.address.testutil.TypicalRecycleBin.getTypicalRecyclbin;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;

//@@author Pengyuz
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code BindeleteCommand}.
 */
public class BindeleteCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalRecyclbin(), new UserPrefs());
    private ArrayList<Index> personsToDelete1 = new ArrayList<>();
    private ArrayList<ReadOnlyPerson> persontodelete = new ArrayList<>();


    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        persontodelete.add(personToDelete);

        personsToDelete1.add(INDEX_FIRST_PERSON);

        BindeleteCommand bindeleteCommand1 = prepareCommand(personsToDelete1);

        String expectedMessage1 = bindeleteCommand1.MESSAGE_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());

        expectedModel1.deleteBinPerson(persontodelete);

        assertCommandSuccess(bindeleteCommand1, model, expectedMessage1, expectedModel1);
    }

    @Test
    public void execute_validIndexUnfilteredList_success2() throws Exception {
        ReadOnlyPerson personToDelete = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson secondToDelete = model.getRecycleBinPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        persontodelete.clear();
        persontodelete.add(personToDelete);
        persontodelete.add(secondToDelete);

        personsToDelete1.clear();
        personsToDelete1.add(INDEX_FIRST_PERSON);
        personsToDelete1.add(INDEX_SECOND_PERSON);

        BindeleteCommand bindeleteCommand = prepareCommand(personsToDelete1);

        String expectedMessage1 = BindeleteCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());

        expectedModel1.deleteBinPerson(persontodelete);

        assertCommandSuccess(bindeleteCommand, model, expectedMessage1, expectedModel1);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getRecycleBinPersonList().size() + 1);
        personsToDelete1.clear();
        personsToDelete1.add(outOfBoundIndex);
        BindeleteCommand bindeleteCommand = prepareCommand(personsToDelete1);

        assertCommandFailure(bindeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstBinPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personsToDelete1.clear();
        personsToDelete1.add(INDEX_FIRST_PERSON);
        persontodelete.clear();
        persontodelete.add(personToDelete);
        BindeleteCommand bindeleteCommand = prepareCommand(personsToDelete1);

        String expectedMessage = String.format(BindeleteCommand.MESSAGE_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.deleteBinPerson(persontodelete);
        showNoBinPerson(expectedModel);

        assertCommandSuccess(bindeleteCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstBinPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        personsToDelete1.clear();
        personsToDelete1.add(INDEX_SECOND_PERSON);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecycleBin().getPersonList().size());

        BindeleteCommand bindeleteCommand = prepareCommand(personsToDelete1);

        assertCommandFailure(bindeleteCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }



    @Test
    public void equals() {
        ArrayList<Index> first = new ArrayList<>();
        ArrayList<Index> second = new ArrayList<>();
        first.add(INDEX_FIRST_PERSON);
        second.add(INDEX_SECOND_PERSON);
        BindeleteCommand bindeleteFirstCommand = new BindeleteCommand(first);
        BindeleteCommand bindeleteSecondCommand = new BindeleteCommand(second);

        // same object -> returns true
        assertTrue(bindeleteFirstCommand.equals(bindeleteFirstCommand));

        // same values -> returns true
        BindeleteCommand deleteFirstCommandCopy = new BindeleteCommand(first);
        assertTrue(bindeleteFirstCommand.equals(deleteFirstCommandCopy));

        // different types -> returns false
        assertFalse(bindeleteFirstCommand.equals(1));

        // null -> returns false
        assertFalse(bindeleteFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(bindeleteFirstCommand.equals(bindeleteSecondCommand));
    }

    /**
     * Returns a {@code BindeleteCommand} with the parameter {@code index}.
     */
    private BindeleteCommand prepareCommand(ArrayList<Index> indexes) {

        BindeleteCommand bindeleteCommand = new BindeleteCommand(indexes);
        bindeleteCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return bindeleteCommand;
    }


    /**
     * Updates {@code model}'s recycle bin filtered list to show no one.
     */
    private void showNoPerson(Model model) {
        model.updateFilteredPersonList(p -> false);

        assert model.getFilteredPersonList().isEmpty();
    }

    private void showNoBinPerson(Model model) {
        model.updateFilteredBinList(p-> false);
        assert model.getRecycleBinPersonList().isEmpty();
    }

}
