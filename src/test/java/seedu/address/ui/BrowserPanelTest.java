package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.FACEBOOK_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAP_DIR_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAP_URL_END;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAP_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_MAP_URL_SUFFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_SUFFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_URL_SUFFIX;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.commons.events.ui.PersonFacebookOpenEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.SearchMajorEvent;
import seedu.address.commons.events.ui.SearchNameEvent;
import seedu.address.commons.util.StringUtil;
import seedu.address.model.person.Facebook;
import seedu.address.model.person.Major;
import seedu.address.model.person.Person;

public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private BrowserPanelLocateEvent locateEventStub;
    private PersonFacebookOpenEvent facebookOpenEventStub;
    private SearchMajorEvent searchMajorEvent;
    private SearchNameEvent searchNameEvent;
    private Person dummy;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    private String startAddress = "Clementi";
    private String endAddress = "NUS";

    @Before
    public void setUp() {
        dummy = new Person(ALICE);
        dummy.setMajor(new Major("Computer Science"));
        dummy.setFacebook(new Facebook("zuck"));

        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        locateEventStub = new BrowserPanelLocateEvent(startAddress, endAddress);
        facebookOpenEventStub = new PersonFacebookOpenEvent(dummy);
        searchMajorEvent = new SearchMajorEvent(dummy.getMajor().value);
        searchNameEvent = new SearchNameEvent(dummy.getName().fullName);



        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }
    //@@author heiseish
    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_MAP_URL_PREFIX
                + StringUtil.partiallyEncode(ALICE.getAddress().value)
                + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());


        // associated facebook page of a person
        postNow(facebookOpenEventStub);
        expectedPersonUrl = new URL(FACEBOOK_PREFIX + StringUtil.partiallyEncode(dummy.getFacebook().value));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }

    @Test
    public void displayFacxebook() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated facebook page of a person
        postNow(facebookOpenEventStub);
        URL expectedPersonUrl = new URL(FACEBOOK_PREFIX + StringUtil.partiallyEncode(dummy.getFacebook().value));

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }



    @Test
    public void displayName() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());


        // search name of a person
        postNow(searchNameEvent);
        URL expectedPersonUrl = new URL(GOOGLE_URL_PREFIX
                + StringUtil.partiallyEncode(dummy.getName().fullName) + GOOGLE_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }

    @Test
    public void displayMajor() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // search major of a person
        postNow(searchMajorEvent);
        URL expectedPersonUrl = new URL(GOOGLE_URL_PREFIX
                + StringUtil.partiallyEncode("NUS " + dummy.getMajor().value) + GOOGLE_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }

    //@@author majunting
    @Test
    public void displayLocate() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = new URL(DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        postNow(locateEventStub);
        URL expectedLocateUrl = new URL(GOOGLE_MAP_DIR_URL_PREFIX
                + StringUtil.partiallyEncode(startAddress) + GOOGLE_MAP_URL_SUFFIX
                + StringUtil.partiallyEncode(endAddress) + GOOGLE_MAP_URL_SUFFIX
                + GOOGLE_MAP_URL_END);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedLocateUrl, browserPanelHandle.getLoadedUrl());
    }
    //@@author
}
