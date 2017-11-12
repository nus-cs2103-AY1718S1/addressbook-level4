# hansiang93
###### /java/guitests/guihandles/WebViewUtil.java
``` java
/**
 * Helper methods for dealing with {@code WebView}.
 */
public class WebViewUtil {

    /**
     * Returns the {@code URL} of the currently loaded page in the {@code webView}.
     */
    public static URL getLoadedUrl(WebView webView) {
        try {
            return new URL(webView.getEngine().getLocation());
        } catch (MalformedURLException mue) {
            throw new AssertionError("webView should not be displaying an invalid URL.", mue);
        }
    }

    /**
     * If the {@code browserPanelHandle}'s {@code WebView} is loading, sleeps the thread till it is successfully loaded.
     */
    public static void waitUntilBrowserLoaded(BrowserPanelHandle browserPanelHandle) {
        new GuiRobot().waitForEvent(browserPanelHandle::isLoaded);
    }
}
```
###### /java/seedu/address/logic/commands/FilterCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindCommand}.
 */
public class FilterCommandTest {
    private Model model = new ModelManager(getSortedTypicalAddressBook(), new UserPrefs(), new UserPerson());

    @Test
    public void equals() {
        FilterKeywordsPredicate firstPredicate =
                new FilterKeywordsPredicate(Collections.singletonList("first"));
        FilterKeywordsPredicate secondPredicate =
                new FilterKeywordsPredicate(Collections.singletonList("second"));

        FilterCommand filterFirstCommand = new FilterCommand(firstPredicate);
        FilterCommand filterSecondCommand = new FilterCommand(secondPredicate);

        // same object -> returns true
        assertTrue(filterFirstCommand.equals(filterFirstCommand));

        // same values -> returns true
        FilterCommand findFirstCommandCopy = new FilterCommand(firstPredicate);
        assertTrue(filterFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(filterFirstCommand.equals(1));

        // null -> returns false
        assertFalse(filterFirstCommand.equals(null));

        // different person -> returns false
        assertFalse(filterFirstCommand.equals(filterSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noPersonFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 0);
        FilterCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multiplePersonsFound() {
        String expectedMessage = String.format(MESSAGE_PERSONS_LISTED_OVERVIEW, 3);
        FilterCommand command = prepareCommand("owesMoney");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(BENSON, DANIEL, ELLE));
    }

    /**
     * Parses {@code userInput} into a {@code FindCommand}.
     */
    private FilterCommand prepareCommand(String userInput) {
        FilterCommand command =
                new FilterCommand(new FilterKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FilterCommand command, String expectedMessage,
                                      List<ReadOnlyPerson> expectedList) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();

        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expectedList, model.getFilteredPersonList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### /java/seedu/address/logic/commands/WebCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code WebCommand}.
 */
public class WebCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validWebLinkName_success() {
        assertExecutionSuccess(WEBLINK_FACEBOOK);
        assertExecutionSuccess(WEBLINK_SEARCH);
        assertExecutionSuccess(WEBLINK_MAPS);
    }

    @Test
    public void execute_invalidWebLink_failure() {
        String invalidWeblink = "invalidString";

        assertExecutionFailure(invalidWeblink, Messages.MESSAGE_INVALID_WEBLINK_TAG);
    }

    @Test
    public void equals() {
        WebCommand webMapsCommand = new WebCommand(WEBLINK_MAPS);
        WebCommand webFacebookCommand = new WebCommand(WEBLINK_FACEBOOK);

        // same object -> returns true
        assertTrue(webMapsCommand.equals(webMapsCommand));

        // same values -> returns true
        WebCommand selectFirstCommandCopy = new WebCommand(WEBLINK_MAPS);
        assertTrue(webMapsCommand.equals(selectFirstCommandCopy));

        // different types -> returns false
        assertFalse(webMapsCommand.equals(1));

        // null -> returns false
        assertFalse(webMapsCommand.equals(null));

        // different person -> returns false
        assertFalse(webMapsCommand.equals(webFacebookCommand));
    }

    /**
     * Executes a {@code WebCommand} with the given {@code webLink},
     * and checks that {@code WebsiteSelectionRequestEvent}
     * is raised with the correct webLink.
     */
    private void assertExecutionSuccess(String weblink) {
        WebCommand webCommand = new WebCommand(weblink);

        try {
            CommandResult commandResult = webCommand.execute();
            assertEquals(WebCommand.MESSAGE_SUCCESS, commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Execution of command should not fail.", ce);
        }

        WebsiteSelectionRequestEvent lastEvent =
                (WebsiteSelectionRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(weblink, lastEvent.getWebsiteRequested());
    }

    /**
     * Executes a {@code WebCommand} with the given {@code webLink}, and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String webLink, String expectedMessage) {
        WebCommand webCommand = new WebCommand(webLink);

        try {
            webCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
```
###### /java/seedu/address/logic/parser/FilterCommandParserTest.java
``` java
public class FilterCommandParserTest {

    private FilterCommandParser parser = new FilterCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FilterCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FilterCommand expectedFindCommand =
                new FilterCommand(new FilterKeywordsPredicate(Arrays.asList("Neighbours", "Friends")));
        assertParseSuccess(parser, "Neighbours Friends", expectedFindCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n Neighbours \n \t Friends  \t", expectedFindCommand);
    }

}
```
###### /java/seedu/address/logic/parser/WebCommandParserTest.java
``` java
/**
 * Test scope: similar to {@code DeleteCommandParserTest}.
 * @see DeleteCommandParserTest
 */
public class WebCommandParserTest {

    private WebCommandParser parser = new WebCommandParser();

    @Test
    public void parse_validArgs_returnsWebCommand() {
        assertParseSuccess(parser, "maps", new WebCommand(WebCommandParser.WEBSITES_MAP.get("maps")));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "a", String.format(MESSAGE_INVALID_COMMAND_FORMAT, WebCommand.MESSAGE_USAGE));
    }
}
```
###### /java/seedu/address/testutil/TypicalWebLinks.java
``` java
/**
 * A utility class containing a list of {@code Index} objects to be used in tests.
 */
public class TypicalWebLinks {
    public static final String WEBLINK_FACEBOOK = WEBSITES_MAP.get("facebook");
    public static final String WEBLINK_MAPS = WEBSITES_MAP.get("maps");
    public static final String WEBLINK_SEARCH = WEBSITES_MAP.get("search");
}
```
###### /java/seedu/address/ui/BrowserPanelTest.java
``` java
public class BrowserPanelTest extends GuiUnitTest {
    private PersonPanelSelectionChangedEvent selectionChangedEventStub;
    private WebsiteSelectionRequestEvent mapsSelectionEventStub;
    private WebsiteSelectionRequestEvent searchSelectionEventStub;

    private BrowserPanel browserPanel;
    private BrowserPanelHandle browserPanelHandle;

    @Before
    public void setUp() {
        selectionChangedEventStub = new PersonPanelSelectionChangedEvent(new PersonCard(ALICE, 0));
        mapsSelectionEventStub = new WebsiteSelectionRequestEvent("mapsView");
        searchSelectionEventStub = new WebsiteSelectionRequestEvent("searchView");

        guiRobot.interact(() -> browserPanel = new BrowserPanel());
        uiPartRule.setUiPart(browserPanel);

        browserPanelHandle = new BrowserPanelHandle(browserPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default web page
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertEquals(expectedDefaultPageUrl, browserPanelHandle.getLoadedUrl());

        // associated web page of a person
        postNow(selectionChangedEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }

    @Test
    public void buttonPressDisplay() throws Exception {
        postNow(selectionChangedEventStub);
        // associated maps page of a person
        postNow(mapsSelectionEventStub);
        String expectedPersonMapsUrl = "/maps/search/";

        waitUntilBrowserLoaded(browserPanelHandle);
        assertTrue(browserPanelHandle.getLoadedUrl().toString().contains(expectedPersonMapsUrl));

        // associated search page of a person
        postNow(searchSelectionEventStub);
        URL expectedPersonUrl = new URL(GOOGLE_SEARCH_URL_PREFIX
                + ALICE.getName().fullName.replaceAll(" ", "+") + GOOGLE_SEARCH_URL_SUFFIX);

        waitUntilBrowserLoaded(browserPanelHandle);
        assertEquals(expectedPersonUrl, browserPanelHandle.getLoadedUrl());
    }
}
```
