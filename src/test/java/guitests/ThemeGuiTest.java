package guitests;

import org.junit.Test;
import seedu.address.commons.events.ui.ChangeBrightThemeEvent;
import seedu.address.commons.events.ui.ChangeDarkThemeEvent;
import seedu.address.commons.events.ui.ChangeDefaultThemeEvent;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
//@@author ZhangH795
public class ThemeGuiTest extends AddressBookGuiTest {
    @Test
    public void changeToDarkThemeTest(){
        ArrayList<String> darkTheme = new ArrayList<>();
        darkTheme.add("view/DarkTheme.css");
        ChangeDarkThemeEvent darkThemeEvent = new ChangeDarkThemeEvent();

        postNow(darkThemeEvent);
        assertEquals(darkTheme, stage.getScene().getStylesheets());
    }

    @Test
    public void changeToBrightThemeTest(){
        ArrayList<String> brightTheme = new ArrayList<>();
        brightTheme.add("view/BrightTheme.css");
        ChangeBrightThemeEvent brightThemeEvent = new ChangeBrightThemeEvent();

        postNow(brightThemeEvent);
        assertEquals(brightTheme, stage.getScene().getStylesheets());
    }

    @Test
    public void changeToDefaultThemeTest(){
        ArrayList<String> defaultTheme = new ArrayList<>();
        defaultTheme.add("view/Extensions.css");
        ChangeDefaultThemeEvent defaultThemeEvent = new ChangeDefaultThemeEvent();

        postNow(defaultThemeEvent);
        assertEquals(defaultTheme, stage.getScene().getStylesheets());
    }
}
