package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
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

/**
 * Removes a tag from existing person(s) in the address book.
 */
public class TagRemoveCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "t-remove";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Remove tag to the person(s) identified "
            + "by the index number used in the last person listing.\n"
            + "Parameters: INDEX1 INDEX2... (must be a positive integer) "
            + "[TAG] (TAG should not start with a number).\n"
            + "If no index is provided, remove the tag from all people. "
            + "Example: " + COMMAND_WORD + " 1 2 3 "
            + "[friends]";

    public static final String MESSAGE_REMOVE_TAG_SUCCESS = "Edited Person: %1$s";
    public static final String MESSAGE_NOT_EDITED = "At least one field to edit must be provided.";
    public static final String MESSAGE_DUPLICATE_PERSON = "This person already exists in the address book.";
    public static final String MESSAGE_TAG_NOT_FOUND = "The %1$s tag is not found.";

    private ArrayList<Index> index;
    private final TagRemoveDescriptor tagRemoveDescriptor;

    /**
     * @param index of the person in the filtered person list to edit
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
        FilteredList<ReadOnlyPerson> tagFilteredPersonList = new FilteredList<>(lastShownList);
        String tagInString = tagRemoveDescriptor.getTags().toArray()[0].toString();
        String tagInString1 = tagInString.substring(1, tagInString.lastIndexOf("]"));

        TagMatchingKeywordPredicate tagPredicate = new TagMatchingKeywordPredicate(tagInString1);
        tagFilteredPersonList.setPredicate(tagPredicate);

        StringBuilder editedPersonDisplay = new StringBuilder();
        int tagNotFoundPerson = 0;

        Object tagToRemove = tagRemoveDescriptor.getTags().toArray()[0];
        if (tagFilteredPersonList == null) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagInString));
        }
        if (index.size() == 0) {
            index = makeFullIndexList(lastShownList);
        }
        checkIndexInRange(lastShownList);
        for (int i = 0; i < index.size(); i++) {
            ReadOnlyPerson personToEdit = lastShownList.get(index.get(i).getZeroBased());
            if (tagFilteredPersonList.contains(personToEdit)) {
                Set<Tag> modifiableTagList = createModifiableTagSet(personToEdit.getTags());
                modifiableTagList.remove(tagToRemove);
                tagRemoveDescriptor.setTags(modifiableTagList);
                Person editedPerson = createEditedPerson(personToEdit, tagRemoveDescriptor);
                try {
                    model.updatePerson(personToEdit, editedPerson);
                } catch (DuplicatePersonException dpe) {
                    throw new CommandException(MESSAGE_DUPLICATE_PERSON);
                } catch (PersonNotFoundException pnfe) {
                    throw new AssertionError("The target person cannot be missing");
                }
                model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
                editedPersonDisplay.append(String.format(MESSAGE_REMOVE_TAG_SUCCESS, editedPerson));
                if (i != index.size() - 1) {
                    editedPersonDisplay.append("\n");
                }
            } else {
                tagNotFoundPerson++;
            }
        }
        if (tagNotFoundPerson == index.size()) {
            throw new CommandException(String.format(MESSAGE_TAG_NOT_FOUND, tagInString));
        }
        return new CommandResult(editedPersonDisplay.toString());
    }

    /**
     * @param unmodifiable tag List
     */
    public Set<Tag> createModifiableTagSet(Set<Tag> unmodifiable) {
        Set<Tag> modifiable = new HashSet<>();
        for (Tag t : unmodifiable) {
            modifiable.add(t);
        }
        return modifiable;
    }

    /**
     * @param tagList current tag List
     */
    public boolean containsTag(List<Tag> tagList) {
        Set<Tag> tagsToRemove = tagRemoveDescriptor.getTags();
        for (Tag tagToRemove: tagsToRemove) {
            for (Tag current : tagList) {
                if (tagToRemove.tagName.equalsIgnoreCase(current.tagName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * @param personList current tag List
     */
    public ArrayList<Index> makeFullIndexList(List<ReadOnlyPerson> personList) {
        ArrayList<Index> indexList = new ArrayList<>();
        for (int i = 1; i <= personList.size(); i++) {
            indexList.add(Index.fromOneBased(i));
        }
        return indexList;
    }

    /**
     * @param lastShownList current tag List
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
     * edited with {@code editPersonDescriptor}.
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

        public TagRemoveDescriptor() {}

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
