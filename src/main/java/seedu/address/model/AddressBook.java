package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.FavoriteCommand;
import seedu.address.model.group.DuplicateGroupException;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
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
    private final UniqueGroupList groups;

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
        groups = new UniqueGroupList();
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

    //@@author hthjthtrh
    public void setGroups(List<? extends Group> groups) throws DuplicateGroupException, DuplicatePersonException {
        this.groups.setGroups(groups);
    }
    //@@author

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
            setGroups(newData.getGroupList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        } catch (DuplicateGroupException e) {
            assert false : "AddressBooks should not have duplicate groups";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }

    //// person-level operations

    /**
     * Adds a person to the address book.
     * Also checks the new person's tags and updates {@link #tags} with any new tags found,
     * and updates the Tag objects in the person to point to those in {@link #tags}.
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

    //@@author hthjthtrh
    /**
     * Creates and adds the group into groups
     * @param groupName name of the group
     * @param personToGroup person in the group
     * @throws DuplicateGroupException
     */
    public void addGroup(String groupName, List<ReadOnlyPerson> personToGroup)
            throws DuplicateGroupException {
        Group newGroup = new Group(groupName);
        personToGroup.forEach(person -> {
            try {
                newGroup.add(new Person(person));
            } catch (DuplicatePersonException e) {
                throw new AssertionError("Shouldn't exist duplicate person");
            }
        });
        newGroup.updatePreviews();
        groups.add(newGroup);
    }

    /**
     * adds the grp to the grp list
     * @param grp
     * @throws DuplicateGroupException
     */
    public void addGroup(Group grp) throws DuplicateGroupException {
        Group newGroup;
        try {
            newGroup = new Group(grp);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("Shouldn't exist duplicate person");
        }
        groups.add(newGroup);
    }
    //@@author

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

    //@@author majunting
    /**
     * sort the unique person list by the given attribute, then put the favorite contacts at the top of the list
     */
    public void sortPersonBy(int attribute) {
        persons.sortPersonBy(attribute);
        persons.sort();
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

    /**
     * Favorites {@code key} in this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public void favoritePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        requireNonNull(key);
        persons.favorite(key);
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
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

    @Override
    public ObservableList<Group> getGroupList() {
        return groups.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }

    //@@author hthjthtrh
    /**
     * Deletes or updates the group, if the group contains personToEdit
     * @param personToEdit original person to be updated
     * @param editedPerson the person to update to. If null, it is a deletion
     */
    public void checkPersonInGroupList(ReadOnlyPerson personToEdit, Person editedPerson, Class commandClass) {
        if (this.groups.asObservableList().size() == 0) {
            return;
        }

        if (commandClass.equals(FavoriteCommand.class)) {
            this.groups.forEach(group -> {
                if (group.contains(personToEdit)) {
                    try {
                        group.favorite(personToEdit);
                    } catch (PersonNotFoundException pnfe) {
                        throw new AssertionError("The target person cannot be missing");
                    }

                    group.updatePreviews();
                }
            });
        } else if (commandClass.equals(DeleteCommand.class)) {
            this.groups.forEach(group -> {
                if (group.contains(personToEdit)) {
                    try {
                        group.remove(personToEdit);
                    } catch (PersonNotFoundException pnfe) {
                        throw new AssertionError("The target person cannot be missing");
                    }

                    group.updatePreviews();
                }
            });
        } else {
            this.groups.forEach(group -> {
                if (group.contains(personToEdit)) {
                    try {
                        group.setPerson(personToEdit, editedPerson);
                    } catch (DuplicatePersonException dpe) {
                        throw new AssertionError("Shouldn't have duplicate person if"
                                + " update person is successful");
                    } catch (PersonNotFoundException pnfe) {
                        throw new AssertionError("The target person cannot be missing");
                    }

                    group.updatePreviews();
                }
            });
        }
    }

    public void removeGroup(Group grpToDelete) {
        groups.removeGroup(grpToDelete);
    }

    public void setGrpName(Group targetGrp, String newName) throws DuplicateGroupException {
        this.groups.setGrpName(targetGrp, newName);
    }

    public Index getGroupIndex(String groupName) {
        return Index.fromZeroBased(groups.getGroupIndex(groupName));
    }

    /**
     * removes targetPerson from targetGroup
     * @param targetGrp
     * @param targetPerson
     */
    public void removePersonFromGroup(Group targetGrp, ReadOnlyPerson targetPerson) {
        groups.removePersonFromGroup(targetGrp, targetPerson);
    }

    /**
     * addes targetPerson to targetGroup
     * @param targetGrp
     * @param targetPerson
     */
    public void addPersonToGroup(Group targetGrp, ReadOnlyPerson targetPerson) throws DuplicatePersonException {
        groups.addPersonToGroup(targetGrp, targetPerson);
    }
    //@@author
}
