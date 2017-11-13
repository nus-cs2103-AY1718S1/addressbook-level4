# justinpoh
###### \java\guitests\guihandles\BirthdayReminderCardHandle.java
``` java
/**
 * Provides a handle to a birthday reminder card in the birthday reminder list panel.
 */
public class BirthdayReminderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String NICKNAME_FIELD_ID = "#nickname";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label nicknameLabel;
    private final Label birthdayLabel;

    public BirthdayReminderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.nicknameLabel = getChildNode(NICKNAME_FIELD_ID);
        this.birthdayLabel = getChildNode(BIRTHDAY_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getNickname() {
        return nicknameLabel.getText();
    }
    public String getBirthday() {
        return birthdayLabel.getText();
    }

}
```
###### \java\guitests\guihandles\ReminderCardHandle.java
``` java
/**
 * Provides a handle to a reminder card in the reminder list panel.
 */
public class ReminderCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String REMINDER_FIELD_ID = "#reminder";
    private static final String DATE_FIELD_ID = "#date";
    private static final String TIME_FIELD_ID = "#time";

    private final Label idLabel;
    private final Label reminderLabel;
    private final Label dateLabel;
    private final Label timeLabel;

    public ReminderCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.reminderLabel = getChildNode(REMINDER_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);
        this.timeLabel = getChildNode(TIME_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getReminder() {
        return reminderLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }
}
```
###### \java\seedu\address\logic\commands\AddReminderCommandTest.java
``` java
    @Test
    public void constructor_nullReminder_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddReminderCommand(null);
    }


    @Test
    public void execute_personAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingReminderAdded modelStub = new ModelStubAcceptingReminderAdded();
        Reminder validReminder = new ReminderBuilder().build();

        CommandResult commandResult = getAddReminderCommandForReminder(validReminder, modelStub).execute();

        assertEquals(String.format(AddReminderCommand.MESSAGE_SUCCESS, validReminder), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validReminder), modelStub.remindersAdded);
    }

    @Test
    public void execute_duplicateReminder_throwsCommandException() throws Exception {
        ModelStub modelStub = new ModelStubThrowingDuplicateReminderException();
        Reminder validReminder = new ReminderBuilder().build();

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddReminderCommand.MESSAGE_DUPLICATE_REMINDER);

        getAddReminderCommandForReminder(validReminder, modelStub).execute();
    }

    @Test
    public void equals() {
        Reminder coffeeReminder = new ReminderBuilder().build();
        Reminder assignmentReminder = new ReminderBuilder().withReminder("Assignment").build();
        AddReminderCommand addCoffeeCommand = new AddReminderCommand(coffeeReminder);
        AddReminderCommand addAssignmentCommand = new AddReminderCommand(assignmentReminder);

        // same object -> returns true
        assertTrue(addCoffeeCommand.equals(addCoffeeCommand));

        // same values -> returns true
        AddReminderCommand addCoffeeCommandCopy = new AddReminderCommand(coffeeReminder);
        assertTrue(addCoffeeCommand.equals(addCoffeeCommandCopy));

        // different types -> returns false
        assertFalse(addCoffeeCommand.equals(1));

        // null -> returns false
        assertFalse(addCoffeeCommand.equals(null));

        // different reminder -> returns false
        assertFalse(addCoffeeCommand.equals(addAssignmentCommand));
    }

    /**
     * Generates a new AddReminderCommand with the details of the given reminder.
     */
    private AddReminderCommand getAddReminderCommandForReminder(Reminder reminder, Model model) {
        AddReminderCommand command = new AddReminderCommand(reminder);
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }
```
###### \java\seedu\address\logic\commands\DeleteReminderCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for {@code DeleteReminderCommand}.
 */
public class DeleteReminderCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_validIndexSortedReminderList_success() throws Exception {
        ReadOnlyReminder reminderToDelete = model.getSortedReminderList().get(INDEX_FIRST_PERSON.getZeroBased());

        DeleteReminderCommand deleteReminderCommand = prepareCommand(INDEX_FIRST_PERSON);

        String expectedMessage = String.format(DeleteReminderCommand.MESSAGE_DELETE_REMINDER_SUCCESS, reminderToDelete);

        ModelManager expectedModel = new ModelManager(model.getAddressBook(), model.getUniqueReminderList(),
                new UserPrefs());
        expectedModel.deleteReminder(reminderToDelete);

        assertCommandSuccess(deleteReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidIndexSortedReminderList_throwsCommandException() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedReminderList().size() + 1);
        DeleteReminderCommand deleteReminderCommand = prepareCommand(outOfBoundIndex);

        assertCommandFailure(deleteReminderCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        DeleteReminderCommand deleteFirstReminderCommand = new DeleteReminderCommand(INDEX_FIRST_PERSON);
        DeleteReminderCommand deleteSecondReminderCommand = new DeleteReminderCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(deleteFirstReminderCommand.equals(deleteFirstReminderCommand));

        // same values -> returns true
        DeleteReminderCommand deleteFirstReminderCommandCopy = new DeleteReminderCommand(INDEX_FIRST_PERSON);
        assertTrue(deleteFirstReminderCommand.equals(deleteFirstReminderCommandCopy));

        // different types -> returns false
        assertFalse(deleteFirstReminderCommand.equals(1));

        // null -> returns false
        assertFalse(deleteFirstReminderCommand.equals(null));

        // different reminder -> returns false
        assertFalse(deleteFirstReminderCommand.equals(deleteSecondReminderCommand));
    }

    /**
     * Returns a {@code DeleteReminderCommand} with the parameter {@code index}.
     */
    private DeleteReminderCommand prepareCommand(Index index) {
        DeleteReminderCommand deleteReminderCommand = new DeleteReminderCommand(index);
        deleteReminderCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return deleteReminderCommand;
    }
}
```
###### \java\seedu\address\logic\commands\EditReminderCommandTest.java
``` java
public class EditReminderCommandTest {


    private Model model = new ModelManager(getTypicalAddressBook(), getUniqueTypicalReminders(), new UserPrefs());

    @Test
    public void execute_allFieldsSpecified_success() throws Exception {
        Reminder editedReminder = new ReminderBuilder().build();
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder(editedReminder).build();
        EditReminderCommand editCommand = prepareCommand(INDEX_FIRST_PERSON, descriptor);

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updateReminder(model.getSortedReminderList().get(0), editedReminder);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_someFieldsSpecified_success() throws Exception {
        // time field not changed
        Index indexLastReminder = Index.fromOneBased(model.getSortedReminderList().size());
        ReadOnlyReminder lastReminder = model.getSortedReminderList().get(indexLastReminder.getZeroBased());

        ReminderBuilder reminderInList = new ReminderBuilder(lastReminder);
        Reminder editedReminder = reminderInList.withReminder(VALID_REMINDER_ASSIGNMENT)
                .withDate(VALID_REMINDER_DATE_ASSIGNMENT).build();

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_ASSIGNMENT)
                .withDate(VALID_REMINDER_DATE_ASSIGNMENT).build();
        EditReminderCommand editReminderCommand = prepareCommand(indexLastReminder, descriptor);

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());
        expectedModel.updateReminder(lastReminder, editedReminder);

