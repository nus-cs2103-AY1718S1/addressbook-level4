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
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.meeting.exceptions.DuplicateMeetingException;
import seedu.address.model.meeting.exceptions.MeetingNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.testutil.MeetingBuilder;

public class AddMeetingCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private Index index;

    @Test
    public void constructor_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddCommand(null);
    }

    @Test
    public void execute_meetingAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingMeetingAdded modelStub = new ModelStubAcceptingMeetingAdded();
        Meeting validMeeting = new MeetingBuilder().build();

        CommandResult commandResult = getAddMeetingCommandForMeeting(validMeeting, modelStub).execute();

        assertEquals(String.format(AddMeetingCommand.MESSAGE_SUCCESS, validMeeting), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validMeeting), modelStub.meetingsAdded);
    }

    @Test
    public void execute_duplicateMeeting_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateMeetingException();
        Meeting validMeeting = new MeetingBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddMeetingCommand.MESSAGE_DUPLICATE_MEETING);

        getAddMeetingCommandForMeeting(validMeeting, modelStub).execute();
    }

    @Test
    public void equals() {
        Meeting project = new MeetingBuilder().withNameMeeting("Project").build();
        Meeting meeting = new MeetingBuilder().withNameMeeting("Meeting").build();
        AddMeetingCommand addProjectCommand = new AddMeetingCommand(project.getName(), project.getDate(),
                project.getPlace(), index);
        AddMeetingCommand addMeetingCommand = new AddMeetingCommand(meeting.getName(), meeting.getDate(),
                meeting.getPlace(), index);

        // same object -> returns true
        assertTrue(addProjectCommand.equals(addProjectCommand));

        // same values -> returns true
        AddMeetingCommand addProjectCommandCopy = new AddMeetingCommand(project.getName(), project.getDate(),
                project.getPlace(), index);
        assertTrue(addProjectCommand.equals(addProjectCommandCopy));

        // different types -> returns false
        assertFalse(addProjectCommand.equals(1));

        // null -> returns false
        assertFalse(addProjectCommand.equals(null));

        // different person -> returns false
        assertFalse(addProjectCommand.equals(addMeetingCommand));
    }

    /**
     * Generates a new AddMeetingCommand with the details of the given meeting.
     */
    private AddMeetingCommand getAddMeetingCommandForMeeting(Meeting meeting, Model model) {
        this.index = Index.fromZeroBased(1);
        AddMeetingCommand command = new AddMeetingCommand(meeting.getName(), meeting.getDate(), meeting.getPlace(),
                index);
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
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException {
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
        public void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void deleteMeeting(ReadOnlyMeeting target) throws MeetingNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
                throws DuplicatePersonException {
            fail("This method should not be called.");
        }

        @Override
        public void updateMeeting(ReadOnlyMeeting target, ReadOnlyMeeting editedPerson)
                throws DuplicateMeetingException {
            fail("This method should not be called.");
        }

        @Override
        public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public ObservableList<ReadOnlyMeeting> getFilteredMeetingList() {
            fail("This method should not be called.");
            return null;
        }

        @Override
        public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void updateFilteredMeetingList(Predicate<ReadOnlyMeeting> predicate) {
            fail("This method should not be called.");
        }

        @Override
        public void deleteTag(Tag tag) {
            fail("This method should not be called.");
        }
    }

    /**
     * A Model stub that always throw a DuplicatePersonException when trying to add a person.
     */
    private class ModelStubThrowingDuplicateMeetingException extends ModelStub {
        @Override
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException {
            throw new DuplicateMeetingException();
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

    /**
     * A Model stub that always accept the person being added.
     */
    private class ModelStubAcceptingMeetingAdded extends ModelStub {
        final ArrayList<Meeting> meetingsAdded = new ArrayList<>();

        @Override
        public void addMeeting(ReadOnlyMeeting meeting) throws DuplicateMeetingException {
            meetingsAdded.add(new Meeting(meeting));
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            return new AddressBook();
        }
    }

}
