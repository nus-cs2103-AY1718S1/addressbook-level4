//@@author cqhchan
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
import seedu.address.model.Database;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyDatabase;
import seedu.address.model.account.Account;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.AccountBuilder;

public class CreateAccountCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateAccountCommand(null);
    }

    @Test
    public void execute_accountAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingAccountAdded modelStub = new ModelStubAcceptingAccountAdded();
        Account validAccount = new AccountBuilder().build();

        CommandResult commandResult = getCreateAccountCommandForAccount(validAccount, modelStub).execute();

        assertEquals(String.format(CreateAccountCommand.MESSAGE_SUCCESS, validAccount), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validAccount), modelStub.accountsAdded);
    }

    @Test
    public void execute_duplicatePerson_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateAccountException();
        Account validAccount = new AccountBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateAccountCommand.MESSAGE_DUPLICATE_ACCOUNT);

        getCreateAccountCommandForAccount(validAccount, modelStub).execute();
    }

    @Test
    public void equals() {
        Account alice = new AccountBuilder().withUsername("Alice").build();
        Account bob = new AccountBuilder().withUsername("Bob").build();
        CreateAccountCommand addAliceCommand = new CreateAccountCommand(alice);
        CreateAccountCommand addBobCommand = new CreateAccountCommand(bob);

        // same object -> returns true
        assertTrue(addAliceCommand.equals(addAliceCommand));

        // same values -> returns true
        CreateAccountCommand addAliceCommandCopy = new CreateAccountCommand(alice);
        assertTrue(addAliceCommand.equals(addAliceCommandCopy));

        // different types -> returns false
        assertFalse(addAliceCommand.equals(1));

        // null -> returns false
        assertFalse(addAliceCommand.equals(null));

        // different person -> returns false
        assertFalse(addAliceCommand.equals(addBobCommand));
    }

    /**
     * Generates a new CreateAccountCommand with the details of the given person.
     */
    private CreateAccountCommand getCreateAccountCommandForAccount(Account account, Model model) {
        CreateAccountCommand command = new CreateAccountCommand(account);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }
        @Override
        public boolean checkAccount(ReadOnlyAccount account) {
            fail("This method should not be called");
            return true;
        }
        @Override
        public void addAccount(ReadOnlyAccount account) throws DuplicateAccountException {
            fail("This method should not be called");
        }
        @Override
        public void addReminder(ReadOnlyReminder newData) {
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
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateAccountException extends ModelStub {

        @Override
        public void addAccount(ReadOnlyAccount account) throws DuplicateAccountException {
            throw new DuplicateAccountException();
        }

        @Override
        public ReadOnlyDatabase getDatabase() {
            return new Database();
        }
    }


    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingAccountAdded extends ModelStub {
        final ArrayList<Account> accountsAdded = new ArrayList<>();

        @Override
        public void addAccount(ReadOnlyAccount account) throws DuplicateAccountException {
            accountsAdded.add(new Account(account));
        }

        @Override
        public ReadOnlyDatabase getDatabase() {
            return new Database();
        }
    }
}
