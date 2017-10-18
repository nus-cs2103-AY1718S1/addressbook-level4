package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.ReadOnlyEvent;
<<<<<<< HEAD
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
=======

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
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
>>>>>>> ef7f27f85e271ddf3ae222babd802d2750758f22

    private TypicalEvents() {
    } // prevents instantiation

    /**
<<<<<<< HEAD
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
=======
     * Returns an {@code AddressBook} with all the typical events.
     */
    public static AddressBook getTypicalEventAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyEvent event : getTypicalEvents()) {
            ab.addEvent(event);
>>>>>>> ef7f27f85e271ddf3ae222babd802d2750758f22
        }
        return ab;
    }

    public static List<ReadOnlyEvent> getTypicalEvents() {
<<<<<<< HEAD
        return new ArrayList<>(Arrays.asList(MEET_JOHN, MEET_BILL));
=======
        return new ArrayList<>(Arrays.asList(BIRTHDAY, ANNIVERSARY, MOURN, EXAM));
>>>>>>> ef7f27f85e271ddf3ae222babd802d2750758f22
    }
}
