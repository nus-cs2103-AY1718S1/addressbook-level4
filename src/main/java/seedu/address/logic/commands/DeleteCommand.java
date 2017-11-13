package seedu.address.logic.commands;

/**
 * Represents an undoable command that deletes an object from the address book.
 */
public abstract class DeleteCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "delete";
    public static final String COMMAND_ALIAS = "d";
}
