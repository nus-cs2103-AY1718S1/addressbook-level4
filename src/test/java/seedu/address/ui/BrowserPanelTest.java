package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.FACEBOOK_PROFILE_PAGE;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;

import seedu.address.commons.events.ui.FaceBookEvent;

import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowPersonAddressEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private FaceBookEvent selectionChanged;
    private ShowPersonAddressEvent selectionAddress;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        selectionChanged = new FaceBookEvent(ALICE);
        selectionAddress = new ShowPersonAddressEvent("");

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }
    @Test
    public void display() throws Exception {
        // default web page

        URL expectedDefaultPageUrl = new URL(DEFAULT_PAGE);

        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);


        URL expectedPersonUrl = new URL("https://nusmods.com/timetable/2017-2018/sem1?&CS2101[SEC]=1");
        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());


        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }

    //GUI tests for loading facebook page
    @Test
    public void faceBookDisplay() throws Exception {

        URL expectedFaceBookPersonUrl = new URL(FACEBOOK_PROFILE_PAGE + "ronak.lakhotia");
        postNow(selectionChanged);
        assertEquals(expectedFaceBookPersonUrl, browserPanelHandle.getLoadedUrl());

    }
}

