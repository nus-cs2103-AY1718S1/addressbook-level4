package guitests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import static seedu.address.testutil.TypicalPersons.HOON;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_INITIAL;
import static seedu.address.ui.StatusBarFooter.SYNC_STATUS_UPDATED;

import java.time.Clock;
import java.time.Instant;
import java.time.ZoneId;
import java.util.Date;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import seedu.address.logic.commands.ListCommand;
import seedu.address.testutil.PersonUtil;
import seedu.address.ui.StatusBarFooter;

public class StatusBarFooterTest extends RolodexGuiTest {

    private Clock originalClock;
    private Clock injectedClock;

    @Before
    public void injectFixedClock() {
        originalClock = StatusBarFooter.getClock();
        injectedClock = Clock.fixed(Instant.now(), ZoneId.systemDefault());
        StatusBarFooter.setClock(injectedClock);
    }

    @After
    public void restoreOriginalClock() {
        StatusBarFooter.setClock(originalClock);
    }

    @Test
    public void syncStatusInitialValue() {
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    @Test
    public void syncStatusMutatingCommandSucceedsSyncStatusUpdated() {
        String timestamp = new Date(injectedClock.millis()).toString();
        String expected = String.format(SYNC_STATUS_UPDATED, timestamp);
        assertTrue(runCommand(PersonUtil.getAddCommand(HOON))); // mutating command succeeds
        assertEquals(expected, getStatusBarFooter().getSyncStatus());
    }

    @Test
    public void syncStatusNonMutatingCommandSucceedsSyncStatusRemainsUnchanged() {
        assertTrue(runCommand(ListCommand.COMMAND_WORD)); // non-mutating command succeeds
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

    @Test
    public void syncStatusCommandFailsSyncStatusRemainsUnchanged() {
        assertFalse(runCommand("invalid command")); // invalid command fails
        assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
    }

}
