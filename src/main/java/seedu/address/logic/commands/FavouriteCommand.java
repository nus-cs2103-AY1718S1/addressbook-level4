package seedu.address.logic.commands;

//@@author itsdickson

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Favourites a person identified using it's last displayed index from the address book.
 */
public class FavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favourite";
    public static final String COMMAND_ALIAS = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVOURITE_PERSON_SUCCESS = "Favourited Person: %1$s";
    public static final String MESSAGE_FAVOURITE_PERSON_FAIL = "Person is already favourited";

    private final Index targetIndex;

    public FavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToFavourite = lastShownList.get(targetIndex.getZeroBased());

        if (personToFavourite.getFavourite().equals(true)) {
            throw new CommandException(MESSAGE_FAVOURITE_PERSON_FAIL);
        }

        try {
            model.favouritePerson(personToFavourite);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_FAVOURITE_PERSON_SUCCESS, personToFavourite));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteCommand // instanceof handles nulls
                && this.targetIndex.equals(((FavouriteCommand) other).targetIndex)); // state check
    }
}
//@@author
