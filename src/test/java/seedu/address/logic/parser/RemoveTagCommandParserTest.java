package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.model.tag.Tag;

//@@author sidhmads
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RemoveTagCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RemoveTagCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RemoveTagCommandParserTest {

    private RemoveTagCommandParser parser = new RemoveTagCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemoveTagCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsRemoveTagCommand() {
        try {
            Set<Tag> tag = new HashSet<>();
            tag.add(new Tag("friend"));
            RemoveTagCommand expectedRemoveTagCommand =
                    new RemoveTagCommand(new ArrayList<Index>(), tag);
            assertParseSuccess(parser, "removeTag t/friend", expectedRemoveTagCommand);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default tag's value is invalid.");
        }
    }
}
