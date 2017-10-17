package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

public class FavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "favourite";
    public static final String COMMAND_ALIAS = "fav";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a person to your favourite contacts "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1 ";

    public static final String MESSAGE_FAVOURITE_PERSON_SUCCESS = "Added person to favourites: %1$s";
    public static final String MESSAGE_ALREADY_FAVOURITE = "Already favourite";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;


    /**
     * @param index of the person in the filtered person list to edit
     */
    public FavouriteCommand(Index index) {
        requireNonNull(index);

        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToFavourite = lastShownList.get(index.getZeroBased());
        if (personToFavourite.getFavourite().checkFavourite()) {

            return new CommandResult(String.format(MESSAGE_ALREADY_FAVOURITE));
        }
        Person favouritedPerson = createFavouritedPerson(personToFavourite);
        try {
            model.updatePerson(personToFavourite, favouritedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_FAVOURITE_PERSON_SUCCESS, favouritedPerson));

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToFavourite}
     */
    private static Person createFavouritedPerson(ReadOnlyPerson personToFavourite) {
        assert personToFavourite != null;

        Name originalName = personToFavourite.getName();
        Phone originalPhone = personToFavourite.getPhone();
        Email originalEmail = personToFavourite.getEmail();
        Address originalAddress = personToFavourite.getAddress();
        Set<Tag> originalTags = personToFavourite.getTags();

        return new Person(originalName, originalPhone, originalEmail, originalAddress,
                originalTags, new Favourite(true));
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
        return index.equals(e.index);
    }

}
