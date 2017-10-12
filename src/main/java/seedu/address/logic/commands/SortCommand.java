package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Sorts all persons in the address book by indicated format.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the address book in ascending order by an indicated format. NAME or DEFAULT only."
            + "Parameters: "
            + COMMAND_WORD
            + " [name/email/phone/address/tag]\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SUCCESS = "Sorted successfully, Listing all persons below";

    public static final String MESSAGE_ARGUMENTS = "Filter type: %1$s";

    private String filterType;

    public SortCommand (String filterType){
        //Filter type can be null to signify default listing
        //Todo: Allow sort to accept different parameters for filter types (eg. First Name, Last Name)
        this.filterType = filterType;
    }

    @Override
    public CommandResult execute() throws CommandException {
        model.sortFilteredPersonList();
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        //instanceOf handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        //state check
        SortCommand e = (SortCommand) other;
        return filterType.equals(e.filterType);
    }
}
