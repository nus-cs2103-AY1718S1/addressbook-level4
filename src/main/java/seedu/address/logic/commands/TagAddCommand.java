package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.event.Event;
import seedu.address.model.person.Address;
import seedu.address.model.person.Birthday;
import seedu.address.model.person.DateAdded;
import seedu.address.model.person.Email;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagMatchingKeywordPredicate;

//@@author ZhangH795

/**
 * Adds a tag to existing person(s) in the address book.
 * If the tag already exists for at least one of the person(s) selected, error would be thrown.
 */
public class TagAddCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "t-add";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Add tag to the person(s) identified "
            + "by the index number used in the last person listing. "
            + "Input tag will append to the existing tags.\n"
            + "Parameters: INDEX1 INDEX2... (must be a positive integer) "
            + "TAG (TAG Should not start with a number).\n"
            + "Example: " + COMMAND_WORD + " 1 2 3 "
            + "friends";

    public static final String MESSAGE_ADD_TAG_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_TAG_ALREADY_EXISTS = "The %1$s tag already exists "
            + "in person(s) selected.";
    private final ArrayList<Index> index;
    private final TagAddDescriptor tagAddDescriptor;
    private final int zeroBasedFirstIndex = 0;
    private final int stringSecondCharIndex = 1;
    private final int emptyListSize = 0;

    /**
     * @param index            of the person in the filtered person list to edit
     * @param tagAddDescriptor details to edit the person with
     */
    public TagAddCommand(ArrayList<Index> index, TagAddDescriptor tagAddDescriptor) {
        requireNonNull(index);
        requireNonNull(tagAddDescriptor);

        this.index = index;
        this.tagAddDescriptor = new TagAddDescriptor(tagAddDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ObservableList<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        boolean looseFind = false;

        Tag tagToAdd = (Tag) tagAddDescriptor.getTags().toArray()[zeroBasedFirstIndex];
        String tagInStringRaw = tagToAdd.toString();
        String tagInString = tagInStringRaw.substring(stringSecondCharIndex, tagInStringRaw.lastIndexOf("]"));

        StringBuilder editedPersonDisplay = new StringBuilder();
        checkIndexInRange(lastShownList);

        TagMatchingKeywordPredicate tagPredicate = new TagMatchingKeywordPredicate(tagInString, looseFind);
        ObservableList<ReadOnlyPerson> selectedPersonList = createSelectedPersonList(lastShownList);
        FilteredList<ReadOnlyPerson> tagFilteredPersonList = new FilteredList<>(selectedPersonList);
        tagFilteredPersonList.setPredicate(tagPredicate);
        if (tagFilteredPersonList.size() > emptyListSize) {
            throw new CommandException(String.format(MESSAGE_TAG_ALREADY_EXISTS, tagInStringRaw));
        }

        for (int i = 0; i < index.size(); i++) {
            ReadOnlyPerson personToEdit = lastShownList.get(index.get(i).getZeroBased());
            Set<Tag> originalTagList = personToEdit.getTags();
            Set<Tag> modifiableTagList = createModifiableTagSet(originalTagList, tagToAdd);
            TagAddDescriptor tempTagAddDescriptor = new TagAddDescriptor();
            tempTagAddDescriptor.setTags(modifiableTagList);

            Person editedPerson = createEditedPerson(personToEdit, tempTagAddDescriptor);
            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            editedPersonDisplay.append(createTagListDisplay(editedPerson));

            if (tagInString.toLowerCase().contains("fav")) {
                Index firstIndex = new Index(zeroBasedFirstIndex);
                EventsCenter.getInstance().post(new JumpToListRequestEvent(firstIndex));
            } else {
                EventsCenter.getInstance().post(new JumpToListRequestEvent(index.get(zeroBasedFirstIndex)));
            }

            if (i != index.size() - 1) {
                editedPersonDisplay.append("\n");
            }
        }
        return new CommandResult(editedPersonDisplay.toString());
    }

    /**
     * Throws CommandException if any of the user input index is invalid.
     * @param lastShownList current filtered person list
     */
    public void checkIndexInRange(ObservableList<ReadOnlyPerson> lastShownList) throws CommandException {
        for (int i = 0; i < index.size(); i++) {
            if (index.get(i).getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }
    }

    /**
     * Creates string for edited tag list.
     * @param editedPerson edited person to show tag list
     * Returns formatted string to indicate edited tag list.
     */
    public String createTagListDisplay(Person editedPerson) {
        int tagListStringStartIndex = 1;
        int tagListStringEndIndex;
        String tagChangedDisplayRaw = editedPerson.getTags().toString();
        tagListStringEndIndex = tagChangedDisplayRaw.length() - 1;
        String tagChangedDisplay = editedPerson.getName() + " Tag List: "
                + tagChangedDisplayRaw.substring(tagListStringStartIndex, tagListStringEndIndex);
        return String.format(MESSAGE_ADD_TAG_SUCCESS, tagChangedDisplay);
    }

    /**
     * Adds new tag to the copy of existing tag list.
     * @param unmodifiable tag List
     * @param tagToAdd     tag to be added
     * Returns modifiable tag set.
     */
    public Set<Tag> createModifiableTagSet(Set<Tag> unmodifiable, Tag tagToAdd) {
        Set<Tag> modifiable = new HashSet<>();
        for (Tag t : unmodifiable) {
            modifiable.add(t);
        }
        modifiable.add(tagToAdd);
        return modifiable;
    }

    /**
     * Creates selected person list.
     * @param fullPersonList person list
     * Returns selected person list.
     */
    public ObservableList<ReadOnlyPerson> createSelectedPersonList(ObservableList<ReadOnlyPerson> fullPersonList) {
        ArrayList<ReadOnlyPerson> selectedPersonList = new ArrayList<>();
        for (Index i : index) {
            ReadOnlyPerson personToEdit = fullPersonList.get(i.getZeroBased());
            selectedPersonList.add(personToEdit);
        }
        return FXCollections.observableArrayList(selectedPersonList);
    }

    /**
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code tagAddDescriptor}.
     */
    private static Person createEditedPerson(ReadOnlyPerson personToEdit,
                                             TagAddDescriptor tagAddDescriptor) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Birthday updatedBirthday = personToEdit.getBirthday();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = tagAddDescriptor.getTags();
        Set<Event> updatedEvents = personToEdit.getEvents();
        DateAdded updateDateAdded = personToEdit.getDateAdded();

        return new Person(updatedName, updatedBirthday, updatedPhone, updatedEmail, updatedAddress, updatedTags,
                updatedEvents, updateDateAdded);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof TagAddCommand)) {
            return false;
        }

        // state check
        TagAddCommand e = (TagAddCommand) other;
        return index.equals(e.index)
                && tagAddDescriptor.equals(e.tagAddDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class TagAddDescriptor {
        private Name name;
        private Birthday birthday;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Set<Event> events;
        private DateAdded dateAdded;

        public TagAddDescriptor() {
        }

        public TagAddDescriptor(TagAddDescriptor toCopy) {
            this.name = toCopy.name;
            this.birthday = toCopy.birthday;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.tags = toCopy.tags;
            this.events = toCopy.events;
            this.dateAdded = toCopy.dateAdded;
        }

        public TagAddDescriptor(ReadOnlyPerson toCopy) {
            this.name = toCopy.getName();
            this.birthday = toCopy.getBirthday();
            this.phone = toCopy.getPhone();
            this.email = toCopy.getEmail();
            this.address = toCopy.getAddress();
            this.tags = toCopy.getTags();
            this.events = toCopy.getEvents();

        }

        /**
         * Returns true if at least one field is edited.
         */
        public boolean isAnyFieldEdited() {
            return CollectionUtil.isAnyNonNull(this.tags);
        }

        public void setName(Name name) {
            this.name = name;
        }

        public Optional<Name> getName() {
            return Optional.ofNullable(name);
        }

        public void setBirthday(Birthday birthday) {
            this.birthday = birthday;
        }

        public Optional<Birthday> getBirthday() {
            return Optional.ofNullable(birthday);
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

        public void setEvent(Set<Event> events) {
            this.events = events;
        }

        public Optional<Set<Event>> getEvents() {
            return Optional.ofNullable(events);
        }

        public void setTags(Set<Tag> tags) {
            this.tags = tags;
        }

        public Set<Tag> getTags() {
            return tags;
        }

        @Override
        public boolean equals(Object other) {
            // short circuit if same object
            if (other == this) {
                return true;
            }

            // instanceof handles nulls
            if (!(other instanceof TagAddDescriptor)) {
                return false;
            }

            // state check
            TagAddDescriptor e = (TagAddDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }
}
