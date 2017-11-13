package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.module.Module;
import seedu.address.model.module.UniqueModuleList;
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
    // @@author tanchc
    private final UniqueTaskList tasks;
    // @@author
    private final UniqueModuleList modules;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new UniquePersonList();
        // @@author tanchc
        tasks = new UniqueTaskList();
        // @@author
        modules = new UniqueModuleList();
    }

    public AddressBook() {}

    /**
     * Creates an AddressBook using the Persons and Modules in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //// list overwrite operations

    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }
    // @@author tanchc
    public void setTasks(List<? extends ReadOnlyTask> tasks) {
        this.tasks.setTasks(tasks);
    }
    // @@author
    public void setModules(Set<Module> modules) {
        this.modules.setModules(modules);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
            // @@author tanchc
            setTasks(newData.getTaskList());
            // @@author
        } catch (DuplicatePersonException e) {
            assert false : "ContactHub should not have duplicate persons";
        }

        setModules(new HashSet<>(newData.getModuleList()));
        syncMasterModuleListWith(persons);
    }

    //// person-level operations

    /**
     * Adds a person to ContactHub.
     * Also checks the new person's modules and updates {@link #modules} with any new modules found,
     * and updates the Module objects in the person to point to those in {@link #modules}.
     *
     * @throws DuplicatePersonException if an equivalent person already exists.
     */
    public void addPerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        syncMasterModuleListWith(newPerson);
        // TODO: the modules master list will be updated even though the below line fails.
        // This can cause the modules master list to have additional modules that are not modules to any person
        // in the person list.
        persons.add(newPerson);
    }
    // @@author tanchc
    /**
     * Adds a task to ContactHub.
     * Also checks the new task and updates {@link #tasks} with any new tasks found,
     * and updates the Task objects in the person to point to those in {@link #tasks}.
     *
     */
    public void addTask(ReadOnlyTask t) {
        Task newTask = new Task(t);
        tasks.add(newTask);
    }
    // @@author
    /**
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s module list will be updated with the modules of {@code editedReadOnlyPerson}.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *      another existing person in the list.
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterModuleListWith(Person)
     */
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(editedReadOnlyPerson);

        Person editedPerson = new Person(editedReadOnlyPerson);
        syncMasterModuleListWith(editedPerson);
        // TODO: the modules master list will be updated even though the below line fails.
        // This can cause the modules master list to have additional modules that are not modded to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
    }
    // @@author tanchc
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
    // @@author
    /**
     * Ensures that every module in this person:
     *  - exists in the master list {@link #modules}
     *  - points to a Module object in the master list
     */
    private void syncMasterModuleListWith(Person person) {
        final UniqueModuleList personModules = new UniqueModuleList(person.getModules());
        modules.mergeFrom(personModules);

        // Create map with values = module object references in the master list
        // used for checking person module references
        final Map<Module, Module> masterModuleObjects = new HashMap<>();
        modules.forEach(module -> masterModuleObjects.put(module, module));

        // Rebuild the list of person modules to point to the relevant modules in the master module list.
        final Set<Module> correctModuleReferences = new HashSet<>();
        personModules.forEach(module -> correctModuleReferences.add(masterModuleObjects.get(module)));
        person.setModules(correctModuleReferences);
    }

    /**
     * Ensures that every module in these persons:
     *  - exists in the master list {@link #modules}
     *  - points to a Module object in the master list
     *  @see #syncMasterModuleListWith(Person)
     */
    private void syncMasterModuleListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterModuleListWith);
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
    // @@author tanchc
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

    // @@author ahmadalkaff
    /**
     * Sorts the personList alphabetically
     */
    public void sortPersonListByName() {
        persons.sortPersonListByName();
    }
    // @@author tanchc
    /**
     * Sorts the taskList alphabetically
     */
    public void sortTaskListByName() {
        tasks.sortTaskListByName();
    }

    //// module-level operations

    public void addModule(Module m) throws UniqueModuleList.DuplicateModuleException {
        modules.add(m);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + modules.asObservableList().size() +  " modules"
                + tasks.asObservableList().size();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }
    // @@author tanchc
    @Override
    public ObservableList<ReadOnlyTask> getTaskList() {
        return tasks.asObservableList();
    }
    // @@author
    @Override
    public ObservableList<Module> getModuleList() {
        return modules.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.modules.equalsOrderInsensitive(((AddressBook) other).modules));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, modules);
    }
}
