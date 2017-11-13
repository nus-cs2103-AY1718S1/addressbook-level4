package seedu.address.logic.commands.exceptions;

/**
 * Represents an error which occurs during execution of a {@code TransferCommand}.
 */
public class ConfigMissingException extends Exception {
    public ConfigMissingException(String message) {
        super(message);
    }
}
