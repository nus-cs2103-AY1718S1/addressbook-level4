package seedu.address.logic.commands;

//@@author itsdickson

import java.util.ArrayList;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Switches the current theme to a theme identified using it's index from the themes list in address book.
 */
public class SwitchThemeCommand extends Command {

    public static final String COMMAND_WORD = "switch";
    public static final String COMMAND_ALIAS = "sw";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches the current theme to the theme identified by the index number in the themes list.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_DARK_THEME_SUCCESS = "Switched Theme: Dark Theme";
    public static final String MESSAGE_LIGHT_THEME_SUCCESS = "Switched Theme: Light Theme";

    public static final String VIEW_PATH = "/view/";

    private final Index targetIndex;

    public SwitchThemeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }


    @Override
    public CommandResult execute() throws CommandException {

        ArrayList<String> themesList = model.getThemesList();

        if (targetIndex.getZeroBased() >= themesList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
        }

        String themeToChange = themesList.get(targetIndex.getZeroBased());
        String currentTheme = model.getCurrentTheme();

        if (currentTheme.equals(VIEW_PATH + themeToChange)) {
            throw new CommandException(Messages.MESSAGE_INVALID_SWITCH);
        }

        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(themeToChange));

        if (themeToChange.equals("DarkTheme.css")) {
            return new CommandResult(MESSAGE_DARK_THEME_SUCCESS);
        } else {
            return new CommandResult(MESSAGE_LIGHT_THEME_SUCCESS);
        }
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchThemeCommand // instanceof handles nulls
                && this.targetIndex.equals(((SwitchThemeCommand) other).targetIndex)); // state check
    }
}
//@@author
