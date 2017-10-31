package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.UploadPhotoCommand;

//@@author JasmineSee
public class UploadPhotoCommandParserTest {
    private UploadPhotoCommandParser parser = new UploadPhotoCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "asasd",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UploadPhotoCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsUploadPhotoCommand() {
        // valid index,no file path specified
        UploadPhotoCommand expectedCommand = new UploadPhotoCommand(INDEX_FIRST_PERSON, "");
        assertParseSuccess(parser, "1", expectedCommand);

        // valid index and file path
        expectedCommand = new UploadPhotoCommand(INDEX_FIRST_PERSON,
                ".\\src\\test\\resources\\photos\\connectus_icon.png");
        assertParseSuccess(parser, "1 .\\src\\test\\resources\\photos\\connectus_icon.png", expectedCommand);
    }
}
