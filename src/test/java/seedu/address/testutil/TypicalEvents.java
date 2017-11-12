package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EventList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {
    public static final ReadOnlyEvent FIRST = new EventBuilder().withName("First meeting")
            .withDescription("Discuss coding").withTime("10/10/2017").build();

    public static final ReadOnlyEvent SECOND = new EventBuilder().withName("Second meeting")
            .withDescription("Discuss PPT").withTime("13/10/2017").build();

    public static final ReadOnlyEvent THIRD = new EventBuilder().withName("Third meeting")
            .withDescription("Discuss presentation").withTime("17/10/2017").build();

    public static final ReadOnlyEvent FORTH = new EventBuilder().withName("Forth meeting")
            .withDescription("Discuss demo").withTime("03/11/2017").build();

    public static final ReadOnlyEvent FIFTH = new EventBuilder().withName("Fifth meeting")
            .withDescription("Discuss Q & A").withTime("13/11/2017").build();

    public static final ReadOnlyEvent SIXTH = new EventBuilder().withName("Sixth meeting")
            .withDescription("Discuss attire").withTime("05/11/2017").build();

    public static final ReadOnlyEvent SEVENTH = new EventBuilder().withName("Seventh meeting")
            .withDescription("Debug").withTime("04/09/2015").build();

    public static final ReadOnlyEvent EIGHTH = new EventBuilder().withName("Eighth meeting")
            .withDescription("Sleeping").withTime("25/01/2017").build();

    /**
     * Returns an {@code EventList} with all the typical events.
     */
    public static EventList getTypicalEventList() {
        EventList el = new EventList();
        for (ReadOnlyEvent event : getTypicalEvents()) {
            try {
                el.addEvent(event);
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
        return el;
    }

    //@@author HouDenghao
    /**
     * Returns an empty {@code EventList} with all the unsorted events.
     */
    public static EventList getUnsortedEventList() {
        EventList el = new EventList();
        for (ReadOnlyEvent event : getUnsortedEvents()) {
            try {
                el.addEvent(event);
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
        return el;
    }

    /**
     * Returns an empty {@code EventList}.
     */
    public static EventList getEmptyEventList() {
        EventList el = new EventList();
        return el;
    }

    //@@author
    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(FIRST, SECOND, THIRD, FORTH));
    }

    //@@author HouDenghao
    public static List<ReadOnlyEvent> getUnsortedEvents() {
        return new ArrayList<>(Arrays.asList(FORTH, SECOND, FIRST, THIRD));
    }
}
