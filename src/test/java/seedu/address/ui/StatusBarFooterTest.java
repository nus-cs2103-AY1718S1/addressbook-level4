package seedu.address.ui;

import static org.junit.Assert.assertEquals;
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
import seedu.address.commons.events.model.AddressBookChangedEvent;
import seedu.address.model.AddressBook;
import seedu.address.testutil.AddressBookBuilder;
import seedu.address.testutil.PersonBuilder;

public class StatusBarFooterTest extends GuiUnitTest {

    private static final String STUB_SAVE_LOCATION = "Stub";
    private static final String RELATIVE_PATH = "./";

    private static final AddressBookChangedEvent EVENT_STUB = new AddressBookChangedEvent(new AddressBook());
    private static final AddressBookChangedEvent ADDED_PERSON_EVENT =
        new AddressBookChangedEvent(new AddressBookBuilder().withPerson(new PersonBuilder().build()).build());

    //@@author qihao27
    private static final String STUB_ZERO_TOTAL_PERSON = "0";
    private static final String STUB_ONE_TOTAL_PERSON = "1";
    private static final String TOTAL_PERSONS_SUFFIX = " person(s) total";
    //@@author

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
        //@@author qihao27
        StatusBarFooter statusBarFooter = new StatusBarFooter(STUB_SAVE_LOCATION, STUB_ZERO_TOTAL_PERSON);
        //@@author
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION, SYNC_STATUS_INITIAL,
            STUB_ZERO_TOTAL_PERSON + TOTAL_PERSONS_SUFFIX);

        // after address book is updated without changes to list
        postNow(EVENT_STUB);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()),
                STUB_ZERO_TOTAL_PERSON + TOTAL_PERSONS_SUFFIX);

        // after address book is updated with changes to list
        postNow(ADDED_PERSON_EVENT);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
            String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()),
            STUB_ONE_TOTAL_PERSON + TOTAL_PERSONS_SUFFIX);
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation}, and the
     * sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation, String expectedSyncStatus,
                                        String expectedTotalPerson) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertEquals(expectedTotalPerson, statusBarFooterHandle.getTotalPerson());
        guiRobot.pauseForHuman();
    }

}
