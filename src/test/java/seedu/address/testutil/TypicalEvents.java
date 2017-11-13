package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.EventBook;
import seedu.address.model.event.ReadOnlyEvent;
import seedu.address.model.event.exceptions.DuplicateEventException;

//@@author kaiyu92

/**
 * A utility class containing a list of {@code Event} objects to be used in tests.
 */
public class TypicalEvents {

    public static final ReadOnlyEvent SPECTRA = new EventBuilder().withTitle("Spectra")
            .withDescription("Light and water show").withLocation("Marina Bay Sands")
            .withDatetime("01-09-2017 1900").build();
    public static final ReadOnlyEvent DEEPAVALI = new EventBuilder().withTitle("Deepavali")
            .withDescription("Deepavali Celebrations 2017").withLocation("Little India")
            .withDatetime("12-11-2017 1900").build();
    public static final ReadOnlyEvent HENNA = new EventBuilder().withTitle("Henna")
            .withDescription("Henna Workshop").withLocation("Orchard Gateway")
            .withDatetime("18-10-2017 1500").build();
    public static final ReadOnlyEvent WINE = new EventBuilder().withTitle("Wine Fest")
            .withDescription("Singapore Wine Fiesta 2017").withLocation("Clifford Square")
            .withDatetime("26-10-2017 1500").build();

    // Manually added
    public static final ReadOnlyEvent NETWORK = new EventBuilder().withTitle("Network Talk")
            .withDescription("Networking meeting session").withLocation("IMDA").withDatetime("13-05-2017 1300").build();
    public static final ReadOnlyEvent SECURITY = new EventBuilder().withTitle("Security Talk")
            .withDescription("Security meeting session").withLocation("CSIT").withDatetime("26-10-2017 1300").build();

    private TypicalEvents() {
    } // prevents instantiation

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
        return new ArrayList<>(Arrays.asList(SPECTRA, DEEPAVALI, HENNA, WINE));
    }
}
