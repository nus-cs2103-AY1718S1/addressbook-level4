package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.CustomCommand;
import seedu.address.model.customField.CustomField;

//@@author LuLechuan
public class CustomCommandParserTest {

    private CustomCommandParser parser = new CustomCommandParser();

    @Test
    public void parse_validArgs_returnsCustomCommand() throws IllegalValueException {
        assertParseSuccess(parser, "1 NickName Ah",
                new CustomCommand(INDEX_FIRST_PERSON, new CustomField("NickName", "Ah")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomCommand.MESSAGE_USAGE));
    }
}
