package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleObjectProperty;
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
    private final UniqueTagList tags;
    private final ArrayList<String> themes;
    private HashMap<Tag, ArrayList<Person>> tagPersonMap;
    private final ObjectProperty<UniqueTagList> realTags;

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
        themes = new ArrayList<>();
        tagPersonMap = new HashMap<>();
        realTags = new SimpleObjectProperty<>(new UniqueTagList());
    }

    public AddressBook() {
        initialiseThemes();
    }

    /**
     * Creates an AddressBook using the Persons and Tags in the {@code toBeCopied}
     */
    public AddressBook(ReadOnlyAddressBook toBeCopied) {
        this();
        resetData(toBeCopied);
    }

    //@@author yuheng222
    /**
     * Initialises the themes in this {@code AddressBook}.
     */

    private void initialiseThemes() {
        themes.add("MidnightTheme.css");
        themes.add("SummerTheme.css");
        themes.add("CoffeeTheme.css");
        themes.add("CrayonTheme.css");
    }

    public ArrayList<String> getThemesList() {
        return themes;
    }
    //@@author

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
        System.out.println(tagPersonMap.size());
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
    }

    //@@author WangJieee
    /**
     * Updates the {@code tagPersonMap} with {@code updatedPerson}.
     */
    private void updateTagPersonMap(Person updatedPerson) {
        final UniqueTagList personTags = new UniqueTagList(updatedPerson.getTags());
        Set<Tag> tagSet = tagPersonMap.keySet();
        for (Tag tag: personTags) {
            if (!tagSet.contains(tag)) {
                //add a new key to the tagPersonMap if a new tag is introduced
                ArrayList<Person> newPersonList = new ArrayList<>();
                newPersonList.add(updatedPerson);
                tagPersonMap.put(tag, newPersonList);
            } else {
                //add the person to the tagPersonMap for the tag
                if (!tagPersonMap.get(tag).contains(updatedPerson)) {
                    tagPersonMap.get(tag).add(updatedPerson);
                }
            }
        }

        for (Tag tag: tagSet) {
            if (tagPersonMap.get(tag).contains(updatedPerson) && !personTags.contains(tag)) {
                //remove the person from the tagPersonMap for the tag
                tagPersonMap.get(tag).remove(updatedPerson);
            }
        }

        for (Iterator<Map.Entry<Tag, ArrayList<Person>>> itr = tagPersonMap.entrySet().iterator(); itr.hasNext();) {
            Map.Entry<Tag, ArrayList<Person>> entry = itr.next();
            if (entry.getValue().isEmpty()) {
                itr.remove();
            }
        }
    }

    /**
     * Updates the {@code tagPersonMap} with {@code removedPerson}.
     */
    private void updateTagPersonMapRemovePerson(ReadOnlyPerson removedPerson) {
        Set<Tag> tagSet = tagPersonMap.keySet();
        for (Tag tag: tagSet) {
            if (tagPersonMap.get(tag).contains(removedPerson)) {
                //remove the person from the tagPersonMap for the tag
                tagPersonMap.get(tag).remove(removedPerson);
            }
        }

        for (Iterator<Map.Entry<Tag, ArrayList<Person>>> itr = tagPersonMap.entrySet().iterator(); itr.hasNext();) {
            Map.Entry<Tag, ArrayList<Person>> entry = itr.next();
            if (entry.getValue().isEmpty()) {
                itr.remove();
            }
        }
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
        updateTagPersonMap(person);
        realTags.set(new UniqueTagList(tagPersonMap.keySet()));

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

    //@@author WangJieee
    /**
     * Removes {@code key} from this {@code AddressBook}.
     * Update {@code tagPersonMap}
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removePerson(ReadOnlyPerson key) throws PersonNotFoundException {
        if (persons.remove(key)) {
            updateTagPersonMapRemovePerson(key);
            realTags.set(new UniqueTagList(tagPersonMap.keySet()));
            return true;
        } else {
            throw new PersonNotFoundException();
        }
    }
    //@@author

    //@@author yuheng222
    /** Sorts the persons in this {@code AddressBook} lexicographically */

    public void sort() {
        persons.sort();
    }
    //@@author

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

    //@@author WangJieee
    /**
     * Returns a tag list containing the existing tags
     */
    public ObjectProperty<UniqueTagList> getRealTagList() {
        return realTags;
    }
    //@@author

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
        return Objects.hash(persons, tags, themes, realTags, tagPersonMap);
    }
}
