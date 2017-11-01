# kennard123661
###### \java\seedu\address\logic\commands\ImportCommand.java
``` java
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
```
###### \java\seedu\address\logic\parser\ImportCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ImportCommand object
 * @throws ParseException if its an illegal value or the file name is not an alphanumeric xml.
 * This is to prevent directory traversal attacks through the import command, by creating a whitelist of accepted
 * commands through a validation regex.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    public static final String FILE_NAME_VALIDATION_REGEX = "^([A-z0-9])\\w+[.][x][m][l]";
    public static final String MESSAGE_FILE_NAME_INVALID = "File name should be an xml file that only contains "
            + "alphanumeric characters";

    /**
     * Parses the given {@code String} of arguments in the context of the ImportCommand
     * and returns an ImportCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format, or the storage file is not able to
     * be found or it is in the wrong data format.
     */
    public ImportCommand parse(String arg) throws ParseException {
        String trimmedArgument = arg.trim();

        // preventing directory traversal attack
        if (!isValidFileName(trimmedArgument)) {
            throw new ParseException(MESSAGE_FILE_NAME_INVALID);
        }

        try {
            ReadOnlyAddressBook readOnlyAddressBook = ParserUtil.parseImportFilePath("./data/import/"
                    + trimmedArgument);
            return new ImportCommand(readOnlyAddressBook.getParcelList());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE) + "\nMore Info: "
                            + ive.getMessage());
        }
    }

    /**
     * checks if the file is alphanumeric name
     */
    public boolean isValidFileName(String fileName) {
        return fileName.matches(FILE_NAME_VALIDATION_REGEX);
    }

}
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Adds all Parcel objects in parcels to the AddressBook
     * @param parcels list of parcels to add
     * @param parcelsAdded parcels that are added without causing duplicates
     * @param duplicateParcels parcels that are not added because doing so will cause duplicates
     */
    void addAllParcels(List<ReadOnlyParcel> parcels, List<ReadOnlyParcel> parcelsAdded, List<ReadOnlyParcel>
            duplicateParcels);

    /**
     * Replaces the given parcel {@code target} with {@code editedParcel}.
     *
     * @throws DuplicateParcelException if updating the parcel's details causes the parcel to be equivalent to
     *                                  another existing parcel in the list.
     * @throws ParcelNotFoundException  if {@code target} could not be found in the list.
     */
    void updateParcel(ReadOnlyParcel target, ReadOnlyParcel editedParcel)
            throws DuplicateParcelException, ParcelNotFoundException;

    /**
     * Returns an unmodifiable view of the filtered parcel list
     */
    ObservableList<ReadOnlyParcel> getFilteredParcelList();

    /**
     * Updates the filter of the filtered parcel list to filter by the given {@code predicate}.
     *
     * @throws NullPointerException if {@code predicate} is null.
     */
    void updateFilteredParcelList(Predicate<ReadOnlyParcel> predicate);

    /**
     * Method to sort the lists of addresses by delivery date with the earliest date in front
     */
    void maintainSorted();

    /**
     * Method to check if there is a parcel selected.
     */
    boolean hasSelected();

    /**
     * Method to toggle whether or not a parcel has been selected
     */
    void select();

    /**
     * Method to toggle whether or not a parcel has been selected
     */
    void unselect();

    /**
     * Method to set the prevIndex attribute to the specified target.
     */
    void setPrevIndex(Index target);

    /**
     * Method to retrieve Index of last selected Parcel Card.
     */
    Index getPrevIndex();

    /**
     * Method to force the model to select a card without using the select command.
     */
    void forceSelect(Index target);

    /**
     * Method to reselect a parcel card if there is a card selected.
     */
    void reselect(ReadOnlyParcel parcel);

}


