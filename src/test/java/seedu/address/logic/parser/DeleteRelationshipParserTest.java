package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalPersons.INDEX_FIRST_PERSON;
import static seedu.address.testutil.TypicalPersons.INDEX_SECOND_PERSON;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.logic.commands.DeleteRelationshipCommand;

public class DeleteRelationshipParserTest {
    private DeleteRelationshipCommandParser parser = new DeleteRelationshipCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        assertParseSuccess(parser, "1 2", new DeleteRelationshipCommand(INDEX_FIRST_PERSON,
                INDEX_SECOND_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRelationshipCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "          ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRelationshipCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "           1        2", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRelationshipCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "1 1", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRelationshipCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0 2", String.format(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX,
                DeleteRelationshipCommand.MESSAGE_USAGE));
        assertParseFailure(parser, "0 0", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteRelationshipCommand.MESSAGE_USAGE));
    }
}
