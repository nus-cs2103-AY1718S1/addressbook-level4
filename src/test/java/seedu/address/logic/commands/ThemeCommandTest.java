package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_DARK;
import static seedu.address.logic.commands.CommandTestUtil.VALID_THEME_LIGHT;
import static seedu.address.logic.commands.ThemeCommand.DARK_THEME;
import static seedu.address.logic.commands.ThemeCommand.LIGHT_THEME;
import static seedu.address.logic.commands.ThemeCommand.SWITCH_THEME_FAILURE_MESSAGE;
import static seedu.address.logic.commands.ThemeCommand.SWITCH_THEME_SUCCESS_MESSAGE;

import static seedu.address.model.ThemeUnit.THEME_LIGHT_UNIT;
import static seedu.address.model.ThemeUnit.getCurrentThemeUnit;

import org.junit.Test;

//@@author cctdaniel
public class ThemeCommandTest {

    @Test
    public void execute_lightThemeCommand_success() {
        ThemeCommand light = new ThemeCommand(VALID_THEME_LIGHT);
        CommandResult result = light.execute();
        if (getCurrentThemeUnit() == THEME_LIGHT_UNIT && light.getIsLight()) {
            assertEquals(SWITCH_THEME_FAILURE_MESSAGE, result.feedbackToUser);
        } else {
            assertEquals(SWITCH_THEME_SUCCESS_MESSAGE + LIGHT_THEME + ".", result.feedbackToUser);
        }
    }

    @Test
    public void execute_darkThemeCommand_success() {
        ThemeCommand dark = new ThemeCommand(VALID_THEME_DARK);
        CommandResult result = dark.execute();
        if (getCurrentThemeUnit() == THEME_LIGHT_UNIT && !dark.getIsLight()) {
            assertEquals(SWITCH_THEME_FAILURE_MESSAGE, result.feedbackToUser);
        } else {
            assertEquals(SWITCH_THEME_SUCCESS_MESSAGE + DARK_THEME + ".", result.feedbackToUser);
        }
    }
}
