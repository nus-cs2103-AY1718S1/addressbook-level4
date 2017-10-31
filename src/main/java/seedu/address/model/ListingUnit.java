package seedu.address.model;

import java.util.function.Predicate;

//@@author junming403
/**
 * A Enumeration class that consists of all possible Listing
 * Unit in the panel.
 */
public enum ListingUnit {
    MODULE, LOCATION, LESSON;

    private static ListingUnit currentListingUnit = MODULE;
    private static Predicate currentPredicate;

    /**
     * Get current Listing unit
     */
    public static ListingUnit getCurrentListingUnit() {
        return currentListingUnit;
    }

    /**
     * Reset listing unit in the panel with the new ListingUnit and set previous listing unit
     */
    public static void setCurrentListingUnit(ListingUnit unit) {
        currentListingUnit = unit;
    }

    /**
     * Get current predicate
     */
    public static Predicate getCurrentPredicate() {
        return currentPredicate;
    }

    /**
     * Set current predicate
     */
    public static void setCurrentPredicate(Predicate predicate) {
        currentPredicate = predicate;
    }

}
