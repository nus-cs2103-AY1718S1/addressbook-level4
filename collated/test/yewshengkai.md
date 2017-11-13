# yewshengkai
###### \java\guitests\AboutWindowTest.java
``` java
public class AboutWindowTest extends AddressBookGuiTest {
    private static final String ERROR_MESSAGE = "ATTENTION!!!! : On some computers, this test may fail when run on "
            + "non-headless mode as FxRobot#clickOn(Node, MouseButton...) clicks on the wrong location. We suspect "
            + "that this is a bug with TestFX library that we are using. If this test fails, you have to run your "
            + "tests on headless mode. See UsingGradle.adoc on how to do so.";

    @Test
    public void openAboutWindow() {
        //use accelerator
        getCommandBox().click();
        getMainMenu().openAboutWindowUsingAccelerator();
        assertAboutWindowOpen();

        getResultDisplay().click();
        getMainMenu().openAboutWindowUsingAccelerator();
        assertAboutWindowOpen();

        getPersonListPanel().click();
        getMainMenu().openAboutWindowUsingAccelerator();
        assertAboutWindowOpen();

        getBrowserPanel().click();
        getMainMenu().openAboutWindowUsingAccelerator();
        assertAboutWindowNotOpen();

        //use menu button
        getMainMenu().openAboutWindowUsingMenu();
        assertAboutWindowOpen();

        //use command box
        runCommand(AboutCommand.COMMAND_WORD);
        assertAboutWindowOpen();
    }

    /**
     * Asserts that the about window is open, and closes it after checking.
     */
    private void assertAboutWindowOpen() {
        assertTrue(ERROR_MESSAGE, AboutWindowHandle.isWindowPresent());
        guiRobot.pauseForHuman();

        new AboutWindowHandle(guiRobot.getStage(AboutWindowHandle.ABOUT_WINDOW_TITLE)).close();
        mainWindowHandle.focus();
    }

    /**
     * Asserts that the about window isn't open.
     */
    private void assertAboutWindowNotOpen() {
        assertFalse(ERROR_MESSAGE, AboutWindowHandle.isWindowPresent());
    }

}
```
###### \java\guitests\guihandles\AboutWindowHandle.java
``` java
/**
 * A handle to the {@code AboutWindow} of the application.
 */
public class AboutWindowHandle extends StageHandle {

    public static final String ABOUT_WINDOW_TITLE = "About";

    private static final String ABOUT_WINDOW_BROWSER_ID = "#browser";

    public AboutWindowHandle(Stage aboutWindowStage) {
        super(aboutWindowStage);
    }

    /**
     * Returns true if a about window is currently present in the application.
     */
    public static boolean isWindowPresent() {
        return new GuiRobot().isWindowShown(ABOUT_WINDOW_TITLE);
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(ABOUT_WINDOW_BROWSER_ID));
    }
}
```
###### \java\guitests\guihandles\PersonSideCardHandle.java
``` java
/**
 * Provides a handle to a person card in the person list panel.
 */
public class PersonSideCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String TAGS_FIELD_ID = "#tags";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label phoneLabel;
    private final List<Label> tagLabels;

    public PersonSideCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.phoneLabel = getChildNode(PHONE_FIELD_ID);

        Region tagsContainer = getChildNode(TAGS_FIELD_ID);
        this.tagLabels = tagsContainer
                .getChildrenUnmodifiable()
                .stream()
                .map(Label.class::cast)
                .collect(Collectors.toList());
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }


    public String getPhone() {
        return phoneLabel.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }
}
```
###### \java\seedu\address\logic\commands\AboutCommandTest.java
``` java
public class AboutCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_about_success() {
        CommandResult result = new AboutCommand().execute();
        assertEquals(SHOWING_ABOUT_MESSAGE, result.feedbackToUser);
        assertTrue(eventsCollectorRule.eventsCollector.getMostRecent() instanceof ShowAboutRequestEvent);
        assertTrue(eventsCollectorRule.eventsCollector.getSize() == 1);
    }
}
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    @Test
    public void equals() {
        PersonContainsKeywordsPredicate firstPredicate =
                new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD, Collections.singletonList("first"), false);
        PersonContainsKeywordsPredicate secondPredicate =
                new PersonContainsKeywordsPredicate(
                        FindCommand.COMMAND_WORD, Collections.singletonList("second"), false);
```
###### \java\seedu\address\logic\commands\FindCommandTest.java
``` java
    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FindCommand prepareCommand(String commandPrefix, String userInput) {
        switch (commandPrefix) {

        case FindCommand.COMMAND_WORD:
            FindCommand command = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD, Arrays.asList(userInput.split("\\s+")), false));
            command.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command;
        case FindCommand.COMMAND_WORD_ADDRESS:
            FindCommand command2 = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD_ADDRESS, Arrays.asList(userInput.split("\\s+")), false));
            command2.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command2;
        case FindCommand.COMMAND_WORD_EMAIL:
            FindCommand command3 = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD_EMAIL, Arrays.asList(userInput.split("\\s+")), false));
            command3.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command3;
        case FindCommand.COMMAND_WORD_HOMEPAGE:
            FindCommand command4 = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD_HOMEPAGE, Arrays.asList(userInput.split("\\s+")), false));
            command4.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command4;
        case FindCommand.COMMAND_WORD_PHONE:
            FindCommand command5 = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD_PHONE, Arrays.asList(userInput.split("\\s+")), false));
            command5.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command5;
        case FindCommand.COMMAND_WORD_TAG:
            FindCommand command6 = new FindCommand(new PersonContainsKeywordsPredicate(
                    FindCommand.COMMAND_WORD_TAG, Arrays.asList(userInput.split("\\s+")), false));
            command6.setData(model, EMPTY_COMMAND_HISTORY, EMPTY_FIND_HISTORY, EMPTY_STACK);
            return command6;

        default:
            break;
        }

        return null;
    }
```
###### \java\seedu\address\logic\commands\GmapCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code GmapCommand}.
 */
