package seedu.room.logic;

import javafx.collections.ObservableList;
import seedu.room.logic.commands.CommandResult;
import seedu.room.logic.commands.exceptions.CommandException;
import seedu.room.logic.parser.exceptions.ParseException;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.person.Person;
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

    /** Returns an unmodifiable view of the filtered list of events */
    ObservableList<ReadOnlyEvent> getFilteredEventList();

    //@@author shitian007
    /**
     * Updates Picture of resident {@code Person} within model
     * @param resident whose picture is to be updated
     */
    void updatePersonListPicture(Person resident);

    /**
     * Updates autocomplete list within {@code Logic}
     * @param userInput
     */
    void updateAutoCompleteList(String userInput);

    /**
     * @return the latest array of suggestions from {@code Logic}
     */
    String[] getAutoCompleteList();
    //@@author

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
