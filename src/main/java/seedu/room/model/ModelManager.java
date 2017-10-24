package seedu.room.model;

import static java.util.Objects.requireNonNull;
import static seedu.room.commons.util.CollectionUtil.requireAllNonNull;

import java.time.LocalDateTime;
import java.util.Iterator;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.room.commons.core.ComponentManager;
import seedu.room.commons.core.LogsCenter;
import seedu.room.commons.events.model.RoomBookChangedEvent;
import seedu.room.logic.commands.exceptions.AlreadySortedException;
import seedu.room.model.person.Person;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.UniquePersonList;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.person.exceptions.PersonNotFoundException;


/**
 * Represents the in-memory model of the room book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final RoomBook roomBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;

    /**
     * Initializes a ModelManager with the given roomBook and userPrefs.
     */
    public ModelManager(ReadOnlyRoomBook roomBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(roomBook, userPrefs);

        logger.fine("Initializing with room book: " + roomBook + " and user prefs " + userPrefs);

        this.roomBook = new RoomBook(roomBook);
        filteredPersons = new FilteredList<>(this.roomBook.getPersonList());
        try {
            deleteTemporary(this.roomBook);
        } catch (PersonNotFoundException e) {
            logger.warning("no such person found");
        }
    }

    public ModelManager() {
        this(new RoomBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyRoomBook newData) {
        roomBook.resetData(newData);
        indicateRoomBookChanged();
    }

    @Override
    public ReadOnlyRoomBook getRoomBook() {
        return roomBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateRoomBookChanged() {
        raise(new RoomBookChangedEvent(roomBook));
    }

    /** delete temporary persons on start up of the app */
    public synchronized void deleteTemporary(RoomBook roomBook) throws PersonNotFoundException {
        UniquePersonList personsList = roomBook.getUniquePersonList();
        Iterator<Person> itr = personsList.iterator(); //iterator to iterate through the persons list
        while (itr.hasNext()) {
            Person person = itr.next();
            LocalDateTime personExpiry = person.getTimestamp().getExpiryTime();
            LocalDateTime current = LocalDateTime.now();
            if (personExpiry != null) { //if this is a temporary contact
                if (current.compareTo(personExpiry) == 1) { //if current time is past the time of expiry
                    itr.remove();
                }
            }
        }
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        roomBook.removePerson(target);
        indicateRoomBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        roomBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateRoomBookChanged();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        roomBook.updatePerson(target, editedPerson);
        indicateRoomBookChanged();
    }

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code roomBook}
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
        return roomBook.equals(other.roomBook)
                && filteredPersons.equals(other.filteredPersons);
    }


    //=========== Sorting Person List =============================================================
    /** Sorts the Room Book by name, phone, room or phone depending on the sortCriteria */
    public void sortBy(String sortCriteria) throws AlreadySortedException {
        roomBook.sortBy(sortCriteria);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateRoomBookChanged();
    }

}
