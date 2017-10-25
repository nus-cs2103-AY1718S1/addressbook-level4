package seedu.address.logic.parser;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.logic.commands.LockCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class LockCommandParserTest {

    @Test
    public void test_parse() throws ParseException {
        LockCommand command;

        command = new LockCommandParser().parse("1234");
        assertTrue(command.equals(new LockCommand("1234")));

        command = new LockCommandParser().parse("abcd  ddd");
        assertTrue(command.equals(new LockCommand("abcd  ddd")));
    }

    @Test
    public void test_parse_throwsParseException() {

        assertTrue(isThrowsParseException(null));

        assertTrue(isThrowsParseException(""));

        assertTrue(isThrowsParseException("  "));

        assertTrue(isThrowsParseException(" a  "));

        assertTrue(isThrowsParseException("123"));
    }

    /**
     * @return true if a {@code ParseException} is thrown
     */
    private boolean isThrowsParseException(String password) {
        try {
            new LockCommandParser().parse(password);
            return false;
        } catch (ParseException e) {
            return true;
        }
    }

}
