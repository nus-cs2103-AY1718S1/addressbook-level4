package seedu.room.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.logic.commands.exceptions.TagNotFoundException;

/**
 * Adds a person to the address book.
 */
public class HighlightCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "highlight";
    public static final String COMMAND_ALIAS = "hl";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Highlights names with the specified tag. "
            + "Parameters: " + "tag."
            + "Example: " + COMMAND_WORD + " "
            + "Unregistered";

    public static final String MESSAGE_SUCCESS = "Highlighted persons with tag: ";
    public static final String MESSAGE_TAG_NOT_FOUND = "Tag not found: ";

    private final String highlightTag;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public HighlightCommand(String highlightTag) {
        this.highlightTag = highlightTag;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.updateHighlightStatus(highlightTag);
            return new CommandResult(MESSAGE_SUCCESS + highlightTag);
        } catch (TagNotFoundException e) {
            throw new CommandException(MESSAGE_TAG_NOT_FOUND + highlightTag);
        }
    }
}
