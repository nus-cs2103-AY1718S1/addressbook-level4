package seedu.address.logic.commands.person;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.model.ModelToggleEvent;
import seedu.address.commons.events.ui.ToggleToAliasViewEvent;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.CommandResult;

//@@author Alim95
/**
 * Lists all aliases in the address book to the user.
 */
public class ListAliasCommand extends Command {

    public static final String COMMAND_WORD = "listalias";

    public static final String MESSAGE_SUCCESS = "Listed all alias";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ToggleToAliasViewEvent());
        EventsCenter.getInstance().post(new ModelToggleEvent(ModelToggleEvent.Toggle.aliasEnabled));
        return new CommandResult(MESSAGE_SUCCESS);
    }
}
