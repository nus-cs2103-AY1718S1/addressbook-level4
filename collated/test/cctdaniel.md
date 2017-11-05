# cctdaniel
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String VALID_FONT_SIZE_XSMALL = "xsmall";
    public static final String VALID_FONT_SIZE_SMALL = "small";
    public static final String VALID_THEME_LIGHT = "light";
    public static final String VALID_THEME_DARK = "dark";
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String FONT_SIZE_DESC_XSMALL = " " + PREFIX_FONT_SIZE + VALID_FONT_SIZE_XSMALL;
    public static final String FONT_SIZE_DESC_SMALL = " " + PREFIX_FONT_SIZE + VALID_FONT_SIZE_SMALL;
```
###### /java/seedu/address/logic/commands/CommandTestUtil.java
``` java
    public static final String INVALID_FONT_SIZE_DESC = " " + PREFIX_FONT_SIZE
            + "small!"; // '!' not allowed in font size
    public static final String INVALID_THEME_DESC = "blue";
```
###### /java/seedu/address/logic/commands/CustomiseCommandTest.java
``` java
public class CustomiseCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());

    @Test
    public void execute_fontSizeChangedToXsmall_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_XSMALL));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_XSMALL + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToSmall_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_SMALL));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_SMALL + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToNormal_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_NORMAL));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_NORMAL + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToLarge_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_LARGE));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_LARGE + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void execute_fontSizeChangedToXLarge_success() throws Exception {
        CustomiseCommand customiseCommand = prepareCommand(new FontSize(FONT_SIZE_XLARGE));

        String expectedMessage = MESSAGE_SUCCESS + FONT_SIZE_XLARGE + ".";

        assertEquals(expectedMessage, customiseCommand.execute().feedbackToUser);
    }

    @Test
    public void equals() {
        try {
            FontSize fontSizeXsmall = new FontSize(FONT_SIZE_XSMALL);
            FontSize fontSizeXlarge = new FontSize(FONT_SIZE_XLARGE);

            final CustomiseCommand customiseCommand = new CustomiseCommand(fontSizeXsmall);

            // same object -> returns true
            assertTrue(customiseCommand.equals(customiseCommand));

            // same values -> returns true
            CustomiseCommand commandWithSameValues = new CustomiseCommand(fontSizeXsmall);
            assertTrue(customiseCommand.equals(commandWithSameValues));

            // different types -> returns false
            assertFalse(customiseCommand.equals(1));

            // null -> returns false
            assertFalse(customiseCommand.equals(null));

            // different Customise command -> returns false
            CustomiseCommand commandWithDifferentValues = new CustomiseCommand(fontSizeXlarge);
            assertFalse(customiseCommand.equals(commandWithDifferentValues));
        } catch (IllegalValueException ile) {
            throw new AssertionError(MESSAGE_FONT_SIZE_CONSTRAINTS);
        }
    }

    /**
     * Returns an {@code RemarkCommand} with parameters {@code index} and {@code remark}
     */
    private CustomiseCommand prepareCommand(FontSize fontSize) {
        CustomiseCommand customiseCommand = new CustomiseCommand(fontSize);
        customiseCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return customiseCommand;
    }

}
```
###### /java/seedu/address/logic/commands/ThemeCommandTest.java
``` java
public class ThemeCommandTest {

    @Test
    public void execute_lightThemeCommand_success() {
        ThemeCommand light = new ThemeCommand(VALID_THEME_LIGHT);
        CommandResult result = light.execute();
        if (getCurrentThemeUnit() == THEME_LIGHT_UNIT && light.getIsLight()) {
            assertEquals(SWITCH_THEME_FAILURE_MESSAGE, result.feedbackToUser);
        } else {
            assertEquals(SWITCH_THEME_SUCCESS_MESSAGE + LIGHT_THEME + ".", result.feedbackToUser);
        }
    }

    @Test
    public void execute_darkThemeCommand_success() {
        ThemeCommand dark = new ThemeCommand(VALID_THEME_DARK);
        CommandResult result = dark.execute();
        if (getCurrentThemeUnit() == THEME_LIGHT_UNIT && !dark.getIsLight()) {
            assertEquals(SWITCH_THEME_FAILURE_MESSAGE, result.feedbackToUser);
        } else {
            assertEquals(SWITCH_THEME_SUCCESS_MESSAGE + DARK_THEME + ".", result.feedbackToUser);
        }
    }
}
```
###### /java/seedu/address/logic/parser/CustomiseCommandParserTest.java
``` java
public class CustomiseCommandParserTest {

