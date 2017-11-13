package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.GoogleMapBrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.GoogleMapBrowserPanel.GOOGLEMAP_SEARCH_URL_PREFIX;
import static seedu.address.ui.GoogleMapBrowserPanel.GOOGLEMAP_SEARCH_URL_SUFFIX;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.GoogleMapBrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

// @@author johnweikangong
public class GoogleMapBrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private GoogleMapBrowserPanel googleMapBrowserPanel;
    private GoogleMapBrowserPanelHandle googleMapBrowserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> googleMapBrowserPanel = new GoogleMapBrowserPanel());
        uiPartRule.setUiPart(googleMapBrowserPanel);

        googleMapBrowserPanelHandle = new GoogleMapBrowserPanelHandle(googleMapBrowserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // Default web page.
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, googleMapBrowserPanelHandle.getLoadedUrl());

        String []segment = ALICE.getAddress().value.split("#");

        // Associated web page of a person.
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLEMAP_SEARCH_URL_PREFIX
                + segment[0].replaceAll(" ", "+") + GOOGLEMAP_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(googleMapBrowserPanelHandle);
        assertEquals(expectedPersonUrl, googleMapBrowserPanelHandle.getLoadedUrl());
    }
}
