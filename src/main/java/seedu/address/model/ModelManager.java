package seedu.address.model;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.logging.Logger;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import seedu.address.commons.core.ComponentManager;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.person.exceptions.DuplicatePersonException;
import seedu.address.model.person.exceptions.PersonNotFoundException;
import seedu.address.model.person.exceptions.RelationshipNotFoundException;
import seedu.address.model.person.exceptions.TagNotFoundException;
import seedu.address.model.relationship.ConfidenceEstimate;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.RelationshipDirection;
import seedu.address.model.relationship.exceptions.DuplicateRelationshipException;

/**
 * Represents the in-memory model of the address book data.
 * All changes to any model should be synchronized.
 */
public class ModelManager extends ComponentManager implements Model {
    private static final Logger logger = LogsCenter.getLogger(ModelManager.class);

    private final AddressBook addressBook;
    private final UserPrefs userPrefs;
    private final FilteredList<ReadOnlyPerson> filteredPersons;

    /**
     * Initializes a ModelManager with the given addressBook and userPrefs.
     */
    public ModelManager(ReadOnlyAddressBook addressBook, UserPrefs userPrefs) {
        super();
        requireAllNonNull(addressBook, userPrefs);

        logger.fine("Initializing with address book: " + addressBook + " and user prefs " + userPrefs);

        this.addressBook = new AddressBook(addressBook);
        this.userPrefs = userPrefs;
        filteredPersons = new FilteredList<>(this.addressBook.getPersonList());
    }

    public ModelManager() {
        this(new AddressBook(), new UserPrefs());
    }

    @Override
    public void resetData(ReadOnlyAddressBook newData) {
        addressBook.resetData(newData);
        indicateAddressBookChanged();
    }

    @Override
    public ReadOnlyAddressBook getAddressBook() {
        return addressBook;
    }

    //@@author Xenonym
    public UserPrefs getUserPrefs() {
        return userPrefs;
    }
    //@@author

    /** Raises an event to indicate the model has changed */
    private void indicateAddressBookChanged() {
        raise(new AddressBookChangedEvent(addressBook));
    }

    @Override
    public synchronized void deletePerson(ReadOnlyPerson target) throws PersonNotFoundException {
        addressBook.removePerson(target);
        indicateAddressBookChanged();
    }