public class GmapCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
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

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
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

        assertExecutionFailure(outOfBoundsIndex, Messages.MESSAGE_INVALID_PERSON_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        GmapCommand gmapFirstCommand = new GmapCommand(INDEX_FIRST_PERSON);
        GmapCommand gmapecondCommand = new GmapCommand(INDEX_SECOND_PERSON);

        // same object -> returns true
        assertTrue(gmapFirstCommand.equals(gmapFirstCommand));

        // same values -> returns true
        GmapCommand gmapFirstCommandCopy = new GmapCommand(INDEX_FIRST_PERSON);
        assertTrue(gmapFirstCommand.equals(gmapFirstCommandCopy));

        // different types -> returns false
        assertFalse(gmapFirstCommand.equals(1));

        // null -> returns false
        assertFalse(gmapFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(gmapFirstCommand.equals(gmapecondCommand));
    }

    /**
     * Executes a {@code GmapCommand} with the given {@code index}, and checks that {@code JumpToListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(Index index) {
        GmapCommand gmapCommand = prepareCommand(index);

        try {
            CommandResult commandResult = gmapCommand.execute();
            assertEquals(String.format(GmapCommand.MESSAGE_SELECT_PERSON_SUCCESS, index.getOneBased()),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        MapToListRequestEvent lastEvent = (MapToListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(TypicalPersons.getTypicalAddressBook().getPersonList().get(index.getZeroBased()),
                lastEvent.targetPerson);
    }

    /**
     * Executes a {@code GmapCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(Index index, String expectedMessage) {
        GmapCommand gmapCommand = prepareCommand(index);

        try {
            gmapCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }

    /**
     * Returns a {@code GmapCommand} with parameters {@code index}.
     */
    private GmapCommand prepareCommand(Index index) {
        GmapCommand gmapCommand = new GmapCommand(index);
        gmapCommand.setData(model, new CommandHistory(), new FindHistory(), new UndoRedoStack());
        return gmapCommand;
    }
}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_find() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, keywords, false)), command);

        FindCommand aliasCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD, keywords, false)), aliasCommand);
    }

    @Test
    public void parseCommand_findany() throws Exception {
        List<String> keywords = Arrays.asList("foo", "bar", "baz");
        FindCommand command = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_WORD_ANY + " " + keywords.stream().collect(Collectors.joining(" ")));

        assertEquals(new FindCommand(new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD_ANY, keywords, true)), command);

        FindCommand aliasCommand = (FindCommand) parser.parseCommand(
                FindCommand.COMMAND_ALIAS_ANY + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindCommand(new PersonContainsKeywordsPredicate(
                FindCommand.COMMAND_WORD_ANY, keywords, true)), aliasCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_theme() throws Exception {
        ThemeCommand command = (ThemeCommand) parser.parseCommand(
                ThemeCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ThemeCommand(INDEX_FIRST_PERSON), command);

        ThemeCommand aliasCommand = (ThemeCommand) parser.parseCommand(
                ThemeCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new ThemeCommand(INDEX_FIRST_PERSON), aliasCommand);
    }
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommand_gmap() throws Exception {
        GmapCommand command = (GmapCommand) parser.parseCommand(
                GmapCommand.COMMAND_WORD + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new GmapCommand(INDEX_FIRST_PERSON), command);

        GmapCommand aliasCommand = (GmapCommand) parser.parseCommand(
                GmapCommand.COMMAND_ALIAS + " " + INDEX_FIRST_PERSON.getOneBased());
        assertEquals(new GmapCommand(INDEX_FIRST_PERSON), aliasCommand);
    }
```
###### \java\seedu\address\logic\parser\GmapCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class GmapCommandParserTest {

    private GmapCommandParser parser = new GmapCommandParser();

    @Test
    public void parse_validArgs_returnsSelectCommand() {
        assertParseSuccess(parser, "1", new GmapCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, GmapCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseAllDetail_withValidDetails() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        ArrayList<String> addressList = ParserUtil.parseAllDetail(
                Arrays.asList(VALID_ADDRESS), FindCommand.COMMAND_WORD_ADDRESS);
        assertEquals(expectedAddress.value, addressList.toString().replaceAll(
                "['\\[\\],']", ""));
        ParserUtil.parseAllDetail(addressList, FindCommand.COMMAND_WORD_ADDRESS);

        Email expectedEmail = new Email(VALID_EMAIL);
        ArrayList<String> emailList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_EMAIL), FindCommand.COMMAND_WORD_EMAIL);
        assertEquals(expectedEmail.value, emailList.toString().replaceAll(
                "['\\[\\]']", ""));
        ParserUtil.parseAllDetail(emailList, FindCommand.COMMAND_WORD_EMAIL);

```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
        ParserUtil.parseAllDetail(homepageList, FindCommand.COMMAND_WORD_HOMEPAGE);
        Phone expectedPhone = new Phone(VALID_PHONE);
        ArrayList<String> phoneList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_PHONE), FindCommand.COMMAND_WORD_PHONE);
        assertEquals(expectedPhone.value, phoneList.toString().replaceAll(
                "['\\[\\]']", ""));
        ParserUtil.parseAllDetail(phoneList, FindCommand.COMMAND_WORD_PHONE);

        Tag expectedTag = new Tag(VALID_TAG_1);
        ArrayList<String> tagList = ParserUtil.parseAllDetail(Arrays.asList(
                VALID_TAG_1), FindCommand.COMMAND_WORD_TAG);
        assertEquals(expectedTag.tagName, tagList.toString().replaceAll(
                "['\\[\\]']", ""));
        ParserUtil.parseAllDetail(tagList, FindCommand.COMMAND_WORD_TAG);
    }

