package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.MainApp;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Create backup copy of address book.
 */
public class BackupCommand extends Command {
    public static final String COMMAND_WORD = "backup";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Creates backup copy of address book.";
    public static final String MESSAGE_BACKUP_SUCCESS = "New backup created";
    public static final String MESSAGE_BACKUP_ERROR = "Error creating backup";

    @Override
    public CommandResult execute() throws CommandException {
        try {
            MainApp.getBackup().backupAddressBook(model.getAddressBook());
            return new CommandResult(String.format(MESSAGE_BACKUP_SUCCESS));
        } catch (IOException e) {
            return new CommandResult(String.format(MESSAGE_BACKUP_ERROR) + e.getMessage());
        }

    }
}
