//@@author WangJieee
package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
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
public class RemoveTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "removetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Removes the entered tag(s) to the person identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[TAG]..."
            + "Example: " + COMMAND_WORD + " 1 " + "friends classmates ";

    public static final String MESSAGE_REMOVE_TAG_PERSON_SUCCESS = "Tags updated!";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_TAG_NOT_FOUND = "This person does not have the tag! Tag name: ";

    private final Index index;
    private final Set<Tag> tagsToRemove;

    /**
     * @param index of the person in the filtered person list to edit
     * @param tagsToRemove details to remove existing tags with
     */
    public RemoveTagCommand(Index index, Set<Tag> tagsToRemove) {
        requireNonNull(index);
        requireNonNull(tagsToRemove);

        this.index = index;
        this.tagsToRemove = tagsToRemove;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createUpdatedPerson(personToEdit, tagsToRemove);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_REMOVE_TAG_PERSON_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code tagsToRemove}.
     */
    private static Person createUpdatedPerson(ReadOnlyPerson personToEdit,
                                              Set<Tag> tagsToRemove) throws CommandException {
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
            updatedTags = getUpdatedTags(existingTags, tagsToRemove);
        } catch (TagNotFoundException tnfe) {
            throw new CommandException(tnfe.getMessage());
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
        if (!(other instanceof RemoveTagCommand)) {
            return false;
        }

        // state check
        RemoveTagCommand a = (RemoveTagCommand) other;
        return index.equals(a.index)
                && tagsToRemove.equals(a.tagsToRemove);
    }

    /**
     * Creates and returns a new tag list that removes specified tags from existing tags
     */
    public static HashSet<Tag> getUpdatedTags(Set<Tag> oldTags, Set<Tag> tagsToRemove) throws TagNotFoundException {
        HashSet<Tag> updatedTags = new HashSet<>(oldTags);
        for (Tag toRemove : tagsToRemove) {
            requireNonNull(toRemove);
            if (!oldTags.contains(toRemove)) {
                throw new TagNotFoundException(toRemove.tagName);
            }
            updatedTags.remove(toRemove);
        }
        return updatedTags;
    }

    /**
     * Signals that an operation does not fulfill the constraint that all the tags entered must exist in the person
     */
    public static class TagNotFoundException extends IllegalValueException {
        protected TagNotFoundException(String tagName) {
            super(MESSAGE_TAG_NOT_FOUND + tagName);
        }

        @Override
        public String getMessage() {
            return super.getMessage();
        }
    }
}
