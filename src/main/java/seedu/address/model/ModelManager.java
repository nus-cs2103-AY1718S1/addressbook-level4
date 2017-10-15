package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.person.Address;
import seedu.address.model.person.Email;
import seedu.address.model.person.Phone;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.predicates.UniqueAddressPredicate;
import seedu.address.model.person.predicates.UniqueEmailPredicate;
import seedu.address.model.person.predicates.UniquePhonePredicate;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;

    private final HashSet<ReadOnlyPerson> favourList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        favourList = new HashSet<ReadOnlyPerson>();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public HashSet<Address> getUniqueAdPersonSet() {
        HashSet<Address> set = new HashSet<>();

        ObservableList<ReadOnlyPerson> personLst = getFilteredPersonList();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        for (ReadOnlyPerson p : personLst) {
            if (!set.contains(p.getAddress())) {
                set.add(p.getAddress());
            }
        }
        return set;
    }

    @Override
    public HashSet<Email> getUniqueEmailPersonSet() {
        HashSet<Email> set = new HashSet<>();

        ObservableList<ReadOnlyPerson> personLst = getFilteredPersonList();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        for (ReadOnlyPerson p : personLst) {
            if (!set.contains(p.getEmail())) {
                set.add(p.getEmail());
            }
        }
        return set;
    }

    @Override
    public HashSet<Phone> getUniquePhonePersonSet() {
        HashSet<Phone> set = new HashSet<>();

        ObservableList<ReadOnlyPerson> personLst = getFilteredPersonList();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        for (ReadOnlyPerson p : personLst) {
            if (!set.contains(p.getPhone())) {
                set.add(p.getPhone());
            }
        }
        return set;
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
    public void collectPerson(ReadOnlyPerson target) throws DuplicatePersonException {
        if (!favourList.contains(target)) {
            favourList.add(target);
        } else {
            throw new DuplicatePersonException();
        }
    }

    @Override
    public synchronized void deletePersonSet(List<ReadOnlyPerson> personList) throws PersonNotFoundException {

        for (ReadOnlyPerson person : personList) {
            addressBook.removePerson(person);
        }
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        handleListingUnit();
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

    @Override
    public void handleListingUnit() {
        switch (ListingUnit.getCurrentListingUnit()) {

        case ADDRESS:
            UniqueAddressPredicate addressPredicate = new UniqueAddressPredicate(getUniqueAdPersonSet());
            updateFilteredPersonList(addressPredicate);
            break;

        case PHONE:
            UniquePhonePredicate phonePredicate = new UniquePhonePredicate(getUniquePhonePersonSet());
            updateFilteredPersonList(phonePredicate);
            break;

        case EMAIL:
            UniqueEmailPredicate emailPredicate = new UniqueEmailPredicate(getUniqueEmailPersonSet());
            updateFilteredPersonList(emailPredicate);
            break;

        default:
            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        }
    }

}
