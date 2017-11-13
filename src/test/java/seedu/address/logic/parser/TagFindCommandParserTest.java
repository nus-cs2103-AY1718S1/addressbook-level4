package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.TagFindCommand;
import seedu.address.model.tag.TagMatchingKeywordPredicate;

//@@author ZhangH795

public class TagFindCommandParserTest {

    private TagFindCommandParser parser = new TagFindCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, TagFindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsTagFindCommand() {
        boolean looseFind = true;
        TagFindCommand expectedTagFindCommandOne =
                new TagFindCommand(new TagMatchingKeywordPredicate("friends", true));
        assertParseSuccess(parser, "friends", expectedTagFindCommandOne);
        TagFindCommand expectedTagFindCommandTwo =
                new TagFindCommand(new TagMatchingKeywordPredicate("friend 2 be", true));
        assertParseSuccess(parser, "friend 2 be", expectedTagFindCommandTwo);
        TagFindCommand expectedTagFindCommandThree =
                new TagFindCommand(new TagMatchingKeywordPredicate("1 2 3", true));
        assertParseSuccess(parser, "1 2 3", expectedTagFindCommandThree);
    }

}