        assertCommandSuccess(editReminderCommand, model, expectedMessage, expectedModel);
    }


    @Test
    public void execute_noFieldSpecified_success() throws Exception {
        EditReminderCommand editReminderCommand = prepareCommand(INDEX_FIRST_PERSON, new EditReminderDescriptor());
        ReadOnlyReminder editedReminder = model.getSortedReminderList().get(INDEX_FIRST_PERSON.getZeroBased());

        String expectedMessage = String.format(EditReminderCommand.MESSAGE_EDIT_REMINDER_SUCCESS, editedReminder);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()),
                model.getUniqueReminderList(), new UserPrefs());

        assertCommandSuccess(editReminderCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_duplicateReminder_failure() throws Exception {
        Reminder firstReminder = new Reminder(model.getSortedReminderList().get(INDEX_FIRST_PERSON.getZeroBased()));
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder(firstReminder).build();
        EditReminderCommand editReminderCommand = prepareCommand(INDEX_SECOND_PERSON, descriptor);

        assertCommandFailure(editReminderCommand, model, EditReminderCommand.MESSAGE_DUPLICATE_REMINDER);
    }

    @Test
    public void execute_invalidReminderIndex_failure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getSortedReminderList().size() + 1);
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withReminder(VALID_REMINDER_ASSIGNMENT).build();
        EditReminderCommand editReminderCommand = prepareCommand(outOfBoundIndex, descriptor);

        assertCommandFailure(editReminderCommand, model, Messages.MESSAGE_INVALID_REMINDER_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final EditReminderCommand standardCommand = new EditReminderCommand(INDEX_FIRST_PERSON, REMINDER_COFFEE);

        // same values -> returns true
        EditReminderDescriptor copyDescriptor = new EditReminderDescriptor(REMINDER_COFFEE);
        EditReminderCommand commandWithSameValues = new EditReminderCommand(INDEX_FIRST_PERSON, copyDescriptor);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new EditReminderCommand(INDEX_SECOND_PERSON, REMINDER_COFFEE)));

        // different descriptor -> returns false
        assertFalse(standardCommand.equals(new EditReminderCommand(INDEX_FIRST_PERSON, REMINDER_ASSIGNMENT)));
    }

    /**
     * Returns an {@code EditReminderCommand} with parameters {@code index} and {@code descriptor}
     */
    private EditReminderCommand prepareCommand(Index index, EditReminderDescriptor descriptor) {
        EditReminderCommand editReminderCommand = new EditReminderCommand(index, descriptor);
        editReminderCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editReminderCommand;
    }
}
```
###### \java\seedu\address\logic\commands\EditReminderDescriptorTest.java
``` java
public class EditReminderDescriptorTest {
    @Test
    public void equals() {
        // same values -> returns true
        EditReminderDescriptor descriptorWithSameValues = new EditReminderDescriptor(REMINDER_COFFEE);
        assertTrue(REMINDER_COFFEE.equals(descriptorWithSameValues));

        // same objects -> returns true
        assertTrue(REMINDER_COFFEE.equals(REMINDER_COFFEE));

        // null -> returns false
        assertFalse(REMINDER_COFFEE.equals(null));

        // different types -> returns false
        assertFalse(REMINDER_COFFEE.equals(5));

        // different values -> returns false
        assertFalse(REMINDER_COFFEE.equals(REMINDER_ASSIGNMENT));

        // different reminder -> returns false
        EditReminderDescriptor editedCoffee = new EditReminderDescriptorBuilder(REMINDER_COFFEE)
                .withReminder(VALID_REMINDER_ASSIGNMENT).build();
        assertFalse(REMINDER_COFFEE.equals(editedCoffee));

        // different date -> returns false;
        editedCoffee = new EditReminderDescriptorBuilder(REMINDER_COFFEE)
                .withDate(VALID_REMINDER_DATE_ASSIGNMENT).build();
        assertFalse(REMINDER_COFFEE.equals(editedCoffee));

        // different time -> returns false;
        editedCoffee = new EditReminderDescriptorBuilder(REMINDER_COFFEE)
                .withTime(VALID_REMINDER_TIME_ASSIGNMENT).build();
        assertFalse(REMINDER_COFFEE.equals(editedCoffee));
    }
}
```
###### \java\seedu\address\logic\commands\ToggleCommandTest.java
``` java
public class ToggleCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_toggle_success() {
        CommandResult result = new ToggleCommand().execute();
        assertEquals(MESSAGE_TOGGLE_SUCCESS, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof BrowserAndRemindersPanelToggleEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\LogicManagerTest.java
``` java
    @Test
    public void getBirthdayPanelFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getBirthdayPanelFilteredPersonList().remove(0);
    }

    @Test
    public void getReminderList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        logic.getReminderList().remove(0);
    }
```
###### \java\seedu\address\logic\parser\AddReminderCommandParserTest.java
``` java
public class AddReminderCommandParserTest {

    private AddReminderCommandParser parser = new AddReminderCommandParser();

    @Test
    public void parse_allFieldsPresent_success() throws Exception {
        Reminder expectedReminder = new ReminderBuilder().build();
        assertParseSuccess(parser, REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE
                + REMINDER_DESC_TIME_COFFEE, new AddReminderCommand(expectedReminder));
    }

    @Test
    public void parse_fieldMissing_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);

        // missing reminder
        assertParseFailure(parser, REMINDER_DESC_DATE_COFFEE + REMINDER_DESC_TIME_COFFEE, expectedMessage);

        // missing date
        assertParseFailure(parser, REMINDER_DESC_COFFEE + REMINDER_DESC_TIME_COFFEE, expectedMessage);

        // missing time
        assertParseFailure(parser, REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE);

        //invalid reminder
        assertParseFailure(parser, INVALID_REMINDER_DESC + REMINDER_DESC_DATE_COFFEE + REMINDER_DESC_TIME_COFFEE,
                expectedMessage);

        //invalid date
        assertParseFailure(parser, REMINDER_DESC_COFFEE + INVALID_REMINDER_DESC_DATE + REMINDER_DESC_TIME_COFFEE,
                Date.MESSAGE_DATE_CONSTRAINTS);

        //invalid time
        assertParseFailure(parser, REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE + INVALID_REMINDER_DESC_TIME,
                Time.MESSAGE_TIME_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\DeleteReminderCommandParserTest.java
``` java
/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteReminderCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteReminderCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteReminderCommandParserTest {
    private DeleteReminderCommandParser parser = new DeleteReminderCommandParser();

    @Test
    public void parse_validArgs_returnsDeleteReminderCommand() throws IOException {
        assertParseSuccess(parser, "1", new DeleteReminderCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() throws IOException {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                DeleteReminderCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\EditReminderCommandParserTest.java
``` java
public class EditReminderCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, EditReminderCommand.MESSAGE_USAGE);

    private EditReminderCommandParser parser = new EditReminderCommandParser();

    @Test
    public void parse_missingParts_failure() throws IOException {
        // no index specified
        assertParseFailure(parser, VALID_REMINDER_COFFEE, MESSAGE_INVALID_FORMAT);

        // no field specified
        assertParseFailure(parser, "1", EditReminderCommand.MESSAGE_NOT_EDITED);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() throws IOException {
        // negative index
        assertParseFailure(parser, "-5" + REMINDER_DESC_COFFEE, MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0" + REMINDER_DESC_COFFEE, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidValue_failure() throws IOException {
        assertParseFailure(parser, "1" + INVALID_REMINDER_DESC, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditReminderCommand.MESSAGE_REMINDER_FORMAT)); // invalid reminder
        assertParseFailure(parser, "1" + INVALID_REMINDER_DESC_DATE, Date.MESSAGE_DATE_CONSTRAINTS); // invalid date
        assertParseFailure(parser, "1" + INVALID_REMINDER_DESC_TIME, Time.MESSAGE_TIME_CONSTRAINTS); // invalid time

        // invalid date followed by valid time
        assertParseFailure(parser, "1" + INVALID_REMINDER_DESC_DATE + VALID_REMINDER_TIME_COFFEE,
                Date.MESSAGE_DATE_CONSTRAINTS);

        // valid date followed by invalid date
        assertParseFailure(parser, "1" + REMINDER_DESC_COFFEE + INVALID_REMINDER_DESC_DATE,
                Date.MESSAGE_DATE_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_REMINDER_DESC + INVALID_REMINDER_DESC_TIME
                + INVALID_REMINDER_DESC_DATE, String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                EditReminderCommand.MESSAGE_REMINDER_FORMAT));
    }

    @Test
    public void parse_allFieldsSpecified_success() throws IOException {
        Index targetIndex = INDEX_SECOND_PERSON;
        String userInput = targetIndex.getOneBased() + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE
                + REMINDER_DESC_TIME_COFFEE;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_COFFEE)
                .withDate(VALID_REMINDER_DATE_COFFEE).withTime(VALID_REMINDER_TIME_COFFEE).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_someFieldsSpecified_success() throws IOException {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_COFFEE)
                .withDate(VALID_REMINDER_DATE_COFFEE).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_oneFieldSpecified_success() throws IOException {
        // reminder
        Index targetIndex = INDEX_THIRD_PERSON;
        String userInput = targetIndex.getOneBased() + REMINDER_DESC_COFFEE;
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withReminder(VALID_REMINDER_COFFEE).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // date
        userInput = targetIndex.getOneBased() + REMINDER_DESC_DATE_COFFEE;
        descriptor = new EditReminderDescriptorBuilder().withDate(VALID_REMINDER_DATE_COFFEE).build();
        expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // time
        userInput = targetIndex.getOneBased() + REMINDER_DESC_TIME_COFFEE;
        descriptor = new EditReminderDescriptorBuilder().withTime(VALID_REMINDER_TIME_COFFEE).build();
        expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_multipleRepeatedFields_acceptsLast() throws IOException {
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased()  + REMINDER_DESC_ASSIGNMENT + REMINDER_DESC_DATE_COFFEE
                + REMINDER_DESC_DATE_ASSIGNMENT + REMINDER_DESC_TIME_ASSIGNMENT + REMINDER_DESC_TIME_COFFEE
                + REMINDER_DESC_COFFEE;

        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_COFFEE)
                .withDate(VALID_REMINDER_DATE_ASSIGNMENT).withTime(VALID_REMINDER_TIME_COFFEE).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);

        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_invalidValueFollowedByValidValue_success() throws IOException {
        // no other valid values specified
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + INVALID_REMINDER_DESC_DATE + REMINDER_DESC_DATE_COFFEE;
        EditReminderDescriptor descriptor = new EditReminderDescriptorBuilder()
                .withDate(VALID_REMINDER_DATE_COFFEE).build();
        EditReminderCommand expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);

        // other valid values specified
        userInput = targetIndex.getOneBased() + REMINDER_DESC_ASSIGNMENT + INVALID_REMINDER_DESC_DATE
                + REMINDER_DESC_DATE_COFFEE + REMINDER_DESC_TIME_ASSIGNMENT;
        descriptor = new EditReminderDescriptorBuilder().withReminder(VALID_REMINDER_ASSIGNMENT)
                .withDate(VALID_REMINDER_DATE_COFFEE).withTime(VALID_REMINDER_TIME_ASSIGNMENT).build();
        expectedCommand = new EditReminderCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseBirthday_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseBirthday(null);
    }

    @Test
    public void parseBirthday_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseBirthday(Optional.of(INVALID_BIRTHDAY));
    }

    @Test
    public void parseBirthday_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseBirthday(Optional.empty()).isPresent());
    }

    @Test
    public void parseBirthday_validValue_returnsBirthday() throws Exception {
        Birthday expectedBirthday = new Birthday(VALID_BIRTHDAY);
        Optional<Birthday> actualBirthday = ParserUtil.parseBirthday(Optional.of(VALID_BIRTHDAY));

        assertEquals(expectedBirthday, actualBirthday.get());
    }

    @Test
    public void parseDate_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseDate(null);
    }

    @Test
    public void parseDate_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseDate(Optional.of(INVALID_DATE));
    }

    @Test
    public void parseDate_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseDate(Optional.empty()).isPresent());
    }

    @Test
    public void parseDate_validValue_returnsDate() throws Exception {
        Date expectedDate = new Date(VALID_DATE);
        Optional<Date> actualDate = ParserUtil.parseDate(Optional.of(VALID_DATE));

        assertEquals(expectedDate, actualDate.get());
    }

    @Test
    public void parseTime_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTime(null);
    }

    @Test
    public void parseTime_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parseTime(Optional.of(INVALID_TIME));
    }

    @Test
    public void parseTime_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parseTime(Optional.empty()).isPresent());
    }

    @Test
    public void parseTime_validValue_returnsTime() throws Exception {
        Time expectedTime = new Time(VALID_TIME);
        Optional<Time> actualTime = ParserUtil.parseTime(Optional.of(VALID_TIME));

        assertEquals(expectedTime, actualTime.get());
    }
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void getBirthdayPanelFilteredPersonList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getBirthdayPanelFilteredPersonList().remove(0);
    }

    @Test
    public void getSortedReminderList_modifyList_throwsUnsupportedOperationException() {
        ModelManager modelManager = new ModelManager();
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getSortedReminderList().remove(0);
    }
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
        // different list of reminders -> return false
        XmlSerializableReminders differentReminders = new XmlSerializableReminders();
        UniqueReminderList uniqueDifferentReminders = new UniqueReminderList(differentReminders);
        assertFalse(modelManager.equals(new ModelManager(addressBook, uniqueDifferentReminders, userPrefs)));
```
###### \java\seedu\address\model\person\BirthdayTest.java
``` java
public class BirthdayTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void getMonthOfBirthday() throws Exception {
        // non-empty birthday string
        Birthday birthday1 = new Birthday("21/11/2017");
        assertEquals(11, birthday1.getMonthOfBirthday());

        // empty birthday string
        Birthday emptyBirthday = new Birthday("");
        assertEquals(EMPTY_BIRTHDAY_FIELD_MONTH, emptyBirthday.getMonthOfBirthday());
    }

    @Test
    public void getDayOfBirthday() throws Exception {
        // non-empty birthday string
        Birthday birthday1 = new Birthday("21/11/2017");
        assertEquals(21, birthday1.getDayOfBirthday());

        // empty birthday string
        Birthday emptyBirthday = new Birthday("");
        assertEquals(EMPTY_BIRTHDAY_FIELD_DAY, emptyBirthday.getDayOfBirthday());
    }

    @Test
    public void isBirthdayToday() {
        LocalDate testCurrentDate = LocalDate.of(2017, 12, 10);

        // is birthday today -> returns true
        Birthday birthdayTodaySameYear = getBirthdayTestInstance("10/12/2017", testCurrentDate);
        assertTrue(birthdayTodaySameYear.isBirthdayToday());
        Birthday birthdayTodayDifferentYear = getBirthdayTestInstance("10/12/1995", testCurrentDate);
        assertTrue(birthdayTodayDifferentYear.isBirthdayToday());

        // is not birthday today -> returns true
        Birthday notBirthdayTodayPast = getBirthdayTestInstance("11/12/2017", testCurrentDate);
        assertFalse(notBirthdayTodayPast.isBirthdayToday());

        Birthday notBirthdayTodayUpcoming = getBirthdayTestInstance("13/12/2017", testCurrentDate);
        assertFalse(notBirthdayTodayUpcoming.isBirthdayToday());

    }

    @Test
    public void isBirthdayTomorrow() {
        LocalDate testCurrentDate = LocalDate.of(2017, 12, 10);

        // is birthday tomorrow -> returns true
        Birthday birthdayTomorrowSameYear = getBirthdayTestInstance("11/12/2017", testCurrentDate);
        assertTrue(birthdayTomorrowSameYear.isBirthdayTomorrow());
        Birthday birthdayTomorrowDifferentYear = getBirthdayTestInstance("11/12/2000", testCurrentDate);
        assertTrue(birthdayTomorrowDifferentYear.isBirthdayTomorrow());

        // is not birthday tomorrow -> returns false
        Birthday birthdayToday = getBirthdayTestInstance("10/12/2017", testCurrentDate);
        assertFalse(birthdayToday.isBirthdayTomorrow());

        Birthday birthdayAfterTomorrow = getBirthdayTestInstance("12/12/2017", testCurrentDate);
        assertFalse(birthdayAfterTomorrow.isBirthdayTomorrow());

    }

    @Test
    public void isValidBirthday() {
        // day out of bounds -> returns false
        assertFalse(Birthday.isValidBirthday("32.3.1995"));
        assertFalse(Birthday.isValidBirthday("31.4.1995"));
        assertFalse(Birthday.isValidBirthday("0.1.1995"));

        // month out of bounds -> returns false
        assertFalse(Birthday.isValidBirthday("21.13.1995"));
        assertFalse(Birthday.isValidBirthday("21.0.1995"));

        // year too small -> returns false
        assertFalse(Birthday.isValidBirthday("01.01.999"));
        assertFalse(Birthday.isValidBirthday("01.01.1899"));

        // year too big -> returns false
        assertFalse(Birthday.isValidBirthday("01.01.10000"));
        assertFalse(Birthday.isValidBirthday("01.01.2100"));

        // input format not correct -> returns false
        assertFalse(Birthday.isValidBirthday("1 January 1999"));
        assertFalse(Birthday.isValidBirthday("10/ 10 / 1992"));

        // input values are not integers -> returns false
        assertFalse(Birthday.isValidBirthday("3.0/10.0/1995"));

        // not even a date -> returns false
        assertFalse(Birthday.isValidBirthday("Not a date"));

        // wrong leap day date -> returns false
        assertFalse(Birthday.isValidBirthday("29/2/2017"));

        // space -> returns true
        assertTrue(Birthday.isValidBirthday(" "));

        // empty string -> returns true
        assertTrue(Birthday.isValidBirthday(""));

        // valid dates using / separator -> returns true
        assertTrue(Birthday.isValidBirthday("31/3/1995"));
        assertTrue(Birthday.isValidBirthday("31/03/1995"));
        assertTrue(Birthday.isValidBirthday("01/03/1995"));

        // valid dates using - separator -> returns true
        assertTrue(Birthday.isValidBirthday("01-03-1995"));
        assertTrue(Birthday.isValidBirthday("31-03-1995"));
        assertTrue(Birthday.isValidBirthday("01-03-1995"));

        // valid dates using . separator -> returns true
        assertTrue(Birthday.isValidBirthday("01.03.1995"));
        assertTrue(Birthday.isValidBirthday("31.03.1995"));
        assertTrue(Birthday.isValidBirthday("01.03.1995"));

        // valid leap year day -> returns true
        assertTrue(Birthday.isValidBirthday("29-2-2016"));
        assertTrue(Birthday.isValidBirthday("29-2-2000"));

        // flexible day-month-year separator -> returns true
        assertTrue(Birthday.isValidBirthday("01/03.1995"));
        assertTrue(Birthday.isValidBirthday("31.03/1995"));
        assertTrue(Birthday.isValidBirthday("01/03-1995"));
    }

}
```
###### \java\seedu\address\model\person\UpcomingBirthdayInCurrentMonthPredicateTest.java
``` java
public class UpcomingBirthdayInCurrentMonthPredicateTest {

    private static final int MONTH_DECEMBER = 12;
    private static final int MONTH_NOVEMBER = 11;
    private static final  int MONTH_OCTOBER = 10;
    private static final int DAY_TEN = 10;
    private static final int DAY_ELEVEN = 11;
    private static final int DAY_TWELVE = 12;
    private static final int DEFAULT_YEAR = 2017;
    private static final String FIELD_SEPARATOR = "-";

    @Test
    public void equals() {
        UpcomingBirthdayInCurrentMonthPredicate firstPredicate = getTestInstance(MONTH_NOVEMBER, DAY_ELEVEN);
        UpcomingBirthdayInCurrentMonthPredicate secondPredicate = getTestInstance(MONTH_NOVEMBER, DAY_ELEVEN);
        UpcomingBirthdayInCurrentMonthPredicate thirdPredicate = getTestInstance(MONTH_DECEMBER, DAY_ELEVEN);
        UpcomingBirthdayInCurrentMonthPredicate fourthPredicate = getTestInstance(MONTH_NOVEMBER, DAY_TWELVE);

        // same object -> return true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same value -> return true
        assertTrue(firstPredicate.equals(secondPredicate));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different month -> returns false
        assertFalse(firstPredicate.equals(thirdPredicate));

        // different day -> returns false
        assertFalse(firstPredicate.equals(fourthPredicate));
    }

    @Test
    public void test_personContainsBirthdayCorrectMonthAndDay_returnsTrue() {
        UpcomingBirthdayInCurrentMonthPredicate predicate = getTestInstance(MONTH_NOVEMBER, DAY_ELEVEN);

        // same month and same day
        final String birthdaySameMonthSameDay = DAY_ELEVEN + FIELD_SEPARATOR + MONTH_NOVEMBER
                + FIELD_SEPARATOR + DEFAULT_YEAR;
        assertTrue(predicate.test(new PersonBuilder().withBirthday(birthdaySameMonthSameDay).build()));

        // same month and later day
        final String birthdaySameMonthLaterDay = DAY_TWELVE + FIELD_SEPARATOR + MONTH_NOVEMBER
                + FIELD_SEPARATOR + DEFAULT_YEAR;
        assertTrue(predicate.test(new PersonBuilder().withBirthday(birthdaySameMonthLaterDay).build()));

    }

    @Test
    public void test_personContainsBirthdayWrongMonthWrongDay_returnsFalse() {
        UpcomingBirthdayInCurrentMonthPredicate predicate = getTestInstance(MONTH_NOVEMBER, DAY_ELEVEN);

        // same month and earlier day
        final String birthdaySameMonthEarlierDay = DAY_TEN + FIELD_SEPARATOR + MONTH_NOVEMBER
                + FIELD_SEPARATOR + DEFAULT_YEAR;
        assertFalse(predicate.test(new PersonBuilder().withBirthday(birthdaySameMonthEarlierDay).build()));

        // different month same day
        final String birthdayEarlierMonthSameDay = DAY_ELEVEN + FIELD_SEPARATOR + MONTH_OCTOBER
                + FIELD_SEPARATOR + DEFAULT_YEAR;
        assertFalse(predicate.test(new PersonBuilder().withBirthday(birthdayEarlierMonthSameDay).build()));

        final String birthdayLaterMonthSameDay = DAY_ELEVEN + FIELD_SEPARATOR + MONTH_DECEMBER
                + FIELD_SEPARATOR + DEFAULT_YEAR;
        assertFalse(predicate.test(new PersonBuilder().withBirthday(birthdayLaterMonthSameDay).build()));
    }

    @Test
    public void test_personContainsNoBirthday_returnsFalse() {
        UpcomingBirthdayInCurrentMonthPredicate predicate = new UpcomingBirthdayInCurrentMonthPredicate();
        assertFalse(predicate.test(new PersonBuilder().withBirthday("").build()));
    }
}
```
###### \java\seedu\address\model\reminder\DateTest.java
``` java
public class DateTest {

    @Test
    public void isValidDate() {
        // day out-of-bounds -> returns false
        assertFalse(Date.isValidDate("0/01/2017"));
        assertFalse(Date.isValidDate("31/04/2017"));

        // month out-of-bounds -> returns false
        assertFalse(Date.isValidDate("01/13/2017"));
        assertFalse(Date.isValidDate("01/00/2017"));

        // year out-of-bounds -> returns false
        assertFalse(Date.isValidDate("01/13/2100"));
        assertFalse(Date.isValidDate("01/13/1899"));

        // incorrect date input format -> returns false
        assertFalse(Date.isValidDate("1 Mar 2017"));

        // input is not even a date -> returns false
        assertFalse(Date.isValidDate("Not a date"));

        // invalid leap day -> returns false
        assertFalse(Date.isValidDate("29/02/2017"));

        // valid leap day -> returns true
        assertTrue(Date.isValidDate("29/02/2016"));

        // valid dates with paired separator -> returns true
        assertTrue(Date.isValidDate("01/01/2017"));
        assertTrue(Date.isValidDate("01.01.2017"));
        assertTrue(Date.isValidDate("01-01-2017"));

        // valid dates with flexible separator -> returns true
        assertTrue(Date.isValidDate("01/01.2017"));
        assertTrue(Date.isValidDate("01.01-2017"));
        assertTrue(Date.isValidDate("01-01/2017"));
    }

    @Test
    public void equals() throws Exception {

        Date date1 = new Date("01/01/2017");

        // different date -> returns false
        Date date2 = new Date("02/01/2017");
        assertFalse(date1.equals(date2));

        // different type -> returns false
        assertFalse(date1.equals(100));

        // null -> returns false
        assertFalse(date1.equals(null));

        // same object -> returns true
        assertTrue(date1.equals(date1));

        // same value -> returns true
        Date date3 = new Date("01/01/2017");
        assertTrue(date1.equals(date3));
    }
}
```
###### \java\seedu\address\model\reminder\StatusTest.java
``` java
public class StatusTest {

    private static final String DEFAULT_DATE = "10/10/2017";
    private static final String DATE_DAY_PAST = "5/10/2017";
    private static final String DATE_MONTH_PAST = "10/9/2017";
    private static final String DATE_YEAR_PAST = "10/10/2016";
    private static final String DATE_DAY_UPCOMING = "15/10/2017";
    private static final String DATE_MONTH_UPCOMING = "10/11/2017";
    private static final String DATE_YEAR_UPCOMING = "10/10/2018";
    private static final String ONE_DAY_AWAY = "11/10/2017";
    private static final String TWO_DAYS_AWAY = "12/10/2017";
    private static final String THREE_DAYS_AWAY = "13/10/2017";
    private static final String FOUR_DAYS_AWAY = "14/10/2017";
    private static final String ONE_DAY_BEFORE = "09/10/2017";

    private static final String DEFAULT_TIME = "12:30";
    private static final String TIME_HOUR_PAST = "09:30";
    private static final String TIME_MINUTE_PAST = "12:10";
    private static final String TIME_HOUR_UPCOMING = "13:30";
    private static final String TIME_MINUTE_UPCOMING = "12:40";

    @Test
    public void hasEventPassed() throws Exception {

        // day past -> returns true
        Reminder reminder1 = new ReminderBuilder().withDate(DATE_DAY_PAST).withTime(DEFAULT_TIME).build();
        Status status1 = getStatusTestInstance(reminder1, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status1.hasEventPassed());

        // month past -> returns true
        Reminder reminder2 = new ReminderBuilder().withDate(DATE_MONTH_PAST).withTime(DEFAULT_TIME).build();
        Status status2 = getStatusTestInstance(reminder2, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status2.hasEventPassed());

        // year past -> returns true
        Reminder reminder3 = new ReminderBuilder().withDate(DATE_YEAR_PAST).withTime(DEFAULT_TIME).build();
        Status status3 = getStatusTestInstance(reminder3, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status3.hasEventPassed());

        // hour past -> returns true
        Reminder reminder4 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_HOUR_PAST).build();
        Status status4 = getStatusTestInstance(reminder4, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status4.hasEventPassed());

        // minute past -> returns true
        Reminder reminder5 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_MINUTE_PAST).build();
        Status status5 = getStatusTestInstance(reminder5, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status5.hasEventPassed());

        // upcoming day -> returns false
        Reminder reminder6 = new ReminderBuilder().withDate(DATE_DAY_UPCOMING).withTime(DEFAULT_TIME).build();
        Status status6 = getStatusTestInstance(reminder6, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status6.hasEventPassed());

        // upcoming month -> returns false
        Reminder reminder7 = new ReminderBuilder().withDate(DATE_MONTH_UPCOMING).withTime(DEFAULT_TIME).build();
        Status status7 = getStatusTestInstance(reminder7, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status7.hasEventPassed());

        // upcoming year -> returns false
        Reminder reminder8 = new ReminderBuilder().withDate(DATE_YEAR_UPCOMING).withTime(DEFAULT_TIME).build();
        Status status8 = getStatusTestInstance(reminder8, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status8.hasEventPassed());

        // upcoming hour -> returns false
        Reminder reminder9 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_HOUR_UPCOMING).build();
        Status status9 = getStatusTestInstance(reminder9, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status9.hasEventPassed());

        // upcoming minute -> returns false
        Reminder reminder10 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_MINUTE_UPCOMING).build();
        Status status10 = getStatusTestInstance(reminder10, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status10.hasEventPassed());

        // same date and time -> returns false
        Reminder reminder11 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(DEFAULT_TIME).build();
        Status status11 = getStatusTestInstance(reminder11, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status11.hasEventPassed());
    }

    @Test
    public void isEventToday() {
        // different date -> returns false
        Reminder reminder1 = new ReminderBuilder().withDate(DATE_DAY_PAST).withTime(DEFAULT_TIME).build();
        Status status1 = getStatusTestInstance(reminder1, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status1.isEventToday());

        // time past -> returns false
        Reminder reminder2 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_HOUR_PAST).build();
        Status status2 = getStatusTestInstance(reminder2, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status2.isEventToday());

        // same date, same time -> returns true
        Reminder reminder3 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(DEFAULT_TIME).build();
        Status status3 = getStatusTestInstance(reminder3, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status3.isEventToday());

        // same date, upcoming time -> returns true
        Reminder reminder4 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_HOUR_UPCOMING).build();
        Status status4 = getStatusTestInstance(reminder4, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status4.isEventToday());
    }

    @Test
    public void isEventWithinThreeDays() {
        // one day before -> returns false
        Reminder reminder1 = new ReminderBuilder().withDate(ONE_DAY_BEFORE).withTime(DEFAULT_TIME).build();
        Status status1 = getStatusTestInstance(reminder1, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status1.isEventWithinThreeDays());

        // same day -> returns true
        Reminder reminder2 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(DEFAULT_TIME).build();
        Status status2 = getStatusTestInstance(reminder2, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status2.isEventWithinThreeDays());

        // one day later -> returns true
        Reminder reminder3 = new ReminderBuilder().withDate(ONE_DAY_AWAY).withTime(DEFAULT_TIME).build();
        Status status3 = getStatusTestInstance(reminder3, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status3.isEventWithinThreeDays());

        // two days later -> returns true
        Reminder reminder4 = new ReminderBuilder().withDate(TWO_DAYS_AWAY).withTime(DEFAULT_TIME).build();
        Status status4 = getStatusTestInstance(reminder4, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status4.isEventWithinThreeDays());

        // three days later -> returns true
        Reminder reminder5 = new ReminderBuilder().withDate(THREE_DAYS_AWAY).withTime(DEFAULT_TIME).build();
        Status status5 = getStatusTestInstance(reminder5, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertTrue(status5.isEventWithinThreeDays());

        // four days later -> returns false
        Reminder reminder6 = new ReminderBuilder().withDate(FOUR_DAYS_AWAY).withTime(DEFAULT_TIME).build();
        Status status6 = getStatusTestInstance(reminder6, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status6.isEventWithinThreeDays());

        // same day, time past -> returns false
        Reminder reminder7 = new ReminderBuilder().withDate(DEFAULT_DATE).withTime(TIME_HOUR_PAST).build();
        Status status7 = getStatusTestInstance(reminder7, convertStringToLocalDate(DEFAULT_DATE),
                convertStringToLocalTime(DEFAULT_TIME));
        assertFalse(status7.isEventWithinThreeDays());
    }

    @Test
    public void equals() throws Exception {

        // Since value of Status.value is dependent on the methods
        // Status.hasEventPassed, Status.isEventToday, Status.isEventWithinThreeDays,
        // this equals test would not test for Status.value so thoroughly as the tests
        // for the methods are already conducted above.

        Reminder reminder1 = new ReminderBuilder().build();
        Status status1 = new Status(reminder1);

        // different type -> returns false
        assertFalse(status1.equals(100));

        // null -> returns false
        assertFalse(status1.equals(null));

        // same object -> returns true
        assertTrue(status1.equals(status1));

        // same value -> returns true
        Reminder reminder2 = new ReminderBuilder(reminder1).build();
        Status status2 = new Status(reminder2);
        assertTrue(status1.equals(status2));
    }

    /**
     * Converts a string in the form dd/mm/yyyy, dd.mm.yyyy or dd-mm-yyyy
     * into a LocalDate object.
     */
    private LocalDate convertStringToLocalDate(String date) {
        String[] splitDate = date.split(DATE_SPLIT_REGEX);

        final int day = Integer.parseInt(splitDate[DATE_DAY_INDEX]);
        final int month = Integer.parseInt(splitDate[DATE_MONTH_INDEX]);
        final int year = Integer.parseInt(splitDate[DATE_YEAR_INDEX]);

        return LocalDate.of(year, month, day);
    }

    /**
     * Converts a string in the form hh:mm into a LocalTime object.
     */
    private LocalTime convertStringToLocalTime(String time) {
        String[] splitTime = time.split(HOUR_MIN_SEPARATOR);
        final int hour = Integer.parseInt(splitTime[TIME_HOUR_INDEX]);
        final int min = Integer.parseInt(splitTime[TIME_MIN_INDEX]);
        return LocalTime.of(hour, min);
    }
}
```
###### \java\seedu\address\model\reminder\TimeTest.java
``` java
public class TimeTest {

    @Test
    public void isValidTime() {
        // invalid 24-hr time -> returns false
        assertFalse(Time.isValidTime("24:00"));
        assertFalse(Time.isValidTime("08:60"));

        // incorrect time input format -> returns false
        assertFalse(Time.isValidTime("8AM"));
        assertFalse(Time.isValidTime("0800"));

        // input is not even a time -> returns false
        assertFalse(Time.isValidTime("Not a time"));

        // valid time -> returns true
        assertTrue(Time.isValidTime("00:00"));
        assertTrue(Time.isValidTime("08:00"));
        assertTrue(Time.isValidTime("23:59"));
    }

    @Test
    public void equals() throws Exception {

        Time time1 = new Time("08:00");

        // different time -> returns false
        Time time2 = new Time("08:01");
        assertFalse(time1.equals(time2));

        // different type -> returns false
        assertFalse(time1.equals(100));

        // null -> returns false
        assertFalse(time1.equals(null));

        // same object -> returns true
        assertTrue(time1.equals(time1));

        // same value -> returns true
        Time time3 = new Time("08:00");
        assertTrue(time1.equals(time3));
    }
}
```
###### \java\seedu\address\model\reminder\UniqueReminderListTest.java
``` java
public class UniqueReminderListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueReminderList reminderList = new UniqueReminderList();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueReminderList uniqueReminderList = new UniqueReminderList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueReminderList.asObservableList().remove(0);
    }

    @Test
    public void constructor() {
        assertEquals(Collections.emptyList(), reminderList.asObservableList());
    }

    @Test
    public void constructor_withDuplicateReminders_throwsAssertionError() {
        // repeat coffee reminder twice
        List<ReadOnlyReminder> newReminders = Arrays.asList(COFFEE_REMINDER, COFFEE_REMINDER);
        XmlSerializableReminders serializableReminders = new XmlSerializableReminders(newReminders);

        thrown.expect(AssertionError.class);
        new UniqueReminderList(serializableReminders);
    }

    @Test
    public void contains_withReminderInside_returnTrue() throws Exception {
        reminderList.add(COFFEE_REMINDER);
        assertTrue(reminderList.contains(COFFEE_REMINDER));
    }

    @Test
    public void contains_withoutReminderInside_returnFalse() {
        assertFalse(reminderList.contains(COFFEE_REMINDER));
    }

    @Test
    public void add_null_throwsNullPointerException() throws DuplicateReminderException {
        thrown.expect(NullPointerException.class);
        reminderList.add(null);
    }

    @Test
    public void size_initialSizeZero() throws Exception {
        assertEquals(0, reminderList.size());

        // after adding one Reminder, size should increase by 1
        reminderList.add(COFFEE_REMINDER);
        assertEquals(1, reminderList.size());
    }

    @Test
    public void remove_null_throwsNullPointerException() throws ReminderNotFoundException {
        thrown.expect(NullPointerException.class);
        reminderList.remove(null);
    }

    @Test
    public void setReminders_sameReminders() {
        reminderList.setReminders(getUniqueTypicalReminders());
        assertEquals(reminderList, getUniqueTypicalReminders());
    }

    @Test
    public void equals() throws Exception {

        // null -> returns false
        assertFalse(reminderList.equals(null));

        // different types -> returns false
        assertFalse(reminderList.equals(0));

        // different reminder -> return false
        reminderList.setReminders(getUniqueTypicalReminders());
        UniqueReminderList differentReminderList = new UniqueReminderList();
        differentReminderList.setReminders(getUniqueTypicalReminders());
        differentReminderList.add(MEETING_REMINDER);
        assertFalse(reminderList.equals(differentReminderList));

        // same object -> returns true
        assertTrue(reminderList.equals(reminderList));

        // same reminders -> returns true
        assertTrue(reminderList.equals(getUniqueTypicalReminders()));
    }
}
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void remindersReadSave() throws Exception {
        UniqueReminderList original = getUniqueTypicalReminders();
        storageManager.saveReminders(original);
        UniqueReminderList retrieved = new UniqueReminderList(storageManager.readReminders().get());
        assertEquals(original, retrieved);
    }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void getRemindersFilePath() {
        assertNotNull(storageManager.getRemindersFilePath());
    }
```
###### \java\seedu\address\storage\StorageManagerTest.java
``` java
    @Test
    public void handleRemindersChangedEvent_exceptionThrown_eventRaised() {
        // Create a StorageManager while injecting a stub that  throws an exception when the save method is called
        Storage storage = new StorageManager(new XmlAddressBookStorage("dummy"),
                new XmlRemindersStorageExceptionThrowingStub("dummy"),
                new JsonUserPrefsStorage("dummy"), new ImageDisplayPictureStorage());
        storage.handleRemindersChangedEvent(new RemindersChangedEvent(new UniqueReminderList()));
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof DataSavingExceptionEvent);
    }


    /**
     * A Stub class to throw an exception when the save method is called
     */
    class XmlRemindersStorageExceptionThrowingStub extends XmlRemindersStorage {

        public XmlRemindersStorageExceptionThrowingStub(String filePath) {
            super(filePath);
        }

        @Override
        public void saveReminders(ReadOnlyUniqueReminderList reminderList, String filePath) throws IOException {
            throw new IOException("dummy exception");
        }
    }
```
###### \java\seedu\address\storage\XmlRemindersStorageTest.java
``` java
public class XmlRemindersStorageTest {
    private static final String TEST_DATA_FOLDER = FileUtil.getPath("./src/test/data/XmlRemindersStorageTest/");

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Rule
    public TemporaryFolder testFolder = new TemporaryFolder();

    @Test
    public void readAddressBook_nullFilePath_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        readReminders(null);
    }

    private java.util.Optional<ReadOnlyUniqueReminderList> readReminders(String filePath) throws Exception {
        return new XmlRemindersStorage(filePath).readReminders(addToTestDataPathIfNotNull(filePath));
    }

    private String addToTestDataPathIfNotNull(String prefsFileInTestDataFolder) {
        return prefsFileInTestDataFolder != null
                ? TEST_DATA_FOLDER + prefsFileInTestDataFolder
                : null;
    }

    @Test
    public void read_missingFile_emptyResult() throws Exception {
        assertFalse(readReminders("NonExistentFile.xml").isPresent());
    }

    @Test
    public void read_notXmlFormat_exceptionThrown() throws Exception {
        thrown.expect(DataConversionException.class);
        readReminders("NotXmlFormatReminders.xml");
    }

    @Test
    public void readAndSaveReminders_allInOrder_success() throws Exception {
        String filePath = testFolder.getRoot().getPath() + "TempReminders.xml";
        UniqueReminderList original = getUniqueTypicalReminders();
        XmlRemindersStorage xmlRemindersStorage = new XmlRemindersStorage(filePath);

        //Save in new file and read back
        xmlRemindersStorage.saveReminders(original, filePath);
        ReadOnlyUniqueReminderList readBack = xmlRemindersStorage.readReminders(filePath).get();
        assertEquals(original, new UniqueReminderList(readBack));

        //Modify data, overwrite exiting file, and read back
        original.add(new Reminder(MEETING_REMINDER));
        original.remove(new Reminder(HOMEWORK_REMINDER));
        xmlRemindersStorage.saveReminders(original, filePath);
        readBack = xmlRemindersStorage.readReminders(filePath).get();
        assertEquals(original, new UniqueReminderList(readBack));

        //Save and read without specifying file path
        original.add(new Reminder(DENTIST_REMINDER));
        xmlRemindersStorage.saveReminders(original); //file path not specified
        readBack = xmlRemindersStorage.readReminders().get(); //file path not specified
        assertEquals(original, new UniqueReminderList(readBack));

    }

    @Test
    public void saveReminders_nullReminders_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        saveReminders(null, "SomeFile.xml");
    }

    @Test
    public void getReminderList_modifyList_throwsUnsupportedOperationException() {
        UniqueReminderList reminderList = new UniqueReminderList();
        thrown.expect(UnsupportedOperationException.class);
        reminderList.asObservableList().remove(0);
    }

    /**
     * Saves {@code reminders} at the specified {@code filePath}.
     */
    private void saveReminders(UniqueReminderList reminders, String filePath) {
        try {
            new XmlRemindersStorage(filePath).saveReminders(reminders, addToTestDataPathIfNotNull(filePath));
        } catch (IOException ioe) {
            throw new AssertionError("There should not be an error writing to the file.", ioe);
        }
    }

    @Test
    public void saveReminders_nullFilePath_throwsNullPointerException() throws IOException {
        thrown.expect(NullPointerException.class);
        saveReminders(new UniqueReminderList(), null);
    }

}

```
###### \java\seedu\address\TestApp.java
``` java
        this.saveFileLocation = saveFileLocation;
        this.saveReminderFileLocation = saveReminderFileLocation;
```
###### \java\seedu\address\TestApp.java
``` java
        if (initialReminderDataSupplier.get() != null) {
            createDataFileWithData(new XmlSerializableReminders(),
                    this.saveReminderFileLocation);
        }
```
###### \java\seedu\address\TestApp.java
``` java
        userPrefs.setRemindersFilePath(saveReminderFileLocation);
```
###### \java\seedu\address\TestApp.java
``` java
    /**
     * Returns a defensive copy of the reminder data stored inside the storage file.
     */
    public UniqueReminderList readStorageUniqueReminderList() {
        try {
            return new UniqueReminderList(storage.readReminders().get());
        } catch (DataConversionException dce) {
            throw new AssertionError("Data is not in the AddressBook format.");
        } catch (IOException ioe) {
            throw new AssertionError("Storage file cannot be found.");
        }
    }
```
###### \java\seedu\address\testutil\EditReminderDescriptorBuilder.java
``` java
/**
 * A utility class to help with building EditReminderDescriptor objects.
 */
public class EditReminderDescriptorBuilder {

    private EditReminderDescriptor descriptor;

    public EditReminderDescriptorBuilder() {
        descriptor = new EditReminderDescriptor();
    }

    public EditReminderDescriptorBuilder(EditReminderDescriptor descriptor) {
        this.descriptor = new EditReminderDescriptor(descriptor);
    }

    public EditReminderDescriptorBuilder(Reminder reminder) {
        descriptor = new EditReminderDescriptor();
        descriptor.setReminder(reminder.getReminder());
        descriptor.setDate(reminder.getDate());
        descriptor.setTime(reminder.getTime());
    }

    /**
     * Sets the reminder of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withReminder(String reminder) {
        if (reminder.trim().isEmpty()) {
            throw new IllegalArgumentException("reminder is expected to be unique.");
        }
        descriptor.setReminder(reminder);
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withDate(String date) {
        try {
            ParserUtil.parseDate(Optional.of(date)).ifPresent(descriptor::setDate);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code EditReminderDescriptor} that we are building.
     */
    public EditReminderDescriptorBuilder withTime(String time) {
        try {
            ParserUtil.parseTime(Optional.of(time)).ifPresent(descriptor::setTime);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("time is expected to be unique.");
        }
        return this;
    }

    public EditReminderDescriptor build() {
        return descriptor;
    }
}
```
###### \java\seedu\address\testutil\ReminderBuilder.java
``` java
/**
 * A utility class to help with building Reminder objects.
 */
public class ReminderBuilder {

    public static final String DEFAULT_REMINDER = "Drink coffee";
    public static final String DEFAULT_DATE = "01/11/2017";
    public static final String DEFAULT_TIME = "08:00";

    private Reminder reminder;

    public ReminderBuilder() {
        try {
            Date date = new Date(DEFAULT_DATE);
            Time time = new Time(DEFAULT_TIME);
            this.reminder = new Reminder(DEFAULT_REMINDER, date, time);
        } catch (IllegalValueException ive) {
            throw new AssertionError("Default values cannot be wrong!");
        }
    }

    /**
     * Initializes the ReminderBuilder with the data of {@code reminderToCopy}.
     */
    public ReminderBuilder(ReadOnlyReminder reminderToCopy) {
        this.reminder = new Reminder(reminderToCopy);
    }

    /**
     * Sets the {@code reminder} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withReminder(String reminder) {
        this.reminder.setReminder(reminder);
        return this;
    }

    /**
     * Sets the {@code Time} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withTime(String time) {
        try {
            this.reminder.setTime(new Time(time));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Reminder} that we are building.
     */
    public ReminderBuilder withDate(String date) {
        try {
            this.reminder.setDate(new Date(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    public Reminder build() {
        return this.reminder;
    }
}
```
###### \java\seedu\address\testutil\ReminderUtil.java
``` java
/**
 * A utility class for Reminder.
 */
public class ReminderUtil {

    /**
     * Returns an add reminder command string for adding the {@code reminder}.
     */
    public static String getAddReminderCommand(ReadOnlyReminder reminder) {
        return AddReminderCommand.COMMAND_WORD + " " + getReminderDetails(reminder);
    }

    /**
     * Returns the part of command string for the given {@code reminder}'s details.
     */
    public static String getReminderDetails(ReadOnlyReminder reminder) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_REMINDER + reminder.getReminder() + " ");
        sb.append(PREFIX_DATE + reminder.getDate().value + " ");
        sb.append(PREFIX_TIME + reminder.getTime().value + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\testutil\TypicalReminders.java
``` java
/**
 * A utility class containing a list of {@code Reminder} objects to be used in tests.
 */
public class TypicalReminders {

    public static final ReadOnlyReminder COFFEE_REMINDER = new ReminderBuilder().build();
    public static final ReadOnlyReminder HOMEWORK_REMINDER = new ReminderBuilder().withReminder("Do homework")
                                                        .withDate("01/01/2018").withTime("07:30").build();
    public static final ReadOnlyReminder DINNER_REMINDER = new ReminderBuilder().withReminder("Dinner with family")
                                                        .withDate("25/12/2017").withTime("18:00").build();

    // Manually added
    public static final ReadOnlyReminder MEETING_REMINDER = new ReminderBuilder().withReminder("Meet with CS2103 group")
                                                  .withDate("09/09/2017").withTime("12:00").build();
    public static final ReadOnlyReminder DENTIST_REMINDER = new ReminderBuilder().withReminder("Go for dental checkup")
            .withDate("10/10/2017").withTime("14:00").build();

    private TypicalReminders() {} //prevents instantiation

    public static List<ReadOnlyReminder> getTypicalReminders() {
        return new ArrayList<>(Arrays.asList(COFFEE_REMINDER, HOMEWORK_REMINDER, DINNER_REMINDER));
    }

    /**
     * Utility method to return a UniqueReminderList containing a list of {@code Reminder} objects
     * to be used in tests.
     */
    public static UniqueReminderList getUniqueTypicalReminders() {
        return new UniqueReminderList(new XmlSerializableReminders(getTypicalReminders()));
    }
}
```
###### \java\seedu\address\ui\BirthdayReminderCardTest.java
``` java
    @Test
    public void display() {
        // no nickname
        Person personWithNoNickName = new PersonBuilder().withNickname("").build();
        BirthdayReminderCard birthdayCard = new BirthdayReminderCard(personWithNoNickName, 1);
        uiPartRule.setUiPart(birthdayCard);
        assertCardDisplay(birthdayCard, personWithNoNickName, 1);

        // with nickname
        Person personWithNickname = new PersonBuilder().withNickname("nickname").build();
        birthdayCard = new BirthdayReminderCard(personWithNickname, 2);
        uiPartRule.setUiPart(birthdayCard);
        assertCardDisplay(birthdayCard, personWithNickname, 2);

        //changes made to Person reflects on card
        guiRobot.interact(() -> {
            personWithNickname.setName(ALICE.getName());
            personWithNickname.setNickname(ALICE.getNickname());
        });
        assertCardDisplay(birthdayCard, personWithNickname, 2);
    }

    @Test
    public void equals() {
        Person person = new PersonBuilder().build();
        BirthdayReminderCard birthdayCard = new BirthdayReminderCard(person, 0);

        // same person, same index -> returns true
        BirthdayReminderCard copy = new BirthdayReminderCard(person, 0);
        assertTrue(birthdayCard.equals(copy));

        // same object -> returns true
        assertTrue(birthdayCard.equals(birthdayCard));

        // null -> returns false
        assertFalse(birthdayCard.equals(null));

        // different types -> returns false
        assertFalse(birthdayCard.equals(0));

        // different person, same index -> returns false
        Person differentPerson = new PersonBuilder().withName("different name").build();
        assertFalse(birthdayCard.equals(new BirthdayReminderCard(differentPerson, 0)));

        // same person, different index -> returns false
        assertFalse(birthdayCard.equals(new BirthdayReminderCard(person, 1)));
    }
```
###### \java\seedu\address\ui\ReminderCardTest.java
``` java
    @Test
    public void display() {

        Reminder reminder = new ReminderBuilder().build();
        ReminderCard reminderCard = new ReminderCard(reminder, 1);
        uiPartRule.setUiPart(reminderCard);
        assertCardDisplay(reminderCard, reminder, 1);

        // changes made to Reminder reflects on card
        guiRobot.interact(() -> {
            reminder.setReminder(HOMEWORK_REMINDER.getReminder());
            reminder.setDate(HOMEWORK_REMINDER.getDate());
            reminder.setTime(HOMEWORK_REMINDER.getTime());
        });
        assertCardDisplay(reminderCard, reminder, 1);
    }

    @Test
    public void equals() {
        Reminder reminder = new ReminderBuilder().build();
        ReminderCard reminderCard = new ReminderCard(reminder, 0);

        // same reminder, same index -> returns true
        ReminderCard copy = new ReminderCard(reminder, 0);
        assertTrue(reminderCard.equals(copy));

        // same object -> returns true
        assertTrue(reminderCard.equals(reminderCard));

        // null -> returns false
        assertFalse(reminderCard.equals(null));

        // different types -> returns false
        assertFalse(reminderCard.equals(0));

        // different reminder, same index -> returns false
        Reminder differentReminder = new ReminderBuilder().withReminder("different reminder").build();
        assertFalse(reminderCard.equals(new ReminderCard(differentReminder, 0)));

        // same reminder, different index -> returns false
        assertFalse(reminderCard.equals(new ReminderCard(reminder, 1)));
    }
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertBirthdayReminderCardDisplaysPerson(ReadOnlyPerson expectedPerson,
                                                                BirthdayReminderCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getBirthday().value, actualCard.getBirthday());
        assertEquals(expectedPerson.getNickname().value, actualCard.getNickname());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedReminder}.
     */
    public static void assertReminderCardDisplaysReminder(Reminder expectedReminder,
                                                                ReminderCardHandle actualCard) {
        assertEquals(expectedReminder.getReminder(), actualCard.getReminder());
        assertEquals(expectedReminder.getDate().toString(), actualCard.getDate());
        assertEquals(expectedReminder.getTime().toString(), actualCard.getTime());
    }
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /*Case: add a person with all fields same as another person in the address book except birthday -> added */
        toAdd = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY)
                .withAddress(VALID_ADDRESS_AMY).withBirthday(VALID_BIRTHDAY_BOB).withTags(VALID_TAG_FRIEND).build();
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + BIRTHDAY_DESC_BOB + TAG_DESC_FRIEND;
        assertCommandSuccess(command, toAdd);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: missing birthday -> added */
        assertCommandSuccess(HARRY);
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
        /* Case: invalid birthday -> rejected */
        command = AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY + EMAIL_DESC_AMY + ADDRESS_DESC_AMY
                + INVALID_BIRTHDAY_DESC;
        assertCommandFailure(command, Birthday.MESSAGE_BIRTHDAY_CONSTRAINTS);
```
###### \java\systemtests\AddReminderCommandSystemTest.java
``` java
public class AddReminderCommandSystemTest extends UniqueReminderListSystemTest {

    @Test
    public void addreminder() throws Exception {
        Model model = getModel();

        /* Case: add a reminder, command with leading spaces and trailing spaces
         * -> added
         */
        ReadOnlyReminder toAdd = COFFEE_REMINDER;
        String command = "   " + AddReminderCommand.COMMAND_WORD + "  " + REMINDER_DESC_COFFEE + "  "
                + REMINDER_DESC_DATE_COFFEE + "  " + REMINDER_DESC_TIME_COFFEE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a duplicate reminder -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE
                + REMINDER_DESC_TIME_COFFEE;
        assertCommandFailure(command, AddReminderCommand.MESSAGE_DUPLICATE_REMINDER);

        /* Case: add a reminder with all fields same as another reminder in the reminder list except reminder -> added */
        toAdd = new ReminderBuilder().withReminder(VALID_REMINDER_ASSIGNMENT).withDate(VALID_REMINDER_DATE_COFFEE)
                .withTime(VALID_REMINDER_TIME_COFFEE).build();
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_ASSIGNMENT + REMINDER_DESC_DATE_COFFEE
                + REMINDER_DESC_TIME_COFFEE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a reminder with all fields same as another reminder in the reminder list except date -> added */
        toAdd = new ReminderBuilder().withReminder(VALID_REMINDER_COFFEE).withDate(VALID_REMINDER_DATE_ASSIGNMENT)
                .withTime(VALID_REMINDER_TIME_COFFEE).build();
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_ASSIGNMENT
                + REMINDER_DESC_TIME_COFFEE;
        assertCommandSuccess(command, toAdd);

        /* Case: add a reminder with all fields same as another reminder in the reminder list except time -> added */
        toAdd = new ReminderBuilder().withReminder(VALID_REMINDER_COFFEE).withDate(VALID_REMINDER_DATE_COFFEE)
                .withTime(VALID_REMINDER_TIME_ASSIGNMENT).build();
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_COFFEE + REMINDER_DESC_DATE_COFFEE
                + REMINDER_DESC_TIME_ASSIGNMENT;
        assertCommandSuccess(command, toAdd);

        /* Case: add to empty reminder list -> added */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getPersonList().size() == 0;
        assertCommandSuccess(MEETING_REMINDER);

        /* Case: missing reminder -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_DATE_ASSIGNMENT + REMINDER_DESC_TIME_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_ASSIGNMENT + REMINDER_DESC_TIME_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));

        /* Case: missing time -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_ASSIGNMENT + REMINDER_DESC_DATE_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));

        /* Case: invalid keyword -> rejected */
        command = "addreminders " + ReminderUtil.getReminderDetails(HOMEWORK_REMINDER);
        assertCommandFailure(command, Messages.MESSAGE_UNKNOWN_COMMAND);

        /* Case: invalid reminder -> rejected */
        command = AddReminderCommand.COMMAND_WORD + INVALID_REMINDER_DESC + REMINDER_DESC_DATE_ASSIGNMENT
                + REMINDER_DESC_TIME_ASSIGNMENT;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddReminderCommand.MESSAGE_USAGE));

        /* Case: invalid date -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_ASSIGNMENT + INVALID_REMINDER_DESC_DATE
                + REMINDER_DESC_TIME_ASSIGNMENT;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid time -> rejected */
        command = AddReminderCommand.COMMAND_WORD + REMINDER_DESC_ASSIGNMENT + REMINDER_DESC_DATE_ASSIGNMENT
                + INVALID_REMINDER_DESC_TIME;
        assertCommandFailure(command, Time.MESSAGE_TIME_CONSTRAINTS);

    }

    /**
     * Executes the {@code AddReminderCommand} that adds {@code toAdd} to the model and verifies that the command box
     * displays an empty string, the result display box displays the success message of executing
     * {@code AddReminderCommand} with the details of {@code toAdd}, and the model related components equal to the
     * current model added with {@code toAdd}. These verifications are done by
     * {@code UniqueReminderListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class.
     * @see UniqueReminderListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandSuccess(ReadOnlyReminder toAdd) {
        assertCommandSuccess(ReminderUtil.getAddReminderCommand(toAdd), toAdd);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyReminder)}. Executes {@code command}
     * instead.
     * @see AddReminderCommandSystemTest#assertCommandSuccess(ReadOnlyReminder)
     */
    private void assertCommandSuccess(String command, ReadOnlyReminder toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addReminder(toAdd);
        } catch (DuplicateReminderException dpe) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        String expectedResultMessage = String.format(AddReminderCommand.MESSAGE_SUCCESS, toAdd);


        assertCommandSuccess(command, expectedModel, expectedResultMessage);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyReminder)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddReminderCommandSystemTest#assertCommandSuccess(String, ReadOnlyReminder)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
    }

