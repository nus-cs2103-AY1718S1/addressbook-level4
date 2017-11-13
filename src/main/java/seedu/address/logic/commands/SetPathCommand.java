package seedu.address.logic.commands;

import java.io.File;

import seedu.address.commons.core.ProfilePicturesFolder;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author jaivigneshvenugopal
/**
 * Sets the absolute path to the profile pictures folder that is residing in user's workspace.
 */
public class SetPathCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "setpath";
    public static final String MESSAGE_SUCCESS = "Location to access profile pictures is now set!";
    public static final String MESSAGE_FAILURE = "Path is invalid!";
    public static final String MESSAGE_USAGE = COMMAND_WORD + "C:/Users/acer/Desktop/SE/profilepic/";

    private String path;

    public SetPathCommand(String path) {
        this.path = path;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        File pathChecker = new File(path);
        if (pathChecker.exists()) {
            ProfilePicturesFolder.setPath(reformatPath(path));
            model.setProfilePicsPath(reformatPath(path));
        } else {
            throw new CommandException(MESSAGE_FAILURE);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    /**
     * Reformats the path to suit the application
     * @return modified path to suit the application
     */
    private String reformatPath(String path) {
        path = path.replaceAll("\\\\", "/");
        String lastChar = path.substring(path.length() - 1);
        if (!lastChar.equals("/")) {
            path = path.concat("/");
        }
        return path;
    }
}
