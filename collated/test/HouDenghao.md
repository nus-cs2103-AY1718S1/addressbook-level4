# HouDenghao
###### \java\guitests\guihandles\EventCardHandle.java
``` java
/**
 * Provides a handle to a event card in the event list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DESCRIPTION_FIELD_ID = "#description";
    private static final String TIME_FIELD_ID = "#time";

    private final Label idLabel;
    private final Label nameLabel;
    private final Label descriptionLabel;
    private final Label timeLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
        this.timeLabel = getChildNode(TIME_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getName() {
        return nameLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

    public String getTime() {
        return timeLabel.getText();
    }

}
```
###### \java\guitests\guihandles\EventListPanelHandle.java
``` java
/**
 * Provides a handle for {@code EventListPanel} containing the list of {@code EventCard}.
 */
public class EventListPanelHandle extends NodeHandle<ListView<EventCard>> {
    public static final String EVENT_LIST_VIEW_ID = "#eventListView";

    private Optional<EventCard> lastRememberedSelectedEventCard;

    public EventListPanelHandle(ListView<EventCard> eventListPanelNode) {
        super(eventListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code EventCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public EventCardHandle getHandleToSelectedCard() {
        List<EventCard> eventList = getRootNode().getSelectionModel().getSelectedItems();

        if (eventList.size() != 1) {
            throw new AssertionError("Event list size expected 1.");
        }

        return new EventCardHandle(eventList.get(0).getRoot());
    }

    /**
     * Returns the index of the selected card.
     */
    public int getSelectedCardIndex() {
        return getRootNode().getSelectionModel().getSelectedIndex();
    }

    /**
     * Returns true if a card is currently selected.
     */
    public boolean isAnyCardSelected() {
        List<EventCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the event.
     */
    public void navigateToCard(ReadOnlyEvent event) {
        List<EventCard> cards = getRootNode().getItems();
        Optional<EventCard> matchingCard = cards.stream().filter(card -> card.event.equals(event)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Event does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the event card handle of a event associated with the {@code index} in the list.
     */
    public EventCardHandle getEventCardHandle(int index) {
        return getEventCardHandle(getRootNode().getItems().get(index).event);
    }

    /**
     * Returns the {@code EventCardHandle} of the specified {@code event} in the list.
     */
    public EventCardHandle getEventCardHandle(ReadOnlyEvent event) {
        Optional<EventCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.event.equals(event))
                .map(card -> new EventCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Person does not exist."));
    }

    /**
     * Selects the {@code EventCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code EventCard} in the list.
     */
    public void rememberSelectedPersonCard() {
        List<EventCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedEventCard = Optional.empty();
        } else {
            lastRememberedSelectedEventCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code PersonCard} is different from the value remembered by the most recent
     * {@code rememberSelectedPersonCard()} call.
     */
    public boolean isSelectedPersonCardChanged() {
        List<EventCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedEventCard.isPresent();
        } else {
            return !lastRememberedSelectedEventCard.isPresent()
                    || !lastRememberedSelectedEventCard.get().equals(selectedItems.get(0));
        }
    }

    /**
     * Returns the size of the list.
     */
    public int getListSize() {
        return getRootNode().getItems().size();
    }
}
```
###### \java\guitests\guihandles\InformationBoardHandle.java
``` java
/**
 * A handler for the {@code InformationBoard} of the UI
 */
public class InformationBoardHandle extends NodeHandle<TextArea> {

    public static final String INFORMATION_BOARD_ID = "#informationBoard";

    public InformationBoardHandle(TextArea informationBoardNode) {
        super(informationBoardNode);
    }

    /**
     * Returns the text in the information board.
     */
    public String getText() {
        return getRootNode().getText();
    }
}
```
###### \java\seedu\address\logic\commands\FindEventCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) for {@code FindEventCommand}.
 */
