package seedu.address.ui.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.input.KeyCombination;

/**
 * A utility class for mapping key events.
 */
public class KeyListenerUtil {

    public static final KeyCombination FOCUS_PERSON_LIST = KeyCombination.valueOf("Esc");
    public static final KeyCombination FOCUS_COMMAND_BOX = KeyCombination.valueOf("Enter");
    public static final KeyCombination DELETE_SELECTION = KeyCombination.valueOf("Ctrl+D");
    public static final KeyCombination CLEAR = KeyCombination.valueOf("Ctrl+Shift+D");
    public static final KeyCombination HISTORY = KeyCombination.valueOf("Ctrl+H");
    public static final KeyCombination UNDO = KeyCombination.valueOf("Ctrl+Z");
    public static final KeyCombination REDO = KeyCombination.valueOf("Ctrl+Y");
    public static final KeyCombination LIST = KeyCombination.valueOf("Ctrl+L");

    public static final Set<KeyCombination> POSSIBLE_KEY_COMBINATIONS =
            new HashSet<>(Arrays.asList(
                    FOCUS_PERSON_LIST,
                    FOCUS_COMMAND_BOX,
                    DELETE_SELECTION,
                    CLEAR,
                    HISTORY,
                    UNDO,
                    REDO,
                    LIST
            ));
}
