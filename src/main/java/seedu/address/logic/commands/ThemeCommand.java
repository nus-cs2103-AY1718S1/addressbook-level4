package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.UiPart;

/**
 * Set theme of current addressbook based on index listed or theme name.
 */
//@@author Choony93
public class ThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Changes current theme based on index.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1\n"
            + "OR\n"
            + ": Changes current theme based on theme name.\n"
            + "Parameters: NAME \n"
            + "Example: " + COMMAND_WORD + " modena";

    public static final String MESSAGE_THEME_CHANGE_SUCCESS = "Theme successfully changed to: %1$s";
    public static final String MESSAGE_THEME_INVALID_NAME = "Could not find theme Name or Index.\n"
            + "Available themes:\n%1$s";

    private final boolean usingName;
    private String themeName = "";
    private Index targetIndex = Index.fromOneBased(Integer.parseInt("1"));

    public ThemeCommand(Index targetIndex) {
        this.usingName = false;
        this.targetIndex = targetIndex;
    }

    public ThemeCommand(String themeName) {
        this.usingName = true;
        this.themeName = themeName;
    }

    @Override
    public CommandResult execute() throws CommandException {

        boolean themeNameNotFound = false;
        if (this.usingName) {
            themeNameNotFound = true;
            for (int i = 0; i < UiPart.THEME_LIST_DIR.size(); i++) {
                String tempName = UiPart.getThemeNameByIndex(i);
                if (tempName.contains(")")) {
                    tempName = tempName.substring(tempName.lastIndexOf(")") + 2);
                }
                if (tempName.equalsIgnoreCase(this.themeName)) {
                    this.targetIndex = Index.fromOneBased(i + 1);
                    themeNameNotFound = false;
                }
            }
        }

        if (targetIndex.getZeroBased() >= UiPart.THEME_LIST_DIR.size() || themeNameNotFound) {
            String themeList = "";
            for (int i = 0; i < UiPart.THEME_LIST_DIR.size(); i++) {
                themeList += (Integer.toString(i + 1) + ". " + UiPart.getThemeNameByIndex(i) + "\n");
            }
            throw new CommandException(String.format(MESSAGE_THEME_INVALID_NAME, themeList));
        }

        EventsCenter.getInstance().post(new ChangeThemeEvent(targetIndex));
        return new CommandResult(String.format(MESSAGE_THEME_CHANGE_SUCCESS,
                UiPart.getThemeNameByIndex(this.targetIndex.getZeroBased())));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.targetIndex.equals(((ThemeCommand) other).targetIndex)) // state check
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.themeName.equals(((ThemeCommand) other).themeName)); // state check
    }
}
