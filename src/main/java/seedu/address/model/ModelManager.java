package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static java.util.stream.Collectors.toCollection;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.storage.PasswordSecurity.getSha512SecurePassword;

import java.util.Date;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

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
    private final UserPrefs userPrefs;

    private FilteredList<ReadOnlyPerson> filteredWhitelistedPersons;
    private FilteredList<ReadOnlyPerson> filteredBlacklistedPersons;
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
        filteredWhitelistedPersons = new FilteredList<>(this.addressBook.getWhitelistedPersonList());
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
     * @return String value of the current displayed list
     */
    @Override
    public String getCurrentList() {
        return currentList;
    }

    /**
     * Sets String value of the current displayed list using value of {@param currentList}
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
     * Removes a specific person from blacklist in the AddressBook.
     * @param target to be removed from blacklist.
     * @return removedBlacklistedPerson
     * @throws PersonNotFoundException if no person is found.
     */
    @Override
    public synchronized ReadOnlyPerson removeBlacklistedPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        ReadOnlyPerson removedBlacklistedPerson = addressBook.removeBlacklistedPerson(target);
        updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);
        changeListTo(BlacklistCommand.COMMAND_WORD);
        indicateAddressBookChanged();
        return removedBlacklistedPerson;
    }

    /**
     * Deletes a specific person from whitelist in the AddressBook.
     * @param target to be removed from whitelist.
     * @return removedBlacklistedPerson
     * @throws PersonNotFoundException if no person is found.
     */
    @Override
    public synchronized ReadOnlyPerson removeWhitelistedPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        ReadOnlyPerson whitelistedPerson = addressBook.removeWhitelistedPerson(target);
        updateFilteredWhitelistedPersonList(PREDICATE_SHOW_ALL_WHITELISTED_PERSONS);
        indicateAddressBookChanged();
        return whitelistedPerson;
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    /**
     * Adds a specific person to blacklist in the AddressBook.
     * @param person to be updated.
     * @return newBlacklistedPerson
     * @throws DuplicatePersonException if this operation causes a contact to be a duplicate of another.
     */
    @Override
    public synchronized ReadOnlyPerson addBlacklistedPerson(ReadOnlyPerson person) {
        ReadOnlyPerson newBlacklistPerson = addressBook.addBlacklistedPerson(person);
        updateFilteredBlacklistedPersonList(PREDICATE_SHOW_ALL_BLACKLISTED_PERSONS);
        changeListTo(BlacklistCommand.COMMAND_WORD);
        indicateAddressBookChanged();
        return newBlacklistPerson;
    }

    /**
     * Adds a specific person to whitelist in the AddressBook.
     * @param person to be updated.
     * @return whitelistedPerson
     * @throws DuplicatePersonException if this operation causes a contact to be a duplicate of another.
     */
    @Override
    public synchronized ReadOnlyPerson addWhitelistedPerson(ReadOnlyPerson person) {
        ReadOnlyPerson whitelistedPerson = person;

        try {
            whitelistedPerson = addressBook.resetPersonDebt(person);
            whitelistedPerson = addressBook.setDateRepaid(whitelistedPerson);
        } catch (PersonNotFoundException e) {
            assert false : "This person cannot be missing from addressbook";
        }

        if (!whitelistedPerson.isBlacklisted()) {
            whitelistedPerson = addressBook.addWhitelistedPerson(whitelistedPerson);
        }
        updateFilteredWhitelistedPersonList(PREDICATE_SHOW_ALL_WHITELISTED_PERSONS);
        indicateAddressBookChanged();
        return whitelistedPerson;
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
     * Reads the masterlist and updates the blacklist accordingly.
     */
    public void syncBlacklist() {
        filteredBlacklistedPersons = new FilteredList<>(this.addressBook.getBlacklistedPersonList());
    }

    /**
     * Reads the masterlist and updates the whitelist accordingly.
     */
    public void syncWhitelist() {
        filteredWhitelistedPersons = new FilteredList<>(this.addressBook.getWhitelistedPersonList());
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
        if (fileUsername.equals(username) && checkAgainstPasswordFromUserPref(password.toString(),
                userPrefs.getPasswordSalt())) {
            raise(new LoginAppRequestEvent(true));
        } else {
            raise(new LoginAppRequestEvent(false));
            throw new UserNotFoundException();
        }
    }

    /**
     * Logs user out
     */
    public void logout() {
        raise(new LoginAppRequestEvent(false));
    }

    public String getUsernameFromUserPref() {
        return userPrefs.getAdminUsername();
    }

    public String getPasswordFromUserPref() {
        return userPrefs.getAdminPassword();
    }

    /**
     * Checks the entered password against the password stored in {@code UserPrefs} class
     * @param currPassword password entered by user
     * @return true if the hash generated from the entered password matches the hashed password stored
     * in {@code UserPrefs}
     */
    public boolean checkAgainstPasswordFromUserPref(String currPassword, byte[] salt) {
        String hashedPassword = getSha512SecurePassword(currPassword, salt);
        return hashedPassword.equals(userPrefs.getAdminPassword());
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
     * @return repayingPerson
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     * @throws IllegalValueException if {@code amount} that is repaid by the person is more than the debt owed.
     */
    @Override
    public ReadOnlyPerson deductDebtFromPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException,
            IllegalValueException {
        ReadOnlyPerson repayingPerson = addressBook.deductDebtFromPerson(target, amount);
        indicateAddressBookChanged();
        return repayingPerson;
    }
    //@@author

    @Override
    public void changeListTo(String listName) {
        raise(new ChangeInternalListEvent(listName));
    }

    @Override
    public void updateDebtFromInterest(ReadOnlyPerson person, int differenceInMonths) {
        String accruedAmount = person.calcAccruedAmount(differenceInMonths);
        try {
            Debt amount = new Debt(accruedAmount);
            addDebtToPerson(person, amount);
        } catch (PersonNotFoundException pnfe) {
            assert false : "Should not occur as person obtained from allPersons";
        } catch (IllegalValueException ive) {
            assert false : Debt.MESSAGE_DEBT_CONSTRAINTS;
        }
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

    /**
     * Returns an unmodifiable view of the whitelist of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredWhitelistedPersonList() {
        setCurrentList("whitelist");
        return FXCollections.unmodifiableObservableList(filteredWhitelistedPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    /**
     * Obtains the latest list of blacklisted persons from masterlist and adds to {@code filteredBlacklistedPersons}
     * Filters {@code filteredBlacklistedPersons} according to given {@param predicate}
     */
    @Override
    public void updateFilteredBlacklistedPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        syncBlacklist();
        filteredBlacklistedPersons.setPredicate(predicate);
    }

    /**
     * Obtains the latest list of whitelisted persons from masterlist and adds to {@code filteredWhitelistedPersons}
     * Filters {@code filteredWhitelistedPersons} according to given {@param predicate}
     */
    @Override
    public void updateFilteredWhitelistedPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        syncWhitelist();
        filteredWhitelistedPersons.setPredicate(predicate);
    }

    /**
     * Obtains and updates the list of persons that share the same cluster as {@param selectedPerson}.
     */
    @Override
    public void updateSelectedPerson(ReadOnlyPerson selectedPerson) {
        this.selectedPerson = selectedPerson;
        nearbyPersons = allPersons.stream().filter(person -> person.isSameCluster(selectedPerson))
                .collect(toCollection(FXCollections::observableArrayList));
    }

    /**
     * Retrieves the full list of persons nearby a particular person.
     */
    @Override
    public ObservableList<ReadOnlyPerson> getNearbyPersons() {
        return nearbyPersons;
    }

    /**
     * Retrieves the current selected person.
     */
    @Override
    public ReadOnlyPerson getSelectedPerson() {
        return selectedPerson;
    }

    /**
     * Retrieves the full list of persons in addressbook.
     */
    @Override
    public ObservableList<ReadOnlyPerson> getAllPersons() {
        return allPersons;
    }

    /**
     * Sorts the {@code internal list} in addressbook according to {@param order}
     */
    @Override
    public void sortBy(String order) throws IllegalArgumentException {
        addressBook.sortBy(order);
        indicateAddressBookChanged();
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


    //==================== Event Handling Code ===============================================================

    @Subscribe
    public void handleLoginUpdateDebt(LoginAppRequestEvent event) {
        // login is successful
        if (event.getLoginStatus() == true) {
            for (ReadOnlyPerson person : allPersons) {
                if (!person.getInterest().value.equals("No interest set.")
                        && (person.checkUpdateDebt(new Date()) != 0)) {
                    updateDebtFromInterest(person, person.checkUpdateDebt(new Date()));
                }
            }
        }
    }

}
