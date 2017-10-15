package seedu.address.ui.util;

import java.util.HashMap;

import javafx.scene.input.KeyCombination;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * A utility class for mapping key events.
 */
public class KeyListenerUtil {
    public static HashMap<String, KeyCombination> getKeys() {

        HashMap<String, KeyCombination> keys = new HashMap<>();

        keys.put("FOCUS_PERSON_LIST", KeyCombination.valueOf("Esc"));
        keys.put("FOCUS_COMMAND_BOX", KeyCombination.valueOf("Enter"));
        keys.put("DELETE_SELECTION", KeyCombination.valueOf("Ctrl+D"));
        keys.put(ClearCommand.COMMAND_WORD, KeyCombination.valueOf("Ctrl+Shift+D"));
        keys.put(HistoryCommand.COMMAND_WORD, KeyCombination.valueOf("Ctrl+H"));
        keys.put(UndoCommand.COMMAND_WORD, KeyCombination.valueOf("Ctrl+Z"));
        keys.put(RedoCommand.COMMAND_WORD, KeyCombination.valueOf("Ctrl+Y"));
        keys.put(ListCommand.COMMAND_WORD, KeyCombination.valueOf("Ctrl+L"));

        return keys;
    }
}
