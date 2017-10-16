package seedu.address.testutil;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.commandidentifier.CommandIdentifier;

/**
 * Helper methods related to command identifier
 */
public class CommandIdentifierUtils {

    /**
     * Returns a CommandIdentifier object of the specified {@code commandIdentifier}.
     */
    public static CommandIdentifier createCommandIdentifier(String commandIdentifier) {
        CommandIdentifier newCommandIdentifier;

        try {
            newCommandIdentifier = new CommandIdentifier(commandIdentifier);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException(ive.getMessage());
        }

        return newCommandIdentifier;
    }
}
