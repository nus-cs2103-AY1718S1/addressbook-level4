package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemoveRemarkCommand;

public class RemoveRemarkCommandParserTest {
    private RemoveRemarkCommandParser parser = new RemoveRemarkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        ArrayList<Integer> remarksIndexNothing = new ArrayList<>();
        remarksIndexNothing.add(1);

        //if no remark indexes
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + "1";
        RemoveRemarkCommand expectedCommand = new RemoveRemarkCommand(INDEX_FIRST_PERSON, remarksIndexNothing);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveRemarkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, RemoveRemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
