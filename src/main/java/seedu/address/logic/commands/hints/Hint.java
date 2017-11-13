package seedu.address.logic.commands.hints;

import static java.util.Objects.requireNonNull;

import java.util.logging.Logger;

import seedu.address.commons.core.LogsCenter;

/**
 * Hints for inline hint suggestion and tab autocompletion
 */
public abstract class Hint {

    protected static final Logger LOGGER = LogsCenter.getLogger(Hint.class);

    protected String argumentHint;
    protected String description;
    protected String userInput;
    protected String arguments;

    /**
     * returns the new user input when user presses tab
     */
    public abstract String autocomplete();

    /**
     * returns the argument hint of current user input
     */
    public String getArgumentHint() {
        return argumentHint;
    }

    /**
     * returns the description of current user input
     */
    public String getDescription() {
        return description;
    }

    /**
     * asserts that require info is non null
     * should be called after parse()
     */
    public final void requireFieldsNonNull() {
        requireNonNull(argumentHint);
        requireNonNull(description);
        requireNonNull(userInput);
        requireNonNull(autocomplete());
    }


}
