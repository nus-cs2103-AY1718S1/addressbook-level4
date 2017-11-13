package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeBrightThemeEvent;
import seedu.address.commons.events.ui.ChangeDarkThemeEvent;
import seedu.address.commons.events.ui.ChangeDefaultThemeEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author ZhangH795

/**
 * Changes theme to the user theme of choice.
 */
public class SwitchThemeCommand extends Command {

    public static final String COMMAND_WORD = "theme";
    public static final String DARK_THEME_WORD1 = "dark";
    public static final String DARK_THEME_WORD2 = "Twilight";
    public static final String DARK_THEME_WORD3 = "1";
    public static final String BRIGHT_THEME_WORD1 = "bright";
    public static final String BRIGHT_THEME_WORD2 = "Sunburst";
    public static final String BRIGHT_THEME_WORD3 = "2";
    public static final String DEFAULT_THEME_WORD1 = "default";
    public static final String DEFAULT_THEME_WORD2 = "Minimalism";
    public static final String DEFAULT_THEME_WORD3 = "3";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change into the theme of choice of iConnect.\n"
            + "Available themes: 1.Twilight 2.Sunburst 3.Minimalism\n"
            + "Parameters: THEME\n"
            + "Example: " + COMMAND_WORD + " Twilight";

    public static final String MESSAGE_THEME_CHANGE_SUCCESS = "Theme changed to: %1$s";
    public static final String MESSAGE_INVALID_INDEX = "The index %1$s is invalid.\n";
    public static final String MESSAGE_UNKNOWN_THEME = "The theme %1$s is unknown.\n";

    private final String userThemeInput;
    private String themeChoice;

    public SwitchThemeCommand(String themeChoice) {
        this.userThemeInput = themeChoice;
    }

    @Override
    public CommandResult execute() throws CommandException {

        if (userThemeInput.matches("\\d+")) {
            if (DARK_THEME_WORD3.equals(userThemeInput)) {
                themeChoice = DARK_THEME_WORD2;
            } else if (BRIGHT_THEME_WORD3.equals(userThemeInput)) {
                themeChoice = BRIGHT_THEME_WORD2;
            } else if (DEFAULT_THEME_WORD3.equals(userThemeInput)) {
                themeChoice = DEFAULT_THEME_WORD2;
            } else {
                throw new CommandException(String.format(MESSAGE_INVALID_INDEX, userThemeInput));
            }
        } else {
            if (userThemeInput.toLowerCase().contains(DARK_THEME_WORD1)
                    || DARK_THEME_WORD1.contains(userThemeInput.toLowerCase())
                    || userThemeInput.toLowerCase().contains(DARK_THEME_WORD2.toLowerCase())
                    || DARK_THEME_WORD2.toLowerCase().contains(userThemeInput.toLowerCase())) {
                themeChoice = DARK_THEME_WORD2;
            } else if (userThemeInput.toLowerCase().contains(BRIGHT_THEME_WORD1)
                    || BRIGHT_THEME_WORD1.contains(userThemeInput.toLowerCase())
                    || userThemeInput.toLowerCase().contains(BRIGHT_THEME_WORD2.toLowerCase())
                    || BRIGHT_THEME_WORD2.toLowerCase().contains(userThemeInput.toLowerCase())) {
                themeChoice = BRIGHT_THEME_WORD2;
            } else if (userThemeInput.toLowerCase().contains(DEFAULT_THEME_WORD1)
                    || DEFAULT_THEME_WORD1.contains(userThemeInput.toLowerCase())
                    || userThemeInput.toLowerCase().contains(DEFAULT_THEME_WORD2.toLowerCase())
                    || DEFAULT_THEME_WORD2.toLowerCase().contains(userThemeInput.toLowerCase())) {
                themeChoice = DEFAULT_THEME_WORD2;
            } else {
                throw new CommandException(String.format(MESSAGE_UNKNOWN_THEME, userThemeInput));
            }
        }
        if (themeChoice.equals(DARK_THEME_WORD2)) {
            EventsCenter.getInstance().post(new ChangeDarkThemeEvent());
        } else if (themeChoice.equals(BRIGHT_THEME_WORD2)) {
            EventsCenter.getInstance().post(new ChangeBrightThemeEvent());
        } else {
            EventsCenter.getInstance().post(new ChangeDefaultThemeEvent());
        }
        return new CommandResult(String.format(MESSAGE_THEME_CHANGE_SUCCESS, userThemeInput));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SwitchThemeCommand // instanceof handles nulls
                && this.userThemeInput.equals(((SwitchThemeCommand) other).userThemeInput)); // state check
    }
}
