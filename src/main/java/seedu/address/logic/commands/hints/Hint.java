package seedu.address.logic.commands.hints;

import java.util.logging.Logger;
import seedu.address.commons.core.LogsCenter;

public abstract class Hint {
    public String argumentHint;
    public String description;
    public String userInput;
    public String arguments;

    public abstract void parse();
    public abstract String autocomplete();

    protected static final Logger logger = LogsCenter.getLogger(Hint.class);

    protected final void assertRequiredisNonNull() {
        if ((argumentHint == null) ||
                (description == null) ||
                (userInput == null) ||
                (argumentHint == null)) {
            assert false;
        }

    }
}
