package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.FindHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.model.Model;
import seedu.address.model.person.PersonContainsRecentPredicate;
import seedu.address.model.person.ReadOnlyPerson;

//@@author karrui
/**
 * Lists all the persons searched by user from the start of app launch.
 */
public class RecentCommand extends Command {
    public static final String COMMAND_WORD = "recent";
    public static final String COMMAND_ALIAS = "rc";
    public static final String MESSAGE_SUCCESS = "Searched persons listed";
    public static final String MESSAGE_NO_RECENT = "You have not yet searched for any persons.";



    @Override
    public CommandResult execute() {
        List<ReadOnlyPerson> previousFinds = findHistory.getHistory();

        if (previousFinds.isEmpty()) {
            return new CommandResult(MESSAGE_NO_RECENT);
        }

        model.updateFilteredPersonList(new PersonContainsRecentPredicate(previousFinds));
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public void setData(Model model, CommandHistory history, FindHistory findHistory, UndoRedoStack undoRedoStack) {
        requireNonNull(findHistory);
        requireNonNull(model);
        this.model = model;
        this.findHistory = findHistory;
    }
}
