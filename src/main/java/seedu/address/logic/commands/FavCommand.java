package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.ChannelId;
import seedu.address.model.person.Email;
import seedu.address.model.person.Favourite;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author six3oo
/**
 * Adds a person identified using it's last displayed index from the address book to the favourites list.
 */

public class FavCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "fave";
    public static final String COMMAND_ALIAS = "fv";
    public static final String COMMAND_HELP = "fave INDEX BOOLEAN";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds or removes the person identified by the index number used in the last person listing to a "
            + "favourites list.\n"
            + "Parameters: INDEX (must be a positive integer), STATUS (must be a boolean variable, e.g. 'true' or "
            + "'false')\n"
            + "Example: " + COMMAND_WORD + " 1 true";

    public static final String MESSAGE_FAVE_PERSON_SUCCESS = "Added Person to Favourites: %1$s";
    public static final String MESSAGE_UNFAVE_PERSON_SUCCESS = "Removed Person from Favourites: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person is already in the favourites list.";

    private final Index index;
    private final String faveString;

    /**
     * @param index of the person in the filtered person list to edit
     * @param faveState expression of the person's new favourites state
     */
    public FavCommand(Index index, String faveState) {
        requireNonNull(index);
        requireNonNull(faveState);
        this.index = index;
        this.faveString = faveState;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, faveString);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (faveString.equals("true")) {
            return new CommandResult(String.format(MESSAGE_FAVE_PERSON_SUCCESS, editedPerson));
        } else {
            return new CommandResult(String.format(MESSAGE_UNFAVE_PERSON_SUCCESS, editedPerson));
        }
    }

    /**
     * Creates and returns a {@code Person} with the updated faveState.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             String faveString) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        ChannelId updatedChannelId = personToEdit.getChannelId();
        Set<Tag> updatedTags = personToEdit.getTags();
        Favourite updatedFavs = new Favourite(faveString);

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedChannelId, updatedTags,
                updatedFavs);
    }
}
