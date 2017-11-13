package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.DisplayPictureChangedEvent;
import seedu.address.commons.events.model.PopularContactChangedEvent;
import seedu.address.commons.events.model.RemindersChangedEvent;
import seedu.address.commons.events.model.UpdateListForSelectionEvent;
import seedu.address.commons.events.model.UpdatePopularityCounterForSelectionEvent;
import seedu.address.commons.events.ui.ClearSelectionEvent;
import seedu.address.commons.events.ui.LoadPersonWebpageEvent;
import seedu.address.commons.events.ui.SelectFirstAfterDeleteEvent;
import seedu.address.commons.events.ui.SendingEmailEvent;
import seedu.address.commons.events.ui.ShowDefaultPanelEvent;
import seedu.address.commons.events.ui.ShowLocationEvent;
import seedu.address.model.email.Body;
import seedu.address.model.email.Service;
import seedu.address.model.email.Subject;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UpcomingBirthdayInCurrentMonthPredicate;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminders.ReadOnlyReminder;
import seedu.address.model.reminders.ReadOnlyUniqueReminderList;
import seedu.address.model.reminders.UniqueReminderList;
import seedu.address.model.reminders.exceptions.DuplicateReminderException;
import seedu.address.model.reminders.exceptions.ReminderNotFoundException;


