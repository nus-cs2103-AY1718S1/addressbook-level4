//@@author Hailinx
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.TodoCommandParser.PARSE_EXCEPTION_MESSAGE;
import static seedu.address.logic.parser.TodoCommandParser.parseIndexString;
import static seedu.address.logic.parser.TodoCommandParser.parseStrAfterIndex;
import static seedu.address.testutil.TodoItemUtil.getTodoItemThree;

import org.junit.Assert;
import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.TodoCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class TodoCommandParserTest {

    private TodoCommandParser parser = new TodoCommandParser();

    @Test
    public void test_parseIndexString_success() throws Exception {
        Assert.assertEquals("3", parseIndexString("3"));

        Assert.assertEquals("3", parseIndexString("3 abcd"));

        Assert.assertEquals("3", parseIndexString("3 -n"));
    }

    @Test
    public void test_parseIndexString_throwsException() {
        try {
            parseIndexString("");
            Assert.fail("Execute without throwing exception");
        } catch (ParseException e) {
            Assert.assertEquals(e.getMessage(), PARSE_EXCEPTION_MESSAGE);
        }

        try {
            parseIndexString("a");
            Assert.fail("Execute without throwing exception");
        } catch (ParseException e) {
            Assert.assertEquals(e.getMessage(), PARSE_EXCEPTION_MESSAGE);
        }
    }

    @Test
    public void test_parseStrAfterIndex() throws Exception {
        Assert.assertEquals("abcd", parseStrAfterIndex(parseIndexString("5 abcd"), "5 abcd"));

        Assert.assertEquals("", parseStrAfterIndex(parseIndexString("5"), "5"));
    }

    @Test
    public void parse_validArgs_returnsTodoCommand() {
        Index personIndex = Index.fromOneBased(5);

        // TodoOption: Add
        assertParseSuccess(parser, "5 -a f/01-01-2017 12:00 d/task: item three",
                new TodoCommand(TodoCommand.PREFIX_TODO_ADD, personIndex, getTodoItemThree(), null));

        // TodoOption: Delete one
        assertParseSuccess(parser, "5 -d 3",
                new TodoCommand(TodoCommand.PREFIX_TODO_DELETE_ONE, personIndex, null, Index.fromOneBased(3)));

        // TodoOption: Delete all
        assertParseSuccess(parser, "5 -c",
                new TodoCommand(TodoCommand.PREFIX_TODO_DELETE_ALL, personIndex, null, null));

        // TodoOption: List one
        assertParseSuccess(parser, "5 -l",
                new TodoCommand(TodoCommand.PREFIX_TODO_LIST, personIndex, null, null));

        // TodoOption: List all
        assertParseSuccess(parser, "",
                new TodoCommand(TodoCommand.PREFIX_TODO_LIST_ALL, null, null, null));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-p", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TodoCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "0 -l", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TodoCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "--p", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TodoCommand.MESSAGE_USAGE));

        assertParseFailure(parser, "1 -p", String.format(MESSAGE_INVALID_COMMAND_FORMAT, TodoCommand.MESSAGE_USAGE));
    }

}
