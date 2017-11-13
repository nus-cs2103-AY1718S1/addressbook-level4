package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.NicknameCommand;
import seedu.address.model.person.Nickname;

//@@author chuaweiwen
public class NicknameCommandParserTest {

    private NicknameCommandParser parser = new NicknameCommandParser();

    @Test
    public void parse_validParts_returnsNicknameCommand() throws Exception {
        // Index and nickname included - nickname accepted
        assertParseSuccess(parser, "1 john",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("john")));

        // Index without the nickname - nickname as an empty string accepted
        assertParseSuccess(parser, "1",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("")));

        // Spaces before the index - nickname accepted
        assertParseSuccess(parser, "  1 john",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("john")));

        // Spaces between the index and nickname - nickname without spaces at the front accepted
        assertParseSuccess(parser, "1   john",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("john")));

        // Spaces within the nickname - nickname with the spaces accepted
        assertParseSuccess(parser, "1 john  peter",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("john  peter")));

        // Spaces after nickname - nickname without spaces at the back accepted
        assertParseSuccess(parser, "1 john peter  ",
                new NicknameCommand(INDEX_FIRST_PERSON, new Nickname("john peter")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws Exception {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NicknameCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_missingPartsFailure() throws Exception {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                NicknameCommand.MESSAGE_USAGE));
    }
}
//@@author
