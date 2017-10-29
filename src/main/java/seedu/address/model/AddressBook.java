package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.model.util.DateUtil.formatDate;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.PaybackCommand;
import seedu.address.model.person.DateRepaid;
import seedu.address.model.person.Debt;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.UniquePersonList;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.tag.Tag;
import seedu.address.model.tag.UniqueTagList;
import seedu.address.model.tag.exceptions.TagNotFoundException;

/**
 * Wraps all data at the address-book level
 * Duplicates are not allowed (by .equals comparison)
 */
public class AddressBook implements ReadOnlyAddressBook {

    private final UniquePersonList persons;
    private final UniqueTagList tags;

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
     * Adds a person to the blacklist in the address book.
     * @return ReadOnly newBlacklistedPerson
     */
    public ReadOnlyPerson addBlacklistedPerson(ReadOnlyPerson p) {
        int index;
        index = persons.getIndexOf(p);

        Person newBlacklistedPerson = new Person(p);
        newBlacklistedPerson.setIsBlacklisted(true);
        try {
            updatePerson(p, newBlacklistedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The target person cannot be a duplicate");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("This is not possible as prior checks have been done");
        }

        return persons.getReadOnlyPerson(index);
    }

    /**
     * Adds a person to the whitelist in the address book.
     * @return ReadOnly newWhitelistedPerson
     */
    public ReadOnlyPerson addWhitelistedPerson(ReadOnlyPerson p) {
        int index;
        index = persons.getIndexOf(p);

        Person newWhitelistedPerson = new Person(p);
        newWhitelistedPerson.setIsWhitelisted(true);
        try {
            updatePerson(p, newWhitelistedPerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The target person cannot be a duplicate");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("This is not possible as prior checks have been done");
        }
        return persons.getReadOnlyPerson(index);
    }

    /**
     * Adds a person to the overdue debt list in the address book.
     * @return ReadOnly newOverduePerson
     */
    public ReadOnlyPerson addOverdueDebtPerson(ReadOnlyPerson p) {
        int index;
        index = persons.getIndexOf(p);

        Person newOverduePerson = new Person(p);
        newOverduePerson.setHasOverdueDebt(true);
        try {
            updatePerson(p, newOverduePerson);
        } catch (DuplicatePersonException e) {
            throw new AssertionError("The target person cannot be a duplicate");
        } catch (PersonNotFoundException e) {
            throw new AssertionError("This is not possible as prior checks have been done");
        }
        return persons.getReadOnlyPerson(index);
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
        return persons.remove(key);
    }

    /**
     * Updates {@code key} to exclude {@code key} from the blacklist in this {@code AddressBook}.
     * @return ReadOnly newUnBlacklistedPerson
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public ReadOnlyPerson removeBlacklistedPerson(ReadOnlyPerson key) throws PersonNotFoundException {
        int index;
        index = persons.getIndexOf(key);

        Person newUnBlacklistedPerson = new Person(key);
        newUnBlacklistedPerson.setIsBlacklisted(false);

        if (newUnBlacklistedPerson.getDebt().toNumber() == 0) {
            newUnBlacklistedPerson.setIsWhitelisted(true);
        }

        persons.remove(key);

        try {
            persons.add(index, newUnBlacklistedPerson);
        } catch (DuplicatePersonException e) {
            assert false : "This is not possible as prior checks have"
                    + " been done to ensure AddressBook does not have duplicate persons";
        }

        return persons.getReadOnlyPerson(index);
    }

    /**
     * Updates {@code key} to exclude {@code key} from the whitelist in this {@code AddressBook}.
     * @return ReadOnly newWhitelistedPerson
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public ReadOnlyPerson removeWhitelistedPerson(ReadOnlyPerson key) throws PersonNotFoundException {
        int index;
        index = persons.getIndexOf(key);

        Person newWhitelistedPerson = new Person(key);
        newWhitelistedPerson.setIsWhitelisted(false);
        persons.remove(key);
        try {
            persons.add(index, newWhitelistedPerson);
        } catch (DuplicatePersonException e) {
            assert false : "This is not possible as prior checks have"
                    + " been done to ensure AddressBook does not have duplicate persons";
        }
        return persons.getReadOnlyPerson(index);
    }

    /**
     * Updates {@code key} to exclude {@code key} from the overdue list in this {@code AddressBook}.
     * @return ReadOnly newOverdueDebtPerson
     * @throws PersonNotFoundException if the {@code key} is not in this {@code AddressBook}.
     */
    public ReadOnlyPerson removeOverdueDebtPerson(ReadOnlyPerson key) throws PersonNotFoundException {
        int index;
        index = persons.getIndexOf(key);

        Person newOverdueDebtPerson = new Person(key);
        newOverdueDebtPerson.setHasOverdueDebt(false);
        persons.remove(key);
        try {
            persons.add(index, newOverdueDebtPerson);
        } catch (DuplicatePersonException e) {
            assert false : "This is not possible as prior checks have"
                    + " been done to ensure AddressBook does not have duplicate persons";
        }
        return persons.getReadOnlyPerson(index);
    }

    //// tag-level operations

    /**
     * Adds a {@code Tag} to the tag list.
     * @param t the tag to be added.
     * @throws UniqueTagList.DuplicateTagException if the tag already exists.
     */
    public void addTag(Tag t) throws UniqueTagList.DuplicateTagException {
        tags.add(t);
    }

    /**
     * Removes a {@code Tag} from the tag list.
     * @param t the tag to be removed.
     * @throws TagNotFoundException if the tag does not exist.
     */
    public void removeTag(Tag t) throws TagNotFoundException {
        tags.remove(t);
    }

    //// sorting operations

    /**
     * Sorts the address book by given order
     */
    public void sortBy(String order) throws IllegalArgumentException {
        persons.sortBy(order);
    }


    //@@author jelneo
    /**
     * Increase debts of a person by the indicated amount
     * @param target person that borrowed more money
     * @param amount amount that the person borrowed. Must be either a positive integer or positive number with
     *               two decimal places
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     */
    public void addDebtToPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException {
        Person editedPerson = new Person(target);

        try {
            Debt newDebt = new Debt(target.getDebt().toNumber() + amount.toNumber());
            editedPerson.setDebt(newDebt);
            persons.setPerson(target, editedPerson);
        } catch (DuplicatePersonException dpe) {
            assert false : "There should be no duplicate when updating the debt of a person";
        } catch (IllegalValueException ive) {
            assert false : "New debt amount should not be invalid since amount and debt field in target have "
                    + "been validated";
        }
    }

    /**
     * Decrease the debt of a person by the amount indicated
     * @param target person in the address book who paid back some money
     * @param amount amount that the person paid back. Must be either a positive integer or positive number with
     *               two decimal places
     * @return ReadOnly editPerson
     * @throws PersonNotFoundException if {@code target} could not be found in the list.
     * @throws IllegalValueException if {@code amount} that is repaid by the person is more than the debt owed.
     */
    public ReadOnlyPerson deductDebtFromPerson(ReadOnlyPerson target, Debt amount) throws PersonNotFoundException,
            IllegalValueException {
        int index;
        index = persons.getIndexOf(target);

        Person editedPerson = new Person(target);
        double newDebtAmt = target.getDebt().toNumber() - amount.toNumber();

        if (newDebtAmt < 0) {
            throw new IllegalValueException(PaybackCommand.MESSAGE_PAYBACK_FAILURE);
        }

        try {
            Debt newDebt = new Debt(newDebtAmt);
            editedPerson.setDebt(newDebt);
            persons.setPerson(target, editedPerson);
        } catch (DuplicatePersonException dpe) {
            assert false : "There should be no duplicate when updating the debt of a person";
        } catch (IllegalValueException ive) {
            assert false : "New debt amount should not be invalid since amount and debt field in target have "
                    + "been validated";
        }

        return persons.getReadOnlyPerson(index);
    }
    //@@author

    /**
     * Resets person's debt field to zero, in the masterlist of the addressbook.
     * @return ReadOnly existingPerson
     * @throws PersonNotFoundException if person does not exist in list.
     */
    public ReadOnlyPerson resetPersonDebt(ReadOnlyPerson p) throws PersonNotFoundException {
        int index;
        index = persons.getIndexOf(p);

        Person existingPerson = new Person(p);
        try {
            existingPerson.setDebt(new Debt(Debt.DEBT_ZER0_VALUE));
        } catch (IllegalValueException e) {
            assert false : "The target value cannot be of illegal value";
        }

        persons.remove(p);

        try {
            persons.add(index, existingPerson);
        } catch (DuplicatePersonException dpe) {
            assert false : "There should be no duplicate when resetting the debt of a person";
        }

        return persons.getReadOnlyPerson(index);
    }

    /**
     * Resets person's {@code dateRepaid} field to current date, in the masterlist of the addressbook.
     * @return ReadOnly existingPerson
     * @throws PersonNotFoundException if person does not exist in list.
     */
    public ReadOnlyPerson setDateRepaid(ReadOnlyPerson p) throws PersonNotFoundException {
        int index;
        index = persons.getIndexOf(p);

        Person existingPerson = new Person(p);
        existingPerson.setDateRepaid(new DateRepaid(formatDate(new Date())));

        persons.remove(p);

        try {
            persons.add(index, existingPerson);
        } catch (DuplicatePersonException dpe) {
            assert false : "There should be no duplicate when resetting the date repaid field of a person";
        }

        return persons.getReadOnlyPerson(index);
    }

    //// util methods

    @Override
    public String toString() {
        return persons.asObservableList().size() + " persons, "
                + tags.asObservableList().size() +  " tags";
        // TODO: refine later
    }
    @Override
    public ObservableList<ReadOnlyPerson> getPersonList() {
        return persons.asObservableList();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getBlacklistedPersonList() {
        return persons.asObservableBlacklist();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getWhitelistedPersonList() {
        return persons.asObservableWhitelist();
    }

    @Override
    public ObservableList<ReadOnlyPerson> getOverduePersonList() {
        return persons.asObservableOverdueList();
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
