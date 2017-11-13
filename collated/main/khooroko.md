# khooroko
###### \java\seedu\address\commons\events\ui\ChangeThemeRequestEvent.java
``` java
/**
 * Indicates a request to change theme
 */
public class ChangeThemeRequestEvent extends BaseEvent {

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
```
###### \java\seedu\address\commons\events\ui\JumpToNearbyListRequestEvent.java
``` java
/**
 * Indicates a request to jump to the list of nearby persons
 */
public class JumpToNearbyListRequestEvent extends BaseEvent {

    public final int targetIndex;

    public JumpToNearbyListRequestEvent(Index targetIndex) {
        this.targetIndex = targetIndex.getZeroBased();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
```
###### \java\seedu\address\commons\events\ui\NearbyPersonNotInCurrentListEvent.java
``` java
/**
 * Represents a selection change in the Nearby Person List Panel to a person that is not in the current Person List
 * Panel.
 */
public class NearbyPersonNotInCurrentListEvent extends BaseEvent {

    private final PersonCard newSelection;

    public NearbyPersonNotInCurrentListEvent(PersonCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\address\commons\events\ui\NearbyPersonPanelSelectionChangedEvent.java
``` java
/**
 * Represents a selection change in the Nearby Person List Panel
 */
public class NearbyPersonPanelSelectionChangedEvent extends BaseEvent {

    private final PersonCard newSelection;

    public NearbyPersonPanelSelectionChangedEvent(PersonCard newSelection) {
        this.newSelection = newSelection;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

    public PersonCard getNewSelection() {
        return newSelection;
    }
}
```
###### \java\seedu\address\logic\commands\Command.java
``` java
    /**
     * Retrieves a person based on the input {@code Index} for the command to be applied on.
     * @param index must be a positive integer.
     * @return the person in the specified index in the last shown list.
     */
    public ReadOnlyPerson selectPersonForCommand(Index index) throws CommandException {
        List<ReadOnlyPerson> lastShownList = ListObserver.getCurrentFilteredList();
        if (index.getZeroBased() >= lastShownList.size()) {
            throw new CommandException(Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
        }
        return lastShownList.get(index.getZeroBased());
    }

    /**
     * Retrieves the current person for the command to be applied on.
     */
    public ReadOnlyPerson selectPersonForCommand() throws CommandException {
        if (ListObserver.getSelectedPerson() == null) {
            throw new CommandException(Messages.MESSAGE_NO_PERSON_SELECTED);
        }
        return ListObserver.getSelectedPerson();
    }

    /**
     * Selects the specified person if he/she exists in the list. If he/she does not, a deselection is done.
     * @param person the person to be selected.
     */
    public void reselectPerson(ReadOnlyPerson person) {
        if (ListObserver.getCurrentFilteredList().contains(person)) {
            EventsCenter.getInstance().post(new
                    JumpToListRequestEvent(ListObserver.getIndexOfPersonInCurrentList(person)));
        } else {
            model.deselectPerson();
        }
    }
}
```
###### \java\seedu\address\logic\commands\DeleteCommand.java
``` java
    @Override
    public CommandResult executeUndoableCommand() throws CommandException {
        boolean requireJump = personToDelete.equals(model.getSelectedPerson())
                && ListObserver.getIndexOfPersonInCurrentList(personToDelete).getOneBased()
                != ListObserver.getCurrentFilteredList().size();
        Index index = ListObserver.getIndexOfPersonInCurrentList(personToDelete);
        try {
            model.deletePerson(personToDelete);
        } catch (PersonNotFoundException pnfe) {
            assert false : "The target person cannot be missing";
        }
        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);
        if (requireJump) {
            EventsCenter.getInstance().post(new JumpToListRequestEvent(index));
        } else {
            model.deselectPerson();
        }

        return new CommandResult(String.format(MESSAGE_DELETE_PERSON_SUCCESS, personToDelete.getName()));
    }

```
###### \java\seedu\address\logic\commands\NearbyCommand.java
``` java
/**
 * Selects a person identified using last displayed index from currently selected person's nearby list.
 */
public class NearbyCommand extends Command {

    public static final String COMMAND_WORD = "nearby";
    public static final String COMMAND_WORD_ALIAS = "n";

    public static final String MESSAGE_USAGE = COMMAND_WORD
            + ": Selects the person identified by the index number used in the currently selected person's "
            + "nearby listing. If no index is provided, the next person in the nearby list is selected.\n"
            + "Parameters: INDEX (must be a positive integer)\n"
            + "Example: " + COMMAND_WORD + " 1";

    public static final String MESSAGE_NEARBY_PERSON_SUCCESS = "Selected person in same area: %1$s";
    public static final String MESSAGE_INVALID_NEARBY_INDEX = "The index provided is invalid. There are only %d "
            + "contacts in this area";

    private final Index targetIndex;

    public NearbyCommand(Index targetIndex) {
        this.targetIndex = targetIndex;
    }

