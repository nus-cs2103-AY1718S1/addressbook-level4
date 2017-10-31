package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AddRemarkCommand;
import seedu.address.model.person.Remark;

public class AddRemarkCommandParserTest {
    private AddRemarkCommandParser parser = new AddRemarkCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        ArrayList<Remark> remarksNothing = new ArrayList<>();
        remarksNothing.add(new Remark(""));

        //if no remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        AddRemarkCommand expectedCommand = new AddRemarkCommand(INDEX_FIRST_PERSON, remarksNothing);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRemarkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, AddRemarkCommand.COMMAND_WORD, expectedMessage);
    }
}
