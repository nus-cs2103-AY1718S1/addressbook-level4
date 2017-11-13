package seedu.address.logic.commands.hints;

/**
 * Generates description for Clear Command
 * Assumes that {@code userInput} are from a Clear Command.
 */
public class ClearCommandHint extends NoArgumentsHint {

    protected static final String CLEAR_COMMAND_DESC = "clears all contacts";

    public ClearCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = CLEAR_COMMAND_DESC;
        parse();
    }

}
