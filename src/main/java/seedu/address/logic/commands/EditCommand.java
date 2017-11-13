package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEADLINE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DEBT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HANDPHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_HOME_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INTEREST;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OFFICE_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_POSTAL_CODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TOTAL_DEBT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.util.DateUtil.formatDate;

import java.util.Date;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.DateRepaid;
import seedu.address.model.person.Deadline;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Email;
import seedu.address.model.person.Handphone;
import seedu.address.model.person.HomePhone;
import seedu.address.model.person.Interest;
import seedu.address.model.person.Name;
import seedu.address.model.person.OfficePhone;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.PostalCode;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.DateUtil;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_WORD_ALIAS = "e";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits the details of the person identified "
            + "by the index number used in the last person listing or of the currently selected person if no "
            + "index is specified. "
            + "Existing values will be overwritten by the input values. At least one field must be present.\n"
            + "Parameters: [INDEX]\n"
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_HANDPHONE + "HANDPHONE] "
            + "[" + PREFIX_HOME_PHONE + "HOME PHONE] "
            + "[" + PREFIX_OFFICE_PHONE + "OFFICE PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS] "
            + "[" + PREFIX_POSTAL_CODE + "POSTAL CODE] "
            + "[" + PREFIX_DEBT + "DEBT] "
            + "[" + PREFIX_TOTAL_DEBT + "TOTAL DEBT] "
            + "[" + PREFIX_INTEREST + "INTEREST] "
            + "[" + PREFIX_DEADLINE + "DEADLINE] "
            + "[" + PREFIX_TAG + "TAG]...\n"
            + "Example 1: " + COMMAND_WORD + " 1 "
            + PREFIX_HANDPHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + "Example 2: " + COMMAND_WORD
            + PREFIX_POSTAL_CODE + " " + "123456";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_INVALID_TOTAL_DEBT = "Total debt cannot be less than current debt";

    private final ReadOnlyPerson personToEdit;
    private final EditPersonDescriptor editPersonDescriptor;

    /**
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(EditPersonDescriptor editPersonDescriptor) throws CommandException {
        requireNonNull(editPersonDescriptor);

        personToEdit = selectPersonForCommand();
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    /**
     * @param targetIndex of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index targetIndex, EditPersonDescriptor editPersonDescriptor) throws CommandException {
        requireNonNull(targetIndex);
        requireNonNull(editPersonDescriptor);

        personToEdit = selectPersonForCommand(targetIndex);
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        Person editedPerson = createEditedPerson(personToEdit, editPersonDescriptor);

        try {
            editedPerson = listSyncChecks(personToEdit, editedPerson);
            model.updatePerson(personToEdit, editedPerson);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);
        reselectPerson(editedPerson);

        String currentList = ListObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson.getName()));
    }

    /**
     * Does the appropriate list status checks on {@code personToEdit}
     * and changes the list status of {@code editedPerson} accordingly
     * @return editedPerson
     */
    private Person listSyncChecks(ReadOnlyPerson personToEdit, Person editedPerson) {
        if (personToEdit.isWhitelisted() && editedPerson.getDebt().toNumber() > 0) {
            editedPerson.setIsWhitelisted(false);
            editedPerson.setDateRepaid(new DateRepaid());
        } else if (!personToEdit.isWhitelisted() && !personToEdit.isBlacklisted()
                && editedPerson.getDebt().toNumber() == 0) {
            editedPerson.setIsWhitelisted(true);
            editedPerson.setDateRepaid(new DateRepaid(formatDate(new Date())));
        }
        if (editedPerson.getDebt().toNumber() == 0) {
            try {
                editedPerson.setDeadline(new Deadline(Deadline.NO_DEADLINE_SET));
                editedPerson.setHasOverdueDebt(false);
            } catch (IllegalValueException ive) {
                assert false : "Cannot happen as input for new deadline was controlled.";
            }
        }
        if (!editedPerson.getDeadline().value.equals(Deadline.NO_DEADLINE_SET)) {
            Date editedPersonDeadline = DateUtil.convertStringToDate(editedPerson.getDeadline().valueToDisplay);
            Date currentDate = new Date();
            if (personToEdit.hasOverdueDebt() && currentDate.before(editedPersonDeadline)) {
                editedPerson.setHasOverdueDebt(false);
            }
            if (!personToEdit.hasOverdueDebt() && editedPersonDeadline.before(currentDate)) {
                editedPerson.setHasOverdueDebt(true);
            }
        }
        return editedPerson;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) throws CommandException {
        requireNonNull(personToEdit);

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Handphone updatedHandphone = editPersonDescriptor.getHandphone().orElse(personToEdit.getHandphone());
        HomePhone updatedHomePhone = editPersonDescriptor.getHomePhone().orElse(personToEdit.getHomePhone());
        OfficePhone updatedOfficePhone = editPersonDescriptor.getOfficePhone().orElse(personToEdit.getOfficePhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        PostalCode updatedPostalCode = editPersonDescriptor.getPostalCode().orElse(personToEdit.getPostalCode());
        Debt updatedDebt = editPersonDescriptor.getDebt().orElse(personToEdit.getDebt());
        Debt updatedTotalDebt = editPersonDescriptor.getTotalDebt().orElse(personToEdit.getTotalDebt());
        Interest updatedInterest = editPersonDescriptor.getInterest().orElse(personToEdit.getInterest());
        Deadline updatedDeadline = editPersonDescriptor.getDeadline().orElse(personToEdit.getDeadline());
        try {
            updatedDeadline.checkDateBorrow(personToEdit.getDateBorrow().getDate());
        } catch (IllegalValueException ive) {
            throw new CommandException("Deadline cannot be before date borrow.");
        }
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        Person personCreated = new Person(updatedName, updatedHandphone, updatedHomePhone, updatedOfficePhone,
                updatedEmail, updatedAddress, updatedPostalCode, updatedDebt, updatedInterest, updatedDeadline,
                updatedTags);
        try {
            personCreated.setTotalDebt(updatedTotalDebt);
        } catch (IllegalValueException ive) {
            throw new CommandException(ive.getMessage());
        }
        personCreated.setDateBorrow(personToEdit.getDateBorrow());
        personCreated.setDateRepaid(personToEdit.getDateRepaid());
        personCreated.setIsBlacklisted(personToEdit.isBlacklisted());
        personCreated.setIsWhitelisted(personToEdit.isWhitelisted());
        personCreated.setHasOverdueDebt(personToEdit.hasOverdueDebt());
        personCreated.setHasDisplayPicture(personToEdit.hasDisplayPicture());
        return personCreated;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof EditCommand // instanceof handles nulls
                && (this.personToEdit.equals(((EditCommand) other).personToEdit)) // state check
                && (this.editPersonDescriptor.equals(((EditCommand) other).editPersonDescriptor)));
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Handphone handphone;
        private HomePhone homePhone;
        private OfficePhone officePhone;
        private Email email;
        private Address address;
        private PostalCode postalCode;
        private Debt debt;
        private Debt totalDebt;
        private Interest interest;
        private Deadline deadline;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.handphone = toCopy.handphone;
            this.homePhone = toCopy.homePhone;
            this.officePhone = toCopy.officePhone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.postalCode = toCopy.postalCode;
            this.debt = toCopy.debt;
            this.totalDebt = toCopy.totalDebt;
            this.interest = toCopy.interest;
            this.deadline = toCopy.deadline;
            this.tags = toCopy.tags;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.handphone, this.homePhone, this.officePhone,
                    this.email, this.address, this.postalCode, this.debt, this.totalDebt, this.interest, this.deadline,
                    this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setHandphone(Phone phone) {
            this.handphone = (Handphone) phone;
        }

        public Optional<Handphone> getHandphone() {
            return Optional.ofNullable(handphone);
        }

        public void setHomePhone(Phone phone) {
            this.homePhone = (HomePhone) phone;
        }

        public Optional<HomePhone> getHomePhone() {
            return Optional.ofNullable(homePhone);
        }

        public void setOfficePhone(Phone phone) {
            this.officePhone = (OfficePhone) phone;
        }

        public Optional<OfficePhone> getOfficePhone() {
            return Optional.ofNullable(officePhone);
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

        public void setTotalDebt(Debt totalDebt) {
            this.totalDebt = totalDebt;
        }

        public Optional<Debt> getTotalDebt() {
            return Optional.ofNullable(totalDebt);
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
                    && getHandphone().equals(e.getHandphone())
                    && getHomePhone().equals(e.getHomePhone())
                    && getOfficePhone().equals(e.getOfficePhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getPostalCode().equals(e.getPostalCode())
                    && getDebt().equals(e.getDebt())
                    && getTotalDebt().equals(e.getTotalDebt())
                    && getInterest().equals(e.getInterest())
                    && getDeadline().equals(e.getDeadline())
                    && getTags().equals(e.getTags());
        }
    }
}
