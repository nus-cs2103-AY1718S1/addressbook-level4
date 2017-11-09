package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.CARL;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.SOCIAL_MEDIA_URL_PREFIX;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ToggleChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private ToggleChangedEvent toggleEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(CARL, 0));

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        //@@author seankwekjk
        // associated address of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + CARL.getAddress().value.replaceAll(" ", "+").replaceAll(",", "%2C"));

        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
        assertEquals(CARL.getAddress().value, browserPanel.getValue());
        assertEquals("Address", browserPanel.getField());

        // associated social media page of a person
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 1));
        BrowserPanel.setBrowserMode();
        postNow(selectionChangedEventStub);
        URL expectedSocialUrl = new URL(SOCIAL_MEDIA_URL_PREFIX + ALICE.getSocialMedia() + "/");

        assertEquals(expectedSocialUrl, browserPanelHandle.getLoadedUrl());
        assertEquals(ALICE.getSocialMedia(), browserPanel.getValue());
        assertEquals("Social Media", browserPanel.getField());

        // toggle refresh behaviour
        BrowserPanel.setBrowserMode();
        toggleEventStub = new ToggleChangedEvent();
        postNow(toggleEventStub);
        expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getAddress().value.replaceAll(" ", "+").replaceAll(",", "%2C"));

        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
        assertEquals(ALICE.getAddress().value, browserPanel.getValue());
        assertEquals("Address", browserPanel.getField());

        BrowserPanel.setBrowserMode();
        toggleEventStub = new ToggleChangedEvent();
        postNow(toggleEventStub);
        expectedSocialUrl = new URL(SOCIAL_MEDIA_URL_PREFIX + ALICE.getSocialMedia() + "/");

        assertEquals(expectedSocialUrl, browserPanelHandle.getLoadedUrl());
        assertEquals(ALICE.getSocialMedia(), browserPanel.getValue());
        assertEquals("Social Media", browserPanel.getField());

        //revert BrowserMode to original state
        BrowserPanel.setBrowserMode();
    }
}
