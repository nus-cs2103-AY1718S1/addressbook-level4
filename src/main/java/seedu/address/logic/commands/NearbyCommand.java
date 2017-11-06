package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
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
            + "Parameters: INDEX (optional, must be a positive integer if present)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NEARBY_PERSON_SUCCESS = "Selected person in same area: %1$s";
    public static final String MESSAGE_NO_NEARBY_PERSON = "There is only one person in this area";

    private final Index targetIndex;

    public NearbyCommand() {
        this.targetIndex = null;
    }

    public NearbyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> nearbyList = model.getNearbyPersons();

        if (nearbyList == null || nearbyList.size() == 0) {
            throw new CommandException(Messages.MESSAGE_NO_PERSON_SELECTED);
        }

        if (nearbyList.size() == 1) {
            throw new CommandException(MESSAGE_NO_NEARBY_PERSON);
        }

        Index nearbyIndex;

        if (targetIndex != null) {
            if (targetIndex.getZeroBased() >= nearbyList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
            nearbyIndex = targetIndex;
        } else {
            nearbyIndex = Index.fromZeroBased((nearbyList.indexOf(model.getSelectedPerson()) + 1) % nearbyList.size());
        }

        model.updateSelectedPerson(nearbyList.get(nearbyIndex.getZeroBased()));
        EventsCenter.getInstance().post(new JumpToNearbyListRequestEvent(nearbyIndex));

        String currentList = listObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(MESSAGE_NEARBY_PERSON_SUCCESS, nearbyIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NearbyCommand // instanceof handles nulls
                && ((this.targetIndex == null && ((NearbyCommand) other).targetIndex == null) // both targetIndex null
                || this.targetIndex.equals(((NearbyCommand) other).targetIndex))); // state check
    }
}