    /**
     * Executes {@code command} and verifies that the command box displays {@code command}, the result display
     * box displays {@code expectedResultMessage} and the model related components equal to the current model.
     * These verifications are done by
     * {@code UniqueReminderListSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the status bar remain unchanged, and the command box has the
     * error style.
     * @see UniqueReminderListSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     */
    private void assertCommandFailure(String command, String expectedResultMessage) {
        Model expectedModel = getModel();

        executeCommand(command);
        assertApplicationDisplaysExpected(command, expectedResultMessage, expectedModel);
        assertCommandBoxShowsErrorStyle();
    }
}
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
    @Test
    public void editTestClearingBirthday() throws Exception {
        Model model = getModel();

        /* ----------------- Performing edit operation while an unfiltered list is being shown ---------------------- */
        /* ----------------- This test only consist of performing the clearing of birthday field -------------------- */


        /* Case: clear birthday -> cleared */
        Index index = INDEX_FIRST_PERSON;
        ReadOnlyPerson personToEdit = getModel().getFilteredPersonList().get(index.getZeroBased());
        String command = EditCommand.COMMAND_WORD + " " + index.getOneBased() + " " + PREFIX_BIRTHDAY.getPrefix();
        Person editedPerson = new PersonBuilder(personToEdit).withBirthday("").build();
        assertCommandSuccess(command, index, editedPerson);
    }