    private CustomiseCommandParser parser = new CustomiseCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        try {
            FontSize fontSize = new FontSize(VALID_FONT_SIZE_SMALL);

            // multiple font sizes - last font size accepted
            assertParseSuccess(parser, CustomiseCommand.COMMAND_WORD + FONT_SIZE_DESC_XSMALL
                    + FONT_SIZE_DESC_SMALL, new CustomiseCommand(fontSize));

        } catch (IllegalValueException ile) {
            throw new AssertionError(MESSAGE_FONT_SIZE_CONSTRAINTS);
        }
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, CustomiseCommand.MESSAGE_USAGE);

        // missing font size prefix
        assertParseFailure(parser, CustomiseCommand.COMMAND_WORD + VALID_FONT_SIZE_XSMALL, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid font size
        assertParseFailure(parser, CustomiseCommand.COMMAND_WORD
                + INVALID_FONT_SIZE_DESC, FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS);
    }

}
```
###### /java/seedu/address/logic/parser/ThemeCommandParserTest.java
``` java
public class ThemeCommandParserTest {
    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_validArgs_returnsThemeCommand() {
        assertParseSuccess(parser, VALID_THEME_LIGHT, new ThemeCommand("light"));
        assertParseSuccess(parser, VALID_THEME_DARK, new ThemeCommand("dark"));
    }


    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, INVALID_THEME_DESC,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }

}
```
###### /java/seedu/address/ui/BrowserPanelTest.java
``` java
public class BrowserPanelTest extends GuiUnitTest {
    private LessonPanelSelectionChangedEvent selectionChangedEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new LessonPanelSelectionChangedEvent(new LessonListCard(MA1101R_L1, 0));

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a lesson
        postNow(selectionChangedEventStub);
        URL expectedLessonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + MA1101R_L1.getCode().fullCodeName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedLessonUrl, browserPanelHandle.getLoadedUrl());
    }
}
```
###### /java/seedu/address/ui/LessonListPanelTest.java
``` java
public class LessonListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyLesson> TYPICAL_LESSONS =
            FXCollections.observableList(getTypicalLessons());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_LESSON);

    private LessonListPanelHandle lessonListPanelHandle;

    @Before
    public void setUp() {
        LessonListPanel lessonListPanel = new LessonListPanel(TYPICAL_LESSONS);
        uiPartRule.setUiPart(lessonListPanel);

        lessonListPanelHandle = new LessonListPanelHandle(getChildNode(lessonListPanel.getRoot(),
                LessonListPanelHandle.LESSON_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_LESSONS.size(); i++) {
            lessonListPanelHandle.navigateToCard(TYPICAL_LESSONS.get(i));
            ReadOnlyLesson expectedLesson = TYPICAL_LESSONS.get(i);
            LessonCardHandle actualCard = lessonListPanelHandle.getLessonListCardHandle(i);

            assertCardDisplaysLesson(expectedLesson, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        LessonCardHandle expectedCard =
                lessonListPanelHandle.getLessonListCardHandle(INDEX_SECOND_LESSON.getZeroBased());
        LessonCardHandle selectedCard = lessonListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
/**
 * A set of assertion methods useful for writing GUI tests.
 */
public class GuiTestAssert {
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(LessonCardHandle expectedCard, LessonCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getCode(), actualCard.getCode());
        assertEquals(expectedCard.getClassType(), actualCard.getClassType());
        assertEquals(expectedCard.getGroup(), actualCard.getGroup());
        assertEquals(expectedCard.getLocation(), actualCard.getLocation());
        assertEquals(expectedCard.getTimeSlot(), actualCard.getTimeSlot());
        assertEquals(expectedCard.getLecturers(), actualCard.getLecturers());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedLesson}.
     */
    public static void assertCardDisplaysLesson(ReadOnlyLesson expectedLesson, LessonCardHandle actualCard) {
        assertEquals(expectedLesson.getCode().fullCodeName, actualCard.getCode());
        assertEquals(expectedLesson.getClassType().value, actualCard.getClassType());
        assertEquals(expectedLesson.getLocation().value, actualCard.getLocation());
        assertEquals(expectedLesson.getGroup().value, actualCard.getGroup());
        assertEquals(expectedLesson.getTimeSlot().value, actualCard.getTimeSlot());
        assertEquals(expectedLesson.getLecturers().stream().map(lecturer -> lecturer.lecturerName)
                        .collect(Collectors.toList()), actualCard.getLecturers());
    }

    /**
     * Asserts that the list in {@code lessonListPanelHandle} displays the details of {@code lessons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(LessonListPanelHandle lessonListPanelHandle, ReadOnlyLesson... lessons) {
        for (int i = 0; i < lessons.length; i++) {
            assertCardDisplaysLesson(lessons[i], lessonListPanelHandle.getLessonListCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code lessonListPanelHandle} displays the details of {@code lessons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(LessonListPanelHandle lessonListPanelHandle, List<ReadOnlyLesson> lessons) {
        assertListMatching(lessonListPanelHandle, lessons.toArray(new ReadOnlyLesson[0]));
    }

    /**
     * Asserts the size of the list in {@code lessonListPanelHandle} equals to {@code size}.
     */
    public static void assertListSize(LessonListPanelHandle lessonListPanelHandle, int size) {
        int numberOfLessons = lessonListPanelHandle.getListSize();
        assertEquals(size, numberOfLessons);
    }

    /**
     * Asserts the message shown in {@code resultDisplayHandle} equals to {@code expected}.
     */
    public static void assertResultMessage(ResultDisplayHandle resultDisplayHandle, String expected) {
        assertEquals(expected, resultDisplayHandle.getText());
    }
}
```
###### /java/seedu/address/ui/testutil/StageRule.java
``` java
/**
 * Properly sets up and tears down a JavaFx stage for our testing purposes.
 */
public class StageRule implements TestRule {
    protected void before() throws Throwable {
        FxToolkit.registerPrimaryStage();
    }

    protected void after() throws Throwable {
        FxToolkit.cleanupStages();
    }

    @Override
    public Statement apply(final Statement base, final Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                before();
                try {
                    base.evaluate();
                } finally {
                    after();
                }
            }
        };
    }
}
```
###### /java/systemtests/SelectCommandSystemTest.java
``` java
public class SelectCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void select() {
        /* Case: select the first card in the lesson list, command with leading spaces and trailing spaces
         * -> selected
         */

        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);

        String command = "   " + SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased() + "   ";
        assertCommandSuccess(command, INDEX_FIRST_LESSON);

        /* Case: select the first card in the lesson list -> selected */
        Index lessonCount = Index.fromOneBased(1);
        command = SelectCommand.COMMAND_WORD + " " + lessonCount.getOneBased();
        assertCommandSuccess(command, lessonCount);

        /* Case: undo previous selection -> rejected */
        command = UndoCommand.COMMAND_WORD;
        String expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: redo selecting last card in the list -> rejected */
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: select the middle card in the lesson list -> selected */
        Index middleIndex = Index.fromOneBased(lessonCount.getOneBased() / 2 + 1);
        command = SelectCommand.COMMAND_WORD + " " + middleIndex.getOneBased();
        assertCommandSuccess(command, middleIndex);

        /* Case: invalid index (size + 1) -> rejected */
        int invalidIndex = getModel().getFilteredLessonList().size() + 1;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: select the current selected card -> selected */
        assertCommandSuccess(command, middleIndex);

        /* Case: filtered lesson list, select index within bounds of address book but out of bounds of lesson list
         * -> rejected
         */
        showLessonsWithName(KEYWORD_MATCHING_LT27);
        invalidIndex = getModel().getAddressBook().getLessonList().size();
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + invalidIndex, MESSAGE_INVALID_DISPLAYED_INDEX);

        /* Case: filtered lesson list, select index within bounds of address book and lesson list -> selected */
        Index validIndex = Index.fromOneBased(1);
        assert validIndex.getZeroBased() < getModel().getFilteredLessonList().size();
        command = SelectCommand.COMMAND_WORD + " " + validIndex.getOneBased();
        assertCommandSuccess(command, validIndex);

        /* Case: invalid index (0) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + 0,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid index (-1) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + -1,
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (alphabets) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: invalid arguments (extra argument) -> rejected */
        assertCommandFailure(SelectCommand.COMMAND_WORD + " 1 abc",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, SelectCommand.MESSAGE_USAGE));

        /* Case: mixed case command word -> rejected */
        assertCommandFailure("SeLeCt 1", MESSAGE_UNKNOWN_COMMAND);

        /* Case: select from empty address book -> rejected */
        executeCommand(ClearCommand.COMMAND_WORD);
        assert getModel().getAddressBook().getLessonList().size() == 0;
        assertCommandFailure(SelectCommand.COMMAND_WORD + " " + INDEX_FIRST_LESSON.getOneBased(),
                MESSAGE_INVALID_DISPLAYED_INDEX);
    }

    /**
     * Executes {@code command} and verifies that the command box displays an empty string, the result display
     * box displays the success message of executing select command with the {@code expectedSelectedCardIndex}
     * of the selected lesson, and the model related components equal to the current model.
     * These verifications are done by
     * {@code AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)}.<br>
     * Also verifies that the command box has the default style class and the status bar remain unchanged. The resulting
     * browser url and selected card will be verified if the current selected card and the card at
     * {@code expectedSelectedCardIndex} are different.
     * @see AddressBookSystemTest#assertApplicationDisplaysExpected(String, String, Model)
     * @see AddressBookSystemTest#assertSelectedCardChanged(Index)
     */
    private void assertCommandSuccess(String command, Index expectedSelectedCardIndex) {
        Model expectedModel = getModel();
        String expectedResultMessage = String.format(
                MESSAGE_SELECT_LESSON_SUCCESS, expectedSelectedCardIndex.getOneBased());
        int preExecutionSelectedCardIndex = getLessonListPanel().getSelectedCardIndex();

        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, expectedModel);

        if (preExecutionSelectedCardIndex == expectedSelectedCardIndex.getZeroBased()) {
            assertSelectedCardUnchanged();
        } else {
            assertSelectedCardChanged(expectedSelectedCardIndex);
        }

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
        assertSelectedCardUnchanged();
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }


}
```
