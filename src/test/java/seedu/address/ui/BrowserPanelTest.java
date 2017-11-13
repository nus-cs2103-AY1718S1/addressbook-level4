package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.BENSON;
import static seedu.address.testutil.TypicalPersons.BOB;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAPS_DIRECTIONS_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAPS_DIRECTIONS_SUFFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAPS_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAPS_URL_SUFFIX;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.commons.events.ui.BrowserPanelNavigateEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.Location;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private BrowserPanelLocateEvent panelLocateEventStub;
    private BrowserPanelNavigateEvent panelNavigateEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() throws IllegalValueException {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE,
                0, PersonCard.DEFAULT_FONT_SIZE_MULTIPLIER));
        panelLocateEventStub = new BrowserPanelLocateEvent(BOB);
        panelNavigateEventStub = new BrowserPanelNavigateEvent(new Location("NUS"),
                new Location(BENSON.getAddress().toString()));
        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    //@@author jeffreygohkw
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // google maps page of a person
        postNow(panelLocateEventStub);
        URL expectedMapUrl = new URL(GOOGLE_MAPS_URL_PREFIX
                + BOB.getAddress().toString().replaceAll(" ", "+") + GOOGLE_MAPS_URL_SUFFIX
                + "?dg=dbrw&newdg=1");

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedMapUrl, browserPanelHandle.getLoadedUrl());

        // google maps page of a person
        postNow(panelNavigateEventStub);
        URL expectedDirUrl = new URL(GOOGLE_MAPS_DIRECTIONS_PREFIX
                + "&origin="
                + panelNavigateEventStub.getFromLocation().toString().replaceAll("#(\\w+)\\s*", "")
                .replaceAll(" ", "+").replaceAll("-(\\w+)\\s*", "")
                + "&destination="
                + panelNavigateEventStub.getToLocation().toString().replaceAll("#(\\w+)\\s*", "")
                .replaceAll(" ", "+").replaceAll("-(\\w+)\\s*", "")
                + GOOGLE_MAPS_DIRECTIONS_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedDirUrl, browserPanelHandle.getLoadedUrl());
    }
}
