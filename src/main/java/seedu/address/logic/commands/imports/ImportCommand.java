package seedu.address.logic.commands.imports;

import java.util.HashMap;

import seedu.address.logic.commands.UndoableCommand;

/**
 * Imports data from various format to the application.
 */
public abstract class ImportCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "import";
    public static final String COMMAND_ALIAS = "i";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Imports data from various locations in various formats.\n"
            + "Examples:\n"
            + COMMAND_WORD + " --script C:\\Users\\John Doe\\Documents\\bonus.bo (Windows)\n"
            + COMMAND_WORD + " /Users/John Doe/Documents/bonus.xml (macOS, Linux)\n";

    public static final String MESSAGE_IMPORT_SUCCESS = "Imported data from: %1$s";
    public static final String MESSAGE_NOT_XML_FILE = "According to the extension, the file is not a valid XML "
            + "file.\nYou need to specify with explicit parameter if you want to use other formats.";
    public static final String MESSAGE_NOT_BO_FILE = "According to the extension, the file is not a valid BoNUS"
            + "script file (should end with .bo).";
    public static final String MESSAGE_PROBLEM_READING_FILE = "There is a problem when the application tried to"
            + " read the given file. Please check the file permission.";

    /**
     * Different types of sub-commands within {@link ImportCommand}.
     */
    public enum ImportType {
        XML, SCRIPT, NUSMODS
    }

    public static final HashMap<String, ImportType> TO_ENUM_IMPORT_TYPE = new HashMap<>();

    static {
        TO_ENUM_IMPORT_TYPE.put("xml", ImportType.XML);
        TO_ENUM_IMPORT_TYPE.put("script", ImportType.SCRIPT);
        TO_ENUM_IMPORT_TYPE.put("nusmods", ImportType.NUSMODS);
    }

    protected String path;

    private ImportType importType;

    public ImportCommand(String path, ImportType importType) {
        this.path = path;
        this.importType = importType;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && importType.equals(((ImportCommand) other).importType)
                && path.equals(((ImportCommand) other).path));
    }
}
