package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;

/**
 * 1. Adds a person identified using it's last displayed index into the whitelist.
 * 2. Resets the person's debt to zero.
 */
public class RepaidCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "repaid";
    public static final String COMMAND_WORD_ALIAS = "rp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds person identified by the index number into the whitelist and concurrently clear his debt.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_WHITELIST_PERSON_SUCCESS = "Added person to whitelist: %1$s";

    private final Index targetIndex;

    public RepaidCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToWhitelist = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.addWhitelistedPerson(personToWhitelist);
        } catch (DuplicatePersonException e) {
            assert false : "The target person is already in whitelist";
        }

        return new CommandResult(String.format(MESSAGE_WHITELIST_PERSON_SUCCESS, personToWhitelist));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RepaidCommand // instanceof handles nulls
                && this.targetIndex.equals(((RepaidCommand) other).targetIndex)); // state check
    }
}
