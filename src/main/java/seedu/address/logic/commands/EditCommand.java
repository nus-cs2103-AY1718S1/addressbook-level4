package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.MessageAlignmentFormatter.FORMAT_ALIGNMENT_TO_EDIT;
import static seedu.address.commons.core.MessageAlignmentFormatter.FORMAT_ALIGNMENT_TO_PARAMETERS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MATRIC_NO;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NEW_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_OLD_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TIMETABLE;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.PersonSelectedEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.Email;
import seedu.address.model.person.Gender;
import seedu.address.model.person.MatricNo;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.Remark;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.timetable.Timetable;
import seedu.address.model.photo.PhotoPath;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Edits the details of an existing person in the address book.
 */
public class EditCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "edit";
    public static final String COMMAND_ALIAS = "e";
    public static final String COMMAND_SECONDARY_ONE = "modify";
    public static final String COMMAND_SECONDARY_TWO = "change";

    public static final String MESSAGE_USAGE = "| " + COMMAND_WORD + " |"
            + ": Edits the details of the person identified by the index number used in the last person listing.\n"
            + FORMAT_ALIGNMENT_TO_EDIT +  "Existing values will be overwritten by the input values.\n"
            + "Parameters: INDEX (must be a positive integer) "
            + "[" + PREFIX_NAME + "NAME] "
            + "[" + PREFIX_GENDER + "GENDER] "
            + "[" + PREFIX_MATRIC_NO + "MATRIC NO.] "
            + "[" + PREFIX_PHONE + "PHONE] "
            + "[" + PREFIX_EMAIL + "EMAIL] "
            + "[" + PREFIX_ADDRESS + "ADDRESS]\n"
            + FORMAT_ALIGNMENT_TO_PARAMETERS + "[" + PREFIX_TIMETABLE + "TIMETABLE_URL] "
            + "[" + PREFIX_TAG + "TAG]... "
            + "[" + PREFIX_BIRTHDAY + "BIRTHDAY]\n"
            + "Example: " + COMMAND_WORD + " 1 "
            + PREFIX_PHONE + "91234567 "
            + PREFIX_EMAIL + "johndoe@example.com\n"
            + "OR\n"
            + "| " + COMMAND_WORD + " |"
            + ": Edits the specified tag in all contacts containing this tag with a new specified tag.\n"
            + "Parameters: " + PREFIX_OLD_TAG + "TAG " + PREFIX_NEW_TAG + "TAG\n"
            + "Example: " + COMMAND_WORD + " " + PREFIX_OLD_TAG + "CS1020 " + PREFIX_NEW_TAG + "CS2010";

    public static final String MESSAGE_EDIT_PERSON_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_EDIT_TAG_SUCCESS = "Edited Tag: %1$s";
    public static final String MESSAGE_NONEXISTENT_TAG = "The specified old tag does not exist";

    private final boolean isEditForPerson;
    private final Index index;
    private final EditPersonDescriptor editPersonDescriptor;
    private final Tag oldTag;
    private final Tag newTag;

    //@@author nbriannl
    /**
     * @param index of the person in the filtered person list to edit
     * @param editPersonDescriptor details to edit the person with
     */
    public EditCommand(Index index, EditPersonDescriptor editPersonDescriptor) {
        requireNonNull(index);
        requireNonNull(editPersonDescriptor);

        this.isEditForPerson = true;
        this.index = index;
        this.editPersonDescriptor = new EditPersonDescriptor(editPersonDescriptor);
        this.oldTag = null;
        this.newTag = null;
    }

    //@@author nbriannl
    /**
     *
     * @param oldTag the old tag to be replaced by the new tag
     * @param newTag that will replace the old tag
     */
    public EditCommand(Tag oldTag, Tag newTag) {
        requireNonNull(oldTag);
        requireNonNull(newTag);

        this.isEditForPerson = false;
        this.index = null;
        this.editPersonDescriptor = null;
        this.oldTag = oldTag;
        this.newTag = newTag;
    }

    //@@author nbriannl
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        if (isEditForPerson) {
            return executeCommandForPerson();
        } else {
            return executeCommandForTag();
        }
    }

    //@@author nbriannl
    /**
     * Command execution of {@code EditCommand} for a {@code Tag}
     */
    private CommandResult executeCommandForTag () throws CommandException {
        try {
            model.editTag(oldTag, newTag);
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("Updating the tags on one person cannot possibly make the person"
                    + " identical to another person.");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        } catch (TagNotFoundException tgne) {
            throw new CommandException(MESSAGE_NONEXISTENT_TAG);
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        return new CommandResult(generateResultMsgForTag(oldTag, newTag));
    }

    //@@author
    /**
     * Command execution of {@code EditCommand} for a {@code Tag}
     */
    private CommandResult executeCommandForPerson () throws CommandException {
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
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        EventsCenter.getInstance().post(new PersonSelectedEvent(editedPerson, index.getZeroBased()));
        return new CommandResult(String.format(MESSAGE_EDIT_PERSON_SUCCESS, editedPerson));
    }

    //@@author April0616
    /**
     * Creates a new person object with the details of the person to be edited.
     * Be edited with {@param editPersonDescriptor}.
     * @param personToEdit the person to be edited
     * @return the created person object
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             EditPersonDescriptor editPersonDescriptor) {
        assert personToEdit != null;

        Name updatedName = editPersonDescriptor.getName().orElse(personToEdit.getName());
        Gender updatedGender = editPersonDescriptor.getGender().orElse(personToEdit.getGender());
        MatricNo updatedMatricNo = editPersonDescriptor.getMatricNo().orElse(personToEdit.getMatricNo());
        Phone updatedPhone = editPersonDescriptor.getPhone().orElse(personToEdit.getPhone());
        Email updatedEmail = editPersonDescriptor.getEmail().orElse(personToEdit.getEmail());
        Address updatedAddress = editPersonDescriptor.getAddress().orElse(personToEdit.getAddress());
        Birthday updateBirthday = editPersonDescriptor.getBirthday().orElse(personToEdit.getBirthday());
        Timetable updatedTimetable = editPersonDescriptor.getTimetable().orElse(personToEdit.getTimetable());
        Remark updatedRemark = personToEdit.getRemark();
        PhotoPath updatedPhotoPath = personToEdit.getPhotoPath();
        Set<Tag> updatedTags = editPersonDescriptor.getTags().orElse(personToEdit.getTags());

        return new Person(updatedName, updatedGender, updatedMatricNo,
                updatedPhone, updatedEmail, updatedAddress,
                updatedTimetable, updatedRemark, updatedPhotoPath, updatedTags, updateBirthday);
    }

    //@@author nbriannl
    /**
     * Generates the command result String for Edit Command when editing tags
     */
    public static String generateResultMsgForTag(Tag oldTag, Tag newTag) {
        return "Edited Tag:\n" + "From '" + oldTag.tagName + "' to '" + newTag.tagName + "'";
    }

    //@@author nbriannl
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

        if (isEditForPerson) {
            return  isEditForPerson == e.isEditForPerson
                    && index.equals(e.index)
                    && editPersonDescriptor.equals(e.editPersonDescriptor);
        } else {
            return isEditForPerson == e.isEditForPerson
                    && oldTag.equals(e.oldTag)
                    && newTag.equals(e.newTag);
        }
    }

    //@@author
    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class EditPersonDescriptor {
        private Name name;
        private Gender gender;
        private MatricNo matricNo;
        private Phone phone;
        private Email email;
        private Address address;
        private Timetable timetable;
        private Set<Tag> tags;
        private Birthday birthday;

        public EditPersonDescriptor() {}

        public EditPersonDescriptor(EditPersonDescriptor toCopy) {
            this.name = toCopy.name;
            this.gender = toCopy.gender;
            this.matricNo = toCopy.matricNo;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.timetable = toCopy.timetable;
            this.tags = toCopy.tags;
            this.birthday = toCopy.birthday;
        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.name, this.gender, this.matricNo, this.phone, this.email,
                    this.address, this.timetable, this.tags, this.birthday);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        //@@author April0616
        /**
         * Sets the details of the gender to edit the person with.
         * @param gender
         */
        public void setGender(Gender gender) {
            this.gender = gender;
        }

        /**
         * Gets the details of the gender to edit the person with.
         * @return gender if the gender is specified
         */
        public Optional<Gender> getGender() {
            return Optional.ofNullable(gender);
        }

        /**
         * Sets the details of the matriculation number to edit the person with.
         * @param matricNo
         */
        public void setMatricNo(MatricNo matricNo) {
            this.matricNo = matricNo;
        }

        /**
         * Gets the details of the matriculation number to edit the person with.
         * @return matriculation number if it is specified
         */
        public Optional<MatricNo> getMatricNo() {
            return Optional.ofNullable(matricNo);
        }
        //@@author

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

        public void setTimetable(Timetable timetable) {
            this.timetable = timetable;
        }

        public Optional<Timetable> getTimetable() {
            return Optional.ofNullable(timetable);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Optional<Set<Tag>> getTags() {
            return Optional.ofNullable(tags);
        }

        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
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
                    && getGender().equals(e.getGender())
                    && getMatricNo().equals(e.getMatricNo())
                    && getPhone().equals(e.getPhone())
                    && getEmail().equals(e.getEmail())
                    && getAddress().equals(e.getAddress())
                    && getTimetable().equals(e.getTimetable())
                    && getTags().equals(e.getTags())
                    && getBirthday().equals(e.getBirthday());
        }
    }
}
