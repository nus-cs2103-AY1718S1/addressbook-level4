//@@author tbhbhbh
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SocialCommand;

public class SocialCommandParserTest {

    private SocialCommandParser parser = new SocialCommandParser();


    @Test
    public void parse_validArgs_returnsSocialCommand() {
        assertParseSuccess(parser, "1 ig", prepareCommand(INDEX_FIRST_PERSON, "ig"));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "",
                String.format((MESSAGE_INVALID_COMMAND_FORMAT + SocialCommand.MESSAGE_USAGE), ""));
    }

    /**
     * Returns a {@code SocialCommand} with the parameters {@code index and socialMedia}.
     */
    private SocialCommand prepareCommand(Index index, String socialMedia) {

        SocialCommand socialCommand = new SocialCommand(index, socialMedia);

        return socialCommand;
    }

}
