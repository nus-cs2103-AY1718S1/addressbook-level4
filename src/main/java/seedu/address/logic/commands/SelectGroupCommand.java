package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.ReadOnlyGroup;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class SelectGroupCommand extends Command {

    public static final String COMMAND_WORD = "selectgroup";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the group identified by the index number used in the group listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_GROUP_SUCCESS = "Selected Group: %1$s";

    private final Index targetIndex;

    public SelectGroupCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyGroup> lastShownList = model.getGroupList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToGroupListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_GROUP_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectGroupCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectGroupCommand) other).targetIndex)); // state check
    }
}
