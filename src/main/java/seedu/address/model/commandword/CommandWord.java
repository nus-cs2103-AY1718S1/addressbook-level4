package seedu.address.model.commandword;

import static java.util.Objects.requireNonNull;

import java.util.List;

import com.google.common.collect.ImmutableList;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.Command;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.UndoCommand;

public class CommandWord {
    public static final String MESSAGE_COMMAND_WORD_CONSTRAINTS = "Invalid command argument!\n"
            + "The command argument should be one of the following: "
            + AddCommand.COMMAND_WORD + ", "
            + ClearCommand.COMMAND_WORD + ", "
            + DeleteCommand.COMMAND_WORD + ", "
            + EditCommand.COMMAND_WORD + ", "
            + ExitCommand.COMMAND_WORD + ", "
            + FindCommand.COMMAND_WORD + ", "
            + HelpCommand.COMMAND_WORD + ", "
            + HistoryCommand.COMMAND_WORD + ", "
            + ListCommand.COMMAND_WORD + ", "
            + RedoCommand.COMMAND_WORD + ", "
            + SelectCommand.COMMAND_WORD + ", "
            + UndoCommand.COMMAND_WORD;

    public static final List<String> COMMAND_VALIDATION_LIST = ImmutableList.of(
            AddCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD,
            EditCommand.COMMAND_WORD,
            ExitCommand.COMMAND_WORD,
            FindCommand.COMMAND_WORD,
            HelpCommand.COMMAND_WORD,
            HistoryCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD,
            RedoCommand.COMMAND_WORD,
            SelectCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD);

    public final String value;

    /**
     * Validates given command word.
     *
     * @throws IllegalValueException if the given tag name string is invalid.
     */
    public CommandWord(String commandWord) throws IllegalValueException {
        requireNonNull(commandWord);
        if (!isValidCommandWord(commandWord) && !commandWord.equals("")) {
            throw new IllegalValueException(MESSAGE_COMMAND_WORD_CONSTRAINTS);
        }
        this.value = commandWord;
    }

    /**
     * Returns true if a given string is a valid existing command word.
     */
    private boolean isValidCommandWord(String test) {
        return COMMAND_VALIDATION_LIST.contains(test);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof CommandWord // instanceof handles nulls
                && this.value.equals(((CommandWord) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }
}
