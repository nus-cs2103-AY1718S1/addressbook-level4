package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.TodoItem;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.UniqueTodoList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.DuplicateTodoItemException;
import seedu.address.model.person.exceptions.NoPersonFoundException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueTodoList todos;

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
        todos = new UniqueTodoList();
    }

    public AddressBook() {}

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

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }

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

    //@@author aaronyhsoh
    /**
     * Replaces the given person {@code target} in the list with {@code favouritedReadOnlyPerson}.
     * Sorts the list to show favourite contacts first.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code favouritedReadOnlyPerson}.
     *
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     *
     * @see #syncMasterTagListWith(Person)
     */
    public void favouritePerson(ReadOnlyPerson target, ReadOnlyPerson favouritedReadOnlyPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireNonNull(favouritedReadOnlyPerson);

        Person favouritedPerson = new Person(favouritedReadOnlyPerson);

        syncMasterTagListWith(favouritedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, favouritedPerson);

        UniquePersonList notFavouriteList = new UniquePersonList();
        UniquePersonList favouriteList = new UniquePersonList();
        for (ReadOnlyPerson person : persons) {
            if (person.getFavourite()) {
                favouriteList.add(person);
            } else {
                notFavouriteList.add(person);
            }
        }
        persons.setPersons(favouriteList);
        for (ReadOnlyPerson person : notFavouriteList) {
            persons.add(person);
        }
    }

    //@@author Hailinx
    /**
     * Adds a todoItem to target person.
     */
    public void addTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws PersonNotFoundException, DuplicatePersonException, DuplicateTodoItemException {
        requireNonNull(target);

        Person person = new Person(target);
        List<TodoItem> todoItemList = new ArrayList<>(target.getTodoItems());

        if (todoItemList.contains(todoItem)) {
            throw new DuplicateTodoItemException();
        }

        todoItemList.add(todoItem);
        Collections.sort(todoItemList);

        person.setTodoItems(todoItemList);

        persons.setPerson(target, person);
    }

    /**
     * Deletes the given todoItem from target person.
     */
    public void deleteTodoItem(ReadOnlyPerson target, TodoItem todoItem)
            throws PersonNotFoundException, DuplicatePersonException {
        requireAllNonNull(target, todoItem);

        Person person = new Person(target);
        List<TodoItem> todoItemList = new ArrayList<>(target.getTodoItems());

        todoItemList.remove(todoItem);
        person.setTodoItems(todoItemList);

        persons.setPerson(target, person);
    }

    /**
     * Resets all todoItem for target person.
     */
    public void resetTodoItem(ReadOnlyPerson target)
            throws PersonNotFoundException, DuplicatePersonException {
        requireNonNull(target);
        Person person = new Person(target);
        person.setTodoItems(new ArrayList<>());

        persons.setPerson(target, person);
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

    //@@author qihao27
    /**
     * Sorts the list by the specified @param parameter.
     * @throws NoPersonFoundException if no persons found in this {@code AddressBook}.
     */
    public void sortPerson(String parameter) throws NoPersonFoundException {
        persons.sort(parameter);
    }
    //@@author

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, "
            + tags.asObservableList().size() + " tags, "
            + todos.asObservableList().size() + " todos";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    //@@author qihao27
    @Override
    public ObservableList<TodoItem> getTodoList() {
        return todos.asObservableList();
    }
    //@@author

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags)
                && this.todos.equalsOrderInsensitive(((AddressBook) other).todos));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
