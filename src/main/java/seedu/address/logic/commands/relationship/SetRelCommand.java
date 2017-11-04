package seedu.address.logic.commands.relationship;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_RELATIONSHIP;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.UndoableCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Company;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Note;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.Photo;
import seedu.address.model.person.Position;
import seedu.address.model.person.Priority;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Status;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.tag.Tag;
/**
 *
 */
public class SetRelCommand extends UndoableCommand {
    public static final String COMMAND_WORD = "set";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sets the relationship between two persons."
        + "by the index number used in the last person listing. "
        + "Existing values will be overwritten by the input values.\n"
        + "Parameters: INDEX (must be a positive integer) "
        + "[" + PREFIX_ADD_RELATIONSHIP + "RELATIONSHIP]"
        + "Example: " + COMMAND_WORD + " 1 2 "
        + PREFIX_ADD_RELATIONSHIP + "colleagues";
    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Relationship Added for Person: %1$s\n"
        + "& Person: %2$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index indexOne;
    private final Index indexTwo;
    private final EditPerson editPerson;

    /**
     * @param indexOne   first index of the person in the filtered person list to add relationship
     * @param indexTwo   second index of the person in the filtered person list to add relationship
     * @param editPerson details to edit the person with
     */
    public SetRelCommand(Index indexOne, Index indexTwo, EditPerson editPerson) {
        requireNonNull(indexOne);
        requireNonNull(indexTwo);

        this.indexOne = indexOne;
        this.indexTwo = indexTwo;
        this.editPerson = editPerson;
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit, EditPerson editPerson,
                                             ReadOnlyPerson personTwo) {
        assert personToEdit != null;

        Name updatedName = editPerson.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editPerson.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPerson.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPerson.getAddress().orElse(personToEdit.getAddress());
        Company updatedCompany = editPerson.getCompany().orElse(personToEdit.getCompany());
        Position updatedPosition = editPerson.getPosition().orElse(personToEdit.getPosition());
        Status updatedStatus = editPerson.getStatus().orElse(personToEdit.getStatus());
        Priority updatedPriority = editPerson.getPriority().orElse(personToEdit.getPriority());
        Note updatedNote = editPerson.getNote().orElse(personToEdit.getNote());
        Photo updatedPhoto = editPerson.getPhoto().orElse(personToEdit
            .getPhoto());
        Set<Tag> updatedTags = editPerson.getTags().orElse(personToEdit.getTags());
        Set<Relationship> relation = personToEdit.getRelation();
        final Set<Relationship> updatedRel = new HashSet<>();
        if (!editPerson.shouldClear()) {
            updatedRel.addAll(relation);
        }
        editPerson.getToAddRel(personTwo).ifPresent(updatedRel::addAll);
        editPerson.getToDeleteRel(personTwo).ifPresent(updatedRel::removeAll);
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, updatedCompany,
        updatedPosition, updatedStatus, updatedPriority, updatedNote, updatedPhoto, updatedTags, updatedRel);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if ((indexOne.getZeroBased() >= lastShownList.size()) && (indexTwo.getZeroBased() >= lastShownList.size())) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEditOne = lastShownList.get(indexOne.getZeroBased());
        ReadOnlyPerson personToEditTwo = lastShownList.get(indexTwo.getZeroBased());
        Person editedPersonOne = createEditedPerson(personToEditOne, editPerson, personToEditTwo);
        Person editedPersonTwo = createEditedPerson(personToEditTwo, editPerson, personToEditOne);

