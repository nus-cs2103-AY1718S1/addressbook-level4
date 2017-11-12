# DarrenCzen
###### \java\guitests\guihandles\EventsDetailsPanelHandle.java
``` java
/**
 * A handle to the {@code EventsDetailsPanel} of the application.
 */
public class EventsDetailsPanelHandle extends NodeHandle<Node> {
    public static final String EVENTS_DETAILS_PANEL_ID = "#eventsDetailsPanelPlaceholder";

    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String DATE_FIELD_ID = "#date";
    private static final String ADDRESS_FIELD_FIELD_ID = "#addressField";
    private static final String DATE_FIELD_FIELD_ID = "#dateField";

    private final Label nameLabel;
    private final Label addressLabel;
    private final Label dateLabel;

    private final Text addressText;
    private final Text dateText;

    private String latestName;
    private String latestAddress;
    private String latestDate;

    public EventsDetailsPanelHandle(Node eventsDetailsPanelNode) {
        super(eventsDetailsPanelNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);

        this.addressText = getChildNode(ADDRESS_FIELD_FIELD_ID);
        this.dateText = getChildNode(DATE_FIELD_FIELD_ID);
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getAddressField() {
        return addressText.getText();
    }

    public String getDateField () {
        return dateText.getText();
    }

```
###### \java\seedu\address\logic\commands\AccessCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code AccessCommand}.
 */