```
###### \java\systemtests\SystemTestSetupHelper.java
``` java
    /**
     * Sets up the {@code TestApp} and returns it.
     */
    public TestApp setupApplication() {
        try {
            FxToolkit.setupApplication(() -> testApp = new TestApp(TypicalPersons::getTypicalAddressBook,
                    UniqueReminderList::new, TestApp.SAVE_LOCATION_FOR_TESTING,
                    TestApp.SAVE_LOCATION_FOR_REMINDER_TESTING));
        } catch (TimeoutException te) {
            throw new AssertionError("Application takes too long to set up.");
        }

        return testApp;
    }
```
###### \java\systemtests\UniqueReminderListSystemTest.java
``` java
public class UniqueReminderListSystemTest {
    @ClassRule
    public static ClockRule clockRule = new ClockRule();

    private static final List<String> COMMAND_BOX_DEFAULT_STYLE = Arrays.asList("text-input", "text-field");
    private static final List<String> COMMAND_BOX_ERROR_STYLE =
            Arrays.asList("text-input", "text-field", CommandBox.ERROR_STYLE_CLASS);

    private MainWindowHandle mainWindowHandle;
    private TestApp testApp;
    private SystemTestSetupHelper setupHelper;

    @BeforeClass
    public static void setupBeforeClass() {
        SystemTestSetupHelper.initializeStage();
    }

