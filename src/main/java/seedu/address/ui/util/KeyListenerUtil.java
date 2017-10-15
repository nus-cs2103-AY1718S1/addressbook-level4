package seedu.address.ui.util;

import java.util.HashMap;

import javafx.scene.input.KeyCombination;

/**
 * A utility class for mapping key events.
 */
public class KeyListenerUtil {
    public static HashMap<String, KeyCombination> getKeys() {
        return new HashMap<String, KeyCombination>() {
            {
                put("FOCUS_PERSON_LIST", KeyCombination.valueOf("Esc"));

                put("FOCUS_COMMAND_BOX", KeyCombination.valueOf("Enter"));

                put("DELETE_SELECTION", KeyCombination.valueOf("Ctrl+D"));

                put("CLEAR_LIST", KeyCombination.valueOf("Ctrl+Shift+D"));

                put("VIEW_HISTORY", KeyCombination.valueOf("Ctrl+H"));

                put("UNDO", KeyCombination.valueOf("Ctrl+Z"));

                put("REDO", KeyCombination.valueOf("Ctrl+Y"));

                put("LIST", KeyCombination.valueOf("Ctrl+l"));
            }
        };
    }
}
