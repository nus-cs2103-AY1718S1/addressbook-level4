package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.SortCommandParser.DATA_FIELD_ADDRESS;
import static seedu.address.logic.parser.SortCommandParser.DATA_FIELD_EMAIL;
import static seedu.address.logic.parser.SortCommandParser.DATA_FIELD_NAME;
import static seedu.address.logic.parser.SortCommandParser.DATA_FIELD_PHONE;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import java.util.function.Predicate;
import java.util.logging.Logger;

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
import seedu.address.model.util.ComparatorUtil;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final SortedList<ReadOnlyPerson> sortedFilteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedFilteredPersons = new SortedList<>(filteredPersons);
        // Sort contacts by favourite status, then name, then phone, then email, then address
        sortedFilteredPersons.setComparator(ComparatorUtil.getAllComparatorsFavThenNameFirst());
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

    /**
     * Raises an event to indicate the model has changed
     */
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

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(sortedFilteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void sortByDataFieldFirst(String dataField) {
        switch (dataField) {
        case DATA_FIELD_NAME:
            sortedFilteredPersons.setComparator(ComparatorUtil.getAllComparatorsFavThenNameFirst());
            break;
        case DATA_FIELD_PHONE:
            sortedFilteredPersons.setComparator(ComparatorUtil.getAllComparatorsFavThenPhoneFirst());
            break;
        case DATA_FIELD_EMAIL:
            sortedFilteredPersons.setComparator(ComparatorUtil.getAllComparatorsFavThenEmailFirst());
            break;
        case DATA_FIELD_ADDRESS:
            sortedFilteredPersons.setComparator(ComparatorUtil.getAllComparatorsFavThenAddressFirst());
            break;
        default:
            break;
        }
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

    @Override
    public void removeTag(Set<Tag> tag, Set<Index> index) throws DuplicatePersonException,
            PersonNotFoundException {
        int totalSize = getFilteredPersonList().size();
        Boolean tagExist = false;

        if (!index.isEmpty()) {
            Iterator<Index> indexIt = index.iterator();
            while (indexIt.hasNext()) {
                int indexToRemove = indexIt.next().getZeroBased();
                Person toDelete = new Person(getFilteredPersonList().get(indexToRemove));
                Person toUpdate = new Person(toDelete);
                Set<Tag> oldTags = toDelete.getTags();
                Set<Tag> newTags = deleteTag(tag, oldTags);
                if (!(newTags.size() == oldTags.size())) {
                    toUpdate.setTags(newTags);
                    tagExist = true;
                    addressBook.updatePerson(toDelete, toUpdate);
                }
            }
        } else {
            for (int i = 0; i < totalSize; i++) {
                Person toDelete = new Person(getFilteredPersonList().get(i));
                Person toUpdate = new Person(toDelete);
                Set<Tag> oldTags = toDelete.getTags();
                Set<Tag> newTags = deleteTag(tag, oldTags);
                if (!(newTags.size() == oldTags.size())) {
                    toUpdate.setTags(newTags);
                    tagExist = true;
                    addressBook.updatePerson(toDelete, toUpdate);
                }
            }
        }

        if (!tagExist) {
            throw new PersonNotFoundException();
        }
    }

    @Override
    public void addTag(Set<Tag> tag, Set<Index> index) throws PersonNotFoundException,
            DuplicatePersonException {

        Iterator<Index> indexIt = index.iterator();
        boolean added = false;

        while (indexIt.hasNext()) {
            int indexToAdd = indexIt.next().getZeroBased();
            Person toCheck = new Person(getFilteredPersonList().get(indexToAdd));
            Person toUpdate = new Person(toCheck);
            Set<Tag> current = toCheck.getTags();
            Set<Tag> updated = newTag(tag, current);
            if (!(current.size() == updated.size())) {
                toUpdate.setTags(updated);
                added = true;
                addressBook.updatePerson(toCheck, toUpdate);
            }
        }

        if (!added) {
            throw new PersonNotFoundException();
        }
    }


    /**
     *
     * @param tag set of tags input by user
     * @param oldTags set of current tags
     * @return Set of Tags of new Person to be updated
     */
    private Set<Tag> deleteTag(Set<Tag> tag, Set<Tag> oldTags) {
        Set<Tag> newTags = new HashSet<>();

        Iterator<Tag> it = oldTags.iterator();
        while (it.hasNext()) {
            Tag checkTag = it.next();
            String current = checkTag.tagName;
            Boolean toAdd = true;
            Iterator<Tag> it2 = tag.iterator();
            while (it2.hasNext()) {
                String toCheck = it2.next().tagName;
                if (current.equals(toCheck)) {
                    toAdd = false;
                }
            }
            if (toAdd) {
                newTags.add(checkTag);
            }
        }
        return newTags;
    }

    /**
     *
     * @param tag set of tags input by user
     * @param current set of current tags
     * @return Set of tags to be updated
     */
    private Set<Tag> newTag(Set<Tag> tag, Set<Tag> current) {
        Set<Tag> updated = new HashSet<>();
        Iterator<Tag> it = current.iterator();
        boolean exist = false;
        for (Tag t : tag) {
            Tag toAdd = t;
            while (it.hasNext()) {
                Tag toCheck = it.next();
                if (current.equals(t)) {
                    exist = true;
                }
                updated.add(toCheck);
            }
            if (!exist) {
                updated.add(toAdd);
                exist = false;
            }
        }
        return updated;
    }



}
