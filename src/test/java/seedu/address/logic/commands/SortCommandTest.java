package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.TypicalPersons;

public class SortCommandTest {
    private Model model = new ModelManager(new AddressBook(), new UserPrefs());

    @Test
    public void execute_validIndexUnfilteredList_success() throws Exception {
        //setting up the model to for entries in reverse order
        model.addPerson(TypicalPersons.GEORGE);
        model.addPerson(TypicalPersons.FIONA);
        model.addPerson(TypicalPersons.ELLE);
        model.addPerson(TypicalPersons.DANIEL);
        model.addPerson(TypicalPersons.CARL);
        model.addPerson(TypicalPersons.BENSON);
        //model.addPerson(TypicalPersons.ALICE);

        //create expectations
//        SortCommand sortCommand = new SortCommand();
        ModelManager expectedModel = new ModelManager(new AddressBook(), new UserPrefs());
        expectedModel.addPerson(TypicalPersons.BENSON);
        expectedModel.addPerson(TypicalPersons.CARL);
        expectedModel.addPerson(TypicalPersons.DANIEL);
        expectedModel.addPerson(TypicalPersons.ELLE);
        expectedModel.addPerson(TypicalPersons.FIONA);
        expectedModel.addPerson(TypicalPersons.GEORGE);

        String expectedMessage = SortCommand.MESSAGE_SUCCESS;

        assertCommandSuccess(new SortCommand(), model, expectedMessage, expectedModel);
    }
}