package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.mod.Mod;
import seedu.address.model.mod.UniqueModList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.task.ReadOnlyTask;
import seedu.address.model.task.Task;
import seedu.address.model.task.UniqueTaskList;
import seedu.address.model.task.exceptions.TaskNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTaskList tasks;
    private final UniqueModList mods;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        tasks = new UniqueTaskList();
        mods = new UniqueModList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Mods in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    public void setTasks(List<? extends ReadOnlyTask> tasks) {
        this.tasks.setTasks(tasks);
    }

    public void setTags(Set<Mod> mods) {
        this.mods.setMods(mods);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
            setTasks(newData.getTaskList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's mods and updates {@link #mods} with any new mods found,
     * and updates the Mod objects in the person to point to those in {@link #mods}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        syncMasterTagListWith(newPerson);
        // TODO: the mods master list will be updated even though the below line fails.
        // This can cause the mods master list to have additional mods that are not modded to any person
        // in the person list.
        persons.add(newPerson);
    }

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
     *
     */
    public void addTask(ReadOnlyTask t) {
        Task newTask = new Task(t);
        tasks.add(newTask);
    }

    /**
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s mod list will be updated with the mods of {@code editedReadOnlyPerson}.
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
        // TODO: the mods master list will be updated even though the below line fails.
        // This can cause the mods master list to have additional mods that are not modded to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
    }

    /**
     * Replaces the given task {@code target} in the list with {@code editedReadOnlyTask}.
     *
     * @throws TaskNotFoundException if {@code target} could not be found in the list.
     */
    public void updateTask(ReadOnlyTask target, ReadOnlyTask editedReadOnlyTask)
            throws TaskNotFoundException {
        requireNonNull(editedReadOnlyTask);

        Task editedTask = new Task(editedReadOnlyTask);
        tasks.setTask(target, editedTask);
    }

    /**
     * Ensures that every mod in this person:
     *  - exists in the master list {@link #mods}
     *  - points to a Mod object in the master list
     */
    private void syncMasterTagListWith(Person person) {
        final UniqueModList personTags = new UniqueModList(person.getMods());
        mods.mergeFrom(personTags);

        // Create map with values = mod object references in the master list
        // used for checking person mod references
        final Map<Mod, Mod> masterTagObjects = new HashMap<>();
        mods.forEach(mod -> masterTagObjects.put(mod, mod));

        // Rebuild the list of person mods to point to the relevant mods in the master mod list.
        final Set<Mod> correctTagReferences = new HashSet<>();
        personTags.forEach(mod -> correctTagReferences.add(masterTagObjects.get(mod)));
        person.setTags(correctTagReferences);
    }

    /**
     * Ensures that every mod in these persons:
     *  - exists in the master list {@link #mods}
     *  - points to a Mod object in the master list
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

    /**
     * Sorts the personList alphabetically
     */
    public void sortPersonListByName() {
        persons.sortPersonListByName();
    }

    /**
     * Sorts the taskList alphabetically
     */
    public void sortTaskListByName() {
        tasks.sortTaskListByName();
    }

    //// mod-level operations

    public void addMod(Mod t) throws UniqueModList.DuplicateModException {
        mods.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + mods.asObservableList().size() +  " mods";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return tasks.asObservableList();
    }

    @Override
    public ObservableList<Mod> getTagList() {
        return mods.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.mods.equalsOrderInsensitive(((AddressBook) other).mods));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, mods);
    }
}
