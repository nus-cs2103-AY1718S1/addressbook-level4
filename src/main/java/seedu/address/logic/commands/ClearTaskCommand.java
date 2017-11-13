package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.parser.AutoCorrectCommand;
import seedu.address.model.task.exceptions.TaskNotFoundException;

//@@author JYL123
/**
 * Clears the task list.
 */
public class ClearTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "cleartask";
    public static final String COMMAND_WORD_ALIAS = "ctask";
    public static final String AUTOCOMPLETE_FORMAT = COMMAND_WORD;
    public static final String MESSAGE_USAGE = "";
    public static final String MESSAGE_SUCCESS = "Task list has been cleared!";
    private AutoCorrectCommand autoCorrectCommand = new AutoCorrectCommand();


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);

        int numberOfTasks = model.getFilteredTaskList().size();
        for (int i = numberOfTasks - 1; i >= 0; i--) {
            try {
                model.deleteTask(model.getFilteredTaskList().get(i));
            } catch (TaskNotFoundException e) {
                assert false : "The target task cannot be missing";
            }
        }

        if (autoCorrectCommand.getMessageToUser().equals("")) {
            return new CommandResult(MESSAGE_SUCCESS);
        } else {
            return new CommandResult(autoCorrectCommand.getMessageToUser()
                    + "\n"
                    + MESSAGE_SUCCESS);
        }
    }
}
