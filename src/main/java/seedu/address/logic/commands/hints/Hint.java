package seedu.address.logic.commands.hints;

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

    public abstract void parse();
    public abstract String autocomplete();

    /**
     * asserts that require info is non null
     * should be called after parse()
     */
    protected final void assertRequiredIsNonNull() {

        if ((argumentHint == null)
                || (description == null)
                || (userInput == null)
                || (argumentHint == null)) {
            assert false;
        }
    }

    public String getArgumentHint() {
        return argumentHint;
    }

    public String getDescription() {
        return description;
    }
}
