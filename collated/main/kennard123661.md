# kennard123661
###### \java\seedu\address\logic\commands\ImportCommand.java
``` java
/**
 * Imports the parcels and tags stored in {@code parcels} into the current instance of Ark.
 */
public class ImportCommand extends UndoableCommand {

    public static final String COMMAND_WORD = "import";

    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Adds the data of a storage file stored in "
            + "data/import/ directory into Ark.\n"
            + "Parameters: FILE (Must be a valid addressbook file stored in xml format) i.e.\n"
            + "Example: " + COMMAND_WORD + " ark_storage";

    public static final String MESSAGE_SUCCESS_SUMMARY = "Summary: %1$d parcels added and %2$d duplicate "
            + "parcels not added.\n";
    public static final String MESSAGE_SUCCESS_BODY = "Parcels added: %3$s\nDuplicate Parcels: %4$s";
    public static final String MESSAGE_SUCCESS = MESSAGE_SUCCESS_SUMMARY + MESSAGE_SUCCESS_BODY;

    public static final String MESSAGE_FAILURE_DUPLICATE_PARCELS = "All parcels in the imported save file will create "
            + "duplicate parcels";

    private final List<ReadOnlyParcel> parcels;

    /**
     * Creates an ImportCommand to add all {@code ReadOnlyParcel}s in {@param parcelList}.
     */
    public ImportCommand(List<ReadOnlyParcel> parcelList) {
        parcels = parcelList;
    }

    /**
     * Adds all unique parcels in {@code parcels} to the {@link UniqueParcelList} of the {@link AddressBook}. Ignores
     * parcels that will create duplicates in the {@link UniqueParcelList}
     *
     * @return {@link CommandResult} created by {@link ImportCommand#executeUndoableCommand()}
     * @throws CommandException if {@code parcels} contain only duplicate parcels.
     */
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        requireNonNull(model);

        List<ReadOnlyParcel> uniqueParcels = new ArrayList<>(); // list of unique parcels added to Ark.
        List<ReadOnlyParcel> duplicateParcels = new ArrayList<>(); // list of duplicate parcels that are not added
        List<ReadOnlyParcel> storedParcels = model.getAddressBook().getParcelList(); // parcels already stored in Ark

        // check if all parcels are duplicates
        if (storedParcels.containsAll(parcels)) {
            throw new CommandException(MESSAGE_FAILURE_DUPLICATE_PARCELS);
        }

        model.addAllParcels(parcels, uniqueParcels, duplicateParcels);

        String addedParcelsString = getImportFormattedParcelListString(uniqueParcels);
        String duplicatedParcelsString = getImportFormattedParcelListString(duplicateParcels);

        return new CommandResult(String.format(MESSAGE_SUCCESS, uniqueParcels.size(), duplicateParcels.size(),
                addedParcelsString, duplicatedParcelsString));
    }

    /**
     * @return formatted list of parcels added/not added for ImportCommand execution result.
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
```
###### \java\seedu\address\logic\Logic.java
``` java
    /**
     * Returns an unmodifiable view of the filtered list of {@link ReadOnlyParcel}s from the {@link Model} that have
     * {@link Status} that is COMPLETED.
     */
    ObservableList<ReadOnlyParcel> getCompletedParcelList();

    /**
     * Returns an unmodifiable view of the filtered list of {@link ReadOnlyParcel}s from the {@link Model} that have
     * {@link Status} that is not COMPLETED.
     */
    ObservableList<ReadOnlyParcel> getUncompletedParcelList();

    /** Sets the active list of {@link Model} at the particular instance
     *
     * @param isCompleted if true, the active list in {@link Model} will be set to the list of {@link ReadOnlyParcel}s
     *                    with {@link Status} that is COMPLETED. Otherwise, it will be set the list of parcels
     *                    with {@link Status} that is not COMPLETED.
     */
    void setActiveList(boolean isCompleted);

    /** Returns an unmodifiable view of the current active list in {@link Model} at the instance it waas called. */
    ObservableList<ReadOnlyParcel> getActiveList();
```
###### \java\seedu\address\logic\Logic.java
``` java

    /** Returns the list of input entered by the user, encapsulated in a {@code ListElementPointer} object */
    ListElementPointer getHistorySnapshot();
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    /**
     * @see Logic#getFilteredParcelList();
     */
    @Override
    public ObservableList<ReadOnlyParcel> getFilteredParcelList() {
        return model.getFilteredParcelList();
    }

    /**
     * @see Logic#getCompletedParcelList()
     */
    @Override
    public ObservableList<ReadOnlyParcel> getCompletedParcelList() {
        return model.getCompletedParcelList();
    }

    /**
     * @see Logic#setActiveList(boolean)
     */
    @Override
    public void setActiveList(boolean isCompleted) {
        model.setActiveList(isCompleted);
    }

    /**
     * @see Logic#getUncompletedParcelList()
     */
    @Override
    public ObservableList<ReadOnlyParcel> getUncompletedParcelList() {
        return model.getUncompletedParcelList();
    }

    /**
     * @see Logic#getActiveList()
     */
    @Override
    public ObservableList<ReadOnlyParcel> getActiveList() {
        return model.getActiveList();
    }
```
###### \java\seedu\address\logic\parser\ImportCommandParser.java
``` java
/**
 * Parses input arguments and creates a new ImportCommand object
 *
 * @throws ParseException if its an illegal value or the file name contains non-alphanumeric or non-underscore
 * characters.
 */
public class ImportCommandParser implements Parser<ImportCommand> {

    public static final String FILE_NAME_VALIDATION_REGEX = "([a-zA-Z0-9_]+)";
    public static final String MESSAGE_FILE_NAME_INVALID = "File name should be an xml file that only contains "
            + "alphanumeric or underscore characters";

    /**
     * Parses the given {@code String} of arguments in the context of the {@link ImportCommand}
     * and returns an {@link ImportCommand} object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format, or the storage file is not able to
     * be found or it is in the wrong data format.
     */
    public ImportCommand parse(String arg) throws ParseException {
        String trimmedArgument = arg.trim();

        if (!isValidFileName(trimmedArgument)) {
            throw new ParseException(MESSAGE_FILE_NAME_INVALID);
        }

        try {
            ReadOnlyAddressBook readOnlyAddressBook = ParserUtil.parseImportFilePath("./data/import/"
                    + trimmedArgument + ".xml");
            return new ImportCommand(readOnlyAddressBook.getParcelList());
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE) + "\nMore Info: "
                            + ive.getMessage());
        }
    }

    /**
     * checks if the file is a valid file name using {@code FILE_NAME_VALIDATION_REGEX}
     */
    public static boolean isValidFileName(String fileName) {
        return fileName.matches(FILE_NAME_VALIDATION_REGEX);
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> trackingNumber} into an {@code Optional<TrackingNumber>} if
     * {@code trackingNumber} is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<TrackingNumber> parseTrackingNumber(Optional<String> trackingNumber)
            throws IllegalValueException {
        requireNonNull(trackingNumber);
        return trackingNumber.isPresent() ? Optional.of(new TrackingNumber(trackingNumber.get())) : Optional.empty();
    }
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses {@code Optional<String>} into an {@code Optional<Status>} if {@code Status} is present.
     * Leading and trailing whitespaces will be trimmed.
     */
    public static Optional<Status> parseStatus(Optional<String> status) throws IllegalValueException {
        requireNonNull(status);
        return status.isPresent() ? Optional.of(Status.getInstance(status.get())) : Optional.empty();
    }
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Adds all unique {@link Parcel}s stored in {@code parcels} to the {@link AddressBook}
     *
     * @param parcels the list of parcels to add into the {@link AddressBook}.
     * @param uniqueParcels the list of unique parcels stored in {@param parcels} that will not create duplicate parcels
     *                      if added into the {@link UniqueParcelList} in the {@link AddressBook}
     * @param duplicateParcels the list of parcels stored in {@param parcels} that will create duplicate parcels in the
     *                         if added into the {@link UniqueParcelList} in the {@link AddressBook}
     */
    void addAllParcels(List<ReadOnlyParcel> parcels, List<ReadOnlyParcel> uniqueParcels, List<ReadOnlyParcel>
            duplicateParcels);
```
###### \java\seedu\address\model\Model.java
``` java
    /**
     * Returns an unmodifiable view of the filtered list of {@link Parcel} from that have {@link Status}
     * that is COMPLETED.
     */
    ObservableList<ReadOnlyParcel> getCompletedParcelList();

    /**
     * Returns an unmodifiable view of the filtered list of {@link Parcel} from that have {@link Status}
     * that is not COMPLETED.
     */
    ObservableList<ReadOnlyParcel> getUncompletedParcelList();

    /**
     * Returns an unmodifiable view of the current active list.
     */
    ObservableList<ReadOnlyParcel> getActiveList();
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public void setActiveList(boolean isCompleted) {
        activeParcels = isCompleted ? completedParcels : uncompletedParcels;
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    @Override
    public synchronized void addAllParcels(List<ReadOnlyParcel> parcels, List<ReadOnlyParcel> uniqueParcels,
                                           List<ReadOnlyParcel> duplicateParcels) {

        for (ReadOnlyParcel parcel : parcels) {
            ReadOnlyParcel parcelToAdd = new Parcel(parcel);
            try {
                addressBook.addParcel(parcelToAdd); // throws duplicate parcel exception if parcel is non-unique
                uniqueParcels.add(parcelToAdd);
            } catch (DuplicateParcelException ive) {
                duplicateParcels.add(parcelToAdd);
            }
        }

        updateFilteredParcelList(PREDICATE_SHOW_ALL_PARCELS);
        indicateAddressBookChanged();
    }
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Returns an unmodifiable view of the list of {@link ReadOnlyParcel} with {@link Status} that is COMPLETED,
     * backed by the internal list of {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyParcel> getCompletedParcelList() {
        return FXCollections.unmodifiableObservableList(completedParcels);
    }

    /**
     * Returns an unmodifiable view of the list of {@link ReadOnlyParcel} with {@link Status} that is not COMPLETED,
     * backed by the internal list of {@code addressBook}
     */
    @Override
    public ObservableList<ReadOnlyParcel> getUncompletedParcelList() {
        return FXCollections.unmodifiableObservableList(uncompletedParcels);
    }

    /**
     * Returns an unmodifiable view of the list of {@link ReadOnlyParcel} in the {@code activeParcels}
     */
    @Override
    public ObservableList<ReadOnlyParcel> getActiveList() {
        return FXCollections.unmodifiableObservableList(activeParcels);
    }
```
###### \java\seedu\address\model\parcel\PostalCode.java
``` java
/**
 * Represents a possible {@link Address} postal code in the address book.
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
                || (other instanceof PostalCode // instanceof handles null
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
 * Status represents the delivery status of a parcel.
 * It can only be one of these values: PENDING, DELIVERING, COMPLETED and OVERDUE.
 * Guarantees: immutable
 *
 * {@code Status.PENDING} means that the {@link Parcel} is pending delivery.
 * {@code Status.DELIVERING} means that the {@link Parcel} is being delivered.
 * {@code Status.COMPLETED} means that the {@link Parcel} has successfully been delivered.
 * {@code Status.OVERDUE} means that the {@link Parcel} is pending delivery and the present date is past the delivery
 * date.
 */
public enum Status {

    PENDING, DELIVERING, COMPLETED, OVERDUE;

    public static final String MESSAGE_STATUS_CONSTRAINTS =
            "Status can only be PENDING, DELIVERING, COMPLETED or OVERDUE";

    /**
     * Returns a static instance of Status based on the {@code status} parameter.
     *
     * @param status can be case-insensitive {@code String} of PENDING, DELIVERING, COMPLETED or OVERDUE.
     * @return one of four possible {@code Status} values.
     * @throws IllegalValueException if {@param status} is not a possible value of {@code Status}
     */
    public static Status getInstance(String status) throws IllegalValueException {
        String trimmedStatus = status.trim().toUpperCase();

        // checks if trimmedStatus can be any of the possible values of Status.
        for (Status value : Status.values()) {
            if (value.toString().equalsIgnoreCase(trimmedStatus)) {
                return value;
            }
        }

        throw new IllegalValueException(MESSAGE_STATUS_CONSTRAINTS);
    }

    /**
     * Returns an updated Status based on the delivery date of the parcel relative to the today's date.
     *
     * @param status the {@code Status} to be updated.
     * @param date the {@link DeliveryDate} of a parcel.
     * @return an updated {@code Status}. If {@code Status} is {@code PENDING} or {@code OVERDUE},
     *         returns {@code PENDING Status} if {@code date} is not after the present date or {@code OVERDUE Status}
     *         if {@code date} is after the present date. If {@code status} is not {@code OVERDUE} or {@code PENDING},
     *         returns {@code status}
     */
    public static Status getUpdatedInstance(Status status, DeliveryDate date) {
        switch (status.toString()) {
        case "PENDING": // fallthrough
        case "OVERDUE":
            return getUpdatedInstance(date);

        default:
            return status;
        }
    }

    /**
     * Returns an updated Status based on the delivery date of the parcel relative to the today's date.
     *
     * @param date the {@link DeliveryDate} of a parcel.
     * @return an updated {@code Status} based on the {@code date}. returns {@code PENDING Status} if {@code date} is
     * not after the present date and returns {@code OVERDUE Status} if {@code date} is after the present date.
     */
    private static Status getUpdatedInstance(DeliveryDate date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        LocalDate delivery = LocalDate.parse(date.toString(), formatter);
        LocalDate present = LocalDate.now();

        return present.isAfter(delivery) ? Status.OVERDUE : Status.PENDING;
    }
}
```
###### \java\seedu\address\model\parcel\TrackingNumber.java
``` java
/**
 * Represents the tracking number of a parcel.
 * Presently compatible with only SingPost tracking numbers.
 */
public class TrackingNumber {

    public static final String MESSAGE_TRACKING_NUMBER_CONSTRAINTS =
            "Parcel tracking number should start with 'RR', followed by 9 digits, and end with 'SG'";
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

    public int compareTo(TrackingNumber other) {
        return this.toString().compareTo(other.toString());
    }

}
```
###### \java\seedu\address\model\tag\Tag.java
``` java
/**
 * Represents a Tag in the address book.
 * It can only be one of these values: FLAMMABLE, FROZEN, HEAVY or FRAGILE
 * Guarantees: immutable;
 *
 * {@code Tag.FLAMMABLE} means that the {@link Parcel} contents are highly flammable.
 * {@code Tag.FROZEN} means that the {@link Parcel} contents are temperature sensitive and need to be kept cold.
 * {@code Tag.HEAVY} means that the {@link Parcel} contents are heavy and require additional manpower to deliver.
 * {@code Tag.FRAGILE} means that the {@link Parcel} contents are fragile and require additional care.
 */
public enum Tag {

    FLAMMABLE, FROZEN, HEAVY, FRAGILE;

    public static final String MESSAGE_TAG_CONSTRAINTS = "Tags can only be FLAMMABLE, FROZEN, HEAVY or FRAGILE";

    /**
     * Validates given tag.
     *
     * @param tag can be insensitive {@code String} of possible values of Tag.
     * @throws IllegalValueException if the given tag tag string is invalid.
     */
    public static Tag getInstance(String tag) throws IllegalValueException {
        requireNonNull(tag);
        String trimmedTag = tag.trim();

        for (Tag value : Tag.values()) {
            if (value.toString().equalsIgnoreCase(trimmedTag)) {
                return value;
            }
        }

        throw new IllegalValueException(MESSAGE_TAG_CONSTRAINTS);
    }

    /**
     * Format tags string as a text for viewing.
     */
    public String getFormattedString() {
        return '[' + this.toString() + ']';
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
                logger.info("AddressBook present, back up success!");
            } else {
                logger.warning("AddressBook not present, backup not possible.");
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
###### \java\seedu\address\ui\BrowserPanel.java
``` java
    public static String getMapQueryStringFromPostalString(String postalCode) {
        int firstDigitIndex = 1;
        int lastDigitIndex = 7;

        return "Singapore+" + postalCode.substring(firstDigitIndex, lastDigitIndex);
    }
}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Initialize the {@link MainWindow#splitPanePlaceholder} to start in list mode.
     */
    public void initSplitPanePlaceholder() {
        splitPanePlaceholder.setDividerPositions(0.0);
    }
```
###### \java\seedu\address\ui\MainWindow.java
``` java

    /**
     * Sets the active list of the model based on the current selected tab index in the Ui of Ark.
     */
    @FXML @Subscribe
    private void handleTabEvent(JumpToTabRequestEvent event) {
        logic.setActiveList(event.targetIndex == INDEX_SECOND_TAB.getZeroBased());
        logger.info("Active list now set to " + (event.targetIndex == INDEX_SECOND_TAB.getZeroBased()
                ? "completed parcels list." : "uncompleted parcels list."));
    }
```
###### \java\seedu\address\ui\ParcelCard.java
``` java
    @FXML
    private Label trackingNumber;
    @FXML
    private Tooltip trackingNumberTooltip;

    @FXML
    private Label name;
    @FXML
    private Tooltip nameTooltip;

    @FXML
    private Label id;
    @FXML
    private Tooltip idTooltip;

    @FXML
    private Label phone;
    @FXML
    private Tooltip phoneTooltip;

    @FXML
    private Label address;
    @FXML
    private Tooltip addressTooltip;

    @FXML
    private Label email;
    @FXML
    private Tooltip emailTooltip;

    @FXML
    private Label deliveryDate;
    @FXML
    private Tooltip deliveryDateTooltip;

    @FXML
    private Label status;
    @FXML
    private Tooltip statusTooltip;
```
###### \java\seedu\address\ui\ParcelCard.java
``` java
    /**
     * Binds the individual UI elements to observe their respective {@code Parcel} properties
     * so that they will be notified of any changes. Tooltips will also be assigned to their respective {@code Label}
     */
    private void bindListeners(ReadOnlyParcel parcel) {
        trackingNumber.textProperty().bind(Bindings.convert(parcel.trackingNumberProperty()));
        trackingNumberTooltip.textProperty().bind(Bindings.convert(parcel.trackingNumberProperty()));

        name.textProperty().bind(Bindings.convert(parcel.nameProperty()));
        nameTooltip.textProperty().bind(Bindings.convert(parcel.nameProperty()));

        phone.textProperty().bind(Bindings.convert(parcel.phoneProperty()));
        phoneTooltip.textProperty().bind(Bindings.convert(parcel.phoneProperty()));

        address.textProperty().bind(Bindings.convert(parcel.addressProperty()));
        addressTooltip.textProperty().bind(Bindings.convert(parcel.addressProperty()));

        email.textProperty().bind(Bindings.convert(parcel.emailProperty()));
        emailTooltip.textProperty().bind(Bindings.convert(parcel.emailProperty()));

        deliveryDate.textProperty().bind(Bindings.convert(parcel.deliveryDateProperty()));
        deliveryDateTooltip.textProperty().bind(Bindings.convert(parcel.deliveryDateProperty()));

        status.textProperty().bind(Bindings.convert(parcel.statusProperty()));
        statusTooltip.textProperty().bind(Bindings.convert(parcel.statusProperty()));
        setColorForStatus();

        parcel.tagProperty().addListener((observable, oldValue, newValue) -> {
            tags.getChildren().clear();
            initTags(parcel);
        });
    }

```
###### \java\seedu\address\ui\ParcelCard.java
``` java
    /**
     * Sets color for the status {@code Label}s based on the {@link ParcelCard#status} text value.
     */
    private void setColorForStatus() {
        switch (status.textProperty().get()) {
        case "PENDING":
            status.setStyle("-fx-background-color: " + "#d68411");
            break;

        case "DELIVERING":
            status.setStyle("-fx-background-color: " + "#ffc200");
            break;

        case "OVERDUE":
            status.setStyle("-fx-background-color: " + "red");
            break;

        case "COMPLETED":
        default: // fall through
            status.setStyle("-fx-background-color: " + "#00bf00");
            break;

        }
    }
```
###### \java\seedu\address\ui\ParcelCard.java
``` java
    /**
     * Assign colour to Tag {@code Label} based on {@param tagValue}
     */
    private static String setColorForTag(String tagValue) {
        switch (tagValue) {
        case "FLAMMABLE":
            return "#d6130a"; // Chrysler Radiant Fire Red

        case "FROZEN":
            return "#1db1b8"; // light blue

        case "HEAVY":
            return "#8b8d7a"; // Stone Gray

        case "FRAGILE": //fallthrough
        default:
            return "#bf9900"; // gold yellow

        }
    }
```
###### \java\seedu\address\ui\ParcelListPanel.java
``` java
    private void setConnections(ObservableList<ReadOnlyParcel> uncompletedParcels,
                                ObservableList<ReadOnlyParcel> completedParcels) {
        ObservableList<ParcelCard> mappedList = EasyBind.map(
                uncompletedParcels, (parcel) -> new ParcelCard(parcel,
                        uncompletedParcels.indexOf(parcel) + 1));
        allUncompletedParcelListView.setItems(mappedList);
        allUncompletedParcelListView.setCellFactory(listView -> new ParcelListViewCell());

        mappedList = EasyBind.map(completedParcels, (parcel) -> new ParcelCard(parcel,
                        completedParcels.indexOf(parcel) + 1));
        allCompletedParcelListView.setItems(mappedList);
        allCompletedParcelListView.setCellFactory(listView -> new ParcelListViewCell());

        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        allUncompletedParcelListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in parcel list panel changed to : '" + newValue + "'");
                        raise(new ParcelPanelSelectionChangedEvent(newValue));
                    }
                });

        allCompletedParcelListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (newValue != null) {
                        logger.fine("Selection in parcel list panel changed to : '" + newValue + "'");
                        raise(new ParcelPanelSelectionChangedEvent(newValue));
                    }
                });

        tabPanePlaceholder.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    logger.fine("Tab in parcel list panel changed to : '" + newValue.getText() + "'");
                    if (newValue.getText().equals("All Parcels")) {
                        handleTabSelection(INDEX_FIRST_TAB);
                    } else {
                        handleTabSelection(INDEX_SECOND_TAB);
                    }
                });
    }

    /**
     * Scrolls to the {@code ParcelCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            if (tabPanePlaceholder.getSelectionModel().getSelectedIndex() == 0) {
                allUncompletedParcelListView.scrollTo(index);
                allUncompletedParcelListView.getSelectionModel().clearAndSelect(index);
            }
        });

        Platform.runLater(() -> {
            if (tabPanePlaceholder.getSelectionModel().getSelectedIndex() == 1) {
                allCompletedParcelListView.scrollTo(index);
                allCompletedParcelListView.getSelectionModel().clearAndSelect(index);
            }
        });
    }
```
###### \java\seedu\address\ui\ParcelListPanel.java
``` java
    public void handleTabSelection(Index selectedIndex) {
        EventsCenter.getInstance().post(new JumpToTabRequestEvent(selectedIndex));
    }
```
###### \resources\view\ParcelListCard.fxml
``` fxml
        <Label fx:id="id" styleClass="cell_big_label" text="\$id">
          <minWidth>
            <!-- Ensures that the label text is never truncated -->
            <Region fx:constant="USE_PREF_SIZE" />
          </minWidth>
               <tooltip>
                  <Tooltip fx:id="idTooltip" text="id tooltip" />
               </tooltip>
        </Label>
        <Label fx:id="name" styleClass="cell_big_label" text="\$first" GridPane.columnIndex="1">
               <tooltip>
                  <Tooltip fx:id="nameTooltip" text="name tooltip" />
               </tooltip></Label>
        <Label fx:id="trackingNumber" styleClass="cell_big_label" text="\$trackingNumber" GridPane.columnIndex="2">
               <tooltip>
                  <Tooltip fx:id="trackingNumberTooltip" text="tracking number tooltip" />
               </tooltip></Label>
        <Label fx:id="phone" styleClass="cell_big_label" text="\$phone" GridPane.columnIndex="3">
               <tooltip>
                  <Tooltip fx:id="phoneTooltip" text="phone tooltip" />
               </tooltip></Label>
        <Label fx:id="address" styleClass="cell_big_label" text="\$address" GridPane.columnIndex="4">
               <tooltip>
                  <Tooltip fx:id="addressTooltip" text="address tooltip" />
               </tooltip></Label>
        <Label fx:id="email" styleClass="cell_big_label" text="\$email" GridPane.columnIndex="5">
               <tooltip>
                  <Tooltip fx:id="emailTooltip" text="email tooltip" />
               </tooltip></Label>
        <Label fx:id="deliveryDate" styleClass="cell_big_label" text="\$deliveryDate" GridPane.columnIndex="6">
               <tooltip>
                  <Tooltip fx:id="deliveryDateTooltip" text="delivery date tooltip" />
               </tooltip></Label>
        <Label fx:id="status" styleClass="cell_small_label" text="\$status" textAlignment="CENTER" GridPane.columnIndex="7">
               <tooltip>
                  <Tooltip fx:id="statusTooltip" text="status tooltip" />
               </tooltip></Label>
          <FlowPane fx:id="tags" alignment="CENTER_LEFT" prefHeight="30.0" prefWidth="249.0" GridPane.columnIndex="8" />
         </children>
      </GridPane>
```
