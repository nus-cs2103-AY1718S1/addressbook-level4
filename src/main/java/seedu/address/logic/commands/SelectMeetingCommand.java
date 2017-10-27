package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.meeting.ReadOnlyMeeting;

/**
 * Selects a meeting identified using it's last displayed index from the address book.
 */
public class SelectMeetingCommand extends Command {

    public static final String COMMAND_WORD = "selectmeeting";
    public static final String COMMAND_ALIAS = "sm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the meeting identified by the index number used in the last meeting listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_MEETING_SUCCESS = "Selected Meeting: %1$s";

    private final Index targetMeetingIndex;

    public SelectMeetingCommand(Index targetIndex) {
        this.targetMeetingIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyMeeting> lastShownMeetingList = model.getFilteredMeetingList();

        if (targetMeetingIndex.getZeroBased() >= lastShownMeetingList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_MEETING_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetMeetingIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_MEETING_SUCCESS, targetMeetingIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectMeetingCommand // instanceof handles nulls
                && this.targetMeetingIndex.equals(((SelectMeetingCommand) other).targetMeetingIndex)); // state check
    }
}
