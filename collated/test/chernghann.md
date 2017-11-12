# chernghann
###### /java/guitests/guihandles/EventCardHandle.java
``` java
import javafx.scene.Node;
import javafx.scene.control.Label;

/**
 * Provides a handle to a person card in the person list panel.
 */
public class EventCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String NAME_FIELD_ID = "#name";
    private static final String DATE_FIELD_ID = "#date";
    private static final String ADDRESS_FIELD_ID = "#address";

    private Label nameLabel;
    private Label idLabel;
    private Label dateLabel;
    private Label addressLabel;

    public EventCardHandle(Node cardNode) {
        super(cardNode);

        this.idLabel = getChildNode(ID_FIELD_ID);
        this.nameLabel = getChildNode(NAME_FIELD_ID);
        this.dateLabel = getChildNode(DATE_FIELD_ID);
        this.addressLabel = getChildNode(ADDRESS_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getEventName() {
        return nameLabel.getText();
    }

    public String getEventDate() {
        return dateLabel.getText();
    }

    public String getEventAddress() {
        return addressLabel.getText();
    }

}
```
###### /java/guitests/guihandles/EventListPanelHandle.java
``` java

import java.util.List;
import java.util.Optional;

import javafx.scene.control.ListView;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.ui.EventCard;
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
    public void navigateToCard(ReadOnlyEvent events) {
        List<EventCard> cards = getRootNode().getItems();
        Optional<EventCard> matchingCard = cards.stream().filter(card -> card.event.equals(events)).findFirst();

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
     * Returns the event card handle of an event associated with the {@code index} in the list.
     */
    public EventCardHandle getEventCardHandle(int index) {
        return getEventCardHandle(getRootNode().getItems().get(index).event);
    }

    /**
     * Returns the {@code EventCardHandle} of the specified {@code event} in the list.
     */
    public EventCardHandle getEventCardHandle(ReadOnlyEvent events) {
        Optional<EventCardHandle> handle = getRootNode().getItems().stream()
                .filter(card -> card.event.equals(events))
                .map(card -> new EventCardHandle(card.getRoot()))
                .findFirst();
        return handle.orElseThrow(() -> new IllegalArgumentException("Event does not exist."));
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
    public void rememberSelectedEventCard() {
        List<EventCard> selectedItems = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedItems.size() == 0) {
            lastRememberedSelectedEventCard = Optional.empty();
        } else {
            lastRememberedSelectedEventCard = Optional.of(selectedItems.get(0));
        }
    }

    /**
     * Returns true if the selected {@code EventCard} is different from the value remembered by the most recent
     * {@code rememberSelectedEventCard()} call.
     */
    public boolean isSelectedEventCardChanged() {
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
###### /java/seedu/address/logic/commands/AddEventCommandTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.Event;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEventCommand(null);
    }

    @Test
    public void equals() {
        Event zouk = new EventBuilder().withName("ZoukOut").build();
        AddEventCommand addZoukCommand = new AddEventCommand(zouk);

        // same object -> returns true
        assertTrue(addZoukCommand.equals(addZoukCommand));

        // different types -> returns false
        assertFalse(addZoukCommand.equals(1));

        // null -> returns false
        assertFalse(addZoukCommand.equals(null));
    }
}
```
###### /java/seedu/address/logic/parser/AddEventParserTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_B_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_B_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_B_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_B_ADDRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_B_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_B_NAME;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Address;

public class AddEventParserTest {
    private AddEventCommandParser parser = new AddEventCommandParser();

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE);

        // missing name prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_EVENT_B_NAME
                + EVENT_DATE_B_DESC + EVENT_ADDRESS_B_DESC, expectedMessage);

        // missing date prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_B_DESC
                + VALID_EVENT_B_DATE + EVENT_ADDRESS_B_DESC, expectedMessage);

        // missing address prefix
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_B_DESC
                + EVENT_DATE_B_DESC + VALID_EVENT_B_ADDRESS, expectedMessage);

        // all prefixes missing
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + VALID_EVENT_B_NAME
                + VALID_EVENT_B_DATE + VALID_EVENT_B_ADDRESS, expectedMessage);
    }

    @Test
    public void parse_invalidValue_failure() {
        // invalid name
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + INVALID_NAME_DESC
                + EVENT_DATE_B_DESC + EVENT_ADDRESS_B_DESC, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_B_DESC
                + INVALID_DATE_DESC + EVENT_ADDRESS_B_DESC, Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_B_DESC
                + EVENT_DATE_B_DESC + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS);
    }
}
```
###### /java/seedu/address/model/AddressBookTest.java
``` java
    @Test
    public void getEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        addressBook.getEventList().remove(0);
    }
