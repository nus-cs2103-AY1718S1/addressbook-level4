package seedu.address.logic;

import static junit.framework.TestCase.assertEquals;
import static seedu.address.logic.Autocomplete.autocomplete;

import org.junit.Test;

import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.MusicCommand;

//@@author goweiwen
public class AutocompleteTest {
    @Test
    public void autocomplete_emptyInput_returnsEmpty() {
        assertEquals("", autocomplete(""));
    }

    @Test
    public void autocomplete_invalidCommand_returnsItself() {
        assertEquals(
                "should-not-complete-to-any-commands",
                autocomplete("should-not-complete-to-any-commands"));
    }

    @Test
    public void autocomplete_incompleteCommand_returnsFullCommandAndTrailingSpace() {
        assertEquals(
                AddCommand.COMMAND_WORD + " ",
                autocomplete(AddCommand.COMMAND_WORD.substring(0, AddCommand.COMMAND_WORD.length() - 1)));
        assertEquals(
                ListCommand.COMMAND_WORD + " ",
                autocomplete(ListCommand.COMMAND_WORD.substring(0, ListCommand.COMMAND_WORD.length() - 1)));
    }

    @Test
    public void autocomplete_validCommands_returnsParameters() {
        assertEquals(AddCommand.COMMAND_WORD + " n/", autocomplete(AddCommand.COMMAND_WORD));
        assertEquals(AddCommand.COMMAND_WORD + " n/", autocomplete(AddCommand.COMMAND_WORD + " "));
        assertEquals(AddCommand.COMMAND_WORD + " n/", autocomplete(AddCommand.COMMAND_WORD + " n"));
        assertEquals(AddCommand.COMMAND_WORD + " p/", autocomplete(AddCommand.COMMAND_WORD + " n/"));

        assertEquals(EditCommand.COMMAND_WORD + " 1", autocomplete(EditCommand.COMMAND_WORD));
        assertEquals(EditCommand.COMMAND_WORD + " 2", autocomplete(EditCommand.COMMAND_WORD + " 1"));
        assertEquals(EditCommand.COMMAND_WORD + " 1 n/", autocomplete(EditCommand.COMMAND_WORD + " 1 "));
        assertEquals(EditCommand.COMMAND_WORD + " 1 n/", autocomplete(EditCommand.COMMAND_WORD + " 1 n"));
        assertEquals(EditCommand.COMMAND_WORD + " 1 p/", autocomplete(EditCommand.COMMAND_WORD + " 1 n/"));

        assertEquals(FindCommand.COMMAND_WORD + " n/", autocomplete(FindCommand.COMMAND_WORD));
        assertEquals(FindCommand.COMMAND_WORD + " n/", autocomplete(FindCommand.COMMAND_WORD + " n"));
        assertEquals(FindCommand.COMMAND_WORD + " p/", autocomplete(FindCommand.COMMAND_WORD + " n/"));

        if (!MusicCommand.isMusicPlaying()) {
            assertEquals(MusicCommand.COMMAND_WORD + " play", autocomplete(MusicCommand.COMMAND_WORD));
            assertEquals(MusicCommand.COMMAND_WORD + " play", autocomplete(MusicCommand.COMMAND_WORD + " sto"));
        } else {
            assertEquals(MusicCommand.COMMAND_WORD + " stop", autocomplete(MusicCommand.COMMAND_WORD));
            assertEquals(MusicCommand.COMMAND_WORD + " stop", autocomplete(MusicCommand.COMMAND_WORD + " sto"));
        }
    }

}
