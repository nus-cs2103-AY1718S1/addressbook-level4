package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.parcel.ReadOnlyParcel;

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

    //@@author kennard123661
    /** Returns an unmodifiable view of the filtered list of parcels */
    ObservableList<ReadOnlyParcel> getFilteredParcelList();

    /** Returns an unmodifiable view of the filtered list of parcels with Status COMPLETE */
    ObservableList<ReadOnlyParcel> getDeliveredParcelList();

    /** Returns an unmodifiable view of the filtered list of parcels with Status not COMPLETE */
    ObservableList<ReadOnlyParcel> getUndeliveredParcelList();

    /** Returns an unmodifiable view of the filtered list of parcels with Status not COMPLETE */
    void setActiveList(boolean isDelivered);
    //@@author

    /** Returns an unmodifiable view of the filtered list of parcels in the active tab */
    ObservableList<ReadOnlyParcel> getActiveList();

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
