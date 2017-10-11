package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.NameIsPrivatePredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Hides a person identified using it's last displayed index from the address book.
 */
public class HideCommand extends Command {

    public static final String COMMAND_WORD = "hide";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Hides the person identified by the index number in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD  + " 1";

    public static final String MESSAGE_HIDE_PERSON_SUCCESS = "Hidden Person: %1$s";

    private final Index targetIndex;
    private NameIsPrivatePredicate predicate = new NameIsPrivatePredicate(true);

    public HideCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToHide = lastShownList.get(targetIndex.getZeroBased());

        try {
            model.hidePerson(personToHide);
            model.updateFilteredPersonList(predicate);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_HIDE_PERSON_SUCCESS, personToHide));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof HideCommand // instanceof handles nulls
                && this.targetIndex.equals(((HideCommand) other).targetIndex)); // state check
    }
}
