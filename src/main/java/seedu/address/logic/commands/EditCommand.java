package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEBT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.util.DateUtil.formatDate;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateRepaid;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_WORD_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing or of the currently selected person if no"
            + "index is specified. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (optional, must be a positive integer if present) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_POSTAL_CODE + "POSTAL CODE] "
            + "[" + PREFIX_DEBT + "DEBT] "
            + "[" + PREFIX_INTEREST + "INTEREST] "
            + "[" + PREFIX_DEADLINE + "DEADLINE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example 1: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + "Example 2: " + COMMAND_WORD
            + PREFIX_POSTAL_CODE + " " + "123456";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index targetIndex;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(editPersonDescriptor);

        this.targetIndex = null;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index targetIndex, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(targetIndex);
        requireNonNull(editPersonDescriptor);

        this.targetIndex = targetIndex;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ReadOnlyPerson personToEdit = selectPerson(targetIndex);
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        try {
            editedPerson = listSyncChecks(personToEdit, editedPerson);
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
     * Does the appropriate list status checks on {@code personToEdit}
     * and changes the list status of {@code editedPerson} accordingly
     * @return editedPerson
     */
    private Person listSyncChecks(ReadOnlyPerson personToEdit, Person editedPerson) {
        if (personToEdit.isWhitelisted() && editedPerson.getDebt().toNumber() > 0) {
            editedPerson.setIsWhitelisted(false);
        } else if (!personToEdit.isWhitelisted() && !personToEdit.isBlacklisted()
                && editedPerson.getDebt().toNumber() == 0) {
            editedPerson.setIsWhitelisted(true);
            editedPerson.setDateRepaid(new DateRepaid(formatDate(new Date())));
        }
        return editedPerson;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) throws CommandException {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        PostalCode updatedPostalCode = editPersonDescriptor.getPostalCode().orElse(personToEdit.getPostalCode());
        Debt updatedDebt = editPersonDescriptor.getDebt().orElse(personToEdit.getDebt());
        Interest updatedInterest = editPersonDescriptor.getInterest().orElse(personToEdit.getInterest());
        Deadline updatedDeadline = editPersonDescriptor.getDeadline().orElse(personToEdit.getDeadline());
        try {
            updatedDeadline.checkDateBorrow(personToEdit.getDateBorrow().getDate());
        } catch (IllegalValueException ive) {
            throw new CommandException("Deadline cannot be before date borrow.");
        }
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        Person personCreated = new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedPostalCode,
                updatedDebt, updatedInterest, updatedDeadline, updatedTags);

        personCreated.setDateBorrow(personToEdit.getDateBorrow());
        personCreated.setDateRepaid(personToEdit.getDateRepaid());
        personCreated.setIsBlacklisted(personToEdit.isBlacklisted());
        personCreated.setIsWhitelisted(personToEdit.isWhitelisted());
        return personCreated;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditCommand // instanceof handles nulls
                && ((this.targetIndex == null && ((EditCommand) other).targetIndex == null) // both targetIndex null
                || (this.editPersonDescriptor.equals(((EditCommand) other).editPersonDescriptor)
                && this.targetIndex.equals(((EditCommand) other).targetIndex)))); // state check
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
        private PostalCode postalCode;
        private Debt debt;
        private Interest interest;
        private Deadline deadline;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.postalCode = toCopy.postalCode;
            this.debt = toCopy.debt;
            this.interest = toCopy.interest;
            this.deadline = toCopy.deadline;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address, this.postalCode,
            this.debt, this.interest, this.deadline, this.tags);
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

        public void setPostalCode(PostalCode postalCode) {
            this.postalCode = postalCode;
        }

        public Optional<PostalCode> getPostalCode() {
            return Optional.ofNullable(postalCode);
        }

        public void setDebt(Debt debt) {
            this.debt = debt;
        }

        public Optional<Debt> getDebt() {
            return Optional.ofNullable(debt);
        }
        public void setInterest(Interest interest) {
            this.interest = interest;
        }

        public Optional<Interest> getInterest() {
            return Optional.ofNullable(interest);
        }

        public void setDeadline(Deadline deadline) {
            this.deadline = deadline;
        }

        public Optional<Deadline> getDeadline() {
            return Optional.ofNullable(deadline);
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
                    && getPostalCode().equals(e.getPostalCode())
                    && getDebt().equals(e.getDebt())
                    && getInterest().equals(e.getInterest())
                    && getDeadline().equals(e.getDeadline())
                    && getTags().equals(e.getTags());
        }
    }
}
