package seedu.address.ui;

import static junit.framework.TestCase.assertFalse;
import static seedu.address.ui.BrowserPanel.DEFAULT_PAGE;
import static seedu.address.ui.UiPart.FXML_FILE_FOLDER;

import java.net.URL;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.CalendarPanelHandle;
import seedu.address.MainApp;

//@@author yilun-zhu

public class CalendarPanelTest extends GuiUnitTest {

    private CalendarPanel calendarPanel;
    private CalendarPanelHandle calendarPanelHandle;

    @Before
    public void setUp() {

        guiRobot.interact(() -> calendarPanel = new CalendarPanel());
        uiPartRule.setUiPart(calendarPanel);

        calendarPanelHandle = new CalendarPanelHandle(calendarPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default window should be updated
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertFalse(expectedDefaultPageUrl.equals(calendarPanelHandle.getLoadedUrl()));

        /* The section below can be used for local testing. It does not load on Travis-CI.
        postNow(calendarRequestEvent);

        URL expectedCalendarUrl = new URL("https://accounts.google.com/ServiceLogin?service=cl&"
                + "passive=1209600&osid=1&continue=https://calendar.google.com/calendar/render&followup="
                + "https://calendar.google.com/calendar/render&scc=1");

        waitUntilCalendarLoaded(calendarPanelHandle);
        assertTrue(expectedCalendarUrl.equals(calendarPanelHandle.getLoadedUrl()));
        */
    }
}
