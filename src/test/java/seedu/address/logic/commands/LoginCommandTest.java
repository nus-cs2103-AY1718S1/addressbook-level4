package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_LOGIN_ACKNOWLEDGEMENT;
import static seedu.address.logic.commands.LoginCommand.MESSAGE_LOGIN_UNSUCCESSFUL;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.events.ui.LoginAppRequestEvent;
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.Password;
import seedu.address.logic.Username;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.ui.testutil.EventsCollectorRule;

//@@author jelneo
public class LoginCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();
    public ExpectedException thrown = ExpectedException.none();

    private final boolean hasLoggedIn = true;
    private LoginAppRequestEvent event;

    @Test
    public void execute_login_success() {
        try {
            Username username = new Username("TESTloanShark97");
            Password password = new Password("TESThitMeUp123");
            CommandResult result = new LoginCommand(username, password).execute();
            assertEquals(hasLoggedIn, event.getLoginStatus());
            assertEquals(MESSAGE_LOGIN_ACKNOWLEDGEMENT, result.feedbackToUser);

        } catch (IllegalValueException ive) {
            ive.printStackTrace();
        } catch (CommandException ce) {
            ce.printStackTrace();
        }
    }
    @Test
    public void execute_login_failure() {
        try {
            Username username = new Username("iDontExist");
            Password password = new Password("thisPwdDontExist");
            CommandResult result = new LoginCommand(username, password).execute();
            assertEquals(!hasLoggedIn, event.getLoginStatus());
            assertEquals(MESSAGE_LOGIN_UNSUCCESSFUL, result.feedbackToUser);

        } catch (Exception e) {
            thrown.expect(IllegalValueException.class);
        }
    }

}
