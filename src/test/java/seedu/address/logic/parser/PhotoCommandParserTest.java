package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.PhotoCommand;

public class PhotoCommandParserTest {

    private PhotoCommandParser parser = new PhotoCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, PhotoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsPhotoCommand() {
        // no leading and trailing whitespaces
        PhotoCommand expectedPhotoCommand =
                new PhotoCommand(INDEX_FIRST_PERSON, "nus.jpg");
        assertParseSuccess(parser, "1 nus.jpg", expectedPhotoCommand);

    }

}
