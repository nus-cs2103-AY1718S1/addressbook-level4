package seedu.address.logic.commands;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.tag.Tag;

import static java.util.Objects.requireNonNull;

/**
 * Adds a person to the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes specified tag ";

    public static final String MESSAGE_SUCCESS = "Tag removed";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";


    private final String toRemove;

    /**
     * Creates an RemoveTagCommand to add the specified {@code ReadOnlyPerson}
     */
    public RemoveTagCommand (String tag) {
        toRemove = tag.trim();
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.removeTag(new Tag(toRemove));
            return new CommandResult(String.format(MESSAGE_SUCCESS));
        } catch (Exception e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }
}