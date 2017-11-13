package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;
import seedu.address.testutil.ReminderBuilder;
//@@author RonakLakhotia
public class AddReminderTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReminder(null);
    }

    @Test
    public void execute_reminderAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReminderAdded modelStub = new ModelStubAcceptingReminderAdded();
        Reminder validReminder = new ReminderBuilder().build();

        CommandResult commandResult = getAddCommandForReminder(validReminder, modelStub).execute();

        assertEquals(String.format(AddReminder.MESSAGE_SUCCESS, validReminder), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validReminder), modelStub.remindersAdded);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateReminderException();
        Reminder validReminder = new ReminderBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddReminder.MESSAGE_DUPLICATE_REMINDER);

        getAddCommandForReminder(validReminder, modelStub).execute();
    }

    @Test
    public void equals() {
        Reminder assignment = new ReminderBuilder().withDetails("Assignment").build();
        Reminder meeting = new ReminderBuilder().withDetails("Meeting").build();
        AddReminder addAssignmentCommand = new AddReminder(assignment);
        AddReminder addMeetingCommand = new AddReminder(meeting);

        // same object -> returns true
        assertTrue(addAssignmentCommand.equals(addAssignmentCommand));

        // same values -> returns true
        AddReminder addAssignmentCommandCopy = new AddReminder(assignment);
        assertTrue(addAssignmentCommand.equals(addAssignmentCommandCopy));

        // different types -> returns false
        assertFalse(addAssignmentCommand.equals(1));

        // null -> returns false
        assertFalse(addAssignmentCommand.equals(null));

        // different person -> returns false
        assertFalse(addAssignmentCommand.equals(addMeetingCommand));
    }

    /**
     * Generates a new AddReminderCommand with the details of the given reminder.
     */
    private AddReminder getAddCommandForReminder(Reminder reminder, Model model) {
        AddReminder command = new AddReminder(reminder);
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
        public void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
            fail("This method should not be called.");
        }
        @Override
        public void deleteReminder(ReadOnlyReminder reminder) throws ReminderNotFoundException {
            fail("This method should not be called.");
        }
        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            fail("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            fail("This method should not be called.");
            return null;
        }
        @Override
        public void addPhotoPerson(ReadOnlyPerson person, String FilePath, Index targetIndex)
                throws PersonNotFoundException {
            fail("This method should not be called.");

        }
        //@@author
        //@@author yangminxingnus
        @Override
        public void addRemarkPerson(ReadOnlyPerson person, String remark, Index targetIndex) {
            fail("This method should not be called.");
        }
        //@@author yangminxingnus
        //@@author RonakLakhotia
        @Override
        public void clearBrowserPanel() {
            fail("This method should not be called.");
        }

        @Override
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }
        @Override
        public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder changedReminder) {
            fail("This method should not be called");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }
        @Override
        public ObservableList<ReadOnlyReminder> getFilteredReminderList() {
            fail("This method should not be called.");
            return null;
        }
        @Override
        public void updateFilteredReminderList(Predicate<ReadOnlyReminder> predicate) {
            fail("This method should not be called.");
        }
        @Override
        public void sendMailToContacts(String tagName, String subject, List<ReadOnlyPerson> lastShownList) {
            fail("This method should never be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateTagColorPair(Set<Tag> tagList, TagColor color) throws IllegalValueException {
            fail("This method should not be called.");
        }

        @Override
        public void faceBook(ReadOnlyPerson person) throws PersonNotFoundException {
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
