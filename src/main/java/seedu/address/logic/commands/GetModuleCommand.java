//@@author jshoung
package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ClearPersonSelectionEvent;
import seedu.address.commons.events.ui.GetModuleRequestEvent;

/**
 *
 * Opens NUSMods webpage to get module information.
 *
 */
public class GetModuleCommand extends Command {
    public static final String COMMAND_WORD = "getmodule";
    public static final String COMMAND_ALIAS = "gm";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Shows module information.\n"
            + "Parameters: KEYWORD\n"
            + "Example: " + COMMAND_WORD + " CS2101" + "\n"
            + "Example: " + COMMAND_ALIAS + " CS2103T";

    public static final String SHOWING_MODULE_MESSAGE = "Loaded module information for: ";

    private final String module;

    public GetModuleCommand(String module) {
        this.module = module;
    }

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new GetModuleRequestEvent(module));
        EventsCenter.getInstance().post(new ClearPersonSelectionEvent());
        return new CommandResult(SHOWING_MODULE_MESSAGE + module);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof GetModuleCommand // instanceof handles nulls
                && this.module.equals(((GetModuleCommand) other).module)); // state check
    }
}
//@@author
