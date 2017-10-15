package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
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
        assertEquals(parser.parseForCommands("a"), Arrays.asList(new String[] {AddCommand.COMMAND_USAGE, "a"}));
        assertEquals(parser.parseForCommands("b"), Arrays.asList(new String[] {"b"}));
        assertEquals(parser.parseForCommands("c"), Arrays.asList(new String[] {ClearCommand.COMMAND_USAGE, "c"}));
        assertEquals(parser.parseForCommands("d"), Arrays.asList(new String[] {DeleteCommand.COMMAND_USAGE, "d"}));
        assertEquals(parser.parseForCommands("e"),
                Arrays.asList(new String[] {EditCommand.COMMAND_USAGE, ExitCommand.COMMAND_USAGE, "e"}));
        assertEquals(parser.parseForCommands("f"), Arrays.asList(new String[] {FindCommand.COMMAND_USAGE, "f"}));
        assertEquals(parser.parseForCommands("g"), Arrays.asList(new String[] {"g"}));
        assertEquals(parser.parseForCommands("h"),
                Arrays.asList(new String[] {HelpCommand.COMMAND_USAGE, HistoryCommand.COMMAND_USAGE, "h"}));
        assertEquals(parser.parseForCommands("i"), Arrays.asList(new String[] {"i"}));
        assertEquals(parser.parseForCommands("j"), Arrays.asList(new String[] {"j"}));
        assertEquals(parser.parseForCommands("k"), Arrays.asList(new String[] {"k"}));
        assertEquals(parser.parseForCommands("l"), Arrays.asList(new String[] {ListCommand.COMMAND_USAGE, "l"}));
        assertEquals(parser.parseForCommands("m"), Arrays.asList(new String[] {"m"}));
        assertEquals(parser.parseForCommands("n"), Arrays.asList(new String[] {"n"}));
        assertEquals(parser.parseForCommands("o"), Arrays.asList(new String[] {"o"}));
        assertEquals(parser.parseForCommands("p"), Arrays.asList(new String[] {"p"}));
        assertEquals(parser.parseForCommands("q"), Arrays.asList(new String[] {"q"}));
        assertEquals(parser.parseForCommands("r"),
                Arrays.asList(new String[] {RedoCommand.COMMAND_USAGE, RemarkCommand.COMMAND_USAGE,
                    RemoveTagCommand.COMMAND_USAGE, "r"}));
        assertEquals(parser.parseForCommands("s"), Arrays.asList(new String[] {SelectCommand.COMMAND_USAGE, "s"}));
        assertEquals(parser.parseForCommands("t"), Arrays.asList(new String[] {"t"}));
        assertEquals(parser.parseForCommands("u"), Arrays.asList(new String[] {UndoCommand.COMMAND_USAGE, "u"}));
        assertEquals(parser.parseForCommands("v"), Arrays.asList(new String[] {"v"}));
        assertEquals(parser.parseForCommands("w"), Arrays.asList(new String[] {"w"}));
        assertEquals(parser.parseForCommands("x"), Arrays.asList(new String[] {"x"}));
        assertEquals(parser.parseForCommands("y"), Arrays.asList(new String[] {"y"}));
        assertEquals(parser.parseForCommands("z"), Arrays.asList(new String[] {"z"}));
    }

}
