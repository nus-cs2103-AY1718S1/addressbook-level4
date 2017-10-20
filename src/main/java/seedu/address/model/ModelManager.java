package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Comparator;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
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
    private final SortedList<ReadOnlyPerson> sortedPersonsList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedPersonsList = new SortedList<ReadOnlyPerson>(filteredPersons);
        sortFilteredPersons(0);
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

    @Override
    public void deleteTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson orginalPerson = addressBook.getPersonList().get(i);

            Person person = new Person(orginalPerson);
            Set<Tag> tags = person.getTags();

            tags.remove(tag);
            person.setTags(tags);

            addressBook.updatePerson(orginalPerson, person);

        }
    }


    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(sortedPersonsList);
    }

    /**
     * @param: int
     * 0 = sort by name ascending
     * 1 = sort by tags ascending
     * Returns a sorted unmodifable view of the list {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    public void sortFilteredPersons(int sortOrder) {

        //sort by name by default
        Comparator<ReadOnlyPerson> sort = new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                return o1.getName().fullName.toUpperCase().compareTo(o2.getName().fullName.toUpperCase());
            }
        };

        if (sortOrder == 1) {
            //sort by tags
            sort = new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                    SortedSet<Tag> o1SortedTags = new TreeSet<Tag>(o1.getTags());
                    SortedSet<Tag> o2SortedTags = new TreeSet<Tag>(o2.getTags());

                    if (o1SortedTags.size() == 0) {
                        return 1;
                    } else if (o2SortedTags.size() == 0) {
                        return -1;
                    } else {
                        return o1SortedTags.first().tagName.compareTo(o2SortedTags.first().tagName);
                    }
                }
            };
        } else if (sortOrder == 2) {
            sort = new Comparator<ReadOnlyPerson>() {
                @Override
                public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                    return o2.getName().fullName.toUpperCase().compareTo(o1.getName().fullName.toUpperCase());
                }
            };
        }

        sortedPersonsList.setComparator(sort);
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
                && sortedPersonsList.equals(other.sortedPersonsList);
    }

}
