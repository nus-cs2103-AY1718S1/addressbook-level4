package seedu.address.commons.events.ui;

//@@author chernghann
import seedu.address.commons.events.BaseEvent;
import java.time.YearMonth;

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
