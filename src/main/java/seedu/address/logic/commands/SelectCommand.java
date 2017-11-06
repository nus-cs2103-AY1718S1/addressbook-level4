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
public class SelectCommand extends Command {

    public static final String COMMAND_WORD = "select";
    public static final String COMMAND_WORD_ALIAS = "s";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the last person listing. If no index"
            + " is provided, the next person in the list is selected.\n"
            + "Parameters: INDEX (optional, must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_SELECT_PERSON_SUCCESS = "Selected Person: %1$s";
    private static final int INDEX_FIRST_PERSON = 1;

    private final Index targetIndex;

    public SelectCommand() {
        this.targetIndex = null;
    }

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = listObserver.getCurrentFilteredList();

        Index selectIndex;

        if (targetIndex == null) {
            if (model.getSelectedPerson() == null) {
                selectIndex = Index.fromOneBased(INDEX_FIRST_PERSON);
            } else {
                selectIndex = Index.fromZeroBased((lastShownList.indexOf(model.getSelectedPerson()) + 1)
                        % lastShownList.size());
            }
        } else {
            selectIndex = targetIndex;
        }

        if (selectIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.updateSelectedPerson(lastShownList.get(selectIndex.getZeroBased()));
        EventsCenter.getInstance().post(new JumpToListRequestEvent(selectIndex));

        String currentList = listObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(MESSAGE_SELECT_PERSON_SUCCESS, selectIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && ((this.targetIndex == null && ((SelectCommand) other).targetIndex == null) // both targetIndex null
                || this.targetIndex.equals(((SelectCommand) other).targetIndex))); // state check
    }
}
