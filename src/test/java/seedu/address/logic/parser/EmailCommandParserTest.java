//@@author conantteo
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EmailCommand;

public class EmailCommandParserTest {

    private EmailCommandParser parser = new EmailCommandParser();
    private Index[] indexArray;

    @Test
    public void parse_validArgs_returnsEmailCommand() {
        assertParseSuccess(parser, "1", prepareCommand(INDEX_FIRST_PERSON));
        assertParseSuccess(parser, "1 2", prepareCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_duplicatedArgs_throwsParseException() {
        assertParseFailure(parser, "1 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ParserUtil.MESSAGE_INDEX_DUPLICATES));
    }

    /**
     * Returns a {@code EmailCommand} with the parameter {@code index}.
     */
    private EmailCommand prepareCommand(Index index) {
        indexArray = new Index[1];
        indexArray[0] = index;

        EmailCommand emailCommand = new EmailCommand(indexArray);

        return emailCommand;
    }

    /**
     * Returns a {@code EmailCommand} with more than one parameter {@code index}.
     */
    private EmailCommand prepareCommand(Index firstIndex, Index secondIndex) {
        indexArray = new Index[2];
        indexArray[0] = firstIndex;
        indexArray[1] = secondIndex;

        EmailCommand emailCommand = new EmailCommand(indexArray);

        return emailCommand;
    }
}
