package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;

/**
 * API of the Logic component
 */
public interface Logic {
    /**
     * Executes the command and returns the result.
     * @param commandText The command as entered by the user.
     * @return the result of the command execution.
     * @throws CommandException If an error occurs during command execution.
     * @throws ParseException If an error occurs during parsing.
     */
    CommandResult execute(String commandText) throws CommandException, ParseException;

    /** Returns an unmodifiable view of all persons */
    ObservableList<ReadOnlyPerson> getAllPersons();

    /** Sets the current selected person*/
    void updateSelectedPerson(ReadOnlyPerson person);

    /** Resets the filtered list of persons to a list of all persons */
    void resetFilteredPersonList();

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    //@@author jaivigneshvenugopal
    /** Returns an unmodifiable view of the filtered list of blacklisted persons */
    ObservableList<ReadOnlyPerson> getFilteredBlacklistedPersonList();

    /** Returns an unmodifiable view of the filtered list of whitelisted persons */
    ObservableList<ReadOnlyPerson> getFilteredWhitelistedPersonList();
    //@@author

    /** Returns an unmodifiable view of the filtered list of persons with overdue debt */
    ObservableList<ReadOnlyPerson> getFilteredOverduePersonList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
