package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.meeting.exceptions.MeetingNotFoundException;

/**
 * Deletes a meeting in the address book
 */
public class DeleteMeetingCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletemeeting";
    public static final String COMMAND_ALIAS = "dm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the meeting identified by the index number used in the meeting list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DELETE_MEETING_SUCCESS = "Delete meeting: %1$s";

    private final Index targetIndex;

    public DeleteMeetingCommand(Index targetIndex) { this.targetIndex = targetIndex; }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyMeeting> lastShownList = model.getFilteredMeetingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        ReadOnlyMeeting meetingToDelete = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.deleteMeeting(meetingToDelete);
        } catch (MeetingNotFoundException pnfe) {
            assert false : "The target meeting cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_SUCCESS, meetingToDelete));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof DeleteCommand // instanceof handles nulls
                && this.targetIndex.equals(((DeleteMeetingCommand) other).targetIndex)); // state check
    }
}