        try {
            model.updatePerson(personToEditOne, editedPersonOne);
            model.updatePerson(personToEditTwo, editedPersonTwo);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPersonOne, editedPersonTwo));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SetRelCommand)) {
            return false;
        }

        // state check
        SetRelCommand e = (SetRelCommand) other;
        return indexOne.equals(e.indexOne) && editPerson.equals(e.editPerson);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPerson {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Company company;
        private Position position;
        private Status status;
        private Priority priority;
        private Note note;
        private Photo photo;
        private Set<Tag> tags;
        private boolean clearRels = false;
        private Set<Relationship> addRel;
        private Set<Relationship> deleteRel;

        public EditPerson() {
        }

        public EditPerson(EditPerson toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.company = toCopy.company;
            this.position = toCopy.position;
            this.status = toCopy.status;
            this.priority = toCopy.priority;
            this.note = toCopy.note;
            this.photo = toCopy.photo;
            this.tags = toCopy.tags;
            this.clearRels = toCopy.clearRels;
            this.addRel = toCopy.addRel;
            this.deleteRel = toCopy.deleteRel;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.phone, this.email, this.address, this.company,
            this.position, this.status, this.priority, this.note, this.tags, this.addRel, this.deleteRel) || clearRels;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Phone> getPhone() {
            return Optional.ofNullable(phone);
        }

        public void setPhone(Phone phone) {
            this.phone = phone;
        }

        public Optional<Email> getEmail() {
            return Optional.ofNullable(email);
        }

        public void setEmail(Email email) {
            this.email = email;
        }

        public Optional<Address> getAddress() {
            return Optional.ofNullable(address);
        }

        public void setAddress(Address address) {
            this.address = address;
        }

        public Optional<Company> getCompany() {
            return Optional.ofNullable(company);
        }

        public void setCompany(Company company) {
            this.company = company;
        }

        public Optional<Position> getPosition() {
            return Optional.ofNullable(position);
        }

        public void setPosition(Position position) {
            this.position = position;
        }

        public Optional<Status> getStatus() {
            return Optional.ofNullable(status);
        }

        public void setStatus(Status status) {
            this.status = status;
        }

        public Optional<Priority> getPriority() {
            return Optional.ofNullable(priority);
        }

        public void setPriority(Priority priority) {
            this.priority = priority;
        }

        public Optional<Note> getNote() {
            return Optional.ofNullable(note);
        }

        public void setNote(Note note) {
            this.note = note;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        public void setPhoto(Photo photo) {
            this.photo = photo;
        }

        public Optional<Photo> getPhoto() {
            return Optional.ofNullable(photo);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public void setToAdd(Set<Relationship> addRel) {
            this.addRel = addRel;
        }

        public void setToRemove(Set<Relationship> deleteRel) {
            this.deleteRel = deleteRel;
        }
        public Optional<Set<Relationship>> getToAddRel() {
            return Optional.ofNullable(addRel);
        }
        public Optional<Set<Relationship>> getToAddRel(ReadOnlyPerson person) {
            Set<Relationship> relWithName = new HashSet<>();
            if (addRel == null) {
                return Optional.ofNullable(null);
            }
            relWithName.add(new Relationship(person.getName().toString(), addRel));

            return Optional.of(relWithName);
        }
        public Optional<Set<Relationship>> getToDeleteRel() {
            return Optional.ofNullable(deleteRel);
        }
        public Optional<Set<Relationship>> getToDeleteRel(ReadOnlyPerson person) {
            Set<Relationship> relWithName = new HashSet<>();
            relWithName.add(new Relationship(person.getName().toString(), deleteRel));
            return Optional.of(relWithName);
        }
        public boolean shouldClear() {
            return clearRels;
        }
        public void setClearRels(boolean clearRels) {
            this.clearRels = clearRels;
        }
        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditCommand.EditPersonDescriptor)) {
                return false;
            }

            // state check
            EditPerson e = (EditPerson) other;

            return getName().equals(e.getName())
                && getPhone().equals(e.getPhone())
                && getEmail().equals(e.getEmail())
                && getAddress().equals(e.getAddress())
                && getCompany().equals(e.getCompany())
                && getPosition().equals(e.getPosition())
                && getStatus().equals(e.getStatus())
                && getPriority().equals(e.getPriority())
                && getNote().equals(e.getNote())
                && getPhoto().equals(e.getPhoto())
                && getTags().equals(e.getTags())
                && getToAddRel().equals(e.getToAddRel())
                && getToDeleteRel().equals(e.getToDeleteRel())
                && clearRels == e.clearRels;
        }
    }
}
