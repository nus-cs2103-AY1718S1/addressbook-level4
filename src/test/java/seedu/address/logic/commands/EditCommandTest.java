package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.logic.commands.CommandTestUtil.DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HANDPHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstPersonOnly;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.CommandTest;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.EditCommand.EditPersonDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;

/**
 * Contains integration tests (interaction with the Model) and unit tests for EditCommand.
 */
public class EditCommandTest extends CommandTest {

    @Test
    public void execute_allFieldsSpecifiedUnfilteredList_success() {
        try {
            Person editedPerson = new PersonBuilder().build();
            EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
            EditCommand editCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

            String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName());

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

            assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        } catch (DuplicatePersonException | PersonNotFoundException | CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_deadlineBeforeDateBorrow_failure() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        descriptor.setDeadline(new Deadline("11-11-2001"));
        EditCommand editCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format("Deadline cannot be before date borrow.");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandFailure(editCommand, model, expectedMessage);
    }

    //@@author jelneo
    @Test
    public void execute_totalDebtLessThanCurrentDebt_failure() throws Exception {
        Person editedPerson = new PersonBuilder().build();
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        descriptor.setTotalDebt(new Debt("0"));
        EditCommand editCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format("Total debt cannot be less than current debt");

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandFailure(editCommand, model, expectedMessage);
    }
    //@@author

    @Test
    public void execute_overdueUpdate_afterEditDeadline() throws Exception {
        int sizeOfList = model.getFilteredPersonList().size();
        Index lastIdx = Index.fromOneBased(sizeOfList);
        // person has overdue debt
        assertTrue(model.getFilteredPersonList().get(lastIdx.getZeroBased()).hasOverdueDebt());

        Person editedPerson = (Person) model.getFilteredPersonList().get(lastIdx.getZeroBased());
        editedPerson.setDeadline(new Deadline("11-11-2020"));
        editedPerson.setHasOverdueDebt(false); // editedPerson has updated deadline, overdue debt set to false.

        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
        EditCommand editCommand = prepareCommand(lastIdx, descriptor);

        String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName());

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(lastIdx.getZeroBased()), editedPerson);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        // after edit, debt no longer overdue
        assertFalse(model.getFilteredPersonList().get(lastIdx.getZeroBased()).hasOverdueDebt());
    }

    @Test
    public void execute_someFieldsSpecifiedUnfilteredList_success() {
        try {
            Index indexLastPerson = Index.fromOneBased(model.getFilteredPersonList().size());
            ReadOnlyPerson lastPerson = model.getFilteredPersonList().get(indexLastPerson.getZeroBased());

            PersonBuilder personInList = new PersonBuilder(lastPerson);
            Person editedPerson = personInList.withName(VALID_NAME_BOB).withHandphone(VALID_HANDPHONE_BOB)
                    .withTags(VALID_TAG_HUSBAND).build();

            EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                    .withHandphone(VALID_HANDPHONE_BOB).withTags(VALID_TAG_HUSBAND).build();
            EditCommand editCommand = prepareCommand(indexLastPerson, descriptor);

            String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName());

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.updatePerson(lastPerson, editedPerson);

            assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        } catch (DuplicatePersonException | PersonNotFoundException | CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        try {
            EditCommand editCommand = prepareCommand(INDEX_FIRST_PERSON, new EditPersonDescriptor());
            ReadOnlyPerson editedPerson = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());

            String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName());

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

            assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }

    @Test
    public void execute_filteredList_success() {
        try {
            showFirstPersonOnly(model);

            ReadOnlyPerson personInFilteredList = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
            Person editedPerson = new PersonBuilder(personInFilteredList).withName(VALID_NAME_BOB).build();
            EditCommand editCommand = prepareCommand(INDEX_FIRST_PERSON,
                    new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());

            String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName());

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

            assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        } catch (DuplicatePersonException | PersonNotFoundException | CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_duplicatePersonUnfilteredList_failure() throws Exception {
        Person firstPerson = new Person(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(firstPerson).build();
        descriptor.setTotalDebt(firstPerson.getTotalDebt());
        assertCommandFailure(prepareCommand(INDEX_SECOND_PERSON, descriptor), model,
                EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_duplicatePersonFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);

        // edit person in filtered list into a duplicate in address book
        ReadOnlyPerson personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        EditCommand editCommand = prepareCommand(INDEX_FIRST_PERSON,
                new EditPersonDescriptorBuilder(personInList).build());
        assertCommandFailure(editCommand, model, EditCommand.MESSAGE_DUPLICATE_PERSON);
    }

    @Test
    public void execute_invalidPersonIndexUnfilteredList_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build();
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        EditCommand editCommand = prepareCommand(outOfBoundIndex, descriptor);
    }

    //@@author khooroko
    @Test
    public void execute_noIndexPersonSelected_success() {
        try {
            Person editedPerson = new PersonBuilder().build();
            EditPersonDescriptor descriptor = new EditPersonDescriptorBuilder(editedPerson).build();
            model.updateSelectedPerson(model.getFilteredPersonList().get(0));
            EditCommand editCommand = prepareCommand(descriptor);

            String expectedMessage = ListObserver.MASTERLIST_NAME_DISPLAY_FORMAT
                    + String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName());

            Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
            expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

            assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
        } catch (DuplicatePersonException | PersonNotFoundException | CommandException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void execute_noIndexNoSelection_failure() throws Exception {
        ReadOnlyPerson personInList = model.getAddressBook().getPersonList().get(INDEX_SECOND_PERSON.getZeroBased());
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_NO_PERSON_SELECTED);
        prepareCommand(new EditPersonDescriptorBuilder(personInList).build());
        fail(UNEXPECTED_EXECTION);
    }

    //@@author
    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidPersonIndexFilteredList_failure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());
        thrown.expect(CommandException.class);
        thrown.expectMessage(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        prepareCommand(outOfBoundIndex, new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB).build());
        fail(UNEXPECTED_EXECTION);
    }

    @Test
    public void equals() throws Exception {
        final EditCommand standardCommand = new EditCommand(INDEX_FIRST_PERSON, DESC_AMY);

        // same values -> returns true
        EditPersonDescriptor copyDescriptor = new EditPersonDescriptor(DESC_AMY);
        EditCommand commandWithSameValues = new EditCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_SECOND_PERSON, DESC_AMY)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_BOB)));
    }

    /**
     * Returns an {@code EditCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditCommand prepareCommand(Index index, EditPersonDescriptor descriptor) throws CommandException {
        EditCommand editCommand = new EditCommand(index, descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }

    /**
     * Returns an {@code EditCommand} with parameter {@code descriptor}
     */
    private EditCommand prepareCommand(EditPersonDescriptor descriptor) throws CommandException {
        EditCommand editCommand = new EditCommand(descriptor);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
    }
}
