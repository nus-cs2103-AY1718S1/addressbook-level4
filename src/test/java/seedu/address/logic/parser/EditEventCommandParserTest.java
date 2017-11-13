package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DESC_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_TIME_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_NAME;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_EVENT_TIME_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_DESC_SECOND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_NAME_FIRST;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_TIME_SECOND;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.EditEventCommand;
import seedu.address.logic.commands.EditEventCommand.EditEventDescriptor;
import seedu.address.model.event.EventDescription;
import seedu.address.model.event.EventName;
import seedu.address.model.event.EventTime;
import seedu.address.testutil.EventDescriptorBuilder;

// @@author Adoby7
/**
 * Test the EditEventCommandParser
 */
public class EditEventCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditEventCommand.MESSAGE_USAGE);

    private EditEventCommandParser parser = new EditEventCommandParser();

    @Test
    public void parseMissingPartsFailure() {
        // no index specified
        assertParseFailure(parser, EVENT_NAME_FIRST, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditEventCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseInvalidParametersFailure() {
        // negative index
        assertParseFailure(parser, "-5" + EVENT_NAME_FIRST, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + EVENT_NAME_FIRST, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 not an integer", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parseInvalidValueFailure() {
        assertParseFailure(parser, "1" + INVALID_EVENT_NAME,
            EventName.MESSAGE_EVENT_NAME_CONSTRAINTS); // invalid name

        assertParseFailure(parser, "1" + INVALID_EVENT_DESC,
            EventDescription.MESSAGE_EVENT_DESCRIPTION_CONSTRAINTS); // invalid phone

        assertParseFailure(parser, "1" + INVALID_EVENT_TIME_FIRST,
            EventTime.MESSAGE_EVENT_TIME_CONSTRAINTS); // invalid email
    }

    @Test
    public void parseAllFieldsSpecified() {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + EVENT_DESC_SECOND + EVENT_TIME_SECOND + EVENT_NAME_FIRST;

        EditEventDescriptor descriptor = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_FIRST)
            .withDescription(VALID_EVENT_DESC_SECOND).withTime(VALID_EVENT_TIME_SECOND).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parsePartFieldsSpecified() {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + EVENT_NAME_FIRST;
        EditEventDescriptor descriptor = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_FIRST).build();
        EditEventCommand expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + EVENT_DESC_SECOND;
        descriptor = new EventDescriptorBuilder().withDescription(VALID_EVENT_DESC_SECOND).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + EVENT_TIME_SECOND;
        descriptor = new EventDescriptorBuilder().withTime(VALID_EVENT_TIME_SECOND).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + EVENT_DESC_SECOND + EVENT_TIME_SECOND;
        descriptor = new EventDescriptorBuilder().withDescription(VALID_EVENT_DESC_SECOND)
            .withTime(VALID_EVENT_TIME_SECOND).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + EVENT_TIME_SECOND + EVENT_NAME_FIRST;
        descriptor = new EventDescriptorBuilder().withName(VALID_EVENT_NAME_FIRST)
            .withTime(VALID_EVENT_TIME_SECOND).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        userInput = targetIndex.getOneBased() + EVENT_DESC_SECOND + EVENT_NAME_FIRST;
        descriptor = new EventDescriptorBuilder().withDescription(VALID_EVENT_DESC_SECOND)
            .withName(VALID_EVENT_NAME_FIRST).build();
        expectedCommand = new EditEventCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
