package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilMapBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.MapWindow.PLUS;
import static seedu.address.ui.MapWindow.SPACE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;
import org.testfx.api.FxToolkit;

import guitests.guihandles.MapWindowHandle;

import javafx.stage.Stage;

//@@author liliwei25
public class MapWindowTest extends GuiUnitTest {
    private static final String GOOGLE_MAPS_URL_PREFIX = "https://www.google.com.sg/maps/search/";
    private static final String GOOGLE_SEARCH_URL_SUFFIX = "/data=!4m2!2m1!4b1?dg=dbrw&newdg=1";
    private MapWindow mapWindow;
    private MapWindowHandle mapWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> mapWindow = new MapWindow(ALICE));
        Stage mapWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(mapWindow.getRoot().getScene()));
        FxToolkit.showStage();
        mapWindowHandle = new MapWindowHandle(mapWindowStage);
    }

    @Test
    public void display() throws Exception {
        URL expectedHelpPage = new URL(GOOGLE_MAPS_URL_PREFIX
                + ALICE.getAddress().getMapableAddress().trim().replaceAll(SPACE, PLUS) + GOOGLE_SEARCH_URL_SUFFIX);
        waitUntilMapBrowserLoaded(mapWindowHandle);
        assertEquals(expectedHelpPage, mapWindowHandle.getLoadedUrl());
    }
}
