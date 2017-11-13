//@@author yuheng222

package seedu.address.logic.commands;

import java.util.ArrayList;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.SelectThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Selects a theme based on the index provided by the user, which can be referred from the themes list.
 */
public class SelectThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "st";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches the current theme to the theme identified by the index number in the themes list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SWITCH_THEME_SUCCESS = "Switched Theme: %1$s";

    private final Index targetIndex;

    public SelectThemeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        ArrayList<String> themesList = model.getThemesList();

        if (targetIndex.getZeroBased() >= themesList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME_DISPLAYED_INDEX);
        }

        String themeToChange = themesList.get(targetIndex.getZeroBased());

        EventsCenter.getInstance().post(new SelectThemeRequestEvent(themeToChange));

        return new CommandResult(String.format(MESSAGE_SWITCH_THEME_SUCCESS, themeToChange));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectThemeCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectThemeCommand) other).targetIndex)); // state check
    }
}
