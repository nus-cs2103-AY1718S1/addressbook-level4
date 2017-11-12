//@@author arturs68
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import java.io.File;
import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Updates the profile picture of a Person
 */
public class ChangePicCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "changepic";
    public static final String COMMAND_ALIAS = "pic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the profile picture of the person identified "
            + "by the index number used in the last person listing to the one located at PICTURE_PATH.\n"
            + "To reset to default picture, choose 'default_pic.png' as the path"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PATH + "PICTURE_PATH\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PATH + "C:\\Users\\User\\Pictures\\pic.jpg";

    public static final String MESSAGE_CHANGEPIC_SUCCESS = "Changed the picture of the Person: %1$s";

    private final Index index;
    private final String picturePath;

    /**
     * @param index of the person in the filtered person list to change picture
     * @param picturePath of the picture to be loaded
     */
    public ChangePicCommand(Index index, String picturePath) {
        requireNonNull(index);
        requireNonNull(picturePath);

        this.index = index;
        this.picturePath = picturePath;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (picturePath.contains("/") || picturePath.contains("\\")) {
            File file = new File(picturePath);
            if (!file.exists()) {
                throw new CommandException("No file found in the path: " + picturePath);
            }
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        Person editedPerson;
        try {
            editedPerson = new Person(personToEdit);
            editedPerson.setProfilePicture(new ProfilePicture(picturePath));
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException("The person cannot be duplicated when changing the profile picture");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
        model.updateFilteredPersonList(p ->true);
        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        return String.format(MESSAGE_CHANGEPIC_SUCCESS, personToEdit.getName());
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ChangePicCommand)) {
            return false;
        }

        // state check
        ChangePicCommand e = (ChangePicCommand) other;
        return index.equals(e.index)
                && picturePath.equals(e.picturePath);
    }
}