public class FindEventCommandTest {
    private Model model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());

    @Test
    public void equals() {
        EventNameContainsKeywordsPredicate firstPredicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("first"));
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("second"));

        FindEventCommand findFirstCommand = new FindEventCommand(firstPredicate);
        FindEventCommand findSecondCommand = new FindEventCommand(secondPredicate);

        // same object -> returns true
        assertTrue(findFirstCommand.equals(findFirstCommand));

        // same values -> returns true
        FindEventCommand findFirstCommandCopy = new FindEventCommand(firstPredicate);
        assertTrue(findFirstCommand.equals(findFirstCommandCopy));

        // different types -> returns false
        assertFalse(findFirstCommand.equals(1));

        // null -> returns false
        assertFalse(findFirstCommand.equals(null));

        // different event -> returns false
        assertFalse(findFirstCommand.equals(findSecondCommand));
    }

    @Test
    public void execute_zeroKeywords_noEventFound() {
        String expectedMessage = String.format(MESSAGE_EVENTS_LISTED_OVERVIEW, 0);
        FindEventCommand command = prepareCommand(" ");
        assertCommandSuccess(command, expectedMessage, Collections.emptyList());
    }

    @Test
    public void execute_multipleKeywords_multipleEventsFound() {
        String expectedMessage = String.format
                (MESSAGE_EVENTS_LISTED_OVERVIEW, 4);
        FindEventCommand command = prepareCommand("meeting");
        assertCommandSuccess(command, expectedMessage, Arrays.asList(FIRST, SECOND, THIRD, FOURTH));
    }

    /**
     * Parses {@code userInput} into a {@code FindEventCommand}.
     */
    private FindEventCommand prepareCommand(String userInput) {
        FindEventCommand command =
                new FindEventCommand(new EventNameContainsKeywordsPredicate(Arrays.asList(userInput.split("\\s+"))));
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code command} is successfully executed, and<br>
     *     - the command feedback is equal to {@code expectedMessage}<br>
     *     - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code expectedList}<br>
     *     - the {@code AddressBook} in model remains the same after executing the {@code command}
     */
    private void assertCommandSuccess(FindEventCommand command, String expectedMessage, List<ReadOnlyEvent> expected) {
        AddressBook expectedAddressBook = new AddressBook(model.getAddressBook());
        CommandResult commandResult = command.execute();
        assertEquals(expectedMessage, commandResult.feedbackToUser);
        assertEquals(expected, model.getFilteredEventList());
        assertEquals(expectedAddressBook, model.getAddressBook());
    }
}
```
###### \java\seedu\address\logic\commands\ListEventCommandTest.java
``` java
public class ListEventCommandTest {

