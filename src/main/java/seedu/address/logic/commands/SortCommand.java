package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

//@@author bladerail
/**
 * Sorts all persons in the address book by indicated format.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sorts the address book in ascending order by an indicated format."
            + "Sorts by default (Name) if no argument. "
            + "Currently 4 possible formats: Name, Email, Phone, Address.\n"
            + "Accepts aliases 'n', 'e', 'p', 'a' respectively."
            + "Parameters: "
            + COMMAND_WORD
            + " [name/email/phone/address]\n"
            + "Example: " + COMMAND_WORD + " name";

    //@@author hansiang93
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {[name/email/phone/address]}";

    //@@author bladerail
    public static final String MESSAGE_SUCCESS = "Sorted successfully by %1$s, Listed all persons.";

    private String filterType;

    public SortCommand (String filterType) {
        // Filter type can be null to signify default listing
        // Todo: Allow sort to accept different parameters for filter types (eg. First Name, Last Name)
        this.filterType = filterType;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.sortFilteredPersonList(filterType);
        return new CommandResult(String.format(MESSAGE_SUCCESS, filterType));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceOf handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        // state check
        SortCommand e = (SortCommand) other;
        return filterType.equals(e.filterType);
    }
}
