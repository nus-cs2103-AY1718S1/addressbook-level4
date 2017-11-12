package seedu.address.logic.commands.person;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.NameIsPrivatePredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Reverts hide command on a person identified using it's hidden list index from the address book.
 */
public class UnhideCommand extends Command {

    public static final String COMMAND_WORD = "unhide";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Reverts hide command on the person identified by the index number in the hidden person(s) listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNHIDE_PERSON_SUCCESS = "Unhidden Person: %1$s";

    private final Index targetIndex;
    private NameIsPrivatePredicate predicate = new NameIsPrivatePredicate(true);

    public UnhideCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }


        ReadOnlyPerson personToUnhide = lastShownList.get(targetIndex.getZeroBased());

        if (!personToUnhide.isPrivate()) {
            throw new CommandException(Messages.MESSAGE_PERSON_ALREADY_UNHIDDEN);
        }

        try {
            model.unhidePerson(personToUnhide);
            model.updateFilteredPersonList(predicate);
            model.updateFilteredPersonList(Model.PREDICATE_SHOW_ONLY_HIDDEN);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_UNHIDE_PERSON_SUCCESS, personToUnhide));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnhideCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnhideCommand) other).targetIndex)); // state check
    }
}
