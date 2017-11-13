package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //@@author sofarsophie
    @Override
    public void removeTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
        ObservableList<ReadOnlyPerson> personList = addressBook.getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            ReadOnlyPerson currReadOnlyPerson = personList.get(i);

            Person newPerson = new Person(currReadOnlyPerson);
            Set<Tag> tagList = newPerson.getTags();
            tagList.remove(tag);
            newPerson.setTags(tagList);

            addressBook.updatePerson(currReadOnlyPerson, newPerson);
        }
    }
    //@@author

    //@@author kengying
    @Override
    public List<Tag> getTagList() {
        List<Tag> listTagsWithDuplicates = new ArrayList<>();

        filteredPersons.forEach(persons -> listTagsWithDuplicates.addAll(persons.getTags()));

        List<Tag> listTags = listTagsWithDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());

        listTags.sort(Comparator.comparing(Tag::toString));


        return listTags;
    }

    @Override
    public List<Group> getGroupList() {
        ObservableList<ReadOnlyPerson> personList = addressBook.getPersonList();
        List<Group> listGroupWithDuplicates = new ArrayList<>();
        for (int i = 0; i < personList.size(); i++) {
            ReadOnlyPerson currReadOnlyPerson = personList.get(i);

            Person newPerson = new Person(currReadOnlyPerson);
            listGroupWithDuplicates.add(newPerson.getGroup());
        }

        List<Group> listGroups = listGroupWithDuplicates.stream()
                .distinct()
                .collect(Collectors.toList());

        listGroups.sort(Comparator.comparing(Group::toString));


        return listGroups;
    }
    //@@author

    //@@author sofarsophie
    @Override
    public ObservableList<ReadOnlyPerson> sortFilteredPersonList(ObservableList<ReadOnlyPerson> personsList,
                                                                 Prefix prefix) {
        addressBook.sortPersonsList(prefix);
        indicateAddressBookChanged();
        return personsList;
    }
    //@@author

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}
