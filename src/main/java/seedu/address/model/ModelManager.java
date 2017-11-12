package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.model.NewPersonInfoEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.meeting.MeetingContainPersonPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.TagNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<Meeting> filteredMeeting;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        Set<Meeting> meetingSet = new HashSet<Meeting>();
        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        for (ReadOnlyPerson person : this.addressBook.getPersonList()) {
            for (Meeting meeting : person.getMeetings()) {
                meeting.setPerson(person);
                meetingSet.add(meeting);
            }
        }
        this.addressBook.setMeetings(meetingSet);
        filteredMeeting = new FilteredList<>(this.addressBook.getMeetingList());
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
        updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        indicateAddressBookChanged();
        indicateNewPersonInfoAvailable(person);
    }

    //@@author newalter
    /**
     * raise a new NewPersonInfoEvent whenever a person is added or edited
     * @param person the person added or edited
     */
    private void indicateNewPersonInfoAvailable(ReadOnlyPerson person) {
        raise(new NewPersonInfoEvent(person));
    }
    //@@author

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        addressBook.updateMeetings(target, editedPerson);
        indicateAddressBookChanged();
        indicateNewPersonInfoAvailable(editedPerson);
    }

    //@@author alexanderleegs
    @Override
    public void deleteTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException, TagNotFoundException {
        ObservableList<ReadOnlyPerson> personList = addressBook.getPersonList();
        boolean tagFound = false;

        for (int i = 0; i < personList.size(); i++) {
            ReadOnlyPerson oldPerson = personList.get(i);
            Set<Tag> oldTags = new HashSet<Tag>(oldPerson.getTags());
            if (oldTags.contains(tag)) {
                Person newPerson = new Person(oldPerson);
                oldTags.remove(tag);
                newPerson.setTags(oldTags);
                addressBook.updatePerson(oldPerson, newPerson);
                tagFound = true;
            }
        }

        if (!tagFound) {
            throw new TagNotFoundException();
        }
        indicateAddressBookChanged();
    }

    @Override
    public void deleteMeeting(Meeting meeting) {
        addressBook.deleteMeeting(meeting);
        indicateAddressBookChanged();
    }

    @Override
    public void sort(String field) {
        addressBook.sort(field);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void sortMeeting() {
        addressBook.sortMeeting();
        Predicate<Meeting> currPredicate = (Predicate<Meeting>) filteredMeeting.getPredicate();
        if (currPredicate == null) {
            updateFilteredMeetingList(PREDICATE_SHOW_ALL_MEETINGS);
        } else {
            updateFilteredMeetingList(currPredicate);
        }
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
    public Predicate<? super ReadOnlyPerson> getPersonListPredicate() {
        return filteredPersons.getPredicate();
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //=========== Filtered Meeting List Accessors =============================================================

    /**
     * Returns an unmodifiable view of the list of {@code Meeting} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<Meeting> getFilteredMeetingList() {
        sortMeeting();
        return FXCollections.unmodifiableObservableList(filteredMeeting);
    }

    @Override
    public Predicate<? super Meeting> getMeetingListPredicate() {
        return filteredMeeting.getPredicate();
    }

    @Override
    public void updateFilteredMeetingList(Predicate<Meeting> predicate) {
        requireNonNull(predicate);
        filteredMeeting.setPredicate(predicate);
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

    //@@author LimYangSheng
    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        List<ReadOnlyPerson> personSelected = new ArrayList<>();
        personSelected.add(event.getNewSelection().person);
        updateFilteredMeetingList(new MeetingContainPersonPredicate(personSelected));
    }
    //@@author

}
