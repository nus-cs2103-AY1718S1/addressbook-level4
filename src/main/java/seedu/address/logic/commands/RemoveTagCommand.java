package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author yewshengkai
/**
 * Tag specified will be removed from addressbook.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";
    public static final String COMMAND_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Tag specified will be removed from addressbook. "
            + "Parameters: "
            + COMMAND_WORD + "friends ";

    public static final String MESSAGE_SUCCESS = "Tag specified removed from addressbook: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book";
    public static final String MESSAGE_PERSON_TAG_NOT_FOUND = "Person with specified tag does "
            + "not exist in addressbook.";

    private Tag tags;

    /**
     * Creates an RemoveTagCommand to remove the specified {@code tag}
     */
    public RemoveTagCommand(Tag tags) {
        this.tags = tags;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        try {
            model.removeTag(tags);
            return new CommandResult(String.format(MESSAGE_SUCCESS, tags));
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException e) {
            throw new CommandException(MESSAGE_PERSON_TAG_NOT_FOUND);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && tags.equals(((RemoveTagCommand) other).tags));
    }
}
