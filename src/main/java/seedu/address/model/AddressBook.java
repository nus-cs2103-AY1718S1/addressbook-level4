package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.exceptions.DuplicateTaskException;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueTaskList tasks;
    private HashMap<String, String> styleMap;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        tasks = new UniqueTaskList();
        styleMap = new HashMap<String, String>();
    }

    public AddressBook() {
        initialiseStyleMap();
    }

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) throws DuplicateTaskException {
        this.tasks.setTasks(tasks);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
            setTasks(newData.getTasksList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        } catch (DuplicateTaskException e) {
            assert false : "AddressBooks should not have duplicate tasks";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }

    //@@author Esilocke
    /**
     * Resets only the existing contact or task data of this {@code AddressBook}.
     */
    public void resetPartialData(ReadOnlyAddressBook newData, Prefix type) {
        requireNonNull(newData);
        requireNonNull(type);
        try {
            if (type.equals(PREFIX_TASK)) {
                setTasks(newData.getTasksList());
            } else if (type.equals(PREFIX_PERSON)) {
                tasks.clearAssignees();
                setPersons(newData.getPersonList());
                setTags(new HashSet<>(newData.getTagList()));
                syncMasterTagListWith(persons);
            } else {
                throw new AssertionError("Type must either be persons or tasks");
            }
        } catch (DuplicatePersonException e) {
            assert false : "Address books should not have duplicate persons";
        } catch (DuplicateTaskException e) {
            assert false : "Address books should not have duplicate tasks";
        }
    }
    //@@author
    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        syncMasterTagListWith(newPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(newPerson);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Person)
     */
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyPerson);

        Person editedPerson = new Person(editedReadOnlyPerson);
        syncMasterTagListWith(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
    }

    //@@author charlesgoh
    /**
     * Sorts persons in person list by any field, in either ascending or descending order
     *
     * @param field
     * @param order
     */
    public void sortPersonsBy(String field, String order) {
        persons.sortBy(field, order);
    }

    /**
     * Sorts persons in person list by any field, in either ascending or descending order
     *
     * @param field
     * @param order
     */
    public void sortTasksBy(String field, String order) {
        tasks.sortBy(field, order);
    }

    //@@author Esilocke
    /**
     * Returns an array list of {@code Index} corresponding to the index of {@code ReadOnlyPerson} specified
     */
    public ArrayList<Index> extractPersonIndexes(ArrayList<ReadOnlyPerson> personsToExtract) {
        return persons.extractIndexes(personsToExtract);
    }

    //@@author
    //@@author wangyiming1019
    /**
     * Favourites the given person {@code target} to this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code target} is not in this {@code AddressBook}.
     */
    public void favouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        persons.favouritePerson(target);
    }

    /**
     * Unfavourites the given person {@code target} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code target} is not in this {@code AddressBook}.
     */
    public void unfavouritePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        persons.unfavouritePerson(target);
    }
    //@@author
    /**
     * Ensures that every tag in this person:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     */
    private void syncMasterTagListWith(Person person) {
        final UniqueTagList personTags = new UniqueTagList(person.getTags());
        tags.mergeFrom(personTags);

        // Create map with values = tag object references in the master list
        // used for checking person tag references
        final Map<Tag, Tag> masterTagObjects = new HashMap<>();
        tags.forEach(tag -> masterTagObjects.put(tag, tag));

        // Rebuild the list of person tags to point to the relevant tags in the master tag list.
        final Set<Tag> correctTagReferences = new HashSet<>();
        personTags.forEach(tag -> correctTagReferences.add(masterTagObjects.get(tag)));
        person.setTags(correctTagReferences);
    }

    /**
     * Ensures that every tag in these persons:
     *  - exists in the master list {@link #tags}
     *  - points to a Tag object in the master list
     *  @see #syncMasterTagListWith(Person)
     */
    private void syncMasterTagListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }

    //@@author Esilocke
    /**
     * Returns an array containing:
     * Index - The old index of each person in the UniquePersonList
     * Value - The new index of each person after a sort operation
     */
    public Index[] getMappings() {
        return persons.getMappings();
    }
    //@@author
    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// task-level operations

    //@@author Esilocke
    /**
     * Adds a task to the address book.
     *
     * @throws DuplicateTaskException if an equivalent task already exists.
     */
    public void addTask(ReadOnlyTask t) throws DuplicateTaskException {
        Task newTask = new Task(t);
        tasks.add(newTask);
    }

    //@@author Esilocke
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws TaskNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeTask(ReadOnlyTask key) throws TaskNotFoundException {
        if (tasks.remove(key)) {
            return true;
        } else {
            throw new TaskNotFoundException();
        }
    }

    /** Removes the specified person from all assignment lists for every task **/
    public void removePersonFromAssignees(Index target) {
        tasks.removeAssignee(target);
    }

    /**
     * Updates the Assignees for all tasks in the internal tasks list with their new mappings
     */
    public void updateTaskAssigneeMappings(Index[] mappings) {
        tasks.updateAssignees(mappings);
    }

    //@@author Esilocke
    /**
     * Replaces the given task {@code target} in the list with {@code editedReadOnlyTask}.
     *
     * @throws DuplicateTaskException if updating the task's details causes the task to be equivalent to
     *      another existing task in the list.
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedReadOnlyTask)
            throws DuplicateTaskException, TaskNotFoundException {
        requireNonNull(editedReadOnlyTask);

        Task editedTask = new Task(editedReadOnlyTask);
        tasks.setTask(target, editedTask);
    }

    //@@author jeffreygohkw
    //// task-level operations

    /**
     * Initialises the style map by adding the key value pairs
     * for the strings that will be input in ThemeCommand and the file name of the .css file
     */
    private void initialiseStyleMap() {
        styleMap.put("dark", "DarkTheme.css");
        styleMap.put("Dark", "DarkTheme.css");
        styleMap.put("light", "LightTheme.css");
        styleMap.put("Light", "LightTheme.css");
    }

    public HashMap<String, String> getStyleMap() {
        return styleMap;
    }
    //@@author
    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags"
                + tasks.asObservableList().size() +  " tasks";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    //@@author jeffreygohkw
    public ReadOnlyPerson getPersonAtIndexFromPersonList(int index) {
        return persons.asObservableList().get(index);
    }

    //@@author Esilocke
    @Override
    public ObservableList<ReadOnlyTask> getTasksList() {
        return tasks.asObservableList();
    }
    //@@author

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags))
                && this.tasks.equals(((AddressBook) other).tasks);
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags, tasks);
    }
}
