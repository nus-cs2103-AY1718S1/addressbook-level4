package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;

import java.util.ArrayList;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Removes specified tag from all persons from the address book.
 */
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "rtag";
    public static final String COMMAND_ALIAS = "rt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes specified tag from all persons.\n"
            + "Parameters: KEYWORD [MORE_KEYWORDS]...\n"
            + "Example: " + COMMAND_WORD + " friends classmates colleagues";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Removed tag(s)";
    public static final String MESSAGE_TAG_NOT_REMOVED = "Tag(s) not removed";

    private final ArrayList<Tag> tagsToRemove;
    private final int noTagsToRemove;

    public RemoveTagCommand(ArrayList<Tag> tagsToRemove, int noTagsToRemove) {

        this.tagsToRemove = tagsToRemove;
        this.noTagsToRemove = noTagsToRemove;

    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        try {
            for (Tag tag : tagsToRemove) {
                model.removeTag(tag);
            }
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }

        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_SUCCESS));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof RemoveTagCommand // instanceof handles nulls
                && tagsToRemove.equals(((RemoveTagCommand) other).tagsToRemove));
    }
}
