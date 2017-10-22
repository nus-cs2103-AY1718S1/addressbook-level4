package seedu.address.logic.commands.Tasks;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

import static seedu.address.model.Model.PREDICATE_SHOW_ALL_TASKS;

/**
 * Lists all tasks in the address book to the user.
 */
public class ListTasksCommand extends Command {

    public static final String COMMAND_WORD = "listtasks";

    public static final String MESSAGE_SUCCESS = "Listed all tasks";


    @Override
    public CommandResult execute() {
        model.updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
