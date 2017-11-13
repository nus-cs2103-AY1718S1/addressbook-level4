package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.UploadPhotoCommand;
import seedu.address.model.person.Photo;

//@@author LuLechuan
public class UploadPhotoCommandParserTest {

    private UploadPhotoCommandParser parser = new UploadPhotoCommandParser();

    @Test
    public void parse_validArgs_returnsUploadPhotoCommand() throws IllegalValueException {
        assertParseSuccess(parser, "1", new UploadPhotoCommand(INDEX_FIRST_PERSON, new Photo()));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(
                MESSAGE_INVALID_COMMAND_FORMAT, UploadPhotoCommand.MESSAGE_USAGE));
    }
}
