package seedu.room.logic.commands;

import java.io.IOException;

import seedu.room.MainApp;
import seedu.room.logic.commands.exceptions.CommandException;


/**
 * Create backup copy of resident book.
 */
public class BackupCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "backup";
    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates backup copy of resident book.";

    public static final String MESSAGE_SUCCESS = "New backup created";

    public static final String MESSAGE_ERROR = "Error creating backup";

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            MainApp.getBackup().backupResidentBook(model.getResidentBook());
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_ERROR) + e.getMessage());
        }

    }
}
