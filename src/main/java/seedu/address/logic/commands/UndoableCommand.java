package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.ReadOnlyRolodex;
import seedu.address.model.Rolodex;

/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private ReadOnlyRolodex previousRolodex;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#rolodex}.
     */
    private void saveRolodexSnapshot() {
        requireNonNull(model);
        this.previousRolodex = new Rolodex(model.getRolodex());
    }

    /**
     * Reverts the Rolodex to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousRolodex);
        model.resetData(previousRolodex);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    /**
     * Executes the command and updates the filtered person
     * list to show all persons.
     */
    protected final void redo() {
        requireNonNull(model);
        try {
            executeUndoableCommand();
        } catch (CommandException ce) {
            throw new AssertionError("The command has been successfully executed previously; "
                    + "it should not fail now");
        }
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveRolodexSnapshot();
        return executeUndoableCommand();
    }
}
