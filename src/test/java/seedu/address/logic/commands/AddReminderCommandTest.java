package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.sun.javafx.collections.ImmutableObservableList;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.UpdateListForSelectionEvent;
import seedu.address.commons.events.model.UpdatePopularityCounterForSelectionEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.Reminder;
import seedu.address.model.reminders.UniqueReminderList;
import seedu.address.model.reminders.exceptions.DuplicateReminderException;
import seedu.address.model.reminders.exceptions.ReminderNotFoundException;
import seedu.address.testutil.ReminderBuilder;

public class AddReminderCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    //@@author justinpoh
    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReminderCommand(null);
    }


    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
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
        Reminder coffeeReminder = new ReminderBuilder().build();
        Reminder assignmentReminder = new ReminderBuilder().withReminder("Assignment").build();
        AddReminderCommand addCoffeeCommand = new AddReminderCommand(coffeeReminder);
        AddReminderCommand addAssignmentCommand = new AddReminderCommand(assignmentReminder);

        // same object -> returns true
        assertTrue(addCoffeeCommand.equals(addCoffeeCommand));

        // same values -> returns true
        AddReminderCommand addCoffeeCommandCopy = new AddReminderCommand(coffeeReminder);
        assertTrue(addCoffeeCommand.equals(addCoffeeCommandCopy));

        // different types -> returns false
        assertFalse(addCoffeeCommand.equals(1));

        // null -> returns false
        assertFalse(addCoffeeCommand.equals(null));

        // different reminder -> returns false
        assertFalse(addCoffeeCommand.equals(addAssignmentCommand));
    }

    /**
     * Generates a new AddReminderCommand with the details of the given reminder.
     */
    private AddReminderCommand getAddReminderCommandForReminder(Reminder reminder, Model model) {
        AddReminderCommand command = new AddReminderCommand(reminder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
    //@@author

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public void resetReminders(UniqueReminderList newReminders) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
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

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonListForViewTag(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void increaseCounterByOneForATag(List<ReadOnlyPerson> filteredPersonList) {
            fail("This method should not be called");
        }

        @Override
        public ReadOnlyPerson increaseCounterByOne(ReadOnlyPerson person) {
            fail("This method should not be called");
            return person;
        }

        @Override
        public void refreshWithPopulatingAddressBook() {
            fail("This method should not be called");
        }

        @Override
        public void getOnlyTopFiveMaximum() {
            fail("This method should not be called");
        }
        @Override
        public void updatePopularContactList() {
            fail("This method should not be called");
        }

        @Override

        public ObservableList<ReadOnlyPerson> getPopularContactList() {
            fail("This method should not be called");
            return new ImmutableObservableList<>();
        }

        @Override
        public void updatePersonsPopularityCounterByOne(ReadOnlyPerson person) throws DuplicatePersonException,
                PersonNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void updateFilteredListToShowAll() {
            fail("This method should not be called");
        }

        @Override
        public void sortFilteredPersonList() {
            fail("This method should not be called");
        }

        public boolean addDisplayPicture(String path, int newPath) throws IOException {
            fail("This method should not be called");
            return false;
        }

        @Override
        public ObservableList<ReadOnlyPerson> getBirthdayPanelFilteredPersonList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyReminder> getSortedReminderList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public UniqueReminderList getUniqueReminderList() {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
            fail("This method should not be called");
        }

        @Override
        public void showLocation(ReadOnlyPerson person) {
            fail("This method should not be called");
        }

        @Override
        public void processEmailEvent(String recipients, Subject subject, Body body, Service service) {
            fail("This method should not be called");
        }

        @Override
        public void showPersonWebpage(ReadOnlyPerson person) throws PersonNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public String createEmailRecipients(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called");
            return null;
        }

        @Override
        public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder editedReminder)
                throws DuplicateReminderException, ReminderNotFoundException {
            fail("This method should not be called");
        }

        @Override
        public void showDefaultPanel() {
            fail("This method should not be called");
        }

        @Override
        public void handleUpdateListForSelectionEvent(UpdateListForSelectionEvent updateListForSelectionEvent) {
            fail("This method should not be called");
        }

        @Override
        public Index getIndexOfGivenPerson(ReadOnlyPerson person) {
            fail("This method should not be called");
            return Index.fromZeroBased(-1);
        }

        @Override
        public void handleUpdatePopularityCounterForSelectionEvent(
                UpdatePopularityCounterForSelectionEvent updatePopularityCounterForSelectionEvent) {
            fail("This method should not be called");
        }

        @Override
        public void clearSelection() {
            fail("This method should not be called");
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
    }

    /**
     * A Model stub that always throw a DuplicateReminderException when trying to add a reminder.
     */
    private class ModelStubThrowingDuplicateReminderException extends ModelStub {
        @Override
        public void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
            throw new DuplicateReminderException();
        }
    }
}
