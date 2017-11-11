package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.ListObserver;
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
    public static final String MESSAGE_BAN_BLACKLISTED_PERSON_FAILURE = "%1$s is already in BLACKLIST!";

    private final ReadOnlyPerson personToBan;

    public BanCommand() throws CommandException {
        personToBan = selectPersonForCommand();
    }

    public BanCommand(Index targetIndex) throws CommandException {
        personToBan = selectPersonForCommand(targetIndex);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String messageToDisplay = MESSAGE_BAN_PERSON_SUCCESS;
        ReadOnlyPerson targetPerson = personToBan;

        if (personToBan.isBlacklisted()) {
            messageToDisplay = MESSAGE_BAN_BLACKLISTED_PERSON_FAILURE;
        } else {
            targetPerson = model.addBlacklistedPerson(personToBan);
        }

        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);
        reselectPerson(targetPerson);

        String currentList = ListObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(messageToDisplay, personToBan.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BanCommand // instanceof handles nulls
                && this.personToBan.equals(((BanCommand) other).personToBan)); // state check
    }
}
