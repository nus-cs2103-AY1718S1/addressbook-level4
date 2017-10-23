package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;


/**
 * @@reginleiff A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent BIRTHDAY = new EventBuilder().withTitle("Jack's Birthday")
            .withTiming("1900-2300").withDescription("Celebrating Jack's 21st").build();
    public static final ReadOnlyEvent ANNIVERSARY = new EventBuilder().withTitle("Wedding Anniversary")
            .withTiming("0000-2359").withDescription("2nd Wedding Anniversary").build();
    public static final ReadOnlyEvent EXAM = new EventBuilder().withTitle("CS2103 Final Exam")
            .withTiming("1300-1500").withDescription("We are screwed").build();
    public static final ReadOnlyEvent MOURN = new EventBuilder().withTitle("Bai Ah Gong")
            .withTiming("1900-2300").withDescription("@ CCK Cemetery").build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalEventAddressBook() {
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
        return new ArrayList<>(Arrays.asList(BIRTHDAY, ANNIVERSARY, MOURN, EXAM));
    }
}
