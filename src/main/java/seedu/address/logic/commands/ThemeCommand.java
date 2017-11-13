package seedu.address.logic.commands;

//@@author jeffreygohkw

import static java.util.Objects.requireNonNull;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 *
 */
public class ThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";
    public static final String COMMAND_ALIAS = "th";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Sets the theme based on the specified style.\n"
            + "Parameters: STYLE\n"
            + "Example: " + COMMAND_WORD + " dark";

    public static final String MESSAGE_THEME_CHANGE_SUCCESS = "Theme Changed to: %1$s";
    public static final String MESSAGE_THEME_NOT_AVAILABLE = "Theme %1$s is not available.";
    public static final String MESSAGE_THEME_IN_USE = "Theme %1$s is currently in use.";


    private final String style;

    public ThemeCommand (String style) {
        this.style = style;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);

        String styleSheet;

        if (model.getStyleMap().containsKey(style)) {
            styleSheet = model.getStyleMap().get(style);
        } else {
            throw new CommandException(String.format(MESSAGE_THEME_NOT_AVAILABLE, style));
        }

        if (model.getTheme().equals(styleSheet)) {
            throw new CommandException(String.format(MESSAGE_THEME_IN_USE, style));
        }

        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(styleSheet));
        model.setTheme(styleSheet);
        return new CommandResult(String.format(MESSAGE_THEME_CHANGE_SUCCESS, style));
    }

    @Override
    public String toString() {
        return style;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand // instanceof handles nulls
                && this.style.equals(((ThemeCommand) other).style)); // state check
    }
}
