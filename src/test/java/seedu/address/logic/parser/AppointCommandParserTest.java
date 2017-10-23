package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_APPOINT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.AppointCommand;
import seedu.address.model.person.Appoint;

public class AppointCommandParserTest {
    private AppointCommandParser parser = new AppointCommandParser();

    @Test
    public void parse_indexSpecified_failure() throws Exception {
        final Appoint appoint = new Appoint("Some appointment.");

        // have appoints
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_APPOINT.toString() + " " + appoint;
        AppointCommand expectedCommand = new AppointCommand(INDEX_FIRST_PERSON, appoint);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no appoints
        userInput = targetIndex.getOneBased() + " " + PREFIX_APPOINT.toString();
        expectedCommand = new AppointCommand(INDEX_FIRST_PERSON, new Appoint(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AppointCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, AppointCommand.COMMAND_WORD, expectedMessage);
    }
}
