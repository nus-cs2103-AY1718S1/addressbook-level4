package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.alias.ReadOnlyAliasToken;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     *
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException   If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /**
     * Returns an unmodifiable view of the filtered list of persons
     */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered list of AliasTokens
     */
    ObservableList<ReadOnlyAliasToken> getFilteredAliasTokenList();

    /**
     * Returns true if first token of user input is a valid command word
     */
    boolean isCommandWord(String keyword);

    /**
     * Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object
     */
    ListElementPointer getHistorySnapshot();
}
