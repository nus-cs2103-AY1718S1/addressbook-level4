# kennard123661
###### \java\guitests\AddressBookGuiTest.java
``` java
    protected ParcelListPanelHandle getParcelListPanel() {
        return mainWindowHandle.getActiveParcelListPanel();
    }

    protected TabPaneHandle getTabPane() {
        return mainWindowHandle.getTabPane();
    }
```
###### \java\guitests\guihandles\MainWindowHandle.java
``` java
    public ParcelListPanelHandle getActiveParcelListPanel() {
        return tabPane.getActiveParcelList();
    }

    public ParcelListPanelHandle getDeliveredListPanel() {
        return tabPane.getDeliveredParcelListPanel();
    }

    public ParcelListPanelHandle getUndeliveredListPanel() {
        return tabPane.getUndeliveredParcelListPanel();
    }

    public TabPaneHandle getTabPane() {
        return tabPane;
    }
```
###### \java\guitests\guihandles\ParcelListPanelHandle.java
``` java
    @Override
    public boolean equals(Object object) {
        ParcelListPanelHandle parcelList;

        // check object type
        if (object instanceof ParcelListPanelHandle) {
            parcelList = (ParcelListPanelHandle) object;
        } else {
            return false;
        }

        // check same size
        if (parcelList.getListSize() != getListSize()) {
            return false;
        }

        List<ParcelCard> parcels = getRootNode().getItems();
        List<ParcelCard> otherParcels = parcelList.getRootNode().getItems();

        // compares elements and order
        for (int i = 0; i < parcels.size(); i++) {
            if (!parcels.get(i).equals(otherParcels.get(i))) {
                return false;
            }
        }

        return true;
    }
}
```
###### \java\guitests\guihandles\TabHandle.java
``` java
/**
 * Provides a handle to a parcel card in the parcel list panel.
 */
public class TabHandle extends NodeHandle<Node> {
    public static final String UNDELIVERED_PARCEL_TAB_ID = "#undeliveredParcelsTab";
    public static final String DELIVERED_PARCEL_TAB_ID = "#deliveredParcelsTab";

    public TabHandle(Node cardNode) {
        super(cardNode);
    }

    public Node getTabNode() {
        return this.getRootNode();
    }
}
```
###### \java\guitests\guihandles\TabPaneHandle.java
``` java
/**
 * Provides a handle for {@code ParcelListPanel} containing the list of {@code ParcelCard}.
 */
public class TabPaneHandle extends NodeHandle<TabPane> {
    public static final String TAB_PANE_ID = "#tabPanePlaceholder";
    private final ParcelListPanelHandle deliveredParcelListPanel;
    private final ParcelListPanelHandle undeliveredParcelListPanel;

    public TabPaneHandle(TabPane tabPaneNode) {
        super(tabPaneNode);
        undeliveredParcelListPanel = new ParcelListPanelHandle(getChildNode(ParcelListPanelHandle
                .UNDELIVERED_PARCEL_LIST_VIEW_ID));
        deliveredParcelListPanel = new ParcelListPanelHandle(getChildNode(ParcelListPanelHandle
                .DELIVERED_PARCEL_LIST_VIEW_ID));
    }

    public TabHandle getTabHandle(int index) {
        if (index == INDEX_FIRST_TAB.getZeroBased()) {
            return new TabHandle(getChildNode(TabHandle.UNDELIVERED_PARCEL_TAB_ID));
        } else {
            return new TabHandle(getChildNode(TabHandle.DELIVERED_PARCEL_TAB_ID));
        }
    }

    /**
     * Returns a handle to the current {@code ParcelListPanelHandle} and list of parcels
     * Only one list can be available at a point of time.
     * @throws AssertionError if no tab is selected.
     */
    public ParcelListPanelHandle getActiveParcelList() {
        int selectedTabIndex = getRootNode().getSelectionModel().getSelectedIndex();

        if (selectedTabIndex == INDEX_FIRST_TAB.getZeroBased()) {
            return undeliveredParcelListPanel;
        } else {
            return deliveredParcelListPanel;
        }
    }

    public ParcelListPanelHandle getDeliveredParcelListPanel() {
        return deliveredParcelListPanel;
    }

    public ParcelListPanelHandle getUndeliveredParcelListPanel() {
        return undeliveredParcelListPanel;
    }

    /**
     * Returns the index of the selected tab.
     */
    public int getSelectedTabIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }
}
```
###### \java\seedu\address\logic\commands\AddCommandIntegrationTest.java
``` java
    @Test
    public void execute_duplicateParcel_throwsCommandException() {
        Parcel parcelInList = new Parcel(model.getAddressBook().getParcelList().get(0));
        assertCommandFailure(prepareCommand(parcelInList, model), model, AddCommand.MESSAGE_DUPLICATE_PARCEL);

        // Adding a parcel from a undelivered list -> fails
        Parcel parcelInUndeliveredList = new Parcel(model.getFilteredUndeliveredParcelList().get(0));
        assertCommandFailure(prepareCommand(parcelInUndeliveredList, model), model,
                AddCommand.MESSAGE_DUPLICATE_PARCEL);

        // Adding a parcel from a delivered list -> fails
        Parcel parcelInDeliveredList = new Parcel(model.getFilteredDeliveredParcelList().get(0));
        assertCommandFailure(prepareCommand(parcelInDeliveredList, model), model,
                AddCommand.MESSAGE_DUPLICATE_PARCEL);

        // Adding the same parcel with
        Parcel parcelInListWithDifferentStatus = new Parcel(model.getAddressBook().getParcelList().get(0));
        parcelInListWithDifferentStatus.setStatus(Status.COMPLETED);
        assertCommandFailure(prepareCommand(parcelInDeliveredList, model), model,
                AddCommand.MESSAGE_DUPLICATE_PARCEL);
    }

    /**
     * Generates a new {@code AddCommand} which upon execution, adds {@code parcel} into the {@code model}.
     */
    private AddCommand prepareCommand(Parcel parcel, Model model) {
        AddCommand command = new AddCommand(parcel);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    // list of status
    public static final String VALID_STATUS_PENDING = "PENDING";
    public static final String VALID_STATUS_DELIVERING = "DELIVERING";
    public static final String VALID_STATUS_OVERDUE = "OVERDUE";
    public static final String VALID_STATUS_COMPLETED = "COMPLETED";
    public static final String STATUS_DESC_PENDING = " " + PREFIX_STATUS + VALID_STATUS_PENDING;
    public static final String STATUS_DESC_DELIVERING = " " + PREFIX_STATUS + VALID_STATUS_DELIVERING;
    public static final String STATUS_DESC_COMPLETED = " " + PREFIX_STATUS + VALID_STATUS_COMPLETED;

    // list of tags
    public static final String VALID_TAG_FROZEN = "FROZEN";
    public static final String VALID_TAG_FRAGILE = "FRAGILE";
    public static final String VALID_TAG_FLAMMABLE = "FLAMMABLE";
    public static final String VALID_TAG_HEAVY = "HEAVY";
    public static final String TAG_DESC_FLAMMABLE = " " + PREFIX_TAG + VALID_TAG_FLAMMABLE;
    public static final String TAG_DESC_FROZEN = " " + PREFIX_TAG + VALID_TAG_FROZEN;
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    /**
     * Updates {@code model}'s filtered list to show only the first parcel in the {@code model}'s address book.
     * with status not completed (Active List default state references Undelivered parcels)
     */
    public static void showFirstParcelInActiveListOnly(Model model) {
        List<ReadOnlyParcel> parcels = model.getAddressBook().getParcelList();
        Predicate<ReadOnlyParcel> predicate = (model.getActiveList().equals(model.getFilteredUndeliveredParcelList()))
                ? getDeliveredPredicate().negate() : getDeliveredPredicate();

        // find the first parcel in the master list that meets the active list predicate
        Optional<ReadOnlyParcel> firstParcelOptional = parcels.stream().filter(predicate).findFirst();

        if (!firstParcelOptional.isPresent()) {
            throw new NullPointerException("No parcels in active list");
        }

        final String[] splitName = firstParcelOptional.get().getName().fullName.split("\\s+");
        model.updateFilteredParcelList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assert model.getFilteredParcelList().size() == 1;
    }
```
###### \java\seedu\address\logic\commands\DeleteCommandTest.java
``` java
    @Test
    public void execute_validIndexActiveList_success() throws Exception {
        // active list will be valid.
        model.maintainSorted();
        ReadOnlyParcel parcelToDelete = model.getActiveList().get(Index.fromOneBased(model.getActiveList().size())
                .getZeroBased());
        Index activeListValidIndex = Index.fromOneBased(model.getActiveList().size());
        DeleteCommand deleteCommand = prepareCommand(activeListValidIndex);

        String expectedMessage = String.format(DeleteCommand.MESSAGE_DELETE_PARCEL_SUCCESS, parcelToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.maintainSorted();
        expectedModel.deleteParcel(parcelToDelete);

        assertCommandSuccess(deleteCommand, model, expectedMessage, expectedModel);
    }
```
###### \java\seedu\address\logic\commands\ImportCommandTest.java
``` java
public class ImportCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testFormattedString() {
        List<ReadOnlyParcel> parcels = new ArrayList<>();
        parcels.add(ALICE);
        parcels.add(BENSON);

        String expectedString = "\n  " + ALICE.toString() + "\n  " + BENSON.toString();
        assertEquals(expectedString, ImportCommand.getImportFormattedParcelListString(parcels));

        List<ReadOnlyParcel> empty = new ArrayList<>();
        assertEquals("\n  (none)", ImportCommand.getImportFormattedParcelListString(empty));
    }

    @Test
    public void executeImportCommand_throwsCommandException() throws CommandException {
        AddressBook addressBook = new AddressBookBuilder().withParcel(ALICE).withParcel(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);

        List<ReadOnlyParcel> parcels = new ArrayList<>();
        parcels.add(ALICE);
        parcels.add(BENSON);

        ImportCommand importCommand = getImportCommandForParcel(parcels, modelManager);
        thrown.expect(CommandException.class);
        thrown.expectMessage(MESSAGE_DUPLICATE_PARCELS);
        importCommand.execute();
    }

    @Test
    public void executeImportCommandSuccess() throws CommandException {
        AddressBook addressBook = new AddressBookBuilder().build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);

        List<ReadOnlyParcel> parcels = new ArrayList<>();
        parcels.add(ALICE);
        parcels.add(BENSON);

        // importing without any duplicates
        String importListWithAliceBenson = ImportCommand.getImportFormattedParcelListString(parcels);
        ImportCommand importCommand = getImportCommandForParcel(parcels, modelManager);
        assertEquals(importCommand.execute().feedbackToUser, new CommandResult(String.format(MESSAGE_SUCCESS, 2, 0,
                importListWithAliceBenson, "\n  (none)")).feedbackToUser);

        // importing with some duplicates
        parcels.add(HOON);
        assertEquals(importCommand.execute().feedbackToUser, new CommandResult(String.format(MESSAGE_SUCCESS, 1, 2,
                "\n  " + HOON.toString(), importListWithAliceBenson)).feedbackToUser);
    }

    @Test
    public void equals() {
        List<ReadOnlyParcel> parcels = TypicalParcels.getTypicalParcels();
        List<ReadOnlyParcel> sameParcels = TypicalParcels.getTypicalParcels();
        List<ReadOnlyParcel> otherParcels = new ArrayList<>();

        otherParcels.add(ALICE);
        otherParcels.add(AMY);

        ImportCommand importCommand = new ImportCommand(parcels);
        ImportCommand importCommandWithSameParcels = new ImportCommand(sameParcels);
        ImportCommand importCommandWithDifferentParcels = new ImportCommand(otherParcels);

        // basic equality
        assertEquals(importCommand, importCommand);
        assertEquals(importCommand, importCommandWithSameParcels);

        assertFalse(importCommand.equals(importCommandWithDifferentParcels));

        // shift parcel in the list.
        ReadOnlyParcel parcel = sameParcels.get(0);
        sameParcels.remove(parcel);
        sameParcels.add(parcel);

        // test equality checks elements and not order
        assertEquals(importCommand, importCommandWithSameParcels);
    }

    /**
     * Generates a new ImportCommand with the details of the given parcel.
     */
    private ImportCommand getImportCommandForParcel(List<ReadOnlyParcel> parcels, Model model) {
        ImportCommand command = new ImportCommand(parcels);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void getFilteredParcelListWithStatus() {
        // setting up
        AddressBook addressBook = new AddressBookBuilder().withParcel(ALICE).withParcel(BENSON).withParcel(CARL)
                .withParcel(DANIEL).withParcel(ELLE).withParcel(FIONA).withParcel(GEORGE).build();
        UserPrefs userPrefs = new UserPrefs();

        Model testModel = new ModelManager(addressBook, userPrefs);
        Logic testLogic = new LogicManager(testModel);

        // test for completed parcels
        ObservableList<ReadOnlyParcel> completedParcels = testLogic.getDeliveredParcelList();
        assertTrue(completedParcels.stream().allMatch(parcel -> parcel.getStatus().equals(Status.COMPLETED)));

        // Test for uncompleted parcels
        ObservableList<ReadOnlyParcel> uncompletedParcels = testLogic.getUndeliveredParcelList();
        assertFalse(uncompletedParcels.stream().anyMatch(parcel -> parcel.getStatus().equals(Status.COMPLETED)));
    }
```
###### \java\seedu\address\logic\parser\ImportCommandParserTest.java
``` java
public class ImportCommandParserTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseImportFilePath_invalidInput_throwsIllegalValueException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE)
                + "\nMore Info: " + MESSAGE_FILE_NOT_FOUND);
        new ImportCommandParser().parse("Missing.xml");
    }

    @Test
    public void parseImportFilePath_notXmlFormat_throwsIllegalValueException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(String.format(MESSAGE_INVALID_COMMAND_FORMAT, ImportCommand.MESSAGE_USAGE)
                + "\nMore Info: " + String.format(MESSAGE_INVALID_DATA,
                "./data/import/testNotXmlFormatAddressBook.xml"));

        new ImportCommandParser().parse("testNotXmlFormatAddressBook.xml");
    }

    @Test
    public void parseImportFilePath_invalidFileName_throwsIllegalValueException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(ImportCommandParser.MESSAGE_FILE_NAME_INVALID);

        new ImportCommandParser().parse("#!Hfas.xml");

        // directory traversal attempt
        thrown.expect(ParseException.class);
        thrown.expectMessage(ImportCommandParser.MESSAGE_FILE_NAME_INVALID);

        new ImportCommandParser().parse("../addressBook.xml");
    }

    @Test
    public void parseImportFilePath_validInput_success() throws Exception {
        ImportCommand importCommand = new ImportCommandParser().parse("testValidAddressBook.xml");
        assertTrue(importCommand instanceof ImportCommand);
    }
}
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void addAllParcelsTest() {
        AddressBook addressBook = new AddressBookBuilder().withParcel(ALICE).withParcel(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(addressBook, userPrefs);

        List<ReadOnlyParcel> parcels = TypicalParcels.getTypicalParcels();
        List<ReadOnlyParcel> parcelsAdded = new ArrayList<>();
        List<ReadOnlyParcel> duplicateParcels = new ArrayList<>();
        List<ReadOnlyParcel> expectedParcels = TypicalParcels.getTypicalParcels();

        // logic test
        modelManager.addAllParcels(parcels, parcelsAdded, duplicateParcels);
        assertEquals(8, parcels.size());
        assertEquals(6, parcelsAdded.size());
        assertEquals(2, duplicateParcels.size());

        // elements in parcels test
        for (int i = 0; i < expectedParcels.size(); i++) {
            assertEquals(expectedParcels.get(i), parcels.get(i));
        }

        // ensure that addressbook updated
        assertEquals(4, modelManager.getAddressBook().getTagList().size());
        assertEquals(8, modelManager.getAddressBook().getParcelList().size());
        assertEquals(2, modelManager.getFilteredDeliveredParcelList().size());
        assertEquals(6, modelManager.getFilteredUndeliveredParcelList().size());

        assertEquals(modelManager.getActiveList(), modelManager.getFilteredUndeliveredParcelList());
        modelManager.setActiveList(true);
        assertEquals(modelManager.getActiveList(), modelManager.getFilteredDeliveredParcelList());
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withParcel(ALICE).withParcel(BENSON).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        ModelManager modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        modelManager.updateFilteredParcelList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredParcelList(PREDICATE_SHOW_ALL_PARCELS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookName("differentName");
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }

}
```
###### \java\seedu\address\model\ModelStub.java
``` java
    @Override
    public ObservableList<ReadOnlyParcel> getFilteredDeliveredParcelList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ObservableList<ReadOnlyParcel> getActiveList() {
        fail("This method should not be called.");
        return null;
    }

    @Override
    public ObservableList<ReadOnlyParcel> getFilteredUndeliveredParcelList() {
        fail("This method should not be called.");
        return null;
    }
```
###### \java\seedu\address\model\parcel\AddressTest.java
``` java
public class AddressTest {

    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Address.isValidAddress("")); // empty string
        assertFalse(Address.isValidAddress(" ")); // spaces only
        assertFalse(Address.isValidAddress("Blk 456, Den Road, #01-355")); // does not end with 6 digits
        assertFalse(Address.isValidAddress("Blk 456, Den Road, #01-355 345")); // ends with shorter than 6 digits
        assertFalse(Address.isValidAddress("Blk 456, Den Road, #01-355 345123")); // no S prepending 6 digits
        assertFalse(Address.isValidAddress("- S435342")); // one character

        // valid addresses
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355 S345123")); // upper case s
        assertTrue(Address.isValidAddress("12- S435342"));
        assertTrue(Address.isValidAddress("Blk 456, Den Road, #01-355  s345123")); // lower case s
        assertTrue(Address.isValidAddress("Leng Inc; 1234 Market St; USA s123123")); // long address
    }

    @Test
    public void addressBookParsing() throws IllegalValueException {
        String testAddressString = "Blk 456, Den Road, #01-355         S345123";
        String testAddressWithLowerCasePostalCodeString = "Blk 456, Den Road, #01-355         s345123";
        String expectedAddressString = "Blk 456, Den Road, #01-355 S345123";

        Address address = new Address(testAddressString);
        assertEquals(address.toString(), expectedAddressString);
        Address addressWithLowerCasePostalCode = new Address(testAddressWithLowerCasePostalCodeString);
        assertEquals(addressWithLowerCasePostalCode.toString(), expectedAddressString);
        assertEquals(addressWithLowerCasePostalCode.toString(), address.toString());
    }

    @Test
    public void equals() throws IllegalValueException {
        String testAddressString = "Blk 456, Den Road, #01-355         S345123";
        String testAddressWithLowerCasePostalCodeString = "Blk 456, Den Road, #01-355         s345123";
        String expectedAddressString = "Blk 456, Den Road, #01-355 S345123";

        // instantiation
        Address address = new Address(testAddressString);
        Address addressWithLowerCasePostalCode = new Address(testAddressWithLowerCasePostalCodeString);
        Address expectedAddress = new Address(expectedAddressString);

        // equality check
        assertEquals(address, expectedAddress);
        assertEquals(address, addressWithLowerCasePostalCode);
        assertEquals(addressWithLowerCasePostalCode, expectedAddress);

        // hash code equality check
        assertEquals(address.hashCode(), expectedAddress.hashCode());
        assertEquals(address.hashCode(), addressWithLowerCasePostalCode.hashCode());
        assertEquals(addressWithLowerCasePostalCode.hashCode(), expectedAddress.hashCode());
    }
}
```
###### \java\seedu\address\model\parcel\PostalCodeTest.java
``` java
public class PostalCodeTest {

    @Test
    public void isValidPostalCode() {

        // invalid postal code
        assertFalse(PostalCode.isValidPostalCode("")); // empty string
        assertFalse(PostalCode.isValidPostalCode(" ")); // spaces only
        assertFalse(PostalCode.isValidPostalCode("000000")); // no 's' present
        assertFalse(PostalCode.isValidPostalCode("ss000000")); // prepended by two 's'
        assertFalse(PostalCode.isValidPostalCode("s00000")); // shorter than 6 digits
        assertFalse(PostalCode.isValidPostalCode("s0000000")); // longer than 6 digits
        assertFalse(PostalCode.isValidPostalCode("s#!@a11")); // random characters in 6 digits

        // valid postal code
        assertTrue(PostalCode.isValidPostalCode("s000000"));
        assertTrue(PostalCode.isValidPostalCode("S123456"));
        assertTrue(PostalCode.isValidPostalCode("s000845"));
    }
}
```
###### \java\seedu\address\model\parcel\StatusTest.java
``` java
public class StatusTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void testToStringTest() {
        Status completed = Status.COMPLETED;
        assertEquals("COMPLETED", completed.toString());

        Status delivering = Status.DELIVERING;
        assertEquals("DELIVERING", delivering.toString());

        Status pending = Status.PENDING;
        assertEquals("PENDING", pending.toString());

        Status overdue = Status.OVERDUE;
        assertEquals("OVERDUE", overdue.toString());
    }

    @Test
    public void getStatusInstanceTest() throws IllegalValueException {
        // all uppercase
        Status pending = Status.getInstance("PENDING");
        assertEquals(Status.PENDING, pending);

        // all lowercase
        Status delivering = Status.getInstance("delivering");
        assertEquals(Status.DELIVERING, delivering);

        // mix of uppercase and lowercase characters
        Status completed = Status.getInstance("cOmPleTeD");
        assertEquals(Status.COMPLETED, completed);

        // mix of uppercase and lowercase characters
        Status overdue = Status.getInstance("overDUE");
        assertEquals(Status.OVERDUE, overdue);

        thrown.expect(IllegalValueException.class);
        thrown.expectMessage(Status.MESSAGE_STATUS_CONSTRAINTS);
        Status.getInstance("asd1237fa&(&"); // weird characters
        Status.getInstance("JUMPING"); // not one of the possible values
    }

    @Test
    public void isValidStatusTest() {
        assertFalse(Status.isValidStatus("INVALID"));

        // uppercase letters
        assertTrue(Status.isValidStatus("PENDING"));
        assertTrue(Status.isValidStatus("DELIVERING"));
        assertTrue(Status.isValidStatus("OVERDUE"));
        assertTrue(Status.isValidStatus("COMPLETED"));

        // lower case letters
        assertFalse(Status.isValidStatus("pending"));
        assertFalse(Status.isValidStatus("completed"));

        // mix of upper and lower case
        assertFalse(Status.isValidStatus("ComPleTed"));

        // random symbols
        assertFalse(Status.isValidStatus("$!@HBJ123"));
    }

}
```
###### \java\seedu\address\model\parcel\TrackingNumberTest.java
``` java
public class TrackingNumberTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void isValidTrackingNumber() throws Exception {
        assertFalse(TrackingNumber.isValidTrackingNumber("")); // empty string
        assertFalse(TrackingNumber.isValidTrackingNumber(" ")); // spaces only

        // missing parts
        assertFalse(TrackingNumber.isValidTrackingNumber("RR999999999")); // missing postfix 'SG'
        assertFalse(TrackingNumber.isValidTrackingNumber("999999999SG")); // missing prefix 'RR'
        assertFalse(TrackingNumber.isValidTrackingNumber("RRSG")); // missing digits
        assertFalse(TrackingNumber.isValidTrackingNumber("999999999")); // missing postfix and prefix

        // invalid parts
        assertFalse(TrackingNumber.isValidTrackingNumber("PE999999999SG")); // invalid prefix
        assertFalse(TrackingNumber.isValidTrackingNumber("RR999999999TW")); // invalid postfix
        assertFalse(TrackingNumber.isValidTrackingNumber("PE999999999TW")); // invalid prefix and postfix
        assertFalse(TrackingNumber.isValidTrackingNumber("PE9999999999SG")); // too long
        assertFalse(TrackingNumber.isValidTrackingNumber("RR99999999SG")); // too short
        assertFalse(TrackingNumber.isValidTrackingNumber("RR999!@#999SG")); // contain non-digit symbols
        assertFalse(TrackingNumber.isValidTrackingNumber("RR9999SG99999")); // wrong order
        assertFalse(TrackingNumber.isValidTrackingNumber("SG999999999RR")); // wrong order

        // valid email
        assertTrue(TrackingNumber.isValidTrackingNumber("RR999999999SG"));
        assertTrue(TrackingNumber.isValidTrackingNumber("RR123456789SG"));
        assertTrue(TrackingNumber.isValidTrackingNumber("RR001231230SG"));
    }

    @Test
    public void equals() throws Exception {
        TrackingNumber trackingNumber = new TrackingNumber("RR001231230SG");
        TrackingNumber sameTrackingNumber = new TrackingNumber("RR001231230SG");
        TrackingNumber differentTrackingNumber = new TrackingNumber("RR999999999SG");

        assertFalse(differentTrackingNumber.equals(trackingNumber));
        assertFalse(trackingNumber == null);

        assertEquals(trackingNumber, sameTrackingNumber);

        // check toString() equality
        assertFalse(trackingNumber.toString().equals(differentTrackingNumber.toString()));
        assertEquals(trackingNumber.toString(), sameTrackingNumber.toString());
        assertEquals(trackingNumber.toString(), "RR001231230SG");

        // check hashCode() equality
        assertFalse(trackingNumber.hashCode() == differentTrackingNumber.hashCode());
        assertTrue(trackingNumber.hashCode() == sameTrackingNumber.hashCode());
    }

    @Test
    public void testInvalidTrackingNumberInputThrowsExcpetion() throws IllegalValueException {
        expected.expect(IllegalValueException.class);
        expected.expectMessage(MESSAGE_TRACKING_NUMBER_CONSTRAINTS);
        new TrackingNumber(" "); // illegal tracking number
    }

}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void onInitialStartupNoBackupTest() throws DataConversionException, IOException {
        testLogger = new TestLogger(storageManager.getClass(), Level.WARNING);
        storageManager = new StorageManager(new XmlAddressBookStorage("NotXmlFormatAddressBook.xml"),
                new JsonUserPrefsStorage("random.json"));

        // test for log message.
        String capturedLog = testLogger.getTestCapturedLog();
        String expectedLogMessage = "WARNING - AddressBook not present, backup not possible\n";
        assertEquals(capturedLog, expectedLogMessage);

        // testing if backup exists
        Optional<ReadOnlyAddressBook> backupAddressBookOptional = storageManager
                .readAddressBook(storageManager.getBackupStorageFilePath());
        assertFalse(backupAddressBookOptional.isPresent());
    }

    @Test
    public void backupAddressBook() throws Exception {
        // set up
        AddressBook original = getTypicalAddressBook();
        storageManager.saveAddressBook(original);

        // create new backup by loading another Storage Manager
        XmlAddressBookStorage addressBookStorage = new XmlAddressBookStorage(getTempFilePath("ab"));
        JsonUserPrefsStorage userPrefsStorage = new JsonUserPrefsStorage(getTempFilePath("prefs"));
        testLogger = new TestLogger(StorageManager.class, Level.INFO);
        StorageManager backupStorageManager = new StorageManager(addressBookStorage, userPrefsStorage);

        String capturedLog = testLogger.getTestCapturedLog();
        String expectedLog = "INFO - AddressBook present, back up success\n";
        assertEquals(capturedLog, expectedLog);

        // checks that the backup properly backups the new file.
        Optional<ReadOnlyAddressBook> backupAddressBookOptional = backupStorageManager
                .readAddressBook(backupStorageManager.getBackupStorageFilePath());
        AddressBook backupAddressBook = new AddressBook(backupAddressBookOptional.get());
        assertEquals(backupAddressBook, original);

        // checks that the file does not backup on every save
        AddressBook editedBook = new AddressBook();
        backupStorageManager.saveAddressBook(editedBook);
        Optional<ReadOnlyAddressBook> mainAddressBookOptional = backupStorageManager
                .readAddressBook(backupStorageManager.getAddressBookFilePath());

        AddressBook mainAddressBook = new AddressBook(mainAddressBookOptional.get());
        assertFalse(mainAddressBook.equals(backupAddressBook));

        // checks that the backup only saves on the initialization of another storage manager.
        StorageManager anotherStorageManager = new StorageManager(addressBookStorage, userPrefsStorage);
        backupAddressBookOptional = anotherStorageManager
                .readAddressBook(backupStorageManager.getBackupStorageFilePath());
        backupAddressBook = new AddressBook(backupAddressBookOptional.get());
        assertEquals(editedBook, backupAddressBook);
    }

    @Test
    public void backUpUrlTest() {
        String expectedUrl = storageManager.getAddressBookFilePath() + "-backup.xml";
        String actualUrl = storageManager.getBackupStorageFilePath();
        assertEquals(expectedUrl, actualUrl);
    }

    @Test
    public void backUpCommandTest() throws IOException, DataConversionException {
        AddressBook original = getTypicalAddressBook();
        storageManager.backupAddressBook(original);
        Optional<ReadOnlyAddressBook> backupAddressBookOptional = storageManager
                .readAddressBook(storageManager.getBackupStorageFilePath());
        AddressBook backupAddressBook = new AddressBook(backupAddressBookOptional.get());
        assertEquals(backupAddressBook, original);
    }
```
###### \java\seedu\address\testutil\TypicalParcels.java
``` java
    // Parcels that are PENDING.
    public static final String VALID_TRACKING_NUMBER_ALICE = "RR000111000SG";
    public static final String VALID_NAME_ALICE = "Alice Pauline";
    public static final String VALID_PHONE_ALICE = "85355255";
    public static final String VALID_EMAIL_ALICE = "alice@example.com";
    public static final String VALID_ADDRESS_ALICE = "6, Jurong West Ave 1, #08-111 S649520";
    public static final String VALID_DELIVERY_DATE_ALICE = "01-02-2000";
    public static final String TRACKING_NUMBER_DESC_ALICE = " " + PREFIX_TRACKING_NUMBER + VALID_TRACKING_NUMBER_ALICE;
    public static final String NAME_DESC_ALICE = " " + PREFIX_NAME + VALID_NAME_ALICE;
    public static final String PHONE_DESC_ALICE = " " + PREFIX_PHONE + VALID_PHONE_ALICE;
    public static final String EMAIL_DESC_ALICE = " " + PREFIX_EMAIL + VALID_EMAIL_ALICE;
    public static final String ADDRESS_DESC_ALICE = " " + PREFIX_ADDRESS + VALID_ADDRESS_ALICE;
    public static final String DELIVERY_DATE_DESC_ALICE = " " + PREFIX_DELIVERY_DATE + VALID_DELIVERY_DATE_ALICE;

    public static final String VALID_TRACKING_NUMBER_BENSON = "RR111000111SG";
    public static final String VALID_NAME_BENSON = "Benson Meier";
    public static final String VALID_PHONE_BENSON = "98765432";
    public static final String VALID_EMAIL_BENSON = "johnd@example.com";
    public static final String VALID_ADDRESS_BENSON = "336, Clementi Ave 2, #02-25 s120336";
    public static final String VALID_DELIVERY_DATE_BENSON = "02-02-2000";
    public static final String TRACKING_NUMBER_DESC_BENSON = " " + PREFIX_TRACKING_NUMBER
            + VALID_TRACKING_NUMBER_BENSON;
    public static final String NAME_DESC_BENSON = " " + PREFIX_NAME + VALID_NAME_BENSON;
    public static final String PHONE_DESC_BENSON = " " + PREFIX_PHONE + VALID_PHONE_BENSON;
    public static final String EMAIL_DESC_BENSON = " " + PREFIX_EMAIL + VALID_EMAIL_BENSON;
    public static final String ADDRESS_DESC_BENSON = " " + PREFIX_ADDRESS + VALID_ADDRESS_BENSON;
    public static final String DELIVERY_DATE_DESC_BENSON = " " + PREFIX_DELIVERY_DATE + VALID_DELIVERY_DATE_BENSON;

    // Parcels that are OVERDUE
    public static final String VALID_TRACKING_NUMBER_CARL = "RR222000111SG";
    public static final String VALID_NAME_CARL = "Carl Kurz";
    public static final String VALID_PHONE_CARL = "95352563";
    public static final String VALID_EMAIL_CARL = "heinz@example.com";
    public static final String VALID_ADDRESS_CARL = "18 Marina Blvd, S018980";
    public static final String VALID_DELIVERY_DATE_CARL = "03-02-2000";

    public static final String VALID_TRACKING_NUMBER_DANIEL = "RR111321123SG";
    public static final String VALID_NAME_DANIEL = "Daniel Meier";
    public static final String VALID_PHONE_DANIEL = "87652533";
    public static final String VALID_EMAIL_DANIEL = "cornelia@example.com";
    public static final String VALID_ADDRESS_DANIEL = "59 Namly Garden S267387";
    public static final String VALID_DELIVERY_DATE_DANIEL = "04-02-2000";

    // parcels that are DELIVERING
    public static final String VALID_TRACKING_NUMBER_ELLE = "RR111321124SG";
    public static final String VALID_NAME_ELLE = "Elle Meyer";
    public static final String VALID_PHONE_ELLE = "9482224";
    public static final String VALID_EMAIL_ELLE = "werner@example.com";
    public static final String VALID_ADDRESS_ELLE = "2 Finlayson Green, S049247";
    public static final String VALID_DELIVERY_DATE_ELLE = "05-02-2000";

    public static final String VALID_TRACKING_NUMBER_FIONA = "RR999123555SG";
    public static final String VALID_NAME_FIONA = "Fiona Kunz";
    public static final String VALID_PHONE_FIONA = "9482427";
    public static final String VALID_EMAIL_FIONA = "lydia@example.com";
    public static final String VALID_ADDRESS_FIONA = "48 Upper Dickson Rd S207502";
    public static final String VALID_DELIVERY_DATE_FIONA = "06-02-2000";

    // Parcels that are COMPLETED
    public static final String VALID_TRACKING_NUMBER_GEORGE = "RR696969696SG";
    public static final String VALID_NAME_GEORGE = "George Best";
    public static final String VALID_PHONE_GEORGE = "9482442";
    public static final String VALID_EMAIL_GEORGE = "anna@example.com";
    public static final String VALID_ADDRESS_GEORGE = "Block 532 HDB Upper Cross Street s050532";
    public static final String VALID_DELIVERY_DATE_GEORGE = "07-02-2007";

    public static final String VALID_TRACKING_NUMBER_HOON = "RR121212124SG";
    public static final String VALID_NAME_HOON = "Hoon Meier";
    public static final String VALID_PHONE_HOON = "8482424";
    public static final String VALID_EMAIL_HOON = "stefan@example.com";
    public static final String VALID_ADDRESS_HOON = "522 Hougang Ave 6 s530522";
    public static final String VALID_DELIVERY_DATE_HOON = "10-10-2010";

    // Parcels to be manually added
    public static final String VALID_TRACKING_NUMBER_IDA = "RR111333888SG";
    public static final String VALID_NAME_IDA = "Ida Mueller";
    public static final String VALID_PHONE_IDA = "8482131";
    public static final String VALID_EMAIL_IDA = "hans@example.com";
    public static final String VALID_ADDRESS_IDA = "3 River Valley Rd, S179024";
    public static final String VALID_DELIVERY_DATE_IDA = "09-09-2009";

    public static final String VALID_TRACKING_NUMBER_JOHN = "RR998877665SG";
    public static final String VALID_NAME_JOHN = "John Doe";
    public static final String VALID_PHONE_JOHN = "99999991";
    public static final String VALID_EMAIL_JOHN = "jd@example.com";
    public static final String VALID_ADDRESS_JOHN = "3 River Valley Rd, S179024";
    public static final String VALID_DELIVERY_DATE_JOHN = "09-12-2030";

    public static final ReadOnlyParcel ALICE = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_ALICE)
            .withName(VALID_NAME_ALICE).withAddress(VALID_ADDRESS_ALICE)
            .withEmail(VALID_EMAIL_ALICE).withPhone(VALID_PHONE_ALICE).withDeliveryDate(VALID_DELIVERY_DATE_ALICE)
            .withStatus(VALID_STATUS_PENDING).withTags(VALID_TAG_FROZEN).build();
    public static final ReadOnlyParcel BENSON = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_BENSON)
            .withName(VALID_NAME_BENSON).withAddress(VALID_ADDRESS_BENSON).withEmail(VALID_EMAIL_BENSON)
            .withPhone(VALID_PHONE_BENSON).withDeliveryDate(VALID_DELIVERY_DATE_BENSON).withStatus(VALID_STATUS_PENDING)
            .withTags(VALID_TAG_FROZEN, VALID_TAG_FLAMMABLE).build();
    public static final ReadOnlyParcel CARL = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_CARL)
            .withName(VALID_NAME_CARL).withPhone(VALID_PHONE_CARL).withEmail(VALID_EMAIL_CARL)
            .withAddress(VALID_ADDRESS_CARL).withStatus(VALID_STATUS_OVERDUE)
            .withDeliveryDate(VALID_DELIVERY_DATE_CARL).build();
    public static final ReadOnlyParcel DANIEL = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_DANIEL)
            .withName(VALID_NAME_DANIEL).withPhone(VALID_PHONE_DANIEL).withEmail(VALID_EMAIL_DANIEL)
            .withAddress(VALID_ADDRESS_DANIEL).withStatus(VALID_STATUS_OVERDUE)
            .withDeliveryDate(VALID_DELIVERY_DATE_DANIEL).build();
    public static final ReadOnlyParcel ELLE = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_ELLE)
            .withName(VALID_NAME_ELLE).withPhone(VALID_PHONE_ELLE).withEmail(VALID_EMAIL_ELLE)
            .withAddress(VALID_ADDRESS_ELLE).withTags(VALID_TAG_HEAVY).withDeliveryDate(VALID_DELIVERY_DATE_ELLE)
            .build();
    public static final ReadOnlyParcel FIONA = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_FIONA)
            .withName(VALID_NAME_FIONA).withPhone(VALID_PHONE_FIONA).withEmail(VALID_EMAIL_FIONA)
            .withAddress(VALID_ADDRESS_FIONA).withDeliveryDate(VALID_DELIVERY_DATE_FIONA)
            .withStatus(VALID_STATUS_DELIVERING).build();
    public static final ReadOnlyParcel GEORGE = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_GEORGE)
            .withName(VALID_NAME_GEORGE).withPhone(VALID_PHONE_GEORGE).withEmail(VALID_EMAIL_GEORGE)
            .withAddress(VALID_ADDRESS_GEORGE).withDeliveryDate(VALID_DELIVERY_DATE_GEORGE)
            .withTags(VALID_TAG_FRAGILE, VALID_TAG_HEAVY).withStatus(VALID_STATUS_COMPLETED).build();
    public static final ReadOnlyParcel HOON = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_HOON)
            .withName(VALID_NAME_HOON).withPhone(VALID_PHONE_HOON).withEmail(VALID_EMAIL_HOON)
            .withAddress(VALID_ADDRESS_HOON).withDeliveryDate(VALID_DELIVERY_DATE_HOON)
            .withStatus(VALID_STATUS_COMPLETED).build();

    // Manually added
    public static final ReadOnlyParcel IDA = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_IDA)
            .withName(VALID_NAME_IDA).withPhone(VALID_PHONE_IDA).withEmail(VALID_EMAIL_IDA)
            .withAddress(VALID_ADDRESS_IDA).withDeliveryDate(VALID_DELIVERY_DATE_IDA)
            .withStatus(VALID_STATUS_PENDING).build();
    public static final ReadOnlyParcel JOHN = new ParcelBuilder().withTrackingNumber(VALID_TRACKING_NUMBER_JOHN)
            .withName(VALID_NAME_JOHN).withPhone(VALID_PHONE_JOHN).withEmail(VALID_EMAIL_JOHN)
            .withAddress(VALID_ADDRESS_JOHN).withDeliveryDate(VALID_DELIVERY_DATE_JOHN)
            .withTags(VALID_TAG_HEAVY, VALID_TAG_FLAMMABLE).withStatus(VALID_STATUS_COMPLETED).build();
```
###### \java\seedu\address\ui\ParcelListPanelTest.java
``` java
    @Test
    public void display() {
        postNow(JUMP_TO_SECOND_TAB_EVENT);
        guiRobot.pauseForHuman();

        parcelListPanelHandle = new ParcelListPanelHandle(getChildNode(parcelListPanel.getRoot(),
                ParcelListPanelHandle.DELIVERED_PARCEL_LIST_VIEW_ID));
        for (int i = 0; i < TYPICAL_DELIVERED_PARCELS.size(); i++) {
            parcelListPanelHandle.navigateToCard(TYPICAL_DELIVERED_PARCELS.get(i));
            ReadOnlyParcel expectedParcel = TYPICAL_DELIVERED_PARCELS.get(i);
            ParcelCardHandle actualCard = parcelListPanelHandle.getParcelCardHandle(i);

            assertCardDisplaysParcel(expectedParcel, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }

        postNow(JUMP_TO_FIRST_TAB_EVENT);
        guiRobot.pauseForHuman();

        parcelListPanelHandle = new ParcelListPanelHandle(getChildNode(parcelListPanel.getRoot(),
                ParcelListPanelHandle.UNDELIVERED_PARCEL_LIST_VIEW_ID));
        for (int i = 0; i < TYPICAL_UNDELIVERED_PARCELS.size(); i++) {
            parcelListPanelHandle.navigateToCard(TYPICAL_UNDELIVERED_PARCELS.get(i));
            ReadOnlyParcel expectedParcel = TYPICAL_UNDELIVERED_PARCELS.get(i);
            ParcelCardHandle actualCard = parcelListPanelHandle.getParcelCardHandle(i);

            assertCardDisplaysParcel(expectedParcel, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    public ParcelListPanelHandle getParcelListPanel() {
        return mainWindowHandle.getActiveParcelListPanel();
    }

    public ParcelListPanelHandle getDeliveredParcelListPanel() {
        return mainWindowHandle.getDeliveredListPanel();
    }

    public ParcelListPanelHandle getUndeliveredParcelListPanel() {
        return mainWindowHandle.getUndeliveredListPanel();
    }
```
###### \java\systemtests\ImportCommandSystemTest.java
``` java
public class ImportCommandSystemTest extends AddressBookSystemTest {

    private static final String STORAGE_FILE = "testAddressBookForImportSystem.xml";

    @Before
    public void start() throws Exception {
        // reset the storage file used in ImportCommandSystemTest
        AddressBook addressBook = new AddressBookBuilder().build();
        new XmlAddressBookStorage("./data/import/" + STORAGE_FILE).saveAddressBook(addressBook);
    }

    @Test
    public void importTest() throws Exception {
        Model model = getModel();

        /* Case: import parcels without tags to a non-empty address book, command with leading spaces and trailing
         * spaces -> added
         */
        AddressBook addressBook = new AddressBookBuilder().withParcel(AMY).withParcel(BOB).build();
        XmlAddressBookStorage storage = new XmlAddressBookStorage(
                "./data/import/" + STORAGE_FILE);
        storage.saveAddressBook(addressBook);

        List<ReadOnlyParcel> parcelsAdded;
        List<ReadOnlyParcel> duplicateParcels = new ArrayList<>();
        List<ReadOnlyParcel> parcels = addressBook.getParcelList();

        String command = "   " + ImportCommand.COMMAND_WORD + "  " + STORAGE_FILE + "   ";
        assertCommandSuccess(command, parcels, parcels, new ArrayList<>());

        /* Case: undo importing testAddressBookForImportSystem.xml data to the list -> Amy and Bob deleted */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        model.maintainSorted();
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: redo adding Amy to the list -> Amy added again */
        command = RedoCommand.COMMAND_WORD;
        model.addAllParcels(parcels, new ArrayList<>(), new ArrayList<>());
        model.maintainSorted();
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, model, expectedResultMessage);

        /* Case: add a duplicate parcel -> rejected */
        command = ImportCommand.COMMAND_WORD + " " + STORAGE_FILE;
        assertCommandFailure(command, ImportCommand.MESSAGE_DUPLICATE_PARCELS);

        /* Case: import an addressbook xml file containing duplicate parcels except with different tags -> rejected */
        // AddressBook#addAllParcels(List<ReadOnlyParcel>)
        addressBook = new AddressBookBuilder().withParcel(new ParcelBuilder(AMY).withTags("DURABLE").build())
                .withParcel(new ParcelBuilder(BOB).withTags("FRAGILE").build()).build();
        storage.saveAddressBook(addressBook);
        command = ImportCommand.COMMAND_WORD + " " + STORAGE_FILE;
        assertCommandFailure(command, ImportCommand.MESSAGE_DUPLICATE_PARCELS);

        /* Case: imports parcels with all fields same as other parcels in the address book except name -> imported */
        addressBook = new AddressBookBuilder().withParcel(new ParcelBuilder(AMY).withName("Kyle").build())
                .withParcel(new ParcelBuilder(BOB).withName("John").build()).build();
        storage.saveAddressBook(addressBook);
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: imports parcels with all fields same as other parcels in the address book except phone -> imported */
        addressBook = new AddressBookBuilder().withParcel(new ParcelBuilder(AMY).withPhone("98547321").build())
                .withParcel(new ParcelBuilder(BOB).withPhone("96758989").build()).build();
        storage.saveAddressBook(addressBook);
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: imports parcels with all fields same as other parcels in the address book except email -> imported */
        addressBook = new AddressBookBuilder().withParcel(new ParcelBuilder(AMY).withEmail("amy@gmail.com").build())
                .withParcel(new ParcelBuilder(BOB).withEmail("bob@gmail.com").build()).build();
        storage.saveAddressBook(addressBook);
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: imports parcels with all fields same as another parcel in the address book
         * except address -> imported
         */
        addressBook = new AddressBookBuilder().withParcel(new ParcelBuilder(AMY).withAddress("Amy Street S123661")
                .build()).withParcel(new ParcelBuilder(BOB).withAddress("BOB Street S456123").build()).build();
        storage.saveAddressBook(addressBook);
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: add a parcel with all fields same as another parcel in the address book
         * except tracking number -> imported
         */
        addressBook = new AddressBookBuilder().withParcel(new ParcelBuilder(AMY).withTrackingNumber("RR123564897SG")
                .build()).withParcel(new ParcelBuilder(BOB).withTrackingNumber("RR123564897SG").build()).build();
        storage.saveAddressBook(addressBook);
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: add a parcel with all fields same as another parcel in the address book
         * except delivery date -> imported
         */
        addressBook = new AddressBookBuilder().withParcel(new ParcelBuilder(AMY).withDeliveryDate("12-01-2017")
                .build()).withParcel(new ParcelBuilder(BOB).withDeliveryDate("12-01-2017").build()).build();
        storage.saveAddressBook(addressBook);
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: filters the parcel list before importing -> imported */
        executeCommand(FindCommand.COMMAND_WORD + " " + KEYWORD_MATCHING_MEIER);
        assert getModel().getFilteredParcelList().size() < getModel().getAddressBook().getParcelList().size();
        addressBook = new AddressBookBuilder().withParcel(IDA).build();
        storage.saveAddressBook(addressBook);
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: imports to ann empty address book -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        addressBook = new AddressBookBuilder().withParcel(IDA).withParcel(BENSON).build();
        parcelsAdded = addressBook.getParcelList();
        storage.saveAddressBook(addressBook);
        assert getModel().getAddressBook().getParcelList().size() == 0;
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: selects first card in the parcel list, import parcels -> import, card selection remains unchanged */
        executeCommand(SelectCommand.COMMAND_WORD + " 1");
        assert getParcelListPanel().isAnyCardSelected();
        addressBook = new AddressBookBuilder().withParcel(CARL).withParcel(ALICE).build();
        storage.saveAddressBook(addressBook);
        parcelsAdded = addressBook.getParcelList();
        assertCommandSuccess(command, parcelsAdded, parcelsAdded, duplicateParcels);

        /* Case: invalid keyword -> rejected */
        command = "imports " + STORAGE_FILE;
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: missing file -> rejected */
        command = "import " + "missing.xml";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE) + "\nMore Info: " + MESSAGE_FILE_NOT_FOUND);

        /* Case: invalid xml file -> rejected */
        command = "import " + "testNotXmlFormatAddressBook.xml";
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ImportCommand.MESSAGE_USAGE) + "\nMore Info: " + String.format(MESSAGE_INVALID_DATA,
                "./data/import/testNotXmlFormatAddressBook.xml"));

    }

    @After
    public void clear() throws Exception {
        // reset the storage file used in ImportCommandSystemTest
        AddressBook addressBook = new AddressBookBuilder().build();
        new XmlAddressBookStorage("./data/import/" + STORAGE_FILE).saveAddressBook(addressBook);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)}. Executes {@code command}
     * instead.
     */
    private void assertCommandSuccess(String command, List<ReadOnlyParcel> parcelsToAdd,
                                      List<ReadOnlyParcel> expectedAddedParcels,
                                      List<ReadOnlyParcel> expectedDuplicateParcels) {

        Model expectedModel = getModel();
        List<ReadOnlyParcel> duplicateParcels = new ArrayList<>();
        List<ReadOnlyParcel> addedParcels = new ArrayList<>();

        expectedModel.addAllParcels(parcelsToAdd, addedParcels, duplicateParcels);
        String expectedResultMessage = String.format(ImportCommand.MESSAGE_SUCCESS, expectedAddedParcels.size(),
                expectedDuplicateParcels.size(), ImportCommand.getImportFormattedParcelListString(expectedAddedParcels),
                ImportCommand.getImportFormattedParcelListString(expectedDuplicateParcels));

        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the browser url, selected card and status bar remain unchanged, and the command box has the
     * error style.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }

}
```
