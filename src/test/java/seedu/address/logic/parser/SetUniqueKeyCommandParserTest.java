package seedu.address.logic.parser;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;
import seedu.address.logic.commands.SetUniqueKeyCommand;

//@@author Sri-vatsa
public class SetUniqueKeyCommandParserTest {
    private SetUniqueKeyCommandParser parser = new SetUniqueKeyCommandParser();

    @Test
    public void parse_accessCode_success() {
        String accessCode = "0/1e2345h78hy70";

        assertParseSuccess(parser, accessCode,
                new SetUniqueKeyCommand(accessCode));

    }

    @Test
    public void parse_accessCode_failure() {
        String accessCode = "gibberish";

        assertParseFailure(parser, accessCode, "Please make sure the access code you have copied "
                + "follows the format:\nDIGIT/ALPHANUMERICS");
    }

}