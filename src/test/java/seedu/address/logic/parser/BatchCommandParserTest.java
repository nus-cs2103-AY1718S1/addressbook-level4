package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.HashSet;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.BatchCommand;
import seedu.address.model.tag.Tag;

//@@author rushan-khor
public class BatchCommandParserTest {

    private BatchCommandParser parser = new BatchCommandParser();

    @Test
    public void testValidTags() {
        HashSet<Tag> tagSetForTest = new HashSet<>();
        try {
            tagSetForTest.add(new Tag("tag1", "grey"));
        } catch (IllegalValueException e) {
            System.out.println(e.getMessage());
        }
        assertParseSuccess(parser, "tag1", new BatchCommand(tagSetForTest));
        assertParseSuccess(parser, " tag1 ", new BatchCommand(tagSetForTest));
        assertParseSuccess(parser, "tag1 ", new BatchCommand(tagSetForTest));
    }

    @Test
    public void testParseException() {
        assertParseFailure(parser, ".", String.format(MESSAGE_INVALID_COMMAND_FORMAT, BatchCommand.MESSAGE_USAGE));
    }
}
