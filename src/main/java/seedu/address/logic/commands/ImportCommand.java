package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Imports data from various format to the application.
 */
public class ImportCommand extends Command {
    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "i";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports data from the location specified by the file path to the application.\n"
            + "The file can be either XML format (default) or BoNUS script format.\n"
            + "Examples:\n"
            + COMMAND_WORD + " C:\\Users\\John Doe\\Documents\\bonus.bo (Windows)\n"
            + COMMAND_WORD + " /Users/John Doe/Documents/bonus.xml (macOS, Linux)\n";

    public static final String MESSAGE_EXPORT_SUCCESS = "Imported data from: %1$s";
    public static final String MESSAGE_NOT_XML_FILE = "According to the extension, the file is not a valid XML "
            + "file.\nYou need to specify with explicit parameter if you want to use BoNUS script format.";
    public static final String MESSAGE_NOT_BO_FILE = "According to the extension, the file is not a valid BoNUS"
            + "script file (should end with .bo).";

    @Override
    public CommandResult execute() throws CommandException {
        return null;
    }
}
