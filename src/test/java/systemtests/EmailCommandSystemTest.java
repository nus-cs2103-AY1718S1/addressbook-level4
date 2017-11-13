//@@author tshradheya
package systemtests;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.testutil.EmailBuilder.DEFAULT_BODY;
import static seedu.address.testutil.EmailBuilder.DEFAULT_SERVICE;
import static seedu.address.testutil.EmailBuilder.DEFAULT_SUBJECT;
import static seedu.address.testutil.EmailBuilder.DEFAULT_TAG;

import org.junit.Test;

import seedu.address.logic.commands.EmailCommand;
import seedu.address.model.Model;

public class EmailCommandSystemTest extends AddressBookSystemTest {


    @Test
    public void sendingEmail() throws Exception {
        /** With all prefixes */
        String command  = EmailCommand.COMMAND_WORD + " s/" + DEFAULT_SERVICE + " to/" + DEFAULT_TAG + " sub/"
                + DEFAULT_SUBJECT + " body/" + DEFAULT_BODY;
        Model model = getModel();
        assertCommandSuccess(command, model, "");

        /** With optional prefixes missing*/
        command  = EmailCommand.COMMAND_WORD + " s/" + DEFAULT_SERVICE + " to/" + DEFAULT_TAG + " sub/"
                + DEFAULT_SUBJECT;
        model = getModel();
        assertCommandSuccess(command, model, "");

        /** With compulsory prefix missing*/
        command  = EmailCommand.COMMAND_WORD + " to/" + DEFAULT_TAG + " sub/"
                + DEFAULT_SUBJECT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, EmailCommand.MESSAGE_USAGE));
    }

    /**
     * To assert successful command
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertCommandBoxShowsDefaultStyle();
        assertSelectedCardUnchanged();
    }

    /**
     * To assert failed command
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}

