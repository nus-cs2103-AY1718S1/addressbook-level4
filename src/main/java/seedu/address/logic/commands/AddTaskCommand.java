package seedu.address.logic.commands;

public class AddTaskCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtask";
    public static final String MESSAGE_SUCCESS = "Task has been added";
    
    private final Task toAdd;
    
    public AddTaskCommand() {
    }
    
    @Override
    public CommandResult executeUndoableCommand() {
        throw new NullPointerException();
    }
}
