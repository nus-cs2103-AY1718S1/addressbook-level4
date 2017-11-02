# Melvin-leo
###### \java\guitests\guihandles\MeetingCardHandle.java
``` java
/**
 * Provides a handle to a meeting card in the meeting list panel.
 */
public class MeetingCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String PLACE_FIELD_ID = "#place";
    private static final String PHONENUM_FIELD_ID = "#phoneNum";
    private static final String PERSONTOMEET_FIELD_ID = "#person";
    private static final String DATETIME_FIELD_ID = "#date";

    private final Label idLabel;
    private final Label nameMeetingLabel;
    private final Label dateTimeLabel;
    private final Label phoneNumLabel;
    private final Label personToMeetLabel;
    private final Label placeLabel;

    public MeetingCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameMeetingLabel = getChildNode(NAME_FIELD_ID);
        this.placeLabel = getChildNode(PLACE_FIELD_ID);
        this.phoneNumLabel = getChildNode(PHONENUM_FIELD_ID);
        this.personToMeetLabel = getChildNode(PERSONTOMEET_FIELD_ID);
        this.dateTimeLabel = getChildNode(DATETIME_FIELD_ID);

    }

    public String getId() {
        return idLabel.getText();
    }

    public String getNameMeeting() {
        return nameMeetingLabel.getText();
    }

    public String getPlace() {
        return placeLabel.getText();
    }

    public String getPhoneNum() {
        return phoneNumLabel.getText();
    }

    public String getDateTime() {
        return dateTimeLabel.getText();
    }

    public String getPersonToMeet() {
        return personToMeetLabel.getText();
    }

}
```
###### \java\guitests\guihandles\MeetingListPanelHandle.java
``` java
/**
 * Provides a handle for {@code MeetingListPanel} containing the list of {@code MeetingCard}.
 */

public class MeetingListPanelHandle extends NodeHandle<ListView<MeetingCard>> {
    public static final String MEETING_LIST_VIEW_ID = "#meetingListView";

    private Optional<MeetingCard> lastRememberedSelectedMeetingCard;

    public MeetingListPanelHandle(ListView<MeetingCard> meetingListPanelNode) {
        super(meetingListPanelNode);
    }

    /**
     * Returns a handle to the selected {@code MeetingCardHandle}.
     * A maximum of 1 item can be selected at any time.
     * @throws AssertionError if no card is selected, or more than 1 card is selected.
     */
    public MeetingCardHandle getHandleToSelectedCard() {
        List<MeetingCard> meetingList = getRootNode().getSelectionModel().getSelectedItems();

        if (meetingList.size() != 1) {
            throw new AssertionError("Meeting list size expected 1.");
        }

        return new MeetingCardHandle(meetingList.get(0).getRoot());
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
        List<MeetingCard> selectedCardsList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedCardsList.size() > 1) {
            throw new AssertionError("Card list size expected 0 or 1.");
        }

        return !selectedCardsList.isEmpty();
    }

    /**
     * Navigates the listview to display and select the meeting.
     */
    public void navigateToCard(ReadOnlyMeeting meeting) {
        List<MeetingCard> cards = getRootNode().getItems();
        Optional<MeetingCard> matchingCard = cards.stream().filter(card -> card.meeting.equals(meeting)).findFirst();

        if (!matchingCard.isPresent()) {
            throw new IllegalArgumentException("Meeting does not exist.");
        }

        guiRobot.interact(() -> {
            getRootNode().scrollTo(matchingCard.get());
            getRootNode().getSelectionModel().select(matchingCard.get());
        });
        guiRobot.pauseForHuman();
    }

    /**
     * Returns the meeting card handle of a meeting associated with the {@code index} in the list.
     */
    public MeetingCardHandle getMeetingCardHandle(int index) {
        return getMeetingCardHandle(getRootNode().getItems().get(index).meeting);
    }

    /**
     * Returns the {@code MeetingCardHandle} of the specified {@code meeting} in the list.
     */
    public MeetingCardHandle getMeetingCardHandle(ReadOnlyMeeting meeting) {
        Optional<MeetingCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.meeting.equals(meeting))
                .map(card -> new MeetingCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Meeting does not exist."));
    }

    /**
     * Selects the {@code MeetingCard} at {@code index} in the list.
     */
    public void select(int index) {
        getRootNode().getSelectionModel().select(index);
    }

    /**
     * Remembers the selected {@code MeetingCard} in the list.
     */
    public void rememberSelectedMeetingCard() {
        List<MeetingCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedMeetingCard = Optional.empty();
        } else {
            lastRememberedSelectedMeetingCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code MeetingCard} is different from the value remembered by the most recent
     * {@code rememberSelectedMeetingCard()} call.
     */
    public boolean isSelectedMeetingCardChanged() {
        List<MeetingCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            return lastRememberedSelectedMeetingCard.isPresent();
        } else {
            return !lastRememberedSelectedMeetingCard.isPresent()
                    || !lastRememberedSelectedMeetingCard.get().equals(selectedItems.get(0));
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
###### \java\seedu\address\logic\commands\ListMeetingCommandTest.java
``` java
public class ListMeetingCommandTest {
    private Model model;
    private Model expectedModel;
    private ListCommand listCommand;