```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addAllParcels(List<ReadOnlyParcel> parcels, List<ReadOnlyParcel> parcelsAdded,
                                           List<ReadOnlyParcel> duplicateParcels) {

        for (ReadOnlyParcel parcel : parcels) {
            ReadOnlyParcel parcelToAdd = new Parcel(parcel);
            try {
                addressBook.addParcel(parcelToAdd);
                parcelsAdded.add(parcelToAdd);
            } catch (DuplicateParcelException ive) {
                duplicateParcels.add(parcelToAdd);
            }
        }

        updateFilteredParcelList(PREDICATE_SHOW_ALL_PARCELS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\parcel\PostalCode.java
``` java
/**
 * Represents a Address' postal code in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {

    public static final String MESSAGE_POSTAL_CODE_CONSTRAINTS =
            "Parcel's postal code should start with 'S' or 's' appended with 6 digits";

    /*
     * Postal Code should contain 's' or 'S' appended with  6 digits, and it should not be blank
     * otherwise " " (a blank string) becomes a valid input.
     */
    public static final String POSTAL_CODE_VALIDATION_REGEX = "^[sS]{1}[0-9]{6}$";

    public final String value;

    /**
     * Validates given postal code.
     *
     * @throws IllegalValueException if given postalCode string is invalid.
     */
    public PostalCode(String postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!isValidPostalCode(trimmedPostalCode)) {
            throw new IllegalValueException(MESSAGE_POSTAL_CODE_CONSTRAINTS);
        }
        this.value = trimmedPostalCode.toUpperCase();
    }

    /**
     * Returns true if a given string is a valid parcel name.
     */
    public static boolean isValidPostalCode(String test) {
        return test.matches(POSTAL_CODE_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof PostalCode // instanceof handles nulls
                && this.value.equals(((PostalCode) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\parcel\Status.java
``` java
/**
 * Status represents the delivery status of a parcel
 * It can only be one of these values: PENDING, DELIVER, COMPLETED
 */
public enum Status {

    PENDING, DELIVERING, COMPLETED, OVERDUE;

    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Status can only be PENDING, COMPLETED, COMPLETED or OVERDUE";

    public static Status getInstance(String status) throws IllegalValueException {
        String trimmedAndUpperCasedStatus = status.trim().toUpperCase();

        if (!isValidStatus(trimmedAndUpperCasedStatus)) {
            throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
        }

        switch (trimmedAndUpperCasedStatus) {

        case "OVERDUE":
            return OVERDUE;

        case "PENDING":
            return PENDING;

        case "DELIVERING":
            return DELIVERING;

        case "COMPLETED":
            return COMPLETED;

        default:
            throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
        }
    }

    /**
     * @return true if status is case-insensitive equal to the value of any enum Status values.
     */
    public static boolean isValidStatus(String status) {
        switch(status) {
        case "PENDING":  // fallthrough
        case "DELIVERING": // fallthrough
        case "COMPLETED": // fallthrough
        case "OVERDUE": // fallthrough
            return true;

        default:
            return false;
        }
    }
}
```
###### \java\seedu\address\model\parcel\TrackingNumber.java
``` java
/**
 * Represents the tracking number of a parcel.
 */
public class TrackingNumber {

    public static final String MESSAGE_TRACKING_NUMBER_CONSTRAINTS =
            "Parcel tracking number should start with 'RR', followed by 9 digits, and ends with 'SG'";
    public static final String TRACKING_NUMBER_VALIDATION_REGEX = "^[R]{2}[0-9]{9}[S][G]$";

    public final String value;

    /**
     * Validates given trackingNumber.
     *
     * @throws IllegalValueException if given trackingNumber string is invalid.
     */
    public TrackingNumber(String trackingNumber) throws IllegalValueException {
        requireNonNull(trackingNumber);
        String trimmedTrackingNumber = trackingNumber.trim();
        if (!isValidTrackingNumber(trimmedTrackingNumber)) {
            throw new IllegalValueException(MESSAGE_TRACKING_NUMBER_CONSTRAINTS);
        }
        this.value = trimmedTrackingNumber;
    }

    /**
     * Returns true if a given string is a valid parcel tracking number.
     */
    public static boolean isValidTrackingNumber(String test) {
        return test.matches(TRACKING_NUMBER_VALIDATION_REGEX);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof TrackingNumber // instanceof handles nulls
                && this.value.equals(((TrackingNumber) other).value)); // state check
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    public StorageManager(AddressBookStorage addressBookStorage, UserPrefsStorage userPrefsStorage) {
        super();
        this.addressBookStorage = addressBookStorage;
        this.userPrefsStorage = userPrefsStorage;

        Optional<ReadOnlyAddressBook> addressBookOptional;
        try {
            addressBookOptional = addressBookStorage.readAddressBook();
            if (addressBookOptional.isPresent()) {
                backupAddressBook(addressBookOptional.get());
                logger.info("AddressBook present, back up success");
            } else {
                logger.warning("AddressBook not present, backup not possible");
            }
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format. "
                    + "Backup of addressbook not available for this instance.");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file. "
                    + "Backup of addressbook not available for this instance.");
        }
    }
```
###### \java\seedu\address\storage\StorageManager.java
``` java
    public void backupAddressBook(ReadOnlyAddressBook addressBook) throws IOException {
        saveAddressBook(addressBook, getBackupStorageFilePath());
    }

    /**
     * @return filepath to AddressBook backup file.
     */
    public String getBackupStorageFilePath() {
        return addressBookStorage.getAddressBookFilePath() + "-backup.xml";
    }
```
