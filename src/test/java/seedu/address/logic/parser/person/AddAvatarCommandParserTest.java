package seedu.address.logic.parser.person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.AVATAR_VALID_URL;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_URL_COMMA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.person.AddAvatarCommand;
import seedu.address.model.person.Avatar;

public class AddAvatarCommandParserTest {
    private AddAvatarCommandParser parser = new AddAvatarCommandParser();
    private String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAvatarCommand.MESSAGE_USAGE);

    @Test
    public void parse_allFieldsPresent_checkCorrectness() throws Exception {
        String input = "1 " + AVATAR_VALID_URL;
        Command expected = new AddAvatarCommand(Index.fromOneBased(1), new Avatar(AVATAR_VALID_URL));
        assertParseSuccess(parser, input, expected);
    }

    @Test
    public void parse_fieldsMissing_expectException() throws Exception {
        String input = "-1 ";
        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_invalidIndex_expectException() throws Exception {
        String input = "-1 " + AVATAR_VALID_URL;
        assertParseFailure(parser, input, expectedMessage);
    }

    @Test
    public void parse_invalidUrl_expectException() throws Exception {
        String input = "1 " + INVALID_URL_COMMA;
        assertParseFailure(parser, input, expectedMessage);
    }
}
