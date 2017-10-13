package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.FavouriteStatus;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Person;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Deletes a person identified using it's last displayed index from the address book.
 */
public class FavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favourite";
    public static final String COMMAND_ALIAS = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites/Unfavourites the person identified by the index number used in the last person listing if he is currently unfavourited/favourited.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVOURITED_PERSON = "Favourited Person: %1$s";
    public static final String MESSAGE_UNFAVOURITED_PERSON = "Unfavourited Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    public FavouriteCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToToggleFavourite = lastShownList.get(targetIndex.getZeroBased());
        boolean newFavouriteStatus = !personToToggleFavourite.getStatus();
        Person favouriteToggledPerson = new Person(personToToggleFavourite.getName(), personToToggleFavourite.getPhone(), personToToggleFavourite.getEmail(),
                personToToggleFavourite.getAddress(), personToToggleFavourite.getRemark(), new FavouriteStatus(newFavouriteStatus), personToToggleFavourite.getTags());

        try {
            model.updatePerson(personToToggleFavourite, favouriteToggledPerson);
        } catch (DuplicatePersonException dpe) { 
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(generateSuccessMessage(favouriteToggledPerson));
    }

    private String generateSuccessMessage(Person favouriteToggledPerson) {
        if (favouriteToggledPerson.getStatus() == true) {
            return String.format(MESSAGE_FAVOURITED_PERSON, favouriteToggledPerson);
        } else {
            return String.format(MESSAGE_UNFAVOURITED_PERSON, favouriteToggledPerson);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavouriteCommand // instanceof handles nulls
                && this.targetIndex.equals(((FavouriteCommand) other).targetIndex)); // state check
    }
}
