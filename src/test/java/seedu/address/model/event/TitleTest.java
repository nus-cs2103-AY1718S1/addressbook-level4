package seedu.address.model.event;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

//@@author kaiyu92
public class TitleTest {

    @Test
    public void isValidTitle() {
        // invalid title
        assertFalse(Title.isValidTitle("")); // empty string
        assertFalse(Title.isValidTitle(" ")); // spaces only
        assertFalse(Title.isValidTitle("~!?>")); //Symbols cannot be at the start

        // valid title
        assertTrue(Title.isValidTitle("Computing fair")); // alphabets only
        assertTrue(Title.isValidTitle("13580")); // numbers only
        assertTrue(Title.isValidTitle("Chinese New Year Hari Raya Deepavali")); // long title
        assertTrue(Title.isValidTitle("Sentosa Event")); // with capital letters
        assertTrue(Title.isValidTitle("Jay Chou 10th World Tour")); // alphanumeric characters
    }
}
