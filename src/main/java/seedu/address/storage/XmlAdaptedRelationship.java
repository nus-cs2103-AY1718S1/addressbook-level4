package seedu.address.storage;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.RelationshipDirection;
import seedu.address.model.relationship.exceptions.DuplicateRelationshipException;

//@@author Xenonym
/**
 * JAXB-friendly adapted version of Relationship.
 */
public class XmlAdaptedRelationship {

    @XmlElement(required = true)
    private int fromIndex;

    @XmlElement(required = true)
    private int toIndex;

    @XmlElement(required = true)
    private boolean isDirected;

    /**
     * Constructs an XmlAdaptedRelationship.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRelationship() {}

    /**
     * Converts a given Relationship attributes into this class for JAXB use.
     */
    public XmlAdaptedRelationship(int fromIndex, int toIndex, RelationshipDirection direction) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.isDirected = direction.isDirected();
    }

    /**
     * Converts this jaxb-friendly adapted relationship object into the model's Relationship object,
     * then adds it into the model.
     * @param persons current Person list without relationships
     */
    public void addToModel(List<ReadOnlyPerson> persons) {
        Person fromPerson = (Person) persons.get(fromIndex);
        Person toPerson = (Person) persons.get(toIndex);

        try {
            fromPerson.addRelationship(new Relationship(fromPerson, toPerson, getRelationshipDirection()));
        } catch (DuplicateRelationshipException dre) {
            throw new AssertionError("impossible to have duplicate relationships in storage", dre);
        }

    }

    private RelationshipDirection getRelationshipDirection() {
        return isDirected ? RelationshipDirection.DIRECTED : RelationshipDirection.UNDIRECTED;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        XmlAdaptedRelationship that = (XmlAdaptedRelationship) o;

        if (fromIndex != that.fromIndex) {
            return false;
        }
        if (toIndex != that.toIndex) {
            return false;
        }
        return isDirected == that.isDirected;
    }

    @Override
    public int hashCode() {
        int result = fromIndex;
        result = 31 * result + toIndex;
        result = 31 * result + (isDirected ? 1 : 0);
        return result;
    }
}
