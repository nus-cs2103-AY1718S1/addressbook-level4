package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_PERSONS;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_REMINDERS;

import seedu.address.commons.core.EventsCenter;
import seedu.address.commons.events.ui.ChangeFontSizeEvent;
import seedu.address.commons.events.ui.ChangeThemeEvent;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.font.FontSize;
import seedu.address.model.theme.Theme;
/**
 * Represents a command which can be undone and redone.
 */
public abstract class UndoableCommand extends Command {
    private static String previousFontSize = FontSize.getCurrentFontSizeLabel();
    private static String previousTheme = Theme.getCurrentTheme();
    private ReadOnlyAddressBook previousAddressBook;

    protected abstract CommandResult executeUndoableCommand() throws CommandException;

    /**
     * Stores the current state of {@code model#addressBook}.
     */
    private void saveAddressBookSnapshot() {
        requireNonNull(model);
        this.previousAddressBook = new AddressBook(model.getAddressBook());

        previousFontSize = FontSize.getCurrentFontSizeLabel();
        previousTheme = Theme.getCurrentTheme();
    }

    /**
     * Reverts the AddressBook to the state before this command
     * was executed and updates the filtered person list to
     * show all persons.
     */
    protected final void undo() {
        requireAllNonNull(model, previousAddressBook);

        model.resetData(previousAddressBook);
        model.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        //@@author RonakLakhotia
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        //@@author ChenXiaoman

        //Revert font size
        if (this instanceof ChangeFontSizeCommand) {
            FontSize.setCurrentFontSizeLabel(previousFontSize);
            EventsCenter.getInstance().post(new ChangeFontSizeEvent("", previousFontSize));
        }

        //Revert theme
        if (this instanceof ChangeThemeCommand) {
            Theme.setCurrentTheme(previousTheme);
            EventsCenter.getInstance().post(new ChangeThemeEvent(previousTheme));
        }
        //@@author
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
        //@@author RonakLakhotia
        model.updateFilteredReminderList(PREDICATE_SHOW_ALL_REMINDERS);
        //@@author generated
    }

    @Override
    public final CommandResult execute() throws CommandException {
        saveAddressBookSnapshot();
        return executeUndoableCommand();
    }
}
