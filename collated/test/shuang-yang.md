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
    public static final String VALID_PERIOD_MIDTERM = " 180";
    public static final String VALID_PERIOD_SOCCER = " 7";
    public static final String INVALID_PERIOD_SOCCER = " -7";
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
###### \java\seedu\address\logic\commands\event\RepeatCommandTest.java
``` java
package seedu.address.logic.commands.event;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_MIDTERM;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_SOCCER;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandFailure;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.logic.commands.CommandTestUtil.showFirstEventOnly;
import static seedu.address.testutil.TypicalEvents.getTypicalEventAddressBook;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_EVENT;

import java.util.Optional;

import org.junit.Test;

import seedu.address.commons.core.Messages;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.UndoRedoStack;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.event.Event;
import seedu.address.model.event.Period;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.testutil.EventBuilder;

public class RepeatCommandTest {
    private Model model = new ModelManager(getTypicalEventAddressBook(), new UserPrefs());

    @Test
    public void execute_noFieldSpecifiedUnfilteredList_success() {
        RepeatCommand editCommand = prepareCommand(INDEX_FIRST_EVENT, Optional.empty());
        ReadOnlyEvent editedEvent = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());

        String expectedMessage = String.format(RepeatCommand.MESSAGE_REPEAT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_filteredList_success() throws Exception {
        showFirstEventOnly(model);

        ReadOnlyEvent eventInFilteredList = model.getFilteredEventList().get(INDEX_FIRST_EVENT.getZeroBased());
        Event editedEvent = new EventBuilder(eventInFilteredList).withPeriod(VALID_PERIOD_MIDTERM).build();
        Optional<Period> period = Period.generatePeriod(VALID_PERIOD_MIDTERM);
        RepeatCommand editCommand = prepareCommand(INDEX_FIRST_EVENT, period);

        String expectedMessage = String.format(RepeatCommand.MESSAGE_REPEAT_EVENT_SUCCESS, editedEvent);

        Model expectedModel = new ModelManager(new AddressBook(model.getAddressBook()), new UserPrefs());
        expectedModel.updateEvent(model.getFilteredEventList().get(0), editedEvent);

        assertCommandSuccess(editCommand, model, expectedMessage, expectedModel);
    }

    @Test
    public void execute_invalidEventIndexUnfilteredList_failure() {
        Index outOfBoundIndex = Index.fromOneBased(model.getFilteredEventList().size() + 1);
        Optional<Period> period = Period.generatePeriod(VALID_PERIOD_MIDTERM);
        RepeatCommand editCommand = prepareCommand(outOfBoundIndex, period);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    /**
     * Edit filtered list where index is larger than size of filtered list,
     * but smaller than size of address book
     */
    @Test
    public void execute_invalidEventIndexFilteredList_failure() {
        showFirstEventOnly(model);
        Index outOfBoundIndex = INDEX_SECOND_EVENT;
        // ensures that outOfBoundIndex is still in bounds of address book list
        assertTrue(outOfBoundIndex.getZeroBased() < model.getAddressBook().getEventList().size());

        Optional<Period> period = Period.generatePeriod(VALID_PERIOD_MIDTERM);
        RepeatCommand editCommand = prepareCommand(outOfBoundIndex, period);

        assertCommandFailure(editCommand, model, Messages.MESSAGE_INVALID_EVENT_DISPLAYED_INDEX);
    }

    @Test
    public void equals() {
        Optional<Period> period = Period.generatePeriod(VALID_PERIOD_MIDTERM);
        final RepeatCommand standardCommand = new RepeatCommand(INDEX_FIRST_EVENT, period);

        // same values -> returns true
        Optional<Period> copyPeriod = Period.generatePeriod(VALID_PERIOD_MIDTERM);
        RepeatCommand commandWithSameValues = new RepeatCommand(INDEX_FIRST_EVENT, copyPeriod);
        assertTrue(standardCommand.equals(commandWithSameValues));

        // same object -> returns true
        assertTrue(standardCommand.equals(standardCommand));

        // null -> returns false
        assertFalse(standardCommand.equals(null));

        // different types -> returns false
        assertFalse(standardCommand.equals(new ClearCommand()));

        // different index -> returns false
        assertFalse(standardCommand.equals(new RepeatCommand(INDEX_SECOND_EVENT, period)));

        // different descriptor -> returns false
        Optional<Period> newPeriod = Period.generatePeriod(VALID_PERIOD_SOCCER);
        assertFalse(standardCommand.equals(new RepeatCommand(INDEX_FIRST_EVENT, newPeriod)));
    }

    /**
     * Returns an {@code RepeatCommand} with parameters {@code index} and {@code descriptor}
     */
    private RepeatCommand prepareCommand(Index index, Optional<Period> period) {
        RepeatCommand editCommand = new RepeatCommand(index, period);
        editCommand.setData(model, new CommandHistory(), new UndoRedoStack());
        return editCommand;
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
###### \java\seedu\address\logic\parser\event\RepeatCommandParserTest.java
``` java
package seedu.address.logic.parser.event;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_PERIOD_SOCCER;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PERIOD_MIDTERM;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.model.event.Period.MESSAGE_PERIOD_CONSTRAINTS;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_EVENT;

