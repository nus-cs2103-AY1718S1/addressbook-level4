package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
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
        sortedFilteredPersons.setComparator(new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
                boolean v1 = p1.getFavouriteStatus().getStatus();
                boolean v2 = p2.getFavouriteStatus().getStatus();
                if (v1 && !v2) {
                    return -1;
                } else if (!v1 && v2) {
                    return 1;
                }
                return 0;
            }
        }.thenComparing(new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
                return p1.getName().toString().compareTo(p2.getName().toString());
            }
        }.thenComparing(new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
                return p1.getPhone().toString().compareTo(p2.getName().toString());
            }
        }.thenComparing(new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
                return p1.getEmail().toString().compareTo(p2.getEmail().toString());
            }
        }.thenComparing(new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson p1, ReadOnlyPerson p2) {
                return p1.getAddress().toString().compareTo(p2.getAddress().toString());
            }
        })))));
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
            if ((index.size() > 1) && (tag.size() > 1)) {
                throw new DuplicatePersonException();
            } else {
                List<Index> indexList = new ArrayList<>();
                indexList.addAll(index);
                if (index.size() == 1) {
                    int targetIndex = indexList.get(index.size() - 1).getZeroBased();
                    Person toDelete = new Person(getFilteredPersonList().get(targetIndex));
                    Person toUpdate = new Person(toDelete);
                    Set<Tag> oldTags = toDelete.getTags();

                    Set<Tag> newTags = deleteTag(tag, toDelete);
                    if (!(newTags.size() == oldTags.size())) {
                        toUpdate.setTags(newTags);
                        tagExist = true;
                        addressBook.updatePerson(toDelete, toUpdate);
                    }
                } else {
                    Iterator<Index> it = indexList.iterator();
                    while (it.hasNext()) {
                        Index current = it.next();
                        int targetIndex = current.getZeroBased();
                        Person toDelete = new Person(getFilteredPersonList().get(targetIndex));
                        Person toUpdate = new Person(toDelete);
                        Set<Tag> oldTags = toDelete.getTags();

                        Set<Tag> newTags = deleteTag(tag, toDelete);
                        if (!(newTags.size() == oldTags.size())) {
                            toUpdate.setTags(newTags);
                            tagExist = true;
                            addressBook.updatePerson(toDelete, toUpdate);
                        }
                    }
                }
            }
        } else {
            for (int i = 0; i < totalSize; i++) {
                Person toDelete = new Person(getFilteredPersonList().get(i));
                Person toUpdate = new Person(toDelete);
                Set<Tag> oldTags = toDelete.getTags();

                Set<Tag> newTags = deleteTag(tag, toDelete);
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

    /**
     *
     * @param tag
     * @param toDelete
     * @return Set of Tags of new Person to be updated
     */
    private Set<Tag> deleteTag(Set<Tag> tag, Person toDelete) {
        Set<Tag> oldTags = toDelete.getTags();
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
}
