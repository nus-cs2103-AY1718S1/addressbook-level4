package seedu.address.model;


/**
 * A Enumeration class that consists of all possible Listing
 * Unit in the panel.
 */
public enum ListingUnit {
    MODULE, LOCATION, LESSON;

    private static ListingUnit currentListingUnit = MODULE;
    private static ListingUnit previousListingUnit = null;

    /** Get current Listing unit */
    public static ListingUnit getCurrentListingUnit() {
        return currentListingUnit;
    }

    /** Reset listing unit in the panel with the new ListingUnit and set previous listing unit */
    public static void setCurrentListingUnit(ListingUnit unit) {
        previousListingUnit = currentListingUnit;
        currentListingUnit = unit;
    }

    /** Get previous Listing unit */
    public static ListingUnit getPreviousListingUnit() { return previousListingUnit; }

}
