package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.ThemesWindow.THEMES_FILE_PATH;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.ThemesWindowHandle;
import javafx.stage.Stage;

public class ThemesWindowTest extends GuiUnitTest {

    private ThemesWindow themesWindow;
    private ThemesWindowHandle themesWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> themesWindow = new ThemesWindow());
        Stage themesWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(themesWindow.getRoot().getScene()));
        FxToolkit.showStage();
        themesWindowHandle = new ThemesWindowHandle(themesWindowStage);
    }

    @Test
    public void display() {
        URL expectedHelpPage = ThemesWindow.class.getResource(THEMES_FILE_PATH);
        assertEquals(expectedHelpPage, themesWindowHandle.getLoadedUrl());
    }
}