    @Override
    public CommandResult execute() throws CommandException {

        List<ReadOnlyPerson> nearbyList = model.getNearbyPersons();

        if (ListObserver.getSelectedPerson() == null) {
            throw new CommandException(Messages.MESSAGE_NO_PERSON_SELECTED);
        }

        if (targetIndex.getZeroBased() >= nearbyList.size()) {
            throw new CommandException(String.format(MESSAGE_INVALID_NEARBY_INDEX, nearbyList.size()));
        }

        model.updateSelectedPerson(nearbyList.get(targetIndex.getZeroBased()));
        EventsCenter.getInstance().post(new JumpToNearbyListRequestEvent(targetIndex));

        String currentList = ListObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(MESSAGE_NEARBY_PERSON_SUCCESS, targetIndex.getOneBased()));

    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof NearbyCommand // instanceof handles nulls
                && this.targetIndex.equals(((NearbyCommand) other).targetIndex)); // state check
    }
}
```
###### \java\seedu\address\logic\commands\SortCommand.java
``` java
/**
 * Sorts the address book by the input argument (i.e. "name" or "debt").
 */
public class SortCommand extends Command {

    public static final String COMMAND_WORD = "sort";
    public static final String DEFAULT_ORDERING = "name";
    public static final String MESSAGE_USAGE = COMMAND_WORD + ": Sorts the addressbook by specified ordering in "
            + "intuitive order. If no ordering is specified, the addressbook is sorted by name.\n"
            + "Parameters: ORDERING (i.e. \"name\", \"debt\", \"deadline\" or \"cluster\")\n"
            + "Example: " + COMMAND_WORD + " debt";
    public static final String MESSAGE_SUCCESS = "List has been sorted by %1$s!";

    private final String order;

    public SortCommand(String order) {
        //validity of order to sort is checked in {@code SortCommandParser}
        if (order.equals("")) {
            order = DEFAULT_ORDERING;
        }
        this.order = order;
    }

    @Override
    public CommandResult execute() throws CommandException {
        requireNonNull(model);
        model.deselectPerson();
        try {
            model.sortBy(order);
        } catch (IllegalArgumentException ive) {
            throw new CommandException(ive.getMessage());
        }

        ListObserver.updateCurrentFilteredList(PREDICATE_SHOW_ALL_PERSONS);

        String currentList = ListObserver.getCurrentListName();

        return new CommandResult(currentList + String.format(MESSAGE_SUCCESS, order));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof SortCommand)) {
            return false;
        }

        // state check
        SortCommand s = (SortCommand) other;
        return order.equals(s.order);
    }
}
```
###### \java\seedu\address\logic\commands\ThemeCommand.java
``` java
/**
 * Changes theme.
 */
public class ThemeCommand extends Command {
    public static final String COMMAND_WORD = "theme";
    public static final String MESSAGE_SUCCESS = "Theme has been successfully changed!";

    @Override
    public CommandResult execute() {
        EventsCenter.getInstance().post(new ChangeThemeRequestEvent());
        return new CommandResult(MESSAGE_SUCCESS);
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof ThemeCommand); // all ThemeCommands are the same
    }
}
```
###### \java\seedu\address\logic\LogicManager.java
``` java
    /**
     * Updates the selected person.
     * @param person the person that has been selected.
     */
    @Override
    public void updateSelectedPerson(ReadOnlyPerson person) {
        model.updateSelectedPerson(person);
    }

    /**
     * Resets the filteredPersonList to be a list of all persons.
     */
    @Override
    public void resetFilteredPersonList() {
        model.updateFilteredPersonList(Model.PREDICATE_SHOW_ALL_PERSONS);
    }

    @Override
    public ObservableList<ReadOnlyPerson> getAllPersons() {
        return model.getAllPersons();
    }

```
###### \java\seedu\address\logic\parser\NearbyCommandParser.java
``` java
/**
 * Parses input arguments and creates a new {@code NearbyCommand} object
 */
