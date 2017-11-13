//@@author WangJieee
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.DuplicateDataException;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.NokName;
import seedu.address.model.person.NokPhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Adds the specified tags to the tag list of an existing person in the address book.
 */
public class AddTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addtag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Adds the entered tag(s) to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[TAG]..."
            + "Example: " + COMMAND_WORD + " 1 " + "friends classmates ";

    public static final String MESSAGE_ADD_TAG_PERSON_SUCCESS = "Tags updated!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_DUPLICATE_TAG = "You have entered a duplicate tag! Tag name: ";

    private final Index index;
    private final Set<Tag> tagsToAdd;

    /**
     * @param index of the person in the filtered person list to edit
     * @param tagsToAdd details to add existing tags with
     */
    public AddTagCommand(Index index, Set<Tag> tagsToAdd) {
        requireNonNull(index);
        requireNonNull(tagsToAdd);

        this.index = index;
        this.tagsToAdd = tagsToAdd;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createUpdatedPerson(personToEdit, tagsToAdd);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_TAG_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code tagsToAdd}.
     */
    private static Person createUpdatedPerson(ReadOnlyPerson personToEdit,
                                             Set<Tag> tagsToAdd) throws CommandException {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Avatar updatedAvatar = personToEdit.getAvatar();
        NokName updatedNokName = personToEdit.getNokName();
        NokPhone updatedNokPhone = personToEdit.getNokPhone();

        Set<Tag> existingTags = personToEdit.getTags();
        Set<Tag> updatedTags = new HashSet<>();
        try {
            updatedTags = getUpdatedTags(existingTags, tagsToAdd);
        } catch (DuplicateTagException dte) {
            throw new CommandException(dte.getMessage());
        }
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedAvatar,
                          updatedNokName, updatedNokPhone, updatedTags);
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
        AddTagCommand a = (AddTagCommand) other;
        return index.equals(a.index)
                && tagsToAdd.equals(a.tagsToAdd);
    }

    /**
     * Creates and returns a new tag list that combines existing tags and new tags added
     * with no duplicate tags
     */
    public static HashSet<Tag> getUpdatedTags(Set<Tag> oldTags, Set<Tag> tagsToAdd) throws DuplicateTagException {
        HashSet<Tag> updatedTags = new HashSet<>(oldTags);
        for (Tag toAdd : tagsToAdd) {
            requireNonNull(toAdd);
            if (oldTags.contains(toAdd)) {
                throw new DuplicateTagException(toAdd.tagName);
            }
            updatedTags.add(toAdd);
        }
        return updatedTags;
    }

    /**
     * Signals that an operation would have violated the 'no duplicates' property of the list.
     */
    public static class DuplicateTagException extends DuplicateDataException {
        protected DuplicateTagException(String tagName) {
            super(MESSAGE_DUPLICATE_TAG + tagName);
        }

        @Override
        public String getMessage() {
            return super.getMessage();
        }
    }
}
