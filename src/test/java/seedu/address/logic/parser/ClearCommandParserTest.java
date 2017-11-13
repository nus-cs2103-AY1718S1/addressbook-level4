package seedu.address.logic.parser;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PERSON;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TASK;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.ClearPersonCommand;
import seedu.address.logic.commands.ClearTaskCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class ClearCommandParserTest {
    private ClearCommandParser parser = new ClearCommandParser();

    @Test
    public void parseInvalidType_failure() throws ParseException {
        assertParseFailure(parser, " some invalid value",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClearCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidTypes_success() throws ParseException {
        ClearCommand expectedClearAll = new ClearCommand();

        assertTrue(parser.parse(" " + PREFIX_PERSON.getPrefix()) instanceof ClearPersonCommand);
        assertTrue(parser.parse(" " + PREFIX_TASK.getPrefix()) instanceof ClearTaskCommand);

        ClearCommand multipleTypesSpecified = parser.parse(" " + PREFIX_PERSON.getPrefix() + " "
                + PREFIX_TASK.getPrefix());
        assertFalse(multipleTypesSpecified instanceof ClearPersonCommand);
        assertFalse(multipleTypesSpecified instanceof ClearTaskCommand);

        ClearCommand repeatedTypes = parser.parse(" " + PREFIX_PERSON.getPrefix() + " "
                + PREFIX_TASK.getPrefix() + PREFIX_PERSON.getPrefix() + " " + PREFIX_TASK.getPrefix() + " ");
        assertFalse(repeatedTypes instanceof ClearPersonCommand);
        assertFalse(repeatedTypes instanceof ClearTaskCommand);
    }
}
