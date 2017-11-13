package seedu.address.logic.parser;

import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.EditRelationshipCommand;
import seedu.address.model.person.Name;
import seedu.address.model.relationship.ConfidenceEstimate;

//@@author joanneong
public class EditRelationshipCommandParserTest {

    private EditRelationshipCommandParser parser = new EditRelationshipCommandParser();

    @Test
    public void parse_validArgs_returnsEditRelationshipCommand() {
        assertParseSuccess(parser, "1 2", new EditRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED));
    }

    @Test
    public void parse_validArgsWithNameAndCE_returnsEditRelationshipCommand() throws Exception {
        try {
            Name name = new Name("friends");
            ConfidenceEstimate confidenceEstimate = new ConfidenceEstimate(90);
            assertParseSuccess(parser, "1 2 n/friends ce/90",
                    new EditRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, name, confidenceEstimate));
        } catch (IllegalValueException ive) {
            fail("This is not supposed to fail");
        }
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditRelationshipCommand.MESSAGE_USAGE));
    }

}
