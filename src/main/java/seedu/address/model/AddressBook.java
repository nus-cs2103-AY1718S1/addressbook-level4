package seedu.address.model;

import static java.util.Objects.requireNonNull;

//import java.util.ArrayList;
//import java.util.Comparator;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

//import javafx.collections.FXCollections;

import javafx.collections.ObservableList;
import seedu.address.model.insurance.LifeInsurance;
import seedu.address.model.insurance.ReadOnlyInsurance;
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
    private HashMap<String, LifeInsurance>lifeInsuranceMap;

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
        lifeInsuranceMap = new HashMap<>();
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

    public void setLifeInsurances(Map<String, ? extends ReadOnlyInsurance> insurances) {
        this.lifeInsuranceMap = (HashMap<String, LifeInsurance>) insurances;
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

        setLifeInsurances(newData.getLifeInsuranceMap());
        syncMasterLifeInsuranceMapWithPersonList();
        syncMasterPersonListWithLifeInsurance();
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
     * Ensures that every insurance in this person:
     *  - exists in the master map {@link #lifeInsuranceMap}
     *  - points to a LifeInsurance object in the master list
     */
    public void syncMasterLifeInsuranceMapWithPersonList() {
        //Get the list of insurance ids of a person and filter out the ones that are empty
        persons.forEach(p -> {
            if (!p.getLifeInsuranceIds().isEmpty()) {
                //for every insurance id get the insurance object from lifeInsuranceMap
                p.getLifeInsuranceIds().forEach(id -> {
                    String idString = id.toString();
                    LifeInsurance lf = lifeInsuranceMap.get(idString);
                    lf.setInsurancePerson(p);
                });
            }
        });
    }

    /**
     * Ensures that every person in LISA:
     *  - exists in the unique person list{@link #persons}
     *  - points to a LifeInsurance object in the master list
     */
    public void syncMasterPersonListWithLifeInsurance() {
        //Get the list of insurance ids of a person and filter out the ones that are empty
        persons.forEach(p -> {
            if (!p.getLifeInsuranceIds().isEmpty()) {
                //for every insurance id get the insurance object from lifeInsuranceMap
                p.clearLifeInsurances();
                p.getLifeInsuranceIds().forEach(id -> {
                    String idString = id.toString();
                    LifeInsurance lf = lifeInsuranceMap.get(idString);
                    p.addLifeInsurances(lf);
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
        /*
        ArrayList<ReadOnlyPerson> temp = new ArrayList<>();
        ObservableList<ReadOnlyPerson> personList = persons.asObservableList();
        for(ReadOnlyPerson person: personList){
            temp.add(person);
        }
        ObservableList<ReadOnlyPerson> temp2 = FXCollections.observableArrayList(temp);
        Comparator<ReadOnlyPerson> ALPHA_ORDER = new Comparator<ReadOnlyPerson>() {
            public int compare(ReadOnlyPerson first, ReadOnlyPerson second) {
                int x = String.CASE_INSENSITIVE_ORDER.compare(first.getName().fullName, second.getName().fullName);
                if (x== 0) {
                    x = (first.getName().fullName).compareTo(second.getName().fullName);
                }
                return x;
            }
        };
        temp2.sort(ALPHA_ORDER);
        return temp2;
        */
        return persons.asObservableList();
    }

    @Override
    public ObservableList<Tag> getTagList() {
        return tags.asObservableList();
    }

    @Override
    public Map<String, ReadOnlyInsurance> getLifeInsuranceMap() {
        final Map<String, ReadOnlyInsurance> lifeInsurances = this.lifeInsuranceMap.entrySet().stream()
            .collect(Collectors.<Map.Entry<String, LifeInsurance>, String, ReadOnlyInsurance>toMap(
                i -> i.getKey(), i -> i.getValue()
            ));
        return lifeInsurances;
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
