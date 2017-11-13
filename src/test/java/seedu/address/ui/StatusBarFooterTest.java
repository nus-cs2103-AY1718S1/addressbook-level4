package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.storage.util.RolodexStorageUtil.ROLODEX_FILE_EXTENSION;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import guitests.guihandles.StatusBarFooterHandle;
import seedu.address.commons.events.model.RolodexChangedEvent;
import seedu.address.commons.events.storage.RolodexChangedDirectoryEvent;
import seedu.address.model.Rolodex;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final String STUB_SAVE_LOCATION = "Stub" + ROLODEX_FILE_EXTENSION;
    private static final String STUB_SAVE_LOCATION_NEW = "NewStub" + ROLODEX_FILE_EXTENSION;
    private static final String RELATIVE_PATH = "./";

    private static final RolodexChangedEvent EVENT_STUB_CHANGE_DATA =
            new RolodexChangedEvent(new Rolodex());
    private static final RolodexChangedDirectoryEvent EVENT_STUB_CHANGE_DIRECTORY =
            new RolodexChangedDirectoryEvent(RELATIVE_PATH + STUB_SAVE_LOCATION_NEW);

    private static final Clock originalClock = StatusBarFooter.getClock();
    private static final Clock injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());

    private StatusBarFooterHandle statusBarFooterHandle;

    @BeforeClass
    public static void setUpBeforeClass() {
        // inject fixed clock
        StatusBarFooter.setClock(injectedClock);
    }

    @AfterClass
    public static void tearDownAfterClass() {
        // restore original clock
        StatusBarFooter.setClock(originalClock);
    }

    @Before
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(STUB_SAVE_LOCATION);
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION, SYNC_STATUS_INITIAL);

        // after rolodex is updated
        postNow(EVENT_STUB_CHANGE_DATA);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()));

        // after rolodex directory is updated
        postNow(EVENT_STUB_CHANGE_DIRECTORY);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION_NEW,
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()));
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, and the
     * sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation, String expectedSyncStatus) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        guiRobot.pauseForHuman();
    }

}
