package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author alexanderleegs
/**
 * Adds a meeting to an existing person in the address book.
 */
public class AddMeetingCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addmeeting";
    public static final String COMMAND_ALIAS = "am";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "MEETING NAME " + "/ "
            + "MEETING TIME (YYYY-MM-DD HH:MM)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "business " + "/ " + "2017-12-20 10:00";

    public static final String MESSAGE_ADD_MEETING_SUCCESS = "Added Meeting: %1$s";
    public static final String MESSAGE_DUPLICATE_MEETING = "This person already has this meeting.";
    public static final String MESSAGE_TIME_CONSTRAINTS = "Time format should be YYYY-MM-DD HH:MM";

    private final Index index;
    private final String meetingName;
    private final String meetingTime;
    private Meeting newMeeting;

    /**
     * @param index of the person in the filtered person list to edit
     * @param meetingName to be added to the person
     * @param meetingTime to be added to the person
     */
    public AddMeetingCommand(Index index, String meetingName, String meetingTime) {
        requireNonNull(index);

        this.index = index;
        this.meetingName = meetingName;
        this.meetingTime = meetingTime;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        try {
            newMeeting = new Meeting(personToEdit, meetingName, meetingTime);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_TIME_CONSTRAINTS);
        }
        Set<Meeting> oldMeetings = new HashSet<Meeting>(personToEdit.getMeetings());
        if (oldMeetings.contains(newMeeting)) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }
        Person editedPerson = new Person(personToEdit);
        oldMeetings.add(newMeeting);
        editedPerson.setMeetings(oldMeetings);

        try {
            model.updatePerson(personToEdit, editedPerson);
            model.sortMeeting();
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Not creating a new person");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_ADD_MEETING_SUCCESS, newMeeting.meetingName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddMeetingCommand)) {
            return false;
        }

        // state check
        AddMeetingCommand toCompare = (AddMeetingCommand) other;
        return index.equals(toCompare.index)
                && meetingName.equals(toCompare.meetingName)
                && meetingTime.equals(toCompare.meetingTime);
    }
}
