package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.UnlockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class UnlockCommandParserTest {

    @Test
    public void test_parse() throws ParseException {
        UnlockCommand command;

        command = new UnlockCommandParser().parse("1234");
        assertTrue(command.equals(new UnlockCommand("1234")));

        command = new UnlockCommandParser().parse("abcd  ddd");
        assertTrue(command.equals(new UnlockCommand("abcd  ddd")));
    }

    @Test
    public void test_parse_throwsParseException() {

        assertTrue(isThrowsParseException(null));

        assertTrue(isThrowsParseException(""));

        assertTrue(isThrowsParseException("   "));

        assertTrue(isThrowsParseException("  a  "));

        assertTrue(isThrowsParseException("abc"));
    }

    /**
     * @return true if a {@code ParseException} is thrown
     */
    private boolean isThrowsParseException(String password) {
        try {
            new UnlockCommandParser().parse(password);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }
}
