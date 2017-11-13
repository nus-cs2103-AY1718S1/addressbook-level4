//@@author AceCentury

package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.DeleteByNameCommand;
import seedu.address.model.person.Name;

public class DeleteByNameCommandParserTest {

    private DeleteByNameCommandParser parser = new DeleteByNameCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteByNameCommand() throws IllegalValueException {
        //Single word name
        assertParseSuccess(parser, "John", new DeleteByNameCommand(new Name("John")));

        //Multiple word name
        assertParseSuccess(parser, "a b c d e f",
                new DeleteByNameCommand(new Name("a b c d e f")));

        //2 word name with no leading and trailing whitespaces
        Name name = new Name(VALID_NAME_BOB);
        DeleteByNameCommand expectedDeleteByNameCommand = new DeleteByNameCommand(name);
        assertParseSuccess(parser, VALID_NAME_BOB, expectedDeleteByNameCommand);

        //Leading whitespaces
        assertParseSuccess(parser, (" " + VALID_NAME_BOB), expectedDeleteByNameCommand);

        //Trailing whitespaces
        assertParseSuccess(parser, (VALID_NAME_BOB + "  "), expectedDeleteByNameCommand);

        //Leading and trailing whitespaces
        assertParseSuccess(parser, (" " + VALID_NAME_BOB + "  "), expectedDeleteByNameCommand);

        //Numeric input
        assertParseSuccess(parser, ("1"), new DeleteByNameCommand(new Name("1")));

        //Alphanumeric input
        assertParseSuccess(parser, ("1abc"), new DeleteByNameCommand(new Name("1abc")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        //No input case
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //White space input case
        assertParseFailure(parser, " ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Multiple white space input case
        assertParseFailure(parser, "    ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special character input case
        assertParseFailure(parser, "%",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special ASCII character input case
        assertParseFailure(parser, "ƒ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special character within name input case
        assertParseFailure(parser, "J%hn",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special ASCII character within name input case
        //Note: Small o, Capital O, ASCII symbol ○
        assertParseFailure(parser, "fi○na",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special character input case before valid name
        assertParseFailure(parser, "% John",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special character input case after valid name
        assertParseFailure(parser, "John %",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special ASCII character input case before valid name
        assertParseFailure(parser, "ƒ John",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));

        //Special ASCII character input case after valid name
        assertParseFailure(parser, "John ƒ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, DeleteByNameCommand.MESSAGE_USAGE));
    }
}
