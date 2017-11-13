package seedu.address.logic.commands;

// @@author HouDenghao
/**
 * Sorts all persons in the address book for the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";

    @Override
    public CommandResult execute() {
        model.sortPersons();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
