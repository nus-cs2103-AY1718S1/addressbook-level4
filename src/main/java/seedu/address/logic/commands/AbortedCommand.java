package seedu.address.logic.commands;

import seedu.address.model.event.Event;
// @@author HuWanqing
/**
 * A dummy command class used to abort add Event command
 */
public class AbortedCommand extends AddEventCommand {
    public static final String MESSAGE_SUCCESS = "This command is aborted";

    private Event toAdd;

    public AbortedCommand (Event event) {
        super(event);
    }

    @Override
    protected CommandResult executeUndoableCommand() {
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    protected void undo() {
    }

    @Override
    protected void redo() {
    }
}