    @Before
    public void setUp() {
        setupHelper = new SystemTestSetupHelper();
        testApp = setupHelper.setupApplication();
        mainWindowHandle = setupHelper.setupMainWindowHandle();

        waitUntilBrowserLoaded(getBrowserPanel());
        assertApplicationStartingStateIsCorrect();
    }

    @After
    public void tearDown() throws Exception {
        setupHelper.tearDownStage();
        EventsCenter.clearSubscribers();
    }

    public CommandBoxHandle getCommandBox() {
        return mainWindowHandle.getCommandBox();
    }

    public PersonListPanelHandle getPersonListPanel() {
        return mainWindowHandle.getPersonListPanel();
    }

    public BrowserAndRemindersPanelHandle getBrowserPanel() {
        return mainWindowHandle.getBrowserPanel();
    }

    public StatusBarFooterHandle getStatusBarFooter() {
        return mainWindowHandle.getStatusBarFooter();
    }

    public ResultDisplayHandle getResultDisplay() {
        return mainWindowHandle.getResultDisplay();
    }

    /**
     * Executes {@code command} in the application's {@code CommandBox}.
     * Method returns after UI components have been updated.
     */
    protected void executeCommand(String command) {
        rememberStates();
        // Injects a fixed clock before executing a command so that the time stamp shown in the status bar
        // after each command is predictable and also different from the previous command.
        clockRule.setInjectedClockToCurrentTime();

        mainWindowHandle.getCommandBox().run(command);

        waitUntilBrowserLoaded(getBrowserPanel());
    }

