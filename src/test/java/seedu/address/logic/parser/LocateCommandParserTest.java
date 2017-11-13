//@@author majunting
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
//import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
//import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.LocateCommand;

public class LocateCommandParserTest {
    private LocateCommandParser parser = new LocateCommandParser();
    private String startAddress = "clementi street";

    @Test
    public void parse_invalidArgs_parseFail() {
        assertParseFailure(parser, "random",
                MESSAGE_INVALID_COMMAND_FORMAT + LocateCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1",
                MESSAGE_INVALID_COMMAND_FORMAT + LocateCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 t/",
                MESSAGE_INVALID_COMMAND_FORMAT + LocateCommand.MESSAGE_USAGE);
        assertParseFailure(parser, "1 a/",
                MESSAGE_INVALID_COMMAND_FORMAT + LocateCommand.MESSAGE_USAGE);
    }

    //@Test
    //public void parse_validArgs_parseSucceed() {
    //    assertParseSuccess(parser, "1 a/clementi street",
    //            new LocateCommand(INDEX_FIRST_PERSON, startAddress));
    //}
}
