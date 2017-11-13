package seedu.address.logic.commands;

/**
 * Represents an undoable command that edits an object in the address book.
 */
public abstract class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
}
