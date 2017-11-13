package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAP_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_SUFFIX;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_ADDRESS;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_EMAIL;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_NAME;
import static seedu.address.ui.BrowserSearchMode.GOOGLE_SEARCH_PHONE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.ChangeSearchEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

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

    @Test
    @Ignore
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }

    //@@author Hailinx
    @Test
    public void test_getUrl() {
        ReadOnlyPerson person = ALICE;

        postNow(new ChangeSearchEvent(GOOGLE_SEARCH_NAME));
        String searchNameUrl = GOOGLE_SEARCH_URL_PREFIX + "Alice+Pauline" + GOOGLE_SEARCH_URL_SUFFIX;
        assertEquals(browserPanel.getUrl(person), searchNameUrl);

        postNow(new ChangeSearchEvent(GOOGLE_SEARCH_PHONE));
        String searchPhoneUrl = GOOGLE_SEARCH_URL_PREFIX + "85355255" + GOOGLE_SEARCH_URL_SUFFIX;
        assertEquals(browserPanel.getUrl(person), searchPhoneUrl);

        postNow(new ChangeSearchEvent(GOOGLE_SEARCH_EMAIL));
        String searchEmailUrl = GOOGLE_SEARCH_URL_PREFIX + "alice@example.com" + GOOGLE_SEARCH_URL_SUFFIX;
        assertEquals(browserPanel.getUrl(person), searchEmailUrl);

        postNow(new ChangeSearchEvent(GOOGLE_SEARCH_ADDRESS));
        String searchAddressUrl = GOOGLE_MAP_URL_PREFIX + "123,+Jurong+West+Ave+6,+#08-111";
        assertEquals(browserPanel.getUrl(person), searchAddressUrl);
    }
    //@@author
}
