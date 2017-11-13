//@@author erik0704
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.Event;
import seedu.address.model.event.exceptions.DuplicateEventException;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalTaskEvents {
    public static final Event ONE = new TaskEventBuilder().withName("Project Demo")
            .withDates("2016-12-15").withDescription("Read requirements").build();
    public static final Event TWO = new TaskEventBuilder().withName("BFF birthday")
            .withDates("2017-06-11").withDescription("Prepare present").build();
    public static final Event THREE = new TaskEventBuilder().withName("Online quiz")
            .withDates("2017-10-22").build();
    public static final Event FOUR =  new TaskEventBuilder().withName("Dinner with gramp")
            .withDates("2016-01-27").build();

    private TypicalTaskEvents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Event event : getTypicalEvents()) {
            try {
                ab.addEvent(event);
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<Event> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(ONE, TWO, THREE, FOUR));
    }
}
