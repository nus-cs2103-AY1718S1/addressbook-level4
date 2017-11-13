# jacoblipech
###### \java\guitests\AddressBookGuiTest.java
``` java
    protected ExtendedPersonDisplayHandle getExtendedPersonDisplay() {

        return mainWindowHandle.getExtendedPersonDisplay();
    }

```
###### \java\guitests\guihandles\ExtendedPersonDisplayHandle.java
``` java

/**
 * Provides a handle for the information shown in the Extended Person Display.
 */
public class ExtendedPersonDisplayHandle extends NodeHandle<Node> {

    public static final String EXTENDED_PERSON_VIEW_ID = "#extendedPersonDisplayPlaceholder";

    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";

    private final Label nameLabel;
    private final Label phoneLabel;
    private final Label emailLabel;
    private final Label addressLabel;
    private final Label birthdayLabel;

    public ExtendedPersonDisplayHandle(Node cardNode) {
        super(cardNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);

    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getBirthday() {
        return birthdayLabel.getText();
    }

}
```
###### \java\guitests\guihandles\StatusBarFooterHandle.java
``` java
    /**
     * Returns the text of 'total number of people' in contacts of the status bar
     */
    public String getTotalPeople() {
        return totalPeople.getText();
    }

```
###### \java\guitests\HelpWindowTest.java
``` java
        getExtendedPersonDisplay().click();
        getMainMenu().openHelpWindowUsingAccelerator();
        assertHelpWindowOpen();

```
###### \java\seedu\address\logic\commands\AddBirthdayCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for AddBirthdayCommand.
 */

public class AddBirthdayCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_addBirthday_success() throws PersonNotFoundException, IllegalValueException {
        //actual model
        Person editedPerson = new PersonBuilder(model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withBirthday(VALID_BIRTHDAY_AMY).build();
        Birthday toAdd = new Birthday (VALID_BIRTHDAY_AMY);

        AddBirthdayCommand addBirthdayCommand = prepareCommand(INDEX_FIRST_PERSON, toAdd);
        String expectedMessage = String.format(AddBirthdayCommand.MESSAGE_ADD_BIRTHDAY_SUCCESS, toAdd);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), editedPerson);

        assertCommandSuccess(addBirthdayCommand, model, expectedMessage, expectedModel);
    }

    /**
     * Returns an {@code AddBirthdayCommand} with parameter {@code index}
     */
    private AddBirthdayCommand prepareCommand (Index index, Birthday toAdd) {
        AddBirthdayCommand addBirthdayCommand = new AddBirthdayCommand(index, toAdd);
        addBirthdayCommand.setData(model, new CommandHistory(), new UndoRedoStack());

        return addBirthdayCommand;
    }
}
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public void addBirthday (Index targetIndex, Birthday toAdd) throws PersonNotFoundException {
            fail("This method should not be called.");
        }

        @Override
        public Boolean sortPersonByName(ArrayList<ReadOnlyPerson> contactList) {
            fail("This method should not be called.");
            return false;
        }
    }

