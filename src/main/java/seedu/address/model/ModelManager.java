package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.person.Appointment;
import seedu.address.model.person.HasPotentialDuplicatesPredicate;
import seedu.address.model.person.Name;
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

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
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

    /**
     * On/Off tag colors for AddressBook
     * Updates UI by refreshing personListPanel
     */
    @Override
    public synchronized void setTagColor(String tagString, String color) {
        Set<Tag> tag = new HashSet<>(addressBook.getTagList());
        addressBook.setTags(tag, tagString, color);
        indicateAddressBookChanged();
    }

    /**
     * Deletes all persons in the {@code AddressBook} who have a particular {@code tag}.
     * @param tag all persons containing this tag will be deleted
     */
    public void deletePersonsWithTag(Tag tag) throws PersonNotFoundException {
        addressBook.deletePersonsWithTag(tag);
        indicateAddressBookChanged();
    }

    /**
     * Deletes all persons in the {@code AddressBook} who have a particular {@code tag}.
     * @param tags all persons containing this tag will be deleted
     */
    @Override
    public void deletePersonsByTags(Set<Tag> tags) throws PersonNotFoundException {
        for (Tag tag : tags) {
            deletePersonsWithTag(tag);
        }
    }

    /**
     * Adds appointment for a contact in address book
     *
     */
    @Override
    public void addAppointment(Appointment appointment) throws PersonNotFoundException {
        addressBook.addAppointment(appointment);
        indicateAddressBookChanged();
    }

    /**
     * @return an unmodifiable view of the list of ReadOnlyPerson that has nonNull appointment date,
     * in chronological order
     */
    @Override
    public ObservableList<ReadOnlyPerson> listAppointment() {

        ObservableList<ReadOnlyPerson> list = addressBook.getPersonListSortByAppointment();


        return FXCollections.unmodifiableObservableList(list);
    }

    /**
     * @return an unmodifiable view of the list of ReadOnlyPerson that has nonNull name,
     * in increasing chronological order
     */
    @Override
    public ObservableList<ReadOnlyPerson> listNameAscending() {
        ObservableList<ReadOnlyPerson> list = addressBook.getPersonListSortByNameAscending();
        return FXCollections.unmodifiableObservableList(list);
    }

    /**
     * @return an unmodifiable view of the list of ReadOnlyPerson that has nonNull name,
     * in decreasing chronological order
     */
    @Override
    public ObservableList<ReadOnlyPerson> listNameDescending() {
        ObservableList<ReadOnlyPerson> list = addressBook.getPersonListSortByNameDescending();
        return FXCollections.unmodifiableObservableList(list);
    }

    /**
     * @return an unmodifiable view of the list of ReadOnlyPerson that is reversed
     */
    @Override
    public ObservableList<ReadOnlyPerson> listNameReversed() {
        ObservableList<ReadOnlyPerson> list = addressBook.getPersonListReversed();
        return FXCollections.unmodifiableObservableList(list);

     /**
     * Gets a list of duplicate names
     */
    private ArrayList<Name> getDuplicateNames() {
        ArrayList<Name> examinedNames = new ArrayList<>();
        ArrayList<Name> duplicateNames = new ArrayList<>();
        ObservableList<ReadOnlyPerson> allPersonsInAddressBook = getFilteredPersonList();

        for (ReadOnlyPerson person : allPersonsInAddressBook) {
            if (examinedNames.contains(person.getName())) {
                duplicateNames.add(person.getName());
            }
            examinedNames.add(person.getName());
        }
        return duplicateNames;
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

    @Override
    public void updateDuplicatePersonList() {
        ArrayList<Name> duplicateNames = getDuplicateNames();
        HasPotentialDuplicatesPredicate predicate = new HasPotentialDuplicatesPredicate(duplicateNames);
        updateFilteredPersonList(predicate);
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
