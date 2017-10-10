package seedu.address.logic.commands;

/**
 * Lists all sorted persons in the address book to the user.
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String COMMAND_ALIAS = "st";

    public static final String MESSAGE_SUCCESS = "Sorted all persons";


    @Override
    public CommandResult execute() {
        model.sortPerson();

        return new CommandResult(MESSAGE_SUCCESS);
    }
}
