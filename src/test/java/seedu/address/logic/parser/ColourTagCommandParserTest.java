package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ColourTagCommand;
import seedu.address.model.tag.Tag;

//@@author Xenonym
public class ColourTagCommandParserTest {

    private ColourTagCommandParser parser = new ColourTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ColourTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "onlyonearg", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ColourTagCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "&&@&C@B invalidtag", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ColourTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsColourTagCommand() throws Exception {
        assertParseSuccess(parser, "friends red", new ColourTagCommand(new Tag("friends"), "red"));
    }
}
