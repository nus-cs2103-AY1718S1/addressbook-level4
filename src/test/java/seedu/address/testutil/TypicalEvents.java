package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;

// @@author yangshuang
/**
 * A utility class containing a list of {@code Event} objects to be used in
 * tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent MEET_JOHN = new EventBuilder().withTitle
            ("Meeting with John")
            .withDescription("Meeting at 1 Raffles")
            .withTiming("1030-1130")
            .withTags("InfluencerMeeting").build();

    public static final ReadOnlyEvent MEET_BILL = new EventBuilder().withTitle
            ("Meeting with Bill")
            .withDescription("Meeting at 2 Raffles")
            .withTiming("1030-1130")
            .withTags("InfluencerMeeting").build();

    private TypicalEvents() {
    } // prevents instantiation

    /**
     * Returns an {@code DescriptionBook} with all the typical persons.
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
        return new ArrayList<>(Arrays.asList(MEET_JOHN, MEET_BILL));
    }
}
