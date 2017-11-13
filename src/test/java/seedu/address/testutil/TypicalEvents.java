package seedu.address.testutil;

//@@author chernghann
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
