package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.ToggleTagColorCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ToggleTagColorParserTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseTestInputs() throws Exception {

        ToggleTagColorParser parser = new ToggleTagColorParser();

        ToggleTagColorCommand expectedRandom = new ToggleTagColorCommand("random", null);
        ToggleTagColorCommand expectedOff = new ToggleTagColorCommand("off", null);
        ToggleTagColorCommand expectedDefault = new ToggleTagColorCommand("Test", "Test2");


        // Random keyword produces ToggleTagColorCommand(true, "", "")
        assertTrue(parser.parse(parser.getRandomKeyWord()).equals(expectedRandom));

        // Off keyword produces ToggleTagColorCommand (false, "", "")
        assertTrue(parser.parse(parser.getOffKeyWord()).equals(expectedOff));

        // Default case produces ToggleTagColorCommand (true, "args[0]", "args[1]")
        assertTrue(parser.parse("Test Test2").equals(expectedDefault));

        // Throw ArrayIndexOutOfBoundsException
        thrown.expect(ParseException.class);

        parser.parse("Test");
    }
}
