package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.Messages.MESSAGE_DUPLICATE_PERSON;
import static seedu.address.commons.core.Messages.MESSAGE_MISSING_PERSON;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

//@@author justintkj
/**
 * Favourites a person identified using it's last displayed index from the address book.
 */
public class FavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favourite";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Favourites/Highlights the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_FAVOURITE_SUCCESS = "Favourite Person: %1$s";

    private final Index targetIndex;
    private final Favourite favourite;

    public FavouriteCommand(Index targetIndex, Favourite favourite) {
        requireNonNull(targetIndex);
        this.targetIndex = targetIndex;
        this.favourite = favourite;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        CommandUtil.checksIndexSmallerThanList(lastShownList, targetIndex);

        ReadOnlyPerson personToEdit = lastShownList.get(targetIndex.getZeroBased());
        ReadOnlyPerson editedPerson = personToEdit;
        toggleColor(editedPerson);
        editedPerson.setFavourite(favourite);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError(MESSAGE_MISSING_PERSON);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_FAVOURITE_SUCCESS, editedPerson));

    }

    /**
     * Toggles the current color state of the person selected
     *
     * @param editedPerson selected person to edit
     */
    private void toggleColor(ReadOnlyPerson editedPerson) {
        if (editedPerson.getFavourite().toString().equals(Favourite.COLOR_SWITCH)) {
            favourite.inverse();
        }
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof FavouriteCommand)) {
            return false;
        }

        // state check
        FavouriteCommand e = (FavouriteCommand) other;
        return targetIndex.equals(e.targetIndex) && favourite.equals(e.favourite);
    }
}
