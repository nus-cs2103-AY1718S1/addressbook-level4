//@@author arturs68
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.model.util.SampleDataUtil.SAMPLE_PICTURE;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ChangePicCommand;

public class ChangePicCommandParserTest {
    private ChangePicCommandParser parser = new ChangePicCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final String picturePath = SAMPLE_PICTURE;

        // has picturePath
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_PATH.toString() + " " + picturePath;
        ChangePicCommand expectedCommand = new ChangePicCommand(INDEX_FIRST_PERSON, picturePath);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no picturePath specified
        /*userInput = targetIndex.getOneBased() + " " + PREFIX_PATH.toString();
        expectedCommand = new ChangePicCommand(INDEX_FIRST_PERSON, "");
        assertParseFailure(parser, userInput, expectedCommand); */
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ChangePicCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, ChangePicCommand.COMMAND_WORD, expectedMessage);
    }
}
