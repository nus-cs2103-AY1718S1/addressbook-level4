package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.meeting.exceptions.DuplicateMeetingException;
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
            + "Parameters: "
            + PREFIX_NAME + "NAME "
            + PREFIX_DATE + "DATE "
            + PREFIX_LOCATION + "LOCATION "
            + "Example: " + COMMAND_WORD + " "
            + PREFIX_NAME + "John Doe "
            + PREFIX_DATE + "31-10-2017 21:30 "
            + PREFIX_LOCATION + "School of Computing, NUS ";

    public static final String MESSAGE_SUCCESS = "New meeting added: %1$s";
    public static final String MESSAGE_DUPLICATE_MEETING = "This meeting already exists in the address book";

    private final Meeting toAdd;

    /**
     * Creates an AddMeetingCommand to add the specified {@code ReadOnlyMeeting}
     */
    public AddMeetingCommand(ReadOnlyMeeting meeting) {
        toAdd = new Meeting(meeting);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.addMeeting(toAdd);
            return new CommandResult(String.format(MESSAGE_SUCCESS, toAdd));
        } catch (DuplicateMeetingException e) {
            throw new CommandException(MESSAGE_DUPLICATE_MEETING);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddCommand // instanceof handles nulls
                && toAdd.equals(((AddMeetingCommand) other).toAdd));
    }



}
