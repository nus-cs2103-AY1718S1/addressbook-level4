package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEADER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_BIRTHDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.person.event.Desc;
import seedu.address.model.person.event.Event;
import seedu.address.model.person.event.EventDate;
import seedu.address.model.person.event.Header;
import seedu.address.testutil.EventBuilder;

//@@author royceljh
public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withHeader(VALID_HEADER_BIRTHDAY).withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();

        // multiple header - last header accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_MEETING + HEADER_DESC_BIRTHDAY
                + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY, new AddEventCommand(expectedEvent));

        // multiple desc - last desc accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + DESC_DESC_MEETING + DESC_DESC_BIRTHDAY
                + EVENT_DATE_DESC_BIRTHDAY, new AddEventCommand(expectedEvent));

        // multiple eventDates - last eventDate accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_MEETING
                + EVENT_DATE_DESC_BIRTHDAY, new AddEventCommand(expectedEvent));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing header prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_HEADER_BIRTHDAY
                + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY, expectedMessage);

        // missing desc prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + VALID_DESC_BIRTHDAY + EVENT_DATE_DESC_BIRTHDAY, expectedMessage);

        // missing eventDate prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + DESC_DESC_BIRTHDAY + VALID_EVENT_DATE_BIRTHDAY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid header
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_HEADER_DESC + DESC_DESC_BIRTHDAY
                + EVENT_DATE_DESC_BIRTHDAY, Header.MESSAGE_HEADER_CONSTRAINTS);

        // invalid desc
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + INVALID_DESC_DESC + EVENT_DATE_DESC_BIRTHDAY, Desc.MESSAGE_DESC_CONSTRAINTS);

        // invalid eventDate
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + HEADER_DESC_BIRTHDAY
                + DESC_DESC_BIRTHDAY + INVALID_EVENT_DATE_DESC, EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS);
    }
}
