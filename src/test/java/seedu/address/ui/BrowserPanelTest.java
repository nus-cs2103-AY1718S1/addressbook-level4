package seedu.address.ui;

import static seedu.address.testutil.TypicalPersons.ALICE;

import org.junit.Before;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    //    @Test
    //    public void display() throws Exception {
    //        // default web page
    //        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
    //        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());
    //
    //        // associated web page of a person
    //        postNow(selectionChangedEventStub);
    //        URL expectedPersonUrl = new URL(Website.WEBSITE_EXAMPLE);
    //
    //        waitUntilBrowserLoaded(browserPanelHandle);
    //        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    //    }
}
