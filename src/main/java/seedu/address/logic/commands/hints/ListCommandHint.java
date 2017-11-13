package seedu.address.logic.commands.hints;

/**
 * Generates description for List Command
 * Assumes that {@code userInput} are from a List Command.
 */
public class ListCommandHint extends NoArgumentsHint {

    protected static final String LIST_COMMAND_DESC = "lists all contacts";

    public ListCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = LIST_COMMAND_DESC;
        parse();
    }
}
