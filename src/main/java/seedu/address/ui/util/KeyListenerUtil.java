package seedu.address.ui.util;

import java.util.HashMap;

import javafx.scene.input.KeyCombination;

/**
 * A utility class for mapping key events.
 */
public class KeyListenerUtil {
    public static HashMap<String, KeyCombination> getKeys() {

        HashMap<String, KeyCombination> keys = new HashMap<>();

        keys.put("FOCUS_PERSON_LIST", KeyCombination.valueOf("Esc"));
        keys.put("FOCUS_COMMAND_BOX", KeyCombination.valueOf("Enter"));
        keys.put("DELETE_SELECTION", KeyCombination.valueOf("Ctrl+D"));
        keys.put("CLEAR_LIST", KeyCombination.valueOf("Ctrl+Shift+D"));
        keys.put("VIEW_HISTORY", KeyCombination.valueOf("Ctrl+H"));
        keys.put("UNDO", KeyCombination.valueOf("Ctrl+Z"));
        keys.put("REDO", KeyCombination.valueOf("Ctrl+Y"));
        keys.put("LIST", KeyCombination.valueOf("Ctrl+l"));

        return keys;
    }
}
