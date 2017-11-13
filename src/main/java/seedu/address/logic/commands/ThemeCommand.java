package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author yewshengkai
/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "t";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Switches to selected theme\n"
            + "1. No theme\n"
            + "2. Blue theme\n"
            + "3. Dark theme\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_THEME_SUCCESS = "Theme updated: %1$s";

    private final Index targetIndex;
    public ThemeCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {
        String[] themeList = {"NoTheme", "BlueTheme", "DarkTheme"};

        if (targetIndex.getZeroBased() >= themeList.length) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME_INDEX);
        }

        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_THEME_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.targetIndex.equals(((ThemeCommand) other).targetIndex)); // state check
    }

}
