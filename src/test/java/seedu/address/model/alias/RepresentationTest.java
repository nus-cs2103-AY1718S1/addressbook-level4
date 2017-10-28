package seedu.address.model.alias;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RepresentationTest {

    @Test
    public void isValidRepresentation() {
        // invalid representation
        assertFalse(Representation.isValidRepresentation("")); // representation cannot be empty

        // valid representation
        assertTrue(Representation.isValidRepresentation("anything")); // any String that is not empty

    }
}
