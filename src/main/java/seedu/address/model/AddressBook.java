package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.UniqueEventList;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.event.exceptions.EventNotFoundException;
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
    private final UniqueEventList events;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     * among constructors.
     */
    {
        persons = new UniquePersonList();
        tags = new UniqueTagList();
        events = new UniqueEventList();
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

    /*****************************************************
     * List overwrite operations
     *****************************************************/

    //@@author low5545
    /**
     * Adds all persons in the argument person list to this list.
     */
    public void addPersons(List<? extends ReadOnlyPerson> persons) {
        this.persons.addPersons(persons);
    }
    //@@author

    /**
     * Replaces all persons in this list with those in the argument person list.
     */
    public void setPersons(List<? extends ReadOnlyPerson> persons) throws DuplicatePersonException {
        this.persons.setPersons(persons);
    }

    //@@author low5545
    /**
     * Adds all events in the argument event list to this list.
     */
    public void addEvents(List<? extends ReadOnlyEvent> events) {
        this.events.addEvents(events);
    }
    //@@author

    //@@author junyango
    /**
     * Replaces all events in this list with those in the argument event list.
     */
    public void setEvents(List<? extends ReadOnlyEvent> events) throws DuplicateEventException {
        this.events.setEvents(events);
    }

    //@@author

    //@@author low5545
    /**
     * Adds all tags in the argument tag list to this list.
     */
    public void addTags(Set<Tag> tags) {
        this.tags.addTags(tags);
    }
    //@@author

    /**
     * Replaces all tags in this list with those in the argument tag list.
     */
    public void setTags(Set<Tag> tags) {
        this.tags.setTags(tags);
    }

    //@@author low5545
    /**
     * Adds extra {@code newData} into the existing data of this {@code AddressBook}.
     */
    public void addData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);

        addPersons(newData.getPersonList());
        addEvents(newData.getEventList());
        addTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }
    //@@author

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
        try {
            setEvents(newData.getEventList());
        } catch (DuplicateEventException de) {
            assert false : "AddressBooks should not have duplicate events";
        }
        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);
    }

    /*****************************************************
     * Person-level operations
     *****************************************************/

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

    //@@author dennaloh
    /**
     * Returns URL for google maps using the person's address
     * @param key is target person
     * @return URL
     */
    public String getGMapUrl (ReadOnlyPerson key) {

        String address = key.getAddress().toString();
        String replacedAddress = address.replaceAll(" ", "+");
        StringBuilder sb = new StringBuilder();
        sb.append("http://maps.google.com/maps?saddr=");
        sb.append("&daddr=");
        sb.append(replacedAddress);
        String gMapUrl = sb.toString();

        return gMapUrl;
    }

    /**
     * Returns URL to search for person on facebook
     * @param key is target person
     * @return URL
     */
    public String getFbUrl (ReadOnlyPerson key) {
        String name = key.getName().toString();
        String replacedName = name.replaceAll(" ", "%20");
        StringBuilder sb = new StringBuilder();
        sb.append("https://www.facebook.com/search/top/?q=");
        sb.append(replacedName);
        String fbUrl = sb.toString();

        return fbUrl;
    }
    //@@author

    /**
     * Sorts the persons according to their name.
     */
    public void sortPersonList() {
        persons.sortPersons();
    }

    //@@author junyango
    /*****************************************************
     * Event-level operations
     *****************************************************/

    /**
     * Adds an event to the address book.
     *
     * @throws DuplicateEventException if an equivalent event already exists.
     */
    public void addEvent(ReadOnlyEvent e) throws DuplicateEventException {
        Event newEvent = new Event(e);
        events.add(newEvent);
    }

    /**
     * Replaces the given event {@code target} in the list with {@code editedReadOnlyPerson}.
     * {@code AddressBook}'s tag list will be updated with the tags of {@code editedReadOnlyPerson}.
     *
     * @throws DuplicateEventException if updating the event's details causes the event to be equivalent to
     *      another existing event in the list.
     * @throws EventNotFoundException if {@code target} could not be found in the list.
     *
     */
    public void updateEvent(ReadOnlyEvent target, ReadOnlyEvent editedReadOnlyEvent)
            throws DuplicateEventException, EventNotFoundException {
        requireNonNull(editedReadOnlyEvent);

        Event editedEvent = new Event(editedReadOnlyEvent);

        events.setEvent(target, editedEvent);
    }

    /**
     * Removes {@code key} from this {@code AddressBook}.
     * @throws EventNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public boolean removeEvent(ReadOnlyEvent key) throws EventNotFoundException {
        if (events.remove(key)) {
            return true;
        } else {
            throw new EventNotFoundException("Event not found");
        }
    }

    /**
     * Sorts the events according to their date time.
     */
    public void sortEventList() {
        events.sortEvents();
    }
    //@@author

    /*****************************************************
     * Tag-level operations
     *****************************************************/

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

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

    /*****************************************************
     * Util methods
     *****************************************************/

    @Override
    public String toString() {
        // TODO: refine later
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags";
    }

    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<ReadOnlyEvent> getEventList() {
        return events.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
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
