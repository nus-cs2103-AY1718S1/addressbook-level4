package seedu.address.logic.commands;

import seedu.address.commons.core.ProfilePicturesFolder;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author jaivigneshvenugopal
/**
 * Sets the absolute path to the profile pictures folder that is residing in user's workspace.
 */
public class SetPathCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "setpath";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "C:/Users/acer/Desktop/SE/profilepic/";
    public static final String MESSAGE_SUCCESS = "Location to access profile pictures is now set!";

    private String path;

    public SetPathCommand(String path) {
        this.path = path;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ProfilePicturesFolder.setPath(reformatPath(path));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    private String reformatPath(String path) {
        path = path.replaceAll("\\\\", "/");
        return path;
    }
}
