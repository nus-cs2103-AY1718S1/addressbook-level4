package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.ListObserver;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToNearbyListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author khooroko
/**
 * Selects a person identified using last displayed index from currently selected person's nearby list.
 */
public class NearbyCommand extends Command {

    public static final String COMMAND_WORD = "nearby";
    public static final String COMMAND_WORD_ALIAS = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the currently selected person's "
            + "nearby listing. If no index is provided, the next person in the nearby list is selected.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NEARBY_PERSON_SUCCESS = "Selected person in same area: %1$s";
    public static final String MESSAGE_INVALID_NEARBY_INDEX = "The index provided is invalid. There are only %d "
            + "contacts in this area";

    private final Index targetIndex;

    public NearbyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> nearbyList = model.getNearbyPersons();

        if (ListObserver.getSelectedPerson() == null) {
            throw new CommandException(Messages.MESSAGE_NO_PERSON_SELECTED);
        }

        if (targetIndex.getZeroBased() >= nearbyList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_NEARBY_INDEX, nearbyList.size()));
        }

        model.updateSelectedPerson(nearbyList.get(targetIndex.getZeroBased()));
        EventsCenter.getInstance().post(new JumpToNearbyListRequestEvent(targetIndex));

        String currentList = ListObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(MESSAGE_NEARBY_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NearbyCommand // instanceof handles nulls
                && this.targetIndex.equals(((NearbyCommand) other).targetIndex)); // state check
    }
}
