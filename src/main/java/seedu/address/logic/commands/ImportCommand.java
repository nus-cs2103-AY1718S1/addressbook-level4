package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.ModelManager;
import seedu.address.model.parcel.ReadOnlyParcel;
import seedu.address.model.parcel.UniqueParcelList;

//@@author kennard123661
/**
 * Imports the parcels and tags stored in {@code parcels} into the current instance of Ark.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the data of a storage file stored in "
            + "data/import/ directory into Ark.\n"
            + "Parameters: FILE (Must be a valid Ark storage file stored in xml format) i.e.\n"
            + "Example: " + COMMAND_WORD + " ark_storage";

    public static final String MESSAGE_SUCCESS_SUMMARY = "Summary: %1$d parcels added and %2$d duplicate "
            + "parcels not added.\n";
    public static final String MESSAGE_SUCCESS_BODY = "Parcels added: %3$s\nDuplicate Parcels: %4$s";
    public static final String MESSAGE_SUCCESS = MESSAGE_SUCCESS_SUMMARY + MESSAGE_SUCCESS_BODY;

    public static final String MESSAGE_INVALID_DUPLICATE_PARCELS = "All parcels in the imported save file will create "
            + "duplicate parcels";
    public static final String MESSAGE_INVALID_FILE_EMPTY = "File to import is empty";

    private final List<ReadOnlyParcel> parcels;

    /**
     * Creates an ImportCommand to add all {@code ReadOnlyParcel}s in {@param parcelList}.
     */
    public ImportCommand(List<ReadOnlyParcel> parcelList) {
        parcels = parcelList;
    }

    /**
     * Adds all unique parcels in {@code parcels} to the {@link UniqueParcelList} of the {@link AddressBook}. Ignores
     * parcels that will create duplicates in the {@link UniqueParcelList}. To see the internal logic, see
     * {@link ModelManager#addAllParcels(List, List, List)} for more information
     *
     * @return {@link CommandResult} created by {@link ImportCommand#executeUndoableCommand()}
     * @throws CommandException if {@code parcels} contain only duplicate parcels.
     * @see ModelManager#addAllParcels(List, List, List)
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        if (parcels.isEmpty()) {
            throw new CommandException(MESSAGE_INVALID_FILE_EMPTY);
        }

        List<ReadOnlyParcel> uniqueParcels = new ArrayList<>(); // list of unique parcels added to Ark.
        List<ReadOnlyParcel> duplicateParcels = new ArrayList<>(); // list of duplicate parcels that are not added
        List<ReadOnlyParcel> storedParcels = model.getAddressBook().getParcelList(); // parcels already stored in Ark

        // check if all parcels are duplicates
        if (storedParcels.containsAll(parcels)) {
            throw new CommandException(MESSAGE_INVALID_DUPLICATE_PARCELS);
        }

        model.addAllParcels(parcels, uniqueParcels, duplicateParcels);

        String addedParcelsString = getImportFormattedParcelListString(uniqueParcels);
        String duplicatedParcelsString = getImportFormattedParcelListString(duplicateParcels);

        return new CommandResult(String.format(MESSAGE_SUCCESS, uniqueParcels.size(), duplicateParcels.size(),
                addedParcelsString, duplicatedParcelsString));
    }

    /**
     * Returns formatted list of parcels added/not added for ImportCommand execution result.
     */
    public static String getImportFormattedParcelListString(List<ReadOnlyParcel> parcels) {
        if (parcels.size() == 0) {
            return "\n  (none)";
        }

        String formattedString = "";

        for (ReadOnlyParcel parcel : parcels) {
            formattedString += "\n  " + parcel.toString();
        }

        return formattedString;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ImportCommand // instanceof handles nulls
                && hasSameParcels(parcels, ((ImportCommand) other).parcels));
    }

    /**
     * check if {@code parcels} and {@code otherParcels} have the same elements, disregarding order.
     */
    public boolean hasSameParcels(List<ReadOnlyParcel> parcels, List<ReadOnlyParcel> otherParcels) {
        // check # of parcels are equal
        if (parcels.size() != otherParcels.size()) {
            return false;
        }

        return parcels.containsAll(otherParcels);
    }
}