public class AccessCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private Model onePersonModel;
    private AccessCommand accessCommandOne;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        onePersonModel = new ModelManager(getOnePersonAddressBook(), new UserPrefs());
        accessCommandOne = new AccessCommand(INDEX_FIRST_PERSON);
        accessCommandOne.setData(onePersonModel, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_invalidWebsite_failure() {
        try {
            accessCommandOne.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(Messages.MESSAGE_INVALID_WEBSITE, ce.getMessage());
        }

    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        AccessCommand accessFirstPersonCommand = new AccessCommand(INDEX_FIRST_PERSON);
        AccessCommand accessSecondPersonCommand = new AccessCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(accessFirstPersonCommand.equals(accessFirstPersonCommand));

        // same values -> returns true
        AccessCommand accessFirstCommandCopy = new AccessCommand(INDEX_FIRST_PERSON);
        assertTrue(accessFirstPersonCommand.equals(accessFirstCommandCopy));

        // different types -> returns false
        assertFalse(accessFirstPersonCommand.equals(1));

        // null -> returns false
        assertFalse(accessFirstCommandCopy.equals(null));

        // different person -> returns false
        assertFalse(accessFirstCommandCopy.equals(accessSecondPersonCommand));
    }

    /**
     * Executes a {@code AccessCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        AccessCommand accessCommand = prepareCommand(index);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson person = lastShownList.get(index.getZeroBased());
        String name = person.getName().toString();

        try {
            CommandResult commandResult = accessCommand.execute();
            assertEquals(String.format(accessCommand.MESSAGE_ACCESS_PERSON_SUCCESS, index.getOneBased(), name),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        AccessWebsiteRequestEvent lastEvent = (AccessWebsiteRequestEvent) eventsCollectorRule
                .eventsCollector.getMostRecent();
        assertEquals(person.getWebsite().toString(), lastEvent.website);
    }

    /**
     * Executes a {@code AccessCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        AccessCommand accessCommand = prepareCommand(index);

        try {
            accessCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code AccessCommand} with parameters {@code index}.
     */
    private AccessCommand prepareCommand(Index index) {
        AccessCommand accessCommand = new AccessCommand(index);
        accessCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return accessCommand;
    }

    /**
     * Returns an {@code AddressBook} with people in unsorted names.
     */
    private static AddressBook getOnePersonAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getUnsortedPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    private static List<ReadOnlyPerson> getUnsortedPersons() {
        return new ArrayList<>(Arrays.asList(ERIC));
    }

}
```
###### \java\seedu\address\logic\commands\LocationCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code LocationCommand}.
 */
public class LocationCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private Model onePersonModel;
    private LocationCommand locationCommandOne;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        onePersonModel = new ModelManager(getOnePersonAddressBook(), new UserPrefs());
        locationCommandOne = new LocationCommand(INDEX_FIRST_PERSON);
        locationCommandOne.setData(onePersonModel, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_invalidLocation_failure() {
        try {
            locationCommandOne.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(Messages.MESSAGE_INVALID_LOCATION, ce.getMessage());
        }
    }

    @Test
    public void execute_validIndexUnfilteredList_success() {
        Index lastPersonIndex = Index.fromOneBased(model.getFilteredPersonList().size());

        assertExecutionSuccess(INDEX_FIRST_PERSON);
        assertExecutionSuccess(INDEX_THIRD_PERSON);
        assertExecutionSuccess(lastPersonIndex);
    }

    @Test
    public void execute_invalidIndexUnfilteredList_failure() {
        Index outOfBoundsIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void execute_validIndexFilteredList_success() {
        showFirstPersonOnly(model);

        assertExecutionSuccess(INDEX_FIRST_PERSON);
    }

    @Test
    public void execute_invalidIndexFilteredList_failure() {
        showFirstPersonOnly(model);

        Index outOfBoundsIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundsIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        LocationCommand locateFirstPersonCommand = new LocationCommand(INDEX_FIRST_PERSON);
        LocationCommand locateSecondPersonCommand = new LocationCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(locateFirstPersonCommand.equals(locateFirstPersonCommand));

        // same values -> returns true
        LocationCommand locateFirstCommandCopy = new LocationCommand(INDEX_FIRST_PERSON);
        assertTrue(locateFirstPersonCommand.equals(locateFirstCommandCopy));

        // different types -> returns false
        assertFalse(locateFirstPersonCommand.equals(1));

        // null -> returns false
        assertFalse(locateFirstCommandCopy.equals(null));

        // different person -> returns false
        assertFalse(locateFirstCommandCopy.equals(locateSecondPersonCommand));
    }

    /**
     * Executes a {@code LocationCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        LocationCommand locationCommand = prepareCommand(index);
        List<ReadOnlyPerson> lastShownList = model.getFilteredPersonList();
        ReadOnlyPerson person = lastShownList.get(index.getZeroBased());
        String name = person.getName().toString();

        try {
            CommandResult commandResult = locationCommand.execute();
            assertEquals(String.format(locationCommand.MESSAGE_LOCATE_PERSON_SUCCESS, index.getOneBased(), name),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        AccessLocationRequestEvent lastEvent = (AccessLocationRequestEvent) eventsCollectorRule
                .eventsCollector.getMostRecent();
        assertEquals(person.getAddress().toString(), lastEvent.location);
    }

    /**
     * Executes a {@code LocationCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        LocationCommand locationCommand = prepareCommand(index);

        try {
            locationCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code LocationCommand} with parameters {@code index}.
     */
    private LocationCommand prepareCommand(Index index) {
        LocationCommand locationCommand = new LocationCommand(index);
        locationCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return locationCommand;
    }
    /**
     * Returns an {@code AddressBook} with people in unsorted names.
     */
    private static AddressBook getOnePersonAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getUnsortedPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    private static List<ReadOnlyPerson> getUnsortedPersons() {
        return new ArrayList<>(Arrays.asList(ETHAN));
    }

}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
public class SortCommandTest {

    private Model model;
    private Model expectedModel;
    private SortCommand sortCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getUnsortedAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(getTypicalAddressBook(), new UserPrefs());

        sortCommand = new SortCommand();
        sortCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute() throws CommandException {
        //check whether expectedModel is sorted.
        assertSortSuccessful(expectedModel.getAddressBook(), expectedModel.getFilteredPersonList());

        //execute sort command for model
        assertCommandSuccess(sortCommand, model, SortCommand.MESSAGE_SUCCESS, expectedModel);

        assertSortSuccessful(model.getAddressBook(), model.getFilteredPersonList());

        assertEquals(expectedModel.getFilteredPersonList(), model.getFilteredPersonList());
    }

    private void assertSortSuccessful(ReadOnlyAddressBook addressBook, List<ReadOnlyPerson> peopleList) {
        assertTrue(isAddressBookSorted(addressBook, peopleList));
    }

    /**
     * Returns true or false to see whether {@code AddressBook} is sorted.<br>
     * Asserts that {@code AddressBook} contains all individuals being compared.
     */
    private boolean isAddressBookSorted(ReadOnlyAddressBook addressBook, List<ReadOnlyPerson> peopleList) {
        for (int i = 0; i < peopleList.size() - 1; i++) {
            ReadOnlyPerson person1 = peopleList.get(i);
            ReadOnlyPerson person2 = peopleList.get(i + 1);

            assertTrue(addressBook.getPersonList().contains(person1));
            assertTrue(addressBook.getPersonList().contains(person2));

            //return false when names are incorrectly positioned or unsorted
            if (compareNamesAlphabetically(person1, person2)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Returns false if name of person1 is correctly positioned alphabetically in front of person2.
     */
    private boolean compareNamesAlphabetically(ReadOnlyPerson person1, ReadOnlyPerson person2) {
        String name1 = person1.getName().toString();
        String name2 = person2.getName().toString();

        int compare = name1.compareTo(name2);

        //compare > 0 if for e.g. name1 starts with h and name2 starts with f
        if (compare > 0) {
            return true;
        }
        return false;
    }

    /**
     * Returns an {@code AddressBook} with people in unsorted names.
     */
    private static AddressBook getUnsortedAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getUnsortedPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    private static List<ReadOnlyPerson> getUnsortedPersons() {
        return new ArrayList<>(Arrays.asList(CARL, ALICE, FIONA, DANIEL, ELLE, BENSON, GEORGE));
    }
}
```
###### \java\seedu\address\logic\parser\AccessCommandParserTest.java
``` java
public class AccessCommandParserTest {
    private AccessCommandParser parser = new AccessCommandParser();

    @Test
    public void parse_validArgs_returnsAccessCommand() {
        assertParseSuccess(parser, "1", new AccessCommand(INDEX_FIRST_PERSON));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "b", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AccessCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB + WEBSITE_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB, expectedMessage);

        // missing phone prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + VALID_PHONE_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB + WEBSITE_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB, expectedMessage);

        // missing email prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + VALID_EMAIL_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB, expectedMessage);

        // creates a person with no school email
        Person expectedPersonWithNoSchEmail = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSchEmail(SchEmail.SCH_EMAIL_TEMPORARY)
                .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .build();

        // missing schEmail prefix
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB,
                new AddCommand(expectedPersonWithNoSchEmail));

        // creates a person with no homeNumber
        Person expectedPersonWithNoHomeNumber = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSchEmail(VALID_SCH_EMAIL_BOB)
                .withHomeNumber(HomeNumber.HOME_NUMBER_TEMPORARY).withEmail(VALID_EMAIL_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB).withBirthday(VALID_BIRTHDAY_BOB)
                .build();

        // missing homeNumber prefix
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB,
                new AddCommand(expectedPersonWithNoHomeNumber));

        // creates a person with no birthday
        Person expectedPersonWithNoBirthday = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSchEmail(VALID_SCH_EMAIL_BOB)
                .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withBirthday(Birthday.BIRTHDAY_TEMPORARY)
                .build();

        // missing birthday prefix
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB
                        + PHONE_DESC_BOB + HOME_NUM_DESC_BOB
                        + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                        + WEBSITE_DESC_BOB + ADDRESS_DESC_BOB,
                new AddCommand(expectedPersonWithNoBirthday));

        // creates a person with no website
        Person expectedPersonWithNoWebsite = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSchEmail(VALID_SCH_EMAIL_BOB)
                .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
                .withWebsite(Website.WEBSITE_TEMPORARY).withAddress(VALID_ADDRESS_BOB)
                .withBirthday(VALID_BIRTHDAY_BOB)
                .build();

        // missing website prefix
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + BIRTHDAY_DESC_BOB,
                new AddCommand(expectedPersonWithNoWebsite));

        // creates a person with no address
        Person expectedPersonWithNoAddress = new PersonBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withSchEmail(VALID_SCH_EMAIL_BOB)
                .withHomeNumber(VALID_HOME_NUM_BOB).withEmail(VALID_EMAIL_BOB)
                .withWebsite(VALID_WEBSITE_BOB).withAddress(Address.ADDRESS_TEMPORARY)
                .withBirthday(VALID_BIRTHDAY_BOB)
                .build();

        // missing address prefix
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB
                + HOME_NUM_DESC_BOB + EMAIL_DESC_BOB + SCH_EMAIL_DESC_BOB
                + WEBSITE_DESC_BOB + BIRTHDAY_DESC_BOB,
                new AddCommand(expectedPersonWithNoAddress));

        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_NAME_BOB + VALID_PHONE_BOB
                + VALID_EMAIL_BOB + VALID_SCH_EMAIL_BOB + VALID_WEBSITE_BOB
                + VALID_ADDRESS_BOB + VALID_BIRTHDAY_BOB, expectedMessage);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_access() throws Exception {
        AccessCommand command = (AccessCommand) parser.parseCommand(
                AccessCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new AccessCommand(INDEX_FIRST_PERSON), command);
    }

```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_location() throws Exception {
        LocationCommand command = (LocationCommand) parser.parseCommand(
                LocationCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new LocationCommand(INDEX_FIRST_PERSON), command);
    }

    @Test
    public void parseCommand_sort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
    }

