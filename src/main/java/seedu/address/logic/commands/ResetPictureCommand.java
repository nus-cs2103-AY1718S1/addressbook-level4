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
import seedu.address.model.person.ProfPic;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Resets ProfPic attribute of Indexed person back to default profile picture in the address book.
 */
public class ResetPictureCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "ppreset";
    public static final String COMMAND_ALT = "ppr";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Resets the profile picture of the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_RESETPICTURE_PERSON_SUCCESS = "%1$s's profile picture reset to default .";
    public static final String MESSAGE_ALREADY_DEFAULT = "This person's profile picture is already the default.";

    private final Index targetIndex;

    public ResetPictureCommand(Index index) {
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
        Person editedPerson = resetProfPicPerson(personToEdit);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_ALREADY_DEFAULT);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person must exist");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_RESETPICTURE_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the the ProfPic attribute set to the default picture's path.
     */
    private static Person resetProfPicPerson(ReadOnlyPerson personToEdit) {
        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        ProfPic updatedProfPic = new ProfPic("maleicon.png");
        Favourite updatedFavourite = personToEdit.getFavourite();
        Set<Tag> updatedTags = personToEdit.getTags();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedFavourite,
                updatedProfPic, updatedTags);
    }
}
