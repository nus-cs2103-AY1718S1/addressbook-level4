package seedu.address.logic.commands;

//@@author itsdickson

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Unfavourites a person identified using it's last displayed index from the address book.
 */
public class UnfavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "unfavourite";
    public static final String COMMAND_ALIAS = "unfav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Unfavourites the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_UNFAVOURITE_PERSON_SUCCESS = "Unfavourited Person: %1$s";
    public static final String MESSAGE_UNFAVOURITE_PERSON_FAIL = "Person is not favourited";

    private final Index targetIndex;

    public UnfavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUnfavourite = lastShownList.get(targetIndex.getZeroBased());

        if (personToUnfavourite.getFavourite().equals(false)) {
            throw new CommandException(MESSAGE_UNFAVOURITE_PERSON_FAIL);
        }

        try {
            model.unfavouritePerson(personToUnfavourite);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_UNFAVOURITE_PERSON_SUCCESS, personToUnfavourite));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UnfavouriteCommand // instanceof handles nulls
                && this.targetIndex.equals(((UnfavouriteCommand) other).targetIndex)); // state check
    }
}
//@@author
