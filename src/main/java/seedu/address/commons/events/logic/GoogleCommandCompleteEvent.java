package seedu.address.commons.events.logic;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.BaseEvent;
import seedu.address.commons.events.ui.NewResultAvailableEvent;

//@@author philemontan
/**
 * An event fired indicating a GoogleCommand instance has successfully completed. Attributes redirectUrl and
 * commandType are provided by the firer, for identifying the the GoogleCommand subclass of the instance.
 */
public class GoogleCommandCompleteEvent extends BaseEvent {
    private static final String EXPORT_PROGRESS_MESSAGE = "The export to Google will be executed in the background."
            + " You can track this progress by reloading the Google Contacts page. "
            + "\nSimply right click the browser -> click reload page.";
    private static final String IMPORT_PROGRESS_MESSAGE = "Import from Google is executing.\nNote: Limit of import"
            + " is 2000 contacts. Some lag with DoC is to be expected, if you are importing close to this limit.";
    private static final String EXPORT_COMMAND_TYPE = "GOOGLE_export";
    private static final String IMPORT_COMMAND_TYPE = "GOOGLE_import";
    private String redirectUrl;
    private String commandType;

    public GoogleCommandCompleteEvent(String inputUrl, String inputCommandType) {
        redirectUrl = inputUrl;
        commandType = inputCommandType;
        postCommandMessage();
    }

    public String getRedirectUrl() {
        return redirectUrl;
    }
    public String getCommandType() {
        return commandType;
    }

    /**
     * Prompts the user with the relevant message, based on the command being executed
     */
    private void postCommandMessage() {
        switch(commandType) {
        case EXPORT_COMMAND_TYPE: {
            EventsCenter.getInstance().post(new NewResultAvailableEvent(EXPORT_PROGRESS_MESSAGE, false));
        } break;
        case IMPORT_COMMAND_TYPE: {
            EventsCenter.getInstance().post(new NewResultAvailableEvent(IMPORT_PROGRESS_MESSAGE, false));
        } break;
        default : {
            return;
        }
        }
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
