package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ListingUnit;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.predicates.UniqueAddressPredicate;
import seedu.address.model.person.predicates.UniqueEmailPredicate;
import seedu.address.model.person.predicates.UniquePhonePredicate;
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
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_EDIT_ADDRESS_SUCCESS = "Edited Address: %1$s";
    public static final String MESSAGE_EDIT_EMAIL_SUCCESS = "Edited Email: %1$s";
    public static final String MESSAGE_EDIT_PHONE_SUCCESS = "Edited Phone: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;
    private final String attributeValue;


    /**
     * @param index                of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        attributeValue = null;
    }

    /**
     * @param index       of the address in the filtered address list to edit
     * @param attributeValue the new edited address
     */
    public EditCommand(Index index, String attributeValue) {
        requireNonNull(index);
        requireNonNull(attributeValue);

        this.index = index;
        this.editPersonDescriptor = null;
        this.attributeValue = attributeValue;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        switch (ListingUnit.getCurrentListingUnit()) {

        case ADDRESS:
            return executeEditAddress(lastShownList.get(index.getZeroBased()).getAddress());

        case PHONE:
            return executeEditPhone(lastShownList.get(index.getZeroBased()).getPhone());

        case EMAIL:
            return executeEditEmail(lastShownList.get(index.getZeroBased()).getEmail());

        default:
            return executeEditPerson(lastShownList.get(index.getZeroBased()));
        }
    }

    /**
     * Edit the person with updated information.
     */
    private CommandResult executeEditPerson(ReadOnlyPerson personToEdit) throws CommandException {
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);
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
     * Edit the address, all persons with the edited address is updated with a new given address.
     */
    private CommandResult executeEditAddress(Address addressToEdit) throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> personList = model.getFilteredPersonList();
        Address editedAddress = null;
        try {
            editedAddress = new Address(attributeValue);
            for (ReadOnlyPerson p : personList) {

                ReadOnlyPerson curEditedPerson;
                if (p.getAddress().equals(addressToEdit)) {
                    curEditedPerson = new Person(p.getName(), p.getPhone(), p.getEmail(),
                            editedAddress, p.getTags());
                    model.updatePerson(p, curEditedPerson);

                }
            }
            model.updateFilteredPersonList(new UniqueAddressPredicate(model.getUniqueAdPersonSet()));
            return new CommandResult(String.format(MESSAGE_EDIT_ADDRESS_SUCCESS, editedAddress));
        } catch (IllegalValueException ive) {
            model.updateFilteredPersonList(new UniqueAddressPredicate(model.getUniqueAdPersonSet()));
            throw new CommandException(ive.getMessage());
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
    }

    /**
     * Edit the email, all persons with the edited email is updated with a new given email.
     */
    private CommandResult executeEditEmail(Email emailToEdit) throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> personList = model.getFilteredPersonList();
        Email editedEmail = null;
        try {
            editedEmail = new Email(attributeValue);
            for (ReadOnlyPerson p : personList) {

                ReadOnlyPerson curEditedPerson;
                if (p.getEmail().equals(emailToEdit)) {
                    curEditedPerson = new Person(p.getName(), p.getPhone(), editedEmail,
                            p.getAddress(), p.getTags());
                    model.updatePerson(p, curEditedPerson);

                }
            }
            model.updateFilteredPersonList(new UniqueEmailPredicate(model.getUniqueEmailPersonSet()));
            return new CommandResult(String.format(MESSAGE_EDIT_EMAIL_SUCCESS, editedEmail));
        } catch (IllegalValueException ive) {
            model.updateFilteredPersonList(new UniqueEmailPredicate(model.getUniqueEmailPersonSet()));
            throw new CommandException(ive.getMessage());
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
    }

    /**
     * Edit the phone, all persons with the edited phone is updated with a new given phone.
     */
    private CommandResult executeEditPhone(Phone phoneToEdit) throws CommandException {
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        ObservableList<ReadOnlyPerson> personList = model.getFilteredPersonList();
        Phone editedPhone = null;
        try {
            editedPhone = new Phone(attributeValue);
            for (ReadOnlyPerson p : personList) {

                ReadOnlyPerson curEditedPerson;
                if (p.getPhone().equals(phoneToEdit)) {
                    curEditedPerson = new Person(p.getName(), editedPhone, p.getEmail(),
                            p.getAddress(), p.getTags());
                    model.updatePerson(p, curEditedPerson);
                }
            }
            model.updateFilteredPersonList(new UniquePhonePredicate(model.getUniquePhonePersonSet()));
            return new CommandResult(String.format(MESSAGE_EDIT_PHONE_SUCCESS, editedPhone));
        } catch (IllegalValueException ive) {
            model.updateFilteredPersonList(new UniqueEmailPredicate(model.getUniqueEmailPersonSet()));
            throw new CommandException(ive.getMessage());
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

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
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedTags);
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
        if (editPersonDescriptor != null && e.editPersonDescriptor != null) {
            return index.equals(e.index)
                    && editPersonDescriptor.equals(e.editPersonDescriptor);
        } else {
            return index.equals(e.index)
                    && attributeValue.equals(e.attributeValue);
        }
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
        private Set<Tag> tags;

        public EditPersonDescriptor() {
        }

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address, this.tags);
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
                    && getTags().equals(e.getTags());
        }
    }
}
