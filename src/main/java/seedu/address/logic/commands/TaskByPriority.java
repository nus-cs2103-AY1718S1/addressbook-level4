package seedu.address.logic.commands;

/**
 * Task sorted by priority value from 1 to 5
 */
public class TaskByPriority extends Command{

    public static final String COMMAND_WORD = "taskByPriority";
    public static final String COMMAND_ALIAS = "tbp";

    public static final String MESSAGE_SUCCESS = "Tasks have been sorted by priority.";


    @Override
    public CommandResult execute() {
        model.taskByPriority();
        return new CommandResult(MESSAGE_SUCCESS);
    }

}
