package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.ReadOnlyEvent;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent BIRTHDAY = new EventBuilder().withTitle("Jack's Birthday")
            .withTiming("1900-2300").withDescription("Celebrating Jack's 21st").withTags("birthday").build();
    public static final ReadOnlyEvent ANNIVERSARY = new EventBuilder().withTitle("Wedding Anniversary")
            .withTiming("0000-2359").withDescription("2nd Wedding Anniversary").withTags("love").build();
    public static final ReadOnlyEvent EXAM = new EventBuilder().withTitle("CS2103 Final Exam")
            .withTiming("1300-1500").withDescription("We are screwed").withTags("exam").build();
    public static final ReadOnlyEvent MOURN = new EventBuilder().withTitle("Bai Ah Gong")
            .withTiming("1900-2300").withDescription("@ CCK Cemetery").withTags("family").build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalEventAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyEvent event : getTypicalEvents()) {
            ab.addEvent(event);
        }
        return ab;
    }

    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(BIRTHDAY, ANNIVERSARY, MOURN, EXAM));
    }
}
