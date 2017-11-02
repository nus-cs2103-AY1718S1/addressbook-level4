package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.commands.EditCommand.MESSAGE_DUPLICATE_PERSON;

import java.util.HashSet;
import java.util.Set;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SearchData;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UniqueMeetingList meetingList;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final UserPrefs userPrefs;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, ReadOnlyMeetingList meetingList,
                        UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, meetingList, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + ", meeting list: " + meetingList
                + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.meetingList = new UniqueMeetingList(meetingList);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        this.userPrefs = userPrefs;
    }

    public ModelManager() {
        this(new AddressBook(), new UniqueMeetingList(), new UserPrefs());
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

    @Override
    public UniqueMeetingList getMeetingList() {
        return meetingList;
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
    public boolean deleteTag(Tag [] tags) throws PersonNotFoundException, DuplicatePersonException {
        boolean isTagRemoved;
        boolean hasOneOrMoreDeletion = false;
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {

            ReadOnlyPerson oldPerson = addressBook.getPersonList().get(i);
            //creates a new person without each of the tags
            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = new HashSet<>(newPerson.getTags());

            for (Tag tag : tags) {
                isTagRemoved = newTags.remove(tag);
                if (isTagRemoved) {
                    hasOneOrMoreDeletion = isTagRemoved;
                }
            }
            newPerson.setTags(newTags);

            addressBook.updatePerson(oldPerson, newPerson);
        }
        return hasOneOrMoreDeletion;
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
    public void updateFilteredPersonList() {
        updateFilteredPersonList();
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public UserPrefs getUserPrefs() {
        return this.userPrefs;
    }

    @Override
    public void recordSearchHistory() throws CommandException {

        int searchResultsCount = filteredPersons.size();

        for (int i = 0; i < searchResultsCount; i++) {
            ReadOnlyPerson searchedPerson = filteredPersons.get(i);
            SearchData updatedSearchData = searchedPerson.getSearchData();
            updatedSearchData.incrementSearchCount();
            Person modifiedPerson = new Person(searchedPerson.getInternalId(), searchedPerson.getName(),
                    searchedPerson.getPhone(), searchedPerson.getEmail(), searchedPerson.getAddress(),
                    searchedPerson.getTags(), updatedSearchData);
            try {
                updatePerson(searchedPerson, modifiedPerson);
            } catch (DuplicatePersonException dpe) {
                throw new CommandException(MESSAGE_DUPLICATE_PERSON);
            } catch (PersonNotFoundException pnfe) {
                throw new AssertionError("The target person cannot be missing");
            }
        }
    }

    //=========== Sort addressBook methods =============================================================

    /***
     * Sorts persons in address book by searchCount
     * @author Sri-vatsa
     */
    @Override
    public void sortPersonListBySearchCount() {
        addressBook.sortBySearchCount();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    /***
     * Sorts persons in Address book alphabetically
     */
    @Override
    public void sortPersonListLexicographically() {
        addressBook.sortLexicographically();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }


    //=========== Util methods =============================================================

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
