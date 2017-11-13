package seedu.address.logic.commands.hints;

/**
 * Template class for hints that have no arguments in their command format
 * Specifies autocomplete to return the original {@code userInput}
 * {@code argumentHint} set to "" as there are no arguments
 */
public abstract class NoArgumentsHint extends Hint {

    NoArgumentsHint() {
        argumentHint = "";
    }

    /**
     * parses {@code userInput}
     * sets appropriate {@code description}
     * for any No Argument Command
     */
    protected void parse() {
        String whitespace = userInput.endsWith(" ") ? "" : " ";
        this.description = whitespace + this.description;
    }

    @Override
    public String autocomplete() {
        return userInput;
    }
}
