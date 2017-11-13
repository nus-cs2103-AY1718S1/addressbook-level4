package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ADDRESS_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.PHONE_DESC_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_EVENT2;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

//@@author junyango

public class AddEventCommandParserTest {
    private final AddEventParser parser = new AddEventParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withName(VALID_NAME_EVENT1).withDateTime(VALID_DATE_EVENT1)
                .withAddress(VALID_VENUE_EVENT1).withReminder().build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD +  NAME_DESC_EVENT2  + NAME_DESC_EVENT1
                + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1, new AddEventCommand(expectedEvent));

        // multiple phones - last date accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT2
                + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1, new AddEventCommand(expectedEvent));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT1
                + VENUE_DESC_EVENT2 + VENUE_DESC_EVENT1, new AddEventCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_NAME_EVENT1 + DATE_DESC_EVENT1
                + VENUE_DESC_EVENT1, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + VALID_DATE_EVENT1
                + VENUE_DESC_EVENT1, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT2 + PHONE_DESC_BOB
                + VALID_EMAIL_BOB + ADDRESS_DESC_BOB, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + NAME_DESC_EVENT1 + DATE_DESC_EVENT1
                + VALID_VENUE_EVENT1, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_NAME_EVENT1 + VALID_DATE_EVENT1
                + VALID_VENUE_EVENT1, expectedMessage);
    }
}
