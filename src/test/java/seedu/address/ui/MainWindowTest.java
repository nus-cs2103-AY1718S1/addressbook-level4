package seedu.address.ui;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

import guitests.AddressBookGuiTest;

//@@author teclu
public class MainWindowTest extends AddressBookGuiTest {

    @Test
    public void currentThemeDisplayed() {
        // Use menu button
        mainWindowHandle.getMainMenu().changeToDarkThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/DarkTheme.css");
        mainWindowHandle.getMainMenu().changeToRedThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/RedTheme.css");
        mainWindowHandle.getMainMenu().changeToBlueThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/BlueTheme.css");
        mainWindowHandle.getMainMenu().changeToGreenThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/GreenTheme.css");
        mainWindowHandle.getMainMenu().changeToLightThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/LightTheme.css");

        // To show final colour change
        mainWindowHandle.getMainMenu().changeToDarkThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/DarkTheme.css");

    }
}
