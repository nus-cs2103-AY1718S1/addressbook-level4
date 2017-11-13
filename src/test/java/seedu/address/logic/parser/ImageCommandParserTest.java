package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.ImageCommand;

//@@author liliwei25
public class ImageCommandParserTest {

    private static final String VALID_INPUT = "1";
    private static final String VALID_INPUT_REMOVE = "1 remove";
    private static final String INVALID_INDEX_INPUT = "a";
    private static final String INVALID_TYPE_INPUT = "1 edit";
    private static final boolean REMOVE = true;

    private ImageCommandParser parser = new ImageCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        // edit image
        assertParseSuccess(parser, VALID_INPUT, new ImageCommand(INDEX_FIRST_PERSON, !REMOVE));

        // remove image
        assertParseSuccess(parser, VALID_INPUT_REMOVE, new ImageCommand(INDEX_FIRST_PERSON, REMOVE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_INDEX_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE));

        assertParseFailure(parser, INVALID_TYPE_INPUT,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImageCommand.MESSAGE_USAGE));
    }
}
