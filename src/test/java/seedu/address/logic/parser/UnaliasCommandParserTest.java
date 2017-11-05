package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.ALIAS_KEYWORD_DESC_MONDAY;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.alias.UnaliasCommand;
import seedu.address.logic.parser.alias.UnaliasCommandParser;
import seedu.address.model.alias.Keyword;

//@@author deep4k
public class UnaliasCommandParserTest {

    private UnaliasCommandParser parser = new UnaliasCommandParser();

    @Test
    public void parse_keywordValid_success() throws IllegalValueException {

        Keyword expectedKeyword = new Keyword("mon");

        assertParseSuccess(parser, UnaliasCommand.COMMAND_WORD + ALIAS_KEYWORD_DESC_MONDAY,
                new UnaliasCommand(expectedKeyword));
    }

    @Test
    public void parse_keywordInvalid_failure() throws IllegalValueException {
        assertParseFailure(parser, UnaliasCommand.COMMAND_WORD + " gg",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, UnaliasCommand.MESSAGE_USAGE));
    }
}