```
###### /java/seedu/address/model/event/DateTest.java
``` java
public class DateTest {
    @Test
    public void isValidDate() {
        // invalid date
        assertFalse(Date.isValidDate("")); // empty string
        assertFalse(Date.isValidDate(" ")); // spaces only
        assertFalse(Date.isValidDate("date")); // non-numeric
        assertFalse(Date.isValidDate("9011p041")); // alphabets within digits
        assertFalse(Date.isValidDate("2131/12/33")); // not dd/mm/yyyy format

        // valid date
        assertTrue(Date.isValidDate("12/11/2019"));
        assertTrue(Date.isValidDate("11/10/2000"));
        assertTrue(Date.isValidDate("12/04/2018"));
    }
}
```
###### /java/seedu/address/model/event/EventNameTest.java
``` java
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EventNameTest {

    @Test
    public void isValidName() {
        // invalid name
        assertFalse(EventName.isValidName("")); // empty string
        assertFalse(EventName.isValidName(" ")); // spaces only
        assertFalse(EventName.isValidName("peter*")); // contains not allowed non-alphanumeric characters

        // valid name
        assertTrue(EventName.isValidName("peter jack")); // alphabets only
        assertTrue(EventName.isValidName("12345")); // numbers only
        assertTrue(EventName.isValidName("peter the 2nd")); // alphanumeric characters
        assertTrue(EventName.isValidName("Capital Tan")); // with capital letters
        assertTrue(EventName.isValidName("David Roger Jackson Ray Jr 2nd")); // long names
        assertTrue(EventName.isValidName("EE2020's Finals")); // special characters
    }
}
```
###### /java/seedu/address/model/UniqueEventListTest.java
``` java
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.UniqueEventList;

public class UniqueEventListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        UniqueEventList uniqueEventList = new UniqueEventList();
        thrown.expect(UnsupportedOperationException.class);
        uniqueEventList.asObservableList().remove(0);
    }
}
```
###### /java/seedu/address/testutil/AddEventUtil.java
``` java
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * A utility class for Event.
 */
public class AddEventUtil {

    /**
     * Returns an add event command string for adding the {@code event}.
     */
    public static String getAddEventCommand(ReadOnlyEvent event) {
        return AddEventCommand.COMMAND_WORD + " " + getEventDetails(event);
    }

    /**
     * Returns the part of command string for the given {@code event}'s details.
     */
    public static String getEventDetails(ReadOnlyEvent event) {
        StringBuilder sb = new StringBuilder();
        sb.append(PREFIX_NAME + event.getName().fullName + " ");
        sb.append(PREFIX_DATE + event.getDate().value + " ");
        sb.append(PREFIX_ADDRESS + event.getAddress().value + " ");
        return sb.toString();
    }
}
```
###### /java/seedu/address/testutil/EventBuilder.java
``` java
import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.event.Date;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventName;
import seedu.address.model.person.Address;

/**
 * A utility class to help with building Event objects.
 */
public class EventBuilder {
    public static final String DEFAULT_NAME = "ZoukOut";
    public static final String DEFAULT_ADDRESS = "Sentosa, Siloso Beach";
    public static final String DEFAULT_DATE = "12/12/2018";

    private Event event;

    public EventBuilder() {
        try {
            EventName defaultName = new EventName(DEFAULT_NAME);
            Date defaultDate = new Date(DEFAULT_DATE);
            Address defaultAddress = new Address(DEFAULT_ADDRESS);

            this.event = new Event(defaultName, defaultDate, defaultAddress);

        } catch (IllegalValueException ive) {
            throw new AssertionError("Default event's values are invalid.");
        }
    }

