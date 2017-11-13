//@@author erik0704
package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ONE;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_TWO;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_EVENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_EVENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EVENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EVENT_TWO;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ONE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EVENT_ONE;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Dates;
import seedu.address.model.event.Event;
import seedu.address.model.person.Name;
import seedu.address.testutil.TaskEventBuilder;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();


    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new TaskEventBuilder().withName(VALID_NAME_EVENT_ONE).withDates(VALID_DATE_EVENT_ONE)
                .withDescription(VALID_DESCRIPTION_ONE).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT_TWO + NAME_DESC_EVENT_ONE
                + DATE_DESC_ONE + DESCRIPTION_DESC_ONE, new AddEventCommand(expectedEvent));

        // multiple dates - last dates accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT_ONE
                + DATE_DESC_TWO + DATE_DESC_ONE + DESCRIPTION_DESC_ONE, new AddEventCommand(expectedEvent));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT_ONE + DATE_DESC_ONE
                + DESCRIPTION_DESC_TWO + DESCRIPTION_DESC_ONE, new AddEventCommand(expectedEvent));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_NAME_EVENT_ONE
                + DATE_DESC_ONE + DESCRIPTION_DESC_ONE, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT_ONE
                + VALID_DATE_EVENT_ONE + DESCRIPTION_DESC_ONE, expectedMessage);

    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_NAME_EVENT_DESC + DATE_DESC_ONE
                + DESCRIPTION_DESC_ONE, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid dates
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT_ONE + INVALID_DATE_EVENT_DESC
                + DESCRIPTION_DESC_ONE, Dates.MESSAGE_DATE_CONSTRAINTS);
    }

    @Test
    public void parse_noFieldSpecified_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD, expectedMessage);
    }
}

