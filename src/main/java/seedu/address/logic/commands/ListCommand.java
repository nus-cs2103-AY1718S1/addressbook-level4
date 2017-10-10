package seedu.address.logic.commands;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

/**
 * Lists all persons in the address book to the user.
 */
public class ListCommand extends Command {

    public static final String COMMAND_WORD = "list";

    public static final String COMMAND_ALIAS = "l";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Shows a list of all persons in the address book.\n"
            + "Parameters: None\n"
            + MESSAGE_GET_MORE_HELP;

    public static final String MESSAGE_SUCCESS = "Listed all persons";


    @Override
    public CommandResult execute() {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
