package seedu.address.logic.commands;

/**
 * Represents an undoable command that adds an object to the address book.
 */
public abstract class AddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "add";
    public static final String COMMAND_ALIAS = "a";
}
