package seedu.address.model.event;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class TitleTest {

    @Test
   public void isValidTitleTest() {
        // invalid title
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only

        // valid title
        assertTrue(Title.isValidTitle("peter's birthday")); // alphabets only
        assertTrue(Title.isValidTitle("12345")); // numbers only
        assertTrue(Title.isValidTitle("nus 50th anniversary")); // alphanumeric characters
        assertTrue(Title.isValidTitle("CS2103 MIDTERM")); // with capital letters
        // long names with symbols
        assertTrue(Title.isValidTitle("National University of Singapore: 2020 Homecoming Graduation Ceremony"));
    }
}
