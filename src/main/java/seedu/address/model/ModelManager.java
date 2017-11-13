package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.FindLocationRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.event.ReadOnlyEvent;
import seedu.address.model.person.exceptions.DuplicateEventException;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EventNotFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.ViewCountComparator;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<ReadOnlyPerson> filteredEmailTo;
    private final FilteredList<ReadOnlyEvent> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredEmailTo = new FilteredList<>(this.addressBook.getPersonList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
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
        if (target.getViewCount() == editedPerson.getViewCount()) {
            indicateAddressBookChanged();
        }
    }

    //@@author sidhmads
    @Override
    public void removeTag(ObservableList<ReadOnlyPerson> persons, Set<Tag> tag)
            throws PersonNotFoundException, IllegalValueException {
        int counter = 0;
        if (persons.isEmpty()) {
            persons.setAll(addressBook.getPersonList());
        }
        for (ReadOnlyPerson person : persons) {
            if (!Collections.disjoint(person.getTags(), tag)) {
                Person newPerson = new Person(person);
                Set<Tag> newTags = new HashSet<>(person.getTags());
                for (Tag t: tag) {
                    newTags.remove(t);
                }
                newPerson.setTags(newTags);
                updatePerson(person, newPerson);
                counter++;
            }
        }
        if (counter == 0) {
            throw new IllegalValueException("The Tag is invalid!");
        }
    }
    //@@author

    //@@author royceljh
    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        addressBook.removeEvent(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireAllNonNull(target, editedEvent);

        addressBook.updateEvent(target, editedEvent);
        indicateAddressBookChanged();

    }
    //@@author

    //@@author sidhmads
    @Override
    public void findLocation(List<ReadOnlyPerson> person) throws PersonNotFoundException {
        if (person.size() == 0) {
            throw new PersonNotFoundException();
        }
        raise(new FindLocationRequestEvent(person));
    }
    //@@author

    //@@author soonwj
    @Override
    public void sortByViewCount() {
        AddressBook addressBookToSort = new AddressBook(addressBook);
        ObservableList<ReadOnlyPerson> listToSort = addressBookToSort.getPersonList();
        ArrayList<ReadOnlyPerson> listToSortedCopy = new ArrayList<>();
        for (ReadOnlyPerson r : listToSort) {
            listToSortedCopy.add(r);
        }
        Collections.sort(listToSortedCopy, new ViewCountComparator());

        try {
            addressBookToSort.setPersons(listToSortedCopy);
        } catch (DuplicatePersonException dpe) {
            assert false : "Impossible to be duplicate";
        }

        resetData(addressBookToSort);
    }
    //@@author

    //=========== Filtered Person List Accessors =============================================================

    //@@author sidhmads
    /**
     * Sorts the persons in the address book by name
     */
    public void sortPersons() {
        addressBook.sortPersons();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }
    //@@author

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

    //@@author sidhmads
    @Override
    public String updateEmailRecipient(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredEmailTo.setPredicate(predicate);
        List<String> validPeeps = new ArrayList<>();
        for (ReadOnlyPerson person : filteredEmailTo) {
            if (person.getEmail() != null && !person.getEmail().value.equalsIgnoreCase("INVALID_EMAIL@INVALID.COM")
                    && !validPeeps.contains(person.getEmail().value)) {
                validPeeps.add(person.getEmail().value);
            }
        }
        return String.join(",", validPeeps);
    }
    //@@author

    //@@author royceljh
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyEvent} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    @Override
    public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }
    //@@author
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
                && filteredPersons.equals(other.filteredPersons)
                && filteredEvents.equals(other.filteredEvents);
    }

}
