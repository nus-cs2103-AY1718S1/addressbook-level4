//@@author A0162268B
package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class DescriptionTest {

    @Test
    public void isValidDescriptionTest() {
        // invalid title
        assertFalse(Description.isValidDescription("")); // empty string
        assertFalse(Description.isValidDescription(" ")); // spaces only

        // valid title
        assertTrue(Description.isValidDescription("srgsrgdfh")); // alphabets only
        assertTrue(Description.isValidDescription("12345")); // numbers only
        assertTrue(Description.isValidDescription("sasdgsdg898 s9898")); // alphanumeric characters
        assertTrue(Description.isValidDescription("SGSDF839 SD928 92")); // with capital letters
        // long descriptions with symbols
        assertTrue(
                Description.isValidDescription("SDFIUSDFHIHI9839:2983983 2HIUDF938UN OJOFEIJ02JSLDLJDO90JS JOASDJ9"));
    }
}
