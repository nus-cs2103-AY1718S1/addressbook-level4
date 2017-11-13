package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

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
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.group.Group;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
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
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<Group> filteredGroups;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);
        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);
        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredGroups = new FilteredList<>(this.addressBook.getGroupList());
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
    public synchronized void favoritePerson(ReadOnlyPerson target) throws
            PersonNotFoundException {
        addressBook.favoritePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@author hthjthtrh
    @Override
    public void createGroup(String groupName, List<ReadOnlyPerson> personToGroup)
            throws DuplicateGroupException {
        addressBook.addGroup(groupName, personToGroup);
        indicateAddressBookChanged();
    }

    @Override
    public void propagateToGroup(ReadOnlyPerson personToEdit, Person editedPerson, Class commandClass) {
        requireNonNull(personToEdit);

        addressBook.checkPersonInGroupList(personToEdit, editedPerson, commandClass);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteGroup(Group grpToDelete) {
        requireNonNull(grpToDelete);

        addressBook.removeGroup(grpToDelete);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void setGrpName(Group targetGrp, String newName) throws DuplicateGroupException {
        requireAllNonNull(targetGrp, newName);

        addressBook.setGrpName(targetGrp, newName);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPersonToGroup(Group targetGrp, ReadOnlyPerson targetPerson)
            throws DuplicatePersonException {
        requireAllNonNull(targetGrp, targetPerson);

        addressBook.addPersonToGroup(targetGrp, targetPerson);

        indicateAddressBookChanged();
    }

    @Override
    public synchronized void removePersonFromGroup(Group targetGrp, ReadOnlyPerson targetPerson) {
        requireAllNonNull(targetGrp, targetPerson);

        addressBook.removePersonFromGroup(targetGrp, targetPerson);

        indicateAddressBookChanged();
    }

    @Override
    public ObservableList<Group> getFilteredGroupList() {
        return FXCollections.unmodifiableObservableList(filteredGroups);
    }

    @Override
    public void updateFilteredGroupList(Predicate<Group> predicateShowAllGroups) {
        requireNonNull(predicateShowAllGroups);

        filteredGroups.setPredicate(predicateShowAllGroups);
    }

    /**
     * Finds the index of a group in the group list
     * @param groupName
     * @return
     */
    @Override
    public Index getGroupIndex(String groupName) {
        requireNonNull(groupName);

        return addressBook.getGroupIndex(groupName);
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
    public void sortBy(int attribute) {
        addressBook.sortPersonBy(attribute);
        indicateAddressBookChanged();
    }

    /**
     * Delete a tag from all persons in the addressbook
     * @param tag to be deleted
     * @throws PersonNotFoundException
     * @throws DuplicatePersonException
     */
    public void deleteTag(Tag tag)
            throws PersonNotFoundException, DuplicatePersonException {
        boolean isAddressBookChanged = false;
        for (int i = 0; i < addressBook.getPersonList().size(); i++) {
            ReadOnlyPerson originalPerson = addressBook.getPersonList().get(i);
            Set<Tag> tagList = originalPerson.getTags();
            tagList.remove(tag);
            Person newPerson = new Person(originalPerson.getName(), originalPerson.getPhone(),
                    originalPerson.getEmail(), originalPerson.getAddress(), originalPerson.getBirthday(),
                    originalPerson.getRemark(), originalPerson.getMajor(), originalPerson.getFacebook(), tagList);
            if (!newPerson.equals(originalPerson)) {
                addressBook.updatePerson(originalPerson, newPerson);
                isAddressBookChanged = true;
            }
        }
        if (isAddressBookChanged) {
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

        if (this.filteredGroups.size() != other.filteredGroups.size()) {
            return false;
        }
        for (int i = 0; i < this.filteredGroups.size(); i++) {
            if (!this.filteredGroups.get(i).equals(other.filteredGroups.get(i))) {
                return false;
            }
        }

        if (this.filteredPersons.size() != other.filteredPersons.size()) {
            return false;
        }
        for (int i = 0; i < this.filteredPersons.size(); i++) {
            if (!this.filteredPersons.get(i).isSameStateAs(other.filteredPersons.get(i))) {
                return false;
            }
        }

        return addressBook.equals(other.addressBook);
    }

}
