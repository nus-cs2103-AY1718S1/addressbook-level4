package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.AliasTokenChangedEvent;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.alias.exceptions.DuplicateTokenKeywordException;
import seedu.address.model.alias.exceptions.TokenKeywordNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<ReadOnlyAliasToken> filteredAliases;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredAliases = new FilteredList<>(this.addressBook.getAliasTokenList());
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
    public void sortList(String toSort) {
        addressBook.sortList(toSort);
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

    private void indicateAliasTokenAdded(ReadOnlyAliasToken token) {
        raise(new AliasTokenChangedEvent(token, AliasTokenChangedEvent.Action.Added));
    }

    private void indicateAliasTokenRemoved(ReadOnlyAliasToken token) {
        raise(new AliasTokenChangedEvent(token, AliasTokenChangedEvent.Action.Removed));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void hidePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.hidePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void pinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.pinPerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void unpinPerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.unpinPerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_NOT_HIDDEN);
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
    public synchronized void addAliasToken(ReadOnlyAliasToken toAdd) throws DuplicateTokenKeywordException {
        addressBook.addAliasToken(toAdd);
        indicateAddressBookChanged();
        indicateAliasTokenAdded(toAdd);
    }

    @Override
    public synchronized void deleteAliasToken(ReadOnlyAliasToken toRemove) throws TokenKeywordNotFoundException {
        addressBook.removeAliasToken(toRemove);
        indicateAddressBookChanged();
        indicateAliasTokenRemoved(toRemove);
    }

    @Override
    public int getAliasTokenCount() {
        return addressBook.getAliasTokenCount();
    }

    @Override
    public ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList() {
        return FXCollections.unmodifiableObservableList(filteredAliases);
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
                && filteredPersons.equals(other.filteredPersons)
                && filteredAliases.equals(other.filteredAliases);
    }

}
