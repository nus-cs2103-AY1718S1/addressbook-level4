package seedu.address.model;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;

/**
 * Represents a command mode in the application.
 * Guarantees: details are present and not null, field values are validated.
 */
public class CommandMode {

    public static final String MESSAGE_COMMANDMODE_CONSTRAINTS = "Command commandMode should be either addressbook(ab) "
        + "or taskmanager(tm)";

    public static final String COMMANDMODE_VALIDATION_REGEX = "addressbook ab taskmanager tm";

    private String commandMode;

    public CommandMode() {
        commandMode = "ab";
    }
    /**
     * Validates command commandMode.
     *
     * @throws IllegalValueException if the given command commandMode string is invalid.
     */
    public void setCommandMode(String input) throws IllegalValueException {
        requireNonNull(input);
        String trimmedInput = input.trim().toLowerCase();
        if (!isValidMode(trimmedInput)) {
            throw new IllegalValueException(MESSAGE_COMMANDMODE_CONSTRAINTS);
        }
        this.commandMode = trimmedInput;
    }

    public static boolean isValidMode(String test) {
        return StringUtil.containsWordIgnoreCase(COMMANDMODE_VALIDATION_REGEX, test);
    }

    @Override
    public String toString() {
        return commandMode;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
            || (other instanceof CommandMode // instanceof handles nulls
            && this.commandMode.equals(((CommandMode) other).commandMode)); // state check
    }

    @Override
    public int hashCode() {
        return commandMode.hashCode();
    }

}
