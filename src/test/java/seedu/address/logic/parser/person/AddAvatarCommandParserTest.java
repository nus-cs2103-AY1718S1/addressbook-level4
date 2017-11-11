package seedu.address.logic.parser.person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.util.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.person.Avatar.FILE_NOT_IMAGE;
import static seedu.address.model.person.Avatar.IMAGE_NOT_EXISTS;
import static seedu.address.model.person.Avatar.INVALID_PATH_MESSAGE;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.util.FileUtil;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.person.AddAvatarCommand;
import seedu.address.model.person.Avatar;

//@@author yunpengn
public class AddAvatarCommandParserTest {
    private static final String VALID_PATH = FileUtil.getPath("./src/test/resources/SampleAvatar.jpg");
    private static final String NOT_IMAGE_PATH = FileUtil.getPath("./src/test/resources/SampleNotImage.txt");
    private final AddAvatarCommandParser parser = new AddAvatarCommandParser();

    @Test
    public void parse_allFieldsPresent_checkCorrectness() throws Exception {
        String input = "1 " + VALID_PATH;
        Command expected = new AddAvatarCommand(Index.fromOneBased(1), new Avatar(VALID_PATH));
        assertParseSuccess(parser, input, expected);
    }

    @Test
    public void parse_fieldsMissing_expectException() throws Exception {
        String input = "1 ";
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddAvatarCommand.MESSAGE_USAGE);
        assertParseFailure(parser, input, message);
    }

    @Test
    public void parse_invalidIndex_expectException() throws Exception {
        String input = "-1 " + VALID_PATH;
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_INVALID_INDEX);
        assertParseFailure(parser, input, message);
    }

    @Test
    public void parse_invalidFilePath_expectException() throws Exception {
        String input = "1 " + "invalid*.jpg";
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, INVALID_PATH_MESSAGE);
        assertParseFailure(parser, input, message);
    }

    @Test
    public void parse_fileNotExist_expectException() throws Exception {
        String input = "1 " + "noSuchFile.jpg";
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, IMAGE_NOT_EXISTS);
        assertParseFailure(parser, input, message);
    }

    @Test
    public void parse_fileNotImage_expectException() throws Exception {
        String input = "1 " + NOT_IMAGE_PATH;
        String message = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FILE_NOT_IMAGE);
        assertParseFailure(parser, input, message);
    }
}
