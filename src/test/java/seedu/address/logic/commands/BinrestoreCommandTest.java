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
 * Contains integration tests (interaction with the Model) and unit tests for {@code BinrestoreCommand}.
 */
public class BinrestoreCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalRecyclbin(), new UserPrefs());
    private ArrayList<Index> personsToRestore = new ArrayList<>();
    private ArrayList<ReadOnlyPerson> persontorestore = new ArrayList<>();


    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        ReadOnlyPerson personToDelete = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

        persontorestore.add(personToDelete);

        personsToRestore.add(INDEX_FIRST_PERSON);

        BinrestoreCommand binrestoreCommand1 = prepareCommand(personsToRestore);

        String expectedMessage1 = binrestoreCommand1.MESSAGE_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());

        expectedModel1.restorePerson(persontorestore);

        assertCommandSuccess(binrestoreCommand1, model, expectedMessage1, expectedModel1);
    }

    @Test
    public void execute_validIndexUnfilteredList_success2() throws Exception {
        ReadOnlyPerson personToRestore = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson secondToRestore = model.getRecycleBinPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        persontorestore.clear();
        persontorestore.add(personToRestore);
        persontorestore.add(secondToRestore);

        personsToRestore.clear();
        personsToRestore.add(INDEX_FIRST_PERSON);
        personsToRestore.add(INDEX_SECOND_PERSON);

        BinrestoreCommand binrestoreCommand = prepareCommand(personsToRestore);

        String expectedMessage1 = BinrestoreCommand.MESSAGE_SUCCESS;

        ModelManager expectedModel1 = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());

        expectedModel1.restorePerson(persontorestore);

        assertCommandSuccess(binrestoreCommand, model, expectedMessage1, expectedModel1);
    }


    @Test
    public void execute_invalidIndexUnfilteredList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getRecycleBinPersonList().size() + 1);
        personsToRestore.clear();
        personsToRestore.add(outOfBoundIndex);
        BinrestoreCommand binrestoreCommand = prepareCommand(personsToRestore);

        assertCommandFailure(binrestoreCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() throws Exception {
        showFirstBinPersonOnly(model);

        ReadOnlyPerson personToDelete = model.getRecycleBinPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        personsToRestore.clear();
        personsToRestore.add(INDEX_FIRST_PERSON);
        persontorestore.clear();
        persontorestore.add(personToDelete);
        BinrestoreCommand binrestoreCommand = prepareCommand(personsToRestore);

        String expectedMessage = String.format(BinrestoreCommand.MESSAGE_SUCCESS, personToDelete);

        Model expectedModel = new ModelManager(model.getAddressBook(), model.getRecycleBin(), new UserPrefs());
        expectedModel.restorePerson(persontorestore);
        showNoBinPerson(expectedModel);

        assertCommandSuccess(binrestoreCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_invalidIndexFilteredList_throwsCommandException() {
        showFirstBinPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        personsToRestore.clear();
        personsToRestore.add(INDEX_SECOND_PERSON);
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRecycleBin().getPersonList().size());

        BinrestoreCommand binrestoreCommand = prepareCommand(personsToRestore);

        assertCommandFailure(binrestoreCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }



    @Test
    public void equals() {
        ArrayList<Index> first = new ArrayList<>();
        ArrayList<Index> second = new ArrayList<>();
        first.add(INDEX_FIRST_PERSON);
        second.add(INDEX_SECOND_PERSON);
        BinrestoreCommand binrestoreFirstCommand = new BinrestoreCommand(first);
        BinrestoreCommand binrestoreSecondCommand = new BinrestoreCommand(second);

        // same object -> returns true
        assertTrue(binrestoreFirstCommand.equals(binrestoreFirstCommand));

        // same values -> returns true
        BinrestoreCommand binrestoreFirstCommandCopy = new BinrestoreCommand(first);
        assertTrue(binrestoreFirstCommand.equals(binrestoreFirstCommandCopy));

        // different types -> returns false
        assertFalse(binrestoreFirstCommand.equals(1));

        // null -> returns false
        assertFalse(binrestoreFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(binrestoreFirstCommand.equals(binrestoreSecondCommand));
    }

    /**
     * Returns a {@code BinrestoreCommand} with the parameter {@code index}.
     */
    private BinrestoreCommand prepareCommand(ArrayList<Index> indexes) {

        BinrestoreCommand binrestoreCommand = new BinrestoreCommand(indexes);
        binrestoreCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return binrestoreCommand;
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
