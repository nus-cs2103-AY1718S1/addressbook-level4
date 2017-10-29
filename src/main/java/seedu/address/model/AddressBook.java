package seedu.address.model;

import static java.util.Objects.requireNonNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

import javafx.collections.ObservableList;
import seedu.address.commons.function.ThrowingConsumer;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.ReadOnlyInsurance;
import seedu.address.model.insurance.UniqueLifeInsuranceMap;
import seedu.address.model.insurance.exceptions.DuplicateInsuranceException;
import seedu.address.model.insurance.exceptions.InsuranceNotFoundException;
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
    private final UniqueLifeInsuranceMap lifeInsuranceMap;

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
        lifeInsuranceMap = new UniqueLifeInsuranceMap();
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

    public void setLifeInsurances(Map<UUID, ReadOnlyInsurance> insurances) throws DuplicateInsuranceException {
        this.lifeInsuranceMap.setInsurances(insurances);
    }

    /**
     * Resets the existing data of this {@code AddressBook} with {@code newData}.
     */
    public void resetData(ReadOnlyAddressBook newData) {
        requireNonNull(newData);
        try {
            setPersons(newData.getPersonList());
            persons.sortPersons();
        } catch (DuplicatePersonException e) {
            assert false : "AddressBooks should not have duplicate persons";
        }

        setTags(new HashSet<>(newData.getTagList()));
        syncMasterTagListWith(persons);

        try {
            setLifeInsurances(newData.getLifeInsuranceMap());
        } catch (DuplicateInsuranceException e) {
            assert false : "AddressBooks should not have duplicate insurances";
        }
        syncMasterLifeInsuranceMapWith(persons);

        try {
            syncMasterPersonListWith(lifeInsuranceMap);
        } catch (InsuranceNotFoundException e) {
            assert false : "AddressBooks should not contain id that doesn't match to an insurance";
        }
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
        persons.sortPersons();
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
     * Ensures that every insurance in the master map:
     *  - links to its owner, insured, and beneficiary {@code Person} if they exist in master person list respectively
     */
    public void syncMasterLifeInsuranceMapWith(UniquePersonList persons) {
        lifeInsuranceMap.forEach((id, insurance) -> {
            String owner = insurance.getOwner().getName();
            String insured = insurance.getInsured().getName();
            String beneficiary = insurance.getBeneficiary().getName();
            persons.forEach(person -> {
                if (person.getName().fullName.equals(owner)) {
                    insurance.setOwner(person);
                }
                if (person.getName().fullName.equals(insured)) {
                    insurance.setInsured(person);
                }
                if (person.getName().fullName.equals(beneficiary)) {
                    insurance.setBeneficiary(person);
                }
            });
        });
    }

    /**
     * Ensures that every person in the master list:
     *  - contains the correct life insurance corresponding to its id from the master map
     */
    public void syncMasterPersonListWith(UniqueLifeInsuranceMap lifeInsuranceMap) throws InsuranceNotFoundException {
        persons.forEach((ThrowingConsumer<Person>) person -> {
            List<UUID> idList = person.getLifeInsuranceIds();
            if (!idList.isEmpty()) {
                person.clearLifeInsurances();
                idList.forEach((ThrowingConsumer<UUID>) id -> {
                    LifeInsurance lf = lifeInsuranceMap.get(id);
                    person.addLifeInsurances(lf);
                });
            }
        });
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
    public Map<UUID, ReadOnlyInsurance> getLifeInsuranceMap() {
        return lifeInsuranceMap.asMap();
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
