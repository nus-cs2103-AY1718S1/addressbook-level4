# Jeremy
###### \java\seedu\address\commons\core\GuiSettingsTest.java
``` java
public class GuiSettingsTest {

    @Test
    public void testGuiSettingsEquals() {

        GuiSettings guiSettingOne = new GuiSettings();
        GuiSettings guiSettingTwo = new GuiSettings(13.6, 13.6, 0, 0);

        // Same object -> Returns True
        assertTrue(guiSettingOne.equals(guiSettingOne));

        // Same instance different values -> Returns False
        assertFalse(guiSettingOne.equals(guiSettingTwo));

        // Different type -> Returns False
        assertNotNull(guiSettingOne);
        assertFalse(guiSettingOne.equals(1));

    }

}
```
###### \java\seedu\address\commons\core\MessageTest.java
``` java
public class MessageTest {

    @Test
    public void testMessageClass() {
        Messages messageClass = new Messages();

        assertTrue("Unknown command".equals(messageClass.MESSAGE_UNKNOWN_COMMAND));
        assertTrue("Invalid command format! \n%1$s".equals(messageClass.MESSAGE_INVALID_COMMAND_FORMAT));
        assertTrue("The person index provided is invalid".equals(messageClass.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX));
        assertTrue("%1$d persons listed!".equals(messageClass.MESSAGE_PERSONS_LISTED_OVERVIEW));
    }
}
```
###### \java\seedu\address\logic\commands\ListAscendingNameCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListAscendingNameCommand.
 */
public class ListAscendingNameCommandTest {
    private Model model;
    private Model expectedModel;
    private ListAscendingNameCommand listUnderTestCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listUnderTestCommand = new ListAscendingNameCommand();
        listUnderTestCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeFilterList() {
        expectedModel.listNameAscending();
        assertCommandSuccess(listUnderTestCommand, model, ListAscendingNameCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \java\seedu\address\logic\commands\ListByTagCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code ListByTagCommand}.
 */
public class ListByTagCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void equals() {
        TagContainsKeywordsPredicate firstPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("Family"));
        TagContainsKeywordsPredicate secondPredicate =
                new TagContainsKeywordsPredicate(Collections.singletonList("Friends"));

        ListByTagCommand findFirstCommand = new ListByTagCommand(firstPredicate);
        ListByTagCommand findSecondCommand = new ListByTagCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        ListByTagCommand findFirstCommandCopy = new ListByTagCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null
        assertNotNull(findFirstCommand);

        // different person -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private ListByTagCommand prepareCommand(String userInput) {
        ListByTagCommand command =
                new ListByTagCommand(new TagContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    @Test
    public void executeZeroKeywordsNoPersonFound() {
        String expectedMessage = String.format(ListByTagCommand.MESSAGE_SUCCESS);
        ListByTagCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void executeMultipleKeywordsMultiplePersonsFound() {
        String expectedMessage = String.format(ListByTagCommand.MESSAGE_SUCCESS);
        ListByTagCommand command = prepareCommand("Test1 Test2 Test3");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(CARL, ELLE, FIONA));
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     * - the command feedback is equal to {@code expectedMessage}<br>
     * - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     * - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(ListByTagCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }

}
```
###### \java\seedu\address\logic\commands\ListDescendingNameCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListDescendingNameCommand.
 */
public class ListDescendingNameCommandTest {
    private Model model;
    private Model expectedModel;
    private ListDescendingNameCommand listUnderTestCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listUnderTestCommand = new ListDescendingNameCommand();
        listUnderTestCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeFilterList() {
        expectedModel.listNameDescending();
        assertCommandSuccess(listUnderTestCommand, model, ListDescendingNameCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
```
###### \java\seedu\address\logic\commands\ListIntegrationTest.java
``` java
public class ListIntegrationTest {
    private Model model;
    private Model expectedModel;

    @Test
    public void executeIntegrationTest() {

        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        Name expectedFirstName;
        Name expectedLastName;
        Name actualFirstName;
        Name actualLastName;
        int listLength;

        //Ascending Reverse
        expectedModel.listNameDescending();
        model.listNameAscending();
        model.listNameReversed();
        expectedFirstName = expectedModel.getFilteredPersonList().get(0).getName();
        actualFirstName = model.getFilteredPersonList().get(0).getName();
        listLength = expectedModel.getFilteredPersonList().size();
        expectedLastName = expectedModel.getFilteredPersonList().get(listLength - 1).getName();
        assertEquals(expectedFirstName, actualFirstName);
        assertTrue(expectedFirstName.equals(actualFirstName));
        assertNotNull(actualFirstName);
        assertNotNull(expectedFirstName);
        assertFalse(actualFirstName.equals(expectedLastName));

        //Descending Reverse
        expectedModel.listNameAscending();
        model.listNameDescending();
        model.listNameReversed();
        expectedFirstName = expectedModel.getFilteredPersonList().get(0).getName();
        actualFirstName = model.getFilteredPersonList().get(0).getName();
        listLength = expectedModel.getFilteredPersonList().size();
        expectedLastName = expectedModel.getFilteredPersonList().get(listLength - 1).getName();
        assertEquals(expectedFirstName, actualFirstName);
        assertTrue(expectedFirstName.equals(actualFirstName));
        assertNotNull(actualFirstName);
        assertNotNull(expectedFirstName);
        assertFalse(actualFirstName.equals(expectedLastName));

        // (Descending vs Ascending) w Reverse
        expectedModel.listNameDescending();
        expectedModel.listNameReversed();
        expectedModel.listNameReversed();
        model.listNameAscending();
        model.listNameReversed();
        model.listNameReversed();
        listLength = expectedModel.getFilteredPersonList().size();
        actualFirstName = model.getFilteredPersonList().get(0).getName();
        actualLastName = model.getFilteredPersonList().get(listLength - 1).getName();
        expectedLastName = expectedModel.getFilteredPersonList().get(listLength - 1).getName();
        expectedFirstName = expectedModel.getFilteredPersonList().get(0).getName();
        assertFalse(actualFirstName.equals(expectedFirstName));
        assertFalse(actualLastName.equals(expectedLastName));
        assertEquals(actualFirstName, expectedLastName);
        assertEquals(actualLastName, expectedFirstName);
        model.listNameReversed();
        actualFirstName = model.getFilteredPersonList().get(0).getName();
        expectedLastName = expectedModel.getFilteredPersonList().get(listLength - 1).getName();
        expectedFirstName = expectedModel.getFilteredPersonList().get(0).getName();
        assertEquals(expectedFirstName, actualFirstName);
        assertTrue(expectedFirstName.equals(actualFirstName));
        assertNotNull(actualFirstName);
        assertNotNull(expectedFirstName);
        assertFalse(actualFirstName.equals(expectedLastName));

    }

}
```
###### \java\seedu\address\logic\commands\ListReverseCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for ListReverseCommand.
 */
public class ListReverseCommandTest {
    private Model model;
    private Model expectedModel;
    private ListReverseCommand listUnderTestCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listUnderTestCommand = new ListReverseCommand();
        listUnderTestCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void executeFilterList() {
        expectedModel.listNameReversed();
        assertCommandSuccess(listUnderTestCommand, model, ListReverseCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
public class RemarkCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void executeValidRemarkAddSuccess() throws Exception {

        Person personToRemark = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withRemark("Remark To Add").build();

        RemarkCommand remarkCommand = getRemarkCommandForPerson(INDEX_FIRST_PERSON, personToRemark.getRemark().value);

        String expectedMessage = String.format(remarkCommand.MESSAGE_REMARK_PERSON_SUCCESS, personToRemark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personToRemark);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidRemarkDeleteSuccess() throws Exception {

        Person personToRemark = new PersonBuilder(model.getFilteredPersonList()
                .get(INDEX_FIRST_PERSON.getZeroBased())).withRemark("").build();

        RemarkCommand remarkCommand = getRemarkCommandForPerson(INDEX_FIRST_PERSON, personToRemark.getRemark().value);

        String expectedMessage = String.format(remarkCommand.MESSAGE_REMOVE_REMARK_SUCCESS, personToRemark);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personToRemark);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexFilteredListEditRemarksSuccess() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToRemark).withRemark("Test").build();
        RemarkCommand remarkCommand = getRemarkCommandForPerson(INDEX_FIRST_PERSON,
                editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMARK_PERSON_SUCCESS, personToRemark);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personToRemark);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeValidIndexFilteredListDeleteRemarksSuccess() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personToRemark = model.getFilteredPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personToRemark).withRemark("").build();
        RemarkCommand remarkCommand = getRemarkCommandForPerson(INDEX_FIRST_PERSON,
                editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_REMOVE_REMARK_SUCCESS, personToRemark);

        Model expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());
        showFirstPersonOnly(expectedModel);
        expectedModel.updatePerson(model.getFilteredPersonList().get(0), personToRemark);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidIndexUnfilteredListThrowsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredPersonList().size() + 1);
        RemarkCommand remarkCommand = getRemarkCommandForPerson(outOfBoundIndex, VALID_REMARK_AMY);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void executeInvalidIndexFilteredListThrowsCommandException() {
        showFirstPersonOnly(model);

        Index outOfBoundIndex = INDEX_SECOND_PERSON;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getPersonList().size());

        RemarkCommand remarkCommand = getRemarkCommandForPerson(outOfBoundIndex, VALID_REMARK_AMY);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {

        // Create RemarkCommand for testing
        // Amy used to test for empty remarks
        // Bob used to test for filled remarks
        RemarkCommand testCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        RemarkCommand testCommandTwo = new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_BOB));

