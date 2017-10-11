package seedu.address.logic.commands;


/**
 * Sorts the contacts in the address book based on name.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "ss";
    public static final String MESSAGE_SUCCESS = "Address book successfully sorted!";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortContact();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
