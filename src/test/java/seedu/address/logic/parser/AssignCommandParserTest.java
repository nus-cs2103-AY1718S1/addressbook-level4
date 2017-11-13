package seedu.address.logic.parser;
//@@author Esilocke
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TARGET;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AssignCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class AssignCommandParserTest {

    private AssignTaskCommandParser parser = new AssignTaskCommandParser();

    @Test
    public void parseValidPersonIndexAndTaskIndex_success() throws ParseException {
        ArrayList<Index> indexes = new ArrayList<>();
        indexes.add(INDEX_FIRST_PERSON);
        indexes.add(INDEX_SECOND_PERSON);

        String userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET.getPrefix() + INDEX_FIRST_TASK.getOneBased();
        assertParseSuccess(parser, userInput, new AssignCommand(indexes, INDEX_FIRST_TASK));
    }

    @Test
    public void parseInvalidIndexes_failure() throws ParseException {
        // invalid person indexes
        String userInput = "aaaaa" + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET.getPrefix() + INDEX_FIRST_TASK.getOneBased();
        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX);

        // invalid task index
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET.getPrefix() + "aaaaa";
        assertParseFailure(parser, userInput, MESSAGE_INVALID_INDEX);
    }

    @Test
    public void parseInvalidArgsFailure() throws Exception {
        // no person indexes specified
        String userInput = " " + PREFIX_TARGET.getPrefix() + INDEX_FIRST_TASK.getOneBased();
        assertParseFailure(parser, userInput, AssignCommand.MESSAGE_INVALID_PERSONS_ARGS);
        // no target prefix
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + INDEX_FIRST_TASK.getOneBased();
        assertParseFailure(parser, userInput, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AssignCommand.MESSAGE_USAGE));
        // no task index specified
        userInput = INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + " "
                + PREFIX_TARGET.getPrefix();
        assertParseFailure(parser, userInput, AssignCommand.MESSAGE_INVALID_TARGET_ARGS);
    }
}
