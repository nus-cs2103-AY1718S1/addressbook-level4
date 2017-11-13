package seedu.address.logic.hints;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.logic.commands.hints.ClearCommandHint;
import seedu.address.logic.commands.hints.ExitCommandHint;
import seedu.address.logic.commands.hints.HelpCommandHint;
import seedu.address.logic.commands.hints.HistoryCommandHint;
import seedu.address.logic.commands.hints.ListCommandHint;
import seedu.address.logic.commands.hints.NoArgumentsHint;
import seedu.address.logic.commands.hints.RedoCommandHint;
import seedu.address.logic.commands.hints.UndoCommandHint;

public class NoArgumentsHintTest {

    @Test
    public void helpCommandHintTest() {
        HelpCommandHint helpCommandHint = new HelpCommandHint("help");
        assertNoArgHint(helpCommandHint, " shows user guide", "help");

        helpCommandHint = new HelpCommandHint("help ");
        assertNoArgHint(helpCommandHint, "shows user guide", "help ");

        helpCommandHint = new HelpCommandHint("help s");
        assertNoArgHint(helpCommandHint, " shows user guide", "help s");
    }

    @Test
    public void listCommandHintTest() {
        ListCommandHint listCommandHint = new ListCommandHint("list");
        assertNoArgHint(listCommandHint, " lists all contacts", "list");

        listCommandHint = new ListCommandHint("list ");
        assertNoArgHint(listCommandHint, "lists all contacts", "list ");

        listCommandHint = new ListCommandHint("list s");
        assertNoArgHint(listCommandHint, " lists all contacts", "list s");
    }

    @Test
    public void undoCommandHintTest() {
        UndoCommandHint undoCommandHint = new UndoCommandHint("undo");
        assertNoArgHint(undoCommandHint, " undo previous command", "undo");

        undoCommandHint = new UndoCommandHint("undo ");
        assertNoArgHint(undoCommandHint, "undo previous command", "undo ");

        undoCommandHint = new UndoCommandHint("undo s");
        assertNoArgHint(undoCommandHint, " undo previous command", "undo s");
    }

    @Test
    public void redoCommandHintTest() {
        RedoCommandHint redoCommandHint = new RedoCommandHint("redo");
        assertNoArgHint(redoCommandHint, " redo command", "redo");

        redoCommandHint = new RedoCommandHint("redo ");
        assertNoArgHint(redoCommandHint, "redo command", "redo ");

        redoCommandHint = new RedoCommandHint("redo s");
        assertNoArgHint(redoCommandHint, " redo command", "redo s");
    }

    @Test
    public void historyCommandHintTest() {
        HistoryCommandHint historyCommandHint = new HistoryCommandHint("history");
        assertNoArgHint(historyCommandHint, " shows command history", "history");

        historyCommandHint = new HistoryCommandHint("history ");
        assertNoArgHint(historyCommandHint, "shows command history", "history ");

        historyCommandHint = new HistoryCommandHint("history s");
        assertNoArgHint(historyCommandHint, " shows command history", "history s");
    }

    @Test
    public void exitCommandHintTest() {
        ExitCommandHint exitCommandHint = new ExitCommandHint("exit");
        assertNoArgHint(exitCommandHint, " exits the application", "exit");

        exitCommandHint = new ExitCommandHint("exit ");
        assertNoArgHint(exitCommandHint, "exits the application", "exit ");

        exitCommandHint = new ExitCommandHint("exit s");
        assertNoArgHint(exitCommandHint, " exits the application", "exit s");
    }

    @Test
    public void cleatCommandHintTest() {
        ClearCommandHint clearCommandHint = new ClearCommandHint("clear");
        assertNoArgHint(clearCommandHint, " clears all contacts", "clear");

        clearCommandHint = new ClearCommandHint("clear ");
        assertNoArgHint(clearCommandHint, "clears all contacts", "clear ");

        clearCommandHint = new ClearCommandHint("clear s");
        assertNoArgHint(clearCommandHint, " clears all contacts", "clear s");
    }


    private void assertNoArgHint(NoArgumentsHint noArgumentsHint, String description, String autocomplete) {
        assertEquals(description, noArgumentsHint.getDescription());
        assertEquals(autocomplete, noArgumentsHint.autocomplete());
    }
}
