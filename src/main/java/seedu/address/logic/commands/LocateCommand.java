package seedu.address.logic.commands;

//@@author jeffreygohkw
import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.BrowserPanelLocateEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Locates a person's address on Google Maps identified using it's last displayed index from the address book.
 */
public class LocateCommand extends Command {

    public static final String COMMAND_WORD = "locate";
    public static final String COMMAND_ALIAS = "loc";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Locates the address of the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_LOCATE_PERSON_SUCCESS = "Searching for Person at Index: %1$s";
    public static final String MESSAGE_PRIVATE_ADDRESS_FAILURE = "Person %1$s has a Private Address";
    public static final String MESSAGE_NO_ADDRESS_FAILURE = "Person %1$s has no Address";

    private final Index targetIndex;

    public LocateCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        if (model.getFilteredPersonList().get(targetIndex.getZeroBased()).getAddress().getIsPrivate()) {
            throw new CommandException(String.format(MESSAGE_PRIVATE_ADDRESS_FAILURE, targetIndex.getOneBased()));
        }
        if (model.getFilteredPersonList().get(targetIndex.getZeroBased()).getAddress().toString().equals("")) {
            throw new CommandException(String.format(MESSAGE_NO_ADDRESS_FAILURE, targetIndex.getOneBased()));
        }
        EventsCenter.getInstance().post(new BrowserPanelLocateEvent(
                model.getFilteredPersonList().get(targetIndex.getZeroBased())));
        return new CommandResult(String.format(MESSAGE_LOCATE_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof LocateCommand // instanceof handles nulls
                && this.targetIndex.equals(((LocateCommand) other).targetIndex)); // state check
    }
}
