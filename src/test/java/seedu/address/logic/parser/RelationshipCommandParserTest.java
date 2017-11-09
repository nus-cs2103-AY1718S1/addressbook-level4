package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RELATIONSHIP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.RelationshipCommand;
import seedu.address.model.person.Relationship;

//@@author Ernest
public class RelationshipCommandParserTest {
    private RelationshipCommandParser parser = new RelationshipCommandParser();

    @Test
    public void parseIndexSpecifiedFailure() throws Exception {
        final Relationship relation = new Relationship("Some relation.");

        // have relations
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RELATIONSHIP.toString() + " " + relation;
        RelationshipCommand expectedCommand = new RelationshipCommand(INDEX_FIRST_PERSON, relation);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no relations
        userInput = targetIndex.getOneBased() + " " + PREFIX_RELATIONSHIP.toString();
        expectedCommand = new RelationshipCommand(INDEX_FIRST_PERSON, new Relationship(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseNoFieldSpecifiedFailure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RelationshipCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, RelationshipCommand.COMMAND_WORD, expectedMessage);
    }
}
