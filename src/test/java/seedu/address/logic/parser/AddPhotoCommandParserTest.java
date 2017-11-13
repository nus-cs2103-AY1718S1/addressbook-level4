//@@author wishingmaid
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddPhotoCommand;

public class AddPhotoCommandParserTest {
    private AddPhotoCommandParser parser = new AddPhotoCommandParser();
    @Test
    public void parse_missingFields_failure() throws Exception {
        //fails because there is no index and prefix specified
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE);
        assertParseFailure(parser, AddPhotoCommand.COMMAND_WORD, expectedMessage);
        //fails because there is no index specified
        String expectedMessageNoIndex = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE);
        String userInput = AddPhotoCommand.COMMAND_WORD + " " + PREFIX_FILEPATH.toString()
                + "src/main/resources/images/address.png";
        assertParseFailure(parser, userInput, expectedMessageNoIndex);
    }
}

