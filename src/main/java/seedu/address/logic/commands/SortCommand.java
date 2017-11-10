package seedu.address.logic.commands;

// @@author HouDenghao
/**
 * Sorts all persons in the address book to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SUCCESS = "Sorted all persons and events";

    @Override
    public CommandResult execute() {
        model.sortPersons();
        model.sortEvents();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
