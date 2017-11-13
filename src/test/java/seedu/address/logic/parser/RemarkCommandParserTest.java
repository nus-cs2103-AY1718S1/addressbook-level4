package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.REMARK_COFFEE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REMARK_COFFEE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;
//@@author April0616

public class RemarkCommandParserTest {
    private static final String REMARK_EMPTY = " " + PREFIX_REMARK;
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_remarkSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;

        //have remarks
        String userInput = targetIndex.getOneBased() + REMARK_EMPTY + VALID_REMARK_COFFEE;
        RemarkCommand expectedCommand = new RemarkCommand(targetIndex, REMARK_COFFEE);
        assertParseSuccess(parser, userInput, expectedCommand);

        //no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK;
        expectedCommand = new RemarkCommand(targetIndex, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
