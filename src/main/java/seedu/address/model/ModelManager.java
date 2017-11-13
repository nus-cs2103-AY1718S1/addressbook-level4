package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
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
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.NameContainsFavouritePredicate;
import seedu.address.model.person.NameContainsFavouritePrivacyLevelPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPrivacyLevelPredicate;
import seedu.address.model.person.NameContainsTagsPredicate;
import seedu.address.model.person.NameContainsTagsPrivacyLevelPredicate;
import seedu.address.model.person.Person;
import seedu.address.model.person.PersonCompleteMatchPredicate;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.ShowAllPrivacyLevelPredicate;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.task.Assignees;
import seedu.address.model.task.Deadline;
import seedu.address.model.task.Description;
import seedu.address.model.task.Priority;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.TaskAddress;
import seedu.address.model.task.TaskName;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final FilteredList<ReadOnlyPerson> filteredPersons;
    private final FilteredList<ReadOnlyTask> filteredTasks;
    private final UserPrefs userPrefs;
    private int privacyLevel;
    private String theme;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);
        this.userPrefs = userPrefs;

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
        filteredTasks = new FilteredList<>(this.addressBook.getTasksList());
        this.theme = userPrefs.getTheme();
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    //@@author Esilocke
    @Override
    public void resetPartialData(ReadOnlyAddressBook newData, Prefix type) {
        assert(type.equals(PREFIX_TASK) || type.equals(PREFIX_PERSON));
        if (type.equals(PREFIX_TASK)) {
            addressBook.resetPartialData(newData, PREFIX_TASK);
            indicateAddressBookChanged();
        } else {
            addressBook.resetPartialData(newData, PREFIX_PERSON);
            indicateAddressBookChanged();
        }
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

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        ArrayList<ReadOnlyPerson> persons = new ArrayList<>();
        persons.add(target);
        ArrayList<Index> personIndexes = addressBook.extractPersonIndexes(persons);
        Index personIndex = personIndexes.get(0);
        addressBook.removePerson(target);
        addressBook.removePersonFromAssignees(personIndex);
        indicateAddressBookChanged();
    }
    //@@author wangyiming1019
    /**
     * Delete input tag from the specific persons shown in the last list.
     */
    @Override
    public synchronized void deleteTag(Tag toDelete, ArrayList<Index> personIndexes) throws PersonNotFoundException,
            DuplicatePersonException {
        for (int i = 0; i < personIndexes.size(); i++) {
            int index = personIndexes.get(i).getZeroBased();
            ReadOnlyPerson personWithTag = this.getFilteredPersonList().get(index);
            Person personWithoutTag = new Person(personWithTag);
            Set<Tag> newTags = new HashSet<Tag>(personWithoutTag.getTags());
            newTags.remove(toDelete);
            personWithoutTag.setTags(newTags);
            addressBook.updatePerson(personWithTag, personWithoutTag);
            indicateAddressBookChanged();
        }
    }
    //@@author
    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }
    //@@author wangyiming1019
    /**
     * Adds input tag to the specific persons shown in the last list.
     */
    @Override
    public synchronized void addTag(Tag toAdd, ArrayList<Index> personIndexes) throws PersonNotFoundException,
            DuplicatePersonException {
        for (int i = 0; i < personIndexes.size(); i++) {
            int index = personIndexes.get(i).getZeroBased();
            ReadOnlyPerson personWithoutTag = this.getFilteredPersonList().get(index);
            Person personWithTag = new Person(personWithoutTag);
            Set<Tag> newTags = new HashSet<Tag>(personWithTag.getTags());
            newTags.add(toAdd);
            personWithTag.setTags(newTags);
            addressBook.updatePerson(personWithoutTag, personWithTag);
            indicateAddressBookChanged();
        }
    }
    //@@author
    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    //@@author charlesgoh
    @Override
    public void sortPersons(String field, String order) {
        addressBook.sortPersonsBy(field, order);
        Index[] updatedIndexes = addressBook.getMappings();
        System.out.println(updatedIndexes.length);
        addressBook.updateTaskAssigneeMappings(updatedIndexes);
        indicateAddressBookChanged();
    }

    @Override
    public void sortTasks(String field, String order) {
        addressBook.sortTasksBy(field, order);
        indicateAddressBookChanged();
    }

    public UserPrefs getUserPrefs() {
        return userPrefs;
    }

    public boolean getLockState() {
        return getUserPrefs().getAddressBookLockState();
    }

    public void lockAddressBookFromModel() {
        getUserPrefs().lockAddressBook();
    }

    public void unlockAddressBookFromModel() {
        getUserPrefs().unlockAddressBook();
    }
    //@@author

    //@@author Esilocke
    /**
     * Replaces the toChange Tag with the newTag Tag, for all Person objects denoted by the indexes.
     * Guarantees: indexes contains at least 1 person that has the toChange Tag.
     */
    public synchronized void editTag(Tag toChange, Tag newTag, ArrayList<Index> indexes)
            throws PersonNotFoundException, DuplicatePersonException {
        List<ReadOnlyPerson> allPersons = this.getFilteredPersonList();
        Set<Tag> personTags;
        Person toUpdate;
        ReadOnlyPerson toRead;
        int index;
        for (Index i : indexes) {
            index = i.getZeroBased();
            toRead = allPersons.get(index);
            toUpdate = new Person(toRead);
            personTags = new HashSet<>(toRead.getTags());
            personTags.remove(toChange);
            personTags.add(newTag);
            toUpdate.setTags(personTags);
            addressBook.updatePerson(toRead, toUpdate);
        }
        indicateAddressBookChanged();
    }
    //@@author
    //@@author wangyiming1019
    @Override
    public void favouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.favouritePerson(target);
        indicateAddressBookChanged();
    }
    //@@author
    //@@author wangyiming1019
    @Override
    public void unfavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.unfavouritePerson(target);
        indicateAddressBookChanged();
    }
    //@@author
    //@@author Esilocke
    @Override
    public synchronized void addTask(ReadOnlyTask toAdd) throws DuplicateTaskException {
        addressBook.addTask(toAdd);
        updateFilteredTaskList(PREDICATE_SHOW_ALL_TASKS);
        indicateAddressBookChanged();
    }

    //@@author Esilocke
    @Override
    public synchronized void deleteTask(ReadOnlyTask toDelete) throws TaskNotFoundException {
        addressBook.removeTask(toDelete);
        indicateAddressBookChanged();
    }

    //@@author Esilocke
    @Override
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireAllNonNull(target, editedTask);

        addressBook.updateTask(target, editedTask);
        indicateAddressBookChanged();
    }

    //@@author Esilocke
    @Override
    public void assignToTask(ArrayList<ReadOnlyPerson> personsToAssign, ReadOnlyTask taskToAssignTo)
            throws TaskNotFoundException, DuplicateTaskException {
        Assignees assignees = taskToAssignTo.getAssignees();
        Assignees newAssignees = new Assignees(assignees);
        ArrayList<Index> positions = addressBook.extractPersonIndexes(personsToAssign);

        boolean atLeastOneAdded = newAssignees.assign(positions);
        if (!atLeastOneAdded) {
            throw new DuplicateTaskException();
        }
        ReadOnlyTask updatedTask = constructTaskWithNewAssignee(taskToAssignTo, newAssignees);
        addressBook.updateTask(taskToAssignTo, updatedTask);
        indicateAddressBookChanged();
    }

    //@@author Esilocke
    @Override
    public void dismissFromTask(ArrayList<ReadOnlyPerson> personsToDismiss, ReadOnlyTask taskToDismissFrom)
            throws TaskNotFoundException, DuplicateTaskException {
        Assignees assignees = taskToDismissFrom.getAssignees();
        Assignees newAssignees = new Assignees(assignees);
        ArrayList<Index> positions = addressBook.extractPersonIndexes(personsToDismiss);

        boolean atLeastOneDismissed = newAssignees.dismiss(positions);
        if (!atLeastOneDismissed) {
            throw new DuplicateTaskException();
        }
        ReadOnlyTask updatedTask = constructTaskWithNewAssignee(taskToDismissFrom, newAssignees);
        addressBook.updateTask(taskToDismissFrom, updatedTask);
        indicateAddressBookChanged();
    }

    //@@author Esilocke
    @Override
    public void setAsComplete(ReadOnlyTask toSet, boolean isComplete)
            throws TaskNotFoundException, DuplicateTaskException {
        TaskName taskName = toSet.getTaskName();
        Description description = toSet.getDescription();
        Deadline deadline = toSet.getDeadline();
        Priority priority = toSet.getPriority();
        Assignees assignees = toSet.getAssignees();
        TaskAddress taskAddress = toSet.getTaskAddress();
        Boolean state = isComplete;
        if (state == toSet.getCompleteState()) {
            throw new DuplicateTaskException();
        }
        ReadOnlyTask updatedTask = new Task(taskName, description, deadline, priority, assignees, state, taskAddress);
        updateTask(toSet, updatedTask);
    }

    @Override
    public void viewAssignees(ReadOnlyTask task) {
        Assignees assignees = task.getAssignees();
        ArrayList<Index> internalList = assignees.getList();
        ArrayList<ReadOnlyPerson> personsToShow = new ArrayList<>();
        for (Index i : internalList) {
            assert(i.getZeroBased() < filteredPersons.size());
            personsToShow.add(filteredPersons.get(i.getZeroBased()));
        }
        PersonCompleteMatchPredicate assignedPredicate = new PersonCompleteMatchPredicate(personsToShow);
        updateFilteredPersonList(assignedPredicate);
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

    //@@author Esilocke
    @Override
    public ObservableList<ReadOnlyTask> getFilteredTaskList() {
        return FXCollections.unmodifiableObservableList(filteredTasks);
    }
    //@@author

    //@@author jeffreygohkw
    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        if (privacyLevel == 3) {
            if (predicate instanceof NameContainsKeywordsPredicate) {
                this.updateFilteredPersonList(new NameContainsKeywordsPrivacyLevelPredicate(((
                        NameContainsKeywordsPredicate) predicate).getKeywords()));
            } else if (predicate instanceof NameContainsTagsPredicate) {
                this.updateFilteredPersonList(new NameContainsTagsPrivacyLevelPredicate(((
                        NameContainsTagsPredicate) predicate).getTags()));
            } else if (predicate instanceof NameContainsFavouritePredicate) {
                this.updateFilteredPersonList(new NameContainsFavouritePrivacyLevelPredicate());
            } else if (predicate == PREDICATE_SHOW_ALL_PERSONS) {
                this.updateFilteredPersonList(new ShowAllPrivacyLevelPredicate());
            } else {
                filteredPersons.setPredicate(predicate);
            }
        } else {
            filteredPersons.setPredicate(predicate);
        }
    }

    //@@author Esilocke
    @Override
    public void updateFilteredTaskList(Predicate<ReadOnlyTask> predicate) {
        requireNonNull(predicate);
        filteredTasks.setPredicate(predicate);
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
                && filteredTasks.equals(other.filteredTasks);
    }

    //=========== Utility methods =============================================================

    //@@author Esilocke
    /**
     * Constructs a new {@code ReadOnlyTask} from an existing ReadOnlyTask, with the specified assignees list.
     */
    public ReadOnlyTask constructTaskWithNewAssignee(ReadOnlyTask originalTask, Assignees updatedAssignees) {
        TaskName taskName = originalTask.getTaskName();
        Description description = originalTask.getDescription();
        Deadline deadline = originalTask.getDeadline();
        Priority priority = originalTask.getPriority();
        Boolean state = originalTask.getCompleteState();
        TaskAddress taskAddress = originalTask.getTaskAddress();

        ReadOnlyTask updatedTask = new Task(taskName, description, deadline, priority, updatedAssignees,
                state, taskAddress);
        return updatedTask;
    }

    //@@author jeffreygohkw
    @Override
    public void setPrivacyLevel(int level) {
        if (level < 1 || level > 3) {
            throw new IllegalArgumentException("Privacy Level can only be 0, 1 or 2");
        } else {
            this.privacyLevel = level;
        }
    }

    @Override
    public int getPrivacyLevel() {
        return this.privacyLevel;
    }

    @Override
    public ReadOnlyPerson getPersonAtIndexFromAddressBook(int index) {
        return addressBook.getPersonAtIndexFromPersonList(index);
    }

    @Override
    public void setTheme(String theme) {
        this.theme = theme;
        userPrefs.setTheme(theme);
    }

    @Override
    public String getTheme() {
        return theme;
    }

    @Override
    public HashMap<String, String> getStyleMap() {
        return addressBook.getStyleMap();
    }

}
