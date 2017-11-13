package seedu.address.logic.commands.hints;

/**
 * Generates description for Redo Command
 * Assumes that {@code userInput} are from a Redo Command.
 */
public class RedoCommandHint extends NoArgumentsHint {

    protected static final String REDO_COMMAND_DESC = "redo command";

    public RedoCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = REDO_COMMAND_DESC;
        parse();
    }

}
