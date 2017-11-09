# shuang-yang
###### \java\guitests\guihandles\EventCardHandle.java
``` java

/**
 * Provides a handle to a person card in the person list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String TITLE_FIELD_ID = "#title";
    private static final String DATE_FIELD_ID = "#date";
    private static final String TIMING_FIELD_ID = "#timing";
    private static final String DESCRIPTION_FIELD_ID = "#description";

    private final Label idLabel;
    private final Label titleLabel;
    private final Label dateLabel;
    private final Label timingLabel;
    private final Label descriptionLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.titleLabel = getChildNode(TITLE_FIELD_ID);
        this.timingLabel = getChildNode(TIMING_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);
        this.descriptionLabel = getChildNode(DESCRIPTION_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getTitle() {
        return titleLabel.getText();
    }

    public String getTiming() {
        return timingLabel.getText();
    }

    public String getDate() {
        return dateLabel.getText();
    }

    public String getDescription() {
        return descriptionLabel.getText();
    }

}
```
###### \java\seedu\address\logic\commands\CommandTestUtil.java
``` java
    public static final String VALID_PERIOD_MIDTERM = "180";
    public static final String VALID_PERIOD_SOCCER = "7";
    public static final String PERIOD_MIDTERM = " " + PREFIX_PERIOD + VALID_PERIOD_MIDTERM;
    public static final String PERIOD_SOCCER = " " + PREFIX_PERIOD + VALID_PERIOD_SOCCER;
    public static final String INVALID_PERIOD = " " + PREFIX_PERIOD + " -1";
```
###### \java\seedu\address\logic\commands\event\EditEventCommandTest.java
``` java

    @Test
    public void execute_eventTimeClashUnfilteredList_failure() {
        Index indexLastEvent = Index.fromOneBased(model.getFilteredEventList().size());
        ReadOnlyEvent lastEvent = model.getFilteredEventList().get(indexLastEvent.getZeroBased());

        EventBuilder eventInList = new EventBuilder(lastEvent);
        Event editedEvent = eventInList.withTitle(VALID_TITLE_MIDTERM).build();

        EditEventCommand.EditEventDescriptor descriptor = new EditEventDescriptorBuilder(editedEvent).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT, descriptor);

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_TIME_CLASH);
    }

    @Test
    public void execute_eventTimeClashFilteredList_failure() {
        showFirstTwoEventsOnly(model);

        //edit event in filtered list to clash with an existing event in address book
        ReadOnlyEvent secondEventInList = model.getAddressBook().getEventList().get(INDEX_SECOND_EVENT.getOneBased());
        EventBuilder eventInList = new EventBuilder(secondEventInList);
        Event editedEvent = eventInList.withTitle(VALID_TITLE_MIDTERM).build();
        EditEventCommand editEventCommand = prepareCommand(INDEX_FIRST_EVENT,
                new EditEventDescriptorBuilder(editedEvent).build());

        assertCommandFailure(editEventCommand, model, EditEventCommand.MESSAGE_TIME_CLASH);
    }
}
```
###### \java\seedu\address\logic\parser\event\AddEventCommandParserTest.java
``` java
        // multiple period - last period accepted
        assertParseSuccess(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + TIMESLOT_SOCCER + DESCRIPTION_SOCCER
                + PERIOD_SOCCER, new AddEventCommand(expectedEvent));
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);
        // missing title prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_TITLE_SOCCER + TIMESLOT_SOCCER
                + DESCRIPTION_SOCCER, expectedMessage);
        // missing timing prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + VALID_TIMESLOT_SOCCER
                + VALID_TIMESLOT_MIDTERM, expectedMessage);
        // missing email prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + TIMESLOT_SOCCER
                + VALID_DESCRIPTION_SOCCER, expectedMessage);
        // all prefixes missing
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_TITLE_SOCCER + VALID_TIMESLOT_SOCCER
                + VALID_DESCRIPTION_SOCCER, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {

        // invalid title
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE + TIMESLOT_SOCCER + DESCRIPTION_SOCCER,
                Title.MESSAGE_TITLE_CONSTRAINTS);

        // invalid timing
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + TITLE_SOCCER + INVALID_TIMESLOT + DESCRIPTION_SOCCER,
                Timeslot.MESSAGE_TIMESLOT_CONSTRAINTS);

        // two invalid values, only first invalid value reported
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_TITLE + INVALID_TIMESLOT + DESCRIPTION_SOCCER,
                Title.MESSAGE_TITLE_CONSTRAINTS);
    }
}
```
###### \java\seedu\address\logic\parser\ParserUtilTest.java
``` java

    @Test
    public void parsePhoto_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parsePhoto(null);
    }

    @Test
    public void parsePhoto_invalidValue_throwsIllegalValueException() throws
            Exception {
        thrown.expect(IllegalValueException.class);
        ParserUtil.parsePhoto(Optional.of(INVALID_PHOTO));
    }

    @Test
    public void parsePhoto_optionalEmpty_returnsOptionalEmpty() throws Exception {
        assertFalse(ParserUtil.parsePhoto(Optional.empty()).isPresent());
    }

    @Test
    public void parsePhoto_validValue_returnsPhoto() throws Exception {
        Photo expectedPhoto = new Photo(VALID_PHOTO);
        Optional<Photo> actualPhoto = ParserUtil.parsePhoto(Optional.of
                (VALID_PHOTO));

        assertEquals(expectedPhoto, actualPhoto.get());
    }

```
###### \java\seedu\address\model\EventListTest.java
``` java

public class EventListTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        EventList eventList = new EventList();
        thrown.expect(UnsupportedOperationException.class);
        eventList.asObservableList().remove(0);
    }

```
###### \java\seedu\address\testutil\EditEventDescriptorBuilder.java
``` java
    /**
     * Sets the {@code Period} of the {@code EditEventDescriptor} that we are building.
     */
    public EditEventDescriptorBuilder withPeriod(String period) {
        try {
            ParserUtil.parsePeriod(Optional.of(period)).ifPresent(descriptor::setPeriod);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Period is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\testutil\EventBuilder.java
``` java
    /**
     * Sets the {@code Period} of the {@code Event} that we are building.
     */
    public EventBuilder withPeriod(String period) {
        try {
            this.event.setPeriod(new Period(period));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Period is expected to be unique.");
        }
        return this;
    }
