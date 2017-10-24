package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.TagNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes a tag identified using its name from the address book.
 */
public class DeleteTagCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "deletetag";
    public static final String COMMAND_ALIAS = "dt";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes a given tag from a specified person.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "TAG NAME (one alphanumeric tag only)\n"
            + "Example: " + COMMAND_WORD + " 1" + " friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_NO_TAG = "This person does not have this tag.";

    private final Tag targetTag;
    private Index index = null;

    public DeleteTagCommand(Index index, Tag targetTag) {
        requireNonNull(index);

        this.index = index;
        this.targetTag = targetTag;
    }

    public DeleteTagCommand(Tag targetTag) {
        this.targetTag = targetTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        if (index == null) {
            try {
                model.deleteTag(targetTag);
            } catch (DuplicatePersonException | PersonNotFoundException ex) {
                throw new AssertionError("The target person cannot be missing");
            } catch (TagNotFoundException tnfe) {
                throw new CommandException(Messages.MESSAGE_INVALID_TAG_DISPLAYED);
            }
        } else {
            List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }

            ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

            Set<Tag> oldTags = new HashSet<Tag>(personToEdit.getTags());
            if (!(oldTags.contains(targetTag))) {
                throw new CommandException(MESSAGE_NO_TAG);
            }
            Person editedPerson = new Person(personToEdit);
            oldTags.remove(targetTag);
            editedPerson.setTags(oldTags);

            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new AssertionError("Not creating a new person");
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS, targetTag.tagName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof DeleteTagCommand)) {
            return false;
        }

        // state check
        DeleteTagCommand toCompare = (DeleteTagCommand) other;
        return ((index == null && toCompare.index == null)
                || (index != null && toCompare.index != null && index.equals(toCompare.index)))
                && targetTag.equals(toCompare.targetTag);
    }
}
