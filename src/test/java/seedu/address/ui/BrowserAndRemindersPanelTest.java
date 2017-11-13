package seedu.address.ui;

import static guitests.guihandles.WebViewUtil.waitUntilBrowserLoaded;
import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.getTypicalPersons;
import static seedu.address.testutil.TypicalReminders.getTypicalReminders;
import static seedu.address.ui.BrowserAndRemindersPanel.DEFAULT_PAGE;
import static seedu.address.ui.BrowserAndRemindersPanel.GOOGLE_MAPS_URL;
import static seedu.address.ui.BrowserAndRemindersPanel.GOOGLE_SEARCH_URL_PREFIX;
import static seedu.address.ui.BrowserAndRemindersPanel.GOOGLE_SEARCH_URL_SUFFIX;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.BrowserAndRemindersPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.MainApp;
import seedu.address.commons.events.ui.LoadPersonWebpageEvent;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.commons.events.ui.ShowLocationEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.reminders.ReadOnlyReminder;

public class BrowserAndRemindersPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());
    private static final ObservableList<ReadOnlyReminder> TYPICAL_REMINDERS =
            FXCollections.observableList(getTypicalReminders());
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private ShowLocationEvent showLocationEventStub;
    private BrowserAndRemindersPanel browserAndRemindersPanel;
    private BrowserAndRemindersPanelHandle browserAndRemindersPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0), ALICE);
        showLocationEventStub = new ShowLocationEvent(ALICE);

        guiRobot.interact(() -> browserAndRemindersPanel = new BrowserAndRemindersPanel(TYPICAL_PERSONS,
                TYPICAL_REMINDERS));
        uiPartRule.setUiPart(browserAndRemindersPanel);

        browserAndRemindersPanelHandle = new BrowserAndRemindersPanelHandle(browserAndRemindersPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserAndRemindersPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(new LoadPersonWebpageEvent(ALICE));
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserAndRemindersPanelHandle);
        //assertEquals(expectedPersonUrl, browserAndRemindersPanelHandle.getLoadedUrl());

    }

    @Test
    public void checkUrlFormation() {
        postNow(showLocationEventStub);
        String expectedUrl = GOOGLE_MAPS_URL + ALICE.getAddress().value.replaceAll(" ", "+") + "+";
        String url = browserAndRemindersPanel.loadPersonLocation(ALICE.getAddress().value);
        assertEquals(expectedUrl, url);
    }

}
