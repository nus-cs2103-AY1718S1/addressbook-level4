package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
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
    private final UniquePersonList favouritePersons;
    private final UniqueTagList tags;

        /*
        * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
         * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
        *
         * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
         *   among constructors.
        */ {
        persons = new UniquePersonList();
        favouritePersons = new UniquePersonList();
        tags = new UniqueTagList();
    }

    public AddressBook() {
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

    //@@author siri99
    public void setFavouritePersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        this.favouritePersons.setPersons(persons);
    }
    //@@author siri99

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

        //@@author siri99
        try {
            setFavouritePersons(newData.getFavouritePersonList());
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }
        //@@author siri99

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

    //@@author siri99
    /** Adds favourite person to addressBook
     * Also updates new tags found (if any)
     */
    public void addFavouritePerson(ReadOnlyPerson p) throws DuplicatePersonException {
        Person newPerson = new Person(p);
        syncMasterTagListWith(newPerson);
        favouritePersons.add(newPerson);
    }
    //@@author siri99

    /**
     * Replaces the given person {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     *
     * Also replaces the given person {@code target} in the Favourite list with {@code editedReadOnlyPerson}
     * if same person is listed on Favourite list too.
     *
     * @throws DuplicatePersonException if updating the person's details causes the person to be equivalent to
     *                                  another existing person in the list.
     * @throws PersonNotFoundException  if {@code target} could not be found in the list.
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
        /* Line makes sure that Favourite List is synced with main list, if a person is
         * edited from the main list and is also present on the Favourite List,
         * the person will be automatically edited from fav list too
         */
        if (favouritePersons.contains(target)) {
            favouritePersons.setPerson(target, editedPerson);
        }
    }

    //@@author siri99
    /**
     * Sorts the list of people in the address book.
     */
    public void sortPersonsByName() {
        persons.sortPersonsByName();
    }
    //@@author siri99

    public void sortPersonsByBirthday() {
        persons.sortPersonsByBirthday();
    }

    public void sortPersonsByScore() {
        persons.sortPersonsByScore();
    }
    /**
     * Ensures that every tag in this person:
     * - exists in the master list {@link #tags}
     * - points to a Tag object in the master list
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
     * - exists in the master list {@link #tags}
     * - points to a Tag object in the master list
     *
     * @see #syncMasterTagListWith(Person)
     */
    private void syncMasterTagListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterTagListWith);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * Also removes {@code key} from the Favourite List if same person is listed on Fav list too.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {

        if (persons.remove(key)) {
            /* Line makes sure that Favourite List is synced with main list, if a person is
             * deleted from the main list and is also present on the Favourite List,
             * the person will be automatically deleted from fav list too
             */
            if (favouritePersons.contains(key)) {
                favouritePersons.remove(key);
            }
            return true; // returns true if Person is in the main list irrespective of Fav list
        } else {
            throw new PersonNotFoundException();
        }
    }

    //@@author siri99
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeFavouritePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (favouritePersons.remove(key)) {
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }
    //@@author siri99

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + favouritePersons.asObservableList().size()
                + " favourite persons, " + tags.asObservableList().size() + " tags";
        // TODO: refine later
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    //@@author siri99
    @Override
    public ObservableList<ReadOnlyPerson> getFavouritePersonList() {
        return favouritePersons.asObservableList();
    }
    //@@author siri99

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.favouritePersons.equals(((AddressBook) other).favouritePersons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags));
    }

    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        //return Objects.hash(persons, tags);
        return Objects.hash(persons, favouritePersons, tags);
    }
}

