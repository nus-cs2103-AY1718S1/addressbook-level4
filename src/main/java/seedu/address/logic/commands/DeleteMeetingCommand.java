package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.meeting.Meeting;

//@@author alexanderleegs
/**
 * Deletes a meeting from an existing person in the address book.
 */
public class DeleteMeetingCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletemeeting";
    public static final String COMMAND_ALIAS = "dm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Deletes a meeting from the meeting list identified "
            + "by the index number used in the last meeting listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_DELETE_MEETING_SUCCESS = "Deleted Meeting: %1$s";

    private final Index targetIndex;

    /**
     * @param index of the person in the filtered person list to edit
     */
    public DeleteMeetingCommand(Index index) {
        requireNonNull(index);

        this.targetIndex = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<Meeting> lastShownList = model.getFilteredMeetingList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        Meeting meetingToDelete = lastShownList.get(targetIndex.getZeroBased());

        model.deleteMeeting(meetingToDelete);

        return new CommandResult(String.format(MESSAGE_DELETE_MEETING_SUCCESS, meetingToDelete.meetingName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteMeetingCommand)) {
            return false;
        }

        // state check
        DeleteMeetingCommand toCompare = (DeleteMeetingCommand) other;
        return targetIndex.equals(toCompare.targetIndex);
    }
}
