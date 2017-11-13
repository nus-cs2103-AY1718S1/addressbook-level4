package seedu.address.logic;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.scene.control.TabPane;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.person.ReadOnlyPerson;
import seedu.address.model.user.exceptions.DuplicateUserException;
import seedu.address.ui.CalendarView;
import seedu.address.ui.Ui;

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
    CommandResult execute(String commandText) throws CommandException, ParseException, DuplicateUserException;

    /**
     * Returns an unmodifiable view of the filtered list of persons
     */
    ObservableList<ReadOnlyPerson> getFilteredPersonList();

    /**
     * Returns an unmodifiable view of the filtered list of events
     */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    /**
     * Returns an unmodifiable view of the commands available
     */
    ArrayList<ArrayList<String>> getCommands();

    /**
     * Returns the set alias for command, null otherwise
     */
    public String getAliasForCommand(String commandName);

    /**
     * Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object
     */
    ListElementPointer getHistorySnapshot();

    /**
     * Passing the UI Object TabPane
     */
    void setTabPane(TabPane tabPane);

    /**
     * Passing the UI Object Calendar
     */
    void setCalendarView(CalendarView calendarView);

    /**
     * Passing the UI Object
     */
    void setUi(Ui ui);

}
