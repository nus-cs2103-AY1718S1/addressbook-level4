package seedu.room.logic.parser;

import static seedu.room.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.room.logic.commands.CommandTestUtil.DATETIME_DESC_ORIENTATION;
import static seedu.room.logic.commands.CommandTestUtil.DATETIME_DESC_POLYMATH;
import static seedu.room.logic.commands.CommandTestUtil.DESCRIPTION_DESC_ORIENTATION;
import static seedu.room.logic.commands.CommandTestUtil.DESCRIPTION_DESC_POLYMATH;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.room.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.room.logic.commands.CommandTestUtil.LOCATION_DESC_ORIENTATION;
import static seedu.room.logic.commands.CommandTestUtil.LOCATION_DESC_POLYMATH;
import static seedu.room.logic.commands.CommandTestUtil.TITLE_DESC_ORIENTATION;
import static seedu.room.logic.commands.CommandTestUtil.TITLE_DESC_POLYMATH;
import static seedu.room.logic.commands.CommandTestUtil.VALID_DATETIME_ORIENTATION;
import static seedu.room.logic.commands.CommandTestUtil.VALID_DESCRIPTION_ORIENTATION;
import static seedu.room.logic.commands.CommandTestUtil.VALID_LOCATION_ORIENTATION;
import static seedu.room.logic.commands.CommandTestUtil.VALID_TITLE_ORIENTATION;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.room.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.room.logic.commands.AddEventCommand;
import seedu.room.model.event.Datetime;
import seedu.room.model.event.Description;
import seedu.room.model.event.Event;
import seedu.room.model.event.Location;
import seedu.room.model.event.Title;
import seedu.room.testutil.EventBuilder;

//@@author sushinoya
public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withTitle(VALID_TITLE_ORIENTATION)
                .withDescription(VALID_DESCRIPTION_ORIENTATION)
                .withLocation(VALID_LOCATION_ORIENTATION).withDatetime(VALID_DATETIME_ORIENTATION).build();

        // (with command word) multiple titles - last title accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_POLYMATH
                + TITLE_DESC_ORIENTATION + DESCRIPTION_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // (with alias) multiple titles - last title accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_POLYMATH
                + TITLE_DESC_ORIENTATION + DESCRIPTION_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // (with command word) multiple description - last description accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_POLYMATH + DESCRIPTION_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // (with alias) multiple description - last description accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_POLYMATH + DESCRIPTION_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // (with command word) multiple locations - last location accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + LOCATION_DESC_POLYMATH
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // (with alias) multiple locations - last location accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + LOCATION_DESC_POLYMATH
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // (with command word) multiple datetime - last datetime accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + LOCATION_DESC_ORIENTATION
                + DATETIME_DESC_POLYMATH + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));

        // (with alias) multiple datetime - last datetime accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + LOCATION_DESC_ORIENTATION
                + DATETIME_DESC_POLYMATH + DATETIME_DESC_ORIENTATION, new AddEventCommand(expectedEvent));
    }


    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // (with command word) missing title prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + DESCRIPTION_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, expectedMessage);

        // (with command word) missing location prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, expectedMessage);

        // (with command word) missing datetime prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + DESCRIPTION_DESC_ORIENTATION + LOCATION_DESC_ORIENTATION , expectedMessage);

        // (with command word) missing description prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_ORIENTATION
                + LOCATION_DESC_ORIENTATION + DATETIME_DESC_ORIENTATION, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // (with command word) invalid title
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE_DESC + LOCATION_DESC_POLYMATH
                + DESCRIPTION_DESC_POLYMATH + DATETIME_DESC_POLYMATH, Title.MESSAGE_TITLE_CONSTRAINTS);

        // (with alias) invalid title
        assertParseFailure(parser, AddEventCommand.COMMAND_ALIAS + INVALID_TITLE_DESC + LOCATION_DESC_POLYMATH
                + DESCRIPTION_DESC_POLYMATH + DATETIME_DESC_POLYMATH, Title.MESSAGE_TITLE_CONSTRAINTS);

        // (with command word) invalid location
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_POLYMATH + INVALID_LOCATION_DESC
                + DESCRIPTION_DESC_POLYMATH + DATETIME_DESC_POLYMATH, Location.MESSAGE_LOCATION_CONSTRAINTS);

        // (with alias) invalid location
        assertParseFailure(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_POLYMATH + INVALID_LOCATION_DESC
                + DESCRIPTION_DESC_POLYMATH + DATETIME_DESC_POLYMATH, Location.MESSAGE_LOCATION_CONSTRAINTS);

        // (with command word) invalid description
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_POLYMATH + LOCATION_DESC_POLYMATH
                + INVALID_DESCRIPTION_DESC + DATETIME_DESC_POLYMATH, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // (with alias) invalid description
        assertParseFailure(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_POLYMATH
                + LOCATION_DESC_POLYMATH + INVALID_DESCRIPTION_DESC
                + DATETIME_DESC_POLYMATH, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // (with command word) invalid datetime
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_POLYMATH + LOCATION_DESC_POLYMATH
                        + DESCRIPTION_DESC_POLYMATH + INVALID_DATETIME_DESC, Datetime.DATE_CONSTRAINTS_VIOLATION);

        // (with alias) invalid datetime
        assertParseFailure(parser, AddEventCommand.COMMAND_ALIAS + TITLE_DESC_POLYMATH
                        + LOCATION_DESC_POLYMATH + DESCRIPTION_DESC_POLYMATH + INVALID_DATETIME_DESC,
                Datetime.DATE_CONSTRAINTS_VIOLATION);

        // (with command word) two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE_DESC + LOCATION_DESC_POLYMATH
                + DESCRIPTION_DESC_POLYMATH + INVALID_DATETIME_DESC, Title.MESSAGE_TITLE_CONSTRAINTS);

        // (with alias) two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_ALIAS + INVALID_TITLE_DESC
                + LOCATION_DESC_POLYMATH + DESCRIPTION_DESC_POLYMATH + INVALID_DATETIME_DESC,
                Title.MESSAGE_TITLE_CONSTRAINTS);
    }
}
