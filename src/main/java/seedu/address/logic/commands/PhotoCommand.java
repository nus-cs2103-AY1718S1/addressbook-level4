package seedu.address.logic.commands;
//@@author RonakLakhotia
import java.io.IOException;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.FileImage;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Adds a photo of the person to the addressBook
 */

public class PhotoCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "photo";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds a photo of the person to the addressBook\n"
            + "Parameters: INDEX (must be a positive integer) FILE_PATH\n"
            + "File Path must be Valid\n"
            + "Example: " + COMMAND_WORD + " 1" + " /Users/ronaklakhotia/Desktop/Ronak.jpeg";

    public static final String DELETE_SUCCESS = "Deleted photo of Person: %1$s";
    public static final String MESSAGE_PHOTO_PERSON_SUCCESS = "Added Photo to Person: %1$s";

    public static final String MESSAGE_FILE_PATH_NOT_FOUND = "Incorrect file path";

    private final Index targetIndex;
    private  String filePath;

    public PhotoCommand(Index targetIndex, String filePath) {
        this.targetIndex = targetIndex;
        this.filePath = filePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToAddPhoto = lastShownList.get(targetIndex.getZeroBased());

        if (personToAddPhoto.getImage().getFilePath().equals("") && filePath.equalsIgnoreCase("delete")) {
            throw new CommandException(Messages.MESSAGE_NO_IMAGE_TO_DELETE);
        }
        if (filePath.equalsIgnoreCase("Delete")) {
            filePath = "";
        }

        try {
            Person editedPerson = new Person(personToAddPhoto.getName(), personToAddPhoto.getPhone(),
                    personToAddPhoto.getEmail(),
                    personToAddPhoto.getAddress(), personToAddPhoto.getDateOfBirth(), personToAddPhoto.getRemark(),
                    new FileImage(filePath), personToAddPhoto.getUsername(), personToAddPhoto.getTags());

            model.addPhotoPerson(personToAddPhoto, filePath, targetIndex);
            model.updatePerson(personToAddPhoto, editedPerson);


        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        } catch (IOException ioe) {
            assert false : "Correct input required";
        } catch (IllegalValueException ive) {
            assert false : "Invalid input";
        }
        if (filePath.equals("")) {
            return new CommandResult(String.format(DELETE_SUCCESS, personToAddPhoto));
        } else {
            return new CommandResult(String.format(MESSAGE_PHOTO_PERSON_SUCCESS, personToAddPhoto));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PhotoCommand // instanceof handles nulls
                && this.targetIndex.equals(((PhotoCommand) other).targetIndex)); // state check
    }
}


