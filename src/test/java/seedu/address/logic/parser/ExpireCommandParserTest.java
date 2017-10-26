package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EXPIRE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.ExpireCommand;
import seedu.address.model.person.ExpiryDate;

public class ExpireCommandParserTest {

    private ExpireCommandParser parser = new ExpireCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final String dateString = "2011-01-01";

        // have date
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_EXPIRE.toString() + " " + dateString;
        ExpireCommand expectCommand = new ExpireCommand(INDEX_FIRST_PERSON, new ExpiryDate(dateString));

        assertParseSuccess(parser, userInput, expectCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, ExpireCommand.MESSAGE_USAGE);

        assertParseFailure(parser, ExpireCommand.COMMAND_WORD, expectedMessage);
    }
}
