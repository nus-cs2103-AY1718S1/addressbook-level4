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
import seedu.address.model.tag.Tag;

//@@author alexanderleegs
/**
 * Adds a tag to an existing person in the address book.
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtag";
    public static final String COMMAND_ALIAS = "at";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds a tag to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "TAG NAME (one alphanumeric tag only)\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "friends";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Added Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_TAG = "This person already has this tag.";

    private final Index index;
    private final Tag newTag;

    /**
     * @param index of the person in the filtered person list to edit
     * @param newTag to be added to the person
     */
    public AddTagCommand(Index index, Tag newTag) {
        requireNonNull(index);

        this.index = index;
        this.newTag = newTag;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        Set<Tag> oldTags = new HashSet<Tag>(personToEdit.getTags());
        if (oldTags.contains(newTag)) {
            throw new CommandException(MESSAGE_DUPLICATE_TAG);
        }
        Person editedPerson = new Person(personToEdit);
        oldTags.add(newTag);
        editedPerson.setTags(oldTags);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Not creating a new person");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        return new CommandResult(String.format(MESSAGE_ADD_TAG_SUCCESS, newTag.tagName));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagCommand)) {
            return false;
        }

        // state check
        AddTagCommand toCompare = (AddTagCommand) other;
        return index.equals(toCompare.index)
                && newTag.equals(toCompare.newTag);
    }
}
