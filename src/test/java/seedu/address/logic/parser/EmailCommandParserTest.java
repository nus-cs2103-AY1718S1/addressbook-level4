package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.Arrays;

import org.junit.Test;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.person.NameContainsKeywordsPredicate;

//@@author sidhmads
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the EmailCommand code. For example, inputs "to/adam" and "to/adam sam subject/hi body/cool" take the
 * same path through the EmailCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class EmailCommandParserTest {

    private EmailCommandParser parser = new EmailCommandParser();

    @Test
    public void parse_validArgs_returnsEmailCommand() {
        String[] val = new String[2];
        val[0] = "Adam";
        val[1] = "Sam";
        assertParseSuccess(parser, "email to/Adam Sam subject/hi body/cool",
                new EmailCommand((new NameContainsKeywordsPredicate(Arrays.asList(val))), "hi", "cool"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }
}

