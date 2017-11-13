package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilMapLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.DANIEL;
import static seedu.address.ui.MapsPanel.MAPS_DEFAULT_ORIGIN;
import static seedu.address.ui.MapsPanel.MAPS_DEST_PREFIX;
import static seedu.address.ui.MapsPanel.MAPS_DIR_URL_PREFIX;
import static seedu.address.ui.MapsPanel.MAPS_SEARCH_ORIGIN;
import static seedu.address.ui.MapsPanel.MAPS_SEARCH_URL_PREFIX;
import static seedu.address.ui.MapsPanel.MAPS_SEARCH_URL_SUFFIX;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.MapsPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

public class MapsPanelTest extends GuiUnitTest {

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private MapsPanel mapsPanel;
    private MapsPanelHandle mapsPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(DANIEL, 3));

        guiRobot.interact(() -> mapsPanel = new MapsPanel());
        uiPartRule.setUiPart(mapsPanel);

        mapsPanelHandle = new MapsPanelHandle(mapsPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(MAPS_SEARCH_URL_PREFIX + MAPS_SEARCH_ORIGIN);
        assertEquals(expectedDefaultPageUrl, mapsPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(MAPS_DIR_URL_PREFIX + MAPS_DEFAULT_ORIGIN + MAPS_DEST_PREFIX
                + DANIEL.getAddress().toString().replaceAll(" ", "+") + MAPS_SEARCH_URL_SUFFIX);

        waitUntilMapLoaded(mapsPanelHandle);
        assertEquals(expectedPersonUrl, mapsPanelHandle.getLoadedUrl());
    }
}