        //Test for same object
        assertTrue(testCommand.equals(testCommand));
        assertTrue(testCommandTwo.equals(testCommandTwo));

        //Test for same values
        assertTrue(testCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY))));
        assertTrue(testCommandTwo.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_BOB))));

        //Test to ensure command is strictly a RemarkCommand
        assertFalse(testCommand.equals(new AddCommand(CARL)));
        assertFalse(testCommand.equals(new ClearCommand()));
        assertFalse(testCommand.equals(new DeleteCommand(INDEX_FIRST_PERSON)));
        assertFalse(testCommand.equals(new HistoryCommand()));
        assertFalse(testCommand.equals(new HelpCommand()));
        assertFalse(testCommand.equals(new RedoCommand()));
        assertFalse(testCommand.equals(new UndoCommand()));
        assertFalse(testCommand.equals(new ListCommand()));
        assertFalse(testCommand.equals(new EditCommand(INDEX_FIRST_PERSON, DESC_AMY)));

        //Test to check for null
        assertFalse(testCommand == null);
        assertFalse(testCommandTwo == null);

        //Test to check different Index returns false
        assertFalse(testCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));
        assertFalse(testCommandTwo.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));

        //Test to check different remarks returns false
        assertFalse(testCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
        assertFalse(testCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

    }


    /**
     * Generates a new RemarkCommand with the remarks of the given person.
     *
     * @param index  of person in list
     * @param remark new remark to record
     */
    private RemarkCommand getRemarkCommandForPerson(Index index, String remark) {
        RemarkCommand command = new RemarkCommand(index, new Remark(remark));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

}
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void executeValidListByTagTest() {
        String listByTagCommand = ListByTagCommand.COMMAND_WORD + " colleagues";
        assertCommandSuccess(listByTagCommand, ListByTagCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listByTagCommand);
        listByTagCommand = ListCommand.COMMAND_ALIAS + " tag";
        assertCommandSuccess(listByTagCommand, ListByTagCommand.MESSAGE_SUCCESS, model);


        //Existing feature do not check if tag is present or not.
        // Potential enhancement to account for this
        listByTagCommand = ListByTagCommand.COMMAND_WORD + " invalidTag";
        assertCommandSuccess(listByTagCommand, ListByTagCommand.MESSAGE_SUCCESS, model);

    }

    @Test
    public void executeInvalidListByTagTest() {
        String listByTagCommand = ListCommand.COMMAND_ALIAS + " t";
        assertCommandSuccess(listByTagCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        assertHistoryCorrect(listByTagCommand);
        listByTagCommand = ListCommand.COMMAND_ALIAS + " colleagues";
        assertCommandSuccess(listByTagCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listByTagCommand = ListCommand.COMMAND_WORD + " colleagues tag";
        assertCommandSuccess(listByTagCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listByTagCommand = ListCommand.COMMAND_ALIAS + " tags";
        assertCommandSuccess(listByTagCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listByTagCommand = ListCommand.COMMAND_ALIAS + " t/colleagues";
        assertCommandSuccess(listByTagCommand, ListFailureCommand.MESSAGE_FAILURE, model);
    }

    @Test
    public void executeValidAscendingListTest() {
        String listAscendingCommand = ListAscendingNameCommand.COMPILED_COMMAND;
        assertCommandSuccess(listAscendingCommand, ListAscendingNameCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listAscendingCommand);
        listAscendingCommand = ListAscendingNameCommand.COMPILED_SHORTHAND_COMMAND;
        assertCommandSuccess(listAscendingCommand, ListAscendingNameCommand.MESSAGE_SUCCESS, model);
        listAscendingCommand = ListCommand.COMMAND_ALIAS + " " + ListAscendingNameCommand.COMMAND_ALIAS;
        assertCommandSuccess(listAscendingCommand, ListAscendingNameCommand.MESSAGE_SUCCESS, model);
        listAscendingCommand = ListCommand.COMMAND_ALIAS + " " + ListAscendingNameCommand.COMMAND_WORD;
        assertCommandSuccess(listAscendingCommand, ListAscendingNameCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void executeInvalidAscendingListTest() {
        String listAscendingCommand = ListAscendingNameCommand.COMPILED_COMMAND + " test";
        assertCommandSuccess(listAscendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        assertHistoryCorrect(listAscendingCommand);
        listAscendingCommand = ListAscendingNameCommand.COMPILED_SHORTHAND_COMMAND + " test";
        assertCommandSuccess(listAscendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listAscendingCommand = ListCommand.COMMAND_ALIAS + " " + ListAscendingNameCommand.COMMAND_ALIAS + " test";
        assertCommandSuccess(listAscendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listAscendingCommand = ListCommand.COMMAND_ALIAS + " " + ListAscendingNameCommand.COMMAND_WORD + " test";
        assertCommandSuccess(listAscendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
    }

    @Test
    public void executeValidDescendingListTest() {
        String listDescendingCommand = ListDescendingNameCommand.COMPILED_COMMAND;
        assertCommandSuccess(listDescendingCommand, ListDescendingNameCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(listDescendingCommand);
        listDescendingCommand = ListDescendingNameCommand.COMPILED_SHORTHAND_COMMAND;
        assertCommandSuccess(listDescendingCommand, ListDescendingNameCommand.MESSAGE_SUCCESS, model);
        listDescendingCommand = ListCommand.COMMAND_ALIAS + " " + ListDescendingNameCommand.COMMAND_ALIAS;
        assertCommandSuccess(listDescendingCommand, ListDescendingNameCommand.MESSAGE_SUCCESS, model);
        listDescendingCommand = ListCommand.COMMAND_ALIAS + " " + ListDescendingNameCommand.COMMAND_WORD;
        assertCommandSuccess(listDescendingCommand, ListDescendingNameCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void executeInvalidDescendingListTest() {
        String listDescendingCommand = ListDescendingNameCommand.COMPILED_COMMAND + " test";
        assertCommandSuccess(listDescendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        assertHistoryCorrect(listDescendingCommand);
        listDescendingCommand = ListDescendingNameCommand.COMPILED_SHORTHAND_COMMAND + " test";
        assertCommandSuccess(listDescendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listDescendingCommand = ListCommand.COMMAND_ALIAS + " " + ListDescendingNameCommand.COMMAND_ALIAS + " test";
        assertCommandSuccess(listDescendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        listDescendingCommand = ListCommand.COMMAND_ALIAS + " " + ListDescendingNameCommand.COMMAND_WORD + " test";
        assertCommandSuccess(listDescendingCommand, ListFailureCommand.MESSAGE_FAILURE, model);
    }

    @Test
    public void executeValidReverseListTest() {
        String reverseCommand = ListReverseCommand.COMPILED_COMMAND;
        assertCommandSuccess(reverseCommand, ListReverseCommand.MESSAGE_SUCCESS, model);
        assertHistoryCorrect(reverseCommand);
        reverseCommand = ListReverseCommand.COMPILED_SHORTHAND_COMMAND;
        assertCommandSuccess(reverseCommand, ListReverseCommand.MESSAGE_SUCCESS, model);
        reverseCommand = ListCommand.COMMAND_ALIAS + " " + ListReverseCommand.COMMAND_ALIAS;
        assertCommandSuccess(reverseCommand, ListReverseCommand.MESSAGE_SUCCESS, model);
        reverseCommand = ListCommand.COMMAND_ALIAS + " " + ListReverseCommand.COMMAND_WORD;
        assertCommandSuccess(reverseCommand, ListReverseCommand.MESSAGE_SUCCESS, model);
    }

    @Test
    public void executeInvalidReverseListTest() {
        String reverseCommand = ListReverseCommand.COMPILED_COMMAND + " test";
        assertCommandSuccess(reverseCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        assertHistoryCorrect(reverseCommand);
        reverseCommand = ListReverseCommand.COMPILED_SHORTHAND_COMMAND + " test";
        assertCommandSuccess(reverseCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        reverseCommand = ListCommand.COMMAND_ALIAS + " " + ListReverseCommand.COMMAND_ALIAS + " test";
        assertCommandSuccess(reverseCommand, ListFailureCommand.MESSAGE_FAILURE, model);
        reverseCommand = ListCommand.COMMAND_ALIAS + " " + ListReverseCommand.COMMAND_WORD + " test";
        assertCommandSuccess(reverseCommand, ListFailureCommand.MESSAGE_FAILURE, model);
    }
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        //missing phone
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBloodType(VALID_BLOODTYPE_AMY)
                .withPhone(NON_COMPULSORY_PHONE_AMY).withRemark(VALID_REMARK_AMY).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BLOODTYPE_DESC_AMY + REMARK_DESC_AMY,
                new AddCommand(expectedPerson));

        //missing remark
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBloodType(VALID_BLOODTYPE_AMY)
                .withPhone(NON_COMPULSORY_PHONE_AMY).withRemark(NON_COMPULSORY_REMARK).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                        + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BLOODTYPE_DESC_AMY,
                new AddCommand(expectedPerson));

        //missing bloodtype
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBloodType(NON_COMPULSORY_BLOODTYPE)
                .withPhone(NON_COMPULSORY_PHONE_AMY).withRemark(NON_COMPULSORY_REMARK).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));

        //missing address
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(NON_COMPULSORY_ADDRESS_AMY).withBloodType(NON_COMPULSORY_BLOODTYPE)
                .withPhone(NON_COMPULSORY_PHONE_AMY).withRemark(NON_COMPULSORY_REMARK).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY
                + EMAIL_DESC_AMY, new AddCommand(expectedPerson));

        //missing email
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withEmail(NON_COMPULSORY_EMAIL_AMY)
                .withAddress(NON_COMPULSORY_ADDRESS_AMY).withBloodType(NON_COMPULSORY_BLOODTYPE)
                .withPhone(NON_COMPULSORY_PHONE_AMY).withRemark(NON_COMPULSORY_REMARK).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY, new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandRemarkCommandWordReturnsRemarkCommand() throws Exception {
        //Create RemarkCommand up for testing
        String remark = "Dummy";
        Index index = INDEX_FIRST_PERSON;

        RemarkCommand testRemarkCommand = (RemarkCommand) parser.parseCommand(
                RemarkCommand.COMMAND_WORD + " "
                        + index.getOneBased() + " " + PREFIX_REMARK + remark);

        assertTrue(testRemarkCommand instanceof RemarkCommand);
        assertEquals(new RemarkCommand(index, new Remark(remark)), testRemarkCommand);
        assertNotEquals(new RemarkCommand(index, new Remark("")), testRemarkCommand);
    }
```
###### \java\seedu\address\logic\parser\ListByBloodtypeCommandParserTest.java
``` java
public class ListByBloodtypeCommandParserTest {

    private ListByBloodtypeCommandParser parser = new ListByBloodtypeCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                ListByBloodtypeCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsListByBloodtypeCommand() {
        // no leading and trailing whitespaces
        ListByBloodtypeCommand expectedListByBloodtypeCommand =
                new ListByBloodtypeCommand(new BloodtypeContainsKeywordPredicate(Arrays.asList("AB", "O-")));
        assertParseSuccess(parser, "AB O-", expectedListByBloodtypeCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n AB \n \t O-  \t", expectedListByBloodtypeCommand);
    }

}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseRemarkNullThrowsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseRemark(null);
    }

    @Test
    public void parseRemarkOptionalEmptyReturnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseRemark(Optional.empty()).isPresent());
    }

    @Test
    public void parseRemarkValidValueReturnsRemark() throws Exception {
        Remark expectedRemark = new Remark(VALID_REMARK);
        Optional<Remark> actualRemark = ParserUtil.parseRemark(Optional.of(VALID_REMARK));

        assertEquals(expectedRemark, actualRemark.get());
    }
```
###### \java\seedu\address\logic\parser\RemarkCommandParserTest.java
``` java
public class RemarkCommandParserTest {
    private RemarkCommandParser parser = new RemarkCommandParser();

    @Test
    public void parseEmptyStringInputSuccess() {


        //Define user inputs
        final String emptyRemark = "";
        Index thisIndex = INDEX_FIRST_PERSON;


        String userInput = thisIndex.getOneBased() + " " + PREFIX_REMARK.toString() + emptyRemark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(emptyRemark));
        assertParseSuccess(parser, userInput, expectedCommand);


        // no remarks
        userInput = thisIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseFilledStringInputSuccess() {


        //Define user inputs
        final String emptyRemark = "I love Coffee";
        Index thisIndex = INDEX_FIRST_PERSON;


        String userInput = thisIndex.getOneBased() + " " + PREFIX_REMARK.toString() + emptyRemark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(emptyRemark));
        assertParseSuccess(parser, userInput, expectedCommand);


        // no remarks
        userInput = thisIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseNoIndexInputFailure() {

        final String emptyRemark = "I love Coffee";
        String userInput = PREFIX_REMARK.toString() + emptyRemark;

        String expectedCommand = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedCommand);

    }

    @Test
    public void parseNoPrefixInputFailure() {

        Index thisIndex = INDEX_FIRST_PERSON;
        String userInput = thisIndex.getOneBased() + " ";

        String expectedCommand = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);
        assertParseFailure(parser, userInput, expectedCommand);

    }
}
```
###### \java\seedu\address\logic\parser\ToggleTagColorParserTest.java
``` java
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseTestInputs() throws Exception {

        ToggleTagColorParser parser = new ToggleTagColorParser();

        ToggleTagColorCommand expectedRandom = new ToggleTagColorCommand("random", null);
        ToggleTagColorCommand expectedOff = new ToggleTagColorCommand("off", null);
        ToggleTagColorCommand expectedDefault = new ToggleTagColorCommand("Test", "Test2");


        // Random keyword produces ToggleTagColorCommand(true, "", "")
        assertTrue(parser.parse(parser.getRandomKeyWord()).equals(expectedRandom));

        // Off keyword produces ToggleTagColorCommand (false, "", "")
        assertTrue(parser.parse(parser.getOffKeyWord()).equals(expectedOff));

        // Default case produces ToggleTagColorCommand (true, "args[0]", "args[1]")
        assertTrue(parser.parse("Test Test2").equals(expectedDefault));

        // Throw Parse error
        thrown.expect(ParseException.class);
        parser.parse("Test");
    }
```
###### \java\seedu\address\model\person\PersonTest.java
``` java
public class PersonTest {

    @Test
    public void testTagProperty() {
        Person personOne = new PersonBuilder().withName("Tester").withTags("Friend").build();
        Person personTwo = new PersonBuilder().withName("Jane").withTags("Family").build();

        //Same UniqueTagList -> Returns True
        assertTrue(personOne.tagProperty().equals(personOne.tagProperty()));

        //Different UniqueTagList -> Returns False
        assertFalse(personOne.tagProperty().equals(personTwo.tagProperty()));
    }

    @Test
    public void testPersonHash() {
        Person personOne = new PersonBuilder().withName("Tester").build();
        Person personTwo = new PersonBuilder().withName("Jane").build();

        int firstPersonHash = personOne.hashCode();
        int secondPersonHash = personTwo.hashCode();

        // Same person object has same hash
        assertTrue(firstPersonHash == firstPersonHash);

        // Different person object has different hash
        assertFalse(firstPersonHash == secondPersonHash);
    }


}
```
###### \java\seedu\address\model\person\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void testRemark() {
        Remark emptyRemark = new Remark("");
        Remark emptyRemarkCopy = new Remark(emptyRemark.toString());
        Remark filledRemark = new Remark("Filled Remark");
        Remark filledRemarkCopy = new Remark(filledRemark.toString());

        assertTrue(emptyRemark.equals(emptyRemarkCopy));
        assertTrue(filledRemark.equals(filledRemarkCopy));
        assertFalse(emptyRemark.equals(filledRemark));
        assertFalse(emptyRemarkCopy.equals(filledRemarkCopy));

        assertTrue("".equals(emptyRemark.toString()));
        assertFalse(emptyRemark.toString().equals(0));
        assertFalse(emptyRemark.toString() == null);
        assertFalse("".equals(emptyRemark));

        assertTrue(filledRemark.toString().equals("Filled Remark"));
        assertFalse(filledRemark.toString().equals(0));
        assertFalse(filledRemark.toString() == null);
        assertFalse("".equals(filledRemark));

        int filledRemarkHash = filledRemarkCopy.hashCode();
        int filledRemarkCopyHash = filledRemark.hashCode();
        assertEquals(filledRemarkCopyHash, filledRemarkHash);
    }
}
```
###### \java\seedu\address\model\person\TagContainsKeywordsPredicateTest.java
``` java
public class TagContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("Patient");
        List<String> secondPredicateKeywordList = Arrays.asList("Patient", "Colleague");

        TagContainsKeywordsPredicate firstPredicate = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        TagContainsKeywordsPredicate secondPredicate = new TagContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        TagContainsKeywordsPredicate firstPredicateCopy = new TagContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertNotNull(firstPredicate);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testTagIsPresentReturnsTrue() {
        // One Tag
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.singletonList("Patient"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Patient").build()));

        // Multiple tags
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Colleague", "Family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Colleague", "Family").build()));

        // Only one matching tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Colleague", "Family"));
        assertTrue(predicate.test(new PersonBuilder().withTags("Colleague").build()));
        assertTrue(predicate.test(new PersonBuilder().withTags("Family").build()));

        // Mixed-case keywords
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("FaMiLy"));
        assertTrue(predicate.test(new PersonBuilder().withTags("family").build()));
    }

    @Test
    public void testTagIsNotValidReturnsFalse() {
        // Zero keywords
        TagContainsKeywordsPredicate predicate = new TagContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withTags("Family").build()));

        // Non-matching keyword
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("Family"));
        assertFalse(predicate.test(new PersonBuilder().withTags("Colleague").build()));

        // Keywords match phone, email and address, but does not match tag
        predicate = new TagContainsKeywordsPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street", ""));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").withTags("Family").build()));
    }
}
```
###### \java\seedu\address\model\UniqueTagListTest.java
``` java
    @Test
    public void testThrowDuplicateTagError() throws Exception {
        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.setTags(TypicalPersons.ALICE.getTags());
        thrown.expect(UniqueTagList.DuplicateTagException.class);

        Iterator myIterator = TypicalPersons.ALICE.getTags().iterator();
        uniqueTagList.add((Tag) myIterator.next());

    }
```
###### \java\seedu\address\model\UniqueTagListTest.java
``` java
    @Test
    public void testEquals() {
        UniqueTagList uniqueTagList = new UniqueTagList();
        uniqueTagList.setTags(TypicalPersons.ALICE.getTags());

        UniqueTagList uniqueTagListTwo = new UniqueTagList();
        uniqueTagListTwo.setTags(TypicalPersons.ALICE.getTags());

        UniqueTagList uniqueTagListThree = new UniqueTagList();
        uniqueTagListThree.setTags(TypicalPersons.BOB.getTags());

        // same object -> returns true
        assertTrue(uniqueTagList.equals(uniqueTagList));

        // copy of object -> returns true
        assertTrue(uniqueTagList.equals(uniqueTagListTwo));

        // different types -> returns false
        assertFalse(uniqueTagList.equals(1));

        // null -> returns false
        assertNotNull(uniqueTagList);

        // different sets -> returns false
        assertFalse(uniqueTagList.equals(uniqueTagListThree));
    }
```
###### \java\seedu\address\storage\JsonUserPrefsStorageTest.java
``` java
    @Test
    public void testUserPrefsGetAddressBookName() {
        UserPrefs upUnderTest = getTypicalUserPrefs();
        assertTrue("TypicalAddressBookName".equals(upUnderTest.getAddressBookName()));
    }

    @Test
    public void testUserPrefsEquals() {
        UserPrefs upUnderTest = getTypicalUserPrefs();
        UserPrefs copyOfUpUnderTest = getTypicalUserPrefs();

        //Return true if same object
        assertTrue(upUnderTest.equals(upUnderTest));

        //Return true if same instance
        assertTrue(upUnderTest.equals(copyOfUpUnderTest));

        // different types -> returns false
        assertFalse(upUnderTest.equals(1));

        // null -> returns false
        assertNotNull(upUnderTest);

    }

    @Test
    public void testUserPrefsHashCode() {
        UserPrefs upUnderTest = getTypicalUserPrefs();
        int firstHashCode = upUnderTest.hashCode();

        UserPrefs upUnderTestTwo = new UserPrefs();
        upUnderTestTwo.setGuiSettings(5000, 300, 200, 100);
        upUnderTestTwo.setAddressBookFilePath("addressbookDIFF.xml");
        upUnderTestTwo.setAddressBookName("TypicalAddressBookNameDIFF");
        int secondHashCode = upUnderTestTwo.hashCode();

        UserPrefs upUnderTestThree = getTypicalUserPrefs();
        int thirdHashCode = upUnderTestThree.hashCode();

        // Different user pref generates different hash
        assertFalse(firstHashCode == secondHashCode);

        // Same user pref generates same hash
        assertTrue(firstHashCode == thirdHashCode);
    }
```
###### \java\seedu\address\testutil\EditPersonDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Remark} of the {@code EditPersonDescriptor} that we are building.
     */
    public EditPersonDescriptorBuilder withRemark(String remark) {
        try {
            ParserUtil.parseRemark(Optional.of(remark)).ifPresent(descriptor::setRemark);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("bloodtype is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\ui\CommandBoxTest.java
``` java
    @Test
    public void handleKeyPressEscape() {
        //Command Box text field should contain nothing the first time
        guiRobot.push(KeyCode.ESCAPE);
        assertTrue("".equals(commandBoxHandle.getInput()));

        //Enter text in command box field
        guiRobot.write("Testing input");
        //Confirms that text has been written
        assertTrue("Testing input".equals(commandBoxHandle.getInput()));
        //Ensures that text has not been removed by .getInput method
        assertTrue("Testing input".equals(commandBoxHandle.getInput()));
        //Deletes text and ensure text is reset
        guiRobot.push(KeyCode.ESCAPE);
        assertFalse("Testing input".equals(commandBoxHandle.getInput()));
        assertTrue("".equals(commandBoxHandle.getInput()));
    }


    @Test
    public void handleKeyPressShiftAlt() {
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        //Setting up of sandbox environment for testing
        guiRobot.write("Test");
        assertTrue("Test".equals(mySandBox.getText()));

        assertTrue(mySandBox.getCaretPosition() == commandBoxHandle.getInput().length());
        //Caret shifted left -> Returns true
        guiRobot.push(KeyCode.SHIFT, KeyCode.ALT);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 4);
        assertTrue(mySandBox.getCaretPosition() == 0);
    }


    @Test
    public void handleKeyPressAlt() {
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 1. No input retains Caret position
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 2. Empty spaces only input shifts Caret all the way left
        guiRobot.write("         ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 3. Caret at end of word - Shifts to left side of word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 4, Caret between a word - Gets shifted to the left of current word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("101010101010");
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == 0);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.ALT);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 5, Word followed by space - Shifts caret immediately to beginning of word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("101010");
        guiRobot.write("     ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 6, Spaces followed by words - Shifts caret to beginning of word but after spaces
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("     ");
        guiRobot.write("101010");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 5);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 7, Shortcut works for other non-alphabet strings
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("  (*&^%$ 9876543 <>?:{}|  ");
        assertTrue(mySandBox.getCaretPosition() == 26);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 17);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 9);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 2);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.ALT);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

    }

    @Test
    public void handleKeyPressShiftControl() {
        //Shift-Control shifts the caret all the way right
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        //Setting up of sandbox environment for testing
        guiRobot.write("Test");
        assertTrue("Test".equals(mySandBox.getText()));

        assertTrue(mySandBox.getCaretPosition() == commandBoxHandle.getInput().length());
        //Caret shifted left -> Returns true
        guiRobot.push(KeyCode.SHIFT, KeyCode.ALT);
        //Ensure caret is at the left
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 4);
        assertTrue(mySandBox.getCaretPosition() == 0);
        //Push caret to right
        guiRobot.push(KeyCode.SHIFT, KeyCode.CONTROL);
        //Ensure caret is at the right
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);
        assertTrue(mySandBox.getCaretPosition() == 4);
    }


    @Test
    public void handleKeyPressControl() {
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 1. No input retains Caret position
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 2. Empty spaces only input shifts Caret all the way right
        guiRobot.write("         ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 3. Caret at end of word - Nothing happens
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 4, Caret between a word - Gets shifted to the right of current word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("101010101010");
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == 0);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 5, Word followed by space - Shifts right twice
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("101010");
        guiRobot.write("     ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 6);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 6, Spaces followed by words - Shifts caret end of word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("     ");
        guiRobot.write("101010");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(0);
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Test 7, Shortcut works for other non-alphabet strings
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("  (*&^%$ 9876543 <>?:{}|  ");
        assertTrue(mySandBox.getCaretPosition() == 26);
        mySandBox.positionCaret(0);
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 8);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 16);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 24);
        guiRobot.push(KeyCode.CONTROL);
        assertTrue(mySandBox.getCaretPosition() == 26);

    }


    @Test
    public void handleShiftDeleteTestOne() {
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 1: Test for empty input
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 2a: Test for blank space input caret at most left
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(0);
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 2b: Test for blank space input caret at most right
        mySandBox.positionCaret(mySandBox.getText().length());
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        assertTrue(mySandBox.getText().length() == 0);

        //Test 2c: Test for blank space input caret in the middle
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        assertFalse(mySandBox.getText().length() == 0);

        //Test 3: Test for test word input
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

    }


    @Test
    public void handleShiftDeleteTestTwo() {
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 4a: Test for blank space + test word input caret at most right
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("    ");
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 4b: Test for black space + test word input caret between word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("    ");
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getCaretPosition() - 2);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertFalse(mySandBox.getText().length() == 0);
        assertTrue(mySandBox.getText().length() == 2);

        //Test 5a: Test for word + blank space input, Caret at far right
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 5b: Test for word + blank space input, Caret after word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == 0);
        assertTrue(mySandBox.getCaretPosition() == 4);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        mySandBox.positionCaret(mySandBox.getText().length());
        assertTrue(mySandBox.getCaretPosition() == 4);
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
    }


    @Test
    public void handleShiftBackSpaceTestOne() {
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 1: Test for empty input
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

        //Test 2a: Test for blank space input caret at most left
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(0);
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 2b: Test for blank space input caret at most right
        mySandBox.positionCaret(mySandBox.getText().length());
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        assertTrue(mySandBox.getText().length() == 0);

        //Test 2c: Test for blank space input caret in the middle
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        assertFalse(mySandBox.getText().length() == 0);

        //Test 3: Test for test word input
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);

    }


    @Test
    public void handleShiftBackSpaceTestTwo() {
        TextField mySandBox = commandBoxForTesting.getCommandTextField();

        //Test 4a: Test for blank space + test word input caret at most right
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("    ");
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 4b: Test for black space + test word input caret between word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("    ");
        guiRobot.write("Test");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getCaretPosition() - 2);
        assertFalse(mySandBox.getCaretPosition() == mySandBox.getText().length());
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertFalse(mySandBox.getText().length() == 0);
        assertTrue(mySandBox.getText().length() == 2);

        //Test 5a: Test for word + blank space input, Caret at the end
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertFalse(mySandBox.getCaretPosition() == 0);
        guiRobot.push(KeyCode.SHIFT, KeyCode.BACK_SPACE);
        assertTrue(mySandBox.getCaretPosition() == 0);

        //Test 5b: Test for word + blank space input, Caret after word
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
        guiRobot.write("Test");
        guiRobot.write("    ");
        assertFalse(mySandBox.getCaretPosition() == 0);
        mySandBox.positionCaret(mySandBox.getText().length() / 2);
        assertFalse(mySandBox.getCaretPosition() == 0);
        assertTrue(mySandBox.getCaretPosition() == 4);
        guiRobot.push(KeyCode.SHIFT, KeyCode.DELETE);
        assertTrue(mySandBox.getCaretPosition() == 0);
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() > 0);
        mySandBox.positionCaret(mySandBox.getText().length());
        assertTrue(mySandBox.getCaretPosition() == 4);
        mySandBox.clear();
        assertTrue(mySandBox.getCaretPosition() == 0);
    }


    @Test
    public void handleValidRightKeyPressAddLenMaxThree() {
        //This test focuses on ensuring that the key press works only for the add command
        //and shortcut triggers only when "a" or "add" is detected at the front of the statement.
        //Cases like "adda" "addy" "am" or "aa" will not trigger add command shortcut

        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "";

        //Test 1: Test for empty String
        guiRobot.write("         ");
        testString = "         ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));
        testString = "         ";
        assertTrue(testString.equals(mySandBox.getText()));

        //Test 2: Test for single char
        mySandBox.clear();
        testString = "";
        guiRobot.write(AddCommand.COMMAND_ALIAS);
        testString += AddCommand.COMMAND_ALIAS;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        //Does not matter how many white spaces are added before or after
        guiRobot.write(" ");
        testString = " " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.write("      ");
        testString += "      ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue(testString.equals(mySandBox.getText()));

        //Test 3: Test for String with len 2
        //String with len 2 will always fail. Even if first char is 'a', it is joined
        //with another char
        mySandBox.clear();
        testString = "";
        guiRobot.write("Ab");
        testString += "Ab";
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        //Does not matter how many white spaces are added before or after
        guiRobot.write(" ");
        testString = " " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.write("     ");
        testString += "     ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));

        //Test 4: Test for String with len 3
        mySandBox.clear();
        testString = "";
        guiRobot.write(AddCommand.COMMAND_WORD);
        testString += AddCommand.COMMAND_WORD;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        //Does not matter how many white spaces are added before or after
        guiRobot.write(" ");
        testString = " " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.write("     ");
        testString += "     ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue(testString.equals(mySandBox.getText()));

        mySandBox.clear();
        testString = "";
        guiRobot.write("b a");
        testString += "b " + AddCommand.COMMAND_ALIAS;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        //Does not matter how many white spaces are added before or after
        guiRobot.write(" ");
        testString = " " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.write("     ");
        testString += "     ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));

        mySandBox.clear();
        testString = "";
        guiRobot.write("a b");
        testString += AddCommand.COMMAND_ALIAS + " b";
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(0);
        //Does not matter how many white spaces are added before or after
        guiRobot.write(" ");
        testString = " " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.write("     ");
        testString += "     ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue(testString.equals(mySandBox.getText()));
    }


    @Test
    public void handleValidRightKeyPressAddLenAboveThree() {
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "";
        String backupTestString;

        // Input: Add -> Valid
        guiRobot.write("Add");
        testString += "Add";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue(testString.equals(mySandBox.getText()));

        // Input: a Add -> Valid
        mySandBox.positionCaret(0);
        guiRobot.write("a");
        testString = "a" + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.push(KeyCode.RIGHT);
        backupTestString = testString;
        testString += " " + STRING_PHONE;
        assertFalse(testString.equals(mySandBox.getText()));
        testString = backupTestString;
        mySandBox.positionCaret(1);
        guiRobot.write(" ");
        testString = Character.toString(testString.charAt(0))
                + " " + testString.substring(1);
        assertTrue(testString.equals(mySandBox.getText()));

        //Blank spaces are removed
        mySandBox.positionCaret(0);
        guiRobot.write("    ");
        testString = "    " + testString;
        assertTrue(testString.equals(mySandBox.getText()));
        mySandBox.positionCaret(testString.length());
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_PHONE;
        testString += " " + STRING_EMAIL;
        testString += " " + STRING_ADDRESS;
        testString += " " + STRING_BLOODTYPE;
        testString += " " + STRING_REMARK;
        testString += " " + STRING_DATE;
        testString += " " + STRING_TAG;
        assertTrue(testString.equals(mySandBox.getText()));

    }


    @Test
    public void handleInvalidRightKeyPressAdd() {
        //Test to ensure add command shortcut does not trigger as long as
        //caret is within the text

        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        guiRobot.write("Add");
        assertTrue("Add".equals(mySandBox.getText()));

        //Caret shifted left -> Returns true
        guiRobot.push(KeyCode.SHIFT, KeyCode.ALT);
        //Ensure caret is at the left
        assertTrue(mySandBox.getCaretPosition() == 0);
        //Try to trigger add shortcut - Nothing happens, Caret + 1
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add".equals(mySandBox.getText()));
        assertTrue(mySandBox.getCaretPosition() == 1);
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add".equals(mySandBox.getText()));
        assertTrue(mySandBox.getCaretPosition() == 2);
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add".equals(mySandBox.getText()));
        assertTrue(mySandBox.getCaretPosition() == 3);

        //Trigger add shortcut - n/ is concatenated
        guiRobot.push(KeyCode.RIGHT);
        assertTrue("Add n/".equals(mySandBox.getText()));

    }



    @Test
    public void handleValidRightKeyPressAddPrefixInOrder() {
        //Add Command allows users to enter the prefix in any order
        //The Add Command shortcut accounts for missing prefix or jump in prefix but for this test
        //it will focus on testing under the assumption that the prefix in the right order: n p e a b t

        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "add";

        guiRobot.write("add");
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue((testString).equals(mySandBox.getText()));
        //Even if user did not enter any input, p/ will be automatically added
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_PHONE;
        assertTrue((testString).equals(mySandBox.getText()));
        //Ensure that caret is set to far right after concatenation
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);
        //Ensure that shortcut does not run if caret is not at end of line
        int currentCaretPosition = mySandBox.getCaretPosition();
        mySandBox.positionCaret(currentCaretPosition - 1);
        guiRobot.push(KeyCode.RIGHT);
        assertTrue(testString.equals(mySandBox.getText()));
        //Return caret back to original position
        guiRobot.push(KeyCode.SHIFT, KeyCode.CONTROL);
        //Simulate User Input
        guiRobot.write("98765432");
        testString += "98765432";
        //Continue pushing
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_EMAIL;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_ADDRESS;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_BLOODTYPE;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_REMARK;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_DATE;
        assertTrue(testString.equals(mySandBox.getText()));
        //Ensure that even though there is a tag input, more tag are added if user requires
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_TAG;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_TAG;
        assertTrue(testString.equals(mySandBox.getText()));
        //Final assurance that caret is at far right
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

    }


    @Test
    public void handleValidRightKeyPressAddPrefixRandom() {
        //The Add Command shortcut accounts for missing prefix or jump in prefix.
        //This functionality will be tested in this test

        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "add";

        guiRobot.write("add");
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue((testString).equals(mySandBox.getText()));

        //Assume user skips the input of phone prefix : p/
        guiRobot.write(" ");
        guiRobot.write(STRING_EMAIL);
        guiRobot.write("jeremylsw@u.nus.edu");
        testString += " " + STRING_EMAIL + "jeremylsw@u.nus.edu";
        assertTrue((testString).equals(mySandBox.getText()));

        //Add command shortcut will detect missing p/ and concatenate it
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_PHONE;
        assertTrue((testString).equals(mySandBox.getText()));
        //Ensure caret positioning
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Assume user jumps straight to tags, missing out address and bloodtype prefixes
        guiRobot.write(" ");
        guiRobot.write(STRING_TAG);
        guiRobot.write(" ");
        guiRobot.write(STRING_TAG);
        testString += " " + STRING_TAG + " " + STRING_TAG;
        assertTrue((testString).equals(mySandBox.getText()));

        //Subsequent concatenation will detect missing address and bloodtype prefixes and fix it
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_ADDRESS + " " + STRING_BLOODTYPE;
        assertTrue((testString).equals(mySandBox.getText()));

        //Final assurance that caret is at far right
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);
    }

    @Test
    public void handleInvalidRightKeyPressEdit() {
        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString;

        //Test stringToEvaluate.length() < 3 -> false
        guiRobot.write("e ");
        testString = "e ";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));
        mySandBox.clear();
        guiRobot.write("e1");
        testString = "e1";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));
        mySandBox.clear();

        //Test !stringToEvaluate.contains(" ") -> false
        guiRobot.write("edit");
        testString = "edit";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));
        mySandBox.clear();
        guiRobot.write("edit1");
        testString = "edit1";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));
        mySandBox.clear();
        guiRobot.write("edit1n/jeremy");
        testString = "edit1n/jeremy";
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertFalse(testString.equals(mySandBox.getText()));

    }


    @Test
    public void handleInvalidCommandWordEdit() {
        //Test for !containsEditCommand && containsOnlyNumbers -> False

        //Extracts the textfield. Needed to use the caret related methods
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        int counter = 0;

        // e 1 -> Valid
        guiRobot.write("e 1");
        counter += 3;
        assertTrue(mySandBox.getText().length() == counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertTrue(mySandBox.getText().length() == counter);

        // ed 1 -> Invalid
        mySandBox.positionCaret(1);
        guiRobot.write("d");
        counter += 1;
        mySandBox.positionCaret(counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertFalse(mySandBox.getText().length() == counter);
        counter -= 3;
        assertTrue(mySandBox.getText().length() == counter);

        // edi 1 -> Invalid
        mySandBox.positionCaret(2);
        guiRobot.write("i");
        counter += 1;
        mySandBox.positionCaret(counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertFalse(mySandBox.getText().length() == counter);
        counter -= 3;
        assertTrue(mySandBox.getText().length() == counter);

        // edit 1 -> Valid
        mySandBox.positionCaret(3);
        guiRobot.write("t");
        counter += 1;
        mySandBox.positionCaret(counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertTrue(mySandBox.getText().length() == counter);
    }


    @Test
    public void handleInvalidNumberRightKeyPressEdit() {
        //Test for containsEditCommand && !containsOnlyNumbers -> False
        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        int counter = 0;
        //edit 1 -> Valid
        guiRobot.write("edit 1");
        counter += 6;
        assertTrue(mySandBox.getText().length() == counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertTrue(mySandBox.getText().length() == counter);
        //edit 10 -> Valid
        mySandBox.positionCaret(6);
        guiRobot.write("0");
        counter += 1;
        mySandBox.positionCaret(counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertTrue(mySandBox.getText().length() == counter);
        //edit -10 -> Invalid
        mySandBox.positionCaret(5);
        guiRobot.write("-");
        counter += 1;
        mySandBox.positionCaret(counter);
        guiRobot.push(KeyCode.RIGHT);
        counter += 3;
        assertFalse(mySandBox.getText().length() == counter);


    }


    @Test
    public void handleValidRightKeyPressEditPrefixInOrder() {

        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "edit 1";

        guiRobot.write("edit 1");
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue((testString).equals(mySandBox.getText()));
        //Even if user did not enter any input, p/ will be automatically added
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_PHONE;
        assertTrue((testString).equals(mySandBox.getText()));
        //Ensure that caret is set to far right after concatenation
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);
        //Ensure that shortcut does not run if caret is not at end of line
        int currentCaretPosition = mySandBox.getCaretPosition();
        mySandBox.positionCaret(currentCaretPosition - 1);
        guiRobot.push(KeyCode.RIGHT);
        assertTrue(testString.equals(mySandBox.getText()));
        //Return caret back to original position
        guiRobot.push(KeyCode.SHIFT, KeyCode.CONTROL);
        //Simulate User Input
        guiRobot.write("98765432");
        testString += "98765432";
        //Continue pushing
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_EMAIL;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_ADDRESS;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_BLOODTYPE;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_REMARK;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_DATE;
        assertTrue(testString.equals(mySandBox.getText()));
        //Ensure that even though there is a tag input, more tag are added if user requires
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_TAG;
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_TAG;
        assertTrue(testString.equals(mySandBox.getText()));
        //Final assurance that caret is at far right
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

    }


    @Test
    public void handleValidRightKeyPressEditPrefixRandom() {
        //The Edit Command shortcut accounts for missing prefix or jump in prefix.
        //This functionality will be tested in this test

        TextField mySandBox = commandBoxForTesting.getCommandTextField();
        String testString = "edit 1";

        guiRobot.write("edit 1");
        assertTrue(testString.equals(mySandBox.getText()));
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_NAME;
        assertTrue((testString).equals(mySandBox.getText()));

        //Assume user skips the input of phone prefix : p/
        guiRobot.write(" ");
        guiRobot.write(STRING_EMAIL);
        guiRobot.write("jeremylsw@u.nus.edu");
        testString += " " + STRING_EMAIL + "jeremylsw@u.nus.edu";
        assertTrue((testString).equals(mySandBox.getText()));

        //Add command shortcut will detect missing p/ and concatenate it
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_PHONE;
        assertTrue((testString).equals(mySandBox.getText()));
        //Ensure caret positioning
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);

        //Assume user jumps straight to tags, missing out address and bloodtype prefixes
        guiRobot.write(" ");
        guiRobot.write(STRING_TAG);
        guiRobot.write(" ");
        guiRobot.write(STRING_TAG);
        testString += " " + STRING_TAG + " " + STRING_TAG;
        assertTrue((testString).equals(mySandBox.getText()));

        //Subsequent concatenation will detect missing address and bloodtype prefixes and fix it
        guiRobot.push(KeyCode.RIGHT);
        guiRobot.push(KeyCode.RIGHT);
        testString += " " + STRING_ADDRESS + " " + STRING_BLOODTYPE;
        assertTrue((testString).equals(mySandBox.getText()));

        //Final assurance that caret is at far right
        assertTrue(mySandBox.getCaretPosition() == mySandBox.getText().length());
        assertNotNull(mySandBox.getCaretPosition());
        assertFalse(mySandBox.getCaretPosition() == 0);
    }


    @Test
    public void testGetTextField() {
        TextField myTextField = commandBoxForTesting.getCommandTextField();
        guiRobot.write("Same TextField Test");
        //Same text field -> Returns true
        assertTrue(myTextField.getText().equals(commandBoxForTesting.getCommandTextField().getText()));
        //Other values -> Returns false
        assertNotNull(myTextField);
        assertFalse(myTextField.equals(1));

    }
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing remark -> success */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBloodType(VALID_BLOODTYPE_AMY).withRemark(NON_COMPULSORY_REMARK)
                .withTags(VALID_TAG_FRIEND).withAppointment(VALID_NAME_AMY).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + TAG_DESC_FRIEND
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandSuccess(command, toAdd);

        /* Case: missing phone -> success */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NON_COMPULSORY_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBloodType(VALID_BLOODTYPE_AMY).withRemark(NON_COMPULSORY_REMARK)
                .withTags(VALID_TAG_FRIEND).withAppointment(VALID_NAME_AMY).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + EMAIL_DESC_AMY + TAG_DESC_FRIEND
                + ADDRESS_DESC_AMY + BLOODTYPE_DESC_AMY;
        assertCommandSuccess(command, toAdd);

        /* Case: missing email -> success */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NON_COMPULSORY_PHONE_AMY)
                .withEmail(NON_COMPULSORY_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withBloodType(VALID_BLOODTYPE_AMY).withRemark(NON_COMPULSORY_REMARK)
                .withTags(VALID_TAG_FRIEND).withAppointment(VALID_NAME_AMY).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND
                + BLOODTYPE_DESC_AMY;
        assertCommandSuccess(command, toAdd);

        /* Case: missing address -> success */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NON_COMPULSORY_PHONE_AMY)
                .withEmail(NON_COMPULSORY_EMAIL_AMY).withAddress(NON_COMPULSORY_ADDRESS_AMY)
                .withBloodType(VALID_BLOODTYPE_AMY).withRemark(NON_COMPULSORY_REMARK)
                .withTags(VALID_TAG_FRIEND).withAppointment(VALID_NAME_AMY).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + BLOODTYPE_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);

        /* Case: missing bloodtype -> success */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(NON_COMPULSORY_PHONE_AMY)
                .withEmail(NON_COMPULSORY_EMAIL_AMY).withAddress(NON_COMPULSORY_ADDRESS_AMY)
                .withBloodType(NON_COMPULSORY_BLOODTYPE).withRemark(NON_COMPULSORY_REMARK)
                .withTags(VALID_TAG_FRIEND).withAppointment(VALID_NAME_AMY).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
