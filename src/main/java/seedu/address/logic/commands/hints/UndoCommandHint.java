package seedu.address.logic.commands.hints;

public class UndoCommandHint extends NoArgumentsHint {

    protected final static String UNDO_COMMAND_DESC = "undo previous command";

    public UndoCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = UNDO_COMMAND_DESC;
    }
}
