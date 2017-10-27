package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.File;
import java.io.FileNotFoundException;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.email.Email;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.storage.XmlFileStorage;
import seedu.address.storage.XmlSerializableAddressBook;

/**
 * Merge the file given with the default storage file
 */
public class MergeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "merge";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": merge the file given with the default storage file\n"
            + "Parameters: "
            + "XML_FILE_PATH\n"
            + "Example: " + COMMAND_WORD + " ./data/newFile.xml";
    public static final String MESSAGE_SUCCESS = "File merged successfully.";
    public static final String MESSAGE_FILE_NOT_FOUND = "File not found.";
    public static final String MESSAGE_DATA_CONVERSION_ERROR = "Unable to convert file data.";

    private final String newFilePath;

    public MergeCommand(String newFilePath) {
        this.newFilePath = newFilePath;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        XmlSerializableAddressBook newFileData;
        try {
            newFileData = XmlFileStorage.loadDataFromSaveFile(new File(newFilePath));
        } catch (FileNotFoundException fne) {
            throw new CommandException(MESSAGE_FILE_NOT_FOUND);
        } catch (DataConversionException dce) {
            throw new CommandException(MESSAGE_DATA_CONVERSION_ERROR);
        }

        ObservableList<ReadOnlyPerson> newFilePersonList = newFileData.getPersonList();
        model.mergeAddressBook(newFilePersonList);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory commandHistory, UndoRedoStack undoRedoStack, Email emailManager) {
        this.model = model;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof MergeCommand // instanceof handles nulls
                && this.newFilePath.equals(((MergeCommand) other).newFilePath)); // state check
    }
}
