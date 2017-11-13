package seedu.address.logic.commands;

import java.io.IOException;

import com.google.common.eventbus.Subscribe;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.logic.GoogleAuthenticationSuccessEvent;
import seedu.address.commons.events.ui.Oauth2BrowserRequestEvent;

//@@author philemontan
/**
 * This is the abstract parent class for all commands requiring OAuth2 authentication using the BrowserPanel.
 * Child classes are expected to implement the event listener: handleAuthenticationSuccessEvent(), and provide the
 * commandType
 */
public abstract class Oauth2Command extends Command {
    public static final String INVALID_COMMAND_TYPE_MESSAGE = "Child classes of Oauth2Command must provide a valid"
            + " command type in the format: SERVICEPROVIDER_functionality";
    public static final String TRIGGER_BROWSER_AUTH_FAILED_MESSAGE = "Failed to trigger the authentication process "
            + "with the built-in browser.";
    public static final String TRIGGER_BROWSER_AUTH_SUCCESS_MESSAGE = "Authentication process initiated. Please login"
            + " on the built-in browser." + "\nNote: An active internet connection is required for this command.";

    private static final String REDIRECT_URL = "https://cs2103tdummyendpoint.herokuapp.com";

    private final String commandType;

    private String authenticationUrl;

    private boolean commandCompleted;

    protected Oauth2Command(String inputType)  {
        if (!inputTypeValid(inputType)) {
            assert false : INVALID_COMMAND_TYPE_MESSAGE;
        }
        commandType = inputType;
        commandCompleted = false;
        authenticationUrl = getAuthenticationUrl();
    }

    protected Oauth2Command() {
        this(null);
    }

    protected static String getRedirectUrl() {
        return REDIRECT_URL;
    }

    protected void setCommandCompleted() {
        commandCompleted = true;
    }

    protected boolean getCommandCompleted() {
        return commandCompleted;
    }

    public String getCommandType() {
        return commandType;
    }

    /**
     * Common functionality of all Oauth2Command child classes. Fires an event intended for the BrowserPanel,
     * triggering it to start the UI authentication process within the BrowserPanel
     * @throws IOException
     */
    protected void triggerBrowserAuth() throws IOException {
        try {
            Oauth2BrowserRequestEvent trigger = new Oauth2BrowserRequestEvent(commandType, authenticationUrl);
            EventsCenter.getInstance().post(trigger);
        } catch (IOException E) {
            throw E;
        }
    }

    /**
     * Authentication success handling is to be implemented by child classes.
     */
    @Subscribe
    protected abstract void handleAuthenticationSuccessEvent(GoogleAuthenticationSuccessEvent event);

    /**
     *  To be defined by child classes, URL is expected to be unique, based on service provider and scope required.
     */
    public abstract String getAuthenticationUrl ();

    /**
     *  Expected formatting: SERVICEPROVIDER_FUNCTIONALITY
     */
    private boolean inputTypeValid(String inputType) {
        return inputType != null && inputType.charAt(inputType.length() - 1) != '_';
    }
}
