package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.EventStorageChangedEvent;
import seedu.address.logic.commands.exceptions.DeleteOnCascadeException;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.PersonHaveParticipateException;
import seedu.address.model.event.exceptions.PersonNotParticipateException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.HaveParticipateEventException;
import seedu.address.model.person.exceptions.NotParticipateEventException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final EventList eventList;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<ReadOnlyEvent> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyEventList eventList, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.eventList = new EventList(eventList);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredEvents = new FilteredList<>(this.eventList.getEventList());
    }

    public ModelManager() {
        this(new AddressBook(), new EventList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newAddressBook, ReadOnlyEventList newEventList) {
        addressBook.resetData(newAddressBook);
        eventList.resetData(newEventList);
        indicateAddressBookChanged();
        indicateEventListChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public ReadOnlyEventList getEventList() {
        return eventList;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target)
            throws PersonNotFoundException, DeleteOnCascadeException {
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
    public synchronized void addPerson(int position, ReadOnlyPerson person) {
        addressBook.addPerson(position, person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    // @@author HouDenghao
    @Override
    public synchronized void sortPersons() {
        addressBook.sortPersons();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    // @@author
    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public Set<Tag> extractNewTag(ReadOnlyPerson person) {
        return addressBook.extractNewTags(person);
    }

    @Override
    public void removeTags(Set<Tag> tagList) {
        addressBook.separateMasterTagListWith(tagList);
        indicateAddressBookChanged();
    }

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

    //=========== Event List Modifiers =============================================================

    /** Raises an event to indicate the model has changed */
    private void indicateEventListChanged() {
        raise(new EventStorageChangedEvent(eventList));
    }

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target)
            throws EventNotFoundException, DeleteOnCascadeException {
        eventList.removeEvent(target);
        indicateEventListChanged();
    }

    @Override
    public synchronized void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        eventList.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventListChanged();
    }

    @Override
    public synchronized void addEvent(int position, ReadOnlyEvent event) {
        eventList.addEvent(position, event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventListChanged();
    }

    // @@author HouDenghao
    @Override
    public synchronized void sortEvents() {
        eventList.sortEvents();
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
    }

    // @@author
    @Override
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireAllNonNull(target, editedEvent);

        eventList.updateEvent(target, editedEvent);
        indicateAddressBookChanged();
        indicateEventListChanged();
    }

    //=========== Participant Operations =============================================================

    @Override
    public void quitEvent(Person person, Event event)
            throws PersonNotParticipateException, NotParticipateEventException {
        eventList.removeParticipant(person, event);
        indicateEventListChanged();

        addressBook.removeParticipation(person, event);
        indicateAddressBookChanged();
    }

    @Override
    public void joinEvent(Person person, Event event)
            throws PersonHaveParticipateException, HaveParticipateEventException {
        eventList.addParticipant(person, event);
        indicateEventListChanged();

        addressBook.addParticipation(person, event);
        indicateAddressBookChanged();
    }
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
