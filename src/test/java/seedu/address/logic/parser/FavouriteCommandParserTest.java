package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.RedoCommand.INDEX_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.EmailCommandParserTest.ALPHABET_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.model.person.Favourite;

//@@author justintkj
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the FavouriteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class FavouriteCommandParserTest {

    public static final String FIRST_PERSON = INDEX_ONE;
    private FavouriteCommandParser parser = new FavouriteCommandParser();

    @Test
    public void parse_validArgs_returnsFavouriteCommand() throws IllegalValueException {
        assertParseSuccess(parser, FIRST_PERSON, new FavouriteCommand(INDEX_FIRST_PERSON,
                new Favourite(Favourite.COLOR_SWITCH)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, ALPHABET_INDEX, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                FavouriteCommand.MESSAGE_USAGE));
    }
}
