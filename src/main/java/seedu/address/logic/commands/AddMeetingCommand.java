package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;

import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.NameMeeting;
import seedu.address.model.meeting.PersonToMeet;
import seedu.address.model.meeting.PhoneNum;
import seedu.address.model.meeting.Place;
import seedu.address.model.meeting.exceptions.DuplicateMeetingException;
import seedu.address.model.meeting.exceptions.MeetingBeforeCurrDateException;
import seedu.address.model.person.ReadOnlyPerson;
//import seedu.address.model.meeting.exceptions.MeetingNotFoundException;
//import seedu.address.model.person.exceptions.DuplicatePersonException;
//haven implement yet

/**
 * Adds a meeting to the address book.
 */
public class AddMeetingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addmeeting";
    public static final String COMMAND_ALIAS = "am";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a meeting to the address book. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_NAME + "NAME_OF_MEETING "
            + PREFIX_DATE + "DATE_TIME "
            + PREFIX_LOCATION + "LOCATION \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_NAME + "Project Meeting "
            + PREFIX_DATE + "31-10-2017 21:30 "
            + PREFIX_LOCATION + "School of Computing, NUS ";

    public static final String MESSAGE_SUCCESS = "New meeting added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the address book";
    public static final String MESSAGE_OVERDUE_MEETING = "Meeting's date and time is before log in date and time";

    private final Index index;
    private Meeting toAdd;
    private final NameMeeting name;
    private final DateTime date;
    private final Place location;

    /**
     * Creates an AddMeetingCommand to add the specified {@code ReadOnlyMeeting}
     */
    public AddMeetingCommand (NameMeeting name, DateTime date, Place location, Index index) {
        this.index = index;
        this.name = name;
        this.date = date;
        this.location = location;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAdd = lastShownList.get(index.getZeroBased());
        PersonToMeet personName = new PersonToMeet(personToAdd.getName().toString());
        PhoneNum phoneNum = new PhoneNum(personToAdd.getPhone().toString());

        toAdd = new Meeting(name, date, location, personName, phoneNum);
        try {
            model.addMeeting(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateMeetingException e) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        } catch (MeetingBeforeCurrDateException mde) {
            throw new CommandException(MESSAGE_OVERDUE_MEETING);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddMeetingCommand) other).toAdd));
    }



}
