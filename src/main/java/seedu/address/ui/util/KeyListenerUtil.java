package seedu.address.ui.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.input.KeyCombination;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewCommand;
import seedu.address.logic.commands.OpenCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * A utility class for mapping key events.
 */
public class KeyListenerUtil {

    public static final KeyCombination FOCUS_PERSON_LIST_KEY_COMBINATION = KeyCombination.valueOf("Esc");
    public static final KeyCombination FOCUS_PERSON_LIST_ALT_KEY_COMBINATION = KeyCombination.valueOf("Ctrl+Left");
    public static final KeyCombination FOCUS_COMMAND_BOX_KEY_COMBINATION = KeyCombination.valueOf("Enter");
    public static final KeyCombination FOCUS_RESULT_DISPLAY_KEY_COMBINATION = KeyCombination.valueOf("Ctrl+Right");
    public static final KeyCombination DELETE_SELECTION_KEY_COMBINATION = KeyCombination.valueOf("Ctrl+D");
    public static final KeyCombination CLEAR_KEY_COMBINATION = KeyCombination.valueOf(ClearCommand.COMMAND_HOTKEY);
    public static final KeyCombination HISTORY_KEY_COMBINATION = KeyCombination.valueOf(HistoryCommand.COMMAND_HOTKEY);
    public static final KeyCombination UNDO_KEY_COMBINATION = KeyCombination.valueOf(UndoCommand.COMMAND_HOTKEY);
    public static final KeyCombination REDO_KEY_COMBINATION = KeyCombination.valueOf(RedoCommand.COMMAND_HOTKEY);
    public static final KeyCombination LIST_KEY_COMBINATION = KeyCombination.valueOf(ListCommand.COMMAND_HOTKEY);
    public static final KeyCombination OPEN_FILE_KEY_COMBINATION = KeyCombination.valueOf(OpenCommand.COMMAND_HOTKEY);
    public static final KeyCombination NEW_FILE_KEY_COMBINATION = KeyCombination.valueOf(NewCommand.COMMAND_HOTKEY);
    public static final KeyCombination ADD_KEY_COMBINATION = KeyCombination.valueOf(AddCommand.COMMAND_HOTKEY);
    public static final KeyCombination EDIT_KEY_COMBINATION = KeyCombination.valueOf(EditCommand.COMMAND_HOTKEY);
    public static final KeyCombination FIND_KEY_COMBINATION = KeyCombination.valueOf(FindCommand.COMMAND_HOTKEY);
    public static final KeyCombination SELECT_KEY_COMBINATION = KeyCombination.valueOf(SelectCommand.COMMAND_HOTKEY);
    public static final KeyCombination DELETE_KEY_COMBINATION = KeyCombination.valueOf(DeleteCommand.COMMAND_HOTKEY);

    public static final Set<KeyCombination> POSSIBLE_KEY_COMBINATIONS =
            new HashSet<>(Arrays.asList(
                    FOCUS_PERSON_LIST_KEY_COMBINATION,
                    FOCUS_COMMAND_BOX_KEY_COMBINATION,
                    DELETE_SELECTION_KEY_COMBINATION,
                    CLEAR_KEY_COMBINATION,
                    HISTORY_KEY_COMBINATION,
                    UNDO_KEY_COMBINATION,
                    REDO_KEY_COMBINATION,
                    LIST_KEY_COMBINATION,
                    OPEN_FILE_KEY_COMBINATION,
                    NEW_FILE_KEY_COMBINATION,
                    ADD_KEY_COMBINATION,
                    EDIT_KEY_COMBINATION,
                    FIND_KEY_COMBINATION,
                    SELECT_KEY_COMBINATION,
                    DELETE_SELECTION_KEY_COMBINATION
            ));
}
