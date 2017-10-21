package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EventList;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;

public class TypicalEvents {
    public static final ReadOnlyEvent FIRST = new EventBuilder().withName("First meeting")
            .withDescription("Discuss coding").withTime("10/10/2017").build();

    public static final ReadOnlyEvent SECOND = new EventBuilder().withName("Second meeting")
            .withDescription("Discuss PPT").withTime("13/10/2017").build();

    public static final ReadOnlyEvent THIRD = new EventBuilder().withName("Third meeting")
            .withDescription("Discuss presentation").withTime("17/10/2017").build();

    public static final ReadOnlyEvent FORTH = new EventBuilder().withName("Forth meeting")
            .withDescription("Discuss demo").withTime("03/11/2017").build();

    /**
     * Returns an {@code AddressBook} with all the typical events.
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

    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(FIRST,SECOND,THIRD,FORTH));
    }

}
