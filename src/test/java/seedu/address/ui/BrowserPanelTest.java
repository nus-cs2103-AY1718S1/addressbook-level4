package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_PREFIX;
import static seedu.address.ui.BrowserPanel.GOOGLE_SEARCH_URL_SUFFIX;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.MainApp;
import seedu.address.commons.core.GuiSettings;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.DisplayGmapEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.ReadOnlyPerson;

public class BrowserPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private DisplayGmapEvent gmapEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {

        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        gmapEventStub = new DisplayGmapEvent(Index.fromOneBased(1));

        guiRobot.interact(() -> browserPanel = new BrowserPanel(TYPICAL_PERSONS, GuiSettings.DEFAULT_THEME));
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl + "?theme=" + GuiSettings.DEFAULT_THEME,
                browserPanelHandle.getLoadedUrl().toExternalForm());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        // associated google map page of a person
        postNow(gmapEventStub);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertTrue(browserPanelHandle.getLoadedUrl().toString(), browserPanelHandle.getLoadedUrl()
                .toString().contains("https://www.google.com/maps/search/"));
    }
}
