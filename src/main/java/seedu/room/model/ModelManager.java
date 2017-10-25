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
import seedu.room.commons.events.model.ResidentBookChangedEvent;
import seedu.room.logic.commands.exceptions.AlreadySortedException;
import seedu.room.model.person.Person;
import seedu.room.model.person.ReadOnlyPerson;
import seedu.room.model.person.UniquePersonList;
import seedu.room.model.person.exceptions.DuplicatePersonException;
import seedu.room.model.person.exceptions.PersonNotFoundException;


/**
 * Represents the in-memory model of the resident book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final ResidentBook residentBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;

    /**
     * Initializes a ModelManager with the given residentBook and userPrefs.
     */
    public ModelManager(ReadOnlyResidentBook residentBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(residentBook, userPrefs);

        logger.fine("Initializing with resident book: " + residentBook + " and user prefs " + userPrefs);

        this.residentBook = new ResidentBook(residentBook);
        filteredPersons = new FilteredList<>(this.residentBook.getPersonList());
        try {
            deleteTemporary(this.residentBook);
        } catch (PersonNotFoundException e) {
            logger.warning("no such person found");
        }
    }

    public ModelManager() {
        this(new ResidentBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyResidentBook newData) {
        residentBook.resetData(newData);
        indicateResidentBookChanged();
    }

    @Override
    public ReadOnlyResidentBook getResidentBook() {
        return residentBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateResidentBookChanged() {
        raise(new ResidentBookChangedEvent(residentBook));
    }

    /** delete temporary persons on start up of the app */
    public synchronized void deleteTemporary(ResidentBook residentBook) throws PersonNotFoundException {
        UniquePersonList personsList = residentBook.getUniquePersonList();
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
        residentBook.removePerson(target);
        indicateResidentBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        residentBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateResidentBookChanged();
    }

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


    //=========== Sorting Person List =============================================================
    /** Sorts the Resident Book by name, phone, room or phone depending on the sortCriteria */
    public void sortBy(String sortCriteria) throws AlreadySortedException {
        residentBook.sortBy(sortCriteria);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateResidentBookChanged();
    }

}