    @Before
    public void setUp() {
        model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
        expectedModel = new ModelManager(model.getAddressBook(), new UserPrefs());

        listCommand = new ListCommand();
        listCommand.setData(model, new CommandHistory(), new UndoRedoStack());
    }

    @Test
    public void execute_listIsNotFiltered_showsSameList() {
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }

    @Test
    public void execute_listIsFiltered_showsEverything() {
        showFirstPersonOnly(model);
        assertCommandSuccess(listCommand, model, ListCommand.MESSAGE_SUCCESS, expectedModel);
    }
}
```
###### \java\seedu\address\model\AddressBookTest.java
``` java
    @Test
    public void resetData_withDuplicateMeetings_throwsAssertionError() {
        // Repeat AGEING twice
        List<Person> newPersons = Arrays.asList(new Person(ALICE));
        List<Tag> newTags = new ArrayList<>(ALICE.getTags());
        List<Meeting> newMeetings = Arrays.asList(new Meeting(AGEING), new Meeting(AGEING));
        AddressBookStub newData = new AddressBookStub(newPersons, newTags, newMeetings);

        thrown.expect(AssertionError.class);
        addressBook.resetData(newData);
    }
```
###### \java\seedu\address\model\meeting\DateTimeTest.java
``` java
public class DateTimeTest {
    @Test
    public void isValidDateTime() {
        // invalid DateTime
        assertFalse(DateTime.isValidDateTime("")); // empty string
        assertFalse(DateTime.isValidDateTime(" ")); // spaces only

        // valid DateTime
        assertTrue(DateTime.isValidDateTime("21-12-2018 16:00"));
    }
}
```
###### \java\seedu\address\model\meeting\MeetingContainsFullwordPredicateTest.java
``` java
public class MeetingContainsFullwordPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MeetingContainsKeywordsPredicate firstPredicate =
                new MeetingContainsKeywordsPredicate(firstPredicateKeywordList);
        MeetingContainsKeywordsPredicate secondPredicate =
                new MeetingContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MeetingContainsKeywordsPredicate firstPredicateCopy =
                new MeetingContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        MeetingContainsKeywordsPredicate predicate =
                new MeetingContainsKeywordsPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));

        // Multiple keywords
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));

        // Only one matching keyword
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("Date", "Alice"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Date Study").build()));

        // Mixed-case keywords
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("AliCe", "DaTe"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MeetingContainsKeywordsPredicate predicate = new MeetingContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MeetingBuilder().withNameMeeting("Shopping").build()));

        // Non-matching keyword
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("Melvin"));
        assertFalse(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));

        // Keywords match DateTime, Place, but does not match name
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("30-10-2018", "NUS"));
        assertFalse(predicate.test(new MeetingBuilder().withNameMeeting("Shopping").withPlace("NUS")
                .withDateTime("30-10-2018 15:00").build()));
    }
}
```
###### \java\seedu\address\model\meeting\MeetingContainsKeywordsPredicateTest.java
``` java
public class MeetingContainsKeywordsPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        MeetingContainsKeywordsPredicate firstPredicate =
                new MeetingContainsKeywordsPredicate(firstPredicateKeywordList);
        MeetingContainsKeywordsPredicate secondPredicate =
                new MeetingContainsKeywordsPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        MeetingContainsKeywordsPredicate firstPredicateCopy =
                new MeetingContainsKeywordsPredicate(firstPredicateKeywordList);
        assertTrue(firstPredicate.equals(firstPredicateCopy));

        // different types -> returns false
        assertFalse(firstPredicate.equals(1));

        // null -> returns false
        assertFalse(firstPredicate.equals(null));

        // different person -> returns false
        assertFalse(firstPredicate.equals(secondPredicate));
    }

    @Test
    public void test_nameContainsKeywords_returnsTrue() {
        // One keyword
        MeetingContainsKeywordsPredicate predicate =
                new MeetingContainsKeywordsPredicate(Collections.singletonList("Shopping"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));

        // Multiple keywords
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("Shopping", "Date"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));

        // Only one matching keyword
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("Date", "Study"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Date Study").build()));

        // Mixed-case keywords
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("SHOPping", "DaTe"));
        assertTrue(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        MeetingContainsKeywordsPredicate predicate = new MeetingContainsKeywordsPredicate(Collections.emptyList());
        assertFalse(predicate.test(new MeetingBuilder().withNameMeeting("Shopping").build()));

        // Non-matching keyword
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("Study"));
        assertFalse(predicate.test(new MeetingBuilder().withNameMeeting("Shopping Date").build()));

        // Keywords match DateTime, Place, but does not match name
        predicate = new MeetingContainsKeywordsPredicate(Arrays.asList("30-10-2018", "NUS"));
        assertFalse(predicate.test(new MeetingBuilder().withNameMeeting("Shopping").withPlace("NUS")
                .withDateTime("30-10-2018 15:00").build()));
    }
}
```
###### \java\seedu\address\model\meeting\NameMeetingTest.java
``` java
public class NameMeetingTest {
    @Test
    public void isValidName() {
        // invalid NameMeeting
        assertFalse(NameMeeting.isValidName("")); // empty string
        assertFalse(NameMeeting.isValidName(" ")); // spaces only

        // valid NameMeeting
        assertTrue(NameMeeting.isValidName("project meeting")); // alphabets only
        assertTrue(NameMeeting.isValidName("12345")); // numbers only
        assertTrue(NameMeeting.isValidName("peter's 23rd birthday party")); // with punctuation
        assertTrue(NameMeeting.isValidName("Clementi Mall")); // with capital letters
        assertTrue(NameMeeting.isValidName("Raffles Town Club Room 13 ")); // long names
    }
}
```
###### \java\seedu\address\model\meeting\PlaceTest.java
``` java
public class PlaceTest {
    @Test
    public void isValidAddress() {
        // invalid addresses
        assertFalse(Place.isValidAddress("")); // empty string
        assertFalse(Place.isValidAddress(" ")); // spaces only

        // valid addresses
        assertTrue(Place.isValidAddress("Blk 456, Den Road, #01-355"));
        assertTrue(Place.isValidAddress("-")); // one character
        assertTrue(Place.isValidAddress("Leng Inc; 1234 Market St; San Francisco CA 2349879; USA")); // long address
    }
}
```
###### \java\seedu\address\model\UniqueMeetingListTest.java
``` java
public class UniqueMeetingListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueMeetingList uniqueMeetingList = new UniqueMeetingList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueMeetingList.asObservableList().remove(0);
    }
}
```
###### \java\seedu\address\ui\MeetingCardTest.java
``` java
public class MeetingCardTest extends GuiUnitTest {