```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java
    @Test
    public void parseAllDetail_invalidValue_throwsIllegalValueException() throws Exception {
        thrown.expect(IllegalValueException.class);
        Address expectedAddress = new Address(INVALID_ADDRESS);
        ArrayList<String> invalidAddress = ParserUtil.parseAllDetail(
                Arrays.asList(INVALID_ADDRESS), FindCommand.COMMAND_WORD_ADDRESS);
        assertEquals(expectedAddress.value, invalidAddress.toString().replaceAll(
                "['\\[\\],']", ""));
        ParserUtil.parseAllDetail(invalidAddress, FindCommand.COMMAND_WORD_ADDRESS);
        assertFalse(INVALID_ADDRESS, Address.isValidAddress(INVALID_ADDRESS));

        Email expectedEmail = new Email(INVALID_EMAIL);
        ArrayList<String> invalidEmail = ParserUtil.parseAllDetail(
                Arrays.asList(INVALID_EMAIL), FindCommand.COMMAND_WORD_EMAIL);
        assertEquals(expectedEmail.value, invalidEmail.toString());
        ParserUtil.parseAllDetail(invalidEmail, FindCommand.COMMAND_WORD_EMAIL);

        Homepage expectedHomepage = new Homepage(INVALID_HOMEPAGE);
        ArrayList<String> invalidHomepage = ParserUtil.parseAllDetail(
                Arrays.asList(INVALID_HOMEPAGE), FindCommand.COMMAND_WORD_HOMEPAGE);
        assertEquals(expectedHomepage.value, invalidHomepage.toString());
        ParserUtil.parseAllDetail(invalidHomepage, FindCommand.COMMAND_WORD_HOMEPAGE);

        Phone expectedPhone = new Phone(INVALID_PHONE);
        ArrayList<String> invalidPhone = ParserUtil.parseAllDetail(
                Arrays.asList(INVALID_PHONE), FindCommand.COMMAND_WORD_PHONE);
        assertEquals(expectedPhone.value, invalidPhone.toString());
        ParserUtil.parseAllDetail(invalidPhone, FindCommand.COMMAND_WORD_PHONE);

        Tag expectedTag = new Tag(INVALID_TAG);
        ArrayList<String> invalidTag = ParserUtil.parseAllDetail(
                Arrays.asList(INVALID_TAG), FindCommand.COMMAND_WORD_TAG);
        assertEquals(expectedTag.tagName, invalidTag.toString());
        ParserUtil.parseAllDetail(invalidTag, FindCommand.COMMAND_WORD_TAG);
    }
```
###### \java\seedu\address\logic\parser\ThemeCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code SelectCommandParserTest}.
 * @see SelectCommandParserTest
 */
