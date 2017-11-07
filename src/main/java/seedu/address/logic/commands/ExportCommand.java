
//@@author aali195
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.util.CompressUtil;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * This command is used to export a compressed version of the working addressbook and the images saved
 */

public class ExportCommand extends Command {

    public static final String COMMAND_WORD = "export";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Exports a compressed copy of the working addressbook. "
            + "Existing files will be replaced.\n"
            + "Parameters: [FILEPATH] \n"
            + "Example: " + COMMAND_WORD + " C:\\Users\\Admin\\Desktop\\NameOfFile\n"
            + "[FILEPATH] can be omitted to export to the application directory as \"AddressbookData\".";

    public static final String MESSAGE_EXPORT_PATH_FAIL =
            "This specified path cannot be read.";

    public static final String MESSAGE_EXPORT_SUCCESS = "Addressbook has been exported.";

    private String path;
    private String source = "data/";

    public ExportCommand(String path) {
        requireNonNull(path);

        this.path = path;
    }

    /**
     * Executes the command
     * @return a success message
     * @throws CommandException
     */
    public CommandResult execute() throws CommandException {
        try {
            CompressUtil.run(source, path);
        } catch (Exception e) {
            return new CommandResult(generateFailureMessage());
        }
        return new CommandResult(generateSuccessMessage());
    }

    /**
     * Generates success messages
     * @return Message
     */
    private String generateSuccessMessage() {
        return MESSAGE_EXPORT_SUCCESS;
    }

    /**
     * Generates failure messages
     * @return Message
     */
    private String generateFailureMessage() {
        return MESSAGE_EXPORT_PATH_FAIL;
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof ExportCommand)) {
            return false;
        }

        // state check
        ExportCommand e = (ExportCommand) other;
        return path.equals(e.path);
    }
}

//@@author