    /**
     * Sets the {@code Name} of the {@code Event} that we are building.
     */
    public EventBuilder withName(String name) {
        try {
            this.event.setName(new EventName(name));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("name is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Address} of the {@code Event} that we are building.
     */
    public EventBuilder withAddress(String address) {
        try {
            this.event.setAddress(new Address(address));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("address is expected to be unique.");
        }
        return this;
    }

    /**
     * Sets the {@code Date} of the {@code Event} that we are building.
     */
    public EventBuilder withDate(String date) {
        try {
            this.event.setDate(new Date(date));
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("date is expected to be unique.");
        }
        return this;
    }

    public Event build() {
        return this.event;
    }

}
```
###### /java/seedu/address/testutil/TypicalEvents.java
``` java
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_A_ADDRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_A_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_A_NAME;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;


/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent ZOUKOUT = new EventBuilder().withName("ZoukOut").withDate("12/12/2017")
            .withAddress("Sentosa, Siloso Beach").build();
    public static final ReadOnlyEvent HALLOWEEN = new EventBuilder().withName("Halloween Horror Night")
            .withDate("31/10/2017").withAddress("Universal Studios Singapore").build();
    public static final ReadOnlyEvent FINALS = new EventBuilder().withName("Finals").withDate("4/12/2017")
            .withAddress("MPSH 2A").build();
    public static final ReadOnlyEvent CONCERT = new EventBuilder().withName("Ed Sheeren Concert")
            .withDate("01/01/2018").withAddress("National Stadium").build();
    public static final ReadOnlyEvent STARBUCKS = new EventBuilder().withName("Starbucks").withDate("4/10/2017")
            .withAddress("MD7, NUS").build();
    public static final ReadOnlyEvent NATIONALDAY = new EventBuilder().withName("National Day").withDate("8/09/2017")
            .withAddress("Padang").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final ReadOnlyEvent EVENT_A = new EventBuilder().withName(VALID_EVENT_A_NAME)
            .withDate(VALID_EVENT_A_DATE).withAddress(VALID_EVENT_A_ADDRESS).build();

    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyEvent event : getTypicalEvents()) {
            try {
                ab.addEvent(event);
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(ZOUKOUT, HALLOWEEN, FINALS, CONCERT, STARBUCKS, NATIONALDAY));
    }
}
```
###### /java/seedu/address/ui/EventCardTest.java
``` java
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEvent;

import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import seedu.address.model.event.Event;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.testutil.EventBuilder;

public class EventCardTest extends GuiUnitTest {

    @Test
    public void display() {
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 1);
        uiPartRule.setUiPart(eventCard);
        assertCardDisplay(eventCard, event, 1);
    }

    @Test
    public void equals() {
        Event event = new EventBuilder().build();
        EventCard eventCard = new EventCard(event, 0);

        // same person, same index -> returns true
        EventCard copy = new EventCard(event, 0);
        assertTrue(eventCard.equals(copy));

        // same object -> returns true
        assertTrue(eventCard.equals(eventCard));

        // null -> returns false
        assertFalse(eventCard.equals(null));

        // different types -> returns false
        assertFalse(eventCard.equals(0));
    }

    /**
     * Asserts that {@code eventCard} displays the details of {@code expectedEvent} correctly and matches
     * {@code expectedId}.
     */
    private void assertCardDisplay(EventCard eventCard, ReadOnlyEvent expectedEvent, int expectedId) {
        guiRobot.pauseForHuman();

        EventCardHandle eventCardHandle = new EventCardHandle(eventCard.getRoot());

        // verify id is displayed correctly
        assertEquals(Integer.toString(expectedId) + ". ", eventCardHandle.getId());

        // verify person details are displayed correctly
        assertCardDisplaysEvent(expectedEvent, eventCardHandle);
    }
}
```
###### /java/seedu/address/ui/EventListPanelTest.java
``` java

import static junit.framework.TestCase.assertEquals;
import static seedu.address.testutil.TypicalEvents.getTypicalEvents;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEvent;

import org.junit.Before;
import org.junit.Test;

import guitests.guihandles.EventCardHandle;
import guitests.guihandles.EventListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.model.event.ReadOnlyEvent;

public class EventListPanelTest extends GuiUnitTest {
    private static final ObservableList<ReadOnlyEvent> TYPICAL_EVENTS =
            FXCollections.observableList(getTypicalEvents());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

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
            ReadOnlyEvent expectedEvent = TYPICAL_EVENTS.get(i);
            EventCardHandle actualCard = eventListPanelHandle.getEventCardHandle(i);

            assertCardDisplaysEvent(expectedEvent, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }
}
```
###### /java/seedu/address/ui/testutil/GuiTestAssert.java
``` java
    /**
     * Asserts that {@code actualCard} displays the details of {@code expectedEvent}.
     */
    public static void assertCardDisplaysEvent(ReadOnlyEvent expectedEvent, EventCardHandle actualCard) {
        assertEquals(expectedEvent.getName().fullName, actualCard.getEventName());
        assertEquals(expectedEvent.getDate().value, actualCard.getEventDate());
        assertEquals(expectedEvent.getAddress().value, actualCard.getEventAddress());
    }

    /**
     * Asserts that {@code actualCard} displays the same values as {@code expectedCard}.
     */
    public static void assertCardEventsEquals(EventCardHandle expectedCard, EventCardHandle actualCard) {
        assertEquals(expectedCard.getEventName(), actualCard.getEventName());
        assertEquals(expectedCard.getEventDate(), actualCard.getEventDate());
        assertEquals(expectedCard.getEventAddress(), actualCard.getEventAddress());
    }
