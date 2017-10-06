package seedu.address.logic.commands;

/**
 * Exports existing contacts to external XML file
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports existing contacts.\n"
            + "Parameters: FILE PATH (must be a valid file path where the current user has write access\n"
            + "Example: " + COMMAND_WORD + " /Users/seedu/Documents";

    public static final String MESSAGE_EXPORT_CONTACTS_SUCCESS = "Contacts exported to: %1$s";

    private final String exportFilePath;

    public ExportCommand(String filePath) {
        exportFilePath = filePath;
    }

    @Override
    public CommandResult execute() {
        // Does nothing for now
        return null;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.exportFilePath.equals(((ExportCommand) other).exportFilePath)); // state check
    }
}
