package seedu.address.logic.hints;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.logic.commands.hints.AddCommandHint;
import seedu.address.logic.commands.hints.Hint;

public class AddCommandHintTest {

    @Test
    public void parseTest() {
        //offer hint
        AddCommandHint addCommandHint = new AddCommandHint("add", "");
        assertHintContent(addCommandHint, " n/", "name", "add n/");
        addCommandHint = new AddCommandHint("add n/ ", " n/ ");
        assertHintContent(addCommandHint, "p/", "phone", "add n/ p/");
        addCommandHint = new AddCommandHint("add n/nicholas p/321 e/email@e.com a/address",
                " n/nicholas p/321 e/email@e.com a/address");
        assertHintContent(addCommandHint, " r/", "remark (optional)",
                "add n/nicholas p/321 e/email@e.com a/address r/");
        //prefix completion
        addCommandHint = new AddCommandHint("add n", " n");
        assertHintContent(addCommandHint, "/", "name", "add n/");
        addCommandHint = new AddCommandHint("add p", " p");
        assertHintContent(addCommandHint, "/", "phone", "add p/");
        addCommandHint = new AddCommandHint("add e", " e");
        assertHintContent(addCommandHint, "/", "email", "add e/");

        //prefix cycle
        addCommandHint = new AddCommandHint("add i/", " i/");
        assertHintContent(addCommandHint, "", "avatar file path (optional)", "add n/");
        addCommandHint = new AddCommandHint("add a/", " a/");
        assertHintContent(addCommandHint, "", "address", "add r/");

        //exhausted all prefix
        addCommandHint = new AddCommandHint("add n/nicholas p/321 e/email@e.com a/address t/tag i/picture.png r/remark",
                " n/nicholas p/321 e/email@e.com a/address t/tag i/picture.png r/remark");
        assertHintContent(addCommandHint, " ", "",
                "add n/nicholas p/321 e/email@e.com a/address t/tag i/picture.png r/remark ");
    }

    /**
     * parses {@code hint} and checks if the the hint generated has the expected fields
     */
    public static void assertHintContent(Hint hint,
                                         String expectedArgumentHint,
                                         String expectedDescription,
                                         String expectedAutocomplete) {

        assertEquals(expectedArgumentHint, hint.getArgumentHint());
        assertEquals(expectedDescription, hint.getDescription());
        assertEquals(expectedAutocomplete, hint.autocomplete());
    }
}
