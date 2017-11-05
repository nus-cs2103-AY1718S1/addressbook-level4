package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.commands.FindCommand;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    //@@author angtianlannus
    @Test
    public void parse_validTrimmedKeywordsToList_returnsFindCommand() {
        List<String> keywordsInputs = new ArrayList<>();
        keywordsInputs.add("MA1101A");
        keywordsInputs.add("MA1101B");
        keywordsInputs.add("MA1101C");
        assertParseSuccess(parser, "MA1101A   MA1101B   MA1101C ", new FindCommand(keywordsInputs));
    }

}
