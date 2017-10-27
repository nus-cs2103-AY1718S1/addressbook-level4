package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ListClearedEvent;
import seedu.address.model.Rolodex;

/**
 * Clears the rolodex.
 */
public class ClearCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "clear";
    public static final Set<String> COMMAND_WORD_ABBREVIATIONS =
            new HashSet<>(Arrays.asList(COMMAND_WORD, "c"));
    public static final String COMMAND_HOTKEY = "Ctrl+Shift+C";

    public static final String MESSAGE_SUCCESS = "Rolodex has been cleared!";


    @Override
    public CommandResult executeUndoableCommand() {
        requireNonNull(model);
        model.resetData(new Rolodex());

        EventsCenter.getInstance().post(new ListClearedEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
