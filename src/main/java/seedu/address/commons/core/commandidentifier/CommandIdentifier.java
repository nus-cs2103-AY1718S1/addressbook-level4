package seedu.address.commons.core.commandidentifier;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.google.common.collect.ImmutableList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.BackupCommand;
import seedu.address.logic.commands.CalendarCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CopyCommand;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.EmailCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.LocateCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.ScheduleCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.WelcomeCommand;
import seedu.address.model.person.Country;

//@@author CT15

/**
 * Represents the argument that help command takes in.
 * Guarantees: parameter is present and not null, parameter is validated.
 */
public class CommandIdentifier {
    public static final String MESSAGE_COMMAND_WORD_CONSTRAINTS = "The command argument should be one of the "
            + "following (or their aliases):\n"
            + HelpCommand.COMMAND_QUICK_HELP_WORD + ", "
            + AddCommand.COMMAND_WORD + ", "
            + CalendarCommand.COMMAND_WORD + ", "
            + ClearCommand.COMMAND_WORD + ", "
            + Country.COMMAND_WORD + ", "
            + CopyCommand.COMMAND_WORD + ", "
            + DeleteCommand.COMMAND_WORD + ", "
            + EditCommand.COMMAND_WORD + ", "
            + EmailCommand.COMMAND_WORD + ", "
            + ExitCommand.COMMAND_WORD + ", "
            + FindCommand.COMMAND_WORD + ", "
            + HelpCommand.COMMAND_WORD + ", "
            + HistoryCommand.COMMAND_WORD + ", "
            + ListCommand.COMMAND_WORD + ", "
            + RedoCommand.COMMAND_WORD + ", "
            + ScheduleCommand.COMMAND_WORD + ", "
            + LocateCommand.COMMAND_WORD + ", "
            + UndoCommand.COMMAND_WORD + ", "
            + WelcomeCommand.COMMAND_WORD + ", "
            + Country.COMMAND_WORD + ", "
            + BackupCommand.COMMAND_WORD;

    public static final List<String> COMMAND_VALIDATION_LIST = ImmutableList.of(
            HelpCommand.COMMAND_QUICK_HELP_WORD,
            AddCommand.COMMAND_WORD, AddCommand.COMMAND_ALIAS,
            CalendarCommand.COMMAND_WORD, CalendarCommand.COMMAND_ALIAS,
            ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_ALIAS, Country.COMMAND_WORD,
            CopyCommand.COMMAND_WORD, CopyCommand.COMMAND_ALIAS,
            DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_ALIAS,
            EditCommand.COMMAND_WORD, EditCommand.COMMAND_ALIAS,
            EmailCommand.COMMAND_WORD, EmailCommand.COMMAND_ALIAS,
            ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_ALIAS,
            FindCommand.COMMAND_WORD, FindCommand.COMMAND_ALIAS,
            HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_ALIAS,
            HistoryCommand.COMMAND_WORD, HistoryCommand.COMMAND_ALIAS,
            ListCommand.COMMAND_WORD, ListCommand.COMMAND_ALIAS,
            RedoCommand.COMMAND_WORD, RedoCommand.COMMAND_ALIAS,
            ScheduleCommand.COMMAND_WORD, ScheduleCommand.COMMAND_ALIAS,
            LocateCommand.COMMAND_WORD, LocateCommand.COMMAND_ALIAS,
            UndoCommand.COMMAND_WORD, UndoCommand.COMMAND_ALIAS,
            WelcomeCommand.COMMAND_WORD, WelcomeCommand.COMMAND_ALIAS,
            Country.COMMAND_WORD, // no alias
            BackupCommand.COMMAND_WORD, BackupCommand.COMMAND_ALIAS);

    public final String value;

    /**
     * Validates given command identifier.
     *
     * @throws IllegalValueException if the given commandIdentifier string is invalid.
     */
    public CommandIdentifier(String commandIdentifier) throws IllegalValueException {
        requireNonNull(commandIdentifier);
        if (!isValidCommandIdentifier(commandIdentifier) && !commandIdentifier.equals("")) {
            throw new IllegalValueException(MESSAGE_COMMAND_WORD_CONSTRAINTS);
        }
        this.value = commandIdentifier;
    }

    /**
     * Returns true if a given string is a valid existing command identifier.
     */
    public static boolean isValidCommandIdentifier(String test) {
        return COMMAND_VALIDATION_LIST.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommandIdentifier // instanceof handles nulls
                && this.value.equals(((CommandIdentifier) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
