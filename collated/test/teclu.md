# teclu
###### \java\guitests\guihandles\PersonPanelHandle.java
``` java
/**
 * Provides a handle to the Person Panel.
 */
public class PersonPanelHandle extends NodeHandle<Node> {
    public static final String PERSON_PANEL_ID = "#personPanel";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PHONE_FIELD_ID = "#phone";
    private static final String ADDRESS_FIELD_ID = "#address";
    private static final String EMAIL_FIELD_ID = "#email";
    private static final String BIRTHDAY_FIELD_ID = "#birthday";
    private static final String TAGS_FIELD_ID = "#tags";

    private Label name;
    private Label phone;
    private Label address;
    private Label email;
    private Label birthday;
    private Label avatar;
    private List<Label> tagLabels;

    public PersonPanelHandle(Node personPanelNode) {
        super(personPanelNode);

        this.name = getChildNode(NAME_FIELD_ID);
        this.phone = getChildNode(PHONE_FIELD_ID);
        this.address = getChildNode(ADDRESS_FIELD_ID);
        this.email = getChildNode(EMAIL_FIELD_ID);
        this.birthday = getChildNode(BIRTHDAY_FIELD_ID);

        updateTags();
    }

    public String getName() {
        return name.getText();
    }

    public String getPhone() {
        return phone.getText();
    }

    public String getEmail() {
        return email.getText();
    }

    public String getAddress() {
        return address.getText();
    }

    public String getBirthday() {
        return birthday.getText();
    }

    public List<String> getTags() {
        return tagLabels
                .stream()
                .map(Label::getText)
                .collect(Collectors.toList());
    }

    /**
     * Updates the tags of the person.
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
###### \java\seedu\address\logic\commands\ThemeCommandTest.java
``` java
public class ThemeCommandTest extends AddressBookGuiTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    private String[] listThemes = { "Light", "Dark", "Red", "Blue", "Green" };

    @Test
    public void execute_validTheme_success() {
        for (int i = 4; i >= 0; i--) {
            assertExecutionSuccess(listThemes[i]);
        }
    }

    @Test
    public void execute_invalidTheme_failure() {
        assertExecutionFailure("NotATheme", Messages.MESSAGE_INVALID_THEME);
    }

    @Test
    public void equals() {
        ThemeCommand[] listThemeCommands = new ThemeCommand[5];
        for (int i = 0; i < 5; i++) {
            listThemeCommands[i] = new ThemeCommand(listThemes[i]);
        }

        // same object -> returns true
        for (int i = 0; i < 5; i++) {
            assertTrue(listThemeCommands[i].equals(new ThemeCommand(listThemes[i])));
        }

        // different types -> returns false
        for (int i = 0; i < 5; i++) {
            assertFalse(listThemeCommands[i].equals(1));
        }

        // null -> returns false
        for (int i = 0; i < 5; i++) {
            assertFalse(listThemeCommands[i].equals(null));
        }

        // different theme -> returns false
        int j = 4;

        for (int i = 0; i < 5; i++) {
            if (i != j) {
                assertFalse(listThemeCommands[i].equals(listThemeCommands[j]));
            }
            j--;
        }
    }

    /**
     * Executes a {@code ThemeCommand} with the given {@code theme}, and checks that
     * {@code JumpToListRequestEvent} is raised with the correct index.
     */
    private void assertExecutionSuccess(String theme) {
        ThemeCommand themeCommand = new ThemeCommand(theme);

        try {
            CommandResult commandResult = themeCommand.execute();
            assertEquals(String.format(ThemeCommand.MESSAGE_THEME_SUCCESS, theme),
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        ChangeThemeRequestEvent lastEvent =
                (ChangeThemeRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(theme, lastEvent.theme);
    }

    /**
     * Executes a {@code SelectCommand} with the given {@code index}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String theme, String expectedMessage) {
        ThemeCommand themeCommand = new ThemeCommand(theme);

        try {
            themeCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
```
###### \java\seedu\address\logic\parser\ThemeCommandParserTest.java
``` java
public class ThemeCommandParserTest {

    private ThemeCommandParser parser = new ThemeCommandParser();
    private String[] listThemes = { "Light", "Dark", "Red", "Blue", "Green" };

    @Test
    public void parse_validArgs_returnsThemeCommand() {
        for (int i = 0; i < 5; i++) {
            assertParseSuccess(parser, listThemes[i], new ThemeCommand(listThemes[i]));
        }
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // Empty Argument
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ThemeCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\person\AvatarTest.java
``` java
public class AvatarTest {

    @Test
    public void isValidUrl() {
        // Invalid Avatar URLs
        assertFalse(Avatar.isValidUrl("")); // Empty string
        assertFalse(Avatar.isValidUrl("i.imgur.com/jNNT4LE.jpg")); // Missing http(s)://
        assertFalse(Avatar.isValidUrl("C:/Users/Bob/AddressBook/data/-1552827182.png")); // Missing file:/

        // Valid Avatar URLs
        assertTrue(Avatar.isValidUrl("https://i.imgur.com/jNNT4LE.jpg"));
        assertTrue(Avatar.isValidUrl("file:/C:/Users/Bob/AddressBook/data/-1552827182.png"));
    }

    @Test
    public void isSavedInData() {
        // Invalid Avatar Saved URLs
        assertFalse(Avatar.isSavedInData("")); // Empty string
        assertFalse(Avatar.isSavedInData("https://i.imgur.com/jNNT4LE.jpg")); // Online URL
        assertFalse(Avatar.isSavedInData("C:/Users/Bob/pictures/Alice.png")); // Local URL
        assertFalse(Avatar.isSavedInData("file:/C:/Users/Bob/pictures/Alice.png")); // Not in Data Folder

        // Valid Avatar Saved URLs
        assertTrue(Avatar.isSavedInData("file:/C:/Users/Bob/AddressBook/data/-1552827182.png"));
    }
}
```
###### \java\seedu\address\testutil\PersonBuilder.java
``` java
    /**
     * Sets the {@code Avatar} of the {@code Person} that we are building.
     */
    public PersonBuilder withAvatar() {
        try {
            this.person.setAvatar(new Avatar());
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("avatar is expected to be valid.");
        }
        return this;
    }

    /**
     * Sets the {@code Avatar} of the {@code Person} that we are building.
     */
    public PersonBuilder withAvatar(String avatar) {
        try {
            this.person.setAvatar(new Avatar(avatar));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("avatar is expected to be valid.");
        }
        return this;
    }
```
###### \java\seedu\address\ui\HelpWindowTest.java
``` java
public class HelpWindowTest extends GuiUnitTest {

    private HelpWindow helpWindow;
    private HelpWindowHandle helpWindowHandle;

    @Before
    public void setUp() throws Exception {
        guiRobot.interact(() -> helpWindow = new HelpWindow());
        Stage helpWindowStage = FxToolkit.setupStage((stage) -> stage.setScene(helpWindow.getRoot().getScene()));
        FxToolkit.showStage();
        helpWindowHandle = new HelpWindowHandle(helpWindowStage);
    }

    @Test
    public void display() {
        URL expectedHelpPage = HelpWindow.class.getResource(USERGUIDE_FILE_PATH);
        assertEquals(expectedHelpPage, helpWindowHandle.getLoadedUrl());
    }
}
```
###### \java\seedu\address\ui\MainWindowTest.java
``` java
public class MainWindowTest extends AddressBookGuiTest {

    @Test
    public void currentThemeDisplayed() {
        // Use menu button
        mainWindowHandle.getMainMenu().changeToDarkThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/DarkTheme.css");
        mainWindowHandle.getMainMenu().changeToRedThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/RedTheme.css");
        mainWindowHandle.getMainMenu().changeToBlueThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/BlueTheme.css");
        mainWindowHandle.getMainMenu().changeToGreenThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/GreenTheme.css");
        mainWindowHandle.getMainMenu().changeToLightThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/LightTheme.css");

        // To show final colour change
        mainWindowHandle.getMainMenu().changeToDarkThemeUsingMenu();
        assertEquals(MainWindow.getCurrentTheme(), "view/DarkTheme.css");

    }
}
```
###### \java\seedu\address\ui\PersonPanelTest.java
``` java
public class PersonPanelTest extends GuiUnitTest {

    private PersonPanel personPanel;
    private PersonPanelHandle personPanelHandle;

    @Before
    public void setUp() {
        Model model = new ModelManager();
        Logic logic = new LogicManager(model);

        guiRobot.interact(() -> personPanel = new PersonPanel(logic));
        uiPartRule.setUiPart(personPanel);
        personPanelHandle = new PersonPanelHandle(personPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0)));
        assertPersonIsDisplayed(ALICE, personPanelHandle);
        postNow(new PersonPanelSelectionChangedEvent(new PersonCard(BOB, 1)));
        assertPersonIsDisplayed(BOB, personPanelHandle);
    }

    /**
     * Asserts that {@code personPanelHandle} displays the details of {@code expectedPerson} correctly
     */
    private void assertPersonIsDisplayed(ReadOnlyPerson expectedPerson, PersonPanelHandle personPanelHandle) {
        guiRobot.pauseForHuman();
        assertEquals(expectedPerson.getName().toString(), personPanelHandle.getName());
        assertEquals("Phone: " + expectedPerson.getPhone().toString(), personPanelHandle.getPhone());
        assertEquals("Email: " + expectedPerson.getEmail().toString(), personPanelHandle.getEmail());
        assertEquals("Address: " + expectedPerson.getAddress().toString(), personPanelHandle.getAddress());
        assertEquals("Birthday: " + expectedPerson.getBirthday().toString(), personPanelHandle.getBirthday());
        personPanelHandle.updateTags();
        assertTagsAreDisplayed(expectedPerson, personPanelHandle);
    }

    /*
     * Asserts that {@code personPanelHandle} displays the tags of {@code expectedPerson} correctly
     */
    private void assertTagsAreDisplayed(ReadOnlyPerson expectedPerson, PersonPanelHandle personPanelHandle) {
        assertEquals(expectedPerson.getTags().stream().map(tag -> tag.tagName).collect(Collectors.toList()),
                personPanelHandle.getTags());
    }
}
```
