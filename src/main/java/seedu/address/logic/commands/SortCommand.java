package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.EmptyAddressBookException;

/**
 * Sorts and lists all persons in address book.
 *
 */

public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String SORT_NAME = "name";

    public static final String SORT_EMAIL = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts all persons by name or email.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example for sort by name: " + COMMAND_WORD + " name\n"
            + "Example for sort by email: " + COMMAND_WORD + " email";

    public static final String MESSAGE_SUCCESS = "The list has been sorted.";

    public static final String MESSAGE_NO_PERSON_TO_SORT = "There is no contacts to be sorted";

    public final String sortType;

    public SortCommand(String sortType) {
        this.sortType = sortType;
    }

    //@@author stan789
    @Override
    public CommandResult execute() throws CommandException {
        try {
            if (this.binMode) {
                model.executeBinSort(sortType);
            } else {
                model.executeSort(sortType);
            }
            return new CommandResult(MESSAGE_SUCCESS);
        } catch (EmptyAddressBookException e) {
            throw new CommandException(MESSAGE_NO_PERSON_TO_SORT);
        }

    }

    //@@author stan789
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SortCommand // instanceof handles nulls
                && this.sortType.equals(((SortCommand) other).sortType)); // state check
    }
}
