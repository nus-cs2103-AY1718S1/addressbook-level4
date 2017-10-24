package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.commons.exceptions.InvalidFileExtensionException;
import seedu.address.commons.exceptions.InvalidFilePathException;
import seedu.address.commons.exceptions.InvalidNameException;
import seedu.address.commons.exceptions.InvalidNameSeparatorException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.storage.Storage;

/**
 * Exports the address book to a user defined location {@code filePath}
 */
public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Exports the address book data to the location defined by the file path.\n"
            + "Example:\n"
            + COMMAND_WORD + " C:\\Users\\John Doe\\Documents\\addressbook.xml (Windows)\n"
            + COMMAND_WORD + " /Users/John Doe/Documents/addressbook.xml (macOS, Linux)\n";

    public static final String MESSAGE_EXPORT_SUCCESS = "Exported address book data to: %1$s";
    public static final String MESSAGE_PROBLEM_WRITING_FILE = "There is a problem exporting to the specified file"
            + " path. Please check that you have permissions to do so.";
    public static final String MESSAGE_NOT_XML_FILE = "The file path does not point to an XML file.";
    public static final String MESSAGE_INVALID_NAME = "The file path contains file name or folder names with"
            + " prohibited characters (?!%*+:|\"<>).";
    public static final String MESSAGE_INVALID_NAME_SEPARATOR = "The file path contains name-separators (/ or \\) that"
            + " are not defined in your operating system.";
    public static final String MESSAGE_CONSECUTIVE_SEPARATOR = "The file path contains consecutive"
            + " name-separators (/ or \\) or extension-separators (.).";
    private Storage storage;
    private final String filePath;

    public ExportCommand(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public CommandResult execute() throws CommandException {

        try {
            storage.saveAddressBook(model.getAddressBook(), filePath);
            return new CommandResult(String.format(MESSAGE_EXPORT_SUCCESS, filePath));
        } catch (IOException e) {
            throw new CommandException(MESSAGE_PROBLEM_WRITING_FILE);
        } catch (InvalidFileExtensionException e) {
            throw new CommandException(MESSAGE_NOT_XML_FILE);
        } catch (InvalidNameException e) {
            throw new CommandException(MESSAGE_INVALID_NAME);
        } catch (InvalidNameSeparatorException e) {
            throw new CommandException(MESSAGE_INVALID_NAME_SEPARATOR);
        } catch (InvalidFilePathException e) {
            throw new CommandException(MESSAGE_CONSECUTIVE_SEPARATOR);
        }
    }

    @Override
    public void setStorage(Storage storage) {
        this.storage = storage;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.filePath.equals(((ExportCommand) other).filePath)); // state check
    }
}
