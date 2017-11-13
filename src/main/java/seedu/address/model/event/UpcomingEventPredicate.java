//@@author erik0704
package seedu.address.model.event;

import java.text.ParseException;
import java.util.Date;
import java.util.function.Predicate;

import seedu.address.commons.util.DateUtil;

/**
 * Tests that a {@code Event}'s {@code Name} matches any of the keywords given.
 */
public class UpcomingEventPredicate implements Predicate<Event> {
    private Date now;

    public UpcomingEventPredicate() throws ParseException {
        try {
            now = DateUtil.createCurrentDate();
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean test(Event event) {
        Date target = event.getDate().getDate();

        long julianDayNumber1 = now.getTime();
        long julianDayNumber2 = target.getTime();
        if (julianDayNumber1 == julianDayNumber2) {
            return true;
        } else {
            return false;
        }


    }

    @Override
    public boolean equals(Object other) {
        return other == this // short circuit if same object
                || (other instanceof UpcomingEventPredicate); // instanceof handles null
    }

}
