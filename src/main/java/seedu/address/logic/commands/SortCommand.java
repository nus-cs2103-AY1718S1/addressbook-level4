package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyAddressBookException;

//@@author LimeFallacie
/**
 * Sorts list according to sort type entered.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String ARGUMENT_NAME = "name";
    public static final String ARGUMENT_PHONE = "phone";
    public static final String ARGUMENT_EMAIL = "email";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts Address Book contacts according to specified field.\n"
            + "Parameters: TYPE (must be either 'name', 'phone, or 'email')\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SORT_SUCCESS = "Sorted all persons by %s.";

    public static final String MESSAGE_NO_CONTACTS_TO_SORT = "There are no contacts available to sort";

    private String sortType;

    public SortCommand(String type) {
        this.sortType = type;
    }

    @Override
    public CommandResult execute() throws CommandException {
        try {
            model.sort(sortType);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_USAGE); //It will never reach here.
        } catch (EmptyAddressBookException eabe) {
            throw new CommandException(MESSAGE_NO_CONTACTS_TO_SORT);
        }

        //lists all contacts after sorting
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        if (!(ARGUMENT_PHONE.equals(sortType))) {
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
