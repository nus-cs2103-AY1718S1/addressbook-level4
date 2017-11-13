package guitests;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;

import java.util.ArrayList;

import org.junit.Test;

import seedu.address.commons.events.ui.ChangeBrightThemeEvent;
import seedu.address.commons.events.ui.ChangeDarkThemeEvent;
import seedu.address.commons.events.ui.ChangeDefaultThemeEvent;

//@@author ZhangH795
public class ThemeGuiTest extends AddressBookGuiTest {
    /**
     * Asserts that the theme after change is Dark Theme.
     */
    @Test
    public void changeToDarkThemeTest() {
        ArrayList<String> darkTheme = new ArrayList<>();
        darkTheme.add("view/DarkTheme.css");
        ChangeDarkThemeEvent darkThemeEvent = new ChangeDarkThemeEvent();

        postNow(darkThemeEvent);
        assertEquals(darkTheme, stage.getScene().getStylesheets());
    }

    /**
     * Asserts that the theme after change is Bright Theme.
     */
    @Test
    public void changeToBrightThemeTest() {
        ArrayList<String> brightTheme = new ArrayList<>();
        brightTheme.add("view/BrightTheme.css");
        ChangeBrightThemeEvent brightThemeEvent = new ChangeBrightThemeEvent();

        postNow(brightThemeEvent);
        assertEquals(brightTheme, stage.getScene().getStylesheets());
    }

    /**
     * Asserts that the theme after change is Default Theme.
     */
    @Test
    public void changeToDefaultThemeTest() {
        ArrayList<String> defaultTheme = new ArrayList<>();
        defaultTheme.add("view/Extensions.css");
        ChangeDefaultThemeEvent defaultThemeEvent = new ChangeDefaultThemeEvent();

        postNow(defaultThemeEvent);
        assertEquals(defaultTheme, stage.getScene().getStylesheets());
    }
}
