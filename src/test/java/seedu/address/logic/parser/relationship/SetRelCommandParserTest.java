//@@author huiyiiih
package seedu.address.logic.parser.relationship;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_REL_DESC;
import static seedu.address.logic.commands.CommandTestUtil.REL_DESC_COLLEAGUE;
import static seedu.address.logic.commands.CommandTestUtil.REL_DESC_JANE_SIBLINGS;
import static seedu.address.logic.commands.CommandTestUtil.REL_DESC_SIBLINGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REL_COLLEAGUE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_REL_SIBLINGS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADD_RELATIONSHIP;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DELETE_RELATIONSHIP;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.relationship.SetRelCommand;
import seedu.address.logic.commands.relationship.SetRelCommand.EditPerson;
import seedu.address.model.relationship.Relationship;
import seedu.address.testutil.EditPersonBuilder;

public class SetRelCommandParserTest {

    private static final String REL_ADD_EMPTY = " " + PREFIX_ADD_RELATIONSHIP;
    private static final String REL_DELETE_EMPTY = " " + PREFIX_DELETE_RELATIONSHIP;
    private static final String WRONG_REL_PREFIX = " ar.";
    private static final boolean addPrefixPresent = false;

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, SetRelCommand.MESSAGE_USAGE);

    private SetRelCommandParser parser = new SetRelCommandParser();

    @Test
    public void parse_missingInput_failure() {
        // no index specified
        assertParseFailure(parser, VALID_NAME_AMY, MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 7" + REL_DESC_SIBLINGS, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 1" + REL_DESC_SIBLINGS, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_invalidValueFollowedByValidValue_success() {
        // no other valid values specified
        Index targetIndexOne = INDEX_FIRST_PERSON;
        Index targetIndexTwo = INDEX_SECOND_PERSON;
        String userInputOne = targetIndexOne.getOneBased() + " " + targetIndexTwo.getOneBased()
            + INVALID_REL_DESC;
        assertParseFailure(parser, userInputOne, Relationship.MESSAGE_REL_CONSTRAINTS);

        // other valid values specified
        userInputOne = targetIndexOne.getOneBased() + " " + targetIndexTwo.getOneBased() + REL_DESC_SIBLINGS;
        EditPerson descriptor = new EditPersonBuilder().withToAddRel(VALID_REL_SIBLINGS).build();
        SetRelCommand expectedCommand = new SetRelCommand(targetIndexOne, targetIndexTwo, descriptor, addPrefixPresent);
        assertParseSuccess(parser, userInputOne, expectedCommand);
        //assertParseSuccess(parser, userInputTwo, expectedCommand);
    }
    @Test
    public void parse_multiplePrefix_failure() {
        assertParseFailure(parser, "1 2" + REL_DESC_COLLEAGUE + REL_DESC_SIBLINGS,
            SetRelCommandParser.ONE_RELATIONSHIP_ALLOWED);
        assertParseFailure(parser, "1 2" + REL_DELETE_EMPTY + VALID_REL_SIBLINGS + REL_DELETE_EMPTY
            + VALID_REL_COLLEAGUE, SetRelCommandParser.ONE_RELATIONSHIP_ALLOWED);
    }
    @Test
    public void parse_invalidInput_failure() {
        assertParseFailure(parser, "1 2" +  INVALID_REL_DESC, Relationship.MESSAGE_REL_CONSTRAINTS);
        assertParseFailure(parser, "1 2" + REL_ADD_EMPTY, SetRelCommandParser.NULL_RELATION_INPUT);
        assertParseFailure(parser, "1 2" + REL_DELETE_EMPTY, SetRelCommandParser.NULL_RELATION_INPUT);
    }
    @Test
    public void parse_invalidPrefix_failure() {
        assertParseFailure(parser, "1 2" + WRONG_REL_PREFIX + VALID_REL_SIBLINGS,
            MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_validInput_success() {
        Index targetIndexOne = INDEX_FIRST_PERSON;
        Index targetIndexTwo = INDEX_SECOND_PERSON;
        String userInput = targetIndexOne.getOneBased() + " " + targetIndexTwo.getOneBased() + REL_DESC_SIBLINGS;

        EditPerson descriptor = new EditPersonBuilder().withToAddRel(VALID_REL_SIBLINGS).build();
        SetRelCommand expectedCommand = new SetRelCommand(targetIndexOne, targetIndexTwo, descriptor, addPrefixPresent);

        assertParseSuccess(parser, userInput, expectedCommand);
    }
    @Test
    public void parse_invalidIndex_failure() {
        assertParseFailure(parser, "a b" + REL_DESC_JANE_SIBLINGS, MESSAGE_INVALID_FORMAT);
    }
    @Test
    public void parse_sameIndexes_failure() {
        assertParseFailure(parser, "1 1" + REL_DESC_JANE_SIBLINGS, SetRelCommandParser.SAME_INDEX_ERROR);
    }
}
//@@author
