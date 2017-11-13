package seedu.address.logic.commands;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author icehawker
/**
 * Backs up user's address book in their designated directory.
 */
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " (alias: " + COMMAND_ALIAS + ")"
            + ": Backs up your address book to the directory of your choice. Note that existing backups in the same "
            + "location must be removed or renamed as file overwriting is OFF. \n"
            + "Parameters: "
            + "Location of target directory.";

    // if input does not contain "\addressbook.xml", it will be appended by parser.
    public static final String BACKUP_DIR_SUFFIX = "addressbook.xml";
    public static final String BACKUP_DIR_SUFFIX_ALT = "\\addressbook.xml";

    public static final String BACKUP_SUCCESS_MESSAGE = "Address Book backed up at directory: %1$s.";
    public static final String BACKUP_FAILURE_MESSAGE = "Address Book could not be backed up at directory: %1$s. "
            + "Please check target path.";
    private String address;

    public BackupCommand(String targetAddress) {
        this.address = targetAddress;
    }

    @Override
    public CommandResult execute() throws CommandException {
        // source expected to stay in default directory
        Path source = Paths.get("data/addressbook.xml");
        // user defined target directory
        Path target = Paths.get(address);
        try {
            // clone addressbook into target
            Files.copy(source, target);
        } catch (IOException e1) {
            return new CommandResult(String.format(BACKUP_FAILURE_MESSAGE, address));
        }

        return new CommandResult(String.format(BACKUP_SUCCESS_MESSAGE, address));
    }

    public String getLocation() {
        return this.address;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof BackupCommand // instanceof handles nulls
                && this.address.equals(((BackupCommand) other).address)); // state check
    }
}
