package seedu.address.logic.commands.hints;

public class ClearCommandHint extends NoArgumentsHint {

    protected final static String CLEAR_COMMAND_DESC = "clears all contacts";

    public ClearCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = CLEAR_COMMAND_DESC;
    }

}
