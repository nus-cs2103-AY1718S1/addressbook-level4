package seedu.room.logic;

import javafx.collections.ObservableList;
import seedu.room.logic.commands.CommandResult;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.logic.parser.exceptions.ParseException;
import seedu.room.model.person.ReadOnlyPerson;

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

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Updates and gets list of Auto-complete Strings */
    void updateAutoCompleteList(String userInput);
    String[] getAutoCompleteList();


    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
