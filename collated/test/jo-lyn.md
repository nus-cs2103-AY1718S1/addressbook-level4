# jo-lyn
###### \java\guitests\guihandles\PersonDetailPanelHandle.java
``` java
/**
 * A handle for the person detail panel of the App.
 */
public class PersonDetailPanelHandle extends NodeHandle<Node> {
    public static final String PERSON_DETAIL_ID = "#personDetailPanel";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String REMARK_FIELD_ID = "#remark";
    private static final String TAGS_FIELD_ID = "#tagsWithBorder";

    private Label nameLabel;
    private Label addressLabel;
    private Label phoneLabel;
    private Label emailLabel;
    private Label remarkLabel;
    private List<Label> tagLabels;

    public PersonDetailPanelHandle(Node personDetailNode) {
        super(personDetailNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);
        this.remarkLabel = getChildNode(REMARK_FIELD_ID);
        updateTags();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getAddress() {
        return addressLabel.getText();
    }

    public String getPhone() {
        return phoneLabel.getText();
    }

    public String getEmail() {
        return emailLabel.getText();
    }

    public String getRemark() {
        return remarkLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Updates tags in person detail panel.
     */
    public void updateTags() {
        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    /**
     * Returns a copy of an empty tag list.
     */
    public List<String> getEmptyTagList() {
        List<String> tagLabelsCopy = getTags();
        tagLabelsCopy.clear();
        return tagLabelsCopy;
    }
}
```
###### \java\guitests\guihandles\PersonListPanelHandle.java
``` java
    /**
     * Returns true is the listview is focused.
     */
    public boolean isFocused() {
        return getRootNode().isFocused();
    }
```
###### \java\guitests\KeyListenerTest.java
``` java
public class KeyListenerTest extends RolodexGuiTest {

    @Test
    public void executeKeyEventForFocusOnCommandBox() {
        guiRobot.push(KeyCode.ENTER);
        assertTrue(getCommandBox().isFocused());
        guiRobot.push(KeyCode.A);
        assertTrue(getCommandBox().isFocused());

        guiRobot.push(KeyCode.ESCAPE);
        assertFalse(getCommandBox().isFocused());
    }

    @Test
    public void executeKeyEventForFocusOnPersonListPanel() {
        KeyCodeCombination focusKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Left");

        guiRobot.push(focusKeyCode);
        assertTrue(getPersonListPanel().isFocused());

        guiRobot.push(KeyCode.ENTER);
        assertFalse(getPersonListPanel().isFocused());

        guiRobot.push(KeyCode.ESCAPE);
        assertTrue(getPersonListPanel().isFocused());

        // Check scrolling
        guiRobot.push(KeyCode.UP);
        assertTrue(getPersonListPanel().isFocused());
        guiRobot.push(KeyCode.DOWN);
        assertTrue(getPersonListPanel().isFocused());
    }

    @Test
    public void executeKeyEventForFocusOnResultDisplayPanel() {
        KeyCodeCombination focusKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Right");
        guiRobot.push(focusKeyCode);
        assertTrue(getResultDisplay().isFocused());

        guiRobot.push(KeyCode.ENTER);
        assertFalse(getPersonListPanel().isFocused());
    }

    @Test
    public void executeKeyEventForOpenCommand() {
        KeyCodeCombination openKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+O");

        guiRobot.push(openKeyCode);
        assertEquals(OpenRolodexCommand.COMMAND_WORD + " ", getCommandBox().getInput());
    }

    @Test
    public void executeKeyEventForNewCommand() {
        KeyCodeCombination newKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+N");

        guiRobot.push(newKeyCode);
        assertEquals(NewRolodexCommand.COMMAND_WORD + " ", getCommandBox().getInput());
    }

    @Test
    public void executeKeyEventForUndoCommand() {
        KeyCodeCombination undoKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Z");

        // Empty undo stack
        guiRobot.push(undoKeyCode);
        assertEquals(UndoCommand.MESSAGE_FAILURE, getResultDisplay().getText());

        getCommandBox().run("delete 1");
        guiRobot.push(undoKeyCode);
        assertEquals(UndoCommand.MESSAGE_SUCCESS, getResultDisplay().getText());
    }

    @Test
    public void executeKeyEventForRedoCommand() {
        KeyCodeCombination redoKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Y");

        // Empty redo stack
        guiRobot.push(redoKeyCode);
        assertEquals(RedoCommand.MESSAGE_FAILURE, getResultDisplay().getText());

        getCommandBox().run("delete 1");
        getCommandBox().run("undo");

        guiRobot.push(redoKeyCode);
        assertEquals(RedoCommand.MESSAGE_SUCCESS, getResultDisplay().getText());
    }

    @Test
    public void executeKeyEventForClearCommand() {
        KeyCodeCombination clearKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+Shift+C");

        guiRobot.push(clearKeyCode);
        assertEquals(ClearCommand.MESSAGE_SUCCESS, getResultDisplay().getText());
    }

    @Test
    public void executeKeyEventForListCommand() {
        KeyCodeCombination listKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+L");

        guiRobot.push(listKeyCode);
        assertEquals(ListCommand.MESSAGE_SUCCESS, getResultDisplay().getText());
    }

    @Test
    public void executeKeyEventForHistoryCommand() {
        KeyCodeCombination viewHistoryKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+H");

        guiRobot.push(viewHistoryKeyCode);
        assertEquals(HistoryCommand.MESSAGE_NO_HISTORY, getResultDisplay().getText());

        String command1 = "clear";
        getCommandBox().run(command1);
        guiRobot.push(viewHistoryKeyCode);

        String expectedMessage = String.format(HistoryCommand.MESSAGE_SUCCESS,
                String.join("\n", command1, "history"));

        assertEquals(expectedMessage, getResultDisplay().getText());
    }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public Index getIndex(ReadOnlyPerson person) {
            fail("This method should not be called.");
            return null;
        }
```
###### \java\seedu\address\logic\commands\AddCommandTest.java
``` java
        @Override
        public Index getIndex(ReadOnlyPerson person) {
            return Index.fromZeroBased(personsAdded.indexOf(new Person(person)));
        }
```
###### \java\seedu\address\logic\commands\EditPersonDescriptorTest.java
``` java
        // different remark -> returns false
        editedAmy = new EditPersonDescriptorBuilder(DESC_AMY).withRemark(VALID_REMARK_BOB).build();
        assertFalse(DESC_BOB.equals(editedAmy));
```
###### \java\seedu\address\logic\commands\ListCommandTest.java
``` java
    @Test
    public void executeListIsSortedByRemarkDefaultShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_REMARK_DEFAULT);
        assertCommandSuccess(listCommandRemarkDefault, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByRemarkDescendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_REMARK_DESCENDING);
        assertCommandSuccess(listCommandRemarkDescending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void executeListIsSortedByRemarkAscendingShowsSorted() {
        sortAllPersons(expectedModel, SORT_ARGUMENT_REMARK_ASCENDING);
        assertCommandSuccess(listCommandRemarkAscending, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
```
###### \java\seedu\address\logic\commands\RemarkCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for RemarkCommand.
 */
