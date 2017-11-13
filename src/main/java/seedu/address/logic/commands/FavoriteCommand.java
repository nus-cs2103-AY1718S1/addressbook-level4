//@@author hxy0229
package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Favorite;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Favorites a person identified using it's last displayed index from the address book.
 */
public class FavoriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Favorite the person you are interested in!\n"
            + "Example: " + COMMAND_WORD  + "1";

    public static final String MESSAGE_FAVORITE_COMMAND_SUCCESS = "Favorited Person: ";

    public static final String MESSAGE_FAVORITING_FAVORITED_PERSON = " has already been favorited!";

    private final Index targetIndex;

    private final Favorite favorite;

    public FavoriteCommand(Index targetIndex, boolean favorite) {
        this.targetIndex = targetIndex;
        this.favorite = new Favorite(favorite);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToFavorite = lastShownList.get(targetIndex.getZeroBased());
        if (personToFavorite.getFavorite().value == true) {
            return new CommandResult(personToFavorite.getName().fullName
                    + new String(MESSAGE_FAVORITING_FAVORITED_PERSON));
        }
        Person editedPerson = new Person(personToFavorite.getName(), personToFavorite.getPhone(),
                personToFavorite.getEmail(), personToFavorite.getBirthday(), personToFavorite.getAddress(),
                personToFavorite.getProfilePage(), favorite, personToFavorite.getTags());

        try {
            model.updatePerson(personToFavorite, editedPerson);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (DuplicatePersonException e) {
            assert false : "This Person Cannot be duplicate";
        }

        return new CommandResult(new String(MESSAGE_FAVORITE_COMMAND_SUCCESS + personToFavorite.getName().fullName));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof FavoriteCommand // instanceof handles nulls
                && this.targetIndex.equals(((FavoriteCommand) other).targetIndex)); // state check
    }

}
//@@author
