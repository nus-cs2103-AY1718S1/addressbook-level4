# fongwz
###### /java/seedu/address/ui/CommandBoxTest.java
``` java
    /**
     * Testing the command box helper
     */
    @Test
    public void commandBoxHelperTest() {
        // Testing populating command box
        guiRobot.push(KeyCode.A);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, AddCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        //Testing DOWN arrow key on command box helper
        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        //Testing UP arrow key on command box helper
        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.DOWN);
        guiRobot.push(KeyCode.UP);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();

        //Testing whether command box helper disappears or appears appropriately
        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.A);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, "ea");
        clearCommandBox();

        guiRobot.push(KeyCode.E);
        guiRobot.push(KeyCode.D);
        guiRobot.push(KeyCode.I);
        guiRobot.push(KeyCode.T);
        guiRobot.push(KeyCode.DOWN);
        assertInputHistory(KeyCode.TAB, EditCommand.MESSAGE_TEMPLATE);
        clearCommandBox();
    }

    /**
     * Clears the text in current command field
     */
    private void clearCommandBox() {
        while (!commandBoxHandle.getInput().isEmpty()) {
            guiRobot.push(KeyCode.BACK_SPACE);
        }
    }
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertBrowserCardEquals(BrowserSelectorCardHandle expectedCard,
                                               BrowserSelectorCardHandle actualCard) {
        assertEquals(expectedCard.getBrowserTypeName(), actualCard.getBrowserTypeName());
    }

    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedBrowser}.
     */
    public static void assertBrowserCardDisplay(BrowserSelectorCard expectedBrowser,
                                                BrowserSelectorCardHandle actualCard) {
        assertEquals(expectedBrowser.getImageString(), actualCard.getBrowserTypeName());
    }
```
###### /java/seedu/address/ui/BrowserSettingsSelectorTest.java
``` java
public class BrowserSettingsSelectorTest extends GuiUnitTest {
    private static final JumpToBrowserListRequestEvent JUMP_TO_LINKEDIN_EVENT =
            new JumpToBrowserListRequestEvent("linkedin");

    private BrowserSettingsSelectorHandle browserSettingsSelectorHandle;
    private SettingsSelector settingsSelector;

    @Before
    public void setUp() {
        settingsSelector = new SettingsSelector();
        uiPartRule.setUiPart(settingsSelector);

        browserSettingsSelectorHandle = new BrowserSettingsSelectorHandle(settingsSelector.getBrowserSelectorList());
    }

    @Test
    public void display() {
        for (int i = 0; i < 3; i++) {
            browserSettingsSelectorHandle.navigateToCard(settingsSelector.getBrowserItems().get(i));
            BrowserSelectorCard expectedBrowser = settingsSelector.getBrowserItems().get(i);
            BrowserSelectorCardHandle actualCard = browserSettingsSelectorHandle.getBrowserSelectorCardHandle(i);

            assertBrowserCardDisplay(expectedBrowser, actualCard);
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_LINKEDIN_EVENT);
        guiRobot.pauseForHuman();

        BrowserSelectorCardHandle expectedCard = browserSettingsSelectorHandle.getBrowserSelectorCardHandle(0);
        BrowserSelectorCardHandle selectedCard = browserSettingsSelectorHandle.getHandleToSelectedCard();
        assertBrowserCardEquals(expectedCard, selectedCard);
    }
}
```
###### /java/seedu/address/logic/commands/ChooseCommandTest.java
``` java
public class ChooseCommandTest {
    @Rule
    public final EventsCollectorRule eventsCollectorRule = new EventsCollectorRule();

    @Test
    public void execute_validArgs_success() {
        assertExecutionSuccess("linkedin");
        assertExecutionSuccess("meeting");
        assertExecutionSuccess("google");
        assertExecutionSuccess("maps");
    }

    @Test
    public void execute_invalidArgs_failure() {
        Selection.setPersonSelected();
        assertExecutionFailure("badargs", Messages.MESSAGE_INVALID_BROWSER_INDEX);
    }

    /**
     * Executes a {@code ChooseCommand} with the given {@code arguments},
     * and checks that {@code JumpToBrowserListRequestEvent}
     * is raised with the correct index.
     */
    private void assertExecutionSuccess(String args) {
        ChooseCommand chooseCommand = new ChooseCommand(args);

        try {
            CommandResult commandResult = chooseCommand.execute();
            assertEquals(ChooseCommand.MESSAGE_SUCCESS + args,
                    commandResult.feedbackToUser);
        } catch (CommandException ce) {
            throw new IllegalArgumentException("Assert Execution Failed: ", ce);
        }

        JumpToBrowserListRequestEvent event =
                (JumpToBrowserListRequestEvent) eventsCollectorRule.eventsCollector.getMostRecent();
        assertEquals(args, event.browserItem);
    }

    /**
     * Executes a {@code ChooseCommand} with the given {@code arguments},
     * and checks that a {@code CommandException}
     * is thrown with the {@code expectedMessage}.
     */
    private void assertExecutionFailure(String args, String expectedMessage) {
        ChooseCommand chooseCommand = new ChooseCommand(args);

        try {
            chooseCommand.execute();
            fail("The expected CommandException was not thrown.");
        } catch (CommandException ce) {
            assertEquals(expectedMessage, ce.getMessage());
            assertTrue(eventsCollectorRule.eventsCollector.isEmpty());
        }
    }
}
```
###### /java/guitests/guihandles/BrowserSettingsSelectorHandle.java
``` java
/**
 * Provides a handle for {@code browserSelectorList} containing the list of {@code BrowserSelectorCard}.
 */
public class BrowserSettingsSelectorHandle extends NodeHandle<ListView<BrowserSelectorCard>> {
    public static final String BROWSER_LIST_VIEW_ID = "#browserSelectorList";

    //private Optional<PersonCard> lastRememberedSelectedPersonCard;

    public BrowserSettingsSelectorHandle(ListView<BrowserSelectorCard> browserListPanelNode) {
        super(browserListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code PersonCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public BrowserSelectorCardHandle getHandleToSelectedCard() {
        List<BrowserSelectorCard> browserList = getRootNode().getSelectionModel().getSelectedItems();

        if (browserList.size() != 1) {
            throw new AssertionError("Browser list size expected 1.");
        }

        return new BrowserSelectorCardHandle(browserList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }


    /**
     * Navigates the listview to display and select the browser.
     */
    public void navigateToCard(BrowserSelectorCard browserType) {
        List<BrowserSelectorCard> cards = getRootNode().getItems();
        Optional<BrowserSelectorCard> matchingCard = cards.stream()
                .filter(card -> card.getImageString().equals(browserType.getImageString())).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Person does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the person card handle of a person associated with the {@code index} in the list.
     */
    public BrowserSelectorCardHandle getBrowserSelectorCardHandle(int index) {
        return new BrowserSelectorCardHandle(getRootNode().getItems().get(index).getRoot());
    }
}
```
###### /java/guitests/guihandles/BrowserSelectorCardHandle.java
``` java
/**
 * Provides a handle to a person card in the person list panel.
 */
public class BrowserSelectorCardHandle extends NodeHandle<Node> {
    private static final String TEXT_FIELD_ID = "#browserCardText";

    private final Label textLabel;

    public BrowserSelectorCardHandle(Node cardNode) {
        super(cardNode);

        this.textLabel = getChildNode(TEXT_FIELD_ID);
    }

    public String getBrowserTypeName() {
        return textLabel.getText();
    }
}
```