```
###### \java\seedu\address\logic\parser\LocationCommandParserTest.java
``` java
public class LocationCommandParserTest {
    private LocationCommandParser parser = new LocationCommandParser();

    @Test
    public void parse_validArgs_returnsAccessCommand() {
        assertParseSuccess(parser, "1", new LocationCommand(INDEX_FIRST_PERSON));

    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "b", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                LocationCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseWebsite_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseWebsite(null);
    }

    @Test
    public void parseWebsite_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseWebsite(Optional.of(INVALID_WEBSITE));
    }

    @Test
    public void parseWebsite_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseWebsite(Optional.empty()).isPresent());
    }


    @Test
    public void parseWebsite_validValue_returnsWebsite() throws Exception {
        Website expectedWebsite = new Website(VALID_WEBSITE);
        Optional<Website> actualWebsite = ParserUtil.parseWebsite(Optional.of(VALID_WEBSITE));

        assertEquals(expectedWebsite, actualWebsite.get());
    }

```
###### \java\seedu\address\model\person\AddressTest.java
``` java
    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check address field value
        Address addressX = new Address("Blk 456, Den Road, #01-355");
        Address addressY = new Address("Blk 456, Den Road, #01-355");
        assertTrue(addressX.equals(addressY) && addressY.equals(addressX));
        assertTrue(addressX.hashCode() == addressY.hashCode());
    }

