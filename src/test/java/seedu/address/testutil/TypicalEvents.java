package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;
import seedu.address.model.person.ReadOnlyPerson;


/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent EVENT1 = new EventBuilder().withName("HHN 6001")
            .withDateTime("22022015 08:30")
            .withVenue("123, Sentosa, #08-111").build();
    public static final ReadOnlyEvent EVENT2 = new EventBuilder().withName("ZoukOut 6001")
            .withDateTime("25122017 10:30")
            .withVenue("123, Clarke Quay #01-111").build();

    // Manually added
    public static final ReadOnlyEvent EVENTM1 = new EventBuilder().withName("Volleyball Tour 17")
            .withDateTime("25122017 08:30")
            .withVenue("OCBC ARENA Hall 3, #01-111").build();
    public static final ReadOnlyEvent EVENTM2 = new EventBuilder().withName("Meeting with Jason")
            .withDateTime("25112016 02:30")
            .withVenue("123, Sheraton Towers , #06-111").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final ReadOnlyPerson AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final ReadOnlyPerson BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();


    private TypicalEvents() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (ReadOnlyEvent events : getTypicalEvents()) {
            try {
                ab.addEvent(events);
            } catch (DuplicateEventException e) {
                assert false : "not possible";
            }
        }
        return ab;
    }

    public static List<ReadOnlyEvent> getTypicalEvents() {
        return new ArrayList<>(Arrays.asList(EVENT1, EVENT2));
    }
}
