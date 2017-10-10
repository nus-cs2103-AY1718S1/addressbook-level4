package seedu.address.commons.events.ui;

import seedu.address.commons.events.BaseEvent;
import seedu.address.model.ListingUnit;

/**
 * Indicates a request to sort list
 */
public class SortListRequestEvent extends BaseEvent {

    private static ListingUnit listingUnit = ListingUnit.getCurrentListingUnit();

    public SortListRequestEvent(ListingUnit currentListingUnit) {
        this.listingUnit = currentListingUnit;
    }

    public ListingUnit getListingUnit(){
        return listingUnit;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }

}
