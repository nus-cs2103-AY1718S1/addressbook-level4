package seedu.address.logic.commands.hints;

/**
 * Generates description for History Command
 * Assumes that {@code userInput} are from a History Command.
 */
public class HistoryCommandHint extends NoArgumentsHint {

    protected static final String HISTORY_COMMAND_DESC = "shows command history";

    public HistoryCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = HISTORY_COMMAND_DESC;
        parse();
    }
}
