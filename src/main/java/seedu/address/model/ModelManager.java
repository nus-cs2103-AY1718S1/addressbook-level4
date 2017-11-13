package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Timer;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.RepeatEventTimerTask;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.exceptions.EventTimeClashException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.InvalidSortTypeException;
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
    //@@author reginleiff
    private final FilteredList<ReadOnlyEvent> filteredEvents;
    private FilteredList<ReadOnlyEvent> scheduledEvents;
    private FilteredList<ReadOnlyEvent> timetableEvents;

    //@@author

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
        timetableEvents = new FilteredList<>(this.addressBook.getTimetable(this.addressBook.getCurrentDate()));
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

    //=========== Person Operations =============================================================

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

    //@@author sebtsh
    /**
     * Removes specified tag from all the persons in the address book.
     *
     * @param tag Tag to be removed
     */
    public void removeTag(Tag tag) {
        addressBook.removeTagFromAll(tag);
        indicateAddressBookChanged();
    }
    //@@author

    //@@author huiyiiih
    @Override
    public void sortPersonList(String type) throws InvalidSortTypeException {
        addressBook.sortPersonList(type);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
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

    //@@author reginleiff
    //=========== Schedule Accessors  =========================================================================

    @Override
    public ObservableList<ReadOnlyEvent> getTimetable() {
        return FXCollections.unmodifiableObservableList(timetableEvents);
    }

    //=========== Filtered Event List Accessors  ==============================================================

    @Override
    public void updateFilteredEventList(Predicate<ReadOnlyEvent> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        ObservableList<ReadOnlyEvent> list = FXCollections.unmodifiableObservableList(filteredEvents);
        return list;
    }

    //=========== Event Operations  ===========================================================================

    @Override
    public synchronized void addEvent(ReadOnlyEvent event) throws EventTimeClashException {
        addressBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        scheduleRepeatedEvent(event);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        addressBook.removeEvent(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws EventNotFoundException, EventTimeClashException {
        addressBook.updateEvent(target, editedEvent);
        scheduleRepeatedEvent(editedEvent);
        indicateAddressBookChanged();
    }
    //@@author

    //=========== Miscellaneous Operations  ====================================================================

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

    /**
     * Schedule repeated event if period is not 0.
     */
    public void scheduleRepeatedEvent(ReadOnlyEvent addedEvent) {
        int repeatPeriod = Integer.parseInt(addedEvent.getPeriod().toString());
        if (repeatPeriod != 0) {
            Timer timer = new Timer();
            timer.schedule(new RepeatEventTimerTask(this, addedEvent, repeatPeriod), addedEvent.getEndDateTime());
        }
    }

}
