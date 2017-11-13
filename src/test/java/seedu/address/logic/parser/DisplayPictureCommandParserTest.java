//@@author tshradheya
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DisplayPictureCommand;
import seedu.address.model.person.DisplayPicture;

public class DisplayPictureCommandParserTest {

    private DisplayPictureCommandParser parser = new DisplayPictureCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final DisplayPicture displayPicture = new DisplayPicture("somepath");


        Index targetIndex = INDEX_FIRST_PERSON;

        // file path present
        String userInput = targetIndex.getOneBased() + " " +  "somepath";
        DisplayPictureCommand expectedCommand = new DisplayPictureCommand(INDEX_FIRST_PERSON,
                new DisplayPicture(displayPicture.getPath()));
        assertParseSuccess(parser, userInput, expectedCommand);

        //no file path
        userInput = targetIndex.getOneBased() + " ";
        expectedCommand = new DisplayPictureCommand(INDEX_FIRST_PERSON,
                new DisplayPicture(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisplayPictureCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, DisplayPictureCommand.COMMAND_WORD, expectedMessage);
    }
}
