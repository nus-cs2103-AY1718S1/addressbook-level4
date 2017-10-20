package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.ArrayList;
import java.util.List;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.parcel.ReadOnlyParcel;

/**
 * Imports the parcels and tags of parcelsToAdd to the current AddressBook
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the data of a storage file stored in "
            + "data/import/ directory into Ark.\n"
            + "Parameters: FILE (Must be a valid addressbook file stored in xml format)\n"
            + "Example: " + COMMAND_WORD + " addressbook.xml";

    public static final String MESSAGE_SUCCESS = "Summary: %1$d parcels added and %2$d duplicate parcels not added.\n"
            + "Parcels added: %3$s\nDuplicate Parcels: %4$s";
    public static final String MESSAGE_DUPLICATE_PARCELS = "All parcels in the imported save file will create "
            + "duplicate parcels";

    private final List<ReadOnlyParcel> parcelsToAdd;

    /**
     * Creates an ImportCommand to add the parcels in parcelList {@code ReadOnlyParcel}
     */
    public ImportCommand(List<ReadOnlyParcel> parcelList) {
        parcelsToAdd = parcelList;
    }

    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyParcel> addedParcels = new ArrayList<>(); // list of added parcels
        List<ReadOnlyParcel> duplicateParcels = new ArrayList<>(); // list of duplicate parcels that are not added
        List<ReadOnlyParcel> storedParcels = model.getAddressBook().getParcelList(); // parcels already stored in Ark

        // check if all parcels are duplicates
        if (storedParcels.containsAll(parcelsToAdd)) {
            throw new CommandException(MESSAGE_DUPLICATE_PARCELS);
        }

        model.addAllParcels(parcelsToAdd, addedParcels, duplicateParcels);

        String addedParcelsString = getImportFormattedParcelListString(addedParcels);
        String duplicatedParcelsString = getImportFormattedParcelListString(duplicateParcels);

        return new CommandResult(String.format(MESSAGE_SUCCESS, addedParcels.size(), duplicateParcels.size(),
                addedParcelsString, duplicatedParcelsString));
    }

    /**
     * @return formatted list of parcels added/not added for ImportCommand execution feedback
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
                && hasSameParcels(parcelsToAdd, ((ImportCommand) other).parcelsToAdd));
    }

    /**
     * check if the elements of parcels and otherParcels have the same elements, disregarding order.
     */
    public boolean hasSameParcels(List<ReadOnlyParcel> parcels, List<ReadOnlyParcel> otherParcels) {
        // check # of parcels are equal
        if (parcels.size() != otherParcels.size()) {
            return false;
        }

        return parcels.containsAll(otherParcels);
    }
}
