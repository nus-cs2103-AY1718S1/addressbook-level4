package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
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
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.events.ui.GroupPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.NewGroupListEvent;
import seedu.address.commons.events.ui.NewPersonInfoEvent;
import seedu.address.commons.events.ui.NewPersonListEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.SetColourCommand;
import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.group.ReadOnlyGroup;
import seedu.address.model.group.exceptions.DuplicateGroupException;
import seedu.address.model.group.exceptions.GroupNotFoundException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyAddressBookException;
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
    private final FilteredList<ReadOnlyGroup> filteredGroups;
    private HashMap<Tag, String> tagColours = new HashMap<>();
    //@@author LimeFallacie
    private UserPrefs colourPrefs;

    //@@author LimeFallacie
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
        colourPrefs = userPrefs;
        HashMap<String, String> stringColourMap = userPrefs.getColourMap();
        if (stringColourMap != null) {
            try {
                for (HashMap.Entry<String, String> entry : stringColourMap.entrySet()) {
                    tagColours.put(new Tag(entry.getKey()), entry.getValue());
                }

            } catch (IllegalValueException ive) {
                //it shouldn't ever reach here
            }
        }
        updateAllPersons(tagColours);
    }

    //@@author
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

    //@@author eldonng
    @Override
    public synchronized void deletePerson(ReadOnlyPerson... target) throws PersonNotFoundException {
        for (ReadOnlyPerson person : target) {
            addressBook.removePerson(person);
        }

        raise(new NewGroupListEvent(getGroupList(), addressBook.getPersonList()));

        indicateAddressBookChanged();
    }

    //@@author
    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        raise(new NewPersonInfoEvent(person));
        indicateAddressBookChanged();
    }

    //@@author eldonng
    @Override
    public synchronized void addGroup(ReadOnlyGroup group) throws DuplicateGroupException {
        addressBook.addGroup(group);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void deleteGroup(ReadOnlyGroup group) throws GroupNotFoundException {
        addressBook.deleteGroup(group);
        indicateAddressBookChanged();
    }

    //@@author
    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        addressBook.editPersonInGroup(target, editedPerson);
        raise(new NewPersonInfoEvent(editedPerson));
        indicateAddressBookChanged();
    }

    //@@author eldonng
    @Override
    public void pinPerson(ReadOnlyPerson person) throws CommandException, PersonNotFoundException,
            EmptyAddressBookException {
        try {
            person.setPin();
            sort(SortCommand.ARGUMENT_NAME);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(AddCommand.MESSAGE_DUPLICATE_PERSON);
        }
    }

    @Override
    public void unpinPerson(ReadOnlyPerson person) throws CommandException, PersonNotFoundException,
            EmptyAddressBookException {
        try {
            person.setUnpin();
            sort(SortCommand.ARGUMENT_NAME);
        } catch (DuplicatePersonException dpe) {
            throw new CommandException(AddCommand.MESSAGE_DUPLICATE_PERSON);
        }
    }

    @Override
    public void setTagColour(String tagName, String colour) throws IllegalValueException {
        List<Tag> tagList = addressBook.getTagList();
        for (Tag tag: tagList) {
            if (tagName.equals(tag.tagName)) {
                tagColours.put(tag, colour);
                updateAllPersons(tagColours);
                indicateAddressBookChanged();
                raise(new NewPersonListEvent(getFilteredPersonList()));
                colourPrefs.updateColorMap(tagColours);
                return;
            }
        }
        throw new IllegalValueException(SetColourCommand.SETCOLOUR_INVALID_TAG);
    }

    @Override
    public HashMap<Tag, String> getTagColours() {
        return tagColours;
    }

    //@@author LimeFallacie
    private void updateAllPersons(HashMap<Tag, String> allTagColours) {
        colourPrefs.updateColorMap(allTagColours);
    }

    //@@author eldonng
    public Predicate<ReadOnlyPerson> getPredicateForTags(String arg) throws IllegalValueException {
        try {
            Tag targetTag = new Tag(arg);
            Predicate<ReadOnlyPerson> taggedPredicate = p -> p.getTags().contains(targetTag);
            return taggedPredicate;
        }  catch (IllegalValueException ive) {
            throw new IllegalValueException(Tag.MESSAGE_TAG_CONSTRAINTS);
        }
    }
    //=========== Filtered Person List Accessors =============================================================

    //@@author
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    //@@author eldonng
    @Override
    public ObservableList<ReadOnlyGroup> getGroupList() {
        return FXCollections.unmodifiableObservableList(filteredGroups);
    }

    //@@author
    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    //@@author eldonng
    @Override
    public void updateFilteredGroupList(Predicate<ReadOnlyGroup> predicate) {
        requireNonNull(predicate);
        filteredGroups.setPredicate(predicate);
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

    //@@author LimeFallacie
    @Override
    public void sort(String sortType) throws DuplicatePersonException, EmptyAddressBookException {
        switch (sortType) {
        case SortCommand.ARGUMENT_NAME:
            addressBook.setPersons(sortBy(COMPARATOR_SORT_BY_NAME));
            break;

        case SortCommand.ARGUMENT_PHONE:
            addressBook.setPersons(sortBy(COMPARATOR_SORT_BY_PHONE));
            break;

        case SortCommand.ARGUMENT_EMAIL:
            addressBook.setPersons(sortBy(COMPARATOR_SORT_BY_EMAIL));
            break;

        default:
            break;

        }
        indicateAddressBookChanged();
    }

    /**
     * Sort the addressbook by the comparator given
     * @return ArrayList<ReadOnlyPerson> sorted list</ReadOnlyPerson>
     */
    private ArrayList<ReadOnlyPerson> sortBy(Comparator<ReadOnlyPerson> comparator) throws EmptyAddressBookException {
        ArrayList<ReadOnlyPerson> newList = new ArrayList<>();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        SortedList<ReadOnlyPerson> sortedList =
                getFilteredPersonList().filtered(PREDICATE_SHOW_PINNED_PERSONS).sorted(comparator);
        newList.addAll(sortedList);
        sortedList = getFilteredPersonList().filtered(PREDICATE_SHOW_UNPINNED_PERSONS).sorted(comparator);
        newList.addAll(sortedList);

        if (newList.isEmpty()) {
            throw new EmptyAddressBookException();
        }

        return newList;
    }

    //@@author eldonng
    @Subscribe
    private void handleGroupPanelSelectionChangedEvent(GroupPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        updateFilteredPersonList(person -> event.getNewSelection().group.getGroupMembers().contains(person));
        raise(new NewPersonListEvent(getFilteredPersonList()));
    }
}
