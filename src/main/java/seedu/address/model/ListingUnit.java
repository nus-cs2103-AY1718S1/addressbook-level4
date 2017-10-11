package seedu.address.model;


/**
 * A Enumeration class that consists of all possible Listing
 * Unit in the panel.
 */
public enum ListingUnit {
    PERSON, ADDRESS, EMAIL, PHONE;

    private static ListingUnit currentListingUnit = PERSON;

    /** Get current Listing unit */
    public static ListingUnit getCurrentListingUnit() {
        return currentListingUnit;
    }

    /** Reset listing unit in the panel with the new ListingUnit */
    public static void setCurrentListingUnit(ListingUnit unit) {
        currentListingUnit = unit;
    }
}
