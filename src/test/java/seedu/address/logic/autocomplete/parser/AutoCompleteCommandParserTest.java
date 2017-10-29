package seedu.address.logic.autocomplete.parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemoveTagCommand;

public class AutoCompleteCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AutoCompleteCommandParser parser = new AutoCompleteCommandParser();

    @Test
    public void parseAutoCompleteCommandAllAlphabets() throws Exception {
        // no match
        assertEquals(parser.parseForPossibilities("b"), Arrays.asList(new String[] {"b"}));

        // single match
        assertEquals(parser.parseForPossibilities("a"), Arrays.asList(new String[] {AddCommand.COMMAND_USAGE, "a"}));

        // multiple match
        assertEquals(parser.parseForPossibilities("r"),
                Arrays.asList(new String[] {RedoCommand.COMMAND_USAGE, RemarkCommand.COMMAND_USAGE,
                    RemoveTagCommand.COMMAND_USAGE, "r"}));
    }

}
