//@@author shuang-yang
package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PERIOD;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PERIOD_SOCCER;
import static seedu.address.logic.commands.CommandTestUtil.PERIOD_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.PERIOD_SOCCER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_SOCCER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import java.util.Optional;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.event.RepeatCommand;
import seedu.address.logic.parser.ParserUtil;
import seedu.address.model.event.Period;

public class RepeatCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RepeatCommand.MESSAGE_USAGE);

    private RepeatCommandParser parser = new RepeatCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_PERIOD_MIDTERM, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, VALID_PERIOD_MIDTERM, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_PERIOD_SOCCER, Period.MESSAGE_PERIOD_CONSTRAINTS); // invalid period
    }

    @Test
    public void parse_fieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + VALID_PERIOD_MIDTERM;
        Optional<Period> period = Period.generatePeriod(VALID_PERIOD_MIDTERM);

        RepeatCommand expectedCommand = new RepeatCommand(targetIndex, period);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