```
###### /java/systemtests/AddEventCommandSystemTest.java
``` java
import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_A_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_ADDRESS_B_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_A_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_DATE_B_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_A_DESC;
import static seedu.address.logic.commands.CommandTestUtil.EVENT_NAME_B_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_ADDRESS_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_DATE_DESC;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_NAME_DESC;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_A_ADDRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_A_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_A_NAME;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_B_ADDRESS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_B_DATE;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EVENT_B_NAME;
import static seedu.address.testutil.TypicalEvents.EVENT_A;

import org.junit.Test;

import seedu.address.logic.commands.AddEventCommand;
import seedu.address.model.Model;
import seedu.address.model.event.Date;
import seedu.address.model.event.EventName;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.Address;
import seedu.address.testutil.EventBuilder;

public class AddEventCommandSystemTest extends AddressBookSystemTest {

    @Test
    public void add() throws Exception {
        Model model = getModel();
        executeCommand("events");
        ReadOnlyEvent toAdd = EVENT_A;
        String command = " " + AddEventCommand.COMMAND_WORD + " " + EVENT_NAME_A_DESC + " "
                + EVENT_DATE_A_DESC + " " + EVENT_ADDRESS_A_DESC + " ";

        assertCommandSuccess(command, toAdd);

        /* Case: add an event with all fields same as another event in the address book except name -> added */
        toAdd = new EventBuilder().withName(VALID_EVENT_B_NAME).withDate(VALID_EVENT_A_DATE)
                .withAddress(VALID_EVENT_A_ADDRESS).build();

        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_B_DESC + EVENT_DATE_A_DESC + EVENT_ADDRESS_A_DESC;

        assertCommandSuccess(command, toAdd);

        /* Case: add an event with all fields same as another event in the address book except date -> added */
        toAdd = new EventBuilder().withName(VALID_EVENT_A_NAME).withDate(VALID_EVENT_B_DATE)
                .withAddress(VALID_EVENT_A_ADDRESS).build();

        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + EVENT_DATE_B_DESC + EVENT_ADDRESS_A_DESC;

        assertCommandSuccess(command, toAdd);

        /* Case: add an event with all fields same as another event in the address book except address -> added */
        toAdd = new EventBuilder().withName(VALID_EVENT_A_NAME).withDate(VALID_EVENT_A_DATE)
                .withAddress(VALID_EVENT_B_ADDRESS).build();

        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + EVENT_DATE_A_DESC + EVENT_ADDRESS_B_DESC;

        assertCommandSuccess(command, toAdd);

        /* Case: missing name -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_DATE_A_DESC + EVENT_ADDRESS_A_DESC;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + EVENT_ADDRESS_A_DESC;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: missing date -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + EVENT_DATE_A_DESC;
        assertCommandFailure(command, String.format(MESSAGE_INVALID_COMMAND_FORMAT, AddEventCommand.MESSAGE_USAGE));

        /* Case: invalid name -> rejected */
        command = AddEventCommand.COMMAND_WORD + INVALID_NAME_DESC + EVENT_DATE_A_DESC + EVENT_ADDRESS_A_DESC;
        assertCommandFailure(command, EventName.MESSAGE_EVENT_NAME_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + INVALID_DATE_DESC + EVENT_ADDRESS_A_DESC;
        assertCommandFailure(command, Date.MESSAGE_DATE_CONSTRAINTS);

        /* Case: invalid date -> rejected */
        command = AddEventCommand.COMMAND_WORD + EVENT_NAME_A_DESC + EVENT_DATE_A_DESC + INVALID_ADDRESS_DESC;
        assertCommandFailure(command, Address.MESSAGE_ADDRESS_CONSTRAINTS);
    }

    /**
     * Performs the same verification as {@code assertCommandSuccess(ReadOnlyPerson)} except that the result
     * display box displays {@code expectedResultMessage} and the model related components equal to
     * {@code expectedModel}. Executes {@code command} instead.
     */
    private void assertCommandSuccess(String command, ReadOnlyEvent toAdd) {
        Model expectedModel = getModel();
        try {
            expectedModel.addEvent(toAdd);
        } catch (DuplicateEventException dee) {
            throw new IllegalArgumentException("toAdd already exists in the model.");
        }
        executeCommand(command);
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
        executeCommand(command);
        assertEventDisplaysExpected(command, expectedResultMessage);
        assertCommandBoxShowsErrorStyle();
        assertStatusBarUnchanged();
    }
}
```
