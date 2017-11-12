package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TIME_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TIME_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_TIME_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_TIME_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESC_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME_FIRST;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.testutil.EventBuilder;

// @@author Adoby7
public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parseAllFieldsPresentSuccess() {
        Event expectedEvent = new EventBuilder().withName(VALID_EVENT_NAME_FIRST)
                .withDescription(VALID_EVENT_DESC_FIRST).withTime(VALID_EVENT_TIME_FIRST).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_SECOND + EVENT_NAME_FIRST
                        + EVENT_DESC_FIRST + EVENT_TIME_FIRST,
                new AddEventCommand(expectedEvent));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + EVENT_DESC_SECOND
                + EVENT_DESC_FIRST + EVENT_TIME_FIRST,
                new AddEventCommand(expectedEvent));

        // multiple times - last time accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + EVENT_DESC_FIRST
                + EVENT_TIME_SECOND + EVENT_TIME_FIRST,
                new AddEventCommand(expectedEvent));
    }

    @Test
    public void parseCompulsoryFieldMissingFailure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_EVENT_NAME_FIRST + EVENT_DESC_FIRST
                + EVENT_TIME_FIRST, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_DESC_FIRST + VALID_EVENT_DESC_FIRST
                + EVENT_TIME_FIRST, expectedMessage);

        // missing time prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_DESC_FIRST + EVENT_DESC_FIRST
                + VALID_EVENT_TIME_FIRST, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_EVENT_NAME_FIRST
                + VALID_EVENT_DESC_FIRST + VALID_EVENT_TIME_FIRST, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_EVENT_NAME + EVENT_DESC_FIRST
                + EVENT_TIME_FIRST, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + INVALID_EVENT_DESC
                        + EVENT_TIME_FIRST, EventDescription.MESSAGE_EVENT_DESCRIPTION_CONSTRAINTS);

        // invalid times
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + EVENT_DESC_FIRST
                        + INVALID_EVENT_TIME_FIRST, EventTime.MESSAGE_EVENT_TIME_CONSTRAINTS);

        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_FIRST + EVENT_DESC_FIRST
                + INVALID_EVENT_TIME_SECOND, EventTime.MESSAGE_EVENT_TIME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_EVENT_NAME + EVENT_DESC_FIRST
                + INVALID_EVENT_TIME_SECOND, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);
    }
}
