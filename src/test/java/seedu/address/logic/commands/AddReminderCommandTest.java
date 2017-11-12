package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyDatabase;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.ReminderBuilder;

public class AddReminderCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReminderCommand(null);
    }

    @Test
    public void execute_reminderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReminderAdded modelStub = new ModelStubAcceptingReminderAdded();
        Reminder validReminder = new ReminderBuilder().build();

        CommandResult commandResult = getAddReminderCommandForReminder(validReminder, modelStub).execute();

        assertEquals(String.format(AddReminderCommand.MESSAGE_SUCCESS, validReminder), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validReminder), modelStub.remindersAdded);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateReminderException();
        Reminder validReminder = new ReminderBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddReminderCommand.MESSAGE_DUPLICATE_REMINDER);

        getAddReminderCommandForReminder(validReminder, modelStub).execute();
    }

    @Test
    public void equals() {
        Reminder reminder = new ReminderBuilder().withTask("Project").build();
        Reminder anotherReminder = new ReminderBuilder().withTask("Assignment").build();
        AddReminderCommand command = new AddReminderCommand(reminder);
        AddReminderCommand anotherCommand = new AddReminderCommand(anotherReminder);

        // same object -> returns true
        assertTrue(command.equals(command));

        // same values -> returns true
        AddReminderCommand commandCopy = new AddReminderCommand(reminder);
        assertTrue(command.equals(commandCopy));

        // different types -> returns false
        assertFalse(command.equals(1));

        // null -> returns false
        assertFalse(command.equals(null));

        // different reminder -> returns false
        assertFalse(command.equals(anotherCommand));
    }

    /**
     * Generates a new AddReminderCommand with the details of the given reminder.
     */
    private AddReminderCommand getAddReminderCommandForReminder(Reminder reminder, Model model) {
        AddReminderCommand command = new AddReminderCommand(reminder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void addAccount(ReadOnlyAccount account) throws DuplicateAccountException {
            fail("This method should not be called");
        }
        @Override
        public void addReminder(ReadOnlyReminder newData) throws DuplicateReminderException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public void resetDatabase(ReadOnlyDatabase newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ReadOnlyDatabase getDatabase() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteAccount(ReadOnlyAccount account) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        public void updateAccount(ReadOnlyAccount account, ReadOnlyAccount editedAccount)
                throws DuplicateAccountException {
            fail("This method should not be called.");
        }


        @Override
        public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder editedReminder)
                throws DuplicateReminderException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteUnusedTag(Tag tag) {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyAccount> getFilteredAccountList() {
            fail("This method should not be called.");
            return null;
        }


        @Override
        public ObservableList<ReadOnlyReminder> getFilteredReminderList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredAccountList(Predicate<ReadOnlyAccount> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredReminderList(Predicate<ReadOnlyReminder> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deletePersonTag(Tag tag) {
            fail("This metthod should not be called.");
        }

        @Override
        public void deleteReminderTag(Tag tag) {
            fail("This metthod should not be called.");
        }

        public Boolean checkIfPersonListEmpty(ArrayList<ReadOnlyPerson> contactList) {
            fail("This method should not be called.");
            return false;
        }

        public Boolean checkIfReminderListEmpty(ArrayList<ReadOnlyReminder> reminderList) {
            fail("This method should not be called.");
            return false;
        }

        @Override
        public void sortListByName(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void sortListByBirthday(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void sortListByAge(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
            fail("This method should not be called.");
        }

        @Override
        public void sortListByPriority(ArrayList<ReadOnlyReminder> contactList)  throws CommandException {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicateReminderException when trying to add a reminder.
     */
    private class ModelStubThrowingDuplicateReminderException extends ModelStub {
        @Override
        public void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
            throw new DuplicateReminderException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the reminder being added.
     */
    private class ModelStubAcceptingReminderAdded extends ModelStub {
        final ArrayList<Reminder> remindersAdded = new ArrayList<>();

        @Override
        public void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
            remindersAdded.add(new Reminder(reminder));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }
}
