package seedu.address.model.util;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Comparator;

import seedu.address.model.person.event.Event;

//@@author royceljh
/**
 * EventDateComparator compares events based on the LocalDate eventDate.
 * It sorts events starting from the dates that are closest to the currentDate.
 * Expired eventDate will be sorted last.
 */
public class EventDateComparator implements Comparator<Event> {

    @Override
    public int compare(Event e1, Event e2) {
        ZoneId sgt = ZoneId.of("GMT+8");
        LocalDate currentDate = LocalDate.now(sgt);
        if (e1.getEventDate().eventLocalDate.isBefore(currentDate)
                || e2.getEventDate().eventLocalDate.isBefore(currentDate)) {
            return e2.getEventDate().eventLocalDate.compareTo(e1.getEventDate().eventLocalDate);
        } else {
            return e1.getEventDate().eventLocalDate.compareTo(e2.getEventDate().eventLocalDate);
        }
    }
}
