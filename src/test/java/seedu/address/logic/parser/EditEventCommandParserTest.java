package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;

import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.DESC_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.HEADER_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DESC_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_HEADER_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESC_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_BIRTHDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DATE_MEETING;
import static seedu.address.logic.commands.CommandTestUtil.VALID_HEADER_MEETING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_THIRD_EVENT;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.person.event.Desc;
import seedu.address.model.person.event.EventDate;
import seedu.address.model.person.event.Header;
import seedu.address.testutil.EditEventDescriptorBuilder;

//@@author royceljh
public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, VALID_HEADER_MEETING, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + HEADER_DESC_MEETING, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + HEADER_DESC_MEETING, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_HEADER_DESC,
                Header.MESSAGE_HEADER_CONSTRAINTS); // invalid header
        assertParseFailure(parser, "1" + INVALID_DESC_DESC,
                Desc.MESSAGE_DESC_CONSTRAINTS); // invalid desc
        assertParseFailure(parser, "1" + INVALID_EVENT_DATE_DESC,
                EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS); // invalid eventDate


        // invalid desc followed by valid eventDate
        assertParseFailure(parser, "1" + INVALID_DESC_DESC + EVENT_DATE_DESC_MEETING,
                Desc.MESSAGE_DESC_CONSTRAINTS);

        // valid date followed by invalid date. The test case for invalid date followed by valid date
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + EVENT_DATE_DESC_BIRTHDAY + INVALID_EVENT_DATE_DESC,
                EventDate.MESSAGE_EVENT_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_HEADER_DESC + INVALID_DESC_DESC
                + VALID_EVENT_DATE_BIRTHDAY, Header.MESSAGE_HEADER_CONSTRAINTS);
    }

    @Test
    public void parse_allFieldsSpecified_success() {
        Index targetIndex = INDEX_SECOND_EVENT;
        String userInput = targetIndex.getOneBased() + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_MEETING
                + HEADER_DESC_MEETING;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withHeader(VALID_HEADER_MEETING)
                .withDesc(VALID_DESC_BIRTHDAY).withEventDate(VALID_EVENT_DATE_MEETING).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + DESC_DESC_BIRTHDAY + EVENT_DATE_DESC_MEETING;

        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_MEETING).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() {
        // header
        Index targetIndex = INDEX_THIRD_EVENT;
        String userInput = targetIndex.getOneBased() + HEADER_DESC_MEETING;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withHeader(VALID_HEADER_MEETING).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // desc
        userInput = targetIndex.getOneBased() + DESC_DESC_MEETING;
        descriptor = new EditEventDescriptorBuilder().withDesc(VALID_DESC_MEETING).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // eventDate
        userInput = targetIndex.getOneBased() + EVENT_DATE_DESC_MEETING;
        descriptor = new EditEventDescriptorBuilder().withEventDate(VALID_EVENT_DATE_MEETING).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + INVALID_DESC_DESC + DESC_DESC_BIRTHDAY;
        EditEventDescriptor descriptor = new EditEventDescriptorBuilder().withDesc(VALID_DESC_BIRTHDAY).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + EVENT_DATE_DESC_BIRTHDAY + INVALID_DESC_DESC + DESC_DESC_BIRTHDAY;
        descriptor = new EditEventDescriptorBuilder().withDesc(VALID_DESC_BIRTHDAY)
                .withEventDate(VALID_EVENT_DATE_BIRTHDAY).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