    @Test
    public void display() {
        // no tags
        Meeting meeting = new MeetingBuilder().build();
        MeetingCard meetingCard = new MeetingCard(meeting, 1);
        uiPartRule.setUiPart(meetingCard);
        assertCardDisplay(meetingCard, meeting, 1);

        // changes made to Meeting reflects on card
        guiRobot.interact(() -> {
            meeting.setName(DIVING.getName());
            meeting.setPlace(DIVING.getPlace());
            meeting.setDateTime(DIVING.getDate());
            meeting.setPhoneNum(DIVING.getPersonPhone());
            meeting.setPersonName(DIVING.getPersonName());
        });
        assertCardDisplay(meetingCard, meeting, 1);
    }

    @Test
    public void equals() {
        Meeting meeting = new MeetingBuilder().build();
        MeetingCard meetingCard = new MeetingCard(meeting, 0);

        // same meeting, same index -> returns true
        MeetingCard copy = new MeetingCard(meeting, 0);
        assertTrue(meetingCard.equals(copy));

        // same object -> returns true
        assertTrue(meetingCard.equals(meetingCard));

        // null -> returns false
        assertFalse(meetingCard.equals(null));

        // different types -> returns false
        assertFalse(meetingCard.equals(0));

        // different meeting, same index -> returns false
        Meeting differentMeeting = new MeetingBuilder().withNameMeeting("differentName").build();
        assertFalse(meetingCard.equals(new MeetingCard(differentMeeting, 0)));

        // same Meeting, different index -> returns false
        assertFalse(meetingCard.equals(new MeetingCard(meeting, 1)));
    }

