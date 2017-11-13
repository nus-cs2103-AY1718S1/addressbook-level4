package seedu.address.logic.commands;
//@@author pjunwei95
import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Deletes tags from a person identified using it's last displayed index from the address book.
 */
public class DeleteTagCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "deletetag";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Deletes the tag identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer) " + PREFIX_TAG + "TAG (must match tag)\n"
            + "Example: " + COMMAND_WORD + " 1 t/friends";

    public static final String MESSAGE_DELETE_TAG_SUCCESS = "Deleted Tag: %1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_NOT_DELETED = "At least one field to delete must be provided.";
    public static final String MESSAGE_NOT_EXISTING_TAGS = "The tag provided is invalid. Please check again.";

    private final Index index;
    private final DeleteTagDescriptor deleteTagDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param deleteTagDescriptor details of which tags to delete
     */
    public DeleteTagCommand(Index index, DeleteTagDescriptor deleteTagDescriptor) {
        requireNonNull(index);
        requireNonNull(deleteTagDescriptor);

        this.index = index;
        this.deleteTagDescriptor = new DeleteTagDescriptor(deleteTagDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());

        try {
            checkTagExist(personToEdit, deleteTagDescriptor);
        } catch (CommandException net) {
            throw new CommandException(String.format(MESSAGE_NOT_EXISTING_TAGS));
        }

        Person editedPerson = createTagDeletedPerson(personToEdit, new DeleteTagDescriptor(deleteTagDescriptor));

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);

        return new CommandResult(String.format(MESSAGE_DELETE_TAG_SUCCESS,
                deleteTagDescriptor.getTags().orElse(editedPerson.getTags())));
    }
    /**
     * Check whether a given tag exists in address book.
     * @param t tag that is to be checked
     */
    private boolean isExistingTagName(Tag t) {
        for (Tag tag : model.getAddressBook().getTagList()) {
            if (tag.tagName.equals(t.tagName)) {
                return true;
            }
        }
        return false;
    }
    /**
     * Check whether a given tag exists in current database(including the person).
     * Throws {@code CommandException} if tag does not exist
     * @param personToEdit the person that is to be checked
     * @param deleteTagDescriptor details of which tags to delete
     */
    private void checkTagExist(ReadOnlyPerson personToEdit,
                               DeleteTagDescriptor deleteTagDescriptor) throws CommandException {
        Set<Tag> nonExistingTagList = new HashSet<>();
        Set<Tag> personTags = new HashSet<>(personToEdit.getTags());
        Set<Tag> tagsToDelete = deleteTagDescriptor.getTags().orElse(personToEdit.getTags());

        // Check whether tags are not existing tags in address book
        for (Tag tag: tagsToDelete) {
            if (!isExistingTagName(tag)) {
                nonExistingTagList.add(tag);
            }
        }
        //Check whether tags are not existing tags in personTags
        for (Tag tag: tagsToDelete) {
            if (!personTags.contains(tag)) {
                nonExistingTagList.add(tag);
            }
        }

        if (nonExistingTagList.size() != 0) {
            throw new CommandException(String.format(MESSAGE_NOT_EXISTING_TAGS));
        }
    }


    /**
     * Creates and returns a {@code Person} with the tags of {@code personToEdit}
     * deleted with {@code DeleteTagDescriptor}.
     */
    public Person createTagDeletedPerson(ReadOnlyPerson personToEdit,
                                             DeleteTagDescriptor deleteTagDescriptor) {
        assert personToEdit != null;

        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        Set<Tag> tagsToDelete = deleteTagDescriptor.getTags().orElse(personToEdit.getTags());
        updatedTags.removeAll(tagsToDelete);

        return new Person(personToEdit.getName(), personToEdit.getPhone(), personToEdit.getEmail(),
                personToEdit.getAddress(), personToEdit.getDateOfBirth(), personToEdit.getRemark(),
                personToEdit.getImage(), personToEdit.getUsername(), updatedTags);
    }

    /**
     * Stores the details of which tags to be deleted.
     */
    public static class DeleteTagDescriptor {
        private Set<Tag> tags;

        public DeleteTagDescriptor() {
        }

        public DeleteTagDescriptor(DeleteTagDescriptor toCopy) {
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if tag is edited.
         */
        public boolean isTagDeleted() {
            return CollectionUtil.isAnyNonNull(this.tags);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof DeleteTagDescriptor)) {
                return false;
            }

            // state check
            DeleteTagDescriptor e = (DeleteTagDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }
}
