package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Switches the current theme to a theme identified using it's index from the themes list in address book.
 */
public class SwitchThemeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "switchtheme";
    public static final String COMMAND_ALIAS = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches the current theme to the theme identified by the index number in the themes list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SWITCH_THEME_SUCCESS = "Switched Theme: %1$s";

    private final Index targetIndex;

    public SwitchThemeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult executeUndoableCommand() throws CommandException {

        ArrayList<String> themesList = model.getThemesList();

        if (targetIndex.getZeroBased() >= themesList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        String themeToChange = themesList.get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(themeToChange));

        return new CommandResult(String.format(MESSAGE_SWITCH_THEME_SUCCESS, themeToChange));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchThemeCommand // instanceof handles nulls
                && this.targetIndex.equals(((SwitchThemeCommand) other).targetIndex)); // state check
    }
}
