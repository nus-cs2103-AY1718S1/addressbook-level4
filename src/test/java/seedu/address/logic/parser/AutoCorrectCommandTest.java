package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearTaskCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.SearchCommand;
import seedu.address.logic.commands.UndoCommand;

//@@author JYL123
public class AutoCorrectCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private AutoCorrectCommand autoCorrectCommand;
    private String defaultResult;

    @Before
    public void setUp() {
        defaultResult = "Unknown Command";
        autoCorrectCommand = new AutoCorrectCommand();
    }

    @Test
    /**
     *  Check for the situation where the misspelt word cannot be corrected.
     */
    public void executeCommandIsNotFoundThrowsException() {
        /*
        * Check when the misspelt command with edit distance 1 which
        * cannot be auto-corrected against the wrong corresponding command
        */
        assertEquals(defaultResult, autoCorrectCommand.checkMisspeltWords("search", "dad"));
        assertEquals(defaultResult, autoCorrectCommand.checkMisspeltWords("add", "seacrh"));

        /*
        * Check when the misspelt command with edit distance 2 which
        * cannot be auto-corrected against the wrong corresponding command
        */
        assertEquals(defaultResult, autoCorrectCommand.checkMisspeltWords("add", "ddaw"));
        assertEquals(defaultResult, autoCorrectCommand.checkMisspeltWords("add", "sreach"));
    }

    @Test
    /**
     * Check for the input parameter, throws exception when it is null or empty string
     */
    public void executeInputCommandNullException() {
        thrown.expect(IllegalArgumentException.class);
        autoCorrectCommand.correctWord("");
        autoCorrectCommand.correctWord(null);
    }

    @Test
    /**
     * Check the method will return the correct command for edit distance 1 input
     */
    public void executeEditDistance1MatchCommandTest() {
        final String inputCommandSearch = "serach";
        ArrayList<String> misspeltWordsPoolSearch = autoCorrectCommand.editDistance1(SearchCommand.COMMAND_WORD);
        String correctCommandSearch = autoCorrectCommand.correctWord(inputCommandSearch);
        assertTrue(misspeltWordsPoolSearch.stream().anyMatch(e -> e.equals(inputCommandSearch)));
        assertEquals(SearchCommand.COMMAND_WORD, correctCommandSearch);

        final String inputCommandFind = "fnid";
        ArrayList<String> misspeltWordsPoolFind = autoCorrectCommand.editDistance1(FindCommand.COMMAND_WORD);
        String correctCommandFind = autoCorrectCommand.correctWord(inputCommandFind);
        assertTrue(misspeltWordsPoolFind.stream().anyMatch(e -> e.equals(inputCommandFind)));
        assertEquals(FindCommand.COMMAND_WORD, correctCommandFind);

        final String inputCommandUndo = "unod";
        ArrayList<String> misspeltWordsPoolUndo = autoCorrectCommand.editDistance1(UndoCommand.COMMAND_WORD);
        String correctCommandUndo = autoCorrectCommand.correctWord(inputCommandUndo);
        assertTrue(misspeltWordsPoolUndo.stream().anyMatch(e -> e.equals(inputCommandUndo)));
        assertEquals(UndoCommand.COMMAND_WORD, correctCommandUndo);

        final String inputCommandClearTask = "cleartsak";
        ArrayList<String> misspeltWordsPoolClearTask = autoCorrectCommand.editDistance1(ClearTaskCommand.COMMAND_WORD);
        String correctCommandClearTask = autoCorrectCommand.correctWord(inputCommandClearTask);
        assertTrue(misspeltWordsPoolClearTask.stream().anyMatch(e -> e.equals(inputCommandClearTask)));
        assertEquals(ClearTaskCommand.COMMAND_WORD, correctCommandClearTask);
    }

    @Test
    /**
     * Check the method will return the correct command for edit distance 2 input
     */
    public void executeEditDistance2MatchCommandTest() {
        final String inputCommandSearch = "sreach";
        String correctCommandSearch = autoCorrectCommand.correctWord(inputCommandSearch);
        assertEquals(SearchCommand.COMMAND_WORD, correctCommandSearch);

        final String inputCommandFind = "fndi";
        String correctCommandFind = autoCorrectCommand.correctWord(inputCommandFind);
        assertEquals(FindCommand.COMMAND_WORD, correctCommandFind);

        final String inputCommandUndo = "uond";
        String correctCommandUndo = autoCorrectCommand.correctWord(inputCommandUndo);
        assertEquals(UndoCommand.COMMAND_WORD, correctCommandUndo);

        final String inputCommandClearTask = "cleartsat";
        String correctCommandClearTask = autoCorrectCommand.correctWord(inputCommandClearTask);
        assertEquals(ClearTaskCommand.COMMAND_WORD, correctCommandClearTask);
    }

    @Test
    /**
     * Check the method will not return the correct command if
     * the first alphabet of the input command does not match any available commands.
     */
    public void executeCorrectWordsNotMatchTest() {
        final String defaultResult = "Unknown Command";

        final String inputCommandSearch = "ereach";
        String correctCommandSearch = autoCorrectCommand.correctWord(inputCommandSearch);
        assertEquals(defaultResult, correctCommandSearch);

        final String inputCommandFind = "ddam";
        String correctCommandFind = autoCorrectCommand.correctWord(inputCommandFind);
        assertEquals(defaultResult, correctCommandFind);
    }

    @Test
    /**
     * Check the method will return the correct alias
     * as the auto-correct is not meant to auto-correct alias
     */
    public void executeAliasCommandTest() {
        //One-alphabet alise
        final String inputCommandAdd = "a";
        String correctCommandAdd = autoCorrectCommand.correctWord(inputCommandAdd);
        assertEquals(AddCommand.COMMAND_WORD_ALIAS, correctCommandAdd);

        final String inputCommandUnknown = "r";
        String correctCommandUnknown = autoCorrectCommand.correctWord(inputCommandUnknown);
        assertEquals(inputCommandUnknown, correctCommandUnknown);

        //Two-alphabet alise
        final String inputCommandMultifilter = "mf";
        String correctCommandMultifilter = autoCorrectCommand.correctWord(inputCommandMultifilter);
        assertEquals(inputCommandMultifilter, correctCommandMultifilter);

        final String inputCommandUnknownTwoAlphabets = "yx";
        String correctCommandUnknownTwoAlphabets = autoCorrectCommand.correctWord(inputCommandUnknownTwoAlphabets);
        assertNotEquals(inputCommandUnknownTwoAlphabets, correctCommandUnknownTwoAlphabets);

    }

    //@@author lancehaoh
    @After
    /**
     * Clears the autocorrect status messages to prevent them from persisting among
     * tests
     */
    public void clearAutocorrectStatusMessage() {
        AutoCorrectCommand.clearMessageToUser();
    }
}
