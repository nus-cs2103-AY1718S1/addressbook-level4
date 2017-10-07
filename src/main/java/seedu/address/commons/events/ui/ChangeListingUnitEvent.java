package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ListingUnit;

/**
 * Indicates a request to jump to the list of persons
 */
public class ChangeListingUnitEvent extends BaseEvent {

    public final ListingUnit unit;

    public ChangeListingUnitEvent(ListingUnit unit) {
        this.unit = unit;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
