# yilun-zhu
###### \java\guitests\guihandles\CalendarPanelHandle.java
``` java
/**
 * A handler for the {@code CalendarPanel} of the UI.
 */
public class CalendarPanelHandle extends NodeHandle<Node> {

    public static final String CALENDAR_ID = "#calendar";

    private boolean isWebViewLoaded = true;

    public CalendarPanelHandle(Node calendarPanelNode) {
        super(calendarPanelNode);

        WebView webView = getChildNode(CALENDAR_ID);
        WebEngine engine = webView.getEngine();
        new GuiRobot().interact(() -> engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
            if (newState == Worker.State.RUNNING) {
                isWebViewLoaded = false;
            } else if (newState == Worker.State.SUCCEEDED) {
                isWebViewLoaded = true;
            }
        }));
    }

    /**
     * Returns the {@code URL} of the currently loaded page.
     */
    public URL getLoadedUrl() {
        return WebViewUtil.getLoadedUrl(getChildNode(CALENDAR_ID));
    }

    /**
     * Returns true if the calendarPanel is done loading a page, or if this calendarPanel has yet to load any page.
     */
    public boolean isLoaded() {
        return isWebViewLoaded;
    }
}
```
###### \java\guitests\guihandles\WebViewUtil.java
``` java
    /**
     * If the {@code calendarPanelHandle}'s {@code WebView} is loading,
     * sleeps the thread till it is successfully loaded.
     */
    public static void waitUntilCalendarLoaded(CalendarPanelHandle calendarPanelHandle) {
        new GuiRobot().waitForEvent(calendarPanelHandle::isLoaded);
    }
}
```
###### \java\seedu\address\logic\commands\AddEventCommandTest.java
``` java

public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullCalendarEventThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    /* This test can be used for local testing. Does not work for Travis-CI as it requires user to manually
    authorize the google service.

    @Test
    public void executeCommandSuccessful() {
        CalendarEvent validEvent = new CalendarEventBuilder().build();
        CommandResult commandResult = new AddEventCommand(validEvent).execute();

        assertEquals(String.format(AddEventCommand.MESSAGE_SUCCESS, validEvent), commandResult.feedbackToUser);
    }
    */

    @Test
    public void equals() {
        CalendarEvent halloween = new CalendarEventBuilder().withName("Halloween").build();
        CalendarEvent danceClass = new CalendarEventBuilder().withName("Dance class").build();
        AddEventCommand addHalloweenCommand = new AddEventCommand(halloween);
        AddEventCommand addDanceClassCommand = new AddEventCommand(danceClass);

        // same object -> returns true
        assertTrue(addHalloweenCommand.equals(addHalloweenCommand));

        // same values -> returns true
        AddEventCommand addHalloweenCommandCopy = new AddEventCommand(halloween);
        assertTrue(addHalloweenCommand.equals(addHalloweenCommandCopy));

        // different types -> returns false
        assertFalse(addHalloweenCommand.equals(1));

        // different event -> returns false
        assertFalse(addHalloweenCommand.equals(addDanceClassCommand));
    }

}
```
###### \java\seedu\address\logic\commands\CalendarCommandTest.java
``` java
public class CalendarCommandTest {
    @Test
    public void executeCommandSuccessful() {

        CommandResult commandResult = new CalendarCommand().execute();

        assertEquals(String.format(CalendarCommand.MESSAGE_SUCCESS), commandResult.feedbackToUser);
    }
}
```
###### \java\seedu\address\model\calendarevent\EventEndDateTest.java
``` java
public class EventEndDateTest {

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(EventEndDate.isValidDate("")); // empty string
        assertFalse(EventEndDate.isValidDate(" ")); // spaces only
        assertFalse(EventEndDate.isValidDate("20170809")); // no dash in between
        assertFalse(EventEndDate.isValidDate("phone")); // non-numeric
        assertFalse(EventEndDate.isValidDate("9011-p0-41")); // alphabets within digits
        assertFalse(EventEndDate.isValidDate("9312 1534")); // spaces within digits
        assertFalse(EventEndDate.isValidDate("2017-13-20")); // invalid month
        assertFalse(EventEndDate.isValidDate("2017-12-32")); // invalid day

        // valid date
        assertTrue(EventEndDate.isValidDate("2017-11-30")); // valid format
    }
}
```
###### \java\seedu\address\model\calendarevent\EventEndTimeTest.java
``` java
public class EventEndTimeTest {

    @Test
    public void isValidTime() {
        // invalid time
        assertFalse(EventEndTime.isValidTime("")); // empty string
        assertFalse(EventEndTime.isValidTime(" ")); // spaces only
        assertFalse(EventEndTime.isValidTime("0900")); // no colon in between
        assertFalse(EventEndTime.isValidTime("phone")); // non-numeric
        assertFalse(EventEndTime.isValidTime("p0:41")); // alphabets within digits
        assertFalse(EventEndTime.isValidTime("08 00")); // spaces within digits
        assertFalse(EventEndTime.isValidTime("99:45")); // valid format, invalid hour
        assertFalse(EventEndTime.isValidTime("10:61")); // valid format, invalid minute
        assertFalse(EventEndTime.isValidTime("9:45")); // hour should have 2 digits


        // valid time
        assertTrue(EventEndTime.isValidTime("10:24")); // valid format
    }
}
```
###### \java\seedu\address\model\calendarevent\EventNameTest.java
``` java
public class EventNameTest {

    @Test
    public void isValidEventName() {
        // invalid name
        assertFalse(EventName.isValidEventName("")); // empty string
        assertFalse(EventName.isValidEventName(" ")); // spaces only
        assertFalse(EventName.isValidEventName("^")); // only non-alphanumeric characters
        assertFalse(EventName.isValidEventName("halloween*")); // contains non-alphanumeric characters

        // valid name
        assertTrue(EventName.isValidEventName("halloween party")); // alphabets only
        assertTrue(EventName.isValidEventName("12345")); // numbers only
        assertTrue(EventName.isValidEventName("peter the 2nd")); // alphanumeric characters
        assertTrue(EventName.isValidEventName("Capital Halloween")); // with capital letters
        assertTrue(EventName.isValidEventName("David Roger Jackson Ray Jr 2nd Birthday")); // long names
    }
}
```
###### \java\seedu\address\model\calendarevent\EventStartDateTest.java
``` java
public class EventStartDateTest {

    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(EventStartDate.isValidDate("")); // empty string
        assertFalse(EventStartDate.isValidDate(" ")); // spaces only
        assertFalse(EventStartDate.isValidDate("20170809")); // no dash in between
        assertFalse(EventStartDate.isValidDate("phone")); // non-numeric
        assertFalse(EventStartDate.isValidDate("9011-p0-41")); // alphabets within digits
        assertFalse(EventStartDate.isValidDate("9312 1534")); // spaces within digits
        assertFalse(EventEndDate.isValidDate("2017-13-20")); // invalid month
        assertFalse(EventEndDate.isValidDate("2017-12-32")); // invalid day


        // valid date
        assertTrue(EventStartDate.isValidDate("2017-11-30")); // valid format
    }
}
```
###### \java\seedu\address\model\calendarevent\EventStartTimeTest.java
``` java
public class EventStartTimeTest {

    @Test
    public void isValidTime() {
        // invalid time
        assertFalse(EventStartTime.isValidTime("")); // empty string
        assertFalse(EventStartTime.isValidTime(" ")); // spaces only
        assertFalse(EventStartTime.isValidTime("0900")); // no colon in between
        assertFalse(EventStartTime.isValidTime("phone")); // non-numeric
        assertFalse(EventStartTime.isValidTime("p0:41")); // alphabets within digits
        assertFalse(EventStartTime.isValidTime("08 00")); // spaces within digits
        assertFalse(EventStartTime.isValidTime("99:45")); // valid format, invalid hour
        assertFalse(EventStartTime.isValidTime("10:61")); // valid format, invalid minute
        assertFalse(EventStartTime.isValidTime("9:45")); // hour should have 2 digits


        // valid time
        assertTrue(EventStartTime.isValidTime("10:24")); // valid format
    }
}
```
###### \java\seedu\address\model\person\FindFunctionPredicateTest.java
``` java
public class FindFunctionPredicateTest {

    @Test
    public void equals() {
        List<String> firstPredicateKeywordList = Collections.singletonList("first");
        List<String> secondPredicateKeywordList = Arrays.asList("first", "second");

        FindFunctionPredicate firstPredicate = new FindFunctionPredicate(firstPredicateKeywordList);
        FindFunctionPredicate secondPredicate = new FindFunctionPredicate(secondPredicateKeywordList);

        // same object -> returns true
        assertTrue(firstPredicate.equals(firstPredicate));

        // same values -> returns true
        FindFunctionPredicate firstPredicateCopy = new FindFunctionPredicate(firstPredicateKeywordList);
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
        FindFunctionPredicate predicate = new FindFunctionPredicate(Collections.singletonList("Alice"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Multiple keywords
        predicate = new FindFunctionPredicate(Arrays.asList("Alice", "Bob"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Only one matching keyword
        predicate = new FindFunctionPredicate(Arrays.asList("Bob", "Carol"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Carol").build()));

        // Mixed-case keywords
        predicate = new FindFunctionPredicate(Arrays.asList("aLIce", "bOB"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice Bob").build()));

        // Keywords match phone, email and address
        predicate = new FindFunctionPredicate(Arrays.asList("12345", "alice@email.com", "Main", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withName("Alice").withPhone("12345")
                .withEmail("alice@email.com").withAddress("Main Street").build()));

        //Keywords match phone
        predicate = new FindFunctionPredicate(Arrays.asList("12345"));
        assertTrue(predicate.test(new PersonBuilder().withPhone("12345").build()));

        //Keywords match address
        predicate = new FindFunctionPredicate(Arrays.asList("Main", "Street"));
        assertTrue(predicate.test(new PersonBuilder().withAddress("Main Street").build()));

        //Keywords match email
        predicate = new FindFunctionPredicate(Arrays.asList("alice", "email.com"));
        assertTrue(predicate.test(new PersonBuilder().withEmail("alice@email.com").build()));
    }

    @Test
    public void test_nameDoesNotContainKeywords_returnsFalse() {
        // Zero keywords
        FindFunctionPredicate predicate = new FindFunctionPredicate(Collections.emptyList());
        assertFalse(predicate.test(new PersonBuilder().withName("Alice").build()));

        // Non-matching keyword
        predicate = new FindFunctionPredicate(Arrays.asList("Carol"));
        assertFalse(predicate.test(new PersonBuilder().withName("Alice Bob").build()));


    }
}
```
###### \java\seedu\address\testutil\CalendarEventBuilder.java
``` java
/**
 * A utility class to help with building CalendarEvent objects.
 */
public class CalendarEventBuilder {

    public static final String DEFAULT_NAME = "Halloween";
    public static final String DEFAULT_START_DATE = "2017-10-30";
    public static final String DEFAULT_START_TIME = "16:00";
    public static final String DEFAULT_END_DATE = "2017-10-30";
    public static final String DEFAULT_END_TIME = "22:00";

    private CalendarEvent event;

    public CalendarEventBuilder() {
        EventName defaultName = null;
        try {
            defaultName = new EventName(DEFAULT_NAME);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default event's name are invalid.");
        }
        EventStartDate defaultStartDate = null;
        try {
            defaultStartDate = new EventStartDate(DEFAULT_START_DATE);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default event's start date are invalid.");
        }
        EventStartTime defaultStartTime = null;
        try {
            defaultStartTime = new EventStartTime(DEFAULT_START_TIME);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default event's start time are invalid.");
        }
        EventEndDate defaultEndDate = null;
        try {
            defaultEndDate = new EventEndDate(DEFAULT_END_DATE);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default event's end date are invalid.");
        }
        EventEndTime defaultEndTime = null;
        try {
            defaultEndTime = new EventEndTime(DEFAULT_END_TIME);
        } catch (IllegalValueException e) {
            throw new AssertionError("Default event's end time are invalid.");
        }
        this.event = new CalendarEvent(defaultName, defaultStartDate, defaultStartTime, defaultEndDate, defaultEndTime);
    }

    /**
     * Initializes the CalendarEventBuilder with the data of {@code eventToCopy}.
     */
    public CalendarEventBuilder(ReadOnlyCalendarEvent eventToCopy) {
        this.event = new CalendarEvent(eventToCopy);
    }

    /**
     * Sets the {@code Name} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withName(String name) {
        try {
            this.event.setName(new EventName(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventStartDate} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withStartDate(String date) {
        try {
            this.event.setStartDate(new EventStartDate(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventEndDate} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withEndDate(String date) {
        try {
            this.event.setEndDate(new EventEndDate(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventStartTime} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withStartTime(String time) {
        try {
            this.event.setStartTime(new EventStartTime(time));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("time is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code EventEndTime} of the {@code CalendarEvent} that we are building.
     */
    public CalendarEventBuilder withEndTime(String time) {
        try {
            this.event.setEndTime(new EventEndTime(time));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("time is expected to be unique.");
        }
        return this;
    }

    public CalendarEvent build() {
        return this.event;
    }

}
```
###### \java\seedu\address\testutil\CalendarEventsUtil.java
``` java
/**
 * A utility class for CalendarEvent.
 */
public class CalendarEventsUtil {

    /**
     * Returns an addEvent command string for adding the {@code event}.
     */
    public static String getAddEventCommand(ReadOnlyCalendarEvent event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(ReadOnlyCalendarEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getEventName().toString() + " ");
        sb.append(PREFIX_EVENT_START_DATE + event.getStartDate().value + " ");
        sb.append(PREFIX_EVENT_START_TIME + event.getStartTime().value + " ");
        sb.append(PREFIX_EVENT_END_DATE + event.getEndDate().value + " ");
        sb.append(PREFIX_EVENT_END_TIME + event.getEndTime().value + " ");
        return sb.toString();
    }
}
```
###### \java\seedu\address\ui\CalendarPanelTest.java
``` java

public class CalendarPanelTest extends GuiUnitTest {

    private CalendarPanel calendarPanel;
    private CalendarPanelHandle calendarPanelHandle;

    @Before
    public void setUp() {

        guiRobot.interact(() -> calendarPanel = new CalendarPanel());
        uiPartRule.setUiPart(calendarPanel);

        calendarPanelHandle = new CalendarPanelHandle(calendarPanel.getRoot());
    }

    @Test
    public void display() throws Exception {
        // default window should be updated
        URL expectedDefaultPageUrl = MainApp.class.getResource(FXML_FILE_FOLDER + DEFAULT_PAGE);
        assertFalse(expectedDefaultPageUrl.equals(calendarPanelHandle.getLoadedUrl()));

        /* The section below can be used for local testing. It does not load on Travis-CI.
        postNow(calendarRequestEvent);

        URL expectedCalendarUrl = new URL("https://accounts.google.com/ServiceLogin?service=cl&"
                + "passive=1209600&osid=1&continue=https://calendar.google.com/calendar/render&followup="
                + "https://calendar.google.com/calendar/render&scc=1");

        waitUntilCalendarLoaded(calendarPanelHandle);
        assertTrue(expectedCalendarUrl.equals(calendarPanelHandle.getLoadedUrl()));
        */
    }
}
```
###### \java\systemtests\FindCommandSystemTest.java
``` java
        /* Case: find phone number of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getPhone().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find address of person in address book -> 3 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getAddress().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL, CARL, GEORGE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find email of person in address book -> 1 persons found */
        command = FindCommand.COMMAND_WORD + " " + DANIEL.getEmail().value;
        ModelHelper.setFilteredList(expectedModel, DANIEL);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();

        /* Case: find tags of person in address book -> 1 persons found */
        List<Tag> tags = new ArrayList<>(ALICE.getTags());
        command = FindCommand.COMMAND_WORD + " " + tags.get(0).tagName;
        ModelHelper.setFilteredList(expectedModel, ALICE);
        assertCommandSuccess(command, expectedModel);
        assertSelectedCardUnchanged();
```
