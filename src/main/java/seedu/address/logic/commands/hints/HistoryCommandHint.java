package seedu.address.logic.commands.hints;

public class HistoryCommandHint extends NoArgumentsHint {

    protected final static String HISTORY_COMMAND_DESC = "show command history";

    public HistoryCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = HISTORY_COMMAND_DESC;
    }
}
