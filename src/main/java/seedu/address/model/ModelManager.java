package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.group.Group;
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

    //@@author namvd2709
    @Override
    public synchronized void addAppointment(Appointment appointment) throws IllegalValueException,
                                        UniqueAppointmentList.ClashAppointmentException {
        addressBook.addAppointment(appointment);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteAppointment(Appointment target) {
        addressBook.removeAppointment(target);
        indicateAddressBookChanged();
    }

    //@@author
    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //@@author arturs68
    @Override
    public void updateGroups(Group group) {
        if (!addressBook.getGroupList().contains(group)) {
            return;
        }
        for (ReadOnlyPerson person : addressBook.getPersonList()) {
            if (person.getGroups().contains(group)) {
                return;
            }
        }
        Set<Group> newGroups = addressBook.getGroupList()
                .stream()
                .filter(x -> !x.equals(group))
                .collect(Collectors.toSet());

        addressBook.setGroups(newGroups);
        indicateAddressBookChanged();
    }
    //@@author

    @Override
    public boolean removeTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException {
        if (!addressBook.getTagList().contains(tag)) {
            return false;
        }

        for (ReadOnlyPerson oldPerson : addressBook.getPersonList()) {
            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = newPerson.getTags()
                                        .stream()
                                        .filter(x -> !x.tagName.equals(tag.tagName))
                                        .collect(Collectors.toSet());

            newPerson.setTags(newTags);
            addressBook.updatePerson(oldPerson, newPerson);
        }

        Set<Tag> newTags = addressBook.getTagList()
                                      .stream()
                                      .filter(x -> !x.tagName.equals(tag.tagName))
                                      .collect(Collectors.toSet());

        addressBook.setTags(newTags);
        indicateAddressBookChanged();
        return true;
    }

    //@@author namvd2709
    @Override
    public Set<Appointment> getAllAppointments() {
        return addressBook.getAllAppointments();
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
