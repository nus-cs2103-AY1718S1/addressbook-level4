package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.address.commons.events.ui.JumpToPersonListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_ALT = "sel";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + ": Specify prefix g/ to select a group by its index number.\n"
            + "Parameters: [g/]INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";
    public static final String MESSAGE_SELECT_GROUP_SUCCESS = "Selected Group: %1$s";

    private final Index targetIndex;
    private final boolean isGroup;

    public SelectCommand(Index targetIndex, boolean isGroup) {
        this.targetIndex = targetIndex;
        this.isGroup = isGroup;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        List<ReadOnlyGroup> lastShownGroupList = model.getFilteredGroupList();

        if (isGroup) {
            if (targetIndex.getZeroBased() >= lastShownGroupList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_GROUP_DISPLAYED_INDEX);
            }
            EventsCenter.getInstance().post(new JumpToGroupListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SELECT_GROUP_SUCCESS, targetIndex.getOneBased()));
        } else {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            EventsCenter.getInstance().post(new JumpToPersonListRequestEvent(targetIndex));
            return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));
        }

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCommand) other).targetIndex))
                && this.isGroup == ((SelectCommand) other).isGroup; // state check
    }
}
