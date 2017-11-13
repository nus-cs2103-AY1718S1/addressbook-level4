//@@author majunting
package seedu.address.logic.commands;

import static seedu.address.commons.core.Messages.MESSAGE_EXECUTION_FAILURE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * locates a person from a given address and show the route on the browser panel.
 */
public class LocateCommand extends Command {
    public static final String COMMAND_WORD = "locate";
    public static final String COMMAND_ALIAS = "loc";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Finds the route from the given address to  "
            + "the address of the specified contact .\n"
            + "Parameters: index address\n"
            + "Example: " + COMMAND_WORD + " 1 " + PREFIX_ADDRESS + "311, Clementi Ave 2, #02-25";
    public static final String MESSAGE_SUCCESS = "Located person %1$s";

    private final Index index;
    private final String address;

    public LocateCommand (Index Index, String address) {
        this.index = Index;
        this.address = address;
    }

    @Override
    public CommandResult execute() throws CommandException {
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(MESSAGE_EXECUTION_FAILURE, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        ReadOnlyPerson personToLocate = lastShownList.get(index.getZeroBased());
        EventsCenter.getInstance().post(new BrowserPanelLocateEvent(address, personToLocate.getAddress().toString()));
        return new CommandResult(String.format(MESSAGE_SUCCESS, index.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocateCommand // instanceof handles nulls
                && index == ((LocateCommand) other).index
                && address.equals(((LocateCommand) other).address)); // state check
    }
}
