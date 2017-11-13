//@@author zenghou
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_validArgs_returnsRemarkCommand() {
        // with remark
        RemarkCommand rmkCmd = new RemarkCommand(INDEX_FIRST_PERSON, new Remark("Likes to drink coffee"));
        assertParseSuccess(parser, "1 " + PREFIX_REMARK + "Likes to drink coffee", rmkCmd);

        // without remark
        assertParseSuccess(parser, "1 " + PREFIX_REMARK, new RemarkCommand(INDEX_FIRST_PERSON,
                new Remark("")));
    }

    @Test
    public void parse_invalidPreamble_throwsParseException() {
        // negative index
        assertParseFailure(parser, "-1" + PREFIX_REMARK + "Likes to drink coffee",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));

        // zero index
        assertParseFailure(parser, "0" + PREFIX_REMARK + "Likes to drink coffee",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));

        // no index
        assertParseFailure(parser,  PREFIX_REMARK + "Likes to drink coffee",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));

        // alphabetical index
        assertParseFailure(parser, "a" + PREFIX_REMARK + "Likes to drink coffee",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE));

        // invalid prefix
        assertParseFailure(parser, "1 /re Likes to drink coffee", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                RemarkCommand.MESSAGE_USAGE));
    }
}