```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
public class SortCommandTest {

    private Model model;

    @Test
    public void execute_sortEmptyAddressBook_success() {
        model = new ModelManager();
        model.getFilteredPersonList();
        assertCommandSuccess(prepareCommand(model), model, SortCommand.MESSAGE_EMPTY_LIST, model);
    }

    @Test
    public void execute_sortAddressBookByName_success() throws DuplicatePersonException {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        model.addPerson(new PersonBuilder().withName("Alex").build());;
        assertCommandSuccess(prepareCommand(model), model, SortCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void execute_alreadySortedAddressBookByName_success() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        assertCommandSuccess(prepareCommand(model), model, SortCommand.MESSAGE_ALREADY_SORTED, model);
    }

    /**
     * Generates a new {@code SortCommand} which upon execution, sorts the contacts by name in {@code model}.
     */
    private SortCommand prepareCommand(Model model) {
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
}
```
###### \java\seedu\address\logic\parser\AddBirthdayCommandParserTest.java
``` java
public class AddBirthdayCommandParserTest {

    private AddBirthdayCommandParser parser = new AddBirthdayCommandParser();

    @Test
    public void parse_validArgs_returnsBirthdayCommand() throws IllegalValueException {

        Birthday toAdd = new Birthday(VALID_BIRTHDAY_AMY);
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_BIRTHDAY.toString() + VALID_BIRTHDAY_AMY;
        assertParseSuccess(parser, userInput, new AddBirthdayCommand(INDEX_FIRST_PERSON, toAdd));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AddBirthdayCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORDVAR_1.toUpperCase()) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORDVAR_2.toUpperCase() + " 3") instanceof SortCommand);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_birthday() throws Exception {
        final String birthdayName = "24/07/95";
        Birthday toAdd = new Birthday(birthdayName);

        AddBirthdayCommand command = (AddBirthdayCommand) parser.parseCommand(
                AddBirthdayCommand.COMMAND_WORDVAR_1 + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_BIRTHDAY + birthdayName);
        AddBirthdayCommand shortCommand = (AddBirthdayCommand) parser.parseCommand(
                AddBirthdayCommand.COMMAND_WORDVAR_2 + " " + INDEX_FIRST_PERSON.getOneBased() + " "
                        + PREFIX_BIRTHDAY + birthdayName);

        assertEquals(new AddBirthdayCommand(INDEX_FIRST_PERSON, toAdd), command);
        assertEquals(new AddBirthdayCommand(INDEX_FIRST_PERSON, toAdd), shortCommand);
    }

```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    /**
     * Tests if sortPersonByName can return a list of sorted names from an input
     * of names with random orders.
     * @throws Exception
     */
    @Test
    public void sortPersonByName_validSort_success() throws Exception {
        Person inputPerson1 = new PersonBuilder().withName("YING ZHENG").build();
        Person inputPerson2 = new PersonBuilder().withName("JACOB").build();
        Person inputPerson3 = new PersonBuilder().withName("VIVEK").build();
        Person inputPerson4 = new PersonBuilder().withName("JIA SHU").build();

        ArrayList<ReadOnlyPerson> inputPersonList = new ArrayList<>();

        inputPersonList.add(inputPerson1);
        inputPersonList.add(inputPerson2);
        inputPersonList.add(inputPerson3);
        inputPersonList.add(inputPerson4);

        AddressBook inputAddressBook = new AddressBook();
        inputAddressBook.setPersons(inputPersonList);

        ModelManager expectedModel = new ModelManager(inputAddressBook, new UserPrefs());
        inputPersonList.clear();
        expectedModel.sortPersonByName(inputPersonList);
        inputAddressBook.setPersons(inputPersonList);

        ArrayList<ReadOnlyPerson> sortedInputPersonList = new ArrayList<>();

        sortedInputPersonList.add(inputPerson2);
        sortedInputPersonList.add(inputPerson4);
        sortedInputPersonList.add(inputPerson3);
        sortedInputPersonList.add(inputPerson1);

        AddressBook sortedAddressBook = new AddressBook();
        sortedAddressBook.setPersons(sortedInputPersonList);

        ModelManager actualModel = new ModelManager(sortedAddressBook, new UserPrefs());
        assertEquals(expectedModel.getAddressBook().getPersonList(), actualModel.getAddressBook().getPersonList());
    }

```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
public class BirthdayTest {

    @Test
    public void equals() throws IllegalValueException {
        Birthday birthday = new Birthday();
        Birthday birthdayValue = new Birthday("12/04/97");

        // same object -> returns true
        assertTrue(birthday.equals(birthday));
        assertTrue(birthdayValue.equals(birthdayValue));

        // same values -> returns true
        Birthday birthdayCopy = new Birthday();
        Birthday birthdayValueCopy = new Birthday("12/04/97");
        assertTrue(birthday.equals(birthdayCopy));
        assertTrue(birthdayValue.equals(birthdayValueCopy));

        // different types -> returns false
        assertFalse(birthday.equals(true));
        assertFalse(birthdayValue.equals(true));

        // null -> returns false
        assertFalse(birthday == null);
        assertFalse(birthdayValue == null);

        // different values -> returns false
        Birthday differentBirthday = new Birthday("04/04/99");
        assertFalse(birthday.equals(differentBirthday));
        assertFalse(birthdayValue.equals(differentBirthday));
    }

    @Test
    public void isValidBirthday() {

        assertFalse(Birthday.isValidBirthdayFormat("234521893032")); // exceeding number limit
        assertFalse(Birthday.isValidBirthdayFormat("234")); // less than number required
        assertFalse(Birthday.isValidBirthdayFormat("231294")); // incorrect format

        // non integer used
        assertFalse(Birthday.isValidBirthdayFormat("example.com")); // invalid alphabet used
        assertFalse(Birthday.isValidBirthdayFormat(">.<??!")); // invalid random syntax used

        // incorrect day entered
        assertFalse(Birthday.isValidBirthdayFormat("32/05/94")); // day entered more than range
        assertFalse(Birthday.isValidBirthdayFormat("00/05/94")); // day entered less than range

        // incorrect month entered
        assertFalse(Birthday.isValidBirthdayFormat("23/13/94")); // month entered more than range
        assertFalse(Birthday.isValidBirthdayFormat("02/00/94")); // month entered less than range

        // incorrect year entered
        assertFalse(Birthday.isValidBirthdayFormat("23/13/0000")); // month entered more than range

        // valid birthday
        assertTrue(Birthday.isValidBirthdayFormat("04/05/98")); // 6 digits DD/MM/YY
        assertTrue(Birthday.isValidBirthdayFormat("04/05/1998")); // 8 digits DD/MM/YYYY
        assertTrue(Birthday.isValidBirthdayFormat(Birthday.DEFAULT_BIRTHDAY));  // non used

    }

}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withBirthday(String birthday) {
        try {
            ParserUtil.parseBirthday(Optional.of(birthday)).ifPresent(descriptor::setBirthday);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }

```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Birthday} of the {@code Person} that we are building.
     */
    //havent add withBirthday function to the typical persons class being built
    public PersonBuilder withBirthday(String birthday) {
        try {
            this.person.setBirthday(new Birthday(birthday));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("birthday is expected to be unique.");
        }
        return this;
    }

    public Person build() {
        return this.person;
    }

}
```
###### \java\seedu\address\ui\ExtendedPersonDisplayTest.java
``` java
public class ExtendedPersonDisplayTest extends GuiUnitTest {

