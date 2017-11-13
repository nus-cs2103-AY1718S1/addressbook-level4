package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHOTO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_USERID;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthdate;
import seedu.address.model.person.EmailAddress;
import seedu.address.model.person.Name;
import seedu.address.model.person.NusModules;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UserId;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing.\n"
            + "Apart from tags, existing values will be overwritten by the input values.\n"
            + "Tags will be added if person does not have the tag and deleted otherwise.\n"
            + "You can remove all the person's tags by typing `t/` without specifying any tags after it.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL_ADDRESS + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_PHOTO + "PHOTO] "
            + "[" + PREFIX_BIRTHDATE + "BIRTHDATE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "[" + PREFIX_USERID + "INSTAGRAM ID]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL_ADDRESS + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;
    private Photo originalPhoto;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException, IOException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (IOException ioe) {
            throw new AssertionError("The file must exist");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    //@@author ritchielq
    /**
     * Creates new list of tags by taking tags in personToEdit,
     * removing duplicated tags in editPersonDescriptor,
     * and adding in all other tags from editPersonDescriptor
     *
     * @param personToEdit
     * @param editPersonDescriptor
     */
    private static Set<Tag> processTags(ReadOnlyPerson personToEdit,
                                    EditPersonDescriptor editPersonDescriptor) {
        Set<Tag> updatedTags = personToEdit.getTags();

        if (editPersonDescriptor.getTags().isPresent()) {
            Set<Tag> tagsToToggle = new HashSet<Tag>(editPersonDescriptor.getTags().get());

            if (tagsToToggle.isEmpty()) {
                return tagsToToggle;
            } else {
                for (Tag tag : updatedTags) {
                    if (!tagsToToggle.remove(tag)) {
                        tagsToToggle.add(tag);
                    }
                }
            }

            return tagsToToggle;
        } else {
            return updatedTags;
        }

    }

    //@@author
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        EmailAddress updatedEmail = editPersonDescriptor.getEmailAddress().orElse(personToEdit.getEmailAddress());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Photo updatedPhoto = editPersonDescriptor.getPhoto().orElse(personToEdit.getPhoto());
        Set<Tag> updatedTags = processTags(personToEdit, editPersonDescriptor);
        Birthdate updatedBirthdate = editPersonDescriptor.getBirthdate().orElse(personToEdit.getBirthdate());
        UserId updatedId = editPersonDescriptor.getUserId().orElse(personToEdit.getUserId());
        NusModules copyNusModules = personToEdit.getNusModules();

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPhoto, updatedTags,
                updatedBirthdate, copyNusModules, updatedId);

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
        EditCommand e = (EditCommand) other;
        return index.equals(e.index)
                && editPersonDescriptor.equals(e.editPersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Phone phone;
        private EmailAddress emailAddress;
        private Address address;
        private Photo photo;
        private Set<Tag> tags;
        private Birthdate birthdate;
        private UserId id;

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.emailAddress = toCopy.emailAddress;
            this.address = toCopy.address;
            this.photo = toCopy.photo;
            this.tags = toCopy.tags;
            this.birthdate = toCopy.birthdate;
            this.id = toCopy.id;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.emailAddress,
                    this.address, this.photo, this.tags, this.birthdate, this.id);
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

        public void setEmailAddress(EmailAddress emailAddress) {
            this.emailAddress = emailAddress;
        }

        public Optional<EmailAddress> getEmailAddress() {
            return Optional.ofNullable(emailAddress);
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
        }

        public Optional<Photo> getPhoto() {
            return Optional.ofNullable(photo);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public Optional<Birthdate> getBirthdate() {
            return Optional.ofNullable(birthdate);
        }

        public void setBirthdate(Birthdate birthdate) {
            this.birthdate = birthdate;
        }

        public Optional<UserId> getUserId() {
            return Optional.ofNullable(id);
        }

        public void setUserId(UserId id) {
            this.id = id;
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
            if (!(other instanceof EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPersonDescriptor e = (EditPersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmailAddress().equals(e.getEmailAddress())
                    && getAddress().equals(e.getAddress())
                    && getPhoto().equals(e.getPhoto())
                    && getTags().equals(e.getTags())
                    && getBirthdate().equals(e.getBirthdate())
                    && getUserId().equals(e.getUserId());
        }
    }
}
