package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class TagAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "t-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add tag to the person(s) identified "
            + "by the index number used in the last person listing. "
            + "Input tag will append to the existing tags.\n"
            + "Parameters: [TAG] "
            + "INDEX1 INDEX2... (must be a positive integer)"
            + "Example: " + COMMAND_WORD + " [friends] "
            + "1 2 3 ";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final ArrayList<Index> index;
    private final TagAddDescriptor tagAddDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param tagAddDescriptor details to edit the person with
     */
    public TagAddCommand(ArrayList<Index> index, TagAddDescriptor tagAddDescriptor) {
        requireNonNull(index);
        requireNonNull(tagAddDescriptor);

        this.index = index;
        this.tagAddDescriptor = new TagAddDescriptor(tagAddDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.get(0).getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.get(0).getZeroBased());
        Set<Tag> originalTagList = personToEdit.getTags();
        tagAddDescriptor.getTags().addAll(originalTagList);
        Person editedPerson = createEditedPerson(personToEdit, tagAddDescriptor);
        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));

    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             TagAddDescriptor tagAddDescriptor) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = tagAddDescriptor.getTags();
        DateAdded updateDateAdded = personToEdit.getDateAdded();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags, updateDateAdded);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditCommand)) {
            return false;
        }

        // state check
        TagAddCommand e = (TagAddCommand) other;
        return index.equals(e.index)
                && tagAddDescriptor.equals(e.tagAddDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class TagAddDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private DateAdded dateAdded;

        public TagAddDescriptor() {}

        public TagAddDescriptor(TagAddDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.tags = toCopy.tags;
            this.dateAdded = toCopy.dateAdded;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.tags);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Set<Tag> getTags() {
            return tags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof TagAddDescriptor)) {
                return false;
            }

            // state check
            TagAddDescriptor e = (TagAddDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }
}
