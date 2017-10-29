package seedu.address.logic.commands;

import seedu.address.MainApp;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ExitAppRequestEvent;
import seedu.address.ui.Ui;

public class LogoutCommand extends Command {

    public static final String COMMAND_WORD = "logout";

    public static final String MESSAGE_EXIT_ACKNOWLEDGEMENT = "Logging out as requested ...";

    @Override
    public CommandResult execute() {
        Ui ui = MainApp.getUi();
        ui.restart("address");

        return new CommandResult(MESSAGE_EXIT_ACKNOWLEDGEMENT);
    }

}
