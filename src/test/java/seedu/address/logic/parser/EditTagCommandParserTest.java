package seedu.address.logic.parser;
//@@author Esilocke
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TAG_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_DUPLICATE_TAGS;
import static seedu.address.logic.commands.EditTagCommand.MESSAGE_INSUFFICIENT_ARGS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditTagCommand;
import seedu.address.model.tag.Tag;


public class EditTagCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditTagCommand.MESSAGE_USAGE);


    private EditTagCommandParser parser = new EditTagCommandParser();
    @Test
    public void invalidInputTest() {
        // empty argument
        assertParseFailure(parser, " ", MESSAGE_INVALID_FORMAT);
        // too little args
        assertParseFailure(parser, VALID_TAG_FRIEND, MESSAGE_INSUFFICIENT_ARGS);
        // too many args
        assertParseFailure(parser, VALID_TAG_FRIEND + " " + VALID_TAG_FRIEND
                + " " + VALID_TAG_FRIEND, MESSAGE_INSUFFICIENT_ARGS);
        // args are the same
        assertParseFailure(parser, VALID_TAG_FRIEND + " " + VALID_TAG_FRIEND, MESSAGE_DUPLICATE_TAGS);
        // args are invalid
        assertParseFailure(parser, INVALID_TAG_DESC + " " + INVALID_TAG_DESC, MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void validInputTest() throws IllegalValueException {
        Tag friendTag = new Tag(VALID_TAG_FRIEND);
        Tag husbandTag = new Tag(VALID_TAG_HUSBAND);
        Tag friendTagUpper = new Tag (VALID_TAG_FRIEND.toUpperCase());
        // case changes
        assertParseSuccess(parser, VALID_TAG_FRIEND + " "
                + VALID_TAG_FRIEND.toUpperCase(), new EditTagCommand(friendTag, friendTagUpper));
        // two distinct words
        assertParseSuccess(parser, VALID_TAG_FRIEND + " "
                + VALID_TAG_HUSBAND, new EditTagCommand(friendTag, husbandTag));

    }
}
