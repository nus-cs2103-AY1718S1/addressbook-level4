package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.InstagramBrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.InstagramBrowserPanel.INSTAGRAM_SEARCH_URL_PREFIX;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.InstagramBrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;

// @@author johnweikangong
public class InstagramBrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private InstagramBrowserPanel instagramBrowserPanel;
    private InstagramBrowserPanelHandle instagramBrowserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> instagramBrowserPanel = new InstagramBrowserPanel());
        uiPartRule.setUiPart(instagramBrowserPanel);

        instagramBrowserPanelHandle = new InstagramBrowserPanelHandle(instagramBrowserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // Default web page.
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, instagramBrowserPanelHandle.getLoadedUrl());

        // Associated web page of a person.
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(INSTAGRAM_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll("\\s+", "") + "/");

        waitUntilBrowserLoaded(instagramBrowserPanelHandle);
        assertEquals(expectedPersonUrl, instagramBrowserPanelHandle.getLoadedUrl());
    }
}
