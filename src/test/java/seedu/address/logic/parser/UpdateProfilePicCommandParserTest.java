package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_URL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.URL_DESC_LOCAL;
import static seedu.address.logic.commands.CommandTestUtil.URL_DESC_WEB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCAL_IMAGE_URL;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WEB_IMAGE_URL;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.UpdateProfilePicCommand;
import seedu.address.model.person.ProfilePic;

//@@author soonwj
public class UpdateProfilePicCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, UpdateProfilePicCommand.MESSAGE_USAGE);

    private UpdateProfilePicCommandParser parser = new UpdateProfilePicCommandParser();

    @Test
    public void parse_missingParts_failure() {
        //no index specified
        assertParseFailure(parser, URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        //no url specified
        assertParseFailure(parser, "1", UpdateProfilePicCommand.MESSAGE_NOT_UPDATED);

        //no index or url specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + URL_DESC_LOCAL, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_URL_DESC, ProfilePic.MESSAGE_PROFILE_PIC_CONSTRAINTS); // invalid URL
    }

    @Test
    public void parse_validLocalUrl_success() throws Exception {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + URL_DESC_LOCAL;

        UpdateProfilePicCommand expectedCommand = new UpdateProfilePicCommand(targetIndex,
                new ProfilePic(VALID_LOCAL_IMAGE_URL));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_validWebUrl_success() throws Exception {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + URL_DESC_WEB;

        UpdateProfilePicCommand expectedCommand = new UpdateProfilePicCommand(targetIndex,
                new ProfilePic(VALID_WEB_IMAGE_URL));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws Exception {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + URL_DESC_LOCAL + URL_DESC_WEB;

        UpdateProfilePicCommand expectedCommand = new UpdateProfilePicCommand(targetIndex,
                new ProfilePic(VALID_WEB_IMAGE_URL));

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() throws Exception {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_URL_DESC + URL_DESC_WEB;

        UpdateProfilePicCommand expectedCommand = new UpdateProfilePicCommand(targetIndex,
                new ProfilePic(VALID_WEB_IMAGE_URL));
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
