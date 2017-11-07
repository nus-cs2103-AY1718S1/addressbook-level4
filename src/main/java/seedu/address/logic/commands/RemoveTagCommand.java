package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author freesoup
/**
 * Removes a tag from the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";
    public static final String COMMAND_USAGE = COMMAND_WORD + " ";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Removes specified tag\n"
            + "Parameters: TAG (must be a string)\n"
            + "Paramaters: INDEX (must be a positive integer) TAG (must be a string)";

    public static final String MESSAGE_SUCCESS = "Tag removed";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_NOT_DELETED = "Tag not deleted";
    public static final String MESSAGE_EXCEEDTAGNUM = "Please only type one TAG to be removed";

    private final Tag toRemove;
    private final Optional<Index> index;

    /**
     * Creates an RemoveTagCommand to remove the specified {@code Tag} from a (@code Index) if given.
     */
    public RemoveTagCommand (Tag tag) {
        this.toRemove = tag;
        this.index = Optional.ofNullable(null);
    }

    public RemoveTagCommand (Index index, Tag tag) {
        this.toRemove = tag;
        this.index = Optional.of(index);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            if (index.orElse(null) == null) {
                model.removeTag(toRemove);
                return new CommandResult(String.format(MESSAGE_SUCCESS));
            } else {
                model.removeTag(index.get(), toRemove);
                return new CommandResult(String.format(MESSAGE_SUCCESS));
            }
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_NOT_DELETED);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && this.toRemove.equals(((RemoveTagCommand) other).toRemove) // state check
                && this.index.equals(((RemoveTagCommand) other).index)); // state check
    }

}