    /**
     * Asserts that the {@code CommandBox} displays {@code expectedCommandInput}, the {@code ResultDisplay} displays
     * {@code expectedResultMessage}, the model and storage contains the same reminder objects as {@code expectedModel}.
     */
    protected void assertApplicationDisplaysExpected(String expectedCommandInput, String expectedResultMessage,
                                                     Model expectedModel) {
        assertEquals(expectedCommandInput, getCommandBox().getInput());
        assertEquals(expectedResultMessage, getResultDisplay().getText());
        assertEquals(expectedModel, getModel());
        assertEquals(expectedModel.getUniqueReminderList(), testApp.readStorageUniqueReminderList());
    }

    /**
     * Calls {@code BrowserAndRemindersPanelHandle},
     * {@code PersonListPanelHandle} and {@code StatusBarFooterHandle} to remember their current state.
     */
    private void rememberStates() {
        StatusBarFooterHandle statusBarFooterHandle = getStatusBarFooter();
        getBrowserPanel().rememberUrl();
        statusBarFooterHandle.rememberSaveLocation();
        statusBarFooterHandle.rememberSyncStatus();
        getPersonListPanel().rememberSelectedPersonCard();
    }

    /**
     * Asserts that the browser's url and the selected card in the person list panel remain unchanged.
     * @see BrowserAndRemindersPanelHandle#isUrlChanged()
     * @see PersonListPanelHandle#isSelectedPersonCardChanged()
     */
    protected void assertSelectedCardUnchanged() {
        assertFalse(getBrowserPanel().isUrlChanged());
        assertFalse(getPersonListPanel().isSelectedPersonCardChanged());
    }

