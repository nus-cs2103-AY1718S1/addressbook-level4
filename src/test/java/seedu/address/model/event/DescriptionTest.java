package seedu.address.model.event;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author kaiyu92
public class DescriptionTest {

    @Test
    public void isValidDescription() {
        // invalid description
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only
        assertFalse(Description.isValidDescription("~!?>")); //Symbols cannot be at the start

        // valid description
        assertTrue(Description.isValidDescription("IT Fair 2017 with many offers")); // alphabets only
        assertTrue(Description.isValidDescription("123456789")); // numbers only
        assertTrue(Description.isValidDescription(
                "This is a testing event to test to accept long description")); // long description
        assertTrue(Description.isValidDescription("Hello World with Test")); // with capital letters
        assertTrue(Description.isValidDescription("5top Test")); // alphanumeric characters
    }
}
