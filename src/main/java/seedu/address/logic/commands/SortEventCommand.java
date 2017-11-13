package seedu.address.logic.commands;

// @@author HouDenghao
/**
 * Sorts all events in the address book to the user.
 */
public class SortEventCommand extends Command {

    public static final String COMMAND_WORD = "sortE";

    public static final String MESSAGE_SUCCESS = "Sorted all events";

    @Override
    public CommandResult execute() {
        model.sortEvents();
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