public class NearbyCommandParser implements Parser<NearbyCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the NearbyCommand
     * and returns an NearbyCommand object for execution.
     * @throws ParseException if the user input does not conform the expected format
     */
    public NearbyCommand parse(String args) throws ParseException {
        try {
            Index index = ParserUtil.parseIndex(args);
            return new NearbyCommand(index);
        } catch (IllegalValueException ive) {
            throw new ParseException(
                    String.format(MESSAGE_INVALID_COMMAND_FORMAT, NearbyCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtil.java
``` java
    /**
     * Parses a {@code Optional<String> postalCode} into an {@code Optional<PostalCode>} if {@code postalCode}
     * is present.
     * See header comment of this class regarding the use of {@code Optional} parameters.
     */
    public static Optional<PostalCode> parsePostalCode(Optional<String> postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        return postalCode.isPresent() ? Optional.of(new PostalCode(postalCode.get())) : Optional.empty();
    }

```
###### \java\seedu\address\logic\parser\SortCommandParser.java
``` java
/**
 * Parses input arguments and creates a new {@code EditCommand} object
 */
public class SortCommandParser implements Parser<SortCommand> {
    /**
    * Parses the given {@code String} of arguments in the context of the EditCommand
    * and returns an EditCommand object for execution.
    * @throws ParseException if the user input does not conform the expected format
    */
    public SortCommand parse(String args) throws ParseException {
        requireNonNull(args);
        String trimmedArgs = args.trim().toLowerCase();
        switch (trimmedArgs) {
        case "":
        case "name":
        case "debt":
        case "cluster":
        case "deadline":
            return new SortCommand(trimmedArgs);
        default:
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT, SortCommand.MESSAGE_USAGE));
        }
    }
}
```
###### \java\seedu\address\model\ModelManager.java
``` java
    /**
     * Obtains and updates the list of persons that share the same cluster as {@param selectedPerson}.
     */
    @Override
    public void updateSelectedPerson(ReadOnlyPerson selectedPerson) {
        this.selectedPerson = selectedPerson;
        nearbyPersons = allPersons.stream().filter(person -> person.isSameCluster(selectedPerson))
                .collect(toCollection(FXCollections::observableArrayList));
    }

    /**
     * Deselects the currently selected person.
     */
    @Override
    public void deselectPerson() {
        this.selectedPerson = null;
        nearbyPersons = null;
        EventsCenter.getInstance().post(new DeselectionEvent());
    }

    /**
     * Retrieves the full list of persons nearby a particular person.
     */
    @Override
    public ObservableList<ReadOnlyPerson> getNearbyPersons() {
        return nearbyPersons;
    }

    /**
     * Retrieves the current selected person.
     */
    @Override
    public ReadOnlyPerson getSelectedPerson() {
        return selectedPerson;
    }

    /**
     * Retrieves the full list of persons in addressbook.
     */
    @Override
    public ObservableList<ReadOnlyPerson> getAllPersons() {
        return allPersons;
    }

    /**
     * Sorts the {@code internal list} in addressbook according to {@param order}
     */
    @Override
    public void sortBy(String order) throws IllegalArgumentException {
        addressBook.sortBy(order);
        indicateAddressBookChanged();
    }

```
###### \java\seedu\address\model\person\Cluster.java
``` java
/**
 * Represents a Person's {@code Cluster} in the address book.
 * Guarantees: immutable; can only be declared with a valid {@code PostalCode}
 */
public class Cluster {

    public final int clusterNumber;
    public final String value;

    /**
     * Validates given cluster. Can only be called with a validated postal code.
     */
    public Cluster(PostalCode postalCode) {
        requireNonNull(postalCode);
        if (!isValidPostalCode(postalCode.toString())) {
            throw new AssertionError(MESSAGE_POSTAL_CODE_CONSTRAINTS);
        }
        String cluster = getCluster(postalCode.toString());
        clusterNumber = Integer.parseInt(cluster.substring(0, 2));
        this.value = cluster.substring(4);
    }

    @Override
    public String toString() {
        return value;
    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof Cluster // instanceof handles nulls
                && this.clusterNumber == ((Cluster) other).clusterNumber
                && this.value.equals(((Cluster) other).value)); // state check
    }

    public int compareTo(Cluster other) {
        return this.clusterNumber - other.clusterNumber;
    }

    @Override
    public int hashCode() {
        return value.hashCode();
    }

}
```
###### \java\seedu\address\model\person\Handphone.java
``` java
/**
 * Represents a Person's handphone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class Handphone extends Phone {

    public Handphone(String phone) throws IllegalValueException {
        super(phone);
    }

}
```
###### \java\seedu\address\model\person\HomePhone.java
``` java
/**
 * Represents a Person's home number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class HomePhone extends Phone {

    public HomePhone (String phone) throws IllegalValueException {
        super(phone);
    }

}
```
###### \java\seedu\address\model\person\OfficePhone.java
``` java
/**
 * Represents a Person's office phone number in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPhone(String)}
 */
public class OfficePhone extends Phone {

    public OfficePhone (String phone) throws IllegalValueException {
        super(phone);
    }

}
```
###### \java\seedu\address\model\person\Person.java
``` java
    /**
     * Sets postal code of a person to the given PostalCode.
     * @param postalCode must not be null.
     */
    public void setPostalCode(PostalCode postalCode) {
        this.postalCode.set(requireNonNull(postalCode));
    }

    @Override
    public ObjectProperty<PostalCode> postalCodeProperty() {
        return postalCode;
    }

    @Override
    public PostalCode getPostalCode() {
        return postalCode.get();
    }

    /**
     * Sets cluster of a person to the given Cluster.
     * @param cluster must not be null.
     */
    public void setCluster(Cluster cluster) {
        this.cluster.set(requireNonNull(cluster));
    }

    @Override
    public ObjectProperty<Cluster> clusterProperty() {
        return cluster;
    }

    @Override
    public Cluster getCluster() {
        return cluster.get();
    }

```
###### \java\seedu\address\model\person\PostalCode.java
``` java
/**
 * Represents a Person's postal code in the address book.
 * Guarantees: immutable; is valid as declared in {@link #isValidPostalCode(String)}
 */
public class PostalCode {

    public static final String MESSAGE_POSTAL_CODE_CONSTRAINTS =
            "Postal code must be exactly 6 digits long";

    public static final String POSTAL_CODE_VALIDATION_REGEX = "\\d{6}";

    public final String value;

    /**
     * Validates given postal code.
     *
     * @throws IllegalValueException if given postal code string is invalid.
     */
    public PostalCode(String postalCode) throws IllegalValueException {
        requireNonNull(postalCode);
        String trimmedPostalCode = postalCode.trim();
        if (!isValidPostalCode(trimmedPostalCode)) {
            throw new IllegalValueException(MESSAGE_POSTAL_CODE_CONSTRAINTS);
        }
        this.value = trimmedPostalCode;
    }

    /**
     * Returns true if a given string is a valid person postal code.
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
###### \java\seedu\address\model\person\UniquePersonList.java
``` java
    /**
     * Sorts the unique person list by the specified order.
     * @param order to sort the list by.
     */
    public void sortBy(String order) throws IllegalArgumentException {
        switch (order) {
        case "name":
            internalList.sort((Person p1, Person p2) -> p1.getName().compareTo(p2.getName()));
            break;
        case "debt":
            internalList.sort((Person p1, Person p2) -> p2.getDebt().compareTo(p1.getDebt()));
            break;
        case "cluster":
            internalList.sort((Person p1, Person p2) -> p1.getCluster().compareTo(p2.getCluster()));
            break;
        case "deadline":
            internalList.sort((Person p1, Person p2) -> p1.getDeadline().compareTo(p2.getDeadline()));
            internalList.sort((Person p1, Person p2) -> Boolean.compare(p1.getDebt().toNumber() == 0,
                    p2.getDebt().toNumber() == 0));
            break;
        default:
            throw new IllegalArgumentException("Invalid sort ordering");
        }
    }

```
###### \java\seedu\address\model\util\ClusterUtil.java
``` java
/**
 * Contains utility methods for retrieving (@code Cluster) from (@code PostalCode).
 */
public class ClusterUtil {

    public static final String CLUSTER_POSTAL_DISTRICT_01 = "01. Raffles Place, Cecil, Marina, People's Park";
    public static final String CLUSTER_POSTAL_DISTRICT_02 = "02. Anson, Tanjong Pagar";
    public static final String CLUSTER_POSTAL_DISTRICT_03 = "03. Queenstown, Tiong Bahru";
    public static final String CLUSTER_POSTAL_DISTRICT_04 = "04. Telok Blangah, Harbourfront";
    public static final String CLUSTER_POSTAL_DISTRICT_05 = "05. Pasir Panjang, Hong Leong Garden, Clementi New Town";
    public static final String CLUSTER_POSTAL_DISTRICT_06 = "06. High Street, Beach Road";
    public static final String CLUSTER_POSTAL_DISTRICT_07 = "07. Middle Road, Golden Mile";
    public static final String CLUSTER_POSTAL_DISTRICT_08 = "08. Little India";
    public static final String CLUSTER_POSTAL_DISTRICT_09 = "09. Orchard, Cairnhill, River Valley";
    public static final String CLUSTER_POSTAL_DISTRICT_10 = "10. Ardmore, Bukit Timah, Holland Road, Tanglin";
    public static final String CLUSTER_POSTAL_DISTRICT_11 = "11. Watten Estate, Novena, Thomson";
    public static final String CLUSTER_POSTAL_DISTRICT_12 = "12. Balestier, Toa Payoh, Serangoon";
    public static final String CLUSTER_POSTAL_DISTRICT_13 = "13. Macpherson, Braddell";
    public static final String CLUSTER_POSTAL_DISTRICT_14 = "14. Geylang, Eunos";
    public static final String CLUSTER_POSTAL_DISTRICT_15 = "15. Katong, Joo Chiat, Amber Road";
    public static final String CLUSTER_POSTAL_DISTRICT_16 = "16. Bedok, Upper East Coast, Eastwood, Kew Drive";
    public static final String CLUSTER_POSTAL_DISTRICT_17 = "17. Loyang, Changi";
    public static final String CLUSTER_POSTAL_DISTRICT_18 = "18. Tampines, Pasir Ris";
    public static final String CLUSTER_POSTAL_DISTRICT_19 = "19. Serangoon Garden, Hougang, Punggol";
    public static final String CLUSTER_POSTAL_DISTRICT_20 = "20. Bishan, Ang Mo Kio";
    public static final String CLUSTER_POSTAL_DISTRICT_21 = "21. Upper Bukit Timah, Clementi Park, Ulu Pandan";
    public static final String CLUSTER_POSTAL_DISTRICT_22 = "22. Jurong";
    public static final String CLUSTER_POSTAL_DISTRICT_23 = "23. Hillview, Dairy Farm, Bukit Panjang, Choa Chu Kang";
    public static final String CLUSTER_POSTAL_DISTRICT_24 = "24. Lim Chu Kang, Tengah";
    public static final String CLUSTER_POSTAL_DISTRICT_25 = "25. Kranji, Woodgrove";
    public static final String CLUSTER_POSTAL_DISTRICT_26 = "26. Upper Thomson, Springleaf";
    public static final String CLUSTER_POSTAL_DISTRICT_27 = "27. Yishun, Sembawang";
    public static final String CLUSTER_POSTAL_DISTRICT_28 = "28. Seletar";
    public static final String CLUSTER_POSTAL_DISTRICT_UNKNOWN = "99. Unknown";

    public static String getCluster(String postalCode) {
        requireNonNull(postalCode);
        int postalSector = Integer.parseInt(postalCode.substring(0, 2));
        switch (postalSector) {
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
            return CLUSTER_POSTAL_DISTRICT_01;
        case 7:
        case 8:
            return CLUSTER_POSTAL_DISTRICT_02;
        case 14:
        case 15:
        case 16:
            return CLUSTER_POSTAL_DISTRICT_03;
        case 9:
        case 10:
            return CLUSTER_POSTAL_DISTRICT_04;
        case 11:
        case 12:
        case 13:
            return CLUSTER_POSTAL_DISTRICT_05;
        case 17:
            return CLUSTER_POSTAL_DISTRICT_06;
        case 18:
        case 19:
            return CLUSTER_POSTAL_DISTRICT_07;
        case 20:
        case 21:
            return CLUSTER_POSTAL_DISTRICT_08;
        case 22:
        case 23:
            return CLUSTER_POSTAL_DISTRICT_09;
        case 24:
        case 25:
        case 26:
        case 27:
            return CLUSTER_POSTAL_DISTRICT_10;
        case 28:
        case 29:
        case 30:
            return CLUSTER_POSTAL_DISTRICT_11;
        case 31:
        case 32:
        case 33:
            return CLUSTER_POSTAL_DISTRICT_12;
        case 34:
        case 35:
        case 36:
        case 37:
            return CLUSTER_POSTAL_DISTRICT_13;
        case 38:
        case 39:
        case 40:
        case 41:
            return CLUSTER_POSTAL_DISTRICT_14;
        case 42:
        case 43:
        case 44:
        case 45:
            return CLUSTER_POSTAL_DISTRICT_15;
        case 46:
        case 47:
        case 48:
            return CLUSTER_POSTAL_DISTRICT_16;
        case 49:
        case 50:
        case 81:
            return CLUSTER_POSTAL_DISTRICT_17;
        case 51:
        case 52:
            return CLUSTER_POSTAL_DISTRICT_18;
        case 53:
        case 54:
        case 55:
        case 82:
            return CLUSTER_POSTAL_DISTRICT_19;
        case 56:
        case 57:
            return CLUSTER_POSTAL_DISTRICT_20;
        case 58:
        case 59:
            return CLUSTER_POSTAL_DISTRICT_21;
        case 60:
        case 61:
        case 62:
        case 63:
        case 64:
            return CLUSTER_POSTAL_DISTRICT_22;
        case 65:
        case 66:
        case 67:
        case 68:
            return CLUSTER_POSTAL_DISTRICT_23;
        case 69:
        case 70:
        case 71:
            return CLUSTER_POSTAL_DISTRICT_24;
        case 72:
        case 73:
            return CLUSTER_POSTAL_DISTRICT_25;
        case 77:
        case 78:
            return CLUSTER_POSTAL_DISTRICT_26;
        case 75:
        case 76:
            return CLUSTER_POSTAL_DISTRICT_27;
        case 79:
        case 80:
            return CLUSTER_POSTAL_DISTRICT_28;
        default:
            return CLUSTER_POSTAL_DISTRICT_UNKNOWN;
        }
    }

}
```
###### \java\seedu\address\storage\XmlAddressBookStorage.java
``` java
    /**
     * Creates a backup of the current data, if available.
     */
    @Override
    public void backupAddressBook() {
        try {
            Optional<ReadOnlyAddressBook> addressBookOptional;
            ReadOnlyAddressBook initialData;
            addressBookOptional = readAddressBook();
            if (!addressBookOptional.isPresent()) {
                logger.info("No backup will be made as data file does not exist");
            } else {
                initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
                saveAddressBook(initialData, getBackupAddressBookFilePath());
            }
        } catch (DataConversionException e) {
            logger.info("No backup will be made due to data file being in incorrect format");
        } catch (IOException e) {
            logger.info("No backup will be made due to problems while reading from file");
        }
    }

    /**
     * Returns address book data, if available and readable, or backup address book data if it is not.
     * If both are unavailable, a sample address book is returned. If backup address book is in the wrong format
     * or is unreadable, an empty address book is returned.
     */
    @Override
    public ReadOnlyAddressBook getBestAvailableAddressBook() {
        ReadOnlyAddressBook initialData = new AddressBook();
        boolean isDataFileOkay = false;
        Optional<ReadOnlyAddressBook> addressBookOptional;

        try {
            addressBookOptional = readAddressBook();
            if (addressBookOptional.isPresent()) {
                isDataFileOkay = true;
            } else {
                logger.info("Data file not found");
            }
            initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
        } catch (DataConversionException e) {
            logger.warning("Data file not in the correct format");
        } catch (IOException e) {
            logger.warning("Problem while reading from the file");
        }

        if (!isDataFileOkay) {
            try {
                addressBookOptional = readBackupAddressBook();
                if (addressBookOptional.isPresent()) {
                    logger.info("Backup data file found. Will be starting with a backup");
                } else {
                    logger.info("Backup data file not found. Will be starting with a sample AddressBook");
                }
                initialData = addressBookOptional.orElseGet(SampleDataUtil::getSampleAddressBook);
            } catch (DataConversionException e) {
                logger.warning("Backup data file not in the correct format. Will be starting with an empty "
                        + "AddressBook");
            } catch (IOException e) {
                logger.warning("Problem while reading from the backup file. Will be starting with an empty "
                        + "AddressBook");
            }
        }
        return initialData;
    }

}
```
###### \java\seedu\address\ui\InfoPanel.java
``` java
/**
 * The Info Panel of the App that displays full information of a {@code Person}.
 */
public class InfoPanel extends UiPart<Region> {

    private static final String FXML = "InfoPanel.fxml";
    private static final String MESSAGE_INFO_ADDRESS_FIELD = "Address: ";
    private static final String MESSAGE_INFO_HANDPHONE_FIELD = "HP: ";
    private static final String MESSAGE_INFO_HOME_PHONE_FIELD = "Home: ";
    private static final String MESSAGE_INFO_OFFICE_PHONE_FIELD = "Office: ";
    private static final String MESSAGE_INFO_EMAIL_FIELD = "Email: ";
    private static final String MESSAGE_INFO_POSTAL_CODE_FIELD = "Postal Code: ";
    private static final String MESSAGE_INFO_CLUSTER_FIELD = "General Location: ";
    private static final String MESSAGE_INFO_DEBT_FIELD = "Current Debt ($): ";
    private static final String MESSAGE_INFO_TOTAL_DEBT_FIELD = "Total Debt ($): ";
    private static final String MESSAGE_INFO_INTEREST_FIELD = "Interest (%): ";
    private static final String MESSAGE_INFO_DATE_BORROW_FIELD = "Date Borrowed: ";
    private static final String MESSAGE_INFO_DEADLINE_FIELD = "Deadline: ";
    private static final String MESSAGE_INFO_DATE_REPAID_FIELD = "Date Repaid: ";
    private static final String MESSAGE_INFO_NEARBY_PERSON_FIELD = "All contacts in this area: ";
    private static final String MESSAGE_INFO_DEBT_REPAYMENT_FIELD = "Debt repayment progress: ";

    private Logic logic;
    private final Logger logger = LogsCenter.getLogger(this.getClass());

    private NearbyPersonListPanel nearbyPersonListPanel;
    private DebtRepaymentProgressBar debtRepaymentProgressBar;
    private DebtorProfilePicture debtorProfilePicture;

    @FXML
    private Pane pane;
    @FXML
    private ScrollPane personInfoPanel;
    @FXML
    private SplitPane splitPane;
    @FXML
    private VBox content;
    @FXML
    private Label name;
    @FXML
    private Text handphoneField;
    @FXML
    private Label handphone;
    @FXML
    private Text homePhoneField;
    @FXML
    private Label homePhone;
    @FXML
    private Text officePhoneField;
    @FXML
    private Label officePhone;
    @FXML
    private Text addressField;
    @FXML
    private Label address;
    @FXML
    private Text postalCodeField;
    @FXML
    private Label postalCode;
    @FXML
    private Text clusterField;
    @FXML
    private Label cluster;
    @FXML
    private Text emailField;
    @FXML
    private Label email;
    @FXML
    private Text debtField;
    @FXML
    private Label debt;
    @FXML
    private Text totalDebtField;
    @FXML
    private Label totalDebt;
    @FXML
    private Text interestField;
    @FXML
    private Label interest;
    @FXML
    private Text dateBorrowField;
    @FXML
    private Label dateBorrow;
    @FXML
    private Text deadlineField;
    @FXML
    private Label deadline;
    @FXML
    private Text dateRepaidField;
    @FXML
    private Label dateRepaid;
    @FXML
    private Text nearbyPersonField;
    @FXML
    private FlowPane tags;
    @FXML
    private StackPane nearbyPersonListPanelPlaceholder;
    @FXML
    private StackPane progressBarPlaceholder;
    @FXML
    private GridPane personInfoTable;
    @FXML
    private AnchorPane profilePicPlaceholder;
    @FXML
    private Text debtRepaymentField;

    public InfoPanel(Logic logic) {
        super(FXML);
        this.logic = logic;
        loadDefaultPage();
        registerAsAnEventHandler(this);
    }

    /**
     * Loads the full info of the person
     * @param person the selected person to display the full info of.
     */
    public void loadPersonInfo(ReadOnlyPerson person) {
        handphoneField.setText(MESSAGE_INFO_HANDPHONE_FIELD);
        homePhoneField.setText(MESSAGE_INFO_HOME_PHONE_FIELD);
        officePhoneField.setText(MESSAGE_INFO_OFFICE_PHONE_FIELD);
        addressField.setText(MESSAGE_INFO_ADDRESS_FIELD);
        emailField.setText(MESSAGE_INFO_EMAIL_FIELD);
        postalCodeField.setText(MESSAGE_INFO_POSTAL_CODE_FIELD);
        clusterField.setText(MESSAGE_INFO_CLUSTER_FIELD);
        debtField.setText(MESSAGE_INFO_DEBT_FIELD);
        totalDebtField.setText(MESSAGE_INFO_TOTAL_DEBT_FIELD);
        interestField.setText(MESSAGE_INFO_INTEREST_FIELD);
        dateBorrowField.setText(MESSAGE_INFO_DATE_BORROW_FIELD);
        deadlineField.setText(MESSAGE_INFO_DEADLINE_FIELD);
        dateRepaidField.setText(MESSAGE_INFO_DATE_REPAID_FIELD);
        nearbyPersonField.setText(MESSAGE_INFO_NEARBY_PERSON_FIELD);
        debtRepaymentField.setText(MESSAGE_INFO_DEBT_REPAYMENT_FIELD);
        bindListeners(person);
        if (splitPane.lookup(".split-pane-divider") != null) {
            splitPane.lookup(".split-pane-divider").setMouseTransparent(true);
        }
    }

    /**
     * Resets the Nearby Person List Panel
     * @param person the selected person to display the nearby contacts of
     */
    public void resetNearbyPersonListPanel(ReadOnlyPerson person) {
        nearbyPersonListPanel = new NearbyPersonListPanel(logic.getAllPersons(), person);
        nearbyPersonListPanelPlaceholder.getChildren().clear();
        nearbyPersonListPanelPlaceholder.getChildren().add(nearbyPersonListPanel.getRoot());
        personInfoPanel.setVisible(true);
        personInfoPanel.setContent(content);
    }

```
###### \java\seedu\address\ui\InfoPanel.java
``` java
    /**
     * Binds the individual UI elements to observe their respective {@code Person} properties
     * so that they will be notified of any changes.
     */
    private void bindListeners(ReadOnlyPerson person) {
        name.textProperty().bind(Bindings.convert(person.nameProperty()));
        handphone.textProperty().bind(Bindings.convert(person.handphoneProperty()));
        homePhone.textProperty().bind(Bindings.convert(person.homePhoneProperty()));
        officePhone.textProperty().bind(Bindings.convert(person.officePhoneProperty()));
        address.textProperty().bind(Bindings.convert(person.addressProperty()));
        postalCode.textProperty().bind(Bindings.convert(person.postalCodeProperty()));
        cluster.textProperty().bind(Bindings.convert(person.clusterProperty()));
        email.textProperty().bind(Bindings.convert(person.emailProperty()));
        debt.textProperty().bind(Bindings.convert(person.debtProperty()));
        totalDebt.textProperty().bind(Bindings.convert(person.totalDebtProperty()));
        interest.textProperty().bind(Bindings.convert(person.interestProperty()));
        dateBorrow.textProperty().bind(Bindings.convert(person.dateBorrowProperty()));
        deadline.textProperty().bind(Bindings.convert(person.deadlineProperty()));
        dateRepaid.textProperty().bind(Bindings.convert(person.dateRepaidProperty()));
        //TODO: fix tag colours. person.tagProperty().addListener((observable, oldValue, newValue) -> {
        tags.getChildren().clear();
        initTags(person);
    }

    /**
     * Initializes and styles tags belonging to each person.
     * @param person must be a valid person.
     */
    private void initTags(ReadOnlyPerson person) {
        person.getTags().forEach(tag -> {
            Label tagLabel = new Label(tag.tagName);
            tagLabel.setStyle("-fx-font-size:" + "20px");
            tags.getChildren().add(tagLabel);
        });
        logger.finest("All tags for " + person.getName().toString() + " initialized in info");
    }

```
###### \java\seedu\address\ui\InfoPanel.java
``` java
    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof InfoPanel)) {
            return false;
        }

        InfoPanel infoPanel = (InfoPanel) other;
        return name.getText().equals(infoPanel.name.getText())
                && handphone.getText().equals(infoPanel.handphone.getText())
                && homePhone.getText().equals(infoPanel.homePhone.getText())
                && officePhone.getText().equals(infoPanel.officePhone.getText())
                && address.getText().equals(infoPanel.address.getText())
                && postalCode.getText().equals(infoPanel.postalCode.getText())
                && cluster.getText().equals(infoPanel.cluster.getText())
                && debt.getText().equals(infoPanel.debt.getText())
                && totalDebt.getText().equals(infoPanel.totalDebt.getText())
                && interest.getText().equals(infoPanel.interest.getText())
                && email.getText().equals(infoPanel.email.getText())
                && deadline.getText().equals(infoPanel.deadline.getText())
                && dateBorrow.getText().equals(infoPanel.dateBorrow.getText())
                && dateRepaid.getText().equals(infoPanel.dateRepaid.getText())
                && tags.getChildrenUnmodifiable()
                        .stream()
                        .map(Label.class::cast)
                        .map(Label::getText)
                        .collect(Collectors.toList())
                        .equals(infoPanel.tags.getChildrenUnmodifiable()
                                .stream()
                                .map(Label.class::cast)
                                .map(Label::getText)
                                .collect(Collectors.toList()));
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadPersonInfo(event.getNewSelection().person);
        logic.updateSelectedPerson(event.getNewSelection().person);
        resetNearbyPersonListPanel(event.getNewSelection().person);
        resetDebtRepaymentProgressBar(event.getNewSelection().person);
        resetDebtorProfilePicture(event.getNewSelection().person);
        personInfoPanel.setVisible(true);
        personInfoPanel.setContent(content);
    }

    @Subscribe
    private void handleChangeInternalListEvent(ChangeInternalListEvent event) {
        unregisterAsAnEventHandler(this);
    }

    @Subscribe
    private void handleDeselectionEvent(DeselectionEvent event) {
        loadDefaultPage();
    }

}
```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Changes the current theme.
     */
    private void changeTheme() {
        for (String stylesheet : getRoot().getStylesheets()) {
            if (stylesheet.endsWith("DarkTheme.css")) {
                getRoot().getStylesheets().remove(stylesheet);
                getRoot().getStylesheets().add("/view/BrightTheme.css");
                break;
            } else if (stylesheet.endsWith("BrightTheme.css")) {
                getRoot().getStylesheets().remove(stylesheet);
                getRoot().getStylesheets().add("/view/DarkTheme.css");
                break;
            }
        }
    }

```
###### \java\seedu\address\ui\MainWindow.java
``` java
    /**
     * Sets the person panel to display the unfiltered masterlist and raises a {@code JumpToListRequestEvent}.
     */
    @Subscribe
    private void handleNearbyPersonNotInCurrentListEvent(NearbyPersonNotInCurrentListEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        logic.resetFilteredPersonList();
        personListPanel = new PersonListPanel(logic.getFilteredPersonList(), ListCommand.COMMAND_WORD);
        personListPanelPlaceholder.getChildren().clear();
        personListPanelPlaceholder.getChildren().add(personListPanel.getRoot());
        raise(new JumpToListRequestEvent(Index.fromZeroBased(logic.getFilteredPersonList()
                .indexOf(event.getNewSelection().person))));
    }

    /**
     * Handles a request to change theme.
     */
    @Subscribe
    private void handleChangeThemeRequestEvent(ChangeThemeRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        changeTheme();
    }
}
```
###### \java\seedu\address\ui\NearbyPersonListPanel.java
``` java
/**
 * Panel containing the list of nearby persons.
 */
public class NearbyPersonListPanel extends UiPart<Region> {
    private static final String FXML = "NearbyPersonListPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(NearbyPersonListPanel.class);

    private ReadOnlyPerson currentPerson;
    private ObservableList<ReadOnlyPerson> nearbyList;

    @FXML
    private ListView<PersonCard> nearbyPersonListView;

    public NearbyPersonListPanel(ObservableList<ReadOnlyPerson> personList, ReadOnlyPerson person) {
        super(FXML);
        currentPerson = person;
        nearbyList = personList.stream()
                .filter(readOnlyPerson -> readOnlyPerson.isSameCluster(currentPerson))
                .collect(toCollection(FXCollections::observableArrayList));
        setConnections(nearbyList);
        registerAsAnEventHandler(this);
        scrollTo(nearbyList.indexOf(currentPerson));
    }

    private void setConnections(ObservableList<ReadOnlyPerson> personList) {
        ObservableList<PersonCard> mappedList = EasyBind.map(
                personList, (person) -> new PersonCard(person, personList.indexOf(person) + 1));
        nearbyPersonListView.setItems(mappedList);
        nearbyPersonListView.setCellFactory(listView -> new NearbyPersonListViewCell());
        setEventHandlerForSelectionChangeEvent();
    }

    private void setEventHandlerForSelectionChangeEvent() {
        nearbyPersonListView.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (oldValue != null && newValue != null) {
                        logger.fine("Selection in nearby person list panel changed to : '" + newValue + "'");
                        raise(new NearbyPersonPanelSelectionChangedEvent(newValue));
                    }
                });
    }

    /**
     * Scrolls to the {@code PersonCard} at the {@code index} and selects it.
     */
    private void scrollTo(int index) {
        Platform.runLater(() -> {
            nearbyPersonListView.scrollTo(index);
            nearbyPersonListView.getSelectionModel().clearAndSelect(index);
        });
    }

    @Subscribe
    private void handleJumpToListRequestEvent(JumpToNearbyListRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        scrollTo(event.targetIndex);
    }

    @Subscribe
    private void handleChangeInternalListEvent(ChangeInternalListEvent event) {
        unregisterAsAnEventHandler(this);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        unregisterAsAnEventHandler(this);
    }

    /**
     * Custom {@code ListCell} that displays the graphics of a {@code PersonCard}.
     */
    class NearbyPersonListViewCell extends ListCell<PersonCard> {

        @Override
        protected void updateItem(PersonCard person, boolean empty) {
            super.updateItem(person, empty);

            if (empty || person == null) {
                setGraphic(null);
                setText(null);
            } else {
                setGraphic(person.getRoot());
            }
        }
    }

}
```
###### \java\seedu\address\ui\PersonListPanel.java
``` java
    /**
     * When there is a change in selection in nearby panel, scroll to the selected person in this person list panel,
     * if it exists. If it does not exist, a {@code NearbyPersonNotInCurrentListEvent} is raised and this panel is
     * unregistered as an event handler as it will be replaced by a new {@code PersonListPanel} in the
     * {@code MainWindow}.
     */
    @Subscribe
    private void handleNearbyPersonPanelSelectionChangedEvent(NearbyPersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        boolean currentListContainsPerson = false;
        for (PersonCard pc : personListView.getItems()) {
            if (pc.person.equals(event.getNewSelection().person)) {
                scrollTo(personListView.getItems().indexOf(pc));
                currentListContainsPerson = true;
            }
        }
        if (!currentListContainsPerson) {
            raise(new NearbyPersonNotInCurrentListEvent(event.getNewSelection()));
            unregisterAsAnEventHandler(this);
        }
    }

    @Subscribe
    private void handleDeselectionEvent(DeselectionEvent event) {
        personListView.getSelectionModel().clearSelection();
        selectionDisplay.setText(ListObserver.getCurrentListName());
    }

    @Subscribe
    private void handleChangeInternalListEvent(ChangeInternalListEvent event) {
        unregisterAsAnEventHandler(this);
    }

```
###### \java\seedu\address\ui\UiPart.java
``` java
    /**
     * Unregisters the object as an event handler at the {@link EventsCenter}
     * @param handler usually {@code this}
     */
    protected void unregisterAsAnEventHandler(Object handler) {
        EventsCenter.getInstance().unregisterHandler(handler);
    }

```
