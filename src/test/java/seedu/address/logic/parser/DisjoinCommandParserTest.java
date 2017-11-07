package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.DisjoinCommand;

//@@author Adoby7
/**
 * Test disjoin command parser
 */
public class DisjoinCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, DisjoinCommand.MESSAGE_USAGE);

    private static final String FIRST_PERSON = " " + PREFIX_PERSON + INDEX_FIRST_PERSON.getOneBased();
    private static final String FIRST_EVENT = " " + PREFIX_EVENT + INDEX_FIRST_EVENT.getOneBased();

    private DisjoinCommandParser parser = new DisjoinCommandParser();

    @Test
    public void testMissingPartsInput() {
        //Miss all parts
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);

        //Miss person index
        assertParseFailure(parser, FIRST_EVENT, MESSAGE_INVALID_FORMAT);

        //Miss event index
        assertParseFailure(parser, FIRST_PERSON, MESSAGE_INVALID_FORMAT);
    }

    //Miss prefix
    @Test
    public void testMissingPrefixInput() {
        //Miss person prefix
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + FIRST_EVENT,
                MESSAGE_INVALID_FORMAT);

        //Miss event prefix
        assertParseFailure(parser, FIRST_PERSON + " " + INDEX_FIRST_EVENT.getOneBased(),
                MESSAGE_INVALID_FORMAT);

        //Miss all prefixes
        assertParseFailure(parser, INDEX_FIRST_PERSON.getOneBased() + " " + INDEX_FIRST_EVENT.getOneBased(),
                MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void testIllegalInput() {
        // negative index
        assertParseFailure(parser, PREFIX_PERSON + "-5 " + FIRST_EVENT, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, FIRST_PERSON + " " + PREFIX_EVENT + "0" , MESSAGE_INVALID_FORMAT);

        // Non integer
        assertParseFailure(parser, PREFIX_PERSON + "some random string", MESSAGE_INVALID_FORMAT);

    }

    @Test
    public void testSuccess() {
        DisjoinCommand expectedCommand = new DisjoinCommand(INDEX_FIRST_PERSON, INDEX_FIRST_EVENT);
        assertParseSuccess(parser, FIRST_PERSON + " " + FIRST_EVENT, expectedCommand);
    }

}
