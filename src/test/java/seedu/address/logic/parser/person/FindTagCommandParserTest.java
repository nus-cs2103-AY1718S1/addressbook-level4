package seedu.address.logic.parser.person;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.person.FindTagCommand;
import seedu.address.model.property.TagContainsKeywordsPredicate;

//@@author yunpengn
public class FindTagCommandParserTest {
    private final FindTagCommandParser parser = new FindTagCommandParser();

    @Test
    public void parse_tagNamePresent_checkCorrectness() {
        List<String> names = new ArrayList<>();
        names.add("friends");
        names.add("classmates");
        Command expected = new FindTagCommand(new TagContainsKeywordsPredicate(names));
        assertParseSuccess(parser, "friends classmates", expected);
    }

    @Test
    public void parse_tagNameEmpty_expectException() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindTagCommand.MESSAGE_USAGE);
        assertParseFailure(parser, " ", expectedMessage);
    }
}
