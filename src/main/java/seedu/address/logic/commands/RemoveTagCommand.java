package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;


/**
 * Removes a tag from the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes specified tag ";

    public static final String MESSAGE_SUCCESS = "Tag removed";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_NOT_DELETED = "Tag not deleted";

    private final String toRemove;

    /**
     * Creates an RemoveTagCommand to remove the specified {@code Tag}
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
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_NOT_DELETED);
        } catch (IllegalValueException e) {
            throw new CommandException(MESSAGE_NOT_DELETED);
        }
    }
}
