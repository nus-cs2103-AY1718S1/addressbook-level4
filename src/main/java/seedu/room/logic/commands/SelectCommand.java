package seedu.room.logic.commands;

import java.util.List;

import seedu.room.commons.core.EventsCenter;
import seedu.room.commons.core.Messages;
import seedu.room.commons.core.index.Index;
import seedu.room.commons.events.ui.JumpToListRequestEvent;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.person.ReadOnlyPerson;

/**
 * Selects a person identified using it's last displayed index from the resident book.
 */
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";

    private final Index targetIndex;

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
