package seedu.address.model;

import java.util.function.Predicate;
import java.util.logging.Logger;

import seedu.address.MainApp;
import seedu.address.commons.core.LogsCenter;

//@@author junming403
/**
 * A Enumeration class that consists of all possible Listing
 * Unit in the panel.
 */
public enum ListingUnit {
    MODULE, LOCATION, LESSON;

    private static ListingUnit currentListingUnit = MODULE;
    private static Predicate currentPredicate;
    private static final Logger logger = LogsCenter.getLogger(MainApp.class);


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
        logger.info("---Set current listing unit to: " + unit);
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
        logger.info("---Set current predicate to: " + predicate.getClass().getSimpleName());
        currentPredicate = predicate;
    }

}