    private ExtendedPersonDisplay extendedPersonDisplay;
    private ExtendedPersonDisplayHandle extendedPersonDisplayHandle;

    @Before
    public void setUp() {
        try {
            guiRobot.interact(() -> extendedPersonDisplay = new ExtendedPersonDisplay());
            uiPartRule.setUiPart(extendedPersonDisplay);
            extendedPersonDisplayHandle = new ExtendedPersonDisplayHandle(extendedPersonDisplay.getRoot());
        } catch (NullPointerException e) {
            throw new NullPointerException();
        }
    }

    @Test
    public void display() {
        //ReadOnlyPerson personForTesting = ALICE;
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        assertExtendedPersonCardDisplay(ALICE, extendedPersonDisplayHandle);
        //personForTesting = BOB;
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(DANIEL, 3)));
        assertExtendedPersonCardDisplay(DANIEL, extendedPersonDisplayHandle);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        PersonCard personCard = new PersonCard(person, 0);

        // same person, same index -> returns true
        PersonCard copy = new PersonCard(person, 0);
        assertTrue(personCard.equals(copy));

        // same object -> returns true
        assertTrue(personCard.equals(personCard));

        // null -> returns false
        assertFalse(personCard.equals(null));

        // different types -> returns false
        assertFalse(personCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("differentName").build();
        assertFalse(personCard.equals(new PersonCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(personCard.equals(new PersonCard(person, 1)));
    }

    /**
     * Asserts that {@code extendedPersonDisplay} shows the details of {@code expectedPerson} correctly.
     */
    private void assertExtendedPersonCardDisplay(ReadOnlyPerson expectedPerson, ExtendedPersonDisplayHandle
            extendedPersonDisplayHandle) {
        guiRobot.pauseForHuman();

        // verify person details are displayed correctly
        assertExtendedCardDisplaysPerson(expectedPerson, extendedPersonDisplayHandle);
    }
}
```
###### \java\seedu\address\ui\StatusBarFooterTest.java
``` java
    @Before
    public void setUp() {
        StatusBarFooter statusBarFooter = new StatusBarFooter(STUB_SAVE_LOCATION, TOTAL_NUMBER_PEOPLE);
        uiPartRule.setUiPart(statusBarFooter);

        statusBarFooterHandle = new StatusBarFooterHandle(statusBarFooter.getRoot());
    }

    @Test
    public void display() {
        // initial state
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION, SYNC_STATUS_INITIAL, TOTAL_PEOPLE);

        // after address book is updated
        postNow(EVENT_STUB);
        assertStatusBarContent(RELATIVE_PATH + STUB_SAVE_LOCATION,
                String.format(SYNC_STATUS_UPDATED, new Date(injectedClock.millis()).toString()), TOTAL_PEOPLE);
    }

    /**
     * Asserts that the save location matches that of {@code expectedSaveLocation},
     * the expected number of people and the
     * sync status matches that of {@code expectedSyncStatus}.
     */
    private void assertStatusBarContent(String expectedSaveLocation,
                                        String expectedSyncStatus, String expectedTotalPeople) {
        assertEquals(expectedSaveLocation, statusBarFooterHandle.getSaveLocation());
        assertEquals(expectedSyncStatus, statusBarFooterHandle.getSyncStatus());
        assertEquals(expectedTotalPeople, statusBarFooterHandle.getTotalPeople());
        guiRobot.pauseForHuman();
    }

}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code extendedCard} displays the details of {@code expectedPerson}.
     */
    public static void assertExtendedCardDisplaysPerson(ReadOnlyPerson expectedPerson, ExtendedPersonDisplayHandle
            extendedCard) {
        assertEquals(expectedPerson.getName().fullName, extendedCard.getName());
        assertEquals(expectedPerson.getPhone().value, extendedCard.getPhone());
        assertEquals(expectedPerson.getEmail().value, extendedCard.getEmail());
        assertEquals(expectedPerson.getAddress().value, extendedCard.getAddress());
        assertEquals(expectedPerson.getBirthday().toString(), extendedCard.getBirthday());
    }

```
###### \java\systemtests\AddressBookSystemTest.java
``` java
    public ExtendedPersonDisplayHandle getExtendedPersonDisplay() {

        return mainWindowHandle.getExtendedPersonDisplay();
    }

```
