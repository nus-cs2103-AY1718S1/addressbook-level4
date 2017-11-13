package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfPic;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.schedule.Schedule;
import seedu.address.model.tag.Tag;

//@@author nassy93
/**
 * Sets Favourite attribute of Indexed person as false in the address book. (remove from favourites)
 */
public class RemoveFavouriteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "fremove";
    public static final String COMMAND_ALT = "fr";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the favourite status from the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SUCCESS = "%1$s has been removed from favourites.";
    public static final String MESSAGE_ALREADY_NORMAL = "This person is not a favourite.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    public RemoveFavouriteCommand(Index index) {
        requireNonNull(index);
        this.targetIndex = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(targetIndex.getZeroBased());

        if (!personToEdit.getFavourite().getStatus()) {
            throw new CommandException(MESSAGE_ALREADY_NORMAL);
        }

        Person editedPerson = removeFavePerson(personToEdit);

        try {
            model.updateFavouritePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the the Favourite attribute set to true.
     */
    private static Person removeFavePerson(ReadOnlyPerson personToEdit) {
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        ProfPic updatedProfPic = personToEdit.getProfPic();
        Favourite updatedFavourite = new Favourite(false);
        Set<Tag> updatedTags = personToEdit.getTags();
        Set<Group> updatedGroups = personToEdit.getGroups();
        Set<Schedule> updatedSchedule = personToEdit.getSchedule();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedFavourite,
                updatedProfPic, updatedTags, updatedGroups, updatedSchedule);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveFavouriteCommand // instanceof handles nulls
                && this.targetIndex.equals(((RemoveFavouriteCommand) other).targetIndex)); // state check
    }
}
