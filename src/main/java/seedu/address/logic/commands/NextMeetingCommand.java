package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Meeting;
import seedu.address.model.person.InternalId;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Lists all persons in the address book to the user.
 */
public class NextMeetingCommand extends Command {

    public static final String COMMAND_WORD = "nextMeeting";
    public static final String COMMAND_ALIAS = "nm";

    public static final String MESSAGE_SUCCESS = "Displays the upcoming meeting";
    public static final String MESSAGE_INVALID_PARTICIPANT = "Meeting involves person not present in address book";
    public static final String MESSAGE_NO_UPCOMING_MEETINGS = "No upcoming meetings";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD;

    @Override
    public CommandResult execute() throws CommandException {
        Meeting nextMeeting = model.getMeetingList().getUpcomingMeeting();
        if (nextMeeting == null) {
            return new CommandResult(MESSAGE_NO_UPCOMING_MEETINGS);
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Upcoming meeting with ");
        try {
            for (InternalId id : nextMeeting.getListOfPersonsId()) {
                sb.append(model.getAddressBook().getPersonByInternalIndex(id.getId()).getName().fullName);
                sb.append(", ");
            }
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_INVALID_PARTICIPANT);
        }
        sb.delete(sb.length() - 2, sb.length());
        sb.append('\n');

        return new CommandResult("Next upcoming meeting: " + sb.toString() + nextMeeting.toString());
    }
}




