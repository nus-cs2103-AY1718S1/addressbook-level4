package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Updates the profile picture of a Person
 */
public class ChangePictureCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "changepicture";
    public static final String COMMAND_ALIAS = "pic";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Changes the profile picture of the person identified "
            + "by the index number used in the last person listing to the one located at PICTURE_PATH.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + PREFIX_PATH + "PICTURE_PATH\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PATH + "C:\\Users\\User\\Pictures\\pic.jpg";

    public static final String MESSAGE_NOT_IMPLEMENTED_YET = "changePicture command not implemented yet";

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        throw new CommandException(MESSAGE_NOT_IMPLEMENTED_YET);
    }
}
