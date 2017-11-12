package seedu.address.logic.commands;

import java.util.List;

import javafx.collections.ObservableList;
import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.core.ListObserver;
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
    public static final String MESSAGE_EMPTY_LIST_SELECTION_FAILURE = "List is empty!";
    private static final int INDEX_FIRST_PERSON = 1;

    private final Index targetIndex;

    public SelectCommand() throws CommandException {
        ObservableList<ReadOnlyPerson> lastShownList = ListObserver.getCurrentFilteredList();
        if (lastShownList.isEmpty()) {
            throw new CommandException(MESSAGE_EMPTY_LIST_SELECTION_FAILURE);
        }
        if (ListObserver.getSelectedPerson() == null) {
            targetIndex = Index.fromOneBased(INDEX_FIRST_PERSON);
        } else {
            targetIndex = Index.fromZeroBased((ListObserver.getIndexOfSelectedPersonInCurrentList().getZeroBased() + 1)
                    % lastShownList.size());
        }
    }

    public SelectCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> lastShownList = ListObserver.getCurrentFilteredList();

        if (targetIndex.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }

        model.updateSelectedPerson(lastShownList.get(targetIndex.getZeroBased()));
        EventsCenter.getInstance().post(new JumpToListRequestEvent(targetIndex));

        String currentList = ListObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(MESSAGE_SELECT_PERSON_SUCCESS, targetIndex.getOneBased()));
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof SelectCommand // instanceof handles nulls
                && this.targetIndex.equals(((SelectCommand) other).targetIndex)); // state check
    }
}
