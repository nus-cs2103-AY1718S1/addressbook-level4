package seedu.address.logic.commands;

import javafx.scene.control.TextFormatter;
import seedu.address.commons.core.EventsCenter;
import javafx.scene.Scene;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index; 
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.MainWindow;
import seedu.address.ui.Ui;
import seedu.address.ui.UiManager;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class ChangeThemeCommand extends Command {

    public static final String COMMAND_WORD = "ChangeToTheme";
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
                "/view/LightTheme.css"};
        
        if (targetIndex >= themeList.length) {
                throw new CommandException(Messages.MESSAGE_INVALID_THEME_INDEX);
            }
        String themeUrl = getClass().getResource(themeList[targetIndex]).toExternalForm();
        
        return new CommandResult(String.format(MESSAGE_SELECT_THEME_SUCCESS, targetIndex));

    }
    
    public void ThemeChanger() {
        UiManager.changeTheme(themeUrl);
    }
    
}
