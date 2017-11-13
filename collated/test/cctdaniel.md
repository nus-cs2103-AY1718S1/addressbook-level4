# cctdaniel
###### \java\seedu\address\logic\commands\CustomiseCommandTest.java
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
###### \java\seedu\address\logic\commands\ThemeCommandTest.java
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
###### \java\seedu\address\logic\parser\AddCommandParserTest.java
``` java
public class AddCommandParserTest {
    private AddCommandParser parser = new AddCommandParser();

    @Test
    public void parse_allFieldsPresent_success() {
        Lesson expectedLesson = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();

        // multiple names - last name accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_CS2101 + CODE_DESC_MA1101R
                + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));

        // multiple phones - last phone accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_CS2101
                + CLASSTYPE_DESC_MA1101R + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));

        // multiple emails - last email accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_CS2101 + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));

        // multiple addresses - last address accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                +  VENUE_DESC_MA1101R + GROUP_DESC_CS2101 + GROUP_DESC_MA1101R
                + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));

        // multiple addresses - last time slot accepted
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                +  VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_CS2101
                + TIMESLOT_DESC_MA1101R + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));

        // multiple tags - all accepted
        Lesson expectedLessonMultipleLecturer = new LessonBuilder().withCode(VALID_CODE_MA1101R)
                .withClassType(VALID_CLASSTYPE_MA1101R).withLocation(VALID_VENUE_MA1101R)
                .withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R, VALID_LECTURER_CS2101).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                        +  VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                        + LECTURER_DESC_MA1101R + LECTURER_DESC_CS2101,
                new AddCommand(expectedLessonMultipleLecturer));
    }

    @Test
    public void parse_optionalFieldsMissing_success() {
        // zero tags
        Lesson expectedLesson = new LessonBuilder().withCode(VALID_CODE_MA1101R).withClassType(VALID_CLASSTYPE_MA1101R)
                .withLocation(VALID_VENUE_MA1101R).withGroup(VALID_GROUP_MA1101R).withTimeSlot(VALID_TIMESLOT_MA1101R)
                .withLecturers(VALID_LECTURER_MA1101R).build();
        assertParseSuccess(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                +  VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, new AddCommand(expectedLesson));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddCommand.MESSAGE_USAGE);

        // missing code prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_CODE_MA1101R + CLASSTYPE_DESC_MA1101R
                +  VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, expectedMessage);

        // missing class type prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + VALID_CLASSTYPE_MA1101R
                +  VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, expectedMessage);

        // missing venue prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VALID_VENUE_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, expectedMessage);

        // missing group prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + VALID_GROUP_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, expectedMessage);

        // missing time slot prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + VALID_TIMESLOT_MA1101R
                + LECTURER_DESC_MA1101R, expectedMessage);

        // missing lecturer prefix
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + VALID_LECTURER_MA1101R, expectedMessage);


        // all prefixes missing
        assertParseFailure(parser, AddCommand.COMMAND_WORD + VALID_CODE_MA1101R + VALID_CLASSTYPE_MA1101R
                + VALID_VENUE_MA1101R + VALID_GROUP_MA1101R + VALID_TIMESLOT_MA1101R
                + VALID_LECTURER_MA1101R, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid code
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_CODE_DESC + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, Code.MESSAGE_CODE_CONSTRAINTS);

        // invalid class type
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + INVALID_CLASSTYPE_DESC
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);

        // invalid venue
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + INVALID_VENUE_DESC + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, Location.MESSAGE_LOCATION_CONSTRAINTS);

        // invalid group
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + INVALID_GROUP_DESC + TIMESLOT_DESC_MA1101R
                + LECTURER_DESC_MA1101R, Group.MESSAGE_GROUP_CONSTRAINTS);

        // invalid time slot
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + INVALID_TIMESLOT_DESC
                + LECTURER_DESC_MA1101R, TimeSlot.MESSAGE_TIMESLOT_CONSTRAINTS);

        // invalid lecturer
        assertParseFailure(parser, AddCommand.COMMAND_WORD + CODE_DESC_MA1101R + CLASSTYPE_DESC_MA1101R
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + TIMESLOT_DESC_MA1101R
                + INVALID_LECTURER_DESC, Lecturer.MESSAGE_LECTURER_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        //The sequence of Add is {CT, V, GP, TS, C, L}
        assertParseFailure(parser, AddCommand.COMMAND_WORD + INVALID_CODE_DESC + INVALID_CLASSTYPE_DESC
                + VENUE_DESC_MA1101R + GROUP_DESC_MA1101R + INVALID_TIMESLOT_DESC
                + LECTURER_DESC_MA1101R, ClassType.MESSAGE_CLASSTYPE_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\CustomiseCommandParserTest.java
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
###### \java\seedu\address\logic\parser\ThemeCommandParserTest.java
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
###### \java\seedu\address\ui\LessonListPanelTest.java
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
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
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
###### \java\seedu\address\ui\testutil\StageRule.java
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
###### \java\systemtests\CustomiseCommandSystemTest.java
``` java
public class CustomiseCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void customise() {
        Model model = getModel();
        String command;
        String expectedResultMessage;

