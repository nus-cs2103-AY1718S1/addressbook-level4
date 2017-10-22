package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.customField.CustomField;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds or updates the photo of a person identified using it's last displayed index from the address book.
 */
public class UploadPhotoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "upload";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Updates one person's photo identified by the index number used in the last person listing.\n"
            + "Parameters: "
            + "INDEX (must be a positive integer)\n"
            + "photoPath"
            + "Example: " + COMMAND_WORD + " 1" + "/img.png";

    public static final String MESSAGE_UPDATE_PERSON_PHOTO_SUCCESS = "Updated Person: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;

    private final Photo photo;

    public UploadPhotoCommand(Index targetIndex, Photo photo) {
        this.targetIndex = targetIndex;
        this.photo = photo;
    }

    /**
     * Updates a Person's photo
     */
    private Person updatePersonPhoto(ReadOnlyPerson personToUpdatePhoto, Photo photo) {
        Name name = personToUpdatePhoto.getName();
        Phone phone = personToUpdatePhoto.getPhone();
        Email email = personToUpdatePhoto.getEmail();
        Address address = personToUpdatePhoto.getAddress();
        Set<Tag> tags = personToUpdatePhoto.getTags();
        Set<CustomField> customFields = personToUpdatePhoto.getCustomFields();

        Person personUpdated = new Person(name, phone, email, address, photo, tags, customFields);
        return personUpdated;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToUpdatePhoto = lastShownList.get(targetIndex.getZeroBased());

        Person personUpdated = updatePersonPhoto(personToUpdatePhoto, photo);
        Photo img = personUpdated.getPhoto();

        try {
            model.updatePerson(personToUpdatePhoto, personUpdated);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        System.out.println(img.getPathName());
        img.showPhoto();

        return new CommandResult(String.format(MESSAGE_UPDATE_PERSON_PHOTO_SUCCESS, personUpdated));
    }
}
