package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Objects;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.UserPrefs;
//@@author liuhang0213
/**
 * Edits the details of an existing person in the address book.
 */
public class PrefCommand extends Command {

    public static final String COMMAND_WORD = "pref";
    public static final String COMMAND_ALIAS = "p";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Edits user preferences "
            + "Parameters: KEY [NEW_VALUE]\n"
            + "Example: " + COMMAND_WORD + " AddressBookName" + " MyNewAddressBook\n"
            + "See help page for a list of available preferences";
    public static final String MESSAGE_TEMPLATE = COMMAND_WORD + "KEY [NEW_VALUE]";

    public static final String MESSAGE_EDIT_PREF_SUCCESS = "Edited preference: %1$s \nfrom %2$s \nto %3$s";
    public static final String MESSAGE_PREF_KEY_NOT_FOUND = "Invalid preference key: %1$s";
    public static final String MESSAGE_ACCESSING_PREF_ERROR = "Unable to read preference value for %1$s";
    public static final String MESSAGE_INVALID_VALUE_ERROR = "Invalid value %1$s for preference key %2$s";

    private String prefKey;
    private String newPrefValue = "";

    /**
     * Creates a new PrefCommand with the given key and value
     */
    public PrefCommand(String prefKey, String newPrefValue) {
        this.prefKey = prefKey;
        this.newPrefValue = newPrefValue;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        String currentPrefValue;
        currentPrefValue = readPrefValue(prefKey);
        if (!newPrefValue.isEmpty()) {
            // Editing preference
            writePrefValue(prefKey, newPrefValue);
            return new CommandResult(String.format(MESSAGE_EDIT_PREF_SUCCESS, prefKey, currentPrefValue,
                    readPrefValue(prefKey)));
        }
        return new CommandResult(currentPrefValue);
    }

    /**
     * Reads current value for the given preference key.
     *
     * @param prefKey Key name of the preference
     * @return Value of the preference
     * @throws CommandException if the preference key is not defined in UserPrefs or not accessible
     */
    private String readPrefValue(String prefKey) throws CommandException {
        String prefValue;
        try {
            UserPrefs prefs = model.getUserPrefs();
            String getMethodName = "get" + prefKey;
            Class<?> userPrefsClass = prefs.getClass();
            Method getMethod = userPrefsClass.getMethod(getMethodName);
            prefValue = getMethod.invoke(prefs).toString();
        } catch (NoSuchMethodException e) {
            throw new CommandException(String.format(MESSAGE_PREF_KEY_NOT_FOUND, prefKey));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CommandException(String.format(MESSAGE_ACCESSING_PREF_ERROR, prefKey));
        }
        return prefValue;
    }

    /**
     * Modifies the value of preference given by the key.
     *
     * @param prefKey Key name of the preference
     * @param newPrefValue New value of the preference
     * @throws CommandException if the preference key is not defined in UserPrefs or not accessible
     */
    private void writePrefValue(String prefKey, String newPrefValue) throws CommandException {
        try {
            UserPrefs prefs = model.getUserPrefs();
            String setMethodName = "set" + prefKey;
            Class<?> userPrefsClass = prefs.getClass();
            Method setMethod = userPrefsClass.getMethod(setMethodName, String.class);
            setMethod.invoke(prefs, newPrefValue);
        } catch (NoSuchMethodException e) {
            throw new CommandException(String.format(MESSAGE_PREF_KEY_NOT_FOUND, prefKey));
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new CommandException(String.format(MESSAGE_INVALID_VALUE_ERROR, newPrefValue, prefKey));
        }
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

        return Objects.equals(this.toString(), other.toString());
    }

    @Override
    public String toString() {
        return prefKey + ": " + newPrefValue;
    }

}
