package seedu.address.logic.commands.hints;

/**
 * Generates description for Undo Command
 * Assumes that {@code userInput} are from an Undo Command.
 */
public class UndoCommandHint extends NoArgumentsHint {

    protected static final String UNDO_COMMAND_DESC = "undo previous command";

    public UndoCommandHint(String userInput) {
        this.userInput = userInput;
        this.description = UNDO_COMMAND_DESC;
        parse();
    }
}
