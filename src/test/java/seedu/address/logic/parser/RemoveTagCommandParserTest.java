package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

//@@author JasmineSee
public class RemoveTagCommandParserTest {
    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() throws Exception {
        ArrayList<Tag> tagToRemove = new ArrayList<>();
        tagToRemove.add(new Tag("friends"));
        tagToRemove.add(new Tag("colleagues"));

        RemoveTagCommand expectedRemoveTagCommand = new RemoveTagCommand(tagToRemove);
        assertParseSuccess(parser, "friends colleagues", expectedRemoveTagCommand);
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }
}
