package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.UniqueAppointmentList;
import seedu.address.model.group.Group;
import seedu.address.model.group.UniqueGroupList;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.SortedUniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final SortedUniquePersonList persons;
    private final UniqueTagList tags;
    private final UniqueGroupList groups;
    private final UniqueAppointmentList appointments;

    /*
     * The 'unusual' code block below is an non-static initialization block, sometimes used to avoid duplication
     * between constructors. See https://docs.oracle.com/javase/tutorial/java/javaOO/initial.html
     *
     * Note that non-static init blocks are not recommended to use. There are other ways to avoid duplication
     *   among constructors.
     */
    {
        persons = new SortedUniquePersonList();
        tags = new UniqueTagList();
        groups = new UniqueGroupList();
        appointments = new UniqueAppointmentList();
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

    public void setGroups(Set<Group> groups) {
        this.groups.setGroups(groups);
    }

    public void setAppointments(Set<Appointment> appointments) {
        this.appointments.setAppointments(appointments);
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
        setGroups(new HashSet<>(newData.getGroupList()));
        setAppointments(getAllAppointments());
        syncMasterTagListWith(persons);
        syncMasterGroupListWith(persons);
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
        syncMasterGroupListWith(newPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.add(newPerson);
    }

    //@@author namvd2709
    /**
     * Adds an appointment to address book.
     */
    public void addAppointment(Appointment a) throws IllegalValueException,
            UniqueAppointmentList.ClashAppointmentException {
        Appointment newAppointment = new Appointment(Appointment.getOriginalAppointment(a.toString()));
        appointments.add(newAppointment);
    }

    /**
     * Removes an appointment
     */
    public void removeAppointment(Appointment a) {
        appointments.remove(a);
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
        //@@author namvd2709
        Appointment oldAppointment = target.getAppointment();
        Appointment newAppointment = editedPerson.getAppointment();
        syncMasterTagListWith(editedPerson);
        syncMasterGroupListWith(editedPerson);
        // TODO: the tags master list will be updated even though the below line fails.
        // This can cause the tags master list to have additional tags that are not tagged to any person
        // in the person list.
        persons.setPerson(target, editedPerson);
        appointments.remove(oldAppointment);
        if (!newAppointment.value.equals("")) {
            try {
                appointments.add(newAppointment);
            } catch (UniqueAppointmentList.DuplicateAppointmentException e) {
                e.printStackTrace();
            } catch (UniqueAppointmentList.ClashAppointmentException e) {
                e.printStackTrace();
            }
        }
        //@@author
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
    private void syncMasterTagListWith(SortedUniquePersonList persons) {
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

    //@@author arturs68
    /**
     * Ensures that every group in this person:
     *  - exists in the master list {@link #groups}
     *  - points to a Group object in the master list
     */
    private void syncMasterGroupListWith(Person person) {
        final UniqueGroupList personGroups = new UniqueGroupList(person.getGroups());
        groups.mergeFrom(personGroups);

        // Create map with values = group object references in the master list
        // used for checking person group references
        final Map<Group, Group> masterGroupObjects = new HashMap<>();
        groups.forEach(group -> masterGroupObjects.put(group, group));

        // Rebuild the list of person groups to point to the relevant groups in the master group list.
        final Set<Group> correctGroupReferences = new HashSet<>();
        personGroups.forEach(group -> correctGroupReferences.add(masterGroupObjects.get(group)));
        person.setGroups(correctGroupReferences);
    }

    /**
     * Ensures that every group in these persons:
     *  - exists in the master list {@link #groups}
     *  - points to a Group object in the master list
     *  @see #syncMasterGroupListWith(Person)
     */
    private void syncMasterGroupListWith(SortedUniquePersonList persons) {
        persons.forEach(this::syncMasterGroupListWith);
    }
    //@@author

    //// tag-level operations

    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, " + tags.asObservableList().size() +  " tags"
                + groups.asObservableList().size() + " groups";
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

    //@@author namvd2709
    public Set<Appointment> getAllAppointments() {
        return persons.getAllAppointments();
    }

    //@@author
    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof AddressBook // instanceof handles nulls
                && this.persons.equals(((AddressBook) other).persons)
                && this.tags.equalsOrderInsensitive(((AddressBook) other).tags)
                && this.groups.equalsOrderInsensitive(((AddressBook) other).groups));
    }

    //@@author
    @Override
    public int hashCode() {
        // use this method for custom fields hashing instead of implementing your own
        return Objects.hash(persons, tags);
    }
}
