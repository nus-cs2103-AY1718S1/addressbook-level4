//@@author keloysiusmak
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.io.IOException;

import seedu.address.commons.exceptions.DataConversionException;
import seedu.address.logic.commands.exceptions.ConfigMissingException;

/**
 * Export data for transfer to another computer
 */
public class TransferCommand extends Command {

    public static final String COMMAND_WORD = "transfer";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Zips the configuration files and includes a installation guide.\n"
            + "Example: " + COMMAND_WORD;

    public static final String MESSAGE_TRANSFER_SUCCESS = "Successfully exported ZIP file.";

    public TransferCommand() {
    }

    public static String getCommandWord() {
        return COMMAND_WORD;
    }

    @Override
    public CommandResult execute() {
        requireNonNull(model);
        requireNonNull(config);

        try {
            model.transferData();
        } catch (ConfigMissingException e) {
            try {
                model.transferDataWithDefault();
            } catch (IOException i) {
                i.printStackTrace();
            } catch (DataConversionException i) {
                i.printStackTrace();
            }
        }

        return new CommandResult(MESSAGE_TRANSFER_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TransferCommand); // state check
    }
}