```
###### \java\seedu\address\ui\EventCardTest.java
``` java

public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {

        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 2);
        uiPartRule.setUiPart(eventCard);
        assertCardDisplay(eventCard, event, 2);

        // changes made to Event reflects on card
        guiRobot.interact(() -> {
            event.setTitle(BIRTHDAY.getTitle());
            event.setTimeslot(BIRTHDAY.getTimeslot());
            event.setDescription(BIRTHDAY.getDescription());
        });
        assertCardDisplay(eventCard, event, 2);
    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 0);

        // same event, same index -> returns true
        EventCard copy = new EventCard(event, 0);
        assertTrue(eventCard.equals(copy));

        // same object -> returns true
        assertTrue(eventCard.equals(eventCard));

        // null -> returns false
        assertFalse(eventCard.equals(null));

        // different types -> returns false
        assertFalse(eventCard.equals(0));

        // different event, same index -> returns false
        Event differentEvent = new EventBuilder().withTitle("differentTitle")
                .build();
        assertFalse(eventCard.equals(new EventCard(differentEvent, 0)));

        // same event, different index -> returns false
        assertFalse(eventCard.equals(new EventCard(event, 1)));
    }

    /**
     * Asserts that {@code eventCard} displays the details of {@code
     * expectedEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EventCard eventCard, ReadOnlyEvent expectedEvent, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(eventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify event details are displayed correctly
        assertCardDisplaysEvent(expectedEvent, eventCardHandle);
    }
}
```
