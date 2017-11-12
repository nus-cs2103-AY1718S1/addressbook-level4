package seedu.address.relationship;

import static org.junit.Assert.assertTrue;
import static org.junit.Assert.assertFalse;

import org.junit.Test;

import seedu.address.model.person.Name;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.relationship.ConfidenceEstimate;
import seedu.address.model.relationship.Relationship;
import seedu.address.model.relationship.RelationshipDirection;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.RelationshipBuilder;

//@@author joanneong
public class RelationshipTest {

    @Test
    public void equals() {
        Relationship relationshipOne = new RelationshipBuilder().getRelationship();
        ReadOnlyPerson fromPerson = relationshipOne.getFromPerson();
        ReadOnlyPerson toPerson = relationshipOne.getToPerson();
        RelationshipDirection relationshipDirection = relationshipOne.getDirection();
        Name name = relationshipOne.getName();
        ConfidenceEstimate confidenceEstimate = relationshipOne.getConfidenceEstimate();

        ReadOnlyPerson differentFromPerson = new PersonBuilder().withName("Alice").build();
        ReadOnlyPerson differentToPerson = new PersonBuilder().withName("Bob").build();
        RelationshipDirection differentRelationshipDirection = RelationshipDirection.DIRECTED;

        // same object -> returns true
        assertTrue(relationshipOne.equals(relationshipOne));

        // same from person, to person, and direction -> returns true
        Relationship relationshipTwo = new RelationshipBuilder(fromPerson, toPerson,
                relationshipDirection).getRelationship();
        assertTrue(relationshipOne.equals(relationshipTwo));

        // same from person, to person, direction, name, and confidence estimate -> returns true
        Relationship relationshipThree = new RelationshipBuilder(fromPerson, toPerson, relationshipDirection,
                name, confidenceEstimate).getRelationship();
        assertTrue(relationshipOne.equals(relationshipThree));

        // null -> returns false
        assertFalse(relationshipOne.equals(null));

        // different types -> returns false
        assertFalse(relationshipOne.equals("123"));

        // different from person -> returns false
        Relationship relationshipFour = new RelationshipBuilder(differentFromPerson, toPerson, relationshipDirection,
                name, confidenceEstimate).getRelationship();
        assertFalse(relationshipOne.equals(relationshipFour));

        // different to person -> returns false
        Relationship relationshipFive = new RelationshipBuilder(fromPerson, differentToPerson, relationshipDirection,
                name, confidenceEstimate).getRelationship();
        assertFalse(relationshipOne.equals(relationshipFive));

        // different direction -> returns false
        Relationship relationshipSix = new RelationshipBuilder(fromPerson, toPerson, differentRelationshipDirection,
                name, confidenceEstimate).getRelationship();
        assertFalse(relationshipOne.equals(relationshipSix));
    }

}
