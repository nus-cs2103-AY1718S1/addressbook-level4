package seedu.address.logic.commands.exceptions;

import java.nio.file.Path;
import java.nio.file.Paths;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;
import seedu.address.model.Model;
import seedu.address.storage.Storage;

/**
 * Imports contacts from an external XML file
 */
public class ImportCommand extends Command {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Imports contacts from external file.\n"
            + "Parameters: FILE PATH (must be a valid file path where the current user has write access\n"
            + "Example: " + COMMAND_WORD + " /Users/seedu/Documents/exportedData.xml";

    public static final String MESSAGE_IMPORT_CONTACTS_SUCCESS = "Contacts imported from: %1$s";
    public static final String MESSAGE_IMPORT_CONTACTS_FAILURE = "Unable to import contacts from: %1$s";

    private final Path importFilePath;

    public ImportCommand(String filePath) {
        // we store it as a Path rather than a String so that we can get the absolute file path
        // this makes it clearer to the user where the file is imported from
        importFilePath = Paths.get(filePath);
    }

    @Override
    public CommandResult execute() throws CommandException {
        // return null for now
        return null;
    }

    @Override
    public void setData(Model model, Storage storage, CommandHistory commandHistory, UndoRedoStack undoRedoStack) {
        this.model = model;
        this.storage = storage;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && this.importFilePath.equals(((ImportCommand) other).importFilePath)); // state check
    }
}
