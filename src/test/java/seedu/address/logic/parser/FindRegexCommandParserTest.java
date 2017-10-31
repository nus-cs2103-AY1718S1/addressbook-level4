// @@author donjar

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_REGEX;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FindRegexCommand;
import seedu.address.model.person.NameMatchesRegexPredicate;

public class FindRegexCommandParserTest {

    private FindRegexCommandParser parser = new FindRegexCommandParser();

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "(", String.format(MESSAGE_INVALID_REGEX, FindRegexCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindRegexCommand() {
        String[] regexesToTest = new String[] { "abcd", "a   b", "^ab$", "23(x)\\1" };
        for (String regex : regexesToTest) {
            FindRegexCommand expectedCommand = new FindRegexCommand(new NameMatchesRegexPredicate(regex));
            assertParseSuccess(parser, regex, expectedCommand);
        }
    }

}
