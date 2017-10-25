package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.testutil.TypicalWebLinks.WEBLINK_FACEBOOK;
import static seedu.address.testutil.TypicalWebLinks.WEBLINK_MAPS;
import static seedu.address.testutil.TypicalWebLinks.WEBLINK_SEARCH;

import org.junit.Rule;
import org.junit.Test;

import seedu.address.commons.events.ui.WebsiteSelectionRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code WebCommand}.
 */
public class WebCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validWebLinkName_success() {
        assertExecutionSuccess(WEBLINK_FACEBOOK);
        assertExecutionSuccess(WEBLINK_SEARCH);
        assertExecutionSuccess(WEBLINK_MAPS);
    }


    @Test
    public void equals() {
        WebCommand webMapsCommand = new WebCommand(WEBLINK_MAPS);
        WebCommand webFacebookCommand = new WebCommand(WEBLINK_FACEBOOK);

        // same object -> returns true
        assertTrue(webMapsCommand.equals(webMapsCommand));

        // same values -> returns true
        WebCommand selectFirstCommandCopy = new WebCommand(WEBLINK_MAPS);
        assertTrue(webMapsCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(webMapsCommand.equals(1));

        // null -> returns false
        assertFalse(webMapsCommand.equals(null));

        // different person -> returns false
        assertFalse(webMapsCommand.equals(webFacebookCommand));
    }

    /**
     * Executes a {@code WebCommand} with the given {@code webLink},
     * and checks that {@code WebsiteSelectionRequestEvent}
     * is raised with the correct webLink.
     */
    private void assertExecutionSuccess(String weblink) {
        WebCommand webCommand = new WebCommand(weblink);

        try {
            CommandResult commandResult = webCommand.execute();
            assertEquals(WebCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        WebsiteSelectionRequestEvent lastEvent =
                (WebsiteSelectionRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(weblink, lastEvent.getWebsiteRequested());
    }

    /**
     * Executes a {@code WebCommand} with the given {@code webLink}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String webLink, String expectedMessage) {
        WebCommand webCommand = new WebCommand(webLink);

        try {
            webCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
