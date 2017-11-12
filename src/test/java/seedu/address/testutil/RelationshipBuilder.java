package seedu.address.testutil;

import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.relationship.ConfidenceEstimate;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.RelationshipDirection;

//@@author joanneong
/**
 * A utility class to help with building Relationships between two persons.
 */
public class RelationshipBuilder {

    private static final Person DEFAULT_FROM_PERSON = new PersonBuilder().build();
    private static final Person DEFAULT_TO_PERSON = new PersonBuilder().build();
    private static final RelationshipDirection DEFAULT_DIRECTION = RelationshipDirection.UNDIRECTED;

    Relationship relationship;

    public RelationshipBuilder() {
        this.relationship = new Relationship(DEFAULT_FROM_PERSON, DEFAULT_TO_PERSON, DEFAULT_DIRECTION);
    }

    public RelationshipBuilder(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson, RelationshipDirection direction) {
        this.relationship = new Relationship(fromPerson, toPerson, direction);
    }

    public RelationshipBuilder(ReadOnlyPerson fromPerson, ReadOnlyPerson toPerson, RelationshipDirection direction,
                               Name name, ConfidenceEstimate confidenceEstimate) {
        this.relationship = new Relationship(fromPerson, toPerson, direction, name, confidenceEstimate);
    }

    public Relationship getRelationship() {
        return relationship;
    }
}
