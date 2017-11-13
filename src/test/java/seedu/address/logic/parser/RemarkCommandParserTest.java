package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

//@@author nahtanojmil
public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_indexSpecified_success() throws Exception {
        final Remark remarkString = new Remark("I'm so good");
        Index index;
        String userInput;

        //have remarks
        index = INDEX_FIRST_PERSON;
        userInput = index.getOneBased() + " " + PREFIX_REMARK.toString() + " " + remarkString;
        RemarkCommand testCommand = new RemarkCommand(index, remarkString);
        assertParseSuccess(parser, userInput, testCommand);

        //no remarks
        userInput = index.getOneBased() + " " + PREFIX_REMARK.toString();
        RemarkCommand nextTestCommand = new RemarkCommand(index, new Remark(""));
        assertParseSuccess(parser, userInput, nextTestCommand);
    }

    @Test
    public void parse_noSpecifiedField_failure() throws Exception {
        String errorMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, errorMessage);
    }
}
