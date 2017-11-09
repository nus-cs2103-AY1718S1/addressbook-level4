# chernghann
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
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

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
                + EVENT_DATE_B_DESC + EVENT_ADDRESS_B_DESC, Name.MESSAGE_NAME_CONSTRAINTS);

        // invalid date
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_B_DESC
                + INVALID_DATE_DESC + EVENT_ADDRESS_B_DESC, Date.MESSAGE_DATE_CONSTRAINTS);

        // invalid address
        assertParseFailure(parser, AddEventCommand.COMMAND_WORD + EVENT_NAME_B_DESC
                + EVENT_DATE_B_DESC + INVALID_ADDRESS_DESC, Address.MESSAGE_ADDRESS_CONSTRAINTS);
    }
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
import seedu.address.model.person.Address;
import seedu.address.model.person.Name;

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
            Name defaultName = new Name(DEFAULT_NAME);
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
            this.event.setName(new Name(name));
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
    public static final ReadOnlyEvent FINALS = new EventBuilder().withName("Finals").withDate("04/12/2017")
            .withAddress("MPSH 2A").build();
    public static final ReadOnlyEvent CONCERT = new EventBuilder().withName("Ed Sheeren Concert")
            .withDate("01/01/2018").withAddress("National Stadium").build();
    public static final ReadOnlyEvent STARBUCKS = new EventBuilder().withName("Starbucks").withDate("04/10/2017")
            .withAddress("MD7, NUS").build();
    public static final ReadOnlyEvent NATIONALDAY = new EventBuilder().withName("National Day").withDate("08/09/2017")
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