```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check birthday field value
        Birthday birthdayX = new Birthday("14/01/1986");
        Birthday birthdayY = new Birthday("14/01/1986");
        assertTrue(birthdayX.equals(birthdayY) && birthdayY.equals(birthdayX));
        assertTrue(birthdayX.hashCode() == birthdayY.hashCode());
    }

```
###### \java\seedu\address\model\person\EmailTest.java
``` java
    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check email field value
        Email emailX = new Email("PeterJack_1190@example.com");
        Email emailY = new Email("PeterJack_1190@example.com");
        assertTrue(emailX.equals(emailY) && emailY.equals(emailX));
        assertTrue(emailX.hashCode() == emailY.hashCode());
    }

```
###### \java\seedu\address\model\person\HomeNumberTest.java
``` java
    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check home number field value
        HomeNumber homeNumberX = new HomeNumber("93121534");
        HomeNumber homeNumberY = new HomeNumber("93121534");
        assertTrue(homeNumberX.equals(homeNumberY) && homeNumberY.equals(homeNumberX));
        assertTrue(homeNumberX.hashCode() == homeNumberY.hashCode());
    }

```
###### \java\seedu\address\model\person\NameTest.java
``` java
    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check name field value
        Name nameX = new Name("Capital Tan");
        Name nameY = new Name("Capital Tan");
        assertTrue(nameX.equals(nameY) && nameY.equals(nameX));
        assertTrue(nameX.hashCode() == nameY.hashCode());
    }

    @Test
    public void testCapitaliseMethod() throws IllegalValueException {
        String name = "peter jack";
        String expectedName = "Peter Jack";
        assertTrue(Name.isValidName(name)); // alphabets only

        String newName = Name.toCapitalized(name);
        assertEquals(newName.toString(), expectedName.toString());
    }
```
###### \java\seedu\address\model\person\PhoneTest.java
``` java
    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check phone field value
        Phone phoneX = new Phone("93121534");
        Phone phoneY = new Phone("93121534");
        assertTrue(phoneX.equals(phoneY) && phoneY.equals(phoneX));
        assertTrue(phoneX.hashCode() == phoneY.hashCode());
    }
}
```
###### \java\seedu\address\model\person\SchEmailTest.java
``` java
    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check school email field value
        SchEmail schEmailX = new SchEmail("PeterJack_1190@example.com");
        SchEmail schEmailY = new SchEmail("PeterJack_1190@example.com");
        assertTrue(schEmailX.equals(schEmailY) && schEmailY.equals(schEmailX));
        assertTrue(schEmailX.hashCode() == schEmailY.hashCode());
    }

```
###### \java\seedu\address\model\person\WebsiteTest.java
``` java
public class WebsiteTest {

    @Test
    public void isValidWebsite() {
        //blank website
        assertFalse(Website.isValidWebsite(""));
        assertFalse(Website.isValidWebsite(" "));

        //broken links with missing parts
        assertFalse(Website.isValidWebsite("wwwhelpcom"));
        assertFalse(Website.isValidWebsite("https://wwwworld"));
        assertFalse(Website.isValidWebsite("https://www.facebookcom"));
        assertFalse(Website.isValidWebsite(".com"));
        assertFalse(Website.isValidWebsite("https://.com"));
        assertFalse(Website.isValidWebsite("https//hadougen.com"));
        assertFalse(Website.isValidWebsite("https://hadougen."));
        assertFalse(Website.isValidWebsite("https://lamba.n"));

        //valid links
        assertTrue(Website.isValidWebsite("https://www.google.com"));
        assertTrue(Website.isValidWebsite("https://google.com"));
        assertTrue(Website.isValidWebsite("https://www.facebook.com/alex"));
        assertTrue(Website.isValidWebsite("https://www.facebook.com/search"));
        assertTrue(Website.isValidWebsite("https://www.google.net"));
        assertTrue(Website.isValidWebsite("https://www.linkedin.com"));
        assertTrue(Website.isValidWebsite("https://www.facebook.net"));

        // website not filled in
        assertTrue(Website.isValidWebsite("NIL"));
    }

    @Test
    public void testSymmetricHashCode() throws IllegalValueException {
        // equals and hashCode check website field value
        Website websiteX = new Website("https://www.facebook.com/alex");
        Website websiteY = new Website("https://www.facebook.com/alex");
        assertTrue(websiteX.equals(websiteY) && websiteY.equals(websiteX));
        assertTrue(websiteX.hashCode() == websiteY.hashCode());
    }
}
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Website} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withWebsite(String website) {
        try {
            ParserUtil.parseWebsite(Optional.of(website)).ifPresent(descriptor::setWebsite);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("website is expected to be unique.");
        }
        return this;
    }

```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Website} of the {@code Person} that we are building.
     */
    public PersonBuilder withWebsite(String website) {
        try {
            this.person.setWebsite(new Website(website));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("website is expected to be unique.");
        }
        return this;
    }

