package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.commons.core.Messages.MESSAGE_INVALID_IMAGE_FORMAT;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.room.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.room.logic.commands.AddImageCommand;
import seedu.room.model.person.Picture;

//@@author shitian007
public class AddImageCommandParserTest {

    private AddImageCommandParser parser = new AddImageCommandParser();

    @Test
    public void parse_allFieldsValid_success() {
        String validInput = " " + INDEX_FIRST_PERSON.getOneBased() + " url/" + Picture.PLACEHOLDER_IMAGE;
        AddImageCommand expectedCommand = new AddImageCommand(INDEX_FIRST_PERSON, Picture.PLACEHOLDER_IMAGE);

        assertParseSuccess(parser, validInput, expectedCommand);
    }

    @Test
    public void parse_indexNonInteger_failure() {
        String invalidIndexArgs = "one url/" + Picture.PLACEHOLDER_IMAGE;
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddImageCommand.MESSAGE_USAGE);

        assertParseFailure(parser, invalidIndexArgs, expectedMessage);
    }

    @Test
    public void execute_invalidImageFormat_failure() throws Exception {
        String invalidImageFormatUrl = " " + INDEX_FIRST_PERSON.getOneBased() + " url/"
            + Picture.PLACEHOLDER_IMAGE + "g";
        String expectedMessage = String.format(MESSAGE_INVALID_IMAGE_FORMAT,
            AddImageCommand.MESSAGE_VALID_IMAGE_FORMATS);

        assertParseFailure(parser, invalidImageFormatUrl, expectedMessage);
    }

    @Test
    public void parse_invalidArgNumber_failure() {
        String invalidArgs = "1";
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddImageCommand.MESSAGE_USAGE);

        assertParseFailure(parser, invalidArgs, expectedMessage);
    }
}
