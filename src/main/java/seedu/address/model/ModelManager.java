package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;

import seedu.address.commons.events.model.DatabaseChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.account.ReadOnlyAccount;
import seedu.address.model.account.exceptions.DuplicateAccountException;
import seedu.address.model.person.AgeComparator;
import seedu.address.model.person.BirthdayComparator;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.PriorityComparator;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.Reminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.model.tag.Tag;


/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {

    public static final String MESSAGE_DUPLICATE_PERSON =  "Duplicate persons in AddressBook.";
    public static final String MESSAGE_DUPLICATE_REMINDER =  "Duplicate reminders in AddressBook.";
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final Database database;
    private final FilteredList<ReadOnlyAccount> filteredAccount;
    private final FilteredList<ReadOnlyReminder> filteredReminders;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyDatabase database, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.database = new Database(database);
        filteredAccount = new FilteredList<ReadOnlyAccount>(this.database.getAccountList());
        filteredReminders = new FilteredList<>(this.addressBook.getReminderList());
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());

    }

    public ModelManager() {
        this(new AddressBook(), new Database(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public void resetDatabase(ReadOnlyDatabase newData) {
        database.resetData(newData);
        indicateDatabaseChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    @Override
    public ReadOnlyDatabase getDatabase() {
        return database;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    private void indicateDatabaseChanged() {
        raise(new DatabaseChangedEvent(database));
    }

    //// person-level operations

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
    public void deletePersonTag(Tag tag) throws PersonNotFoundException, DuplicatePersonException  {
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);

            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = newPerson.getTags();
            newTags.remove(tag);
            newPerson.setTags(newTags);

            addressBook.updatePerson(oldPerson, newPerson);
        }
    }

    //@@author duyson98
    //// reminder-level operations

    @Override
    public synchronized void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException {
        addressBook.removeReminder(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addReminder(ReadOnlyReminder reminder) throws DuplicateReminderException {
        addressBook.addReminder(reminder);
        updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireAllNonNull(target, editedReminder);
        addressBook.updateReminder(target, editedReminder);
        indicateAddressBookChanged();
    }

    @Override
    public void deleteReminderTag(Tag tag) throws ReminderNotFoundException, DuplicateReminderException  {
        for (int i = 0; i < addressBook.getReminderList().size(); i++) {
            ReadOnlyReminder oldReminder = addressBook.getReminderList().get(i);

            Reminder newReminder = new Reminder(oldReminder);
            Set<Tag> newTags = newReminder.getTags();
            newTags.remove(tag);
            newReminder.setTags(newTags);

            addressBook.updateReminder(oldReminder, newReminder);
        }
    }
    //@@author

    //@@author cqhchan
    @Override
    public synchronized void deleteAccount(ReadOnlyAccount target) throws PersonNotFoundException {
        database.removeAccount(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addAccount(ReadOnlyAccount account) throws DuplicateAccountException {
        database.addAccount(account);
        updateFilteredAccountList(PREDICATE_SHOW_ALL_ACCOUNTS);
        indicateDatabaseChanged();
    }

    @Override
    public synchronized boolean checkAccount(ReadOnlyAccount account) {
        for (ReadOnlyAccount tempAccount : database.getAccountList()) {
            if (tempAccount.getUsername().fullName.equals(account.getUsername().fullName)
                    && tempAccount.getPassword().value.equals(account.getPassword().value)) {
                return true;
            }
        }

        return false;
    }

    //@@author

    //// tag-level operations

    @Override
    public void deleteUnusedTag(Tag tag) {
        if (addressBook.isUnusedTag(tag)) {
            addressBook.removeTag(tag);
            indicateAddressBookChanged();
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

    //=========== Filtered Account List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyAccount> getFilteredAccountList() {
        return FXCollections.unmodifiableObservableList(filteredAccount);
    }

    @Override
    public void updateFilteredAccountList(Predicate<ReadOnlyAccount> predicate) {
        requireNonNull(predicate);
        filteredAccount.setPredicate(predicate);
    }

    //@@author duyson98
    //=========== Filtered Reminder List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyReminder} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyReminder> getFilteredReminderList() {
        logger.info("it came here");

        return FXCollections.unmodifiableObservableList(filteredReminders);
    }

    @Override
    public void updateFilteredReminderList(Predicate<ReadOnlyReminder> predicate) {
        requireNonNull(predicate);
        filteredReminders.setPredicate(predicate);
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
                && filteredReminders.equals(other.filteredReminders);
    }
    //@@author

    @Override
    public Boolean checkIfPersonListEmpty(ArrayList<ReadOnlyPerson> contactList) {
        if (filteredPersons.isEmpty()) {
            return true;
        }
        return false;
    }

    //@@author duyson98
    @Override
    public Boolean checkIfReminderListEmpty(ArrayList<ReadOnlyReminder> contactList) {
        if (filteredReminders.isEmpty()) {
            return true;
        }
        return false;
    }
    //@@author

    //@@author inGall
    /**
     * @param contactList
     * @throws CommandException
     */
    public void sortListByName(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
        contactList.addAll(filteredPersons);
        Collections.sort(contactList, Comparator.comparing(p -> p.toString().toLowerCase()));

        try {
            addressBook.setPersons(contactList);
            indicateAddressBookChanged();
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }

    /**
     * @param contactList
     * @throws CommandException
     */
    public void sortListByAge(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
        contactList.addAll(filteredPersons);
        Collections.sort(contactList, new AgeComparator());

        try {
            addressBook.setPersons(contactList);
            indicateAddressBookChanged();
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }

    /**
     * @param contactList
     * @throws CommandException
     */
    public void sortListByBirthday(ArrayList<ReadOnlyPerson> contactList) throws CommandException {
        contactList.addAll(filteredPersons);
        Collections.sort(contactList, new BirthdayComparator());

        try {
            addressBook.setPersons(contactList);
            indicateAddressBookChanged();
        } catch (DuplicatePersonException e) {
            throw new CommandException(MESSAGE_DUPLICATE_PERSON);
        }
    }

    /**
     * @param contactList
     * @throws CommandException
     */
    public void sortListByPriority(ArrayList<ReadOnlyReminder> contactList) throws CommandException {
        contactList.addAll(filteredReminders);
        Collections.sort(contactList, new PriorityComparator());

        try {
            addressBook.setReminders(contactList);
            indicateAddressBookChanged();
        } catch (DuplicateReminderException e) {
            throw new CommandException(MESSAGE_DUPLICATE_REMINDER);
        }
    }
    //@@author
}
