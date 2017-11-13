package seedu.address.logic.commands;

/**
 * Saves a backup copy of the address book data
 */

//@@author hengyu95
public class BackupCommand extends Command {

    public static final String COMMAND_WORD = "backup";

    public static final String COMMAND_ALIAS = "b";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Saves a backup copy of Augustine data.\n";

    public static final String MESSAGE_SUCCESS = "Data backed up at \"/data/addressbook-backup.xml\"!";

    public BackupCommand() {
    }

    @Override
    public CommandResult execute() {
        model.backupAddressBook();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
