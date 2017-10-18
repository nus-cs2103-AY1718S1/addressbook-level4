package seedu.address.logic.commands;

import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;

import seedu.address.commons.core.index.Index;

/***/
public class AddPhotoCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "addPhoto";
    public static final String MESSAGE_SUCCESS = "New photo added to: %1$s";
    public static final String MESSAGE_EXISTING_PHOTO = "There is already a photo tied to this contact";
    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": adds a photo to the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_FILEPATH + " (must be valid filepath)\n"
            + "Example: " + COMMAND_WORD + " 1 " + "C:\\users\\wishingmaid\\Desktop\\imageFolder\\ThiccBooks";

    public AddPhotoCommand(Index index) {
    }
    public CommandResult executeUndoableCommand() {
        return null;
    }
}
