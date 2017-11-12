//@@author namvd2709
package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.SelectCommand;

public class AutocompleteManagerTest {
    @Test
    public void attemptAutocomplete() {
        AutocompleteManager manager = new AutocompleteManager();

        // test commands which can be matched by 1 character
        assertEquals(manager.attemptAutocomplete("c"), ClearCommand.COMMAND_WORD);

        // test commands which can be matched by 1 character but 2 is supplied
        assertEquals(manager.attemptAutocomplete("de"), DeleteCommand.COMMAND_WORD);
        assertEquals(manager.attemptAutocomplete("se"), SelectCommand.COMMAND_WORD);

        // test commands which can be matched by 2 characters
        assertEquals(manager.attemptAutocomplete("ed"), EditCommand.COMMAND_WORD);
        assertEquals(manager.attemptAutocomplete("ex"), ExitCommand.COMMAND_WORD);
        assertEquals(manager.attemptAutocomplete("he"), HelpCommand.COMMAND_WORD);
        assertEquals(manager.attemptAutocomplete("hi"), HistoryCommand.COMMAND_WORD);

        // test commands which can't be matched by 1 character but 1 is supplied
        assertEquals(manager.attemptAutocomplete("e"), "e");
    }
}
