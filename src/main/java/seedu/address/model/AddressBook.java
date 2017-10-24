package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.MemberList;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.EmptyListException;
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
    private final UniqueEventList events;

    /**
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */


    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        events = new UniqueEventList();
    }

    public AddressBook() {
    }

    /**
     * Creates an AddressBook using the Persons, Tags and Events in the {@code toBeCopied}
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

    public void setEvents(List<? extends Event> events) throws DuplicateEventException {
        this.events.setEvents(events);
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


        try {
            setEvents(newData.getEventList());
        } catch (DuplicateEventException e) {
            assert false : "AddressBooks should not have duplicate events";
        }
        syncMasterEventListWith(persons);
        syncMasterEventListMembers(events);
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

    public void sortPerson(Comparator<ReadOnlyPerson> sortType, boolean isDescending) throws EmptyListException {
        persons.sort(sortType, isDescending);
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
        syncMasterEventListWith(editedPerson);

        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
        syncMasterEventListMembers(new UniqueEventList(editedPerson.getEvents()));

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

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// event-level operations

    /**
     * Ensures that every event in this person:
     * - exists in the master list {@link #events}
     * - points to a Event object in the master list
     */
    private void syncMasterEventListWith(Person person) {
        final UniqueEventList personEvents = new UniqueEventList(person.getEvents());
        events.mergeFrom(personEvents);

        // Create map with values = event object references in the master list
        // used for checking person event references
        final Map<Event, Event> masterEventObjects = new HashMap<>();
        events.forEach(event -> masterEventObjects.put(event, event));

        // Rebuild the list of person event to point to the relevant events in the master event list.
        final Set<Event> correctEventReferences = new HashSet<>();
        personEvents.forEach(event -> correctEventReferences.add(masterEventObjects.get(event)));
        person.setEvents(correctEventReferences);
    }

    /**
     * Ensures that every event in these persons:
     * - exists in the master list {@link #events}
     * - points to an Event object in the master list
     *
     * @see #syncMasterEventListWith(Person)
     */
    private void syncMasterEventListWith(UniquePersonList persons) {
        persons.forEach(this::syncMasterEventListWith);
    }

    //// event-person sync

    /**
     * Ensures that every member in this event:
     * - points to a person object in the master list
     */
    private void syncMasterEventListMembers(Event event) {

        // Create map with values = person object references in the master list
        // used for checking member references
        final Map<Person, Person> masterPersonObjects = new HashMap<>();
        this.persons.forEach(person -> masterPersonObjects.put(person, person));

        ArrayList<ReadOnlyPerson> eventMembers = new ArrayList<>();
        this.persons.asObservableList().stream().filter(readOnlyPerson ->
                readOnlyPerson.getEvents().contains(event)).forEach(eventMembers::add);


        // Rebuild the list of member to point to the relevant person in the master person list.
        final Set<Person> correctPersonReferences = new HashSet<>();
        eventMembers.forEach(person -> correctPersonReferences.add(masterPersonObjects.get(person)));
        event.setMemberList(new MemberList(
                new ArrayList<>(correctPersonReferences)));
    }

    /**
     * Ensures that every member in these events:
     * - points to a person object in the master list
     *
     * @see #syncMasterEventListMembers(Event)
     */
    private void syncMasterEventListMembers(UniqueEventList events) {
        events.forEach(this::syncMasterEventListMembers);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() + " tags, "
                + events.asObservableList().size() + " events";
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
    public ObservableList<Event> getEventList() {
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
