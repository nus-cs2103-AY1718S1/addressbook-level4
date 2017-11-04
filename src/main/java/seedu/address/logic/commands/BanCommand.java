package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jaivigneshvenugopal
/**
 * Adds a person identified using it's last displayed index into the blacklist.
 */
public class BanCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "ban";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Bans the currently selected person or the person identified by the index number used in the last"
            + " person listing.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_BAN_PERSON_SUCCESS = "%1$s has been added to BLACKLIST";
    public static final String MESSAGE_BAN_PERSON_FAILURE = "%1$s is already in BLACKLIST!";

    private final Index targetIndex;

    public BanCommand() {
        this.targetIndex = null;
    }

    public BanCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String messageToDisplay = MESSAGE_BAN_PERSON_SUCCESS;

        ReadOnlyPerson personToBan = selectPerson(targetIndex);

        if (personToBan.isBlacklisted()) {
            messageToDisplay = MESSAGE_BAN_PERSON_FAILURE;
        } else {
            model.addBlacklistedPerson(personToBan);
        }

        listObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);

        String currentList = listObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(messageToDisplay, personToBan.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BanCommand // instanceof handles nulls
                && ((this.targetIndex == null && ((BanCommand) other).targetIndex == null) // both targetIndex null
                || this.targetIndex.equals(((BanCommand) other).targetIndex))); // state check
    }
}
