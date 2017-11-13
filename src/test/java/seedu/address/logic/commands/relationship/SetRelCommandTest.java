//@@author huiyiiih
package seedu.address.logic.commands.relationship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JANE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JOE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REL_SIBLINGS;
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
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.relationship.SetRelCommand.EditPerson;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.EditPersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class SetRelCommandTest {
    private static final boolean addPrefixPresent = false;
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    //private Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_failure() {
        SetRelCommand setRelCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, new SetRelCommand
            .EditPerson(), addPrefixPresent);
        ReadOnlyPerson editedPersonOne = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson editedPersonTwo = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());

        String expectedMessage = String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPersonOne,
            editedPersonTwo);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(setRelCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPerson editPerson = new EditPersonBuilder().withToAddRel(VALID_REL_SIBLINGS).build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_FIRST_PERSON, outOfBoundIndex, editPerson, addPrefixPresent);

        assertCommandFailure(setRelCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        SetRelCommand setRelCommand = prepareCommand(INDEX_FIRST_PERSON, outOfBoundIndex,
            new EditPersonBuilder().withName(VALID_NAME_JOE).build(), addPrefixPresent);

        assertCommandFailure(setRelCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }
    /*@Test
    Todo: To be completed
    public void execute_addRelToPersons_success() throws Exception {
        EditPerson editPerson = new EditPersonBuilder().withToAddRel("siblings").build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, editPerson,
            addPrefixPresent);
        Person personOne = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
            .withRelation("siblings").build();
        Person personTwo = new PersonBuilder(model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased()))
            .withRelation("siblings").build();
        String expectedMessage = String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS,
            personOne, personTwo);
        assertCommandSuccess(setRelCommand, model, expectedMessage, expectedModel);
    }*/

    @Test
    public void equals() {
        final SetRelCommand standardCommand = new SetRelCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, DESC_JANE,
            addPrefixPresent);

        // same values -> returns true
        EditPerson editPerson = new EditPerson(DESC_JANE);
        SetRelCommand commandWithSameValues = new SetRelCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
            editPerson, addPrefixPresent);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // same index -> returns false
        assertFalse(standardCommand.equals(new SetRelCommand(INDEX_SECOND_PERSON, INDEX_SECOND_PERSON, DESC_JANE,
            addPrefixPresent)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new SetRelCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, DESC_JOE,
            addPrefixPresent)));
        // relationship already set for two persons
        assertFalse(standardCommand.equals(new SetRelCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, DESC_JOE,
            true)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private SetRelCommand prepareCommand(Index indexOne, Index indexTwo, EditPerson descriptor,
                                            boolean addPrefixPresent) {
        SetRelCommand setRelCommand = new SetRelCommand(indexOne, indexTwo, descriptor, addPrefixPresent);
        setRelCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return setRelCommand;
    }
}
//@@author

