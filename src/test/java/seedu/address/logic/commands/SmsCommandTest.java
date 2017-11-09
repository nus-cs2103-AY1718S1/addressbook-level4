package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX;
import static seedu.address.commons.core.Messages.MESSAGE_SMS_NUMBER_UNAUTHORIZED;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;

import com.twilio.exception.ApiException;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.ui.testutil.EventsCollectorRule;

/**
 * Contains integration tests (interaction with the Model) for {@code SmsCommand}.
 */
//@@author justuswah
public class SmsCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    }

    @Test
    public void execute_unauthorizedIndex_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size());
        assertExecutionFailure(outOfBoundsIndex, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Executes a {@code SmsCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        SmsCommand smsCommand = new SmsCommand(index, "hi");

        try {
            smsCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (ApiException ae) {
            assertEquals(expectedMessage, MESSAGE_SMS_NUMBER_UNAUTHORIZED);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        } catch (CommandException ce) {
            assertEquals(expectedMessage, MESSAGE_SMS_NUMBER_UNAUTHORIZED);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        } catch (NullPointerException npe) {
            assertEquals(expectedMessage, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
