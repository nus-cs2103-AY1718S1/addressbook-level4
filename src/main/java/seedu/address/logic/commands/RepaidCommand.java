package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author jaivigneshvenugopal
/**
 * Adds a person identified using it's last displayed index into the whitelist.
 * Resets person's debt to zero and sets the date repaid field.
 */
public class RepaidCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "repaid";
    public static final String COMMAND_WORD_ALIAS = "rp";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the currently selected person or the person identified by the index number into the whitelist"
            + " and concurrently clear his/her debt.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_REPAID_PERSON_SUCCESS = "%1$s has now repaid his/her debt";
    public static final String MESSAGE_REPAID_PERSON_FAILURE = "%1$s has already repaid debt!";

    private final ReadOnlyPerson personToWhitelist;

    public RepaidCommand() throws CommandException {
        personToWhitelist = selectPersonForCommand();
    }

    public RepaidCommand(Index targetIndex) throws CommandException {
        personToWhitelist = selectPersonForCommand(targetIndex);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String messageToDisplay = MESSAGE_REPAID_PERSON_SUCCESS;
        ReadOnlyPerson targetPerson = personToWhitelist;

        if (personToWhitelist.getDebt().toNumber() == 0) {
            messageToDisplay = MESSAGE_REPAID_PERSON_FAILURE;
        } else {
            targetPerson = model.addWhitelistedPerson(personToWhitelist);
        }

        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);
        reselectPerson(targetPerson);

        String currentList = ListObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(messageToDisplay, targetPerson.getName()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RepaidCommand // instanceof handles nulls
                && this.personToWhitelist.equals(((RepaidCommand) other).personToWhitelist)); // state check
    }
}
