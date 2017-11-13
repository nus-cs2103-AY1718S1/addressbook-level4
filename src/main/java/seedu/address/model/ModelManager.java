package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.SortCommandParser.DATA_FIELD_ADDRESS;
import static seedu.address.logic.parser.SortCommandParser.DATA_FIELD_EMAIL;
import static seedu.address.logic.parser.SortCommandParser.DATA_FIELD_NAME;
import static seedu.address.logic.parser.SortCommandParser.DATA_FIELD_PHONE;

import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.ShowPersonListViewEvent;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.util.ComparatorUtil;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final SortedList<ReadOnlyPerson> sortedFilteredPersons;
    private final SortedList<Tag> tags;

    //@@author Houjisan
    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.tags = new SortedList<Tag>(this.addressBook.getTagList());
        tags.setComparator(ComparatorUtil.getTagComparator());
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        sortedFilteredPersons = new SortedList<>(filteredPersons);
        // Sort contacts by favourite status, then name, then phone, then email, then address
        sortedFilteredPersons.setComparator(ComparatorUtil.getFavouriteComparator()
                .thenComparing(ComparatorUtil.getAllComparatorsNameFirst()));
    }

    //@@author
    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        indicateChangedToPersonListView();
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

    //@@author Houjisan
    /**
     * Raises an event to indicate that the UI needs to switch to viewing the Person List
     */
    private void indicateChangedToPersonListView() {
        raise(new ShowPersonListViewEvent());
    }

    //@@author
    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        indicateChangedToPersonListView();
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        indicateChangedToPersonListView();
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        indicateChangedToPersonListView();
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
        return FXCollections.unmodifiableObservableList(sortedFilteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //@@author Houjisan
    @Override
    public ObservableList<Tag> getTagList() {
        return FXCollections.unmodifiableObservableList(tags);
    }

    @Override
    public void sortByDataFieldFirst(String dataField, boolean isFavIgnored, boolean isReverseOrder) {
        indicateChangedToPersonListView();
        Comparator<ReadOnlyPerson> comparatorOrder = new Comparator<ReadOnlyPerson>() {
            @Override
            public int compare(ReadOnlyPerson o1, ReadOnlyPerson o2) {
                return 0;
            }
        };
        switch (dataField) {
        case DATA_FIELD_NAME:
            comparatorOrder = isReverseOrder ? ComparatorUtil.getAllComparatorsNameFirstReversed()
                    : ComparatorUtil.getAllComparatorsNameFirst();
            break;
        case DATA_FIELD_PHONE:
            comparatorOrder = isReverseOrder ? ComparatorUtil.getAllComparatorsPhoneFirstReversed()
                    : ComparatorUtil.getAllComparatorsPhoneFirst();
            break;
        case DATA_FIELD_EMAIL:
            comparatorOrder = isReverseOrder ? ComparatorUtil.getAllComparatorsEmailFirstReversed()
                    : ComparatorUtil.getAllComparatorsEmailFirst();
            break;
        case DATA_FIELD_ADDRESS:
            comparatorOrder = isReverseOrder ? ComparatorUtil.getAllComparatorsAddressFirstReversed()
                    : ComparatorUtil.getAllComparatorsAddressFirst();
            break;
        default:
            break;
        }

        if (isFavIgnored) {
            sortedFilteredPersons.setComparator(comparatorOrder
                    .thenComparing(ComparatorUtil.getFavouriteComparator()));
        } else {
            sortedFilteredPersons.setComparator(ComparatorUtil.getFavouriteComparator()
                    .thenComparing(comparatorOrder));
        }
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
                && filteredPersons.equals(other.filteredPersons);
    }
    //@@author NabeelZaheer
    @Override
    public Set<Tag> removeTag(Set<Tag> tag, List<String> index) throws DuplicatePersonException,
            PersonNotFoundException {
        Set<Tag> removedTags;
        Set<Tag> displayTags = new HashSet<>();
        int totalSize = getFilteredPersonList().size();
        boolean tagExist = false;
        int decrease = 0;
        if (!index.isEmpty()) {
            boolean removed = false;
            for (int i = 0; i < index.size(); i++) {
                int currentSize = getFilteredPersonList().size();
                int indexToRemove = Integer.parseInt(index.get(i)) - 1;
                Person toDelete;
                /**
                 * Checks if tags have been removed before
                 * causing filtered list to change
                 */
                if (totalSize != currentSize) {
                    if (removed) {
                        indexToRemove -= decrease;
                    }
                    toDelete = new Person(getFilteredPersonList().get(indexToRemove));
                } else {
                    toDelete = new Person(getFilteredPersonList().get(indexToRemove));
                }
                Person toUpdate = new Person(toDelete);
                Set<Tag> oldTags = toDelete.getTags();
                Set<Tag> newTags = deleteTagUpdate(tag, oldTags);
                if (!(newTags.size() == oldTags.size())) {
                    toUpdate.setTags(newTags);
                    tagExist = true;
                    removed = true;
                    decrease++;
                    updatePerson(toDelete, toUpdate);
                    removedTags = getTagsChanged(oldTags, newTags);
                    displayTags = updateDisplayTags(displayTags, removedTags);
                }
            }
        } else {
            for (int i = 0; i < totalSize; i++) {
                int newSize = getFilteredPersonList().size();
                Person toDelete;
                if (newSize == totalSize) {
                    toDelete = new Person(getFilteredPersonList().get(i));
                } else {
                    toDelete = new Person(getFilteredPersonList().get(0));
                }
                Person toUpdate = new Person(toDelete);
                Set<Tag> oldTags = toDelete.getTags();
                Set<Tag> newTags = deleteTagUpdate(tag, oldTags);
                if (!(newTags.size() == oldTags.size())) {
                    toUpdate.setTags(newTags);
                    tagExist = true;
                    updatePerson(toDelete, toUpdate);
                    removedTags = getTagsChanged(oldTags, newTags);
                    displayTags = updateDisplayTags(displayTags, removedTags);
                }
            }
        }

        if (!tagExist) {
            throw new PersonNotFoundException();
        }
        return displayTags;
    }

    @Override
    public Set<Tag> addTag(Set<Tag> tag, Set<Index> index) throws PersonNotFoundException,
            DuplicatePersonException {
        Set<Tag> addedTags;
        Set<Tag> displayTags = new HashSet<>();
        Iterator<Index> indexIt = index.iterator();
        boolean added = false;

        while (indexIt.hasNext()) {
            int indexToAdd = indexIt.next().getZeroBased();
            Person toCheck = new Person(getFilteredPersonList().get(indexToAdd));
            Person toUpdate = new Person(toCheck);
            Set<Tag> current = toCheck.getTags();
            Set<Tag> updated = newTag(tag, current);
            if (!(current.size() == updated.size())) {
                toUpdate.setTags(updated);
                added = true;
                updatePerson(toCheck, toUpdate);
                addedTags = getTagsChanged(updated, current);
                displayTags = updateDisplayTags(displayTags, addedTags);
            }
        }

        if (!added) {
            throw new PersonNotFoundException();
        }
        return displayTags;
    }

    /**
     *
     * @param currentDisplay
     * @param changedTags
     * @return updated set of removed tags
     */
    private Set<Tag> updateDisplayTags(Set<Tag> currentDisplay, Set<Tag> changedTags) {
        Iterator<Tag> it = changedTags.iterator();
        while (it.hasNext()) {
            Tag checkTag = it.next();
            if (!currentDisplay.contains(checkTag)) {
                currentDisplay.add(checkTag);
            }
        }
        return currentDisplay;
    }

    /**
     *
     * @param compareFrom set with tags to compare individually
     * @param compareWith set to check if tags contain within
     * @return set of tags that has been added/removed
     */
    private Set<Tag> getTagsChanged(Set<Tag> compareFrom, Set<Tag> compareWith) {
        Set<Tag> changedSet = new HashSet<>();

        Iterator<Tag> it = compareFrom.iterator();
        while (it.hasNext()) {
            Tag checkTag = it.next();
            if (!compareWith.contains(checkTag)) {
                changedSet.add(checkTag);
            }
        }
        return changedSet;
    }

    /**
     *
     * @param tag set of tags input by user
     * @param oldTags set of current tags
     * @return Set of Tags of new Person to be updated
     */
    private Set<Tag> deleteTagUpdate(Set<Tag> tag, Set<Tag> oldTags) {
        Set<Tag> newTags = new HashSet<>();

        Iterator<Tag> it = oldTags.iterator();
        while (it.hasNext()) {
            Tag checkTag = it.next();
            String current = checkTag.tagName;
            boolean toAdd = true;
            Iterator<Tag> it2 = tag.iterator();
            while (it2.hasNext()) {
                String toCheck = it2.next().tagName;
                if (current.equals(toCheck)) {
                    toAdd = false;
                }
            }
            if (toAdd) {
                newTags.add(checkTag);
            }
        }
        return newTags;
    }


    /**
     *
     * @param tag set of tags input by user
     * @param current set of current tags
     * @return Set of tags to be updated
     */
    private Set<Tag> newTag(Set<Tag> tag, Set<Tag> current) {
        Set<Tag> updated = new HashSet<>();
        Iterator<Tag> it = current.iterator();
        boolean exist = false;
        for (Tag t : tag) {
            Tag toAdd = t;
            while (it.hasNext()) {
                Tag toCheck = it.next();
                if (current.equals(t)) {
                    exist = true;
                }
                updated.add(toCheck);
            }
            if (!exist) {
                updated.add(toAdd);
                exist = false;
            }
        }
        return updated;
    }
    //@@author


}
