//@@author reginleiff
package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.EventTimeClashException;

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent BIRTHDAY = new EventBuilder().withTitle("Jack's Birthday")
            .withTimeslot("23/10/2017 1900-2300").withDescription("Celebrating Jack's 21st").withPeriod("365").build();
    public static final ReadOnlyEvent ANNIVERSARY = new EventBuilder().withTitle("Wedding Anniversary")
            .withTimeslot("29/12/2018 0000-2359").withDescription("2nd Wedding Anniversary").withPeriod("365").build();
    public static final ReadOnlyEvent EXAM = new EventBuilder().withTitle("CS2103 Final Exam")
            .withTimeslot("09/12/2017 1300-1500").withDescription("We are screwed").withPeriod("30").build();
    public static final ReadOnlyEvent MOURN = new EventBuilder().withTitle("Bai Ah Gong")
            .withTimeslot("10/12/2017 1900-2300").withDescription("@ CCK Cemetery").withPeriod("100").build();
    public static final ReadOnlyEvent DEADLINE = new EventBuilder().withTitle("Paper Submission")
            .withTimeslot("10/12/2017 2359-2359").withDescription("Submit on IVLE").withPeriod("0").build();

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
            } catch (EventTimeClashException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(BIRTHDAY, ANNIVERSARY, MOURN, EXAM));
    }
}
