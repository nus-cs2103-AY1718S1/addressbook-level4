//@@author viviantan95
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_URL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.URL_DESC_LOCAL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddPhotoCommand;
import seedu.address.model.person.Photo;

public class AddPhotoCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddPhotoCommand.MESSAGE_USAGE);

    private AddPhotoCommandParser parser = new AddPhotoCommandParser();

    @Test
    public void parse_invalidInfo_failure() {
        //zero index
        assertParseFailure(parser, "0" + URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        //negative index
        assertParseFailure(parser, "-1" + URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        //index containing chars
        assertParseFailure(parser, "test" + URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        //invalid Url
        assertParseFailure(parser, "1" + INVALID_URL_DESC, Photo.MESSAGE_PHOTO_CONSTRAINTS);
    }


    //Missing information when using addPhoto command -> failure
    @Test
    public void parse_missingInfo_failure() {
        //missing index
        assertParseFailure(parser, URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);


        //missing both index and URL
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

}
