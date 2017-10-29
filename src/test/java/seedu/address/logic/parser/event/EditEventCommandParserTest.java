package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.DATE_DESC_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.NAME_DESC_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VENUE_DESC_EVENT1;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.EditEventCommand;
import seedu.address.logic.commands.event.EditEventCommand.EditEventDescriptor;
import seedu.address.model.property.PropertyManager;
import seedu.address.testutil.EditEventDescriptorBuilder;

public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventParser parser = new EditEventParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_EVENT1, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + NAME_DESC_EVENT1, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + NAME_DESC_EVENT1, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_NAME_DESC,
                PropertyManager.getPropertyConstraintMessage("n")); // invalid name
        assertParseFailure(parser, "1" + INVALID_DATE_DESC,
                PropertyManager.getPropertyConstraintMessage("dt")); // invalid date time
        assertParseFailure(parser, "1" + INVALID_ADDRESS_DESC,
                PropertyManager.getPropertyConstraintMessage("a")); // invalid address


        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_NAME_DESC + INVALID_DATE_DESC + VALID_VENUE_EVENT2,
                PropertyManager.getPropertyConstraintMessage("n"));
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + DATE_DESC_EVENT1 + VENUE_DESC_EVENT1 + NAME_DESC_EVENT1;

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT1)
                .withTime(VALID_DATE_EVENT1).withVenue(VALID_VENUE_EVENT1).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + DATE_DESC_EVENT2 + VENUE_DESC_EVENT1;

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withTime(VALID_DATE_EVENT2)
                .withVenue(VALID_VENUE_EVENT1).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // name
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + NAME_DESC_EVENT1;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withName(VALID_NAME_EVENT1).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date/time
        userInput = targetIndex.getOneBased() + DATE_DESC_EVENT1;
        descriptor = new EditEventDescriptorBuilder().withTime(VALID_DATE_EVENT1).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // address
        userInput = targetIndex.getOneBased() + VENUE_DESC_EVENT1;
        descriptor = new EditEventDescriptorBuilder().withVenue(VALID_VENUE_EVENT1).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
