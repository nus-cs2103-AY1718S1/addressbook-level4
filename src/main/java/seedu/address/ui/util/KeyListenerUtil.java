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

    public static final KeyCombination FOCUS_PERSON_LIST = KeyCombination.valueOf("Esc");
    public static final KeyCombination FOCUS_PERSON_LIST_ALT = KeyCombination.valueOf("Ctrl+Left");
    public static final KeyCombination FOCUS_COMMAND_BOX = KeyCombination.valueOf("Enter");
    public static final KeyCombination FOCUS_RESULT_DISPLAY = KeyCombination.valueOf("Ctrl+Right");
    public static final KeyCombination DELETE_SELECTION = KeyCombination.valueOf("Ctrl+D");
    public static final KeyCombination CLEAR = KeyCombination.valueOf(ClearCommand.COMMAND_HOTKEY);
    public static final KeyCombination HISTORY = KeyCombination.valueOf(HistoryCommand.COMMAND_HOTKEY);
    public static final KeyCombination UNDO = KeyCombination.valueOf(UndoCommand.COMMAND_HOTKEY);
    public static final KeyCombination REDO = KeyCombination.valueOf(RedoCommand.COMMAND_HOTKEY);
    public static final KeyCombination LIST = KeyCombination.valueOf(ListCommand.COMMAND_HOTKEY);
    public static final KeyCombination OPEN_FILE = KeyCombination.valueOf(OpenCommand.COMMAND_HOTKEY);
    public static final KeyCombination NEW_FILE = KeyCombination.valueOf(NewCommand.COMMAND_HOTKEY);
    public static final KeyCombination ADD = KeyCombination.valueOf(AddCommand.COMMAND_HOTKEY);
    public static final KeyCombination EDIT = KeyCombination.valueOf(EditCommand.COMMAND_HOTKEY);
    public static final KeyCombination FIND = KeyCombination.valueOf(FindCommand.COMMAND_HOTKEY);
    public static final KeyCombination SELECT = KeyCombination.valueOf(SelectCommand.COMMAND_HOTKEY);
    public static final KeyCombination DELETE = KeyCombination.valueOf(DeleteCommand.COMMAND_HOTKEY);

    public static final Set<KeyCombination> POSSIBLE_KEY_COMBINATIONS =
            new HashSet<>(Arrays.asList(
                    FOCUS_PERSON_LIST,
                    FOCUS_COMMAND_BOX,
                    DELETE_SELECTION,
                    CLEAR,
                    HISTORY,
                    UNDO,
                    REDO,
                    LIST,
                    OPEN_FILE,
                    NEW_FILE,
                    ADD,
                    EDIT,
                    FIND,
                    SELECT,
                    DELETE
            ));
}
