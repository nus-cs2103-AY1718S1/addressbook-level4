package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventList;
import seedu.address.model.event.ObservableTreeMap;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventNotFoundException;
import seedu.address.model.event.timeslot.Timeslot;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.InvalidSortTypeException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.UniqueRelList;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final EventList events;
    private final UniqueTagList tags;
    private final UniqueTagList eventTags;
    private final UniqueRelList relation;

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
        events = new EventList();
        eventTags = new UniqueTagList();
        relation = new UniqueRelList();
    }

    public AddressBook() { }

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

    public void setEvents(List<? extends ReadOnlyEvent> events) {
        this.events.setEvents(events);
    }

    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    public void setRel(Set<Relationship> relation) {
        this.relation.setRel(relation);
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

        setEvents(newData.getEventList());

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);

        setRel(new HashSet<>(newData.getRelList()));
        syncMasterRelListWith(persons);
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
    }

    /**
     * Sorts the list according to name
     */
    public void sortPerson(int type) throws InvalidSortTypeException {
        persons.sortPersonList(type);
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
     * Ensures that every relationships in this person:
     * - exists in the master list {@link #relation}
     * - points to a Relationship object in the master list
     */
    private void syncMasterRelListWith(Person person) {
        final UniqueRelList personRel = new UniqueRelList(person.getRelation());
        relation.mergeFrom(personRel);

        // Create map with values = Relationship object references in the master list
        // used for checking person relation references
        final Map<Relationship, Relationship> masterRelObjects = new HashMap<>();
        relation.forEach(rel -> masterRelObjects.put(rel, rel));

        // Rebuild the list of person relations to point to the relevant relations in the master relation list.
        final Set<Relationship> correctRelReferences = new HashSet<>();
        personRel.forEach(rel -> correctRelReferences.add(masterRelObjects.get(rel)));
        person.setRel(correctRelReferences);
    }

    /**
     * Ensures that every relation in these persons:
     * - exists in the master list {@link #relation}
     * - points to a Relationship object in the master list
     *
     * @see #syncMasterRelListWith(Person)
     */
    private void syncMasterRelListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterRelListWith);
    }
    /**
     * Removes {@code key} from this {@code AddressBook}.
     *
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
     * Removes a tag from all persons in the list if they have it
     *
     * @param tag Tag to be removed
     */
    public void removeTagFromAll(Tag tag) {
        Iterator<Person> iter = persons.iterator();
        while (iter.hasNext()) {
            iter.next().removeTag(tag);
        }
        tags.remove(tag); //remove tag from Master Tag List
    }

    //// event-level operations

    /**
     * Adds an event to the address book.
     */
    public void addEvent(ReadOnlyEvent e) {
        Event newEvent = new Event(e);
        events.add(newEvent);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     *
     * @throws EventNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeEvent(ReadOnlyEvent key) throws EventNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new EventNotFoundException();
        }
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedReadOnlyEvent}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyEvent}.
     * another existing event in the list.
     *
     * @throws EventNotFoundException if {@code target} could not be found in the list.
     */
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedReadOnlyEvent)
            throws EventNotFoundException {
        requireNonNull(editedReadOnlyEvent);
        Event editedEvent = new Event(editedReadOnlyEvent);
        // TODO: create master list for event tags
        // syncMasterTagListWith(editedEvent);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        events.setEvent(target, editedEvent);
    }

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() + " tags";
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
    public ObservableList<Relationship> getRelList() {
        return relation.asObservableList();
    }

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        return events.asObservableList();
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
}
