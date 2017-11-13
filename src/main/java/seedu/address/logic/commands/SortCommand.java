package seedu.address.logic.commands;

import static seedu.address.model.person.UniquePersonList.EMPTY_LIST;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author justintkj
/**
 * Sorts a the list of persons in ascending alphabetical order
 */
public class SortCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "sort";
    public static final String MESSAGE_SORT_SUCCESS = "Sorted in ascending order: ";
    public static final String MESSAGE_SORT_FAILURE = "Invalid command format!";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the person identified by the index number used in the last person listing.\n"
            + "Parameters: name/num/address/email (the type of sort to be executed)\n"
            + "Example: " + COMMAND_WORD + " name"
            + "Example: " + COMMAND_WORD + " number"
            + "Example: " + COMMAND_WORD + " address"
            + "Example: " + COMMAND_WORD + " remark"
            + "Example: " + COMMAND_WORD + " email"
            + "Example: " + COMMAND_WORD + " favourite"
            + "Example: " + COMMAND_WORD + " birthday"
            + "Example: " + COMMAND_WORD + " numTimesSearched";
    public static final String EMPTY_LIST_EXCEPTION_MESSAGE = "List is empty!";


    private String sortType;

    public SortCommand(String sortType) {
        this.sortType = sortType.toLowerCase();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        String fullsortname = model.sortPerson(sortType);
        if (fullsortname.equals(EMPTY_LIST)) {
            throw new CommandException(EMPTY_LIST_EXCEPTION_MESSAGE);
        }
        return new CommandResult(MESSAGE_SORT_SUCCESS + fullsortname);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.sortType.equals(((SortCommand) other).sortType)); // state check
    }
}
