package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FROM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.DismissCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class DismissCommandParserTest {

    private DismissTaskCommandParser parser = new DismissTaskCommandParser();

    @Test
    public void parseValidPersonIndexAndTaskIndex_success() throws ParseException {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_FROM.getPrefix() + INDEX_FIRST_TASK.getOneBased();
        assertParseSuccess(parser, userInput, new DismissCommand(indexes, INDEX_FIRST_TASK));
    }

    @Test
    public void parseInvalidIndexes_failure() throws ParseException {
        // invalid person indexes
        String userInput = "aaaaa" + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_FROM.getPrefix() + INDEX_FIRST_TASK.getOneBased();
        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX);

        // invalid task index
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_FROM.getPrefix() + "aaaaa";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parseInvalidArgsFailure() throws Exception {
        // no person indexes specified
        String userInput = " " + PREFIX_FROM.getPrefix() + INDEX_FIRST_TASK.getOneBased();
        assertParseFailure(parser, userInput, DismissCommand.MESSAGE_INVALID_PERSONS_ARGS);
        // no target prefix
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + INDEX_FIRST_TASK.getOneBased();
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DismissCommand.MESSAGE_USAGE));
        // no task index specified
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_FROM.getPrefix();
        assertParseFailure(parser, userInput, DismissCommand.MESSAGE_INVALID_TARGET_ARGS);
    }
}
