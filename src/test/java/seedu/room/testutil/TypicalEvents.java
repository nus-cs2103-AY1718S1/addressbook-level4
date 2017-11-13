package seedu.room.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.room.model.EventBook;
import seedu.room.model.event.ReadOnlyEvent;
import seedu.room.model.event.exceptions.DuplicateEventException;

//@@author sushinoya
/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent ORIENTATION = new EventBuilder().withTitle("Orientation")
            .withDescription("Freshmen Incoming").withLocation("Cinnamon College")
            .withDatetime("21/10/2017 2000 to 2200").build();
    public static final ReadOnlyEvent USPOLYMATH = new EventBuilder().withTitle("USPolymath")
            .withDescription("Intellectual Talks").withLocation("Chatterbox")
            .withDatetime("12/12/2017 2000 to 2200").build();
    public static final ReadOnlyEvent IFGTRAINING = new EventBuilder().withTitle("IFG Training")
            .withDescription("Volleyball Training").withLocation("Sports Hall")
            .withDatetime("29/11/2017 1700 to 1800").build();
    public static final ReadOnlyEvent CONCERT = new EventBuilder().withTitle("Livecore Concert")
            .withDescription("Performance").withLocation("Dining Hall")
            .withDatetime("24/09/2017 1800 to 2100").build();

    // Manually added - Event's details found in {@code CommandTestUtil}
    public static final ReadOnlyEvent DANCE = new EventBuilder().withTitle("Livecore Dance")
            .withDescription("Performance").withLocation("Dining Hall")
            .withDatetime("24/09/2017 1800 to 2100").build();

    private TypicalEvents() {} // Prevents instantiation

    /**
     * Returns an {@code EventBook} with all the typical events.
     */
    public static EventBook getTypicalEventBook() {
        EventBook ab = new EventBook();
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
        return new ArrayList<>(Arrays.asList(USPOLYMATH, ORIENTATION, IFGTRAINING, CONCERT));
    }
}
