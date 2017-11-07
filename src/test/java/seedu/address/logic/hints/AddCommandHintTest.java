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
        parseAndAssertHint(addCommandHint, " n/", "name", "add n/");
        addCommandHint = new AddCommandHint("add n/ ", " n/ ");
        parseAndAssertHint(addCommandHint, "p/", "phone", "add n/ p/");
        addCommandHint = new AddCommandHint("add n/nicholas p/321 e/email@e.com a/address",
                " n/nicholas p/321 e/email@e.com a/address");
        parseAndAssertHint(addCommandHint, " t/", "tag",
                "add n/nicholas p/321 e/email@e.com a/address t/");
        //prefix completion
        addCommandHint = new AddCommandHint("add n", " n");
        parseAndAssertHint(addCommandHint, "/", "name", "add n/");
        addCommandHint = new AddCommandHint("add p", " p");
        parseAndAssertHint(addCommandHint, "/", "phone", "add p/");
        addCommandHint = new AddCommandHint("add e", " e");
        parseAndAssertHint(addCommandHint, "/", "email", "add e/");

        //prefix cycle
        addCommandHint = new AddCommandHint("add i/", " i/");
        parseAndAssertHint(addCommandHint, "", "avatar file path", "add n/");
        addCommandHint = new AddCommandHint("add a/", " a/");
        parseAndAssertHint(addCommandHint, "", "address", "add t/");

        //exhausted all prefix
        addCommandHint = new AddCommandHint("add n/nicholas p/321 e/email@e.com a/address t/tag i/picture.png",
                " n/nicholas p/321 e/email@e.com a/address t/tag i/picture.png");
        parseAndAssertHint(addCommandHint, " ", "",
                "add n/nicholas p/321 e/email@e.com a/address t/tag i/picture.png ");
    }

    /**
     * parses {@code hint} and checks if the the hint generated has the expected fields
     */
    public static void parseAndAssertHint(Hint hint,
                                          String expectedArgumentHint,
                                          String expectedDescription,
                                          String expectedAutocomplete) {
        hint.parse();
        assertEquals(expectedArgumentHint, hint.getArgumentHint());
        assertEquals(expectedDescription, hint.getDescription());
        assertEquals(expectedAutocomplete, hint.autocomplete());
    }
}
