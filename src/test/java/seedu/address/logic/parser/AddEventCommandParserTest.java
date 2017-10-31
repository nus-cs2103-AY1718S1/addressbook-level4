/* Placeholder unit test
package seedu.address.logic.parser;


import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;


import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final String name = "Some name";
        final String date = "Some date";
        final String description = "Some text";

        // have description
        String userInput = AddEventCommand.COMMAND_WORD + " " + PREFIX_NAME + name + " "
                + PREFIX_DATE + date + " " + PREFIX_DESCRIPTION + description;
        AddEventCommand expectedCommand = new AddEventCommand(name, date, description);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no description
        userInput = AddEventCommand.COMMAND_WORD + " " + PREFIX_NAME + name + " " + PREFIX_DATE + " " + date;
        expectedCommand = new AddEventCommand(name, date, "");
        assertParseSuccess(parser, userInput, expectedCommand);


    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD, expectedMessage);
    }
}
*/