```
###### \java\seedu\address\ui\BrowserPanelTest.java
``` java
public class BrowserPanelTest extends GuiUnitTest {
    private static final String ALICE_WEBSITE = "https://twitter.com/alice";
    private static final String ALICE_ADDRESS = "123, Jurong West Ave 6, #08-111";

    private AccessWebsiteRequestEvent accessWebsiteEventStub;
    private AccessLocationRequestEvent accessLocationEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        accessWebsiteEventStub = new AccessWebsiteRequestEvent(ALICE.getWebsite().toString());
        accessLocationEventStub = new AccessLocationRequestEvent(ALICE.getAddress().toString());

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void displayWebsite() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(accessWebsiteEventStub);
        URL expectedPersonUrl = new URL(ALICE_WEBSITE);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());

        // associated location of a person
        postNow(accessLocationEventStub);
        URL expectedPersonLocation = new URL(GOOGLE_SEARCH_URL_PREFIX + ALICE_ADDRESS.replaceAll(" ", "+")
                + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        // not equals as generated website for inputted address on google maps is different
        assertNotEquals(expectedPersonLocation, browserPanelHandle.getLoadedUrl());
    }
}
```
###### \java\seedu\address\ui\EventsDetailsPanelTest.java
``` java
public class EventsDetailsPanelTest extends GuiUnitTest {
    private static final String MESSAGE_EMPTY_STRING = "";

