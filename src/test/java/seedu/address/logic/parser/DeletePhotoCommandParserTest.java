package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DeletePhotoCommand;

//@@author JasmineSee
public class DeletePhotoCommandParserTest {
    private DeletePhotoCommandParser parser = new DeletePhotoCommandParser();

    @Test
    public void parse_validArgs_returnsDeletePhotoCommand() {
        assertParseSuccess(parser, "1", new DeletePhotoCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeletePhotoCommand.MESSAGE_USAGE));
    }
}
