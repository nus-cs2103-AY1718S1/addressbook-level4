//@@author Houjisan
package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the contact list according to the specified data field.\n"
            + "Parameters: DATAFIELD (Possible fields: Name, Phone, Email, Address)\n"
            + "Example: " + COMMAND_WORD + " address\n"
            + "If you want to ignore favourites, type -ignorefav after the DATAFIELD\n"
            + "Example: " + COMMAND_WORD + " address -ignorefav\n"
            + "If you want to sort in reverse order, type -reverse after the DATAFIELD\n"
            + "Example: " + COMMAND_WORD + " address -reverse";

    public static final String MESSAGE_SORT_LIST_SUCCESS = "Sorted list according to %1$s";

    private final String dataField;
    private final boolean isFavIgnored;
    private final boolean isReverseOrder;

    public SortCommand(String dataField, boolean isFavIgnored, boolean isReverseOrder) {
        this.dataField = dataField;
        this.isFavIgnored = isFavIgnored;
        this.isReverseOrder = isReverseOrder;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.sortByDataFieldFirst(dataField, isFavIgnored, isReverseOrder);
        model.getFilteredPersonList();

        return new CommandResult(String.format(MESSAGE_SORT_LIST_SUCCESS, dataField)
                + (isReverseOrder ? " in reverse order" : "")
                + (isFavIgnored ? " ignoring favourites" : ""));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.dataField.equals(((SortCommand) other).dataField)); // state check
    }
}
