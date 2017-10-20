package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/***
 * Sort list
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sort list by given single attribute "
            + "the specified attribute (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: ATTRIBUTE\n"
            + "Example: " + COMMAND_WORD + " name";

    public static final String MESSAGE_SORT_LESSON_SUCCESS = "List sorted successfully";

    @Override
    public CommandResult execute() throws CommandException {
        model.sortLessons();
        return new CommandResult(MESSAGE_SORT_LESSON_SUCCESS);
    }

}
