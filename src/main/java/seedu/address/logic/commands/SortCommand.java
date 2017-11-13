package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/***
 * Sort list based on the current listing attribute
 */

//@@author angtianlannus
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_SORT_LESSON_SUCCESS = "List sorted successfully";

    @Override
    public CommandResult execute() throws CommandException {
        model.sortLessons();
        return new CommandResult(MESSAGE_SORT_LESSON_SUCCESS);
    }

}
