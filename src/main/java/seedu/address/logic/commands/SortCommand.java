package seedu.address.logic.commands;

/**
 * Lists all sorted persons in the address book to the user.
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "st";
    public static final String SHORT_MESSAGE_USAGE = COMMAND_WORD;

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPersons();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
