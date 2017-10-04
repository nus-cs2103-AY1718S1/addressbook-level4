package seedu.address.logic.commands;

import java.util.List;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * Selects a person identified using it's last displayed index from the address book.
 */
public class MultiSelectCommand extends Command {

    public static final String COMMAND_WORD = "multiSelect";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_MULTISELECT_SUCCESS = "Selected Person: %1$s";

    private List<Index> indexList;

    public MultiSelectCommand(List<Index> indexList) {
        this.indexList = indexList;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();

        for (Index targetIndex : indexList) {
            if (targetIndex.getZeroBased() >= lastShownList.size()) {
                throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
            }
        }

        EventsCenter.getInstance().post(new JumpToListRequestEvent(indexList.get(0)));
        return new CommandResult(String.format(MESSAGE_MULTISELECT_SUCCESS, indexList));

    }

}
