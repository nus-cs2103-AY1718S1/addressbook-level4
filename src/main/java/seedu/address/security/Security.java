package seedu.address.security;

import seedu.address.commons.events.BaseEvent;
import seedu.address.storage.SecureStore;

public interface Security extends SecureStore{

    /**
     * Sets up commands that are allowed to execute when the system is secured.
     * @param permittedCommands the commandWord of those commands.
     */
    void configSecurity(String... permittedCommands);

    /**
     * Checks whether the system is secured.
     */
    boolean isSecured();

    /**
     * Sends message to UI by {@param event}.
     */
    void raise(BaseEvent event);

    /**
     * Checks whether the given command has permission for execution.
     */
    boolean isPermittedCommand(String commandWord);

}
