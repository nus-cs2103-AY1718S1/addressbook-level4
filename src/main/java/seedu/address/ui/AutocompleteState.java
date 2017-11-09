package seedu.address.ui;

//@@author Kowalski985
/**
 * Represents the states of the autcompleter
 */
public enum AutocompleteState {
    COMMAND,
    COMMAND_NEXT_PREFIX,
    COMMAND_CYCLE_PREFIX,
    COMMAND_COMPLETE_PREFIX,
    EMPTY,
    MULTIPLE_COMMAND,
    NO_RESULT,
    INDEX
}
