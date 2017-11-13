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
import static seedu.address.logic.commands.CommandTestUtil.TITLE_DESC_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATETIME_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_DEEPAVALI;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOCATION_SPECTRA;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_SPECTRA;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.Datetime;
import seedu.address.model.event.Description;
import seedu.address.model.event.Location;
import seedu.address.model.event.Title;
import seedu.address.testutil.EditEventDescriptorBuilder;

//@@author kaiyu92
public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_TITLE_SPECTRA, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + TITLE_DESC_SPECTRA, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + TITLE_DESC_SPECTRA, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC,
                Title.MESSAGE_TITLE_CONSTRAINTS); // invalid title
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC,
                Description.MESSAGE_DESCRIPTION_CONSTRAINTS); // invalid description
        assertParseFailure(parser, "1" + INVALID_LOCATION_DESC,
                Location.MESSAGE_LOCATION_CONSTRAINTS); // invalid location
        assertParseFailure(parser, "1" + INVALID_DATETIME_DESC,
                Datetime.MESSAGE_DATETIME_CONSTRAINTS); // invalid datetime

        // invalid description followed by valid location
        assertParseFailure(parser, "1" + INVALID_DESCRIPTION_DESC
                + LOCATION_DESC_SPECTRA, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // valid description followed by invalid location. The test case for invalid phone followed by valid phone
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + DESCRIPTION_DESC_DEEPAVALI
                + INVALID_DESCRIPTION_DESC, Description.MESSAGE_DESCRIPTION_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_TITLE_DESC + INVALID_DESCRIPTION_DESC
                + VALID_LOCATION_SPECTRA + VALID_DATETIME_SPECTRA, Title.MESSAGE_TITLE_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_DEEPAVALI
                + LOCATION_DESC_SPECTRA + DATETIME_DESC_SPECTRA + TITLE_DESC_SPECTRA;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTitle(VALID_TITLE_SPECTRA)
                .withDescription(VALID_DESCRIPTION_DEEPAVALI).withLocation(VALID_LOCATION_SPECTRA)
                .withDatetime(VALID_DATETIME_SPECTRA).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_DEEPAVALI + LOCATION_DESC_SPECTRA;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_DEEPAVALI)
                .withLocation(VALID_LOCATION_SPECTRA).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // title
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = targetIndex.getOneBased() + TITLE_DESC_SPECTRA;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTitle(VALID_TITLE_SPECTRA).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // description
        userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_SPECTRA;
        descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_SPECTRA).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // location
        userInput = targetIndex.getOneBased() + LOCATION_DESC_SPECTRA;
        descriptor = new EditEventDescriptorBuilder().withLocation(VALID_LOCATION_SPECTRA).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // datetime
        userInput = targetIndex.getOneBased() + DATETIME_DESC_SPECTRA;
        descriptor = new EditEventDescriptorBuilder().withDatetime(VALID_DATETIME_SPECTRA).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + DESCRIPTION_DESC_SPECTRA + DATETIME_DESC_SPECTRA
                + LOCATION_DESC_SPECTRA + DESCRIPTION_DESC_SPECTRA + DATETIME_DESC_SPECTRA + LOCATION_DESC_SPECTRA
                + DESCRIPTION_DESC_DEEPAVALI + DATETIME_DESC_DEEPAVALI + LOCATION_DESC_DEEPAVALI;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_DEEPAVALI)
                .withLocation(VALID_LOCATION_DEEPAVALI).withDatetime(VALID_DATETIME_DEEPAVALI)
                .build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + INVALID_DESCRIPTION_DESC + DESCRIPTION_DESC_DEEPAVALI;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder()
                .withDescription(VALID_DESCRIPTION_DEEPAVALI).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + LOCATION_DESC_DEEPAVALI + INVALID_DESCRIPTION_DESC
                + DATETIME_DESC_DEEPAVALI + DESCRIPTION_DESC_DEEPAVALI;
        descriptor = new EditEventDescriptorBuilder().withDescription(VALID_DESCRIPTION_DEEPAVALI)
                .withLocation(VALID_LOCATION_DEEPAVALI).withDatetime(VALID_DATETIME_DEEPAVALI).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
