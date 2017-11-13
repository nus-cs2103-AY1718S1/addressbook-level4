# pohjie
###### \java\guitests\guihandles\TagCardHandle.java
``` java
/**
 * Provides a handle to a tag card in the tag list panel
 */
public class TagCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TAGSTRING_FIELD_ID = "#tagString";

    private final Label idLabel;
    private final Label tagStringLabel;

    public TagCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.tagStringLabel = getChildNode(TAGSTRING_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTagString() {
        return tagStringLabel.getText();
    }
}
```
###### \java\guitests\guihandles\TagListPanelHandle.java
``` java
/**
 * Provides a handle for {@code TagListPanel} containing list of unique tags
 */
public class TagListPanelHandle extends NodeHandle<ListView<TagCard>> {
    public static final String TAG_LIST_VIEW_ID = "#tagListView";

    public TagListPanelHandle(ListView<TagCard> tagListPanelNode) {
        super (tagListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code TagCardHandle}
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public TagCardHandle getHandleToSelectedCard() {
        List<TagCard> tagList = getRootNode().getSelectionModel().getSelectedItems();

        if (tagList.size() != 1) {
            throw new AssertionError("Tag list size expected 1.");
        }

        return new TagCardHandle(tagList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Navigates the listview to display and select the tag.
     */
    public void navigateToCard(Tag tag) {
        List<TagCard> cards = getRootNode().getItems();
        Optional<TagCard> matchingCard = cards.stream().filter(card -> card.tag.equals(tag)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Tag does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the tag card handle of a tag associated with the {@code index} in the list
     */
    public TagCardHandle getTagCardHandle(int index)  {
        return getTagCardHandle(getRootNode().getItems().get(index).tag);
    }

    /**
     * Returns the {@code TagCardHandle} of the specified {@code tag} in the list.
     */
    public TagCardHandle getTagCardHandle(Tag tag) {
        Optional<TagCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.tag.equals(tag))
                .map(card -> new TagCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Tag does not exist."));
    }

    /**
     * Selects the {@code TagCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### \java\seedu\address\logic\commands\ListAllTagsCommandTest.java
``` java
/**
* Contains integration tests (interaction with the Model) and unit tests for ListAllTagsCommand.
*/
public class ListAllTagsCommandTest {

    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;
    private Model expectedModel;
    private ListAllTagsCommand listAllTagsCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listAllTagsCommand = new ListAllTagsCommand();
        listAllTagsCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listAllTags_success() {
        try {
            listAllTagsCommand.execute();
        } catch (CommandException e) {
            System.out.println("CommandException");
        }
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof JumpToListAllTagsRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }

