package seedu.address.logic.parser;

//@@author jeffreygohkw
import static org.junit.Assert.assertEquals;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.SortCommand.ACCEPTED_FIELD_PARAMETERS;
import static seedu.address.logic.commands.SortCommand.ACCEPTED_LIST_PARAMETERS;
import static seedu.address.logic.commands.SortCommand.ACCEPTED_ORDER_PARAMETERS;
import static seedu.address.logic.commands.SortCommand.MESSAGE_USAGE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.SortCommand;
import seedu.address.logic.parser.exceptions.ParseException;

public class SortCommandParserTest {

    private SortCommandParser parser = new SortCommandParser();

    @Test
    public void no_arguments_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));
    }

    //author charlesgoh
    @Test
    public void parse_wrongArguments_failure() {
        // no list specified
        assertParseFailure(parser,  ACCEPTED_FIELD_PARAMETERS.get(0) + " " + ACCEPTED_ORDER_PARAMETERS.get(0),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // no field specified
        assertParseFailure(parser,  ACCEPTED_LIST_PARAMETERS.get(0) + " " + ACCEPTED_ORDER_PARAMETERS.get(0),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // no order specified
        assertParseFailure(parser,  ACCEPTED_LIST_PARAMETERS.get(0) + " " + ACCEPTED_FIELD_PARAMETERS.get(0),
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // list is person but field is for tasks
        assertParseFailure(parser,  ACCEPTED_LIST_PARAMETERS.get(0) + " " + ACCEPTED_FIELD_PARAMETERS.get(5)
                + " " + ACCEPTED_ORDER_PARAMETERS.get(0), String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // list is task but field is for person
        assertParseFailure(parser,  ACCEPTED_LIST_PARAMETERS.get(1) + " " + ACCEPTED_FIELD_PARAMETERS.get(0)
                + " " + ACCEPTED_ORDER_PARAMETERS.get(0), String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

        // Incorrect test
        assertParseFailure(parser, "random text",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_USAGE));

    }
    //@@author charlesgoh
    @Test
    public void parse_validArguments_success() throws ParseException {
        SortCommand expectedCommand;
        SortCommand actualCommand;

        // For person sorts
        String list = ACCEPTED_LIST_PARAMETERS.get(0);
        String field = ACCEPTED_FIELD_PARAMETERS.get(0);
        String order = ACCEPTED_ORDER_PARAMETERS.get(0);

        expectedCommand = new SortCommand(list, field, order);
        actualCommand = parser.parse(list + " " + field + " " + order);
        assertEquals(true, expectedCommand.sameCommandAs(actualCommand));

        // For task sorts
        list = ACCEPTED_LIST_PARAMETERS.get(1);
        field = ACCEPTED_FIELD_PARAMETERS.get(5);
        order = ACCEPTED_ORDER_PARAMETERS.get(1);

        expectedCommand = new SortCommand(list, field, order);
        actualCommand = parser.parse(list + " " + field + " " + order);
        assertEquals(true, expectedCommand.sameCommandAs(actualCommand));
    }
    //@@author
}
