# johnweikangong
###### \java\guitests\guihandles\HelpPanelHandle.java
``` java
/**
 * A handler for the {@code HelpPanel} of the UI
 */
public class HelpPanelHandle extends NodeHandle<Node> {

    public static final String HELP_PANEL_ID = "#helpPanel";

    public HelpPanelHandle(Node helpPanelNode) {
        super(helpPanelNode);
    }

    /**
     * Returns the HelpPanel node.
     */
    public Node getHelpPanelNode() {
        return getRootNode();
    }
}
```
###### \java\guitests\guihandles\HomePanelHandle.java
``` java
/**
 * A handler for the {@code HomePanel} of the UI
 */
public class HomePanelHandle extends NodeHandle<Node> {

    public static final String HOME_PANEL_ID = "#homePanel";

    public HomePanelHandle(Node homePanelNode) {
        super(homePanelNode);
    }

    /**
     * Returns the HomePanel node.
     */
    public Node getHomePanelNode() {
        return getRootNode();
    }
}
```
###### \java\guitests\guihandles\PersonDetailsPanelHandle.java
``` java
/**
 * Provides a handle for {@code PersonDetailsPanel}.
 */
public class PersonDetailsPanelHandle extends NodeHandle<Node> {
    public static final String PERSON_DETAILS_VIEW_ID = "#personDetailsPanel";
    private static final String NAME_ID = "#name";
    private static final String PHONE_ID = "#phone";
    private static final String BIRTHDAY_ID = "#birthday";
    private static final String EMAIL_ID = "#email";
    private static final String ADDRESS_ID = "#address";
    private static final String POSTALCODE_ID = "#postalCode";

    private final Label name;
    private final Label phone;
    private final Label birthday;
    private final Label email;
    private final Label address;
    private final Label postalCode;

    public PersonDetailsPanelHandle(Node personDetailsPanelNode) {
        super(personDetailsPanelNode);

        this.name = getChildNode(NAME_ID);
        this.phone = getChildNode(PHONE_ID);
        this.birthday = getChildNode(BIRTHDAY_ID);
        this.email = getChildNode(EMAIL_ID);
        this.address = getChildNode(ADDRESS_ID);
        this.postalCode = getChildNode(POSTALCODE_ID);
    }

    public PersonDetailsPanelHandle getPersonDetailsPanelHandle() {
        return this;
    }

    public String getName() {
        return name.getText();
    }

    public String getPhone() {
        return phone.getText();
    }

    public String getBirthday() {
        return birthday.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public String getPostalCode() {
        return postalCode.getText();
    }
}
```
###### \java\seedu\address\logic\commands\ExportCommandTest.java
``` java
public class ExportCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_export_success() {
        CommandResult result = new ExportCommand().execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ExportToFileRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\commands\HomeCommandTest.java
``` java
public class HomeCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_help_success() {
        CommandResult result = new HomeCommand().execute();
        assertEquals(MESSAGE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ChangeInformationPanelRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\model\person\PostalCodeTest.java
``` java
public class PostalCodeTest {

    @Test
    public void isValidAddress() {
        // Invalid postal codes
        assertFalse(PostalCode.isValidPostalCode("@^&")); // Special characters
        assertFalse(PostalCode.isValidPostalCode("12345678")); // More than 6 digits long
        assertFalse(PostalCode.isValidPostalCode("12345")); // Less than 6 digits long
        assertFalse(PostalCode.isValidPostalCode("915920")); // More than 800000

        // Valid postal code
        assertTrue(Address.isValidAddress("000000")); // Lower bound of postal code range
        assertTrue(Address.isValidAddress("450920"));
        assertTrue(Address.isValidAddress("800000")); // Upper bound of postal code range
    }
}
```
###### \java\seedu\address\storage\CsvFileStorageTest.java
``` java
public class CsvFileStorageTest {
    public static final String TEST_DATA_FOLDER = FileUtil.getPath(
            "./src/test/data/CsvFileStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void saveToCsvFile_allInOrder_success() throws Exception {
        String filePath = TEST_DATA_FOLDER + "CsvFileStorageTestActualBevy.csv";
        AddressBook original = getTypicalAddressBook();
        CsvFileStorage csvFileStorage = new CsvFileStorage(filePath);

        // Saves in new file and assert file contents.
        csvFileStorage.saveToCsvFile(original, filePath);
        List<String> expectedFileContent = Files.readAllLines(Paths.get(
                TEST_DATA_FOLDER + "CsvFileStorageTestExpectedBevy1.csv"));
        List<String> actualFileContent = Files.readAllLines(Paths.get(filePath));
        assertEquals(expectedFileContent, actualFileContent);

        // Modify data by adding and removing same person, save and
        // overwrite existing file, and assert file contents.
        List<ReadOnlyPerson> keys = new ArrayList<>();
        keys.add(new Person(HOON));

        original.addPerson(new Person(HOON));
        original.removePersons(keys);
        csvFileStorage.saveToCsvFile(original, filePath);
        actualFileContent = Files.readAllLines(Paths.get(filePath));
        assertEquals(expectedFileContent, actualFileContent);

        // Modify data by adding new person, save and overwrite
        // existing file, and assert file contents.
        original.addPerson(new Person(IDA));
        csvFileStorage.saveToCsvFile(original, filePath);
        expectedFileContent = Files.readAllLines(Paths.get(
                TEST_DATA_FOLDER + "CsvFileStorageTestExpectedBevy2.csv"));
        actualFileContent = Files.readAllLines(Paths.get(filePath));
        assertEquals(expectedFileContent, actualFileContent);
    }

    @Test
    public void saveToCsvFile_nullAddressBook_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveToCsvFile(null, "SomeFile.xml");
    }

    @Test
    public void saveToCsvFile_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveToCsvFile(new AddressBook(), null);
    }

    /**
     * Saves {@code addressBook} at the specified {@code filePath}.
     */
    private void saveToCsvFile(ReadOnlyAddressBook addressBook, String filePath) {
        try {
            new CsvFileStorage(filePath).saveToCsvFile(addressBook);
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the CSV file.", ioe);
        }
    }
}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void getUserPrefsFilePath() {
        assertNotNull(storageManager.getUserPrefsFilePath());
    }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void saveToCsvFile() throws IOException {
        /*
         * Note: This is an integration test that verifies the StorageManager is properly wired to the
         * {@link CsvFileStorage} class.
         * More extensive testing of CsvFileStorage saving is done in {@link CsvFileStorageTest} class.
         */

        // Saves in new file with no filepath specified and asserts file contents.
        AddressBook original = getTypicalAddressBook();
        storageManager.saveToCsvFile(original);
        List<String> expectedFileContent = Files.readAllLines(
                Paths.get(CsvFileStorageTest.TEST_DATA_FOLDER + "StorageManagerTestExpectedBevy.csv"));
        List<String> actualFileContent = Files.readAllLines(Paths.get(storageManager.getCsvFilePath()));
        assertEquals(expectedFileContent, actualFileContent);

        // Saves in new file with filepath specified and asserts file contents.
        storageManager.saveToCsvFile(original, storageManager.getCsvFilePath());
        expectedFileContent = Files.readAllLines(
                Paths.get(CsvFileStorageTest.TEST_DATA_FOLDER + "StorageManagerTestExpectedBevy.csv"));
        actualFileContent = Files.readAllLines(Paths.get(storageManager.getCsvFilePath()));
        assertEquals(expectedFileContent, actualFileContent);
    }

    @Test
    public void getFileStorageFilePath() {
        assertNotNull(storageManager.getCsvFilePath());
    }
```
###### \java\seedu\address\ui\GoogleMapBrowserPanelTest.java
``` java
public class GoogleMapBrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private GoogleMapBrowserPanel googleMapBrowserPanel;
    private GoogleMapBrowserPanelHandle googleMapBrowserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> googleMapBrowserPanel = new GoogleMapBrowserPanel());
        uiPartRule.setUiPart(googleMapBrowserPanel);

        googleMapBrowserPanelHandle = new GoogleMapBrowserPanelHandle(googleMapBrowserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // Default web page.
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, googleMapBrowserPanelHandle.getLoadedUrl());

        String []segment = ALICE.getAddress().value.split("#");

        // Associated web page of a person.
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLEMAP_SEARCH_URL_PREFIX
                + segment[0].replaceAll(" ", "+") + GOOGLEMAP_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(googleMapBrowserPanelHandle);
        assertEquals(expectedPersonUrl, googleMapBrowserPanelHandle.getLoadedUrl());
    }
}
```
###### \java\seedu\address\ui\InstagramBrowserPanelTest.java
``` java
public class InstagramBrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;

    private InstagramBrowserPanel instagramBrowserPanel;
    private InstagramBrowserPanelHandle instagramBrowserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));

        guiRobot.interact(() -> instagramBrowserPanel = new InstagramBrowserPanel());
        uiPartRule.setUiPart(instagramBrowserPanel);

        instagramBrowserPanelHandle = new InstagramBrowserPanelHandle(instagramBrowserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // Default web page.
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, instagramBrowserPanelHandle.getLoadedUrl());

        // Associated web page of a person.
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(INSTAGRAM_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll("\\s+", "") + "/");

        waitUntilBrowserLoaded(instagramBrowserPanelHandle);
        assertEquals(expectedPersonUrl, instagramBrowserPanelHandle.getLoadedUrl());
    }
}
```
###### \java\seedu\address\ui\PersonDetailsPanelTest.java
``` java
public class PersonDetailsPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyPerson> TYPICAL_PERSONS =
            FXCollections.observableList(getTypicalPersons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private PersonListPanelHandle personListPanelHandle;
    private PersonDetailsPanelHandle personDetailsPanelHandle;

    @Before
    public void setUp() {
        PersonListPanel personListPanel = new PersonListPanel(TYPICAL_PERSONS);
        PersonDetailsPanel personDetailsPanel = new PersonDetailsPanel();

        uiPartRule.setUiPart(personDetailsPanel);
        personListPanelHandle = new PersonListPanelHandle(getChildNode(personListPanel.getRoot(),
                PersonListPanelHandle.PERSON_LIST_VIEW_ID));
        personDetailsPanelHandle = new PersonDetailsPanelHandle(getChildNode(personDetailsPanel.getRoot(),
                PersonDetailsPanelHandle.PERSON_DETAILS_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_PERSONS.size(); i++) {
            personListPanelHandle.navigateToCard(TYPICAL_PERSONS.get(i));
            ReadOnlyPerson expectedPerson = TYPICAL_PERSONS.get(i);
            PersonCardHandle actualCard = personListPanelHandle.getPersonCardHandle(i);

            PersonDetailsPanelHandle actualPersonDetailsPanel = personDetailsPanelHandle.getPersonDetailsPanelHandle();
            assertCardDisplaysPerson(expectedPerson, actualCard);

            assertPersonDetailsPanelDisplaysPerson(expectedPerson, actualPersonDetailsPanel);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        PersonCardHandle expectedCard = personListPanelHandle.getPersonCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        PersonCardHandle selectedCard = personListPanelHandle.getHandleToSelectedCard();

        ReadOnlyPerson expectedPerson = TYPICAL_PERSONS.get(INDEX_SECOND_PERSON.getZeroBased());
        PersonDetailsPanelHandle actualPersonDetailsPanel = personDetailsPanelHandle.getPersonDetailsPanelHandle();

        assertCardEquals(expectedCard, selectedCard);
        assertPersonDetailsPanelDisplaysPerson(expectedPerson, actualPersonDetailsPanel);


    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualPersonDetailsPanel} is empty
     */
    public static void assertEmptyPersonDetailsPanel(PersonDetailsPanelHandle actualPersonDetailsPanel) {
        assertEquals("", actualPersonDetailsPanel.getName());
        assertEquals("", actualPersonDetailsPanel.getPhone());
        assertEquals("", actualPersonDetailsPanel.getBirthday());
        assertEquals("", actualPersonDetailsPanel.getEmail());
        assertEquals("", actualPersonDetailsPanel.getAddress());
        assertEquals("", actualPersonDetailsPanel.getPostalCode());
    }

    /**
     * Asserts that {@code actualPersonDetailsPanel} displays the details of {@code expectedPersonDetailsPanel}
     */
    public static void assertPersonDetailsPanelDisplaysPerson(ReadOnlyPerson expectedPerson,
                                                  PersonDetailsPanelHandle actualPersonDetailsPanel) {
        assertEquals(expectedPerson.getName().fullName, actualPersonDetailsPanel.getName());
        assertEquals(expectedPerson.getPhone().value, actualPersonDetailsPanel.getPhone());
        assertEquals(expectedPerson.getBirthday().value, actualPersonDetailsPanel.getBirthday());
        assertEquals(expectedPerson.getEmail().value, actualPersonDetailsPanel.getEmail());
        assertEquals(expectedPerson.getAddress().value, actualPersonDetailsPanel.getAddress());
        assertEquals(expectedPerson.getPostalCode().value, actualPersonDetailsPanel.getPostalCode());
    }
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Executes {@code handle} in the application's {@code MainMenu}
     * Method returns after UI components have been updated.
     */
    protected void executeHandle(String handle) {
        rememberStates();

        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        if (homePanel.equals(handle)) {
            Platform.runLater(() -> testApp.getMainWindow().handleHome());
        } else if (helpPanel.equals(handle)) {
            Platform.runLater(() -> testApp.getMainWindow().handleHelp());
        } else if (birthdayStatisticsPanel.equals(handle)) {
            Platform.runLater(() -> testApp.getMainWindow().handleBirthdayStatistics());
        } else if (tagStatisticsPanel.equals(handle)) {
            Platform.runLater(() -> testApp.getMainWindow().handleTagStatistics());
        }

        try {
            Thread.sleep(250);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }
```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    /**
     * Asserts that the information panel shows correct panel.
     */
    protected void assertInformationPanelShowsCorrectPanel(String expectedInformationPanelId) {
        assertEquals(expectedInformationPanelId, getCurrentInformationPanel());
    }

    /**
     * Asserts that the theme before changing is not the same as the expected theme.
      */
    protected void assertThemeBeforeChangingNotSame(String expectedThemeAllPaths) {
        assertNotEquals(expectedThemeAllPaths, getCurrentStyleSheet());
    }

    /**
     * Asserts that the theme after changing is the same as the expected theme.
      */
    protected void assertThemeAfterChangingSame(String expectedThemeAllPaths) {
        assertEquals(expectedThemeAllPaths, getCurrentStyleSheet());
    }
```
###### \java\systemtests\ChangeInformationPanelSystemTest.java
``` java
public class ChangeInformationPanelSystemTest extends AddressBookSystemTest {

    @Test
    public void changeInformationPanel() {
        final String homePanelId = "[StackPane[id=homePanel]]";
        final String personInformationPanelId = "[SplitPane[id=personInformationPanel, styleClass=split-pane]]";
        final String helpPanelId = "[StackPane[id=helpPanel]]";
        final String birthdayStatisticsPanelId = "[StackPane[id=birthdayStatisticsPanel]]";
        final String tagStatisticsPanelId = "[StackPane[id=tagStatisticsPanel]]";

        assertHandleSuccess("helpPanel", helpPanelId, "");

        assertHandleSuccess("homePanel", homePanelId, "");

        assertHandleSuccess("birthdayStatisticsPanel", birthdayStatisticsPanelId, "");

        assertHandleSuccess("tagStatisticsPanel", tagStatisticsPanelId, "");

        /* Case: Changes information panel of address book using select command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to person information panel.
         */
        String selectCommand = SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased();
        assertCommandSuccess(selectCommand, personInformationPanelId, INDEX_FIRST_PERSON);

        /* Case: Change information panel of address book using help command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to help panel.
         */
        assertCommandSuccess(HelpCommand.COMMAND_WORD, helpPanelId, HelpCommand.MESSAGE_SUCCESS);

        /* Case: Change information panel of address book using home command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> information panel changed to home panel.
        */
        assertCommandSuccess(HomeCommand.COMMAND_WORD, homePanelId, HomeCommand.MESSAGE_SUCCESS);

```
###### \java\systemtests\ChangeThemeSystemTest.java
``` java
public class ChangeThemeSystemTest extends AddressBookSystemTest {

    @Test
    public void changeTheme() {
        String brightThemePath = TestApp.class.getResource(FXML_FILE_FOLDER + "BrightTheme.css").toString();
        String darkThemePath = TestApp.class.getResource(FXML_FILE_FOLDER + "DarkTheme.css").toString();
        String extensionsPath = TestApp.class.getResource(FXML_FILE_FOLDER + "Extensions.css").toString();

        final String expectedBrightThemeAllPaths = "[" + extensionsPath + ", " + brightThemePath + "]";
        final String expectedDarkThemeAllPaths = "[" + extensionsPath + ", " + darkThemePath + "]";

        /* Case: Change theme of non-empty address book using command word, no leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to dark theme
         */
        assertCommandSuccess(ThemeCommand.COMMAND_WORD, expectedDarkThemeAllPaths);

        /* Case: Change theme of non-empty address book using command word, with leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to bright theme
         */
        assertCommandSuccess("   " + ThemeCommand.COMMAND_WORD + " 434@$   ", expectedBrightThemeAllPaths);

        /* Case: Change theme of non-empty address book using command alias, no leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to dark theme
         */
        assertCommandSuccess(ThemeCommand.COMMAND_ALIAS, expectedDarkThemeAllPaths);

        /* Case: Change theme of non-empty address book using command alias, with leading spaces
         * and trailing alphanumeric characters and spaces -> theme changed to bright theme
         */
        assertCommandSuccess("  " + ThemeCommand.COMMAND_ALIAS + " 2#2 ", expectedBrightThemeAllPaths);

        /* Case: Mixed case command word -> rejected */
        assertCommandFailure("ChaNgEtHeme", MESSAGE_UNKNOWN_COMMAND);

        /* Case: Mixed case command alias -> rejected */
        assertCommandFailure("Ct", MESSAGE_UNKNOWN_COMMAND);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays {@code ClearCommand#MESSAGE_SUCCESS} and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar's sync status changes.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(String command, String expectedThemeAllPaths) {
        assertCommandSuccess(command, expectedThemeAllPaths, ThemeCommand.MESSAGE_SUCCESS, getModel());
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String)} except that the result box displays
     * {@code expectedResultMessage} and the model related components equal to {@code expectedModel}.
     * @see ChangeThemeSystemTest#assertCommandSuccess(String, String)
     */
    private void assertCommandSuccess(String command, String expectedThemeAllPaths,
                                      String expectedResultMessage, Model expectedModel) {
        assertThemeBeforeChangingNotSame(expectedThemeAllPaths);
        executeCommand(command);
        assertThemeAfterChangingSame(expectedThemeAllPaths);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchanged();
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
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
###### \java\systemtests\DeleteCommandSystemTest.java
``` java
    @Test
    public void delete() {
        /* ----------------- Performing delete operation while an unfiltered list is being shown -------------------- */

        /* Case: delete the first person in the list, command with leading spaces and trailing spaces -> deleted. */
        Model expectedModel = getModel();
        String command = "     " + DeleteCommand.COMMAND_WORD + "      " + INDEX_FIRST_PERSON.getOneBased() + "       ";
        List<ReadOnlyPerson> deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON);
        HashSet<Integer> zeroBasedIndexes = new HashSet<>();
        zeroBasedIndexes.add(INDEX_FIRST_PERSON.getZeroBased());

        String expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson.size(),
                StringUtil.toIndexedListString(zeroBasedIndexes, deletedPerson));
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        deletedPerson.clear();
        zeroBasedIndexes.clear();

        /* Case: delete multiple person in the list, without overlapping range -> deleted. */
        Model modelBeforeDeletingMultiple = getModel();
        command = DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "-"
                + INDEX_SECOND_PERSON.getOneBased() + " " + INDEX_THIRD_PERSON.getOneBased() + "-"
                + INDEX_FORTH_PERSON.getOneBased();
        deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON, INDEX_SECOND_PERSON,
                INDEX_THIRD_PERSON, INDEX_FORTH_PERSON);
        zeroBasedIndexes.add(INDEX_FIRST_PERSON.getZeroBased());
        zeroBasedIndexes.add(INDEX_SECOND_PERSON.getZeroBased());
        zeroBasedIndexes.add(INDEX_THIRD_PERSON.getZeroBased());
        zeroBasedIndexes.add(INDEX_FORTH_PERSON.getZeroBased());

        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson.size(),
                StringUtil.toIndexedListString(zeroBasedIndexes, deletedPerson));
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        deletedPerson.clear();
        zeroBasedIndexes.clear();

        /* Case: undo deleting multiple persons in the list, without overlap -> multiple persons restored. */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingMultiple, expectedResultMessage);

        /* Case: delete multiple person in the list, with overlapping range -> deleted. */
        expectedModel = getModel();
        command = DeleteCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased() + "-"
                + INDEX_SECOND_PERSON.getOneBased() + " " + INDEX_SECOND_PERSON.getOneBased() + "-"
                + INDEX_THIRD_PERSON.getOneBased();
        deletedPerson = removePerson(expectedModel, INDEX_FIRST_PERSON, INDEX_SECOND_PERSON, INDEX_THIRD_PERSON);
        zeroBasedIndexes.add(INDEX_FIRST_PERSON.getZeroBased());
        zeroBasedIndexes.add(INDEX_SECOND_PERSON.getZeroBased());
        zeroBasedIndexes.add(INDEX_THIRD_PERSON.getZeroBased());

        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson.size(),
                StringUtil.toIndexedListString(zeroBasedIndexes, deletedPerson));
        assertCommandSuccess(command, expectedModel, expectedResultMessage);
        deletedPerson.clear();
        zeroBasedIndexes.clear();

        /* Case: undo deleting multiple persons in the list, with overlap -> multiple persons restored. */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingMultiple, expectedResultMessage);

        /* Case: delete the last person in the list -> deleted. */
        Model modelBeforeDeletingLast = getModel();
        Index lastPersonIndex = getLastIndex(modelBeforeDeletingLast);
        assertCommandSuccess(lastPersonIndex);

        /* Case: undo deleting the last person in the list -> last person restored. */
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: redo deleting the last person in the list -> last person deleted again. */
        command = RedoCommand.COMMAND_WORD;
        removePerson(modelBeforeDeletingLast, lastPersonIndex);
        expectedResultMessage = RedoCommand.MESSAGE_SUCCESS;
        assertCommandSuccess(command, modelBeforeDeletingLast, expectedResultMessage);

        /* Case: delete the middle person in the list -> deleted. */
        Index middlePersonIndex = getMidIndex(getModel());
        assertCommandSuccess(middlePersonIndex);

        /* ------------------ Performing delete operation while a filtered list is being shown ---------------------- */

        /* Case: filtered person list, delete index within bounds of address book and person list -> deleted. */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        Index index = INDEX_FIRST_PERSON;
        assertTrue(index.getZeroBased() < getModel().getFilteredPersonList().size());
        assertCommandSuccess(index);

        /* Case: filtered person list, delete index within bounds of address book but out of bounds of person list
         * -> rejected.
         */
        showPersonsWithName(KEYWORD_MATCHING_MEIER);
        int invalidIndex = getModel().getAddressBook().getPersonList().size();
        command = DeleteCommand.COMMAND_WORD + " " + invalidIndex;
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* --------------------- Performing delete operation while a person card is selected ------------------------ */

        /* Case: delete the selected person -> person list panel selects the person before the deleted person. */
        showAllPersons();
        expectedModel = getModel();
        Index selectedIndex = getLastIndex(expectedModel);
        Index expectedIndex = Index.fromZeroBased(selectedIndex.getZeroBased() - 1);
        selectPerson(selectedIndex);
        command = DeleteCommand.COMMAND_WORD + " " + selectedIndex.getOneBased();
        deletedPerson = removePerson(expectedModel, selectedIndex);
        zeroBasedIndexes.add(selectedIndex.getZeroBased());

        expectedResultMessage = String.format(MESSAGE_DELETE_PERSON_SUCCESS, deletedPerson.size(),
                StringUtil.toIndexedListString(zeroBasedIndexes, deletedPerson));
        assertCommandSuccess(command, expectedModel, expectedResultMessage, expectedIndex);
        deletedPerson.clear();
        zeroBasedIndexes.clear();

        /* --------------------------------- Performing invalid delete operation ------------------------------------ */

        /* Case: invalid index (0) -> rejected. */
        command = DeleteCommand.COMMAND_WORD + " 0";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (-1) -> rejected. */
        command = DeleteCommand.COMMAND_WORD + " -1";
        assertCommandFailure(command, MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid index (size + 1) -> rejected. */
        Index outOfBoundsIndex = Index.fromOneBased(
                getModel().getAddressBook().getPersonList().size() + 1);
        command = DeleteCommand.COMMAND_WORD + " " + outOfBoundsIndex.getOneBased();
        assertCommandFailure(command, MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);

        /* Case: invalid arguments (alphabets) -> rejected. */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: invalid arguments (extra argument) -> rejected. */
        assertCommandFailure(DeleteCommand.COMMAND_WORD + " 1 abc", MESSAGE_INVALID_DELETE_COMMAND_FORMAT);

        /* Case: mixed case command word -> rejected. */
        assertCommandFailure("DelETE 1", MESSAGE_UNKNOWN_COMMAND);
    }
```
