package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected abstract void undo();

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected abstract void redo();

    @Override
    public final CommandResult execute() throws CommandException {
        return executeUndoableCommand();
    }
}
