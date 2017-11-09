package seedu.address.model;

import static java.util.Objects.requireNonNull;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.StringUtil;

//@@author tby1994
/**
 * Represents a command mode in the application.
 * Guarantees: details are present and not null, field values are validated.
 */
public class CommandMode {

    public static final String MESSAGE_COMMANDMODE_CONSTRAINTS = "Command commandMode should be either addressbook(ab) "
        + "or taskmanager(tm)";

    public static final String COMMANDMODE_VALIDATION_REGEX = "addressbook ab taskmanager tm";

    private SimpleStringProperty commandMode = new SimpleStringProperty();

    public CommandMode() {
        commandMode.setValue("AddressBook");
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
        commandMode.setValue(rephraseCommandMode(trimmedInput));
    }

    /**Rephrase the command mode to either addressbook or taskmanager */
    private String rephraseCommandMode(String mode) {
        if ("tm".equalsIgnoreCase(mode) || "taskmanager".equalsIgnoreCase(mode)) {
            return "TaskManager";
        } else if ("ab".equalsIgnoreCase(mode) || "addressbook".equalsIgnoreCase(mode)) {
            return "AddressBook";
        }
        return mode;
    }

    /**Test if the input is valid
     * Only ab addressbook tm taskmanager are valid mode
     */
    public static boolean isValidMode(String test) {
        String [] mode = test.split(" ");
        if (mode.length > 1 || test.length() < 2) {
            return false;
        }
        return StringUtil.containsWordIgnoreCase(COMMANDMODE_VALIDATION_REGEX, test);
    }

    public StringProperty getCommandModeProperty() {
        return commandMode;
    }

    @Override
    public String toString() {
        return commandMode.getValue();
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
