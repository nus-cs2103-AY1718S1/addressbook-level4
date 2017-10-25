package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.ImageUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Image;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * This command is used to add, edit or remove images to persons in the address book
 */

public class ImageCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "image";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/updates the image of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing image will be updated by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_IMAGE + "[FILEPATH]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_IMAGE + "C:\\Users\\Admin\\Desktop\\pic.jpg.";

    public static final String MESSAGE_IMAGE_PATH_FAIL =
            "This specified path cannot be read.";

    public static final String MESSAGE_ADD_IMAGE_SUCCESS = "Added image for person: %1$s";
    public static final String MESSAGE_DELETE_IMAGE = "Deleted image from person:  %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists";

    private Index index;
    private Image image;

    public ImageCommand(Index index, Image image) {
        requireNonNull(index);
        requireNonNull(image);

        this.image = image;
        this.index = index;
    }

    /**
     * Executes the command
     * @return a success message with the updated user's name
     * @throws CommandException
     */
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        if (image.getPath().equalsIgnoreCase("")) {
            image.setPath("");

            Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                    personToEdit.getAddress(), personToEdit.getTags(), personToEdit.getRemark(), image);

            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredListToShowAll();

            return new CommandResult(generateSuccessMessage(editedPerson));
        }

        try {
            ImageUtil imageUtil = new ImageUtil();
            image.setPath(imageUtil.run(image.getPath(), personToEdit.getEmail().hashCode()));
        } catch (Exception e) {
            image.setPath("");
            return new CommandResult(generateFailureMessage());
        }

        Person editedPerson = new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getTags(),
                personToEdit.getRemark(), image);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredListToShowAll();

        return new CommandResult(generateSuccessMessage(editedPerson));
    }

    /**
     * Generates success messages
     * @param personToEdit
     * @return Message and the person
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!image.getPath().isEmpty()) {
            return String.format(MESSAGE_ADD_IMAGE_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_IMAGE, personToEdit);
        }
    }

    /**
     * Generates failure messages
     * @return Message
     */
    private String generateFailureMessage() {
        return MESSAGE_IMAGE_PATH_FAIL;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ImageCommand)) {
            return false;
        }

        // state check
        ImageCommand e = (ImageCommand) other;
        return index.equals(e.index)
                && image.equals(e.image);
    }
}

