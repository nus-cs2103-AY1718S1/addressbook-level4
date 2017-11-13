package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.ui.AboutWindow.USERGUIDE_FILE_PATH;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.AboutWindowHandle;
import javafx.stage.Stage;

//@@author yewshengkai
public class AboutWindowTest extends GuiUnitTest {

    private AboutWindow aboutWindow;
    private AboutWindowHandle aboutWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> aboutWindow = new AboutWindow());
        Stage aboutWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(aboutWindow.getRoot().getScene()));
        FxToolkit.showStage();
        aboutWindowHandle = new AboutWindowHandle(aboutWindowStage);
    }

    @Test
    public void display() {
        URL expectedHelpPage = AboutWindow.class.getResource(USERGUIDE_FILE_PATH);
        assertEquals(expectedHelpPage, aboutWindowHandle.getLoadedUrl());
    }
}
