package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.DATETIME_DESC_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_DESC_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATETIME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESCRIPTION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_LOCATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.LOCATION_DESC_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_SPECTRA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Datetime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Event;
import seedu.address.model.event.Location;
import seedu.address.model.event.Title;
import seedu.address.testutil.EventBuilder;

//@@author kaiyu92
public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withTitle(VALID_TITLE_DEEPAVALI)
                .withDescription(VALID_DESCRIPTION_DEEPAVALI)
                .withLocation(VALID_LOCATION_DEEPAVALI).withDatetime(VALID_DATETIME_DEEPAVALI).build();

        // multiple titles - last title accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_SPECTRA + TITLE_DESC_DEEPAVALI
                + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI, new AddEventCommand(expectedEvent));

        // multiple description - last description accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + DESCRIPTION_DESC_SPECTRA
                + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI, new AddEventCommand(expectedEvent));

        // multiple locations - last location accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_SPECTRA
                + LOCATION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI, new AddEventCommand(expectedEvent));

        // multiple datetime - last datetime accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI
                + DATETIME_DESC_SPECTRA + DATETIME_DESC_DEEPAVALI, new AddEventCommand(expectedEvent));

    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Event expectedEvent = new EventBuilder().withTitle(VALID_TITLE_SPECTRA)
                .withDescription(VALID_DESCRIPTION_SPECTRA)
                .withLocation(VALID_LOCATION_SPECTRA).withDatetime(VALID_DATETIME_SPECTRA).build();
        // command word
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_SPECTRA + DESCRIPTION_DESC_SPECTRA
                + LOCATION_DESC_SPECTRA + DATETIME_DESC_SPECTRA, new AddEventCommand(expectedEvent));

    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing title prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_TITLE_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI, expectedMessage);

        // missing description prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + VALID_DESCRIPTION_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI, expectedMessage);

        // missing location prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI
                + VALID_LOCATION_DEEPAVALI + DATETIME_DESC_DEEPAVALI, expectedMessage);

        // missing datetime prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_DEEPAVALI + VALID_DATETIME_DEEPAVALI, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid title
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE_DESC
                + DESCRIPTION_DESC_DEEPAVALI + LOCATION_DESC_DEEPAVALI
                + DATETIME_DESC_DEEPAVALI, Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid description
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI
                + INVALID_DESCRIPTION_DESC + LOCATION_DESC_DEEPAVALI
                + DATETIME_DESC_DEEPAVALI, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // invalid location
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI
                + DESCRIPTION_DESC_DEEPAVALI + INVALID_LOCATION_DESC
                + DATETIME_DESC_DEEPAVALI, Location.MESSAGE_LOCATION_CONSTRAINTS);

        // invalid datetime
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_DESC_DEEPAVALI
                + DESCRIPTION_DESC_DEEPAVALI + LOCATION_DESC_DEEPAVALI
                + INVALID_DATETIME_DESC, Datetime.MESSAGE_DATETIME_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE_DESC
                + DESCRIPTION_DESC_DEEPAVALI + LOCATION_DESC_DEEPAVALI
                + INVALID_DATETIME_DESC, Title.MESSAGE_TITLE_CONSTRAINTS);
    }
}
