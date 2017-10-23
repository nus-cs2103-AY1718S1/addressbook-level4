//@@author A0162268B
package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.DESCRIPTION_SOCCER;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TIMESLOT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_TITLE;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.TIMESLOT_SOCCER;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.TITLE_SOCCER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DESCRIPTION_SOCCER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TIMESLOT_SOCCER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TITLE_SOCCER;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.event.AddEventCommand;
import seedu.address.model.event.Event;
import seedu.address.model.event.Title;
import seedu.address.model.event.timeslot.Timeslot;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Event expectedEvent = new EventBuilder().withTitle(VALID_TITLE_SOCCER).withTimeslot(VALID_TIMESLOT_SOCCER)
                .withDescription(VALID_DESCRIPTION_SOCCER).build();

        // multiple titles - last title accepted
        String event1 = AddEventCommand.COMMAND_WORD + TITLE_MIDTERM + TITLE_SOCCER + TIMESLOT_SOCCER
                + DESCRIPTION_SOCCER + TIMESLOT_SOCCER;
        assertParseSuccess(parser, event1, new AddEventCommand(expectedEvent));

        // multiple timings - last timing accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + TIMESLOT_MIDTERM + TIMESLOT_SOCCER
                + DESCRIPTION_SOCCER, new AddEventCommand(expectedEvent));

        // multiple descriptions - last description accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + TIMESLOT_SOCCER + DESCRIPTION_MIDTERM
                + DESCRIPTION_SOCCER, new AddEventCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);
        // missing title prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_TITLE_SOCCER + TIMESLOT_SOCCER
                + DESCRIPTION_SOCCER, expectedMessage);
        // missing timing prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + VALID_TIMESLOT_SOCCER
                + VALID_TIMESLOT_MIDTERM, expectedMessage);
        // missing email prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + TIMESLOT_SOCCER
                + VALID_DESCRIPTION_SOCCER, expectedMessage);
        // all prefixes missing
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_TITLE_SOCCER + VALID_TIMESLOT_SOCCER
                + VALID_DESCRIPTION_SOCCER, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid title
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE + TIMESLOT_SOCCER + DESCRIPTION_SOCCER,
                Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid timing
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + INVALID_TIMESLOT + DESCRIPTION_SOCCER,
                Timeslot.MESSAGE_TIMESLOT_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE + INVALID_TIMESLOT + DESCRIPTION_SOCCER,
                Title.MESSAGE_TITLE_CONSTRAINTS);
    }
}