public class ThemeCommandParserTest {

    private ThemeCommandParser parser = new ThemeCommandParser();

    @Test
    public void parse_validArgs_returnsThemeCommand() {
        assertParseSuccess(parser, "1", new ThemeCommand(INDEX_FIRST_PERSON));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }
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
###### \java\seedu\address\ui\AboutWindowTest.java
``` java
public class AboutWindowTest extends GuiUnitTest {

    private AboutWindow aboutWindow;
    private AboutWindowHandle aboutWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> aboutWindow = new AboutWindow());
        Stage aboutWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(aboutWindow.getRoot().getScene()));
        FxToolkit.showStage();
        aboutWindowHandle = new AboutWindowHandle(aboutWindowStage);
    }

    @Test
    public void display() {
        URL expectedHelpPage = AboutWindow.class.getResource(USERGUIDE_FILE_PATH);
        assertEquals(expectedHelpPage, aboutWindowHandle.getLoadedUrl());
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedPerson}.
     */
    public static void assertCardDisplaysPerson(ReadOnlyPerson expectedPerson, PersonSideCardHandle actualCard) {
        assertEquals(expectedPerson.getName().fullName, actualCard.getName());
        assertEquals(expectedPerson.getPhone().value, actualCard.getPhone());
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                actualCard.getTags());
    }
```
