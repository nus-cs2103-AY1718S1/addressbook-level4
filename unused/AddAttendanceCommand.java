package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Attendance;
import seedu.address.model.person.Email;
import seedu.address.model.person.JoinDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ProfilePicture;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonMaxAttendanceException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

//@@author pohjie
/**
 * Reason why this was unused: Tried implementing this feature too late into the iterative cycle,
 * couldn't get it to work. Decided to take it out to ensure quality of code for working parts
 * and leave enough time for writing sufficient and rigorous test cases.
  */
/**
 * Increment attendance count of an existing person in the address book.
 */
public class AddAttendanceCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "addAttendance";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Increment the attendance person identified"
            + "by the index number used in the last person listing. "
            + "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) ";

    public static final String MESSAGE_ADD_ATTENDANCE_SUCCESS = "Edited Person's Attendance: %1$s";
    public static final String MESSAGE_ATTENDANCE_NOT_ADDED =
            "Either invalid index or the attended sessions is already 8";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditAttendancePersonDescriptor editAttendancePersonDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public AddAttendanceCommand(Index index, EditAttendancePersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.index = index;
        this.editAttendancePersonDescriptor = new EditAttendancePersonDescriptor(editPersonDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToEdit = lastShownList.get(index.getZeroBased());
        Person editedPerson = createEditedPerson(personToEdit, editAttendancePersonDescriptor);

        try {
            model.updatePerson(personToEdit, editedPerson);
        } catch (PersonMaxAttendanceException pmae) {
            throw new CommandException(MESSAGE_ATTENDANCE_NOT_ADDED);
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_ADD_ATTENDANCE_SUCCESS, editedPerson));
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code editPersonDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditAttendancePersonDescriptor editAttendancePersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editAttendancePersonDescriptor.getName().orElse(personToEdit.getName());
        Phone updatedPhone = editAttendancePersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editAttendancePersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editAttendancePersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Set<Tag> updatedTags = editAttendancePersonDescriptor.getTags().orElse(personToEdit.getTags());
        //@@author ReneeSeet
        JoinDate date = editAttendancePersonDescriptor.getJoinDate();
        //@@author pohjie
        Attendance attendance = editAttendancePersonDescriptor.getAttendance();
        ProfilePicture updatedProfilePicture = editAttendancePersonDescriptor.getProfilePicture();
        return new Person(updatedName, updatedPhone, updatedEmail, updatedAddress, date, updatedTags);
        //@@author
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof AddAttendanceCommand)) {
            return false;
        }

        // state check
        AddAttendanceCommand e = (AddAttendanceCommand) other;
        return index.equals(e.index)
                && editAttendancePersonDescriptor.equals(e.editAttendancePersonDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditAttendancePersonDescriptor {
        private Name name;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        //@@author pohjie
        private Attendance attendance;
        private ProfilePicture profilePicture;

        //@@author ReneeSeet
        private JoinDate date;
        //@@author

        public EditAttendancePersonDescriptor() {}

        public EditAttendancePersonDescriptor(EditAttendancePersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.tags = toCopy.tags;
            //@@author pohjie
            this.attendance = toCopy.attendance;
            try {
                this.attendance.addAttendance();
            } catch (personMaxAttendanceException e) {
                System.out.println("Max attendance already!");
            }
            this.profilePicture = toCopy.profilePicture;
            //@@author ReneeSeet
            this.date = toCopy.date;
            //@@author
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

        //@@author ReneeSeet
        public void setJoinDate(JoinDate date) {
            this.date = date;
        }

        public JoinDate getJoinDate() {
            return date;
        }

        //@@author pohjie
        public void setAttendance(Attendance attendance) {
            this.attendance = attendance;
        }

        public Attendance getAttendance() {
            return attendance;
        }

        public void setProfilePicture(ProfilePicture profilePicture) {
            this.profilePicture = profilePicture;
        }

        public ProfilePicture getProfilePicture() {
            return profilePicture;
        }
        //@@author

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof EditAttendancePersonDescriptor)) {
                return false;
            }

            // state check
            EditAttendancePersonDescriptor e = (EditAttendancePersonDescriptor) other;

            return getName().equals(e.getName())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTags().equals(e.getTags())
                    && getJoinDate().equals(e.getJoinDate())
                    && getAttendance().equals(e.getAttendance())
                    && getProfilePicture().equals(e.getProfilePicture());
        }
    }
}
