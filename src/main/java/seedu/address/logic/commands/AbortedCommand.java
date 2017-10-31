package seedu.address.logic.commands;

import seedu.address.model.event.Event;

public class AbortedCommand extends AddEventCommand {
    private Event toAdd;
    public static final String MESSAGE_SUCCESS = "This command is aborted";

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