    @Test
    public void execute_personListUnfiltered_showsEverything() throws Exception {
        assertCommandSuccess(listAllTagsCommand, model, ListAllTagsCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
```
###### \java\seedu\address\model\person\AttendanceTest.java
``` java
public class AttendanceTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void defaultConstructorIsWorking() {
        Attendance attendance = new Attendance();
        assertEquals(attendance.getAttended(), 0);
        assertEquals(attendance.getMissed(), 8);
    }

    @Test
    public void constructorWithParametersIsWorking() {
        /**
         * Due to the small range of possible inputs, we shall test all the possible inputs.
         */
        Attendance attendance;
        // boundary value
        try {
            attendance = new Attendance(0);
            assertEquals(attendance.getAttended(), 0);
            assertEquals(attendance.getMissed(), 8);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(1);
            assertEquals(attendance.getAttended(), 1);
            assertEquals(attendance.getMissed(), 7);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(2);
            assertEquals(attendance.getAttended(), 2);
            assertEquals(attendance.getMissed(), 6);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(3);
            assertEquals(attendance.getAttended(), 3);
            assertEquals(attendance.getMissed(), 5);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(4);
            assertEquals(attendance.getAttended(), 4);
            assertEquals(attendance.getMissed(), 4);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(5);
            assertEquals(attendance.getAttended(), 5);
            assertEquals(attendance.getMissed(), 3);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(6);
            assertEquals(attendance.getAttended(), 6);
            assertEquals(attendance.getMissed(), 2);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        try {
            attendance = new Attendance(7);
            assertEquals(attendance.getAttended(), 7);
            assertEquals(attendance.getMissed(), 1);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }

        // boundary value
        try {
            attendance = new Attendance(8);
            assertEquals(attendance.getAttended(), 8);
            assertEquals(attendance.getMissed(), 0);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        }
    }

    @Test
    public void attendanceConstructor_invalidValue_throwsIllegalValueException() throws Exception {
        // boundary cases
        thrown.expect(IllegalValueException.class);
        new Attendance(-1);

        thrown.expect(IllegalValueException.class);
        new Attendance(9);
    }

    @Test
    public void addAttendance_withValidAttended() {
        try {
            Attendance attendance = new Attendance();
            attendance.addAttendance();
            assertEquals(attendance.getAttended(), 1);
            assertEquals(attendance.getMissed(), 7);
        } catch (PersonMaxAttendanceException e) {
            System.out.println("Max attendance already!");
        }

        // boundary case
        try {
            Attendance attendance = new Attendance(7);
            attendance.addAttendance();
            assertEquals(attendance.getAttended(), 8);
            assertEquals(attendance.getMissed(), 0);
        } catch (IllegalValueException e) {
            System.out.println("Illegal value!");
        } catch (PersonMaxAttendanceException e) {
            System.out.println("Max attendance already!");
        }
    }

    @Test
    public void addAttendance_withMaxAttended() throws Exception {
        Attendance attendance = new Attendance();
        for (int i = 0; i < 8; i++) {
            try {
                attendance.addAttendance();
            } catch (PersonMaxAttendanceException e) {
                System.out.println("Max attendance already!");
            }
        }

        assertEquals(attendance.getAttended(), 8);
        assertEquals(attendance.getMissed(), 0);
        thrown.expect(PersonMaxAttendanceException.class);
        attendance.addAttendance();
    }

    @Test
    public void attendance_equalsWorking() {
        Attendance attendance0 = new Attendance();
        Attendance attendance1 = new Attendance();

        assertTrue(attendance0.equals(attendance1));

        try {
            attendance1.addAttendance();
        } catch (PersonMaxAttendanceException e) {
            System.out.println("Max attendance already!");
        }

        assertFalse(attendance0.equals(attendance1));
    }
}
```
###### \java\seedu\address\model\person\ProfilePictureTest.java
``` java
public class ProfilePictureTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    private final String defaultPicPath = "/images/defaultProfilePic.png";

    @Test
    public void defaultConstructorIsWorking() {
        ProfilePicture profilePicture = new ProfilePicture();
        assertEquals(profilePicture.toString(), defaultPicPath);
    }

    @Test
    public void constructorWithParameterIsWorking() {
        try {
            ProfilePicture profilePicture = new ProfilePicture("/images/defaultProfilePic.png");
            assertEquals(profilePicture.toString(), defaultPicPath);
        } catch (NullPointerException e) {
            System.out.println("Null not accepted!");
        }
    }

    @Test
    public void constructorWithInvalidInput() throws Exception {
        thrown.expect(NullPointerException.class);
        new ProfilePicture(null);
    }

    @Test
    public void testEquals() {
        ProfilePicture profilePicture0 = new ProfilePicture();
        ProfilePicture profilePicture1 = new ProfilePicture();
        assertTrue(profilePicture0.equals(profilePicture1));

        ProfilePicture profilePicture2 = new ProfilePicture("/images/calendar.png");
        assertFalse(profilePicture0.equals(profilePicture2));
    }
}
```
###### \java\seedu\address\ui\TagCardTest.java
``` java
public class TagCardTest extends GuiUnitTest {

    private static final ObservableList<Tag> TYPICAL_TAGS =
            FXCollections.observableList(getTypicalAddressBook().getTagList());

    @Test
    public void display() {
        TagCard tagCard = new TagCard(TYPICAL_TAGS.get(0), 0);
        assertEquals(tagCard.tag.getText(), TYPICAL_TAGS.get(0).tagName);
        assertEquals(tagCard.getIdxText(), "0. ");
    }

    @Test
    public void equals() {
        TagCard tagCard = new TagCard(TYPICAL_TAGS.get(0), 0);

        // same tag, same index -> returns true
        TagCard copy = new TagCard(TYPICAL_TAGS.get(0), 0);
        assertTrue(tagCard.equals(copy));

        // same object -> returns true
        assertTrue(tagCard.equals(tagCard));

        // null -> returns false
        assertFalse(tagCard.equals(null));

        // different types -> returns false
        assertFalse(tagCard.equals(0));

        // different tag, same index -> returns false
        TagCard differentCard = new TagCard(TYPICAL_TAGS.get(1), 0);
        assertFalse(tagCard.equals(differentCard));

        // same tag, different index -> returns false
        differentCard = new TagCard(TYPICAL_TAGS.get(0), 1);
        assertFalse(tagCard.equals(differentCard));
    }
}
```
###### \java\seedu\address\ui\TagListPanelTest.java
``` java
public class TagListPanelTest extends GuiUnitTest {
    private static final ObservableList<Tag> TYPICAL_TAGS =
            FXCollections.observableList(getTypicalAddressBook().getTagList());

    private static final JumpToListAllTagsRequestEvent JUMP_TO_LIST_ALL_TAGS_EVENT =
            new JumpToListAllTagsRequestEvent();

    private TagListPanelHandle tagListPanelHandle;

    @Before
    public void setUp() {
        TagListPanel tagListPanel = new TagListPanel(TYPICAL_TAGS);
        uiPartRule.setUiPart(tagListPanel);

        tagListPanelHandle = new TagListPanelHandle(getChildNode(tagListPanel.getRoot(),
                TagListPanelHandle.TAG_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_TAGS.size(); i++) {
            tagListPanelHandle.navigateToCard(TYPICAL_TAGS.get(i));
            Tag expectedTag = TYPICAL_TAGS.get(i);
            TagCardHandle actualCard = tagListPanelHandle.getTagCardHandle(i);

            assertCardDisplaysTag(expectedTag, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }


    @Test
    public void handleJumpToListAllTagsRequestEvent() {
        postNow(JUMP_TO_LIST_ALL_TAGS_EVENT);
        guiRobot.pauseForHuman();

        for (int i = 0; i < TYPICAL_TAGS.size(); i++) {
            tagListPanelHandle.navigateToCard(TYPICAL_TAGS.get(i));
            Tag expectedTag = TYPICAL_TAGS.get(i);
            TagCardHandle actualCard = tagListPanelHandle.getTagCardHandle(i);

            assertCardDisplaysTag(expectedTag, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(TagCardHandle expectedCard, TagCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getTagString(), actualCard.getTagString());
    }
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedTag}.
     */
    public static void assertCardDisplaysTag(Tag expectedTag, TagCardHandle actualCard) {
        assertEquals(expectedTag.getText(), actualCard.getTagString());
    }
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        } catch (PersonMaxAttendanceException e) {
            throw new IllegalArgumentException(
                    "editedPerson's attendance is already maximum!");
        }
```
###### \java\systemtests\EditCommandSystemTest.java
``` java
        assertCommandSuccess(command, expectedModel,
                String.format(EditCommand.MESSAGE_EDIT_PERSON_SUCCESS, editedPerson), expectedSelectedCardIndex);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(String, Model, String, Index)} except that the
     * browser url and selected card remain unchanged.
     * @see EditCommandSystemTest#assertCommandSuccess(String, Model, String, Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage) {
        assertCommandSuccess(command, expectedModel, expectedResultMessage, null);
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays an empty string.<br>
     * 2. Asserts that the result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to {@code expectedModel}.<br>
     * 4. Asserts that the browser url and selected card update accordingly depending on the card at
     * {@code expectedSelectedCardIndex}.<br>
     * 5. Asserts that the status bar's sync status changes.<br>
     * 6. Asserts that the command box has the default style class.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Model expectedModel, String expectedResultMessage,
            Index expectedSelectedCardIndex) {
        executeCommand(command);
        expectedModel.updateFilteredPersonList(PREDICATE_SHOW_ALL_PERSONS);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);
        assertCommandBoxShowsDefaultStyle();
        if (expectedSelectedCardIndex != null) {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        } else {
            assertSelectedCardUnchanged();
        }
        assertStatusBarUnchangedExceptSyncStatus();
    }

    /**
     * Executes {@code command} and in addition,<br>
     * 1. Asserts that the command box displays {@code command}.<br>
     * 2. Asserts that result display box displays {@code expectedResultMessage}.<br>
     * 3. Asserts that the model related components equal to the current model.<br>
     * 4. Asserts that the browser url, selected card and status bar remain unchanged.<br>
     * 5. Asserts that the command box has the error style.<br>
     * Verifications 1 to 3 are performed by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
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