/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<ReadOnlyPerson> filteredPersonsForBirthdayListPanel;
    private final FilteredList<ReadOnlyPerson> filteredPersonsForEmail;
    private final UniqueReminderList reminderList;
    private List<ReadOnlyPerson> listOfPersonsForPopularContacts;

    private SortedList<ReadOnlyPerson> sortedfilteredPersons;
    private SortedList<ReadOnlyPerson> sortedFilteredPersonsForBirthdayListPanel;
    private SortedList<ReadOnlyReminder> sortedReminderList;

    /**
     * Initializes a ModelManager with the given addressBook, reminders and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyUniqueReminderList reminders, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, reminders, userPrefs);

        logger.fine("Initializing with address book: " + addressBook +  " and reminders " + reminders
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.reminderList = new UniqueReminderList(reminders);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredPersonsForBirthdayListPanel = new FilteredList<>(this.addressBook.getPersonList());
        filteredPersonsForBirthdayListPanel.setPredicate(new UpcomingBirthdayInCurrentMonthPredicate());
        filteredPersonsForEmail = new FilteredList<>(this.addressBook.getPersonList());
        listOfPersonsForPopularContacts = new ArrayList<>(this.addressBook.getPersonList());
        updatePopularContactList();
        sortedfilteredPersons = new SortedList<>(filteredPersons);
        sortedFilteredPersonsForBirthdayListPanel = new SortedList<>(filteredPersonsForBirthdayListPanel,
                Comparator.comparingInt(birthday -> birthday.getBirthday().getDayOfBirthday()));
        sortedReminderList = new SortedList<>(reminderList.asObservableList(),
                Comparator.comparing(reminder -> reminder.getLocalDateTime()));
    }

    public ModelManager() {
        this(new AddressBook(), new UniqueReminderList(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
        indicatePopularContactsChangedPossibility();
        updatePopularContactList();
    }

    //@@author justinpoh
    @Override
    public void resetReminders(UniqueReminderList newReminders) {
        reminderList.setReminders(newReminders);
        indicateRemindersChanged();
    }
    //@@author

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }
    //@@author tshradheya

    /** Raises an event to indicate model has changed and favourite contacts might be updated */
    private void indicatePopularContactsChangedPossibility() {
        raise(new PopularContactChangedEvent(this));

    }

    /** Raises an event to indicate the picture has changed */
    private boolean indicateDisplayPictureChanged(String path, int newPath) throws IOException {
        DisplayPictureChangedEvent displayPictureChangedEvent = new DisplayPictureChangedEvent(path, newPath);
        raise(displayPictureChangedEvent);
        return displayPictureChangedEvent.isRead();
    }

    @Override
    public void clearSelection() {
        logger.info("Clears selection of person in list");
        raise(new ClearSelectionEvent());
    }

    //@@author

    //@author justinpoh
    /** Raises an event to indicate the reminders have changed */
    private void indicateRemindersChanged() {
        raise(new RemindersChangedEvent(reminderList));
        showDefaultPanel();
    }
    //@@author

    //@@author tshradheya

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        clearSelection();
        addressBook.removePerson(target);
        if (sortedfilteredPersons.isEmpty()) {
            showDefaultPanel();
        } else {
            raise(new SelectFirstAfterDeleteEvent());
        }
        indicateAddressBookChanged();
        indicatePopularContactsChangedPossibility();
        updatePopularContactList();
    }
    //@@author

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
        indicatePopularContactsChangedPossibility();
        updatePopularContactList();
    }
    //@@author tshradheya

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
        indicatePopularContactsChangedPossibility();
        updatePopularContactList();
    }
    //@@author

    //@@author justinpoh
    @Override
    public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder editedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireAllNonNull(target, editedReminder);

        reminderList.setReminder(target, editedReminder);
        indicateRemindersChanged();
    }
    //@@author

    //@@author tshradheya

    @Override
    public boolean addDisplayPicture(String path, int newPath) throws IOException {
        return indicateDisplayPictureChanged(path, newPath);
    }

    /**
     * Shows location of the given person
     */
    @Override
    public void showLocation(ReadOnlyPerson person) throws PersonNotFoundException {
        raise(new ShowLocationEvent(person));
    }

    /**
     * Shows webpage of the given person
     */
    @Override
    public void showPersonWebpage(ReadOnlyPerson person) throws PersonNotFoundException {
        raise(new LoadPersonWebpageEvent(person));
    }

    /**
     * Raises event for processing Email
     */
    @Override
    public void processEmailEvent(String recipients, Subject subject, Body body, Service service) {
        raise(new SendingEmailEvent(recipients, subject, body, service));
    }

    /**
     * Makes a string of all intended recipient through the given @predicate
     */
    @Override
    public String createEmailRecipients(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);

        filteredPersonsForEmail.setPredicate(predicate);
        increaseCounterByOneForATag(filteredPersonsForEmail);

        List<String> validEmails = new ArrayList<>();
        for (ReadOnlyPerson person : filteredPersonsForEmail) {
            if (Email.isValidEmail(person.getEmail().value)) {
                validEmails.add(person.getEmail().value);
            }
        }
        return String.join(",", validEmails);
    }

    @Override
    public void increaseCounterByOneForATag(List<ReadOnlyPerson> filteredPersonsForEmail) {

        for (ReadOnlyPerson person : filteredPersonsForEmail) {
            try {
                this.updatePersonsPopularityCounterByOne(person);
            } catch (DuplicatePersonException dpe) {
                assert false : "Duplicate is not possible";
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
    }

    //=========== Update Popularity counter for the required commands =======================================

    /**
     * Increases the counter by 1 increasing popularity
     * @param person whose popularity counter increased
     */
    @Override
    public void updatePersonsPopularityCounterByOne(ReadOnlyPerson person) throws DuplicatePersonException,
            PersonNotFoundException {
        ReadOnlyPerson editedPerson = increaseCounterByOne(person);

        addressBook.updatePerson(person, editedPerson);
        indicateAddressBookChanged();
        indicatePopularContactsChangedPossibility();
    }

    @Override
    public ReadOnlyPerson increaseCounterByOne(ReadOnlyPerson person) {
        person.getPopularityCounter().increasePopularityCounter();
        return new Person(person.getName(), person.getPhone(), person.getEmail(), person.getAddress(),
                person.getBirthday(), person.getNickname(), person.getDisplayPicture(), person.getPopularityCounter(),
                person.getTags());
    }

    //=========== Filtered Popular Contact List Accessors and Mutators =======================================

    /**
     * Updates the popular contact list whenever address book is changed
     */
    @Override
    public void updatePopularContactList() {
        logger.info("Popular List getting Refreshed");
        refreshWithPopulatingAddressBook();
        listOfPersonsForPopularContacts.sort((o1, o2) ->
                o2.getPopularityCounter().getCounter() - o1.getPopularityCounter().getCounter());

        getOnlyTopFiveMaximum();
    }

    @Override
    public void getOnlyTopFiveMaximum() {
        while (listOfPersonsForPopularContacts.size() > 5) {
            listOfPersonsForPopularContacts.remove(listOfPersonsForPopularContacts.size() - 1);
        }
    }

    @Override
    public void refreshWithPopulatingAddressBook() {
        listOfPersonsForPopularContacts = new ArrayList<>(this.addressBook.getPersonList());
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPopularContactList() {
        updatePopularContactList();
        return FXCollections.observableList(listOfPersonsForPopularContacts);
    }
    //@@author

    //=========== Filtered Person List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(sortedfilteredPersons);
    }

    //@@author edwinghy
    @Override
    public void sortFilteredPersonList() {

        Comparator<ReadOnlyPerson> sortByName = (o1, o2) -> o1.getName().fullName.compareTo(o2.getName().fullName);
        sortedfilteredPersons.setComparator(sortByName);
        indicateAddressBookChanged();
    }
    //@@author

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }
    //@@author tshradheya
    @Override
    public void updateFilteredPersonListForViewTag(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);

        increaseCounterByOneForATag(filteredPersons);
    }

    @Override
    public void updateFilteredListToShowAll() {
        filteredPersons.setPredicate(null);
    }

    @Override
    public void showDefaultPanel() {
        logger.info("Default panel will be shown on right on refresh");
        raise(new ShowDefaultPanelEvent());
    }
    //@@author

    //@@author justinpoh
    @Override
    public ObservableList<ReadOnlyPerson> getBirthdayPanelFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(sortedFilteredPersonsForBirthdayListPanel);
    }
    //@@author

    //=========== UniqueReminderList Accessors =================================================================

    //@@author justinpoh
    @Override
    public ObservableList<ReadOnlyReminder> getSortedReminderList() {
        return sortedReminderList;
    }

    @Override
    public ReadOnlyUniqueReminderList getUniqueReminderList() {
        return reminderList;
    }

    @Override
    public void addReminder(ReadOnlyReminder toAdd) throws DuplicateReminderException {
        reminderList.add(toAdd);
        indicateRemindersChanged();
    }

    @Override
    public void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException {
        reminderList.remove(target);
        indicateRemindersChanged();
    }

    //@@author
    //@@author tshradheya
    @Override
    public Index getIndexOfGivenPerson(ReadOnlyPerson person) {
        for (int i = 0; i < sortedfilteredPersons.size(); i++) {
            ReadOnlyPerson readOnlyPerson = sortedfilteredPersons.get(i);
            if (readOnlyPerson.isSameStateAs(person)) {
                return Index.fromZeroBased(i);
            }
        }
        assert false : "Should not come here in any case";
        return Index.fromZeroBased(-1);
    }

    @Override
    @Subscribe
    public void handleUpdateListForSelectionEvent(UpdateListForSelectionEvent updateListForSelectionEvent) {
        updateFilteredListToShowAll();
        Index index = getIndexOfGivenPerson(updateListForSelectionEvent.getPerson());
        updateListForSelectionEvent.setIndex(index);
    }

    @Override
    @Subscribe
    public void handleUpdatePopularityCounterForSelectionEvent(
            UpdatePopularityCounterForSelectionEvent updatePopularityCounterForSelectionEvent) {
        try {
            updatePersonsPopularityCounterByOne(updatePopularityCounterForSelectionEvent.getPerson());
        } catch (DuplicatePersonException dpe) {
            assert false : "Is not possible as counter will be increased by one";
        } catch (PersonNotFoundException pnfe) {
            logger.info("Only possible when a person is deleted from a list containing single item");
        }

        updatePopularContactList();
    }
    //@@author

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
                && reminderList.equals(other.reminderList);
    }

}
