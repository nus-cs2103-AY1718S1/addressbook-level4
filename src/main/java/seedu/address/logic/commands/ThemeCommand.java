package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.core.MessageAlignmentFormatter.FORMAT_ALIGNMENT_TO_EXAMPLE;
import static seedu.address.commons.core.MessageAlignmentFormatter.FORMAT_ALIGNMENT_TO_PARAMETERS;

import java.util.HashMap;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

//@@author nbriannl
/**
 * Changes the theme to the theme indicated
 */
public class ThemeCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "th";
    public static final String COMMAND_SECONDARY = "changetheme";

    public static final String MESSAGE_USAGE = "| " + COMMAND_WORD + " |"
            + ": Changes the theme to the specified theme word.\n"
            + "Parameters: THEMEWORD\n"
            + FORMAT_ALIGNMENT_TO_PARAMETERS + "(Possible theme words are: dark, light)\n"
            + "Example: " + COMMAND_WORD + " dark\n"
            + FORMAT_ALIGNMENT_TO_EXAMPLE + COMMAND_WORD + " light";

    public static final String MESSAGE_SUCCESS = "Theme switched: %1$s";
    public static final String VIEW_PATH = "/view/";

    private final String themeKeyword;

    /**
     * Creates an AddCommand to add the specified {@code ReadOnlyPerson}
     */
    public ThemeCommand (String themeKeyword) {
        this.themeKeyword = themeKeyword;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        String themeToSwitch;
        String currentTheme = model.getCurrentTheme();

        HashMap<String, String> themes = model.getThemeMap();

        if (themes.containsKey(themeKeyword)) {
            themeToSwitch = themes.get(themeKeyword);
        } else {
            throw new CommandException(Messages.MESSAGE_THEME_NOT_FOUND);
        }

        if (currentTheme.equals(VIEW_PATH + themeToSwitch)) {
            throw new CommandException(Messages.MESSAGE_ALREADY_IN_THEME);
        }

        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(themeToSwitch));

        return new CommandResult(String.format(MESSAGE_SUCCESS, themeToSwitch));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && themeKeyword.equals(((ThemeCommand) other).themeKeyword));
    }
}
