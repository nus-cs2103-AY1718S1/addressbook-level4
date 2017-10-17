package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.UserPrefs;

/**
 * Edits the details of an existing person in the address book.
 */
public class PrefCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "pref";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits user preferences "
            + "Parameters: KEY NEW_VALUE"
            + "Example: " + " backgroundColour" + " #ff0000";

    public static final String MESSAGE_EDIT_PREF_SUCCESS = "Edited preference: %1$s";
    public static final String MESSAGE_NOT_EDITED = "Please enter the new value for the preference";
    public static final String MESSAGE_PREF_KEY_NOT_FOUND = "Invalid preference key: %1$s";
    public static final String MESSAGE_ACCESSING_PREF_ERROR = "Unable to read preference value for %1$s";

    private static String prefKey;

    /**
     */
    public PrefCommand(String prefKey) {
        this.prefKey = prefKey;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);
        String prefValue = "";
        prefKey = prefKey.trim();
        try {
            UserPrefs prefs = model.getUserPrefs();
            // Attempt to get pref value by accessing the correspnding get method
            String getMethodName = "get" + prefKey;
            Class userPrefsClass = prefs.getClass();
            Method getMethod = userPrefsClass.getMethod(getMethodName, null);
            prefValue = getMethod.invoke(prefs, null).toString();
        } catch (NoSuchMethodException e) {
            throw new CommandException(String.format(MESSAGE_PREF_KEY_NOT_FOUND, prefKey));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CommandException(String.format(MESSAGE_ACCESSING_PREF_ERROR, prefKey));
        }
        return new CommandResult(prefValue);
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof PrefCommand)) {
            return false;
        }
        return true;
    }
}
