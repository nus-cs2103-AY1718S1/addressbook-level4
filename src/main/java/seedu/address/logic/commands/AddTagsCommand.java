package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

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

/**
 * Adds tags to an existing person in the address book.
 */
public class AddTagsCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtags";
    public static final String COMMAND_ALIAS = "at";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add tags to the person identified "
            + "by the index number used in the last person listing. \n"
            + "Parameters: INDEX (must be a positive integer) "
            + "TAG [TAG] (can add 1 or more tags)... \n"
            + "Example: " + COMMAND_WORD + " 1 "
            + "NUS CS2103 classmate";

    public static final String MESSAGE_ADD_TAGS_SUCCESS = "Added Tag/s to Person: %1$s";
    public static final String MESSAGE_NO_TAG = "One or more tags must be added.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final Set<Tag> tags;

    public AddTagsCommand(Index index, Set<Tag> tags) {
        requireNonNull(index);
        requireNonNull(tags);

        this.index = index;
        this.tags = tags;
    }

    @Override
    protected CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        if (tags.isEmpty()) {
            throw new CommandException(MESSAGE_NO_TAG);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, tags);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            assert(false);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_TAGS_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with the {@code tags} to be added.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit, Set<Tag> tags) {
        assert personToEdit != null;

        Set<Tag> personTags = personToEdit.getTags();
        HashSet<Tag> newTags = new HashSet<Tag>(personTags);
        newTags.addAll(tags);

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getRemark(), newTags);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddTagsCommand)) {
            return false;
        }

        AddTagsCommand e = (AddTagsCommand) other;
        return index.equals(e.index) && tags.equals(e.tags);
    }
}
