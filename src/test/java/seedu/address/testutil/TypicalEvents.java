package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_EVENT2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_EVENT1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_VENUE_EVENT2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.property.PropertyManager;



//@@author junyango

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent EVENT1 = new EventBuilder().withName("HHN 6001")
            .withDateTime("22022015 08:30")
            .withAddress("123, Sentosa, #08-111").withReminder().build();
    public static final ReadOnlyEvent EVENT2 = new EventBuilder().withName("ZoukOut 6001")
            .withDateTime("25122017 10:30")
            .withAddress("123, Clarke Quay #01-111").withReminder().build();

    // Manually added
    public static final ReadOnlyEvent EVENTM1 = new EventBuilder().withName("Volleyball Tour 17")
            .withDateTime("25122017 08:30")
            .withAddress("OCBC ARENA Hall 3, #01-111").withReminder().build();
    public static final ReadOnlyEvent EVENTM2 = new EventBuilder().withName("Meeting with Jason")
            .withDateTime("25112016 02:30")
            .withAddress("123, Sheraton Towers , #06-111").withReminder().build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyEvent EV1 = new EventBuilder().withName(VALID_NAME_EVENT1)
            .withDateTime(VALID_DATE_EVENT1).withAddress(VALID_VENUE_EVENT1).build();
    public static final ReadOnlyEvent EV2 = new EventBuilder().withName(VALID_NAME_EVENT2)
            .withDateTime(VALID_DATE_EVENT2).withAddress(VALID_VENUE_EVENT2).build();


    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyEvent events : getTypicalEvents()) {
            try {
                ab.addEvent(events);
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }
    static {
        PropertyManager.initializePropertyManager();
    }

    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT1, EVENT2));
    }
}
