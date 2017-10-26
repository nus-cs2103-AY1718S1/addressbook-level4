package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;
import java.util.regex.PatternSyntaxException;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;

import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.TagColorChangedEvent;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.property.PropertyManager;
import seedu.address.model.property.exceptions.DuplicatePropertyException;
import seedu.address.model.reminder.ReadOnlyReminder;
import seedu.address.model.reminder.exceptions.DuplicateReminderException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.TagColorManager;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<ReadOnlyEvent> filteredEvents;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredEvents = new FilteredList<>(this.addressBook.getEventList());
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

    //=========== Model support for property component =============================================================

    /**
     * Adds a new customize property to {@code PropertyManager}.
     *
     * @throws DuplicatePropertyException if there already exists a property with the same {@code shortName}.
     * @throws PatternSyntaxException     if the given regular expression contains invalid syntax.
     */
    @Override
    public void addProperty(String shortName, String fullName, String message, String regex)
            throws DuplicatePropertyException, PatternSyntaxException {
        PropertyManager.addNewProperty(shortName, fullName, message, regex);
        indicateAddressBookChanged();
    }

    //=========== Model support for contact component =============================================================

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        addressBook.sortPersonList();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    /**
     * Replaces the given person {@code target} with {@code editedPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
     */
    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        addressBook.sortPersonList();
        indicateAddressBookChanged();
    }
    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    //=========== Model support for tag component =============================================================

    /**
     * Removes the specific tag. As a result, all persons who obtain that tag before will lose that tag.
     * TODO: Further investigate potential problems with this method.
     *
     * @param tag is the tag that will be removed.
     */
    @Override
    public void removeTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException {
        // Checks whether each person has that specific tag.
        for (ReadOnlyPerson target : addressBook.getPersonList()) {
            Person person = new Person(target);
            Set<Tag> updatedTags = new HashSet<>(person.getTags());
            updatedTags.remove(tag);
            person.setTags(updatedTags);
            addressBook.updatePerson(target, person);
        }

        // Removes the tag from the master tag list in the overall address book.
        Set<Tag> newTags = new HashSet<>(addressBook.getTagList());
        newTags.remove(tag);
        addressBook.setTags(newTags);
    }

    public boolean hasTag(Tag tag) {
        return addressBook.getTagList().contains(tag);
    }

    /**
     * Changes the displayed color of an existing tag (through {@link TagColorManager}).
     */
    public void setTagColor(Tag tag, String color) {
        TagColorManager.setColor(tag, color);
        indicateAddressBookChanged();
        raise(new TagColorChangedEvent(tag, color));
    }

    //=========== Model support for activity component =============================================================

    @Override
    public synchronized void addEvent(ReadOnlyEvent event) throws DuplicateEventException {
        requireNonNull(event);
        addressBook.addEvent(event);
        addressBook.sortEventList();
        updateFilteredEventsList(PREDICATE_SHOW_ALL_EVENTS);
        indicateAddressBookChanged();
    }

    @Override
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireAllNonNull(target, editedEvent);
        addressBook.updateEvent(target, editedEvent);
        addressBook.sortEventList();
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteEvent(ReadOnlyEvent event) throws EventNotFoundException {
        addressBook.removeEvent(event);
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
     * Updates the filter of the filtered person list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Activity List Accessors =============================================================

    @Override
    public ObservableList<ReadOnlyEvent> getFilteredEventList() {
        return FXCollections.unmodifiableObservableList(filteredEvents);
    }

    /**
     * Updates the filter of the filtered event list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    @Override
    public void updateFilteredEventsList(Predicate<ReadOnlyEvent> predicate) {
        requireNonNull(predicate);
        filteredEvents.setPredicate(predicate);
    }
    //=========== Filtered Activity List Accessors =============================================================

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
                && filteredEvents.equals(other.filteredEvents)
                && filteredReminders.equals(other.filteredReminders);
    }
}
