package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import java.io.IOException;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.storage.PhotoStorage;

/** 
 * Adds a photo to the specified contact. 
 * Incorrect formats of AddPhotoCommand will throw different exceptions.
 * */
public class AddPhotoCommand extends Command {
    public static final String COMMAND_WORD = "addphoto";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds a photo to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_FILEPATH + " (must be valid filepath)\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_FILEPATH + " C:/users/Desktop/imageFolder/Books";

    public static final String MESSAGE_ADD_PHOTO_SUCCESS = "New photo added to: %1$s";
    public static final String MESSAGE_DELETE_PHOTO_SUCCESS = "Removed photo from Person: %1$s";
    public static final String MESSAGE_NO_PHOTO_TO_DELETE = "This person has no photo to delete.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in PEERSONAL.";
    private final Index index;
    private final Photo photo;
    public AddPhotoCommand(Index index, Photo photo) {
        requireNonNull(index);
        requireNonNull(photo);
        this.index = index;
        this.photo = photo;
    }
    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        if(personToEdit.getPhoto().getFilePath().isEmpty() && photo.getFilePath().isEmpty()) {
            throw new CommandException(MESSAGE_NO_PHOTO_TO_DELETE);
        }
        else if(!photo.getFilePath().equals("")) {
            try {
                //produces a new filepath and rewrites the new filepath to the photo object held by the contact
                PhotoStorage rewrite = new PhotoStorage(photo.getFilePath(), personToEdit.getName().hashCode());
                photo.resetFilePath(rewrite.setNewFilePath());
            } catch (IOException e) {
                throw new CommandException(e.getMessage());
            }
        }
        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), personToEdit.getBirthday(),
                personToEdit.getAge(), photo,
                personToEdit.getTags());
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException fpe) {
            throw new AssertionError("The target person cannot be missing.");
        }
        model.getFilteredPersonList();
        return new CommandResult(generateSuccessMessage(editedPerson));
    }
    /**Generate success message*/
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!photo.getFilePath().isEmpty()) {
            return String.format(MESSAGE_ADD_PHOTO_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_PHOTO_SUCCESS, personToEdit);
        }
    }



}
