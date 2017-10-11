package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Sorts list according to sort type entered.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts Address Book contacts according to specified field.\n"
            + "Parameters: 'name', 'phone', or 'email'\n"
            + "Example: " + COMMAND_WORD + " name";

    public String MESSAGE_SORT_SUCCESS = "Sorted all persons by ";

    private String sortType;

    public SortCommand(String type) {
        this.sortType = type;
        MESSAGE_SORT_SUCCESS = MESSAGE_SORT_SUCCESS + type;
    }

    @Override
    public CommandResult execute() {
        model.sort(sortType);

        //because "sorted by phone" sounds weird
        if ("phone".equals(sortType)) {
            sortType = sortType + " number";
        }

        //lists all contacts after sorting
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SORT_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.sortType.equals(((SortCommand) other).sortType)); // state check
    }

}
