package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_STARTDATE_INTERNSHIP;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_NOT_URGENT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_URGENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.tasks.EditTaskCommand;
import seedu.address.logic.commands.tasks.TagTaskCommand;

public class TagTaskCommandParserTest {

    private static final String TAG_EMPTY = " " + PREFIX_TAG;

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagTaskCommand.MESSAGE_USAGE);

    private TagTaskCommandParser parser = new TagTaskCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TAG_URGENT, MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidIndex_failure() throws Exception {
        // negative index
        assertParseFailure(parser, "-5" + VALID_TAG_NOT_URGENT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + INVALID_TAG_DESC, MESSAGE_INVALID_FORMAT);
    }
}
