package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddMeetingCommand.MESSAGE_MEETING_CLASH;
import static seedu.address.logic.commands.AddMeetingCommand.MESSAGE_OVERDUE_MEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;

import java.util.List;
import java.util.Optional;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingTag;
import seedu.address.model.meeting.NameMeeting;
import seedu.address.model.meeting.Place;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.meeting.exceptions.DuplicateMeetingException;
import seedu.address.model.meeting.exceptions.MeetingBeforeCurrDateException;
import seedu.address.model.meeting.exceptions.MeetingClashException;
import seedu.address.model.meeting.exceptions.MeetingNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author kyngyi
/**
 * Edits the details of an existing meeting in the address book.
 */
public class EditMeetingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "editmeeting";
    public static final String COMMAND_ALIAS = "em";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the meeting identified "
            + "by the index number used in the last meeting listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_DATE + "DATE] "
            + "[" + PREFIX_LOCATION + "LOCATION] "
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_DATE + "01-01-2017 12:00 "
            + PREFIX_LOCATION + "Clementi MRT";

    public static final String MESSAGE_EDIT_MEETING_SUCCESS = "Edited Meeting: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the address book.";

    private final Index index;
    private final EditMeetingDescriptor editMeetingDescriptor;

    /**
     * @param index of the meeting in the filtered Meeting list to edit
     * @param editMeetingDescriptor details to edit the meeting with
     */
    public EditMeetingCommand(Index index, EditMeetingDescriptor editMeetingDescriptor) {
        requireNonNull(index);
        requireNonNull(editMeetingDescriptor);

        this.index = index;
        this.editMeetingDescriptor = new EditMeetingDescriptor(editMeetingDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyMeeting> lastShownList = model.getFilteredMeetingList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        ReadOnlyMeeting meetingToEdit = lastShownList.get(index.getZeroBased());
        Meeting editedMeeting = createEditedMeeting(meetingToEdit, editMeetingDescriptor,
                meetingToEdit.getPersonsMeet());

        try {
            model.updateMeeting(meetingToEdit, editedMeeting);
        } catch (DuplicateMeetingException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        } catch (MeetingNotFoundException pnfe) {
            throw new AssertionError("The target meeting cannot be missing");
        } catch (MeetingBeforeCurrDateException mde) {
            throw new CommandException(MESSAGE_OVERDUE_MEETING);
        } catch (MeetingClashException mce) {
            throw new CommandException(MESSAGE_MEETING_CLASH);
        }
        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        return new CommandResult(String.format(MESSAGE_EDIT_MEETING_SUCCESS, editedMeeting));
    }
    /**
     * Creates and returns a {@code Meeting} with the details of {@code meetingToEdit}
     * edited with {@code editMeetingDescriptor}.
     */
    private static Meeting createEditedMeeting(ReadOnlyMeeting meetingToEdit,
                                               EditMeetingDescriptor editMeetingDescriptor,
                                               List<ReadOnlyPerson> persons) {
        assert meetingToEdit != null;

        NameMeeting updatedName = editMeetingDescriptor.getName().orElse(meetingToEdit.getName());
        DateTime updatedDate = editMeetingDescriptor.getDate().orElse(meetingToEdit.getDate());
        Place updatedPlace = editMeetingDescriptor.getPlace().orElse(meetingToEdit.getPlace());
        MeetingTag updatedTag = editMeetingDescriptor.getMeetTag().orElse(meetingToEdit.getMeetTag());

        return new Meeting(updatedName, updatedDate, updatedPlace, persons, updatedTag);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditMeetingCommand)) {
            return false;
        }

        // state check
        EditMeetingCommand e = (EditMeetingCommand) other;
        return index.equals(e.index)
                && editMeetingDescriptor.equals(e.editMeetingDescriptor);
    }

    /**
     * Stores the details to edit the Meeting with. Each non-empty field value will replace the
     * corresponding field value of the meeting.
     */
    public static class EditMeetingDescriptor {
        private NameMeeting name;
        private DateTime date;
        private Place place;
        private List<ReadOnlyPerson> personsMeet;
        private MeetingTag meetTag;

        public EditMeetingDescriptor() {
        }

        public EditMeetingDescriptor(EditMeetingCommand.EditMeetingDescriptor toCopy) {
            this.name = toCopy.name;
            this.date = toCopy.date;
            this.place = toCopy.place;
            this.meetTag = toCopy.meetTag;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.date, this.place);
        }
        public void setNameMeeting(NameMeeting name) {
            this.name = name;
        }

        public Optional<NameMeeting> getName() {
            return Optional.ofNullable(name);
        }

        public void setDate(DateTime date) {
            this.date = date;
        }

        public Optional<DateTime> getDate() {
            return Optional.ofNullable(date);
        }

        public void setPlace(Place place) {
            this.place = place;
        }

        public Optional<Place> getPlace() {
            return Optional.ofNullable(place);
        }

        public Optional<MeetingTag> getMeetTag() {
            return Optional.ofNullable(meetTag);
        }

        public void setPersonsMeet(List<ReadOnlyPerson> persons) {
            this.personsMeet = persons;
        }

        public Optional<List<ReadOnlyPerson>> getPersonsMeet() {
            return Optional.ofNullable(personsMeet);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditMeetingDescriptor)) {
                return false;
            }

            // state check
            EditMeetingDescriptor e = (EditMeetingDescriptor) other;

            return getName().equals(e.getName())
                    && getDate().equals(e.getDate())
                    && getPlace().equals(e.getPlace())
                    && getMeetTag().equals(e.getMeetTag());
        }
    }
}
