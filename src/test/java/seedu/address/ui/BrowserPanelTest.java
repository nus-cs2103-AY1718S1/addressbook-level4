package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalParcels.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAP_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.QUERY_POSTAL_CODE_LENGTH;
import static seedu.address.ui.BrowserPanel.getMapQueryStringFromPostalString;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.ParcelPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private ParcelPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new ParcelPanelSelectionChangedEvent(new ParcelCard(ALICE, 0));

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a parcel
        postNow(selectionChangedEventStub);
        URL expectedParcelUrl = new URL(GOOGLE_MAP_URL_PREFIX
                + getMapQueryStringFromPostalString(ALICE.getAddress().postalCode.toString()));

        waitUntilBrowserLoaded(browserPanelHandle);

        int correctUrlLength = GOOGLE_MAP_URL_PREFIX.length() + QUERY_POSTAL_CODE_LENGTH;
        String actualParcelUrl = browserPanelHandle.getLoadedUrl().toString().substring(0, correctUrlLength);
        assertEquals(expectedParcelUrl.toString(), actualParcelUrl);
    }

}
