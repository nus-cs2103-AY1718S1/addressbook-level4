package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.commands.AddMeetingCommand.MESSAGE_DUPLICATE_MEETING;
import static seedu.address.logic.commands.AddMeetingCommand.MESSAGE_MEETING_CLASH;
import static seedu.address.logic.commands.AddMeetingCommand.MESSAGE_OVERDUE_MEETING;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_MEETINGS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.meeting.DateTime;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingContainsFullWordPredicate;
import seedu.address.model.meeting.MeetingTag;
import seedu.address.model.meeting.NameMeeting;
import seedu.address.model.meeting.Place;
import seedu.address.model.meeting.ReadOnlyMeeting;
import seedu.address.model.meeting.exceptions.DuplicateMeetingException;
import seedu.address.model.meeting.exceptions.MeetingBeforeCurrDateException;
import seedu.address.model.meeting.exceptions.MeetingClashException;
import seedu.address.model.meeting.exceptions.MeetingNotFoundException;
import seedu.address.model.person.Address;
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
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";

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
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";

    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;

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
    public CommandResult executeUndoableCommand() throws CommandException {
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
        }

        //@@author kyngyi
        String personToEditName = personToEdit.getName().toString();
        String[] nameArray = {personToEditName};
        model.updateFilteredMeetingList(new MeetingContainsFullWordPredicate(Arrays.asList(nameArray)));
        List<ReadOnlyMeeting> lastShownMeetingList = model.getFilteredMeetingList();

        if (!editedPerson.getName().toString().equalsIgnoreCase(personToEditName)) {
            while (!lastShownMeetingList.isEmpty()) {
                int initialListSize = lastShownMeetingList.size();
                EditMeetingCommand.EditMeetingDescriptor editedMeetingDescriptor =
                        new EditMeetingCommand.EditMeetingDescriptor();

                try {
                    Index firstIndex = ParserUtil.parseIndex("1");
                    ReadOnlyMeeting meetingToEdit = lastShownMeetingList.get(firstIndex.getZeroBased());

                    Meeting editedMeeting = createEditedMeeting(meetingToEdit, editedMeetingDescriptor,
                            personToEdit, editedPerson);

                    model.updateMeeting(meetingToEdit, editedMeeting);
                } catch (DuplicateMeetingException dpe) {
                    throw new CommandException(MESSAGE_DUPLICATE_MEETING);
                } catch (MeetingNotFoundException pnfe) {
                    throw new AssertionError("The target meeting cannot be missing");
                } catch (MeetingBeforeCurrDateException mde) {
                    throw new CommandException(MESSAGE_OVERDUE_MEETING);
                } catch (MeetingClashException mce) {
                    throw new CommandException(MESSAGE_MEETING_CLASH);
                } catch (IllegalValueException ive) {
                    assert false : "Error in deleting first item";
                }
                int endListSize = lastShownMeetingList.size();
                if (initialListSize == endListSize) {
                    break;
                }
            }
        } else {
            for (ReadOnlyMeeting meeting : lastShownMeetingList) {
                EditMeetingCommand.EditMeetingDescriptor editedMeetingDescriptor =
                        new EditMeetingCommand.EditMeetingDescriptor();

                Meeting editedMeeting = createEditedMeeting(meeting, editedMeetingDescriptor,
                        personToEdit, editedPerson);

                try {
                    model.updateMeeting(meeting, editedMeeting);
                } catch (DuplicateMeetingException dpe) {
                    throw new CommandException(MESSAGE_DUPLICATE_MEETING);
                } catch (MeetingNotFoundException pnfe) {
                    throw new AssertionError("The target meeting cannot be missing");
                } catch (MeetingBeforeCurrDateException mde) {
                    throw new CommandException(MESSAGE_OVERDUE_MEETING);
                } catch (MeetingClashException mce) {
                    throw new CommandException(MESSAGE_MEETING_CLASH);
                }
            }
        }

        model.updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        //@@author

        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
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

    /**
     * Creates and returns a {@code Meeting} with the details of {@code meetingToEdit}
     * edited with {@code editMeetingDescriptor}.
     */

    private static Meeting createEditedMeeting(ReadOnlyMeeting meetingToEdit,
                                               EditMeetingCommand.EditMeetingDescriptor editMeetingDescriptor,
                                               ReadOnlyPerson target, ReadOnlyPerson editedPerson) {
        assert meetingToEdit != null;

        NameMeeting updatedName = editMeetingDescriptor.getName().orElse(meetingToEdit.getName());
        DateTime updatedDate = editMeetingDescriptor.getDate().orElse(meetingToEdit.getDate());
        Place updatedPlace = editMeetingDescriptor.getPlace().orElse(meetingToEdit.getPlace());
        MeetingTag updatedTag = editMeetingDescriptor.getMeetTag().orElse(meetingToEdit.getMeetTag());
        List<ReadOnlyPerson> personList = meetingToEdit.getPersonsMeet();
        personList.remove(target);
        personList.add(editedPerson);
        return new Meeting(updatedName, updatedDate, updatedPlace, personList, updatedTag);
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
        private Email email;
        private Address address;
        private Set<Tag> tags;

        public EditPersonDescriptor() {}

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
