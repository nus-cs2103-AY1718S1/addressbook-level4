package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;


/**
 * Removes a person identified using it's last displayed index from the blacklist.
 */
public class UnbanCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unban";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unban a person identified by the index number used in the last person listing from blacklist.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_UNBAN_PERSON_SUCCESS = "Removed %1$s from BLACKLIST";
    public static final String MESSAGE_UNBAN_PERSON_FAILURE = "%1$s is not BLACKLISTED!";

    private final Index targetIndex;

    public UnbanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        String messagetoDisplay = MESSAGE_UNBAN_PERSON_SUCCESS;
        List<ReadOnlyPerson> lastShownList = listObserver.getCurrentFilteredList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUnban = lastShownList.get(targetIndex.getZeroBased());

        try {
            if (personToUnban.isBlacklisted()) {
                model.removeBlacklistedPerson(personToUnban);
            } else {
                messagetoDisplay = MESSAGE_UNBAN_PERSON_FAILURE;
            }
        } catch (PersonNotFoundException e) {
            assert false : "The target person is not in blacklist";
        }

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);

        String currentList = listObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(messagetoDisplay, personToUnban.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnbanCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnbanCommand) other).targetIndex)); // state check
    }
}
