package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.beans.property.ObjectProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.ChangeInternalListEvent;
import seedu.address.commons.events.ui.LoginAppRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.exceptions.UserNotFoundException;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<ReadOnlyPerson> filteredBlacklistedPersons;
    private final FilteredList<ReadOnlyPerson> filteredWhitelistedPersons;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredBlacklistedPersons = new FilteredList<ReadOnlyPerson>(this.addressBook.getBlacklistedPersonList());
        filteredWhitelistedPersons = new FilteredList<ReadOnlyPerson>(this.addressBook.getWhitelistedPersonList());

        this.userPrefs = userPrefs;
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
    public synchronized void removeBlacklistedPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removeBlacklistedPerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void removeWhitelistedPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removeWhitelistedPerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addBlacklistedPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addBlacklistedPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addWhitelistedPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addWhitelistedPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_WHITELISTED_PERSONS);
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
     * Deletes a specified tag from every person in the AddressBook
     * @param tag
     * @throws PersonNotFoundException
     * @throws DuplicatePersonException
     */
    @Override
    public void deleteTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException, TagNotFoundException {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);
            Person personClone = new Person(oldPerson);
            ObjectProperty<UniqueTagList> oldTagList = oldPerson.tagProperty();
            Set<Tag> tagListToEdit = oldTagList.get().toSet();
            tagListToEdit.remove(tag);
            personClone.setTags(tagListToEdit);
            addressBook.updatePerson(oldPerson, personClone);
        }
        // remove from master tag list in AddressBook
        addressBook.removeTag(tag);
    }

    //@@author jelneo
    /**
     * Authenticates user
     * @throws UserNotFoundException if username and password does not match those in the user preference file
     * @throws IllegalValueException if username and password does not meet username and password requirements
     */
    public void authenticateUser(Username username, Password password) throws UserNotFoundException,
            IllegalValueException {
        Username fileUsername = new Username(getUsernameFromUserPref());
        Password filePassword = new Password(getPasswordFromUserPref());
        if (fileUsername.equals(username) && filePassword.equals(password)) {
            raise(new LoginAppRequestEvent(true));
        } else {
            raise(new LoginAppRequestEvent(false));
            throw new UserNotFoundException();
        }
    }

    public String getUsernameFromUserPref() {
        return userPrefs.getAdminUsername();
    }

    public String getPasswordFromUserPref() {
        return userPrefs.getAdminPassword();
    }

    //@@author

    @Override
    public void changeListTo(String listName) {
        raise(new ChangeInternalListEvent(listName));
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

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredBlacklistedPersonList() {
        return FXCollections.unmodifiableObservableList(filteredBlacklistedPersons);
    }

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredWhitelistedPersonList() {
        return FXCollections.unmodifiableObservableList(filteredWhitelistedPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredBlacklistedPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredBlacklistedPersons.setPredicate(predicate);
    }

    @Override
    public void updateFilteredWhitelistedPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredWhitelistedPersons.setPredicate(predicate);
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
                && filteredBlacklistedPersons.equals(other.filteredBlacklistedPersons)
                && filteredWhitelistedPersons.equals(other.filteredWhitelistedPersons);
    }

}
