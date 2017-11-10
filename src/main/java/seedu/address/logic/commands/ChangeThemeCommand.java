package seedu.address.logic.commands;
//@@author zhoukai07
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.ChangeThemeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "changeToTheme";
    public static final String COMMAND_ALIAS = "ct";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Change theme by the index number of the 4 inbuilt themes.\n"
            + "Parameters: INDEX (1, 2, 3 or 4)\n"
            + "Example: " + COMMAND_WORD + " 1";
    public static final String MESSAGE_USAGE_EXAMPLE = COMMAND_WORD + " {index}";

    public static final String MESSAGE_SELECT_THEME_SUCCESS = "Selected Theme: %1$s";

    private final Integer targetIndex;
    public ChangeThemeCommand(Integer targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        String[] themeList = new String[]{"/view/DarkTheme.css", "/view/DarkTheme2.css", "/view/LightTheme.css",
            "/view/LightTheme2.css"};
        if (targetIndex > themeList.length) {
            throw new CommandException(Messages.MESSAGE_INVALID_THEME_INDEX);
        }
        String themeUrl = getClass().getResource(themeList[targetIndex - 1]).toExternalForm();
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent(themeUrl));
        return new CommandResult(String.format(MESSAGE_SELECT_THEME_SUCCESS, targetIndex));
    }
}
//@@author
