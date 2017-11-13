package seedu.address.logic.commands;
//@@author Esilocke
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import seedu.address.model.task.TaskContainsKeywordPredicate;

/**
 * Finds tasks in the address book.
 */
public class FindTaskCommand extends FindCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD  + " " + PREFIX_TASK
            + ": Finds all tasks whose names or descriptions "
            + "contain any of the specified keywords (case-sensitive) and displays them as a list with index numbers.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]... [p/MINIMUM_PRIORITY] [done/TASK_STATE]\n"
            + "Example: " + COMMAND_WORD + " task make";
    public static final String MESSAGE_INVALID_COMPLETE_VALUE = "The task status should either be 'true' or 'false'";
    public static final String MESSAGE_INVALID_PRIORITY = "The specified priority should be an integer from 1 to 5";
    private final TaskContainsKeywordPredicate taskPredicate;

    public FindTaskCommand(TaskContainsKeywordPredicate predicate) {
        this.taskPredicate = predicate;
    }

    @Override
    public CommandResult execute() {
        assert(taskPredicate != null);
        model.updateFilteredTaskList(taskPredicate);
        return new CommandResult(getMessageForTaskListShownSummary(model.getFilteredTaskList().size()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this
                || other instanceof FindTaskCommand
                && this.taskPredicate.equals(((FindTaskCommand) other).taskPredicate);
    }
}
