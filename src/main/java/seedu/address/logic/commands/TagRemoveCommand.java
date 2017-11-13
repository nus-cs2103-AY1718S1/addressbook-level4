package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
 * Removes a tag from existing person(s) in the address book.
 * If the tag does not exist for at least one of the person(s) selected, error would be thrown.
 */
public class TagRemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "t-remove";
    public static final String FAVOURITE_KEYWORD = "fav";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove tag to the person(s) identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX1 INDEX2... (must be a positive integer) "
            + "TAG (TAG should not start with a number).\n"
            + "If no index is provided, remove the tag from all people. "
            + "Example: " + COMMAND_WORD + " 1 2 3 "
            + "friends";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_TAG_NOT_FOUND = "The %1$s tag is not found.";
    public static final String MESSAGE_TAG_NOT_FOUND_FOR_SOME = "The %1$s tag entered is not found "
            + "for some person selected.";
    private ArrayList<Index> index;
    private final TagRemoveDescriptor tagRemoveDescriptor;
    private final int firstIndex = 0;
    private final int arrayIndexOffset = 1;
    private final int emptyListSize = 0;
    private final int stringSecondCharIndex = 1;

    /**
     * @param index               of the person in the filtered person list to edit
     * @param tagRemoveDescriptor details to edit the person with
     */
    public TagRemoveCommand(ArrayList<Index> index, TagRemoveDescriptor tagRemoveDescriptor) {
        requireNonNull(index);
        requireNonNull(tagRemoveDescriptor);

        this.index = index;
        this.tagRemoveDescriptor = new TagRemoveDescriptor(tagRemoveDescriptor);
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        ObservableList<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        Tag tagToRemove = (Tag) tagRemoveDescriptor.getTags().toArray()[firstIndex];
        String tagInStringRaw = tagToRemove.toString();
        String tagInString = tagInStringRaw.substring(stringSecondCharIndex, tagInStringRaw.lastIndexOf("]"));

        boolean looseFind = tagInString.toLowerCase().contains(FAVOURITE_KEYWORD);
        boolean removeAll = false;
        TagMatchingKeywordPredicate tagPredicate = new TagMatchingKeywordPredicate(tagInString, looseFind);

        StringBuilder editedPersonDisplay = new StringBuilder();
        checkIndexInRange(lastShownList);

        ArrayList<Index> indexList = index;
        if (index.size() == emptyListSize) {
            removeAll = true;
            indexList = makeFullIndexList(lastShownList.size());
        }

        ObservableList<ReadOnlyPerson> selectedPersonList = createSelectedPersonList(indexList, lastShownList);
        FilteredList<ReadOnlyPerson> tagFilteredPersonList = new FilteredList<>(selectedPersonList);
        tagFilteredPersonList.setPredicate(tagPredicate);
        if (tagFilteredPersonList.size() == emptyListSize) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagInStringRaw));
        } else if (!removeAll && tagFilteredPersonList.size() < indexList.size()) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND_FOR_SOME, tagInStringRaw));
        }

        for (int i = 0; i < tagFilteredPersonList.size(); i++) {
            ReadOnlyPerson personToEdit = tagFilteredPersonList.get(i);
            Set<Tag> modifiableTagList = createModifiableTagSet(personToEdit.getTags(), tagToRemove);
            TagRemoveDescriptor tempTagRemoveDescriptor = new TagRemoveDescriptor();
            tempTagRemoveDescriptor.setTags(modifiableTagList);
            Person editedPerson = createEditedPerson(personToEdit, tempTagRemoveDescriptor);
            try {
                model.updatePerson(personToEdit, editedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
            model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            editedPersonDisplay.append(createTagListDisplay(editedPerson));

            Index defaultIndex = new Index(firstIndex);
            EventsCenter.getInstance().post(new JumpToListRequestEvent(defaultIndex));

            if (i != indexList.size() - arrayIndexOffset) {
                editedPersonDisplay.append("\n");
            }
        }
        return new CommandResult(editedPersonDisplay.toString());
    }

    /**
     * Removes a tag from the copy of existing tag list.
     * @param unmodifiable tag List
     * @param tagToRemove     tag to be removed
     * Returns modifiable tag set.
     */
    public Set<Tag> createModifiableTagSet(Set<Tag> unmodifiable, Tag tagToRemove) {
        Set<Tag> modifiable = new HashSet<>();
        String tagName = tagToRemove.tagName;
        boolean removeFavourite = tagName.toLowerCase().contains(FAVOURITE_KEYWORD);
        for (Tag t : unmodifiable) {
            if (!tagToRemove.equals(t) && !(removeFavourite && t.tagName.toLowerCase().contains(FAVOURITE_KEYWORD))) {
                modifiable.add(t);
            }
        }
        return modifiable;
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
        tagListStringEndIndex = tagChangedDisplayRaw.length() - arrayIndexOffset;
        String tagChangedDisplay = editedPerson.getName() + " Tag List: "
                + tagChangedDisplayRaw.substring(tagListStringStartIndex, tagListStringEndIndex);
        return String.format(MESSAGE_REMOVE_TAG_SUCCESS, tagChangedDisplay);
    }

    /**
     * Creates selected person list.
     * @param indexList selected index list
     * @param fullPersonList person list
     * Returns selected person list.
     */
    public ObservableList<ReadOnlyPerson> createSelectedPersonList(ArrayList<Index> indexList,
                                                                   ObservableList<ReadOnlyPerson> fullPersonList) {
        ArrayList<ReadOnlyPerson> selectedPersonList = new ArrayList<>();
        for (Index i : indexList) {
            ReadOnlyPerson personToEdit = fullPersonList.get(i.getZeroBased());
            selectedPersonList.add(personToEdit);
        }
        return FXCollections.observableArrayList(selectedPersonList);
    }

    /**
     * Checks whether the tag list contains tag to remove.
     * @param tagList current tag List
     * Returns true if tag list contains the tag to be removed; false otherwise.
     */
    public boolean containsTag(List<Tag> tagList) {
        Set<Tag> tagsToRemove = tagRemoveDescriptor.getTags();
        for (Tag tagToRemove : tagsToRemove) {
            for (Tag current : tagList) {
                if (tagToRemove.tagName.equalsIgnoreCase(current.tagName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates a person index list from 1 to input list size.
     * @param personListSize current person list size
     * Returns created person list.
     */
    public ArrayList<Index> makeFullIndexList(int personListSize) {
        ArrayList<Index> indexList = new ArrayList<>();
        int firstIndexOneBased = 1;
        for (int i = firstIndexOneBased; i <= personListSize; i++) {
            indexList.add(Index.fromOneBased(i));
        }
        return indexList;
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
     * Creates and returns a {@code Person} with the details of {@code personToEdit}
     * edited with {@code tagRemoveDescriptor}.
     */
    public Person createEditedPerson(ReadOnlyPerson personToEdit,
                                     TagRemoveDescriptor tagRemoveDescriptor) {
        assert personToEdit != null;

        Name updatedName = personToEdit.getName();
        Birthday updatedBirthday = personToEdit.getBirthday();
        Phone updatedPhone = personToEdit.getPhone();
        Email updatedEmail = personToEdit.getEmail();
        Address updatedAddress = personToEdit.getAddress();
        Set<Tag> updatedTags = tagRemoveDescriptor.getTags();
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
        if (!(other instanceof TagRemoveCommand)) {
            return false;
        }

        // state check
        TagRemoveCommand e = (TagRemoveCommand) other;
        return index.equals(e.index)
                && tagRemoveDescriptor.equals(e.tagRemoveDescriptor);
    }

    /**
     * Stores the details to edit the person with. Each non-empty field value will replace the
     * corresponding field value of the person.
     */
    public static class TagRemoveDescriptor {
        private Name name;
        private Birthday birthday;
        private Phone phone;
        private Email email;
        private Address address;
        private Set<Tag> tags;
        private Set<Event> events;
        private DateAdded dateAdded;

        public TagRemoveDescriptor() {
        }

        public TagRemoveDescriptor(TagRemoveDescriptor toCopy) {
            this.name = toCopy.name;
            this.birthday = toCopy.birthday;
            this.phone = toCopy.phone;
            this.email = toCopy.email;
            this.address = toCopy.address;
            this.tags = toCopy.tags;
            this.events = toCopy.events;
            this.dateAdded = toCopy.dateAdded;
        }

        public TagRemoveDescriptor(ReadOnlyPerson toCopy) {
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
            if (!(other instanceof TagRemoveDescriptor)) {
                return false;
            }

            // state check
            TagRemoveDescriptor e = (TagRemoveDescriptor) other;

            return getTags().equals(e.getTags());
        }
    }
}
