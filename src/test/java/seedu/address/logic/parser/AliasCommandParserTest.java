package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_KEYWORD_DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_REPRESENTATION_DESC_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALIAS_KEYWORD_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ALIAS_REPRESENTATION_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_KEYWORD_MONDAY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ALIAS_REPRESENTATION_MONDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.alias.AliasCommand;
import seedu.address.logic.parser.alias.AliasCommandParser;
import seedu.address.model.alias.AliasToken;
import seedu.address.model.alias.Keyword;
import seedu.address.model.alias.Representation;
import seedu.address.testutil.AliasTokenBuilder;

//@@author deep4k
public class AliasCommandParserTest {

    private AliasCommandParser parser = new AliasCommandParser();

    @Test
    public void parse_keywordAndRepresentation_success() {
        AliasToken expectedToken = new AliasTokenBuilder().withKeyword(VALID_ALIAS_KEYWORD_MONDAY).withRepresentation(
                VALID_ALIAS_REPRESENTATION_MONDAY).build();

        assertParseSuccess(parser, AliasCommand.COMMAND_WORD + ALIAS_KEYWORD_DESC_MONDAY
                + ALIAS_REPRESENTATION_DESC_MONDAY, new AliasCommand(expectedToken));
    }

    @Test
    public void parse_representationMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AliasCommand.MESSAGE_USAGE);

        assertParseFailure(parser, AliasCommand.COMMAND_WORD + ALIAS_KEYWORD_DESC_MONDAY, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid keyword
        assertParseFailure(parser, AliasCommand.COMMAND_WORD + INVALID_ALIAS_KEYWORD_DESC
                + ALIAS_REPRESENTATION_DESC_MONDAY, Keyword.MESSAGE_NAME_CONSTRAINTS);

        // invalid representation
        assertParseFailure(parser, AliasCommand.COMMAND_WORD + ALIAS_KEYWORD_DESC_MONDAY
                + INVALID_ALIAS_REPRESENTATION_DESC, Representation.MESSAGE_NAME_CONSTRAINTS);
    }
}
