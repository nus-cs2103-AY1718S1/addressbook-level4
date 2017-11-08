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
import seedu.address.commons.events.ui.ShowAllTodoItemsEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TodoItem;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.DuplicateTodoItemException;
import seedu.address.model.person.exceptions.NoPersonFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;

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
    //@@author aaronyhsoh
    @Override
    public void favouritePerson(ReadOnlyPerson target, ReadOnlyPerson favouritedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, favouritedPerson);

        addressBook.favouritePerson(target, favouritedPerson);
        indicateAddressBookChanged();
    }

    //@@author Hailinx
    @Override
    public void addTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws DuplicatePersonException, PersonNotFoundException, DuplicateTodoItemException {
        requireAllNonNull(target, todoItem);

        addressBook.addTodoItem(target, todoItem);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, todoItem);

        addressBook.deleteTodoItem(target, todoItem);
        indicateAddressBookChanged();
    }

    @Override
    public void resetTodoItem(ReadOnlyPerson target)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(target);

        addressBook.resetTodoItem(target);
        indicateAddressBookChanged();
    }

    @Override
    public void updateTodoItemList() {
        raise(new ShowAllTodoItemsEvent());
    }

    //@@author qihao27
    @Override
    public void sortPerson(String option) throws NoPersonFoundException {
        requireNonNull(option);

        addressBook.sortPerson(option);
        indicateAddressBookChanged();
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
