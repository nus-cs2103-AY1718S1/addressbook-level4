package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;



/**
 * Adds a person identified using it's last displayed index into the blacklist.
 */
public class BanCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "ban";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Ban a person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_BAN_PERSON_SUCCESS = "Added person to blacklist: %1$s";
    public static final String MESSAGE_BAN_PERSON_FAILURE = "Person is already in blacklist!";

    private final Index targetIndex;

    public BanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        String messagetoDisplay = MESSAGE_BAN_PERSON_SUCCESS;
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToBan = lastShownList.get(targetIndex.getZeroBased());

        if (personToBan.isBlacklisted()) {
            messagetoDisplay = MESSAGE_BAN_PERSON_FAILURE;
        } else {
            model.addBlacklistedPerson(personToBan);
        }

        return new CommandResult(String.format(messagetoDisplay, personToBan));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BanCommand // instanceof handles nulls
                && this.targetIndex.equals(((BanCommand) other).targetIndex)); // state check
    }
}
