# jo-lyn
###### \java\guitests\guihandles\PersonDetailPanelHandle.java
``` java
/**
 * A handle for the person detail panel of the App
 */
public class PersonDetailPanelHandle extends NodeHandle<Node> {
    public static final String PERSON_DETAIL_ID = "#personDetailPanel";
    private static final String NAME_FIELD_ID = "#name";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String TAGS_FIELD_ID = "#tags";

    private Label nameLabel;
    private Label addressLabel;
    private Label phoneLabel;
    private Label emailLabel;
    private List<Label> tagLabels;

    public PersonDetailPanelHandle(Node personDetailNode) {
        super(personDetailNode);

        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);
        this.emailLabel = getChildNode(EMAIL_FIELD_ID);

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

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Update tags in person detail panel
     */
    public void updateTags() {
        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }
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
        assertEquals(OpenCommand.COMMAND_WORD + " ", getCommandBox().getInput());
    }

    @Test
    public void executeKeyEventForNewCommand() {
        KeyCodeCombination newKeyCode = (KeyCodeCombination) KeyCombination.valueOf("Ctrl+N");

        guiRobot.push(newKeyCode);
        assertEquals(NewCommand.COMMAND_WORD + " ", getCommandBox().getInput());
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
###### \java\seedu\address\model\ModelManagerTest.java
``` java
    @Test
    public void removeTag() throws IllegalValueException, PersonNotFoundException {

        Set<Tag> tagSet1 = SampleDataUtil.getTagSet("friends", "classmate");
        Set<Tag> tagSet2 = SampleDataUtil.getTagSet("owesMoney", "classmate");

        Person person1 = new Person(ALICE);
        person1.setTags(tagSet1);

        Person person2 = new Person(BENSON);
        person2.setTags(tagSet2);

        Rolodex rolodex = new RolodexBuilder().withPerson(person1).withPerson(person2).build();
        UserPrefs userPrefs = new UserPrefs();

        ModelManager modelManager = new ModelManager(rolodex, userPrefs);

        Tag tagToRemove = new Tag("classmate");
        modelManager.removeTag(tagToRemove);

        Set<Tag> tagSet1New = SampleDataUtil.getTagSet("friends");
        Set<Tag> tagSet2New = SampleDataUtil.getTagSet("owesMoney");

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
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        assertPanelDisplaysPerson(ALICE, personDetailPanelHandle);

        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
        assertPanelDisplaysPerson(BOB, personDetailPanelHandle);
    }

    /**
     * Asserts that {@code actualPerson} displays the details of {@code expectedPerson}.
     */
    private void assertPanelDisplaysPerson(ReadOnlyPerson expectedPerson, PersonDetailPanelHandle actualPerson) {
        guiRobot.pauseForHuman();

        assertEquals(expectedPerson.getName().toString(), actualPerson.getName());
        assertEquals(PERSON_PHONE_ICON + expectedPerson.getPhone().toString(), actualPerson.getPhone());
        assertEquals(PERSON_ADDRESS_ICON + expectedPerson.getAddress().toString(), actualPerson.getAddress());
        assertEquals(PERSON_EMAIL_ICON + expectedPerson.getEmail().toString(), actualPerson.getEmail());

        actualPerson.updateTags();
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualPerson.getTags());
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
