package seedu.address.logic;

import javafx.collections.ObservableList;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.Model;
import seedu.address.model.parcel.Parcel;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.Status;

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

    /** Returns an unmodifiable view of the filtered list of parcels */
    ObservableList<ReadOnlyParcel> getFilteredParcelList();

    //@@author kennard123661
    /**
     * Returns an unmodifiable view of the filtered list of {@link Parcel} from the {@link Model} that have
     * {@link Status} that is COMPLETED.
     */
    ObservableList<ReadOnlyParcel> getCompletedParcelList();

    /**
     * Returns an unmodifiable view of the filtered list of {@link Parcel}s from the {@link Model} that have
     * {@link Status} that is not COMPLETED.
     */
    ObservableList<ReadOnlyParcel> getUncompletedParcelList();

    /** Sets the active list of {@link Model} at the particular instance
     *
     * @param isCompleted if true, the active list in {@link Model} will be set to the list of {@link Parcel}s
     *                    with {@link Status} that is COMPLETED. Otherwise, it will be set the list of parcels
     *                    with {@link Status} that is not COMPLETED.
     */
    void setActiveList(boolean isCompleted);

    /** Returns an unmodifiable view of the current active list in {@link Model} at the instance it waas called. */
    ObservableList<ReadOnlyParcel> getActiveList();
    //@@author kennard123661

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
