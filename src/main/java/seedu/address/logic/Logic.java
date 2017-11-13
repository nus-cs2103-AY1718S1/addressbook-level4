package seedu.address.logic;

import java.io.IOException;
import java.net.URISyntaxException;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.reminders.ReadOnlyReminder;

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
    CommandResult execute(String commandText) throws CommandException, ParseException, IOException, URISyntaxException;

    /** Returns an unmodifiable view of the filtered list of persons */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /** Returns an unmodifiable view of the birthday panel filtered person list */
    ObservableList<ReadOnlyPerson> getBirthdayPanelFilteredPersonList();

    /** Returns an unmodifiable view of the reminder list */
    ObservableList<ReadOnlyReminder> getReminderList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();

    /** Returns top popular contacts for the GUI panel ( capped at size 5) */
    ObservableList<ReadOnlyPerson> getListOfPersonsForPopularContacts();
}
