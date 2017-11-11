package seedu.address.logic.commands.hints;

public class RedoCommandHint extends NoArgumentsHint {

    protected final static String REDO_COMMAND_DESC = "redo command";

    public RedoCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = REDO_COMMAND_DESC;
    }

}
