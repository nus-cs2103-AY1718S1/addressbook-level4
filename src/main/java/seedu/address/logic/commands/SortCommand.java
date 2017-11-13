package seedu.address.logic.commands;

//@@author karrui
/**
 * Sorts all persons in the address book by alphabetical order of contact's name and lists to the user.
 * The order can be either ascending or descending.
 */
public class SortCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Displays all persons in the address book as a list"
            + " sorted by ascending/descending alphabetical order of name."
            + "Command without parameters will be defaulted to ascending order."
            + "Optional Parameters: "
            + "[a] ASCENDING / [d] DESCENDING";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    private final boolean isDescendingSort;

    public SortCommand(boolean isDescendingSort) {
        this.isDescendingSort = isDescendingSort;
    }


    @Override
    public CommandResult executeUndoableCommand() {
        model.sortContactList(isDescendingSort);
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && (isDescendingSort == (((SortCommand) other).isDescendingSort)));
    }
}
