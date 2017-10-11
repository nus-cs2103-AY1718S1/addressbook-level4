package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

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

    /**
     * Raises an event to indicate the model has changed
     */
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
    public void pinPerson(ReadOnlyPerson person) throws CommandException, PersonNotFoundException {
        try {
            List<ReadOnlyPerson> newList = new ArrayList<>();
            Person addPin = addPinTag(person);
            updatePerson(person, addPin);
            combineList(newList);
            indicateAddressBookChanged();
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(AddCommand.MESSAGE_DUPLICATE_PERSON);
        }
    }

    @Override
    public void unpinPerson(ReadOnlyPerson person) throws CommandException, PersonNotFoundException {
        try {
            List<ReadOnlyPerson> newList = new ArrayList<>();
            Person removePin = removePinTag(person);
            updatePerson(person, removePin);
            combineList(newList);
            indicateAddressBookChanged();
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(AddCommand.MESSAGE_DUPLICATE_PERSON);
        }
    }

    private void combineList(List<ReadOnlyPerson> newList) throws DuplicatePersonException {
        newList.addAll(getFilteredPersonList().filtered(PREDICATE_SHOW_PINNED_PERSONS).sorted());
        newList.addAll(getFilteredPersonList().filtered(PREDICATE_SHOW_UNPINNED_PERSONS).sorted());
        addressBook.setPersons(newList);
    }

    /**
     * @param personToPin
     * @return updated Person with added pin to be added to the address book
     * @throws CommandException
     */
    private Person addPinTag(ReadOnlyPerson personToPin) throws CommandException {
        /**
         * Create a new UniqueTagList to add pin tag into the list.
         */
        UniqueTagList updatedTags = new UniqueTagList(personToPin.getTags());
        updatedTags.addPinTag();

        return new Person(personToPin.getName(), personToPin.getPhone(), personToPin.getEmail(),
                personToPin.getAddress(), updatedTags.toSet());
    }

    /**
     * @param personToUnpin
     * @return updated Person with removed pin to be added to the address book
     * @throws CommandException
     */
    private Person removePinTag(ReadOnlyPerson personToUnpin) throws CommandException {
        try {
            UniqueTagList updatedTags = new UniqueTagList(personToUnpin.getTags());
            updatedTags.removePinTag();
            return new Person(personToUnpin.getName(), personToUnpin.getPhone(), personToUnpin.getEmail(),
                    personToUnpin.getAddress(), updatedTags.toSet());
        } catch (IllegalValueException ive) {
            throw new CommandException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
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
    public void sort(String sortType) throws DuplicatePersonException {
        switch (sortType) {
        case "name":
            addressBook.setPersons(sortByName());
            break;

        case "phone":
            addressBook.setPersons(sortByPhone());
            break;

        case "email":
            addressBook.setPersons(sortByEmail());
            break;

        default:
            break;

        }
        indicateAddressBookChanged();
    }

    private ArrayList<ReadOnlyPerson> sortByName() {
        ArrayList<ReadOnlyPerson> newList = new ArrayList<>();
        SortedList<ReadOnlyPerson> sortedList =
                getFilteredPersonList().filtered(PREDICATE_SHOW_PINNED_PERSONS).sorted(COMPARATOR_SORT_BY_NAME);
        newList.addAll(sortedList);
        sortedList = getFilteredPersonList().filtered(PREDICATE_SHOW_UNPINNED_PERSONS).sorted(COMPARATOR_SORT_BY_NAME);
        newList.addAll(sortedList);

        return newList;
    }

    private ArrayList<ReadOnlyPerson> sortByPhone() {
        ArrayList<ReadOnlyPerson> newList = new ArrayList<>();
        SortedList<ReadOnlyPerson> sortedList =
                getFilteredPersonList().filtered(PREDICATE_SHOW_PINNED_PERSONS).sorted(COMPARATOR_SORT_BY_PHONE);
        newList.addAll(sortedList);
        sortedList = getFilteredPersonList().filtered(PREDICATE_SHOW_UNPINNED_PERSONS).sorted(COMPARATOR_SORT_BY_PHONE);
        newList.addAll(sortedList);

        return newList;
    }

    private ArrayList<ReadOnlyPerson> sortByEmail() {
        ArrayList<ReadOnlyPerson> newList = new ArrayList<>();
        SortedList<ReadOnlyPerson> sortedList =
                getFilteredPersonList().filtered(PREDICATE_SHOW_PINNED_PERSONS).sorted(COMPARATOR_SORT_BY_EMAIL);
        newList.addAll(sortedList);
        sortedList = getFilteredPersonList().filtered(PREDICATE_SHOW_UNPINNED_PERSONS).sorted(COMPARATOR_SORT_BY_EMAIL);
        newList.addAll(sortedList);

        return newList;
    }
}