    private Model model;
    private Model expectedModel;
    private ListEventCommand listECommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), model.getEventList(), new UserPrefs());

        listECommand = new ListEventCommand();
        listECommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listECommand, model, ListEventCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstEventOnly(model);
        assertCommandSuccess(listECommand, model, ListEventCommand.MESSAGE_SUCCESS, expectedModel);
    }

}
```
###### \java\seedu\address\logic\commands\SortCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortCommand.
 */
public class SortCommandTest {

    private Model filledModel;
    private Model emptyModel;
    private Model personModel;


    @Before
    public void setUp() {
        filledModel = new ModelManager(getUnsortedAddressBook(), getUnsortedEventList(), new UserPrefs());
        emptyModel = new ModelManager(getEmptyAddressBook(), getEmptyEventList(), new UserPrefs());
        personModel = new ModelManager(getUnsortedAddressBook(), getEmptyEventList(), new UserPrefs());
    }

    @Test
    public void executeEmptyListShowEmptylist() {
        SortCommand command = prepareCommand(emptyModel);
        assertSuccess(command, SortCommand.MESSAGE_SUCCESS, Collections.emptyList(),
                Collections.emptyList(), emptyModel);
    }

    @Test
    public void executeAllFilledListShowSortedList() {
        SortCommand command = prepareCommand(filledModel);
        assertSuccess(command, SortCommand.MESSAGE_SUCCESS, Arrays.asList(ALICE, BENSON, CARL, DANIEL),
                Arrays.asList(FOURTH, SECOND, FIRST, THIRD), filledModel);
    }

    @Test
    public void executeOnlyOneFilledListShowOneSortedList() {
        SortCommand commandOne = prepareCommand(personModel);
        assertSuccess(commandOne, SortCommand.MESSAGE_SUCCESS, Arrays.asList(ALICE, BENSON, CARL, DANIEL),
                Collections.emptyList(), personModel);
    }

    /**
     * Prepares for {@code SortCommand} .
     */
    private SortCommand prepareCommand(Model model) {
        SortCommand command = new SortCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code c} is successfully executed, and<br>
     *     - the command feedback is equal to {@code msg}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code p}<br>
     *     - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code e}<br>
     */
    private void assertSuccess(SortCommand c, String msg, List<ReadOnlyPerson> p, List<ReadOnlyEvent> e, Model m) {
        CommandResult commandResult = c.execute();
        assertEquals(msg, commandResult.feedbackToUser);
        assertEquals(p, m.getFilteredPersonList());
        assertEquals(e, m.getFilteredEventList());
    }

}
```
###### \java\seedu\address\logic\commands\SortEventCommandTest.java
``` java
/**
 * Contains integration tests (interaction with the Model) and unit tests for SortEventCommand.
 */
public class SortEventCommandTest {

    private Model filledModel;
    private Model emptyModel;
    private Model eventModel;

    @Before
    public void setUp() {
        filledModel = new ModelManager(getUnsortedAddressBook(), getUnsortedEventList(), new UserPrefs());
        emptyModel = new ModelManager(getEmptyAddressBook(), getEmptyEventList(), new UserPrefs());
        eventModel = new ModelManager(getEmptyAddressBook(), getUnsortedEventList(), new UserPrefs());
    }

    @Test
    public void executeEmptyListShowEmptylist() {
        SortEventCommand command = prepareCommand(emptyModel);
        assertSuccess(command, SortEventCommand.MESSAGE_SUCCESS, Collections.emptyList(),
                Collections.emptyList(), emptyModel);
    }

    @Test
    public void executeAllFilledListShowSortedList() {
        SortEventCommand command = prepareCommand(filledModel);
        assertSuccess(command, SortEventCommand.MESSAGE_SUCCESS, Arrays.asList(CARL, ALICE, BENSON, DANIEL),
                Arrays.asList(FIRST, SECOND, THIRD, FOURTH), filledModel);
    }

    @Test
    public void executeOnlyOneFilledListShowOneSortedList() {
        SortEventCommand commandTwo = prepareCommand(eventModel);
        assertSuccess(commandTwo, SortEventCommand.MESSAGE_SUCCESS, Collections.emptyList(),
                Arrays.asList(FIRST, SECOND, THIRD, FOURTH), eventModel);
    }

    /**
     * Prepares for {@code SortEventCommand} .
     */
    private SortEventCommand prepareCommand(Model model) {
        SortEventCommand command = new SortEventCommand();
        command.setData(model, new CommandHistory(), new UndoRedoStack());
        return command;
    }

    /**
     * Asserts that {@code c} is successfully executed, and<br>
     *     - the command feedback is equal to {@code msg}<br>
     *     - the {@code FilteredList<ReadOnlyPerson>} is equal to {@code p}<br>
     *     - the {@code FilteredList<ReadOnlyEvent>} is equal to {@code e}<br>
     */
    private void assertSuccess(SortEventCommand c, String msg, List<ReadOnlyPerson> p, List<ReadOnlyEvent> e, Model m) {
        CommandResult commandResult = c.execute();
        assertEquals(msg, commandResult.feedbackToUser);
        assertEquals(p, m.getFilteredPersonList());
        assertEquals(e, m.getFilteredEventList());
    }

}
```
###### \java\seedu\address\logic\parser\AddressBookParserTest.java
``` java
    @Test
    public void parseCommandFindE() throws Exception {
        List<String> keywords = Arrays.asList("first", "second", "third");
        FindEventCommand command = (FindEventCommand) parser.parseCommand(
                FindEventCommand.COMMAND_WORD + " " + keywords.stream().collect(Collectors.joining(" ")));
        assertEquals(new FindEventCommand(new EventNameContainsKeywordsPredicate(keywords)), command);
    }

    @Test
    public void parseCommandShowP() throws Exception {
        ShowParticipantsCommand command = (ShowParticipantsCommand) parser.parseCommand(
                ShowParticipantsCommand.COMMAND_WORD + " " + INDEX_FIRST_EVENT.getOneBased());
        assertEquals(new ShowParticipantsCommand(INDEX_FIRST_EVENT), command);
    }

    @Test
    public void parseCommandSort() throws Exception {
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD) instanceof SortCommand);
        assertTrue(parser.parseCommand(SortCommand.COMMAND_WORD + " 3") instanceof SortCommand);
    }

    @Test
    public void parseCommandSortE() throws Exception {
        assertTrue(parser.parseCommand(SortEventCommand.COMMAND_WORD) instanceof SortEventCommand);
        assertTrue(parser.parseCommand(SortEventCommand.COMMAND_WORD + " 3") instanceof SortEventCommand);
    }

```
###### \java\seedu\address\logic\parser\FindEventCommandParserTest.java
``` java
public class FindEventCommandParserTest {

    private FindEventCommandParser parser = new FindEventCommandParser();

    @Test
    public void parseEmptyArgThrowsParseException() {
        assertParseFailure(parser, "     ",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindEventCommand.MESSAGE_USAGE));
    }

    @Test
    public void parseValidArgsReturnsFindEventCommand() {
        // no leading and trailing whitespaces
        FindEventCommand expectedFindEventCommand =
                new FindEventCommand(new EventNameContainsKeywordsPredicate(Arrays.asList("First", "Second")));
        assertParseSuccess(parser, "First Second", expectedFindEventCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n First \n \t Second  \t", expectedFindEventCommand);
    }

}
```
###### \java\seedu\address\logic\parser\ShowParticipantsCommandParserTest.java
``` java
public class ShowParticipantsCommandParserTest {

    private ShowParticipantsCommandParser parser = new ShowParticipantsCommandParser();

    @Test
    public void parseValidArgsReturnsShowParticipantsCommand() {
        assertParseSuccess(parser, "1", new ShowParticipantsCommand(INDEX_FIRST_EVENT));
    }

    @Test
    public void parseInvalidArgsThrowsParseException() {
        assertParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, ShowParticipantsCommand.MESSAGE_USAGE));
    }
}
```
###### \java\seedu\address\model\event\EventNameContainsKeywordsPredicateTest.java
``` java
public class EventNameContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        EventNameContainsKeywordsPredicate firstPredicate =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);
        EventNameContainsKeywordsPredicate secondPredicate =
                new EventNameContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        EventNameContainsKeywordsPredicate firstPredicateCopy =
                new EventNameContainsKeywordsPredicate(firstPredicateKeywordList);

        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different events -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testNameContainsKeywordsReturnsTrue() {
        // One keyword
        EventNameContainsKeywordsPredicate predicate =
                new EventNameContainsKeywordsPredicate(Collections.singletonList("First"));

        assertTrue(predicate.test(new EventBuilder().withName("First Meeting").build()));

        // Multiple keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("First", "Meeting"));
        assertTrue(predicate.test(new EventBuilder().withName("First Meeting").build()));

        // Only one matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("First", "Meeting"));
        assertTrue(predicate.test(new EventBuilder().withName("Second Meeting").build()));

        // Mixed-case keywords
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("firST", "mEeting"));
        assertTrue(predicate.test(new EventBuilder().withName("First Meeting").build()));
    }

    @Test
    public void testNameDoesNotContainKeywordsReturnsFalse() {
        // Zero keywords
        EventNameContainsKeywordsPredicate predicate = new EventNameContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new EventBuilder().withName("First").build()));

        // Non-matching keyword
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("First"));
        assertFalse(predicate.test(new EventBuilder().withName("Second Meeting").build()));

        // Keywords match description and time but does not match name
        predicate = new EventNameContainsKeywordsPredicate(Arrays.asList("coding", "01/01/2017"));
        assertFalse(predicate.test(
                new EventBuilder().withName("Meeting").withDescription("coding").withTime("01/01/2017").build()));
    }
}
```
###### \java\seedu\address\model\person\PersonJoinsEventsPredicateTest.java
``` java
public class PersonJoinsEventsPredicateTest {
    private Model model;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), getTypicalEventList(), new UserPrefs());
        joinEvents(model);
    }

    @Test
    public void equals() {
        String firstPredicateKeyword = "first";
        String secondPredicateKeyword = "second";

        PersonJoinsEventsPredicate firstPredicate = new PersonJoinsEventsPredicate(firstPredicateKeyword);
        PersonJoinsEventsPredicate secondPredicate = new PersonJoinsEventsPredicate(secondPredicateKeyword);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        PersonJoinsEventsPredicate firstPredicateCopy = new PersonJoinsEventsPredicate(firstPredicateKeyword);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate == null);

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void testPersonJoinsEventsReturnsTrue() {
        PersonJoinsEventsPredicate predicate =
                new PersonJoinsEventsPredicate("First meeting");
        ReadOnlyPerson target = model.getFilteredPersonList().get(0);
        assertTrue(predicate.test(target));
    }

    @Test
    public void testPersonDoesNotJoinEventsReturnsFalse() {
        PersonJoinsEventsPredicate predicate =
                new PersonJoinsEventsPredicate("First Meeting");
        ReadOnlyPerson target = model.getFilteredPersonList().get(5);
        assertFalse(predicate.test(target));
    }
}
```
###### \java\seedu\address\testutil\TypicalEvents.java
``` java
    /**
     * Returns an empty {@code EventList} with all the unsorted events.
     */
    public static EventList getUnsortedEventList() {
        EventList el = new EventList();
        for (ReadOnlyEvent event : getUnsortedEvents()) {
            try {
                el.addEvent(event);
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
        return el;
    }

    /**
     * Returns an empty {@code EventList}.
     */
    public static EventList getEmptyEventList() {
        EventList el = new EventList();
        return el;
    }

```
###### \java\seedu\address\testutil\TypicalEvents.java
``` java
    public static List<ReadOnlyEvent> getUnsortedEvents() {
        return new ArrayList<>(Arrays.asList(FOURTH, SECOND, FIRST, THIRD));
    }
}
```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    /**
     * Returns an {@code AddressBook} with all the unsorted persons.
     */
    public static AddressBook getUnsortedAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyPerson person : getUnsortedPersons()) {
            try {
                ab.addPerson(person);
            } catch (DuplicatePersonException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    /**
     * Returns an empty {@code AddressBook}.
     */
    public static AddressBook getEmptyAddressBook() {
        AddressBook ab = new AddressBook();
        return ab;
    }

```
###### \java\seedu\address\testutil\TypicalPersons.java
``` java
    public static List<ReadOnlyPerson> getUnsortedPersons() {
        return new ArrayList<>(Arrays.asList(CARL, ALICE, BENSON, DANIEL));
    }
}
```
###### \java\seedu\address\ui\EventCardTest.java
``` java
public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no change made to Event
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 1);
        uiPartRule.setUiPart(eventCard);
        assertEventCardDisplay(eventCard, event, 1);

        // changes made to Event reflects on card
        guiRobot.interact(() -> {
            event.setEventName(FIRST.getEventName());
            event.setEventDescription(FIRST.getDescription());
            event.setETime(FIRST.getEventTime());
        });
        assertEventCardDisplay(eventCard, event, 1);
    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 1);

        // same event, same index -> returns true
        EventCard copy = new EventCard(event, 1);
        assertTrue(eventCard.equals(copy));

        // same object -> returns true
        assertTrue(eventCard.equals(eventCard));

        // null -> returns false
        assertFalse(eventCard == null);

        // different types -> returns false
        assertFalse(eventCard.equals(0));

        // different events, same index -> returns false
        Event differentEvent = new EventBuilder().withName("differentName").build();
        assertFalse(eventCard.equals(new EventCard(differentEvent, 1)));

        // same event, different indexes -> returns false
        assertFalse(eventCard.equals(new EventCard(event, 2)));
    }

    /**
     * Asserts that {@code eventCard} displays the details of {@code expectedEvent} correctly
     * and matches {@code expectedId}.
     */
    private void assertEventCardDisplay(EventCard eventCard, ReadOnlyEvent expectedEvent, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(eventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify event details are displayed correctly
        assertCardDisplaysEvent(expectedEvent, eventCardHandle);
    }
}
```
###### \java\seedu\address\ui\EventListPanelTest.java
``` java
public class EventListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyEvent> TYPICAL_EVENTS =
            FXCollections.observableList(getTypicalEvents());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_EVENT);

    private EventListPanelHandle eventListPanelHandle;

    @Before
    public void setUp() {
        EventListPanel eventListPanel = new EventListPanel(TYPICAL_EVENTS);
        uiPartRule.setUiPart(eventListPanel);

        eventListPanelHandle = new EventListPanelHandle(getChildNode(eventListPanel.getRoot(),
                EventListPanelHandle.EVENT_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_EVENTS.size(); i++) {
            eventListPanelHandle.navigateToCard(TYPICAL_EVENTS.get(i));
            ReadOnlyEvent expectedPerson = TYPICAL_EVENTS.get(i);
            EventCardHandle actualCard = eventListPanelHandle.getEventCardHandle(i);

            assertCardDisplaysEvent(expectedPerson, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        EventCardHandle expectedCard = eventListPanelHandle.getEventCardHandle(INDEX_SECOND_EVENT.getZeroBased());
        EventCardHandle selectedCard = eventListPanelHandle.getHandleToSelectedCard();
        assertEventCardEquals(expectedCard, selectedCard);
    }
}
```
###### \java\seedu\address\ui\InformationBoardTest.java
``` java
public class InformationBoardTest extends GuiUnitTest {

    private static final NewResultAvailableEvent FIRST_RESULT_EVENT_STUB = new NewResultAvailableEvent("Stub");
    private static final NewResultAvailableEvent SECOND_RESULT_EVENT_STUB = new NewResultAvailableEvent("Details: ");

    private InformationBoardHandle informationBoardHandle;

    @Before
    public void setUp() {
        InformationBoard informationBoard = new InformationBoard();
        uiPartRule.setUiPart(informationBoard);

        informationBoardHandle = new InformationBoardHandle(getChildNode(informationBoard.getRoot(),
                InformationBoardHandle.INFORMATION_BOARD_ID));
    }

    @Test
    public void display() {
        // default result text
        guiRobot.pauseForHuman();
        assertEquals("", informationBoardHandle.getText());

        //no result received
        postNow(FIRST_RESULT_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals("", informationBoardHandle.getText());

        // new result received
        postNow(SECOND_RESULT_EVENT_STUB);
        guiRobot.pauseForHuman();
        assertEquals(SECOND_RESULT_EVENT_STUB.message, informationBoardHandle.getText());
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertEventCardEquals(EventCardHandle expectedCard, EventCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getName(), actualCard.getName());
        assertEquals(expectedCard.getDescription(), actualCard.getDescription());
        assertEquals(expectedCard.getTime(), actualCard.getTime());
    }

```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEvent}.
     */
    public static void assertCardDisplaysEvent(ReadOnlyEvent expectedEvent, EventCardHandle actualCard) {
        assertEquals(expectedEvent.getEventName().fullEventName, actualCard.getName());
        assertEquals(expectedEvent.getDescription().eventDesc, actualCard.getDescription());
        assertEquals(expectedEvent.getEventTime().eventTime, actualCard.getTime());
    }

```
