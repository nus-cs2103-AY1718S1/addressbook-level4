package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
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
    private final SortedList<ReadOnlyPerson> sortedPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedPersons = new SortedList<>(filteredPersons);
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

    //@@author john19950730
    @Override
    public List<String> getAllNamesInAddressBook() {
        ObservableList<ReadOnlyPerson> listOfPersons = addressBook.getPersonList();
        return listOfPersons.stream()
                .map(person -> person.getName().toString())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllPhonesInAddressBook() {
        ObservableList<ReadOnlyPerson> listOfPersons = addressBook.getPersonList();
        return listOfPersons.stream()
                .map(person -> person.getPhone().toString())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllEmailsInAddressBook() {
        ObservableList<ReadOnlyPerson> listOfPersons = addressBook.getPersonList();
        return listOfPersons.stream()
                .map(person -> person.getEmail().toString())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllAddressesInAddressBook() {
        ObservableList<ReadOnlyPerson> listOfPersons = addressBook.getPersonList();
        return listOfPersons.stream()
                .map(person -> person.getAddress().toString())
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllTagsInAddressBook() {
        ObservableList<Tag> listOfTags = addressBook.getTagList();
        // cut out the square brackets since that is redundant in CLI
        return listOfTags.stream()
                .map(tag -> tag.toString().substring(1, tag.toString().length() - 1))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> getAllRemarksInAddressBook() {
        ObservableList<ReadOnlyPerson> listOfPersons = addressBook.getPersonList();
        return listOfPersons.stream()
                .map(person -> person.getRemark().toString())
                .filter(remark -> !remark.equals(""))
                .collect(Collectors.toList());
    }

    //@@author
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

    @Override
    public void removeTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
        ObservableList<ReadOnlyPerson> list = addressBook.getPersonList();

        for (int i = 0; i < list.size(); i++) {
            ReadOnlyPerson person = list.get(i);
            Person newPerson = new Person(person);
            Set<Tag> tagList = newPerson.getTags();
            tagList = new HashSet<Tag>(tagList);
            tagList.remove(tag);

            newPerson.setTags(tagList);
            addressBook.updatePerson(person, newPerson);
        }
        indicateAddressBookChanged();
    }

    @Override
    public void removeTag(Index index, Tag tag) throws PersonNotFoundException, DuplicatePersonException {
        List<ReadOnlyPerson> list = getFilteredPersonList();
        ReadOnlyPerson person = list.get(index.getZeroBased());
        Person newPerson = new Person(person);
        Set<Tag> tagList = newPerson.getTags();
        tagList = new HashSet<>(tagList);
        tagList.remove(tag);

        newPerson.setTags(tagList);
        addressBook.updatePerson(person, newPerson);
        indicateAddressBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(sortedPersons);
    }

    @Override
    public void sortFilteredPersonList(Comparator<ReadOnlyPerson> comparator) {
        sortedPersons.setComparator(comparator);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        sortedPersons.setComparator(null);
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