        /* Case: change font size to xsmall */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + CustomiseCommand.FONT_SIZE_XSMALL;
        expectedResultMessage = CustomiseCommand.MESSAGE_SUCCESS + CustomiseCommand.FONT_SIZE_XSMALL + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: change font size to small */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + CustomiseCommand.FONT_SIZE_SMALL;
        expectedResultMessage = CustomiseCommand.MESSAGE_SUCCESS + CustomiseCommand.FONT_SIZE_SMALL + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: change font size to normal */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + CustomiseCommand.FONT_SIZE_NORMAL;
        expectedResultMessage = CustomiseCommand.MESSAGE_SUCCESS + CustomiseCommand.FONT_SIZE_NORMAL + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: change font size to large */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + CustomiseCommand.FONT_SIZE_LARGE;
        expectedResultMessage = CustomiseCommand.MESSAGE_SUCCESS + CustomiseCommand.FONT_SIZE_LARGE + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: change font size to xlarge */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + CustomiseCommand.FONT_SIZE_XLARGE;
        expectedResultMessage = CustomiseCommand.MESSAGE_SUCCESS + CustomiseCommand.FONT_SIZE_XLARGE + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: attempt to change font size without typing prefix */
        command = CustomiseCommand.COMMAND_WORD + " " + CustomiseCommand.FONT_SIZE_XLARGE;
        expectedResultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, CustomiseCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: attempt to change font size to undefined sizes */
        command = CustomiseCommand.COMMAND_WORD + " " + CliSyntax.PREFIX_FONT_SIZE + "xxxlarge";
        expectedResultMessage = FontSize.MESSAGE_FONT_SIZE_CONSTRAINTS;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: attempt to undo customise command */
        // CustomiseCommand is not undoable and hence should display an error message
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: attempt to redo customise command */
        // CustomiseCommand is not undoable and hence should display an error message
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);
    }

    /**
     * Executes {@code command} and verifies that the result equals to {@code expectedResultMessage}.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model model) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, model);
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
    }
}
```
###### \java\systemtests\SelectCommandSystemTest.java
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
###### \java\systemtests\ThemeCommandSystemTest.java
``` java
public class ThemeCommandSystemTest extends AddressBookSystemTest {
    @Test
    public void theme() {
        Model model = getModel();
        String command;
        String expectedResultMessage;

        /* Case: change theme to dark theme */
        command = ThemeCommand.COMMAND_WORD + " " + ThemeCommand.DARK_THEME;
        expectedResultMessage = ThemeCommand.SWITCH_THEME_SUCCESS_MESSAGE + ThemeCommand.DARK_THEME + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: attempt to change theme to dark even when already in dark theme */
        command = ThemeCommand.COMMAND_WORD + " " + ThemeCommand.DARK_THEME;
        expectedResultMessage = ThemeCommand.SWITCH_THEME_FAILURE_MESSAGE;
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: change theme to light theme */
        command = ThemeCommand.COMMAND_WORD + " " + ThemeCommand.LIGHT_THEME;
        expectedResultMessage = ThemeCommand.SWITCH_THEME_SUCCESS_MESSAGE + ThemeCommand.LIGHT_THEME + ".";
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: attempt to change theme to light even when already in light theme */
        command = ThemeCommand.COMMAND_WORD + " " + ThemeCommand.LIGHT_THEME;
        expectedResultMessage = ThemeCommand.SWITCH_THEME_FAILURE_MESSAGE;
        assertCommandSuccess(command, expectedResultMessage, model);

        /* Case: attempt to change to other themes that are undefined */
        command = ThemeCommand.COMMAND_WORD + " " + "blue";
        expectedResultMessage = String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE);
        assertCommandFailure(command, expectedResultMessage);

        /* Case: attempt to undo theme command */
        // ThemeCommand is not undoable and hence should display an error message
        command = UndoCommand.COMMAND_WORD;
        expectedResultMessage = UndoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

        /* Case: attempt to redo theme command */
        // ThemeCommand is not undoable and hence should display an error message
        command = RedoCommand.COMMAND_WORD;
        expectedResultMessage = RedoCommand.MESSAGE_FAILURE;
        assertCommandFailure(command, expectedResultMessage);

    }

    /**
     * Executes {@code command} and verifies that the result equals to {@code expectedResultMessage}.
     */
    private void assertCommandSuccess(String command, String expectedResultMessage, Model model) {
        executeCommand(command);
        assertApplicationDisplaysExpected("", expectedResultMessage, model);
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
    }

}
```
