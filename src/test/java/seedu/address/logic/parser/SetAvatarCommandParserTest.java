package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_AVATAR_IMAGE_URL_ONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_AVATAR;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.SetAvatarCommand;
import seedu.address.model.person.Avatar;
import seedu.address.storage.util.ProcessImageUtil;

//@@author karrui
public class SetAvatarCommandParserTest {
    private SetAvatarCommandParser parser = new SetAvatarCommandParser();
    private ArrayList<String> filesCreated = new ArrayList<>();

    @After
    public void cleanup() {
        for (String path : filesCreated) {
            ProcessImageUtil.removeImageFromStorage(path);
        }
    }

    @Test
    public void parse_noParameters_success() throws Exception {
        final Avatar avatarNoParameters = new Avatar("");

        Index targetIndex = INDEX_FIRST_PERSON;
        String userInputEmptyParameter = targetIndex.getOneBased() + " " + PREFIX_AVATAR.toString()
                + " " + avatarNoParameters.path;

        SetAvatarCommand expectedCommandNoParameters = new SetAvatarCommand(INDEX_FIRST_PERSON, avatarNoParameters);
        assertParseSuccess(parser, userInputEmptyParameter, expectedCommandNoParameters);
    }

    @Test
    public void parse_withParameters_success() throws Exception {
        final Avatar avatarWithParameters = new Avatar(VALID_AVATAR_IMAGE_URL_ONE);
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInputWithParameters = targetIndex.getOneBased() + " " + PREFIX_AVATAR.toString()
                + " " + avatarWithParameters.initialUrl;

        SetAvatarCommand expectedCommandWithParameters = new SetAvatarCommand(INDEX_FIRST_PERSON, avatarWithParameters);
        filesCreated.add(expectedCommandWithParameters.getAvatar().path);
        SetAvatarCommand command = parser.parse(userInputWithParameters);
        filesCreated.add(command.getAvatar().path);
        assertEquals(expectedCommandWithParameters, command);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetAvatarCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, SetAvatarCommand.COMMAND_WORD, expectedMessage);
    }
}
