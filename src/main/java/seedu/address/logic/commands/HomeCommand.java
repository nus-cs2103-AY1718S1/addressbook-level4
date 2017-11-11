package seedu.address.logic.commands;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.HomeRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;

/**
 * Changes the graphs shown on the graph panel in the address book.
 */
//@@author nahtanojmil
public class HomeCommand extends Command {

    public static final String COMMAND_WORD = "home";

    public static final String MESSAGE_SELECT_HOME_SUCCESS = "Welcome Home";


    public HomeCommand() {}

    @Override
    public CommandResult execute() throws CommandException {
        EventsCenter.getInstance().post(new HomeRequestEvent());
        return new CommandResult(MESSAGE_SELECT_HOME_SUCCESS);
    }

}
