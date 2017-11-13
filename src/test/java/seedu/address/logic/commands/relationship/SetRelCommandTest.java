//@@author huiyiiih
package seedu.address.logic.commands.relationship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JANE;
import static seedu.address.logic.commands.CommandTestUtil.DESC_JOE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALICE_REL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_BENSON_REL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_JOE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REL_COLLEAGUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REL_SIBLINGS;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FOURTH_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;
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
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.testutil.EditPersonBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for SetRelCommand.
 */
public class SetRelCommandTest {
    private boolean addPrefixPresent = false;
    private boolean shouldClear = true;

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

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
    @Test
    public void execute_addMultipleRelToPersons_failure() {
        addPrefixPresent = true;
        EditPerson editPerson = new EditPersonBuilder().withToAddRel(VALID_REL_COLLEAGUE).build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_THIRD_PERSON, INDEX_FOURTH_PERSON, editPerson,
            addPrefixPresent);
        assertCommandFailure(setRelCommand, model, SetRelCommand.MESSAGE_NO_MULTIPLE_REL);
    }
    @Test
    public void execute_addRelToPerson_success() throws Exception {
        addPrefixPresent = true;
        ReadOnlyPerson personInFilteredListOne = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        ReadOnlyPerson personInFilteredListTwo = model.getFilteredPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        Person personOne = new PersonBuilder(personInFilteredListOne).withRelation(VALID_BENSON_REL).build();
        Person personTwo = new PersonBuilder(personInFilteredListTwo).withRelation(VALID_ALICE_REL).build();
        EditPerson editPerson = new EditPersonBuilder().withToAddRel(VALID_REL_SIBLINGS).build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, editPerson,
            addPrefixPresent);
        String expectedMessage = String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS,
            personOne, personTwo);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personInFilteredListOne, personOne);
        expectedModel.updatePerson(personInFilteredListTwo, personTwo);
        assertCommandSuccess(setRelCommand, model, expectedMessage, expectedModel);

    }
    @Test
    public void execute_deleteRelFromPersons_success() throws Exception {
        ReadOnlyPerson personInFilteredListOne = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        ReadOnlyPerson personInFilteredListTwo = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        Person personOne = new PersonBuilder(personInFilteredListOne).withRelation().build();
        Person personTwo = new PersonBuilder(personInFilteredListTwo).withRelation().build();
        EditPerson editPerson = new EditPersonBuilder().withToDeleteRel(VALID_REL_SIBLINGS).build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_THIRD_PERSON, INDEX_FOURTH_PERSON, editPerson,
            addPrefixPresent);
        String expectedMessage = String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS,
            personOne, personTwo);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personInFilteredListOne, personOne);
        expectedModel.updatePerson(personInFilteredListTwo, personTwo);
        assertCommandSuccess(setRelCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_clearAllRelFromPersons_success() throws Exception {
        EditPerson editPerson = new EditPersonBuilder().withToClearRels(shouldClear).build();
        ReadOnlyPerson personInFilteredListOne = model.getFilteredPersonList().get(INDEX_THIRD_PERSON.getZeroBased());
        ReadOnlyPerson personInFilteredListTwo = model.getFilteredPersonList().get(INDEX_FOURTH_PERSON.getZeroBased());
        Person personOne = new PersonBuilder(personInFilteredListOne).withRelation().build();
        Person personTwo = new PersonBuilder(personInFilteredListTwo).withRelation().build();
        SetRelCommand setRelCommand = prepareCommand(INDEX_THIRD_PERSON, INDEX_FOURTH_PERSON, editPerson,
            addPrefixPresent);
        String expectedMessage = String.format(SetRelCommand.MESSAGE_EDIT_PERSON_SUCCESS,
            personOne, personTwo);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(personInFilteredListOne, personOne);
        expectedModel.updatePerson(personInFilteredListTwo, personTwo);
        assertCommandSuccess(setRelCommand, model, expectedMessage, expectedModel);
    }

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
    }

    /**
     * Returns an {@code SetRelCommand} with parameters {@code index} and {@code descriptor}
     */
    private SetRelCommand prepareCommand(Index indexOne, Index indexTwo, EditPerson descriptor,
                                            boolean addPrefixPresent) {
        SetRelCommand setRelCommand = new SetRelCommand(indexOne, indexTwo, descriptor, addPrefixPresent);
        setRelCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return setRelCommand;
    }
}
//@@author