import java.util.Optional;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.commands.event.RepeatCommand;
import seedu.address.model.event.Period;

public class RepeatCommandParserTest {
    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RepeatCommand.MESSAGE_USAGE);

    private RepeatCommandParser parser = new RepeatCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no field specified
        assertParseFailure(parser, "1", MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5" + VALID_PERIOD_MIDTERM, MESSAGE_INVALID_INDEX);

        // zero index
        assertParseFailure(parser, VALID_PERIOD_MIDTERM, MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_PERIOD_CONSTRAINTS);
    }

    @Test
    public void parse_invalidValue_failure() {
        assertParseFailure(parser, "1" + INVALID_PERIOD_SOCCER, MESSAGE_PERIOD_CONSTRAINTS); // invalid period
    }

    @Test
    public void parse_fieldSpecified_success() {
        Index targetIndex = INDEX_FIRST_EVENT;
        String userInput = targetIndex.getOneBased() + VALID_PERIOD_MIDTERM;
        Optional<Period> period = Period.generatePeriod(VALID_PERIOD_MIDTERM);

        RepeatCommand expectedCommand = new RepeatCommand(targetIndex, period);

        assertParseSuccess(parser, userInput, expectedCommand);
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

    @Test
    public void addWithoutTimeClash_success() {
        EventList eventList = new EventList();
        ReadOnlyEvent toAdd = new EventBuilder().build();
        try {
            eventList.add(toAdd);
        } catch (EventTimeClashException e) {
            fail("Exception should not be thrown");
        }
        ReadOnlyEvent added = eventList.asObservableList().get(0);

        assertEquals(toAdd, added);
    }

    @Test
    public void addWithTimeClash_failure() {
        EventList eventList = new EventList();
        ReadOnlyEvent toAdd1 = new EventBuilder().withTimeslot(VALID_TIMESLOT_SOCCER).build();
        ReadOnlyEvent toAdd2 = new EventBuilder().withTimeslot(VALID_TIMESLOT_SOCCER).build();
        ReadOnlyEvent toAdd3 = new EventBuilder().withTimeslot(VALID_TIMESLOT_SOCCER).build();

        try {
            eventList.add(toAdd1);
            eventList.add(toAdd2);
            fail("The expected exception was not thrown.");
        } catch (Exception e) {
            assertTrue(e instanceof EventTimeClashException);
        }
    }

```
###### \java\seedu\address\model\ObservableTreeMapTest.java
``` java
public class ObservableTreeMapTest {
    private static final int VALID_KEY = 1;
    private static final int VALID_KEY_TWO = 2;
    private static final int VALID_VALUE = 2;
    private static final int VALID_VALUE_TWO = 4;
    private TreeMap<Integer, Integer> internalMap = new TreeMap<Integer, Integer>();


    @Test
    public void addListener_addElement() {
        ObservableTreeMap<Integer, Integer> testMap1 = new ObservableTreeMap<>(internalMap);
        ObservableTreeMap<Integer, Integer> testMap2 = new ObservableTreeMap<>(internalMap);
        testMap1.addListener(new MapChangeListener<Integer, Integer>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Integer> change) {
                boolean added = change.wasAdded();
                if (added != change.wasRemoved()) {
                    if (added) {
                        testMap2.put(change.getKey(), change.getValueAdded());
                    }
                }
            }
        });

        testMap1.put(VALID_KEY, VALID_VALUE);
        assertTrue(testMap2.size() == testMap1.size());
        assertTrue(testMap2.containsKey(VALID_KEY));
        assertTrue(testMap2.containsValue(VALID_VALUE));
        assertTrue(testMap2.get(VALID_KEY) == VALID_VALUE);
        assertTrue(testMap2.get(VALID_KEY) == VALID_VALUE);

        assertTrue(testMap2.keySet().size() == testMap1.keySet().size());
        assertTrue(testMap2.keySet().contains(VALID_KEY));
        assertTrue(testMap2.values().size() == testMap1.values().size());
        assertTrue(testMap2.keySet().toArray().length == testMap2.keySet().toArray().length);
        assertTrue(testMap2.values().contains(VALID_VALUE));
        assertTrue(testMap2.entrySet().size() == testMap1.entrySet().size());
        assertTrue(testMap2.values().toArray().length == testMap2.values().toArray().length);
        assertTrue(testMap2.entrySet().equals(testMap1.entrySet()));

        assertTrue(testMap1.equals(testMap2));
    }

    @Test
    public void addListener_removeElement() {
        internalMap.put(VALID_KEY, VALID_VALUE);
        ObservableTreeMap<Integer, Integer> testMap1 = new ObservableTreeMap<>(internalMap);
        ObservableTreeMap<Integer, Integer> testMap2 = new ObservableTreeMap<>(internalMap);
        testMap1.addListener(new MapChangeListener<Integer, Integer>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Integer> change) {
                boolean removed = change.wasRemoved();
                if (removed != change.wasAdded()) {
                    if (removed) {
                        testMap2.remove(change.getValueRemoved());
                    }
                }
            }
        });

        testMap1.remove(VALID_KEY, VALID_VALUE);
        assertTrue(testMap2.isEmpty());

        assertTrue(testMap2.keySet().size() == testMap1.keySet().size());
        assertTrue(!testMap2.keySet().contains(VALID_KEY));
        assertTrue(testMap2.values().size() == testMap1.values().size());
        assertTrue(!testMap2.values().contains(VALID_VALUE));
        assertTrue(testMap2.entrySet().size() == testMap1.entrySet().size());
        assertTrue(testMap2.entrySet().equals(testMap1.entrySet()));

        assertTrue(testMap1.equals(testMap2));
    }

    @Test
    public void addListener_addMultipleElement() {
        TreeMap<Integer, Integer> toAdd = new TreeMap<>();
        toAdd.put(VALID_KEY, VALID_VALUE);
        toAdd.put(VALID_KEY_TWO, VALID_VALUE_TWO);
        ObservableTreeMap<Integer, Integer> testMap1 = new ObservableTreeMap<>(internalMap);
        ObservableTreeMap<Integer, Integer> testMap2 = new ObservableTreeMap<>(internalMap);
        testMap1.addListener(new MapChangeListener<Integer, Integer>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Integer> change) {
                boolean added = change.wasAdded();
                if (added != change.wasRemoved()) {
                    if (added) {
                        testMap2.put(change.getKey(), change.getValueAdded());
                    }
                }
            }
        });

        testMap1.putAll(toAdd);

        assertTrue(testMap2.size() == testMap1.size());
        assertTrue(testMap1.containsKey(VALID_KEY));
        assertTrue(testMap1.containsKey(VALID_KEY_TWO));
        assertTrue(testMap1.containsValue(VALID_VALUE));
        assertTrue(testMap1.containsValue(VALID_VALUE_TWO));
        assertTrue(testMap2.get(VALID_KEY) == testMap1.get(VALID_KEY));
        assertTrue(testMap2.get(VALID_KEY_TWO) == testMap1.get(VALID_KEY_TWO));

        assertTrue(testMap2.keySet().size() == testMap1.keySet().size());
        assertTrue(testMap2.keySet().containsAll(new ArrayList<>(Arrays.asList(VALID_KEY, VALID_KEY_TWO))));
        assertTrue(testMap2.values().size() == testMap1.values().size());
        assertTrue(testMap2.keySet().toArray().length == testMap2.keySet().toArray().length);
        assertTrue(testMap2.values().containsAll(new ArrayList<>(Arrays.asList(VALID_VALUE, VALID_VALUE_TWO))));
        assertTrue(testMap2.entrySet().size() == testMap1.entrySet().size());
        assertTrue(testMap2.values().toArray().length == testMap2.values().toArray().length);
        assertTrue(testMap2.entrySet().equals(testMap1.entrySet()));

        assertTrue(testMap1.equals(testMap2));
    }

    @Test
    public void addListener_removeMultipleElement() {
        TreeMap<Integer, Integer> toAdd = new TreeMap<>();
        toAdd.put(VALID_KEY, VALID_VALUE);
        toAdd.put(VALID_KEY_TWO, VALID_VALUE_TWO);
        internalMap.putAll(toAdd);
        ObservableTreeMap<Integer, Integer> testMap1 = new ObservableTreeMap<>(internalMap);
        ObservableTreeMap<Integer, Integer> testMap2 = new ObservableTreeMap<>(internalMap);
        testMap1.addListener(new MapChangeListener<Integer, Integer>() {
            @Override
            public void onChanged(Change<? extends Integer, ? extends Integer> change) {
                boolean removed = change.wasRemoved();
                if (removed != change.wasAdded()) {
                    if (removed) {
                        testMap2.remove(change.getValueRemoved());
                    }
                }
            }
        });

        testMap1.remove(VALID_KEY);
        testMap1.remove(VALID_KEY_TWO);

        assertTrue(!testMap1.containsKey(VALID_KEY));
        assertTrue(!testMap1.containsKey(VALID_KEY_TWO));
        assertTrue(!testMap1.containsValue(VALID_VALUE));
        assertTrue(!testMap1.containsValue(VALID_VALUE_TWO));
        assertTrue(testMap2.isEmpty());

        assertTrue(testMap2.keySet().size() == testMap1.keySet().size());
        assertTrue(!testMap2.keySet().contains(VALID_KEY));
        assertTrue(testMap2.values().size() == testMap1.values().size());
        assertTrue(testMap2.keySet().toArray().length == testMap2.keySet().toArray().length);
        assertTrue(!testMap2.values().contains(VALID_VALUE));
        assertTrue(testMap2.entrySet().size() == testMap1.entrySet().size());
        assertTrue(testMap2.values().toArray().length == testMap2.values().toArray().length);
        assertTrue(testMap2.entrySet().equals(testMap1.entrySet()));

        assertTrue(testMap1.equals(testMap2));
    }
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
