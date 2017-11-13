package seedu.address.logic.commands;

//@@author aungmyin23
/**
 * Sorts the list of places in alphabetical order
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_USAGE = COMMAND_WORD;
    public static final String SHOWING_SORT_MESSAGE = "The list is sorted in alphabetical order.";

    @Override
    public CommandResult executeUndoableCommand() {
        model.sortPlaces();
        return new CommandResult(SHOWING_SORT_MESSAGE);
    }
}