    private EventPanelSelectionChangedEvent selectionChangedEventStub;

    private EventsDetailsPanel eventsDetailsPanel;
    private EventsDetailsPanelHandle eventsDetailsPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new EventPanelSelectionChangedEvent(new EventCard(ZOUKOUT, 0));

        guiRobot.interact(() -> eventsDetailsPanel = new EventsDetailsPanel());
        uiPartRule.setUiPart(eventsDetailsPanel);

        eventsDetailsPanelHandle = new EventsDetailsPanelHandle(eventsDetailsPanel.getRoot());
    }

    @Test
    public void testDisplay() throws Exception {
        //Assuming nothing has been clicked yet.
        assertEquals(MESSAGE_EMPTY_STRING, eventsDetailsPanelHandle.getName());
        assertEquals(MESSAGE_EMPTY_STRING, eventsDetailsPanelHandle.getDate());
        assertEquals(MESSAGE_EMPTY_STRING, eventsDetailsPanelHandle.getDateField());
        assertEquals(MESSAGE_EMPTY_STRING, eventsDetailsPanelHandle.getAddress());
        assertEquals(MESSAGE_EMPTY_STRING, eventsDetailsPanelHandle.getAddressField());
        eventsDetailsPanelHandle.rememberSelectedEventDetails();

        //Selecting of ZoukOut EventCard.
        postNow(selectionChangedEventStub);
        assertTrue(eventsDetailsPanelHandle.isSelectedEventChanged());
        eventsDetailsPanelHandle.rememberSelectedEventDetails();
    }

    @Test
    public void testEquals() {
        EventsDetailsPanel eventsDetailsPanelX = new EventsDetailsPanel();
        EventsDetailsPanel eventsDetailsPanelY = new EventsDetailsPanel();

        //Both panels are initially blank
        assertTrue(eventsDetailsPanelX.equals(eventsDetailsPanelY));

        //Panel X is loaded with event info while Panel Y is not
        eventsDetailsPanelX.loadEventInfo(ZOUKOUT);
        assertFalse(eventsDetailsPanelX.equals(eventsDetailsPanelY));

        //Panel Y is loaded with same event info
        eventsDetailsPanelY.loadEventInfo(ZOUKOUT);
        assertTrue(eventsDetailsPanelX.equals(eventsDetailsPanelY));
        assertTrue(eventsDetailsPanelX.equals(eventsDetailsPanelX));
        assertFalse(eventsDetailsPanelX.equals(""));
    }
}
```
