package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GROUP_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Updates the profile picture of a Person
 */
public class ChangePicCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "changepic";
    public static final String COMMAND_ALIAS = "pic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the profile picture of the person identified "
            + "by the index number used in the last person listing to the one located at PICTURE_PATH.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PATH + "PICTURE_PATH\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PATH + "C:\\Users\\User\\Pictures\\pic.jpg";

    public static final String MESSAGE_ARGUMENTS = "Index: %1$d, Picture path: %2$s";

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
        throw new CommandException(String.format(MESSAGE_ARGUMENTS, index.getOneBased(), picturePath));
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
