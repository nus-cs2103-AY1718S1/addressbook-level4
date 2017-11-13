package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMOVE_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.customField.CustomField;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.phone.Phone;
import seedu.address.model.person.phone.UniquePhoneList;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_ADD_TAG + "TAG_TO_ADD]... "
            + "[" + PREFIX_REMOVE_TAG + "TAG_TO_REMOVE]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Persons at the following indices: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final List<Index> indices;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param indices of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(List<Index> indices, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(indices);
        requireNonNull(editPersonDescriptor);

        this.indices = indices;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        //@@author willxujun
        //loops indices to edit multiple contacts
        for (Index index: indices) {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        for (Index index: indices) {
            ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
            Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
        //@@author
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, indices.toString()));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Photo updatedPhoto = editPersonDescriptor.getPhoto().orElse(personToEdit.getPhoto());
        UniquePhoneList updatedPhoneList = editPersonDescriptor.getUniquePhoneList()
                .orElse(personToEdit.getPhoneList());
        //@@author willxujun
        Set<Tag> updatedTags = new HashSet<>(personToEdit.getTags());
        updateTags(updatedTags, editPersonDescriptor);
        //@@author
        Set<CustomField> updatedCustomFields =
                editPersonDescriptor.getCustomFields().orElse(personToEdit.getCustomFields());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updatedPhoto, updatedPhoneList, updatedTags, updatedCustomFields);
    }

    //@@author willxujun
    /**
     * clears, adds and removes tags according to {@code editPersonDescriptor}.
     * @param updatedTags
     * @param editPersonDescriptor
     */
    public static void updateTags(Set<Tag> updatedTags, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(editPersonDescriptor);
        editPersonDescriptor.getTagsToAdd().ifPresent(tagsToAdd -> {
            if (tagsToAdd.isEmpty()) {
                updatedTags.clear();
            }
        });
        editPersonDescriptor.getTagsToRemove().ifPresent(tagsToRemove -> {
            if (tagsToRemove.isEmpty()) {
                updatedTags.clear();
            }
        });
        editPersonDescriptor.getTagsToAdd().ifPresent(updatedTags::addAll);
        editPersonDescriptor.getTagsToRemove().ifPresent(updatedTags::removeAll);
    }
    //@@author

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
        EditCommand e = (EditCommand) other;
        return indices.equals(e.indices)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Photo photo;
        private UniquePhoneList uniquePhoneList;
        private Set<Tag> tagsToAdd;
        private Set<Tag> tagsToRemove;
        private Set<CustomField> customFields;

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.photo = toCopy.photo;
            this.uniquePhoneList = toCopy.uniquePhoneList;
            this.tagsToAdd = toCopy.tagsToAdd;
            this.tagsToRemove = toCopy.tagsToRemove;
            this.customFields = toCopy.customFields;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address,
                    this.photo, this.uniquePhoneList, this.tagsToAdd, this.tagsToRemove, this.customFields);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
        }

        public Optional<Photo> getPhoto() {
            return Optional.ofNullable(photo);
        }

        public Optional<UniquePhoneList> getUniquePhoneList() {
            return Optional.ofNullable(uniquePhoneList);
        }

        //@@author willxujun
        public void setTagsToAdd(Set<Tag> tagsToAdd) {
            this.tagsToAdd = tagsToAdd;
        }

        public Optional<Set<Tag>> getTagsToAdd() {
            return Optional.ofNullable(tagsToAdd);
        }

        public void setTagsToRemove(Set<Tag> tagsToRemove) {
            this.tagsToRemove = tagsToRemove;
        }

        public Optional<Set<Tag>> getTagsToRemove() {
            return Optional.ofNullable(tagsToRemove);
        }
        //@@author

        public Optional<Set<CustomField>> getCustomFields() {
            return Optional.ofNullable(customFields);
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getPhoto().equals(e.getPhoto())
                    && getUniquePhoneList().equals(e.getUniquePhoneList())
                    && getTagsToAdd().equals(e.getTagsToAdd())
                    && getTagsToRemove().equals(e.getTagsToRemove())
                    && getCustomFields().equals(e.getCustomFields());
        }
    }
}
