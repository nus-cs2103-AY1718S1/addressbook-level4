package seedu.address.model.relationship;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.commons.util.CollectionUtil;
import seedu.address.model.person.exceptions.RelationshipNotFoundException;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;

public class UniqueRelationshipList implements Iterable<Relationship> {

    private final ObservableList<Relationship> internalList = FXCollections.observableArrayList();

    /**
     * Constructs empty RelationshipList.
     */
    public UniqueRelationshipList() {}

    /**
     * Creates a UniqueRelationshipList using given relationships.
     * Enforces no nulls.
     */
    public UniqueRelationshipList(Set<Relationship> relationships) {
        requireAllNonNull(relationships);
        internalList.addAll(relationships);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns all relationships in this list as a Set.
     * This set is mutable and change-insulated against the internal list.
     */
    public Set<Relationship> toSet() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return new HashSet<>(internalList);
    }

    /**
     * Replaces the Relationships in this list with those in the argument relationship list.
     */
    public void setRelationships(Set<Relationship> relationships) {
        requireAllNonNull(relationships);
        internalList.setAll(relationships);
        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Ensures every relationship in the argument list exists in this object.
     */
    public void mergeFrom(UniqueRelationshipList from) {
        final Set<Relationship> alreadyInside = this.toSet();
        from.internalList.stream()
                .filter(relationship -> !alreadyInside.contains(relationship))
                .forEach(internalList::add);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Returns true if the list contains an equivalent Relationship as the given argument.
     */
    public boolean contains(Relationship toCheck) {
        requireNonNull(toCheck);
        return internalList.contains(toCheck);
    }

    /**
     * If the tag name is valid and the tag present in the list.
     * @param relationshipID
     * @return the index of the tag in the list
     * @throws IllegalValueException
     */

    public int indexOfRelationshipWithRelationshipID(String relationshipID) throws IllegalValueException {
        requireNonNull(relationshipID);
        Relationship temp = new Relationship(relationshipID);
        return internalList.indexOf(temp);
    }

    /**
     * Adds a Relationship to the list.
     * @throws DuplicateRelationshipException if the Relationship to add is a duplicate
     * of an existing Relationship in the list.
     */
    public void add(Relationship toAdd) throws DuplicateRelationshipException {
        requireNonNull(toAdd);
        if (contains(toAdd)) {
            throw new DuplicateRelationshipException();
        }
        internalList.add(toAdd);

        assert CollectionUtil.elementsAreUnique(internalList);
    }

    /**
     * Remove a Relationship from the list. The Relationship is identified by its ID.
     *
     * throws RelationshipNotFoundException if the Relationship to remove is not found in the list.
     * throws IllegalValueException if the Relationship name input is invalid.
     */
    public Relationship removeRelationship(String relationshipGettingRemoved) throws RelationshipNotFoundException,
            IllegalValueException {
        requireNonNull(relationshipGettingRemoved);
        int relationshipIndexInList = indexOfRelationshipWithRelationshipID(relationshipGettingRemoved);
        if (relationshipIndexInList == -1) {
            throw new RelationshipNotFoundException("Relationship is not found.");
        } else {
            return internalList.remove(relationshipIndexInList);
        }
    }

    /**
     * Remove a Relationship from the list.
     * @param relationshipGettingRemoved
     */
    public boolean removeRelationship(Relationship relationshipGettingRemoved) {
        requireNonNull(relationshipGettingRemoved);
        return internalList.remove(relationshipGettingRemoved);
    }

    @Override
    public Iterator<Relationship> iterator() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.iterator();
    }

    /**
     * Returns the backing list as an unmodifiable {@code ObservableList}.
     */
    public ObservableList<Relationship> asObservableList() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return FXCollections.unmodifiableObservableList(internalList);
    }

    @Override
    public boolean equals(Object other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        return other == this // short circuit if same object
                || (other instanceof UniqueRelationshipList // instanceof handles nulls
                && this.internalList.equals(((UniqueRelationshipList) other).internalList));
    }

    /**
     * Returns true if the element in this list is equal to the elements in {@code other}.
     * The elements do not have to be in the same order.
     */
    public boolean equalsOrderInsensitive(UniqueRelationshipList other) {
        assert CollectionUtil.elementsAreUnique(internalList);
        assert CollectionUtil.elementsAreUnique(other.internalList);
        return this == other || new HashSet<>(this.internalList).equals(new HashSet<>(other.internalList));
    }

    @Override
    public int hashCode() {
        assert CollectionUtil.elementsAreUnique(internalList);
        return internalList.hashCode();
    }
}
