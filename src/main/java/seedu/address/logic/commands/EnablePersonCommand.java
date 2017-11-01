package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.ModelToggleEvent;
import seedu.address.commons.events.ui.ToggleToPersonViewEvent;

/**
 * Enables person commands for the user
 */
public class EnablePersonCommand extends Command {

    public static final String COMMAND_WORD = "person";

    public static final String MESSAGE_SUCCESS = "Showing all persons and only person commands are enabled";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ModelToggleEvent(ModelToggleEvent.Toggle.personEnabled));
        EventsCenter.getInstance().post(new ToggleToPersonViewEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
