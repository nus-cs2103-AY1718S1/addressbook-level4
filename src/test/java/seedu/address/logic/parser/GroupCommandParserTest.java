package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_AMY;
import static seedu.address.logic.commands.CommandTestUtil.GROUP_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_GROUP_AMY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.GroupCommand;

//@@author tingtx
public class GroupCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, GroupCommand.MESSAGE_USAGE);
    private GroupCommandParser parser = new GroupCommandParser();

    @Test
    public void parse_invalidPreamble_failure() {

        //no index
        assertParseFailure(parser, " " + GROUP_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // negative index
        assertParseFailure(parser, "-6" + GROUP_DESC_AMY, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + GROUP_DESC_BOB, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void parse_noPrefix_failure() {

        assertParseFailure(parser, "1" + VALID_GROUP_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_noIndex_success() {
        List<Index> index = new ArrayList<>();
        GroupCommand expectedCommand = new GroupCommand(index, VALID_GROUP_AMY);
        assertParseSuccess(parser, " " + VALID_GROUP_AMY, expectedCommand);
    }

    @Test
    public void parse_showAllString_success() {
        List<Index> index = new ArrayList<>();
        GroupCommand expectedCommand = new GroupCommand(index, "SHOWALL");
        assertParseSuccess(parser, "SHOWALL", expectedCommand);

        expectedCommand = new GroupCommand(index, "showALL");
        assertParseSuccess(parser, "showALL", expectedCommand);
    }

    @Test
    public void parse_oneIndex_success() throws IllegalValueException {

        List<Index> index = new ArrayList<>();
        index.add(ParserUtil.parseIndex("1"));

        GroupCommand expectedCommand = new GroupCommand(index, VALID_GROUP_AMY);
        assertParseSuccess(parser, "1" + GROUP_DESC_AMY, expectedCommand);
    }

    @Test
    public void parse_multipleIndex_success() throws IllegalValueException {

        List<Index> index = new ArrayList<>();

        //to group a smaller group of 3 person
        index.add(ParserUtil.parseIndex("1"));
        index.add(ParserUtil.parseIndex("3"));
        index.add(ParserUtil.parseIndex("4"));

        GroupCommand expectedCommand = new GroupCommand(index, VALID_GROUP_AMY);
        assertParseSuccess(parser, "1 3 4" + GROUP_DESC_AMY, expectedCommand);

        //to group a bigger group of 15 person
        for (int i = 5; i < 17; i++) {
            index.add(ParserUtil.parseIndex(Integer.toString(i)));
        }
        expectedCommand = new GroupCommand(index, VALID_GROUP_AMY);
        String userInput = "1 3 4 5 6 7 8 9 10 11 12 13 14 15 16";
        assertParseSuccess(parser, userInput + GROUP_DESC_AMY, expectedCommand);

    }
}
