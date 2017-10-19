package seedu.address.logic.commands;

import static seedu.address.logic.commands.AddCommand.MESSAGE_DUPLICATE_PERSON;

import java.util.List;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Remove the specified tag from address book
 */
public class RemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "remove";
    public static final String COMMAND_ALIAS = "rm";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Remove the specified tag from a specified person by the index number used "
            + "in the last person listing.\n"
            + "Remove the specified tag in the whole address book by excluding the [INDEX] parameter.\n"
            + "Parameters: TAG INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " friends 1";

    public static final String MESSAGE_REMOVE_SUCCESS = "Removed Tag: %1$s";
    public static final String MESSAGE_TAG_NOT_FOUND = " does not exist in";

    private final Tag tag;
    private final Index index;

    /**
     *
     * @param tag to be removed from address book
     * @param index of the person in the filtered list to remove tag
     */
    public RemoveCommand(Tag tag, Index index)  {
        this.tag = tag;
        this.index = index;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        String successMessage;
        String notFound;

        if (index != null) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            successMessage = MESSAGE_REMOVE_SUCCESS + " from index " + index.getOneBased() + ".";
            notFound = "Tag: " + tag.toString() + MESSAGE_TAG_NOT_FOUND + " index " + index.getOneBased() + ".";
        } else {
            successMessage = MESSAGE_REMOVE_SUCCESS + " from address book.";
            notFound = "Tag: " + tag.toString() + MESSAGE_TAG_NOT_FOUND + " address book.";
        }


        try {
            model.removeTag(tag, index);

        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new CommandException(notFound);
        }
        return new CommandResult(String.format(successMessage, tag));

    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }
        // instanceof handles nulls
        if (!(other instanceof RemoveCommand)) {
            return false;
        }
        // state check
        RemoveCommand e = (RemoveCommand) other;
        if ((index == null) && (e.index == null)) {
            return tag.equals(e.tag);
        }
        return index.equals(e.index)
                && tag.equals(e.tag);
    }

}
