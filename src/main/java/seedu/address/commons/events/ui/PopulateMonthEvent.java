package seedu.address.commons.events.ui;

//@@author chernghann
import java.time.YearMonth;

import seedu.address.commons.events.BaseEvent;

/**
 * Indicates a request to add Event.
 */
public class PopulateMonthEvent extends BaseEvent {

    public final YearMonth yearMonth;

    public PopulateMonthEvent(YearMonth date) {
        this.yearMonth = date;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
