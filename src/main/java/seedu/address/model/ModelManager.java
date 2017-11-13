package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import seedu.address.commons.events.model.MasterTagListHasAnUnusedTagEvent;
import seedu.address.commons.events.model.PersonAddressDisplayDirectionsEvent;
import seedu.address.commons.events.model.PersonAddressDisplayMapEvent;
import seedu.address.model.person.Address;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.photo.PhotoPath;
import seedu.address.model.photo.exceptions.DuplicatePhotoPathException;
import seedu.address.model.photo.exceptions.PhotoPathNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList.DuplicateTagException;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private static String currentTheme;
    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;

    //@@author April0616
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);

        logger.fine("Updating all photopaths...");
        this.addressBook.updatePhotoPathSavedInMasterList();

        logger.fine("Deleting all unused photos...");
        try {
            this.addressBook.removeAllUnusedPhotosAndPaths();
        } catch (PhotoPathNotFoundException e) {
            assert false : "Some of the photopaths cannot be found!";
        }

        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        currentTheme = userPrefs.getCurrentTheme();
    }
    //@@author

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
        checkMasterTagListHasAllTagsUsed();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    //author@@ nbriannl
    /** Raises an event to indicate a tag in the master list of tags is unused*/
    private void indicateMasterTagListHasAnUnusedTag () {
        raise(new MasterTagListHasAnUnusedTagEvent(addressBook.getUnusedTags()));
    }

    //@@author nbriannl
    @Override
    public void showMapOf(ReadOnlyPerson person, Index index) {
        raise(new PersonAddressDisplayMapEvent(person, index.getZeroBased()));
    }

    //@@author nbriannl
    @Override
    public void showDirectionsTo(ReadOnlyPerson target, Address address, Index index) {
        raise(new PersonAddressDisplayDirectionsEvent(target, address, index.getZeroBased()));
    }

    //@@author April0616
    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
        checkMasterTagListHasAllTagsUsed();
    }

    @Override
    public synchronized void deletePersons(ArrayList<ReadOnlyPerson> targets) throws PersonNotFoundException {
        addressBook.removePersons(targets);
        indicateAddressBookChanged();
        checkMasterTagListHasAllTagsUsed();
    }

    @Override
    public void addPhotoPath(PhotoPath photoPath) throws DuplicatePhotoPathException {
        addressBook.addPhotoPath(photoPath);
        indicateAddressBookChanged();
    }
    //@@author

    //author@@ nbriannl
    @Override
    public void checkMasterTagListHasAllTagsUsed () {
        if (!addressBook.hasAllTagsInUse()) {
            indicateMasterTagListHasAnUnusedTag();
        }
    }

    //@@author nbriannl
    @Override
    public synchronized void deleteTag(Tag tag) throws DuplicatePersonException, PersonNotFoundException,
            TagNotFoundException {
        addressBook.removeTag(tag);
        ObservableList<ReadOnlyPerson> personList = addressBook.getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            ReadOnlyPerson oldPerson = personList.get(i);

            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = new HashSet<Tag>(newPerson.getTags());
            newTags.remove(tag);
            newPerson.setTags(newTags);
            addressBook.updatePerson(oldPerson, newPerson);
        }
        indicateAddressBookChanged();
        checkMasterTagListHasAllTagsUsed();
    }

    //@@author nbriannl
    @Override
    public void editTag(Tag oldTag, Tag newTag) throws DuplicatePersonException, PersonNotFoundException,
            TagNotFoundException {
        addressBook.removeTag(oldTag);
        ObservableList<ReadOnlyPerson> personList = addressBook.getPersonList();
        for (int i = 0; i < personList.size(); i++) {
            ReadOnlyPerson oldPerson = personList.get(i);

            Person newPerson = new Person(oldPerson);
            Set<Tag> newTags = new HashSet<Tag>(newPerson.getTags());
            if (newTags.remove(oldTag)) {
                newTags.add(newTag);
                newPerson.setTags(newTags);
                addressBook.updatePerson(oldPerson, newPerson);
            }
        }
        try {
            addressBook.addTag(newTag);
        } catch (DuplicateTagException dpe) {
            //do nothing. It's perfectly fine if the new tag already exists in the address book. Enabled merge
        }

        indicateAddressBookChanged();
        checkMasterTagListHasAllTagsUsed();
    }

    //@@author
    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
        checkMasterTagListHasAllTagsUsed();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);
        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
        checkMasterTagListHasAllTagsUsed();
    }

    //@@author nbriannl
    @Override
    public HashMap<String, String> getThemeMap () {
        return this.addressBook.getThemeMap();
    }

    @Override
    public void setCurrentTheme(String theme) {
        currentTheme = theme;
    }

    @Override
    public String getCurrentTheme() {
        return currentTheme;
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
