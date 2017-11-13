package seedu.address.logic.commands;

import java.io.IOException;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Exports OneBook to a VCard File.
 */

public class ExportCommand extends Command {
    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": export contacts to VCard file or csv file.\n"
            + "Parameters: KEYWORD [FILE DIRECTORY]...\n"
            + "Example for export to VCard file: " + COMMAND_WORD + " C:\\Users\\user\\desktop\\add.vcf(Windows)\n"
            + "Example for export to CSV file: " + COMMAND_WORD + " C:\\Users\\user\\desktop\\add.csv(Windows)\n"
            + "Example for export to VCard file: " + COMMAND_WORD + " /Users/user/Desktop/add.vcf(MAC OS)\n"
            + "Example for export to CSV file: " + COMMAND_WORD + " /Users/user/Desktop/add.csv(MAC OS)\n";

    public static final String MESSAGE_SUCCESS = "OneBook has been exported to %1$s file successfully.";

    public static final String MESSAGE_WRITE_ERROR = "The file cannot be exported.";


    private String fileLocation;
    private String fileName;
    private String extension;

    public ExportCommand(String fileLocation, String fileName, String extension) {

        this.fileLocation = fileLocation;
        this.fileName = fileName;
        this.extension = extension;
    }

    //@@author stan789
    @Override
    public CommandResult execute() throws CommandException {

        try {
            model.exportFile(fileLocation, extension);

        } catch (IOException e) {
            throw new CommandException(MESSAGE_WRITE_ERROR);
        }

        return new CommandResult(String.format(MESSAGE_SUCCESS, fileName));
    }

    //@@author stan789
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ExportCommand // instanceof handles nulls
                && this.fileLocation.equals(((ExportCommand) other).fileLocation)); // state check
    }
}
