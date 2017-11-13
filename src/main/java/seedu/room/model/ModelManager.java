package seedu.room.model;

import static java.util.Objects.requireNonNull;
import static seedu.room.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.room.commons.core.ComponentManager;
import seedu.room.commons.core.LogsCenter;
import seedu.room.commons.events.model.EventBookChangedEvent;
import seedu.room.commons.events.model.ResidentBookChangedEvent;
import seedu.room.commons.exceptions.IllegalValueException;
import seedu.room.logic.commands.exceptions.AlreadySortedException;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.event.exceptions.DuplicateEventException;
import seedu.room.model.event.exceptions.EventNotFoundException;
import seedu.room.model.person.Person;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.person.exceptions.NoneHighlightedException;
import seedu.room.model.person.exceptions.PersonNotFoundException;
import seedu.room.model.person.exceptions.TagNotFoundException;
import seedu.room.model.tag.Tag;


/**
 * Represents the in-memory model of the resident book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ResidentBook residentBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;

    private final EventBook eventBook;
    private final FilteredList<ReadOnlyEvent> filteredEvents;

    /**
     * Initializes a ModelManager with the given residentBook and userPrefs.
     */
    public ModelManager(ReadOnlyResidentBook residentBook, ReadOnlyEventBook eventBook , UserPrefs userPrefs) {
        super();
        requireAllNonNull(residentBook, userPrefs);

        logger.fine("Initializing with resident book: " + residentBook + ", event book: " + eventBook
                + " and user prefs " + userPrefs);

        this.residentBook = new ResidentBook(residentBook);
        this.eventBook = new EventBook(eventBook);
        filteredPersons = new FilteredList<>(this.residentBook.getPersonList());
        filteredEvents = new FilteredList<>(this.eventBook.getEventList());

    }

    public ModelManager() {
        this(new ResidentBook(), new EventBook() , new UserPrefs());
    }

    public ModelManager(ReadOnlyResidentBook residentBook, UserPrefs userPrefs) {
        this(residentBook, new EventBook() , userPrefs);
    }


    @Override
    public void resetData(ReadOnlyResidentBook newData, ReadOnlyEventBook newEventData) {
        residentBook.resetData(newData);
        eventBook.resetData(newEventData);
        indicateResidentBookChanged();
        indicateEventBookChanged();
    }

    @Override
    public ReadOnlyResidentBook getResidentBook() {
        return residentBook;
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateResidentBookChanged() {
        raise(new ResidentBookChangedEvent(residentBook));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        residentBook.removePerson(target);
        indicateResidentBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        residentBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateResidentBookChanged();
    }

    //@@author Haozhe321
    @Override
    public synchronized void deleteByTag(Tag tag) throws IllegalValueException, CommandException {
        residentBook.removeByTag(tag);
        indicateResidentBookChanged();
    }
    //@@author

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);
        residentBook.updatePerson(target, editedPerson);
        indicateResidentBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code residentBook}
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

    //@@author shitian007
    @Override
    public void updateFilteredPersonListPicture(Predicate<ReadOnlyPerson> predicate, Person person) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
        for (ReadOnlyPerson p : filteredPersons) {
            if (p.getName().toString().equals(person.getName().toString())
                    && p.getPhone().toString().equals(person.getPhone().toString())) {
                p.getPicture().setPictureUrl(person.getPicture().getPictureUrl());
            }
        }
        indicateResidentBookChanged();
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
        return residentBook.equals(other.residentBook)
                && filteredPersons.equals(other.filteredPersons);
    }

    @Override
    public void removeTag(Tag t) throws TagNotFoundException {
        residentBook.removeTag(t);
    }

    //@@author shitian007
    /**
     * Updates the highlight status of a resident if tag matches input tag
     */
    public void updateHighlightStatus(String highlightTag) throws TagNotFoundException  {
        residentBook.updateHighlightStatus(highlightTag);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateResidentBookChanged();
    }

    /**
     * Removes the highlight status of all residents
     */
    public void resetHighlightStatus() throws NoneHighlightedException {
        residentBook.resetHighlightStatus();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateResidentBookChanged();
    }
    //@@author

    //=========== Sorting Person List =============================================================

    //@@author sushinoya
    /**
     * Sorts the Resident Book by name, phone, room or phone depending on the sortCriteria
     */
    public void sortBy(String sortCriteria) throws AlreadySortedException {
        residentBook.sortBy(sortCriteria);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateResidentBookChanged();
    }

    //=========== Swapping Residents' Rooms =============================================================

    /**
     * Swaps the rooms between two residents.
     *
     * @throws PersonNotFoundException if the persons specified are not found in the list.
     */
    @Override
    public void swapRooms(ReadOnlyPerson person1, ReadOnlyPerson person2)
            throws PersonNotFoundException {
        residentBook.swapRooms(person1, person2);
        indicateResidentBookChanged();
    }

    //=========== Events Methods =============================================================

    @Override
    public ReadOnlyEventBook getEventBook() {
        return this.eventBook;
    }

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent target) throws EventNotFoundException {
        eventBook.removeEvent(target);
        indicateEventBookChanged();
    }

    @Override
    public synchronized void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        eventBook.addEvent(event);
        updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);
        indicateEventBookChanged();
    }

    @Override
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireAllNonNull(target, editedEvent);

        eventBook.updateEvent(target, editedEvent);
        indicateEventBookChanged();
    }

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
    public void sortEventsBy(String sortCriteria) throws AlreadySortedException {
        eventBook.sortBy(sortCriteria);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateResidentBookChanged();
    }

    /**
     * Raises an event to indicate the model has changed
     */
    private void indicateEventBookChanged() {
        raise(new EventBookChangedEvent(eventBook));
    }

}
