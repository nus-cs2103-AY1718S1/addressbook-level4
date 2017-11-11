package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.getTypicalPersonIndexList;

import java.util.Collections;

import org.junit.Test;

import seedu.address.logic.commands.SelectJoinedEventsCommand;

//@@author LeonChowWenHao
/**
 * Tests for {@code SelectJoinedEventsCommandParser}.
 */
public class SelectJoinedEventsCommandParserTest {

    private SelectJoinedEventsCommandParser parser = new SelectJoinedEventsCommandParser();

    /**
     * Test for parsing valid Index. Includes single and multiple Index input.
     **/
    @Test
    public void parseValidArgsReturnsSelectJoinedEventsCommand() {
        // Valid argument for SelectJoinedEventsCommand is a List<Index>
        // Valid index specified
        assertParseSuccess(parser, "1", new SelectJoinedEventsCommand(
                Collections.singletonList(INDEX_FIRST_PERSON)));

        // Valid indexes specified
        assertParseSuccess(parser, "1 2 3", new SelectJoinedEventsCommand(getTypicalPersonIndexList()));
    }

    /**
     * Test for parsing invalid Index. Includes single and multiple Index input.
     **/
    @Test
    public void parseInvalidArgsThrowsParseException() {
        // Invalid index specified
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));

        // No index specified
        assertParseFailure(parser, "", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));

        // Zero index specified
        assertParseFailure(parser, "0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));

        // Negative index specified
        assertParseFailure(parser, "-1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));

        // Indexes with character in between specified
        assertParseFailure(parser, "1, 2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));

        // One valid and one invalid Index
        assertParseFailure(parser, "1 0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                SelectJoinedEventsCommand.MESSAGE_USAGE));
    }


}
