package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_REMARK;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.model.person.Remark;

public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parse_emptyStringInput_success() {


        //Define user inputs
        final String emptyRemark = "";
        Index thisIndex = INDEX_FIRST_PERSON;


        String userInput = thisIndex.getOneBased() + " " + PREFIX_REMARK.toString() + emptyRemark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(emptyRemark));
        assertParseSuccess(parser, userInput, expectedCommand);


        // no remarks
        userInput = thisIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_filledStringInput_success() {


        //Define user inputs
        final String emptyRemark = "I love Coffee";
        Index thisIndex = INDEX_FIRST_PERSON;


        String userInput = thisIndex.getOneBased() + " " + PREFIX_REMARK.toString() + emptyRemark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(emptyRemark));
        assertParseSuccess(parser, userInput, expectedCommand);


        // no remarks
        userInput = thisIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noIndexInput_failure() {

        final String emptyRemark = "I love Coffee";
        String userInput = PREFIX_REMARK.toString() + emptyRemark;

        String expectedCommand = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedCommand);

    }

    @Test
    public void parse_noPrefixInput_failure() {

        Index thisIndex = INDEX_FIRST_PERSON;
        String userInput = thisIndex.getOneBased() + " ";

        String expectedCommand = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedCommand);

    }
}
