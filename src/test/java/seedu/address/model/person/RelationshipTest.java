package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author Ernest
public class RelationshipTest {

    @Test
    public void equals() {
        Relationship relation = new Relationship("John Doe");

        // same object -> returns true
        assertTrue(relation.equals(relation));

        // same values -> returns true
        Relationship relationshipCopy = new Relationship(relation.value);
        assertTrue(relation.equals(relationshipCopy));

        // different types -> returns false
        assertFalse(relation.equals(1));

        // different person -> returns false
        Relationship differentRelationship = new Relationship("Mary Jane");
        assertFalse(relation.equals(differentRelationship));

        // null
        assertNotNull(relation);
    }
}
