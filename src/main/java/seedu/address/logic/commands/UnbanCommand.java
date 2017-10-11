package seedu.address.logic.commands;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

import java.util.List;

/**
 * Removes a person identified using it's last displayed index from the blacklist.
 */
public class UnbanCommand extends UndoableCommand{

    public static final String COMMAND_WORD = "unban";
    public static final String COMMAND_WORD_ALIAS = "unban";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unban a person identified by the index number used in the last person listing from blacklist.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_UNBAN_PERSON_SUCCESS = "Removed person from blacklist: %1$s";

    private final Index targetIndex;

    public UnbanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredBlacklistedPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUnban = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.removeBlacklistedPerson(personToUnban);
        } catch (PersonNotFoundException e) {
            assert false : "The target person is not in blacklist";
        }

        return new CommandResult(String.format(MESSAGE_UNBAN_PERSON_SUCCESS, personToUnban));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnbanCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnbanCommand) other).targetIndex)); // state check
    }
}
