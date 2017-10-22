package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.MainApp;
import seedu.address.logic.commands.exceptions.CommandException;


/**
 * Create backup copy of address book.
 */
public class BackupCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "backup";
    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates backup copy of address book.";

    public static final String MESSAGE_SUCCESS = "New backup created";

    public static final String MESSAGE_ERROR = "Error creating backup";

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            MainApp.getBackup().backupAddressBook(model.getAddressBook());
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_ERROR) + e.getMessage());
        }

    }
}
