package seedu.address.logic.parser;

import org.junit.Test;
import seedu.address.logic.commands.DeleteByNameCommand;

import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

public class DeleteByNameCommandParserTest {

    private DeleteByNameCommandParser parser = new DeleteByNameCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteByNameCommand() {
        assertParseSuccess(parser, "John Doe", new DeleteByNameCommand("John Doe"));
    }
}
