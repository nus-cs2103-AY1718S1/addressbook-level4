package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.AddRelationshipCommand;
import seedu.address.model.person.Name;
import seedu.address.model.relationship.ConfidenceEstimate;
import seedu.address.model.relationship.RelationshipDirection;


//@@author wenmogu
public class AddRelationshipCommandParserTest {
    private AddRelationshipCommandParser parser = new AddRelationshipCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        // with long names and confidence estimate - accepted
        assertParseSuccess(parser, "1 2 directed n/friends enemy neighbour ce/12",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED,
                        new Name("friends enemy neighbour"), new ConfidenceEstimate(12)));

        // direction ignore case, with long names and confidence estimate - accepted
        assertParseSuccess(parser, "1 2 uNdIREcted n/friends enemy neighbour ce/0.0000000001",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.UNDIRECTED,
                        new Name("friends enemy neighbour"), new ConfidenceEstimate(0.0000000001)));

        // direction ignore case, with long names and confidence estimate - accepted
        assertParseSuccess(parser, "1 2 uNdIREcted ce/0.0000000001 n/friends enemy neighbour",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.UNDIRECTED,
                        new Name("friends enemy neighbour"), new ConfidenceEstimate(0.0000000001)));
    }

    @Test
    public void parse_optionalFieldsMissing_success() throws Exception {
        // no name and no confidence estimate - accepted
        assertParseSuccess(parser, "1 2 directed",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED,
                        Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED));

        // direction ignore cases - accepted
        assertParseSuccess(parser, "1 2 dIREcted", new AddRelationshipCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED, Name.UNSPECIFIED, ConfidenceEstimate.UNSPECIFIED));

        // with long names - accepted
        assertParseSuccess(parser, "1 2 dIREcted n/friends enemy neighbour",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED,
                        new Name("friends enemy neighbour"), ConfidenceEstimate.UNSPECIFIED));

        // with confidence estimate - accepted
        assertParseSuccess(parser, "1 2 dIREcted ce/12",
                new AddRelationshipCommand(INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, RelationshipDirection.DIRECTED,
                        Name.UNSPECIFIED, new ConfidenceEstimate(12)));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE);

        // no direction input
        assertParseFailure(parser, "1 2", expectedMessage);

        // not enough person indexes
        assertParseFailure(parser, "1 dIREctedd", expectedMessage);

        //missing all fields
        assertParseFailure(parser, "addre ", expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddRelationshipCommand.MESSAGE_USAGE);
        String expectedInvalidIndexMsg = String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                AddRelationshipCommand.MESSAGE_USAGE);

        // wrong order of inputs
        assertParseFailure(parser, "1 dIREctedd 2", expectedMessage);

        // redundant spacings
        assertParseFailure(parser, "1 2                dIREctedd", expectedMessage);

        // duplicate index
        assertParseFailure(parser, "1 1 dIREctedd", expectedMessage);

        // invalid index
        assertParseFailure(parser, "0 1 dIREctedd", expectedInvalidIndexMsg);
    }
}
