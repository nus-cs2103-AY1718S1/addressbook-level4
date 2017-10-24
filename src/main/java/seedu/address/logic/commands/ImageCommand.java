package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_IMAGE;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Image;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * This command is used to add, edit or remove images to persons in the address book
 */

public class ImageCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "image";
    public static final String SAMPLE_COMMAND = COMMAND_WORD + " 1 " + PREFIX_IMAGE + "Likes to code.";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds/updates the image of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing image will be updated by the input.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_IMAGE + "[FILEPATH]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_IMAGE + "C:\\Users\\Admin\\Desktop\\pic.jpg.";

    public static final String MESSAGE_ADD_IMAGE_SUCCESS = "Added image for person: %1$s";;
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

    //todo
    protected CommandResult executeUndoableCommand() throws CommandException {
        return null;
    }

    /**
     * Generates success messages
     * @param personToEdit
     * @return Message and the person
     */
    private String generateSuccessMessage(ReadOnlyPerson personToEdit) {
        if (!image.path.isEmpty()) {
            return String.format(MESSAGE_ADD_IMAGE_SUCCESS, personToEdit);
        } else {
            return String.format(MESSAGE_DELETE_IMAGE, personToEdit);
        }
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