public class RemarkCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private Model model = new ModelManager(getTypicalRolodex(), new UserPrefs());

    @Test
    public void executeAddRemarkSuccess() throws Exception {
        Person editedPerson = new PersonBuilder(model.getLatestPersonList().get(INDEX_FIRST_PERSON.getZeroBased()))
                .withRemark("Some remark").build();

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new Rolodex(model.getRolodex()), new UserPrefs());
        expectedModel.updatePerson(model.getLatestPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeDeleteRemarkSuccess() throws Exception {
        Person editedPerson = new Person(model.getLatestPersonList().get(INDEX_FIRST_PERSON.getZeroBased()));
        editedPerson.setRemark(new Remark(""));

        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().toString());

        String expectedMessage = String.format(RemarkCommand.MESSAGE_DELETE_REMARK_SUCCESS, editedPerson);

        Model expectedModel = new ModelManager(new Rolodex(model.getRolodex()), new UserPrefs());
        expectedModel.updatePerson(model.getLatestPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeFilteredListSuccess() throws Exception {
        showFirstPersonOnly(model);

        ReadOnlyPerson personInFilteredList = model.getLatestPersonList().get(INDEX_FIRST_PERSON.getZeroBased());
        Person editedPerson = new PersonBuilder(personInFilteredList)
                .withRemark("Some remark").build();
        RemarkCommand remarkCommand = prepareCommand(INDEX_FIRST_PERSON, editedPerson.getRemark().value);

        String expectedMessage = String.format(RemarkCommand.MESSAGE_ADD_REMARK_SUCCESS, editedPerson);
        Model expectedModel = new ModelManager(new Rolodex(model.getRolodex()), new UserPrefs());
        expectedModel.updatePerson(model.getLatestPersonList().get(0), editedPerson);

        assertCommandSuccess(remarkCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void executeInvalidPersonIndexUnfilteredListFailure() throws Exception {
        Index outOfBoundIndex = Index.fromOneBased(model.getLatestPersonList().size() + 1);
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    /**
     * Edits filtered list where index is larger than size of filtered list,
     * but smaller than size of rolodex.
     */
    @Test
    public void executeInvalidPersonIndexFilteredListFailure() throws Exception {
        showFirstPersonOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_PERSON;

        // ensures that outOfBoundIndex is still in bounds of rolodex list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getRolodex().getPersonList().size());
        RemarkCommand remarkCommand = prepareCommand(outOfBoundIndex, VALID_REMARK_BOB);

        assertCommandFailure(remarkCommand, model, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        final RemarkCommand standardCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));

        // same values -> true
        RemarkCommand commandWithSameValues = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_AMY));
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> false
        assertFalse(standardCommand == null);

        // different types -> false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_SECOND_PERSON, new Remark(VALID_REMARK_AMY))));

        // different descriptor -> false
        assertFalse(standardCommand.equals(new RemarkCommand(INDEX_FIRST_PERSON, new Remark(VALID_REMARK_BOB))));
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}.
     */
    private RemarkCommand prepareCommand(Index index, String remark) {
        RemarkCommand remarkCommand = new RemarkCommand(index, new Remark(remark));
        remarkCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return remarkCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
        // multiple remarks - last remark accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_BOB + PHONE_DESC_BOB + EMAIL_DESC_BOB
                + ADDRESS_DESC_BOB + REMARK_DESC_AMY + REMARK_DESC_BOB + TAG_DESC_FRIEND,
                new AddCommand(expectedPerson));
```
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
    @Test
    public void parseOptionalFieldsMissingSuccess() {
        // zero tags
        Person expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withRemark(VALID_REMARK_AMY)
                .withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + REMARK_DESC_AMY, new AddCommand(expectedPerson));

        // no remark
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY + TAG_DESC_FRIEND, new AddCommand(expectedPerson));

        // no remark and no tags
        expectedPerson = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags().build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + NAME_DESC_AMY + PHONE_DESC_AMY
                + EMAIL_DESC_AMY + ADDRESS_DESC_AMY, new AddCommand(expectedPerson));
    }
```
###### \java\seedu\address\logic\parser\EditCommandParserTest.java
``` java
        // remark
        userInput = targetIndex.getOneBased() + REMARK_DESC_AMY;
        descriptor = new EditPersonDescriptorBuilder().withRemark(VALID_REMARK_AMY).build();
        expectedCommand = new EditCommand(targetIndex, descriptor);
        assertParseSuccess(parser, userInput, expectedCommand);
```
###### \java\seedu\address\logic\parser\parserutil\ParserUtilTest.java
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
    public void parseIndexSpecifiedFailure() throws Exception {
        final Remark remark = new Remark("Some remark.");

        // have remarks
        Index targetIndex = INDEX_FIRST_PERSON;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString() + " " + remark;
        RemarkCommand expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, remark);
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remarks
        userInput = targetIndex.getOneBased() + " " + PREFIX_REMARK.toString();
        expectedCommand = new RemarkCommand(INDEX_FIRST_PERSON, new Remark(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parseNoFieldSpecifiedFailure() throws Exception {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RemarkCommand.MESSAGE_USAGE);

        // nothing at all
        assertParseFailure(parser, RemarkCommand.COMMAND_WORD, expectedMessage);
    }
```
###### \java\seedu\address\logic\parser\RolodexParserTest.java
``` java
    @Test
    public void parseCommandRemark() throws Exception {
        final Remark remark = new Remark("Some remark.");
        RemarkCommand command = (RemarkCommand) parser.parseCommand(RemarkCommand.COMMAND_WORD + " "
            + INDEX_FIRST_PERSON.getOneBased() + " " + PREFIX_REMARK + " " + remark.value);
        assertEquals(new RemarkCommand(INDEX_FIRST_PERSON, remark), command);
    }
```
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void removeTag() throws IllegalValueException, PersonNotFoundException {

        Set<Tag> tagSet1 = getTagSet("friends", "classmate");
        Set<Tag> tagSet2 = getTagSet("owesMoney", "classmate");

        Person person1 = new Person(ALICE);
        person1.setTags(tagSet1);

        Person person2 = new Person(BENSON);
        person2.setTags(tagSet2);

        Rolodex rolodex = new RolodexBuilder().withPerson(person1).withPerson(person2).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(rolodex, userPrefs);

        Tag tagToRemove = new Tag("classmate");
        modelManager.removeTag(tagToRemove);

        Set<Tag> tagSet1New = getTagSet("friends");
        Set<Tag> tagSet2New = getTagSet("owesMoney");

        Person person1New = new Person(ALICE);
        person1.setTags(tagSet1New);
        Person person2New = new Person(BENSON);
        person2.setTags(tagSet2New);

        // check that tagToRemove from all persons are removed
        assertTrue(person1.equals(person1New));
        assertTrue(person2.equals(person2New));

        // check that tagSets are not equal because both are null
        assertFalse(person1.getTags().equals(null));
        assertFalse(person2.getTags().equals(null));
    }

    @Test
    public void getIndex() {
        Rolodex rolodex = new RolodexBuilder().withPerson(ALICE).withPerson(BENSON).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(rolodex, userPrefs);

        // Alice has first index
        Index expectedIndex = INDEX_FIRST_PERSON;
        Index actualIndex = modelManager.getIndex(ALICE);
        assertEquals(expectedIndex, actualIndex);

        // Benson has second index
        expectedIndex = INDEX_SECOND_PERSON;
        actualIndex = modelManager.getIndex(BENSON);
        assertEquals(expectedIndex, actualIndex);
    }
```
###### \java\seedu\address\model\person\RemarkTest.java
``` java
public class RemarkTest {

    @Test
    public void equals() {
        Remark remark = new Remark("Hello");

        // same object -> true
        assertTrue(remark.equals(remark));

        // same values -> true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.equals(remarkCopy));

        // different types -> false
        assertFalse(remark.equals(1));

        // null -> false
        assertFalse(remark == null);

        // different remark -> false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.equals(differentRemark));
    }

    @Test
    public void compareTo() {
        Remark remark = new Remark("Hey");

        // same object -> true
        assertTrue(remark.compareTo(remark) == 0);

        // same values -> true
        Remark remarkCopy = new Remark(remark.value);
        assertTrue(remark.compareTo(remarkCopy) == 0);

        // different remark -> false
        Remark differentRemark = new Remark("Bye");
        assertFalse(remark.compareTo(differentRemark) == 0);
    }
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
            throw new IllegalArgumentException("remark is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Remark} of the {@code Person} that we are building.
     */
    public PersonBuilder withRemark(String remark) {
        this.person.setRemark(new Remark(remark));
        return this;
    }
```
###### \java\seedu\address\ui\PersonDetailPanelTest.java
``` java
public class PersonDetailPanelTest extends GuiUnitTest {

    private PersonDetailPanel personDetailPanel;
    private PersonDetailPanelHandle personDetailPanelHandle;

    @Before
    public void setUp() {
        guiRobot.interact(() -> personDetailPanel = new PersonDetailPanel());
        uiPartRule.setUiPart(personDetailPanel);

        personDetailPanelHandle = new PersonDetailPanelHandle(getChildNode(personDetailPanel.getRoot(),
                PersonDetailPanelHandle.PERSON_DETAIL_ID));
    }

    @Test
    public void display() {

        // changes in person selection reflect on panel
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        assertPanelDisplaysPerson(ALICE, personDetailPanelHandle);

        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
        assertPanelDisplaysPerson(BOB, personDetailPanelHandle);

        // changes made to person reflect on panel
        postNow(new PersonEditedEvent(ALICE, INDEX_FIRST_PERSON));
        assertPanelDisplaysPerson(ALICE, personDetailPanelHandle);

        // panel is empty when list is cleared
        postNow(new ClearPersonDetailPanelRequestEvent());
        assertPanelDisplaysNothing(personDetailPanelHandle);
    }

    /**
     * Asserts that {@code panel} displays the details of {@code expectedPerson}.
     */
    private void assertPanelDisplaysPerson(ReadOnlyPerson expectedPerson, PersonDetailPanelHandle panel) {
        guiRobot.pauseForHuman();

        assertEquals(expectedPerson.getName().toString(), panel.getName());
        assertEquals(expectedPerson.getPhone().toString(), panel.getPhone());
        assertEquals(expectedPerson.getAddress().toString(), panel.getAddress());
        assertEquals(expectedPerson.getEmail().toString(), panel.getEmail());
        assertEquals(expectedPerson.getRemark().toString(), panel.getRemark());

        panel.updateTags();
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                panel.getTags());
    }

    /**
     * Asserts that {@code panel} displays nothing.
     */
    private void assertPanelDisplaysNothing(PersonDetailPanelHandle panel) {
        assertEquals("", panel.getName());
        assertEquals("", panel.getPhone());
        assertEquals("", panel.getAddress());
        assertEquals("", panel.getEmail());
        panel.updateTags();
        assertEquals(panel.getEmptyTagList(), panel.getTags());
    }
}
```
###### \java\seedu\address\ui\PersonListPanelTest.java
``` java
    @Test
    public void setFocus() {
        personListPanel.setFocus();
        assertTrue(personListPanelHandle.isFocused());
    }
```
###### \java\seedu\address\ui\ResultDisplayTest.java
``` java
        // prompt text
        assertEquals(WELCOME_TEXT, resultDisplayHandle.getPromptText());
```
###### \java\systemtests\AddCommandSystemTest.java
``` java
    /**
     * Performs the same verification as {@code assertCommandSuccess(String, ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}.
     * @see AddCommandSystemTest#assertCommandSuccess(String, ReadOnlyPerson)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
                                      ReadOnlyPerson person) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        assertStatusBarUnchangedExceptSyncStatus();
        if (person == null) {
            assertSelectedCardDeselected();
        } else {
            assertSelectedCardChanged(expectedModel.getIndex(person));
        }
    }
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        /* Case: edit some fields excluding name -> edited with no change in index */
        index = INDEX_FIRST_PERSON;
        command = COMMAND_WORD + " " + index.getOneBased() + TAG_DESC_FRIEND;
        personToEdit = getModel().getLatestPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withTags(VALID_TAG_FRIEND).build(personToEdit.getTags());
        assertCommandSuccess(command, index, editedPerson, index);

        /* Case: edit some fields including name -> edited with change in index */
        index = INDEX_SECOND_PERSON;
        command = COMMAND_WORD + " " + index.getOneBased() + NAME_DESC_AMY + PHONE_DESC_AMY;
        personToEdit = getModel().getLatestPersonList().get(index.getZeroBased());
        editedPerson = new PersonBuilder(personToEdit).withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
                .build(personToEdit.getTags());
        newIndex = INDEX_FIRST_PERSON;
        assertCommandSuccess(command, index, editedPerson, newIndex);
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find remark of person in rolodex -> 0 persons found */
        command = FindCommand.COMMAND_WORD + " " + GEORGE.getRemark().value;
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
```