    /**
     * Asserts that the command box's shows the default style.
     */
    protected void assertCommandBoxShowsDefaultStyle() {
        assertEquals(COMMAND_BOX_DEFAULT_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the command box's shows the error style.
     */
    protected void assertCommandBoxShowsErrorStyle() {
        assertEquals(COMMAND_BOX_ERROR_STYLE, getCommandBox().getStyleClass());
    }

    /**
     * Asserts that the entire status bar remains the same.
     */
    protected void assertStatusBarUnchanged() {
        StatusBarFooterHandle handle = getStatusBarFooter();
        assertFalse(handle.isSaveLocationChanged());
        assertFalse(handle.isSyncStatusChanged());
    }

    /**
     * Asserts that the starting state of the application is correct.
     */
    private void assertApplicationStartingStateIsCorrect() {
        try {
            assertEquals("", getCommandBox().getInput());
            assertEquals("", getResultDisplay().getText());
            assertListMatching(getPersonListPanel(), getModel().getFilteredPersonList());
            assertEquals(MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE), getBrowserPanel().getLoadedUrl());
            assertEquals("./" + testApp.getStorageSaveLocation(), getStatusBarFooter().getSaveLocation());
            assertEquals(SYNC_STATUS_INITIAL, getStatusBarFooter().getSyncStatus());
        } catch (Exception e) {
            throw new AssertionError("Starting state is wrong.", e);
        }
    }

    /**
     * Returns a defensive copy of the current model.
     */
    protected Model getModel() {
        return testApp.getModel();
    }
}
```
