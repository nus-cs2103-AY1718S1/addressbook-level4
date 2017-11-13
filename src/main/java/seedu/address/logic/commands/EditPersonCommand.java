package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Avatar;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits a person in the address book.
 */
public class EditPersonCommand extends EditCommand {
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_REMARK + "REMARK] "
            + "[" + PREFIX_AVATAR + "AVATAR] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";
    public static final String MESSAGE_SUCCESS = "Edited Person: \n%1$s";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    public static final String MESSAGE_ALL_FIELDS_PRIVATE = "At least one field to be edited must be public.";

    private static boolean areFieldsAllPrivate = true;
    private final EditPersonDescriptor editPersonDescriptor;
    private final Index index;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditPersonCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        try {
            if (index.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
            Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
            editedPerson.setPrivacyLevel(model.getPrivacyLevel());
            model.updatePerson(personToEdit, editedPerson);
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            return new CommandResult(String.format(MESSAGE_SUCCESS, editedPerson));
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (IllegalArgumentException e) {
            throw new CommandException(MESSAGE_ALL_FIELDS_PRIVATE);
        }

    }

    //@@author jeffreygohkw
    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     * A person with private fields cannot be edited
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor)
            throws IllegalArgumentException {
        assert personToEdit != null;

        Name updatedName;
        Phone updatedPhone;
        Email updatedEmail;
        Address updatedAddress;
        Remark updatedRemark;
        Set<Tag> updatedTags;
        Boolean updateFavourite;
        Avatar updatedAvatar;

        areFieldsAllPrivate = true;
        updatedName = createUpdatedName(personToEdit, editPersonDescriptor);

        updatedPhone = createUpdatedPhone(personToEdit, editPersonDescriptor);

        updatedEmail = createUpdatedEmail(personToEdit, editPersonDescriptor);

        updatedAddress = createUpdatedAddress(personToEdit, editPersonDescriptor);

        updatedRemark = createUpdatedRemark(personToEdit, editPersonDescriptor);

        updatedTags = createUpdatedTags(personToEdit, editPersonDescriptor);

        updateFavourite = createUpdatedFavourite(personToEdit, editPersonDescriptor);

        updatedAvatar = createUpdatedAvatar(personToEdit, editPersonDescriptor);

        if (areFieldsAllPrivate) {
            throw new IllegalArgumentException();
        }
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress,
                updateFavourite, updatedRemark, updatedAvatar, updatedTags);
    }

    /**
     * Creates an updated (@code Name) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Name) from either the personToEdit or the editPersonDescriptor depending on privacy
     */
    private static Name createUpdatedName(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Name updatedName;
        if (!personToEdit.getName().getIsPrivate()) {
            updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
            if (editPersonDescriptor.getName().isPresent()) {
                areFieldsAllPrivate = false;
            }
        } else {
            updatedName = personToEdit.getName();
        }
        return updatedName;
    }

    /**
     * Creates an updated (@code Phone) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Phone) from either the personToEdit or the editPersonDescriptor
     * depending on privacy and the input
     */
    private static Phone createUpdatedPhone(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Phone updatedPhone;
        if (!personToEdit.getPhone().getIsPrivate()) {
            updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
            if (editPersonDescriptor.getPhone().isPresent()) {
                areFieldsAllPrivate = false;
            }
        } else {
            updatedPhone = personToEdit.getPhone();
        }
        return updatedPhone;
    }

    /**
     * Creates an updated (@code Email) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Email) from either the personToEdit or the editPersonDescriptor
     * depending on privacy and the input
     */
    private static Email createUpdatedEmail(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Email updatedEmail;
        if (!personToEdit.getEmail().getIsPrivate()) {
            updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
            if (editPersonDescriptor.getEmail().isPresent()) {
                areFieldsAllPrivate = false;
            }
        } else {
            updatedEmail = personToEdit.getEmail();
        }
        return updatedEmail;
    }

    /**
     * Creates an updated (@code Address) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Address) from either the personToEdit or the editPersonDescriptor
     * depending on privacy and the input
     */
    private static Address createUpdatedAddress(ReadOnlyPerson personToEdit,
                                                EditPersonDescriptor editPersonDescriptor) {
        Address updatedAddress;
        if (!personToEdit.getAddress().getIsPrivate()) {
            updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
            if (editPersonDescriptor.getAddress().isPresent()) {
                areFieldsAllPrivate = false;
            }
        } else {
            updatedAddress = personToEdit.getAddress();
        }
        return updatedAddress;
    }
    //**author charlesgoh
    /**
     * Creates an updated (@code Remark) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Remark) from either the personToEdit or the editPersonDescriptor
     * depending on privacy and the input
     */
    private static Remark createUpdatedRemark(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Remark updatedRemark;
        if (!personToEdit.getRemark().getIsPrivate()) {
            updatedRemark = editPersonDescriptor.getRemark().orElse(personToEdit.getRemark());
            if (editPersonDescriptor.getRemark().isPresent()) {
                areFieldsAllPrivate = false;
            }
        } else {
            updatedRemark = personToEdit.getRemark();
        }
        return updatedRemark;
    }

    /**
     * Creates an updated (@code Avatar) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Avatar) from either the personToEdit or the editPersonDescriptor
     * depending on privacy and the input
     */
    private static Avatar createUpdatedAvatar(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Avatar updatedAvatar = editPersonDescriptor.getAvatar().orElse(personToEdit.getAvatar());
        if (editPersonDescriptor.getAvatar().isPresent()) {
            areFieldsAllPrivate = false;
        }
        return updatedAvatar;
    }
    //author
    /**
     * Creates an updated (@code Tag) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Tag) from either the personToEdit or the editPersonDescriptor depending on the input
     */
    private static Set<Tag> createUpdatedTags(ReadOnlyPerson personToEdit, EditPersonDescriptor editPersonDescriptor) {
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());
        if (editPersonDescriptor.getTags().isPresent()) {
            areFieldsAllPrivate = false;
        }
        return updatedTags;
    }

    /**
     * Creates an updated (@code Favourite) for use in createEditedPerson
     * @param personToEdit The person to edit
     * @param editPersonDescriptor Edited with this editPersonDescriptor
     * @return A new (@code Favourite) from either the personToEdit or the editPersonDescriptor depending on the input
     */
    private static Boolean createUpdatedFavourite(ReadOnlyPerson personToEdit,
                                                  EditPersonDescriptor editPersonDescriptor) {
        Boolean updateFavourite = editPersonDescriptor.getFavourite().orElse(personToEdit.getFavourite());
        if (editPersonDescriptor.getFavourite().isPresent()) {
            areFieldsAllPrivate = false;
        }
        return updateFavourite;
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof EditPersonCommand)) {
            return false;
        }

        EditPersonCommand e = (EditPersonCommand) other;
        return index.equals(e.index) && editPersonDescriptor.equals(e.editPersonDescriptor);
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
        private Boolean favourite;
        private Remark remark;
        private Avatar avatar;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.favourite = toCopy.favourite;
            this.remark = toCopy.remark;
            this.avatar = toCopy.avatar;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address, this.remark,
                    this.avatar, this.tags);
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

        public Optional<Email> getEmail()  {
            return Optional.ofNullable(email);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }
        //@@author wangyiming1019
        public void setFavourite(Boolean favourite) {
            this.favourite = favourite;
        }

        public Optional<Boolean> getFavourite() {
            return Optional.ofNullable(favourite);
        }
        //@@author
        public void setRemark(Remark remark) {
            this.remark = remark;
        }

        public Optional<Remark> getRemark() {
            return Optional.ofNullable(remark);
        }

        public void setAvatar(Avatar avatar) {
            this.avatar = avatar;
        }

        public Optional<Avatar> getAvatar() {
            return Optional.ofNullable(avatar);
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
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getFavourite().equals(e.getFavourite())
                    && getRemark().equals(e.getRemark())
                    && getAvatar().equals(e.getAvatar())
                    && getTags().equals(e.getTags());
        }
    }
}
