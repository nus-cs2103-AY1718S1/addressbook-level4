package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;
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
import seedu.address.logic.commands.BlacklistCommand;
import seedu.address.model.person.Debt;
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
    private final ObservableList<ReadOnlyPerson> allPersons;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private FilteredList<ReadOnlyPerson> filteredBlacklistedPersons;
    private final UserPrefs userPrefs;
    private ObservableList<ReadOnlyPerson> nearbyPersons;
    private ReadOnlyPerson selectedPerson;

    private String currentList;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        allPersons = this.addressBook.getPersonList();
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredBlacklistedPersons = new FilteredList<>(this.addressBook.getBlacklistedPersonList());

        this.userPrefs = userPrefs;

        this.currentList = "list";
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
     * Returns {@code String} value of the current displayed list.
     */
    @Override
    public String getCurrentList() {
        return currentList;
    }

    /**
     * Sets {@code String} value of the current displayed list.
     * from the value of {@param currentList}
     */
    @Override
    public void setCurrentList(String currentList) {
        this.currentList = currentList;
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

    /**
     * Deletes a specific person from blacklist in the AddressBook.
     * @param target to be removed from blacklist.
     * @throws PersonNotFoundException if no person is found.
     */
    @Override
    public synchronized void removeBlacklistedPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removeBlacklistedPerson(target);
        updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    /**
     * Adds a specific tag person to blacklist in the AddressBook.
     * @param person to be updated.
     * @throws DuplicatePersonException if this operation causes a contact to be a duplicate of another.
     */
    @Override
    public synchronized void addBlacklistedPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addBlacklistedPerson(person);
        updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);
        changeListTo(BlacklistCommand.COMMAND_WORD);
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
     * Deletes a specified tag from every person in the AddressBook.
     * @param tag the tag to be deleted.
     * @throws PersonNotFoundException if no person is found.
     * @throws DuplicatePersonException if this operation causes a contact to be a duplicate of another.
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

    /**
     * Reads the main list and updates the blacklist accordingly.
     */
    public void syncBlacklist() {
        filteredBlacklistedPersons = new FilteredList<>(this.addressBook.getBlacklistedPersonList());
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

    /**
     * Increase the debt of a person by the amount indicated
     * @param target person in the address book who borrowed more money
     * @param amount amount that the person borrowed. Must be either a positive integer or positive number with
     *               two decimal places
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    @Override
    public void addDebtToPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException {
        addressBook.addDebtToPerson(target, amount);
        indicateAddressBookChanged();
    }

    /**
     * Decrease the debt of a person by the amount indicated
     * @param target person in the address book who paid back some money
     * @param amount amount that the person paid back. Must be either a positive integer or positive number with
     *               two decimal places
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     * @throws IllegalValueException if {@code amount} that is repaid by the person is more than the debt owed.
     */
    @Override
    public void deductDebtFromPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException,
            IllegalValueException {
        addressBook.deductDebtFromPerson(target, amount);
        indicateAddressBookChanged();
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
        setCurrentList("list");
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    /**
     * Returns an unmodifiable view of the blacklist of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredBlacklistedPersonList() {
        setCurrentList("blacklist");
        return FXCollections.unmodifiableObservableList(filteredBlacklistedPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    /**
     * Obtains the latest list of blacklisted persons from main list and adds to {@code filteredBlacklistedPersons}
     * Raises an {@code event} to signal the requirement for change in displayed list in {@code PersonListPanel}
     * Filters {@code filteredBlacklistedPersons} according to given {@param predicate}
     */
    @Override
    public void updateFilteredBlacklistedPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        syncBlacklist();
        filteredBlacklistedPersons.setPredicate(predicate);
    }

    @Override
    public void updateSelectedPerson(ReadOnlyPerson selectedPerson) {
        this.selectedPerson = selectedPerson;
        nearbyPersons = allPersons.stream().filter(person -> person.isSameCluster(selectedPerson))
                .collect(toCollection(FXCollections::observableArrayList));
    }

    @Override
    public ObservableList<ReadOnlyPerson> getNearbyPersons() {
        return nearbyPersons;
    }

    @Override
    public ObservableList<ReadOnlyPerson> getAllPersons() {
        return allPersons;
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
                && filteredBlacklistedPersons.equals(other.filteredBlacklistedPersons);
    }

}