    @Override
    public synchronized void addPerson(ReadOnlyPerson person) throws DuplicatePersonException {
        addressBook.addPerson(person);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void updatePerson(ReadOnlyPerson target, ReadOnlyPerson editedPerson)
            throws DuplicatePersonException, PersonNotFoundException {
        requireAllNonNull(target, editedPerson);

        addressBook.updatePerson(target, editedPerson);
        indicateAddressBookChanged();
    }

    @Override
    public void sortPersons() {
        addressBook.sortData();
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    //@@author wenmogu
    /**
     * Removes a tag with the tagGettingRemoved string
     * @param tagGettingRemoved
     * @throws TagNotFoundException
     * @throws IllegalValueException
     */
    public void removeTag(String tagGettingRemoved) throws TagNotFoundException, IllegalValueException {
        addressBook.removeTag(tagGettingRemoved);
        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        indicateAddressBookChanged();
    }

    @Override
    public void addRelationship(Index indexFromPerson, Index indexToPerson, RelationshipDirection direction,
                                Name name, ConfidenceEstimate confidenceEstimate)
            throws IllegalValueException, DuplicateRelationshipException {
        List<ReadOnlyPerson> lastShownList = getFilteredPersonList();

        if (indexFromPerson.getZeroBased() >= lastShownList.size()
                || indexToPerson.getZeroBased() >= lastShownList.size()
                || indexFromPerson.getZeroBased() < 0
                || indexToPerson.getZeroBased() < 0) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson fromPerson = lastShownList.get(indexFromPerson.getZeroBased());
        ReadOnlyPerson toPerson = lastShownList.get(indexToPerson.getZeroBased());
        ReadOnlyPerson fromPersonCopy = fromPerson.copy();
        ReadOnlyPerson toPersonCopy = toPerson.copy();
        Relationship relationshipForFromPerson = new Relationship(fromPersonCopy, toPersonCopy, direction,
                name, confidenceEstimate);
        Relationship relationshipForToPerson = relationshipForFromPerson;
        if (!direction.isDirected()) {
            relationshipForToPerson = new Relationship(toPersonCopy, fromPersonCopy, direction,
                    name, confidenceEstimate);
        }


        /*
         Updating the model
         */
        try {
            Person fPerson = (Person) fromPersonCopy;
            Person tPerson = (Person) toPersonCopy;
            fPerson.addRelationship(relationshipForFromPerson);
            tPerson.addRelationship(relationshipForToPerson);
            this.updatePerson(fromPerson, fPerson);
            this.updatePerson(toPerson, tPerson);
        } catch (DuplicateRelationshipException dre) {
            throw new DuplicateRelationshipException();
        } catch (DuplicatePersonException dpe) {
            throw new AssertionError("the person's relationship is unmodified. IMPOSSIBLE.");
        } catch (PersonNotFoundException pnfe) {
            throw new AssertionError("The target person cannot be missing");
        }

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public void deleteRelationship(Index indexFromPerson, Index indexToPerson) throws IllegalValueException {
        List<ReadOnlyPerson> lastShownList = getFilteredPersonList();

        if (indexFromPerson.getZeroBased() >= lastShownList.size()
                || indexToPerson.getZeroBased() >= lastShownList.size()) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson fromPerson = lastShownList.get(indexFromPerson.getZeroBased());
        ReadOnlyPerson toPerson = lastShownList.get(indexToPerson.getZeroBased());

        Relationship relationshipToDelete1 = new Relationship(fromPerson, toPerson, RelationshipDirection.UNDIRECTED);
        Relationship relationshipToDelete2 = new Relationship(fromPerson, toPerson, RelationshipDirection.DIRECTED);

        Person fromPersonCasting = (Person) fromPerson;
        Person toPersonCasting = (Person) toPerson;
        fromPersonCasting.removeRelationship(relationshipToDelete1);
        toPersonCasting.removeRelationship(relationshipToDelete1);

        fromPersonCasting.removeRelationship(relationshipToDelete2);
        toPersonCasting.removeRelationship(relationshipToDelete2);

        updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    //@@author joanneong

    /**
     * Edits a relationship between two persons by updating the name and confidence estimate of the relationship.
     *
     * Note that this edit is actually done by removing the relationship and constructing a new relationship with
     * the new name and confidence estimate.
     */
    public void editRelationship(Index indexFromPerson, Index indexToPerson, Name name,
                                 ConfidenceEstimate confidenceEstimate)
            throws DuplicateRelationshipException, RelationshipNotFoundException, IllegalValueException {
        List<ReadOnlyPerson> lastShownList = getFilteredPersonList();

        if (indexFromPerson.getZeroBased() >= lastShownList.size()
                || indexToPerson.getZeroBased() >= lastShownList.size()
                || indexFromPerson.getZeroBased() < 0
                || indexToPerson.getZeroBased() < 0) {
            throw new IllegalValueException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson fromPerson = lastShownList.get(indexFromPerson.getZeroBased());
        ReadOnlyPerson toPerson = lastShownList.get(indexToPerson.getZeroBased());
        Person fromPersonCasting = (Person) fromPerson;
        Person toPersonCasting = (Person) toPerson;

        Relationship undirectedRelationshipToFind = new Relationship(fromPerson, toPerson,
                RelationshipDirection.UNDIRECTED);
        Relationship directedRelationshipToFind = new Relationship(fromPerson, toPerson,
                RelationshipDirection.DIRECTED);
        Relationship alternativeDirectedRelationshipToFind = new Relationship(toPerson, fromPerson,
                RelationshipDirection.DIRECTED);

        // Check whether the original relationship was directed or undirected since direction is preserved
        Set<Relationship> fromPersonRelationships = fromPerson.getRelationships();
        Relationship oldRelationship = undirectedRelationshipToFind;
        Relationship newRelationship = undirectedRelationshipToFind;
        Name newName = name;
        ConfidenceEstimate newConfidenceEstimate = confidenceEstimate;

        boolean foundRelationship = false;

        for (Relationship fromPersonRelationship : fromPersonRelationships) {
            if (fromPersonRelationship.equals(undirectedRelationshipToFind)) {
                oldRelationship = undirectedRelationshipToFind;
                newRelationship = constructUpdatedRelationship(fromPerson, toPerson, RelationshipDirection.UNDIRECTED,
                        newName, newConfidenceEstimate, fromPersonRelationship);
                foundRelationship = true;
                break;
            } else if (fromPersonRelationship.equals(directedRelationshipToFind)) {
                oldRelationship = directedRelationshipToFind;
                newRelationship = constructUpdatedRelationship(fromPerson, toPerson, RelationshipDirection.DIRECTED,
                        newName, newConfidenceEstimate, fromPersonRelationship);
                foundRelationship = true;
                break;
            } else if (fromPersonRelationship.equals(alternativeDirectedRelationshipToFind)) {
                oldRelationship = alternativeDirectedRelationshipToFind;
                newRelationship = constructUpdatedRelationship(toPerson, fromPerson, RelationshipDirection.DIRECTED,
                        newName, newConfidenceEstimate, fromPersonRelationship);
                foundRelationship = true;
                break;
            }
        }

        if (foundRelationship) {
            fromPersonCasting.removeRelationship(oldRelationship);
            toPersonCasting.removeRelationship(oldRelationship);

            fromPersonCasting.addRelationship(newRelationship);
            toPersonCasting.addRelationship(newRelationship);

            updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
            indicateAddressBookChanged();
        } else {
            throw new RelationshipNotFoundException("This relationship does not exist!");
        }
    }

    /**
     * Constructs a new relationship between two persons with the name and the confidence estimate
     * provided.
     *
     * If there are no name/confidence estimate provided, the relationship will retain the name/
     * confidence estimate of the original (pre-edited) relationship.
     */
    private Relationship constructUpdatedRelationship(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson,
                                                      RelationshipDirection relationshipDirection, Name name,
                                                      ConfidenceEstimate confidenceEstimate,
                                                      Relationship fromPersonRelationship) {

        boolean hasNewName = !name.equals(Name.UNSPECIFIED);
        boolean hasNewConfidenceEstimate = !confidenceEstimate.equals(ConfidenceEstimate.UNSPECIFIED);
        Name newName = name;
        ConfidenceEstimate newConfidenceEstimate = confidenceEstimate;

        if (!hasNewName) {
            newName = fromPersonRelationship.getName();
        }
        if (!hasNewConfidenceEstimate) {
            newConfidenceEstimate = fromPersonRelationship.getConfidenceEstimate();
        }

        return new Relationship(fromPerson, toPerson, relationshipDirection, newName, newConfidenceEstimate);
    }

    //=========== Filtered Person List Accessors =============================================================

    //@@author
    /**
     * Returns an unmodifiable view of the list of {@code ReadOnlyPerson} backed by the internal list of
     * {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyPerson> getFilteredPersonList() {
        return FXCollections.unmodifiableObservableList(filteredPersons);
    }

    @Override
    public void updateFilteredPersonList(Predicate<ReadOnlyPerson> predicate) {
        requireNonNull(predicate);
        filteredPersons.setPredicate(predicate);
    }

    @Override
    public boolean equals(Object obj) {
        // short circuit if same object
        if (obj == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(obj instanceof ModelManager)) {
            return false;
        }

        // state check
        ModelManager other = (ModelManager) obj;
        return addressBook.equals(other.addressBook)
                && filteredPersons.equals(other.filteredPersons);
    }

}