    /**
     * Asserts that {@code meetingCard} displays the details of {@code expectedMeeting} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(MeetingCard meetingCard, ReadOnlyMeeting expectedMeeting, int expectedId) {
        guiRobot.pauseForHuman();

        MeetingCardHandle meetingCardHandle = new MeetingCardHandle(meetingCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", meetingCardHandle.getId());

        // verify meeting details are displayed correctly
        assertCardDisplaysMeeting(expectedMeeting, meetingCardHandle);
    }
}
```
###### \java\seedu\address\ui\MeetingListPanelTest.java
``` java
public class MeetingListPanelTest extends GuiUnitTest {

    private static final ObservableList<ReadOnlyMeeting> TYPICAL_MEETINGS =
            FXCollections.observableList(getTypicalMeetings());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_MEETING);

    private MeetingListPanelHandle meetingListPanelHandle;

    @Before
    public void setUp() {
        MeetingListPanel meetingListPanel = new MeetingListPanel(TYPICAL_MEETINGS);
        uiPartRule.setUiPart(meetingListPanel);

        meetingListPanelHandle = new MeetingListPanelHandle(getChildNode(meetingListPanel.getRoot(),
                MeetingListPanelHandle.MEETING_LIST_VIEW_ID));
    }

    @Test
    public void display() {
        for (int i = 0; i < TYPICAL_MEETINGS.size(); i++) {
            meetingListPanelHandle.navigateToCard(TYPICAL_MEETINGS.get(i));
            ReadOnlyMeeting expectedMeeting = TYPICAL_MEETINGS.get(i);
            MeetingCardHandle actualCard = meetingListPanelHandle.getMeetingCardHandle(i);

            assertCardDisplaysMeeting(expectedMeeting, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        MeetingCardHandle expectedCard =
                meetingListPanelHandle.getMeetingCardHandle(INDEX_SECOND_MEETING.getZeroBased());
        MeetingCardHandle selectedCard = meetingListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCard, selectedCard);
    }
}
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEquals(MeetingCardHandle expectedCard, MeetingCardHandle actualCard) {
        assertEquals(expectedCard.getId(), actualCard.getId());
        assertEquals(expectedCard.getNameMeeting(), actualCard.getNameMeeting());
        assertEquals(expectedCard.getPlace(), actualCard.getPlace());
        assertEquals(expectedCard.getPersonToMeet(), actualCard.getPersonToMeet());
        assertEquals(expectedCard.getPhoneNum(), actualCard.getPhoneNum());
        assertEquals(expectedCard.getDateTime(), actualCard.getDateTime());
    }
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedMeeting}.
     */
    public static void assertCardDisplaysMeeting(ReadOnlyMeeting expectedMeeting, MeetingCardHandle actualCard) {
        assertEquals(expectedMeeting.getName().fullName, actualCard.getNameMeeting());
        assertEquals(expectedMeeting.getPersonPhone().phone, actualCard.getPhoneNum());
        assertEquals(expectedMeeting.getPlace().value, actualCard.getPlace());
        assertEquals(expectedMeeting.getDate().value, actualCard.getDateTime());
        assertEquals(expectedMeeting.getPersonName().fullName, actualCard.getPersonToMeet());
    }
```
###### \java\seedu\address\ui\testutil\GuiTestAssert.java
``` java
    /**
     * Asserts that the list in {@code meetingListPanelHandle} displays the details of {@code meetings} correctly and
     * in the correct order.
     */
    public static void assertListMatching(MeetingListPanelHandle meetingListPanelHandle, ReadOnlyMeeting... meetings) {
        for (int i = 0; i < meetings.length; i++) {
            assertCardDisplaysMeeting(meetings[i], meetingListPanelHandle.getMeetingCardHandle(i));
        }
    }

    /**
     * Asserts that the list in {@code personListPanelHandle} displays the details of {@code persons} correctly and
     * in the correct order.
     */
    public static void assertListMatching(MeetingListPanelHandle meetingListPanelHandle,
                                          List<ReadOnlyMeeting> meetings) {
        assertListMatching(meetingListPanelHandle, meetings.toArray(new ReadOnlyMeeting[0]));
    }
```
