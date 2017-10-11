package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Sorts list according to sort type entered.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts Address Book contacts according to specified field.\n"
            + "Parameters: TYPE (must be either 'name', 'phone, or 'email')\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SORT_SUCCESS = "Sorted all persons by %s";

    private String sortType;

    public SortCommand(String type) {
        this.sortType = type;
    }

    @Override
    public CommandResult execute() {
        model.sort(sortType);

        //lists all contacts after sorting
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (!("phone".equals(sortType))) {
            return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, sortType));
        } else {
            return new CommandResult(String.format(MESSAGE_SORT_SUCCESS, (sortType + " number")));
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.sortType.equals(((SortCommand) other).sortType)); // state check
    }

}
