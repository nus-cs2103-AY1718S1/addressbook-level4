package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.awt.Desktop;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.ClearBrowserPanelEvent;
import seedu.address.commons.events.ui.FaceBookEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.FileImage;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.reminder.exceptions.ReminderNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColor;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */

public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<ReadOnlyReminder> filteredReminders;



    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredReminders = new FilteredList<>(this.addressBook.getReminderList());

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
    public synchronized void clearBrowserPanel() {
        raise(new ClearBrowserPanelEvent());
    }
    @Override
    public synchronized void sendMailToContacts(String tag, String subject, List<ReadOnlyPerson> lastShownList) {

        String appendEmailAddress = "";

        try {
            appendEmailAddress = getAppendedEmailIdOfContacts(tag, lastShownList, appendEmailAddress);

        } catch (IllegalValueException ive) {
            throw new AssertionError("invalid input");
        }
        openUpDesktopBrowser(appendEmailAddress, subject);
    }
    private String getAppendedEmailIdOfContacts(String tag, List<ReadOnlyPerson> lastShownList,
                                                String appendEmailAddress) throws IllegalValueException {

        ReadOnlyPerson getPerson;
        int loopVariable = 0;

        while (loopVariable < lastShownList.size()) {
            getPerson = lastShownList.get(loopVariable);

            if (getPerson.getTags().contains(new Tag(tag))) {
                appendEmailAddress = appendEmailAddress + getPerson.getEmail().toString() + "+";
            }
            loopVariable++;

        }
        return appendEmailAddress;
    }
    /** Opens the default browser in your desktop */
    private void openUpDesktopBrowser(String appendEmailAddress, String subject) {

        appendEmailAddress = appendEmailAddress.substring(0, appendEmailAddress.length() - 1);

        String Gmail_Url = "https://mail.google.com/mail/?view=cm&fs=1&to=" + appendEmailAddress + "&su=" + subject;


        try {

            if (Desktop.isDesktopSupported())
            {
                Desktop.getDesktop().browse(new URI(Gmail_Url));
            }
        } catch (URISyntaxException U) {
            throw new AssertionError("URISyntax error");

        } catch (IOException Ie) {
            throw new AssertionError("IOE error");

        }
    }
    @Override
    public synchronized void faceBook(ReadOnlyPerson person) throws PersonNotFoundException {

        raise(new FaceBookEvent(person));
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {

        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();

    }
    @Override
    public synchronized void addReminder(ReadOnlyReminder target) throws DuplicateReminderException {

        addressBook.addReminder(target);
        updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        indicateAddressBookChanged();

    }
    @Override
    public synchronized void deleteReminder(ReadOnlyReminder target) throws ReminderNotFoundException {

        addressBook.removeReminder(target);
        indicateAddressBookChanged();

    }

    @Override
    public synchronized void addPhotoPerson(ReadOnlyPerson person, String filePath, Index targetIndex)
            throws PersonNotFoundException,
            FileNotFoundException, IOException {

        try {
            person.imageProperty().setValue(new FileImage(filePath));
            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            indicateAddressBookChanged();
        } catch (IllegalValueException ive) {
            throw new AssertionError("Invalid input");
        }
    }
    //@@author ChenXiaoman
    @Override
    public synchronized void updateTagColorPair(Set<Tag> tagList, TagColor color) throws IllegalValueException {
        addressBook.updateTagColorPair(tagList, color);
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
    @Override
    public void updateReminder(ReadOnlyReminder target, ReadOnlyReminder changedReminder)
            throws DuplicateReminderException, ReminderNotFoundException {
        requireAllNonNull(target, changedReminder);

        addressBook.updateReminder(target, changedReminder);
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
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyReminder} backed by the internal list of
     * {@code weaver}
     */
    @Override
    public ObservableList<ReadOnlyReminder> getFilteredReminderList() {
        return FXCollections.unmodifiableObservableList(filteredReminders);
    }
    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
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
}
