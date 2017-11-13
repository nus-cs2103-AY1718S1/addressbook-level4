//@@author Hoang
package seedu.address.logic.commands;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * exports the current address book to a .txt file
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": export the address book to a chosen file type\n"
            + "Parameters: export .txt [PATH]\n"
            + "Example: export .txt C:/user/user/desktop";
    public static final String MESSAGE_SUCCESS = "Successfully exported";
    public static final String MESSAGE_FAILED = "Failed to export";
    public static final String MESSAGE_PATH_NOT_FOUND = "The specified path is not found";
    public static final String MESSAGE_ACCESS_DENIED = "Access denied";
    public static final String MESSAGE_ERROR_WRITING_FILE = "Error writing file";
    public static final String MESSAGE_ERROR_READING_FILE = "Error reading file";
    public static final String MESSAGE_FILE_TYPE_NOT_SUPPORTED = "File type not supported";

    private String exportPath;
    private String exportType;
    private ObservableList<ReadOnlyPerson> personList;
    private Object[] personListArray;

    public ExportCommand(String exportType, String exportPath) {
        this.exportType = exportType;
        this.exportPath = exportPath;
    }

    @Override
    public CommandResult execute() throws CommandException {
        personList = model.getAddressBook().getPersonList();
        personListArray = personList.toArray();

        Charset charset = Charset.forName("US-ASCII");

        File file = new File(exportPath);
        try {
            file.mkdirs();
        } catch (SecurityException e) {
            throw new CommandException(MESSAGE_ACCESS_DENIED);
        }

        file = new File(exportPath + "/addressbook.txt");

        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ACCESS_DENIED);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(file.toPath(), charset)) {
            for (int i = 0; i < personListArray.length; i++) {
                ReadOnlyPerson currentPerson = (ReadOnlyPerson) personListArray[i];
                writer.write(currentPerson.getAsText(), 0, currentPerson.getAsText().length());
                writer.write("\n");
            }
        } catch (IOException e) {
            throw new CommandException(MESSAGE_ERROR_READING_FILE);
        }


        return new CommandResult(MESSAGE_SUCCESS);
    }
}
//@@author Hoang
