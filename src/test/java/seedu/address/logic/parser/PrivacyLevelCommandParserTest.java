package seedu.address.logic.parser;
//@@author jeffreygohkw
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.PrivacyLevelCommand;

public class PrivacyLevelCommandParserTest {

    private PrivacyLevelCommandParser parser = new PrivacyLevelCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new PrivacyLevelCommand(INDEX_FIRST_PERSON.getOneBased()));
        assertParseSuccess(parser, "2", new PrivacyLevelCommand(INDEX_SECOND_PERSON.getOneBased()));
        assertParseSuccess(parser, "3", new PrivacyLevelCommand(INDEX_THIRD_PERSON.getOneBased()));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PrivacyLevelCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "???", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                PrivacyLevelCommand.MESSAGE_USAGE));

    }
}
