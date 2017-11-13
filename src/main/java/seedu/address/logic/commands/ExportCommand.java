package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author LimeFallacie
/**
 * Exports the contacts list in file format to a provided directory in the provided filename
 */
public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports contacts list to chosen directory.\n"
            + "Parameters: directory\n"
            + "Example for export: " + COMMAND_WORD + " C:\\desktop\\circles.xml\n";

    public static final String MESSAGE_SUCCESS = "Contacts list exported successfully.";

    public static final String MESSAGE_WRITE_ERROR = "The file could not be exported.";

    private String fileLocation;

    public ExportCommand(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            storage.saveAddressBook(model.getAddressBook(), fileLocation);
        } catch (IOException ioe) {
            throw new CommandException(MESSAGE_WRITE_ERROR);
        }
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ExportCommand) other).fileLocation)); // state check
    }
}
