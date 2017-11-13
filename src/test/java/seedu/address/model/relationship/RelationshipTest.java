//@@author huiyiiih
package seedu.address.model.relationship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RelationshipTest {

    @Test
    public void isValidPriority() {
        // invalid relationship
        assertFalse(Relationship.isValidRelType("")); // empty string
        // input includes symbols other than square brackets and hyphens
        assertFalse(Relationship.isValidRelType("siblings!"));
    }
    @Test
    public void validPriority() {
        // valid relationship. Only alphabets, square brackets and hyphens
        assertTrue(Relationship.isValidRelType("siblings"));
        assertTrue(Relationship.isValidRelType("ex-colleague"));
    }
}
//@@author
