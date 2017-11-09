package seedu.address.storage;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.relationship.ConfidenceEstimate;
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

    @XmlElement(required = true)
    private double confidenceEstimate;

    @XmlElement(required = true)
    private String name;

    /**
     * Constructs an XmlAdaptedRelationship.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRelationship() {}

    /**
     * Converts a given Relationship attributes into this class for JAXB use.
     */
    public XmlAdaptedRelationship(int fromIndex, int toIndex, RelationshipDirection direction,
                                  ConfidenceEstimate confidenceEstimate, Name name) {
        this.fromIndex = fromIndex;
        this.toIndex = toIndex;
        this.isDirected = direction.isDirected();
        this.confidenceEstimate = confidenceEstimate.value;
        this.name = name.fullName;
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
            fromPerson.addRelationship(new Relationship(fromPerson, toPerson, getRelationshipDirection(),
                    new Name(name), new ConfidenceEstimate(confidenceEstimate)));
        } catch (DuplicateRelationshipException dre) {
            throw new AssertionError("impossible to have duplicate relationships in storage", dre);
        } catch (IllegalValueException ive) {
            throw new AssertionError("impossible to have invalid values in storage", ive);
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
        if (isDirected != that.isDirected) {
            return false;
        }
        if (Double.compare(that.confidenceEstimate, confidenceEstimate) != 0) {
            return false;
        }
        return name.equals(that.name);
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = fromIndex;
        result = 31 * result + toIndex;
        result = 31 * result + (isDirected ? 1 : 0);
        temp = Double.doubleToLongBits(confidenceEstimate);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + name.hashCode();
        return result;
    }
}
