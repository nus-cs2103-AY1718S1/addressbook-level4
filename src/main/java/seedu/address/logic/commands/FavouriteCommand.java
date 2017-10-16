package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Mark a person in the contact as favourite
 */
public class FavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD_1 = "favourite";
    public static final String COMMAND_WORD_2 = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD_1 + ": Mark the person as favourite "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD_1
            + "OR "
            + COMMAND_WORD_2 + " 1 ";

    public static final String MESSAGE_ARGUMENTS = "INDEX: %1$d";
    public static final String MESSAGE_FAVOURITE_SUCCESS = "Favourite Person: %1$s";
    public static final String MESSAGE_DEFAVOURITE_SUCCESS = "Remove Person from Favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private boolean changedToFav;

    /**
     * @param index of the person in the filtered person list to mark as favourite
     */
    public FavouriteCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Favourite favourite = personToEdit.getFavourite();
        favourite.setFavourite();
        if (favourite.getStatus().equals("True")) {
            changedToFav = true;
        } else {
            changedToFav = false;
        }
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), favourite, personToEdit.getTags());

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        if (changedToFav) {
            return new CommandResult(generateFavouriteSuccessMessage(editedPerson));
        } else {
            return new CommandResult(generateDeFavouriteSuccessMessage(editedPerson));
        }
    }

    private String generateFavouriteSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_FAVOURITE_SUCCESS, personToEdit);
    }

    private String generateDeFavouriteSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_DEFAVOURITE_SUCCESS, personToEdit);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if they are the same object
        if (other == this) {
            return true;
        }

        // instanceof handles null
        if (!(other instanceof FavouriteCommand)) {
            return false;
        }

        // state check
        FavouriteCommand e = (FavouriteCommand) other;
        return index.equals(e.index);
    }

}
