package seedu.address.logic.autocomplete.parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.autocomplete.parser.AutoCompleteCommandParser;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.FindTagCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.RemoveTagCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

public class AutoCompleteCommandParserTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final AutoCompleteCommandParser parser = new AutoCompleteCommandParser();

    @Test
    public void parseAutoCompleteCommandAllAlphabets() throws Exception {
        assertEquals(parser.parseForPossibilities("a"), Arrays.asList(new String[] {AddCommand.COMMAND_USAGE, "a"}));
        assertEquals(parser.parseForPossibilities("b"), Arrays.asList(new String[] {"b"}));
        assertEquals(parser.parseForPossibilities("c"), Arrays.asList(new String[] {ClearCommand.COMMAND_USAGE, "c"}));
        assertEquals(parser.parseForPossibilities("d"), Arrays.asList(new String[] {DeleteCommand.COMMAND_USAGE, "d"}));
        assertEquals(parser.parseForPossibilities("e"),
                Arrays.asList(new String[] {EditCommand.COMMAND_USAGE, ExitCommand.COMMAND_USAGE, "e"}));
        assertEquals(parser.parseForPossibilities("f"),
                Arrays.asList(new String[] {FindCommand.COMMAND_USAGE, FindTagCommand.COMMAND_USAGE, "f"}));
        assertEquals(parser.parseForPossibilities("g"), Arrays.asList(new String[] {"g"}));
        assertEquals(parser.parseForPossibilities("h"),
                Arrays.asList(new String[] {HelpCommand.COMMAND_USAGE, HistoryCommand.COMMAND_USAGE, "h"}));
        assertEquals(parser.parseForPossibilities("i"), Arrays.asList(new String[] {"i"}));
        assertEquals(parser.parseForPossibilities("j"), Arrays.asList(new String[] {"j"}));
        assertEquals(parser.parseForPossibilities("k"), Arrays.asList(new String[] {"k"}));
        assertEquals(parser.parseForPossibilities("l"), Arrays.asList(new String[] {ListCommand.COMMAND_USAGE, "l"}));
        assertEquals(parser.parseForPossibilities("m"), Arrays.asList(new String[] {"m"}));
        assertEquals(parser.parseForPossibilities("n"), Arrays.asList(new String[] {"n"}));
        assertEquals(parser.parseForPossibilities("o"), Arrays.asList(new String[] {"o"}));
        assertEquals(parser.parseForPossibilities("p"), Arrays.asList(new String[] {"p"}));
        assertEquals(parser.parseForPossibilities("q"), Arrays.asList(new String[] {"q"}));
        assertEquals(parser.parseForPossibilities("r"),
                Arrays.asList(new String[] {RedoCommand.COMMAND_USAGE, RemarkCommand.COMMAND_USAGE,
                    RemoveTagCommand.COMMAND_USAGE, "r"}));
        assertEquals(parser.parseForPossibilities("s"), Arrays.asList(new String[] {SelectCommand.COMMAND_USAGE, "s"}));
        assertEquals(parser.parseForPossibilities("t"), Arrays.asList(new String[] {"t"}));
        assertEquals(parser.parseForPossibilities("u"), Arrays.asList(new String[] {UndoCommand.COMMAND_USAGE, "u"}));
        assertEquals(parser.parseForPossibilities("v"), Arrays.asList(new String[] {"v"}));
        assertEquals(parser.parseForPossibilities("w"), Arrays.asList(new String[] {"w"}));
        assertEquals(parser.parseForPossibilities("x"), Arrays.asList(new String[] {"x"}));
        assertEquals(parser.parseForPossibilities("y"), Arrays.asList(new String[] {"y"}));
        assertEquals(parser.parseForPossibilities("z"), Arrays.asList(new String[] {"z"}));
    }

}
