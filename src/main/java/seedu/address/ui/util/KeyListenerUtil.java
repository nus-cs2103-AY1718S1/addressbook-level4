package seedu.address.ui.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import javafx.scene.input.KeyCombination;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.NewRolodexCommand;
import seedu.address.logic.commands.OpenRolodexCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.RemarkCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

/**
 * A utility class for mapping key events.
 */
public class KeyListenerUtil {

    // Non-command events
    public static final KeyCombination KEY_COMBINATION_FOCUS_PERSON_LIST = KeyCombination.valueOf("Esc");
    public static final KeyCombination KEY_COMBINATION_FOCUS_PERSON_LIST_ALT = KeyCombination.valueOf("Ctrl+Left");
    public static final KeyCombination KEY_COMBINATION_FOCUS_COMMAND_BOX = KeyCombination.valueOf("Enter");
    public static final KeyCombination KEY_COMBINATION_FOCUS_RESULT_DISPLAY = KeyCombination.valueOf("Ctrl+Right");
    public static final KeyCombination KEY_COMBINATION_DELETE_SELECTION = KeyCombination.valueOf("Ctrl+Shift+D");

    // Command events
    public static final KeyCombination KEY_COMBINATION_CLEAR = KeyCombination.valueOf(ClearCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_HISTORY = KeyCombination.valueOf(HistoryCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_UNDO = KeyCombination.valueOf(UndoCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_REDO = KeyCombination.valueOf(RedoCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_LIST = KeyCombination.valueOf(ListCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_OPEN_FILE =
            KeyCombination.valueOf(OpenRolodexCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_NEW_FILE =
            KeyCombination.valueOf(NewRolodexCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_ADD = KeyCombination.valueOf(AddCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_EDIT = KeyCombination.valueOf(EditCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_FIND = KeyCombination.valueOf(FindCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_SELECT = KeyCombination.valueOf(SelectCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_DELETE = KeyCombination.valueOf(DeleteCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_DELETE_ALTERNATIVE =
            KeyCombination.valueOf(DeleteCommand.COMMAND_HOTKEY_ALTERNATIVE);
    public static final KeyCombination KEY_COMBINATION_REMARK = KeyCombination.valueOf(RemarkCommand.COMMAND_HOTKEY);
    public static final KeyCombination KEY_COMBINATION_EMAIL = KeyCombination.valueOf(EmailCommand.COMMAND_HOTKEY);

    public static final Set<KeyCombination> POSSIBLE_KEY_COMBINATIONS =
            new HashSet<>(Arrays.asList(
                    KEY_COMBINATION_FOCUS_PERSON_LIST,
                    KEY_COMBINATION_FOCUS_PERSON_LIST_ALT,
                    KEY_COMBINATION_FOCUS_COMMAND_BOX,
                    KEY_COMBINATION_FOCUS_RESULT_DISPLAY,
                    KEY_COMBINATION_DELETE_SELECTION,
                    KEY_COMBINATION_CLEAR,
                    KEY_COMBINATION_HISTORY,
                    KEY_COMBINATION_UNDO,
                    KEY_COMBINATION_REDO,
                    KEY_COMBINATION_LIST,
                    KEY_COMBINATION_OPEN_FILE,
                    KEY_COMBINATION_NEW_FILE,
                    KEY_COMBINATION_ADD,
                    KEY_COMBINATION_EDIT,
                    KEY_COMBINATION_FIND,
                    KEY_COMBINATION_SELECT,
                    KEY_COMBINATION_DELETE,
                    KEY_COMBINATION_DELETE_ALTERNATIVE,
                    KEY_COMBINATION_REMARK,
                    KEY_COMBINATION_EMAIL
            ));
}
