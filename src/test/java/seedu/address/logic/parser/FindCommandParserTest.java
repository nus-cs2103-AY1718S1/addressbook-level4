package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindPersonDescriptor;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindPersonDescriptor personDescriptor = new FindPersonDescriptor();
        personDescriptor.setName("Alice Bob");
        personDescriptor.setAddress("Ang Mo Kio");
        FindCommand expectedFindCommand =
                new FindCommand(true, personDescriptor);
        assertParseSuccess(parser, "AND n/Alice Bob a/Ang Mo Kio", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " AND n/\n Alice \n \t Bob  \t a/Ang \n \t Mo Kio", expectedFindCommand);
    }
}
