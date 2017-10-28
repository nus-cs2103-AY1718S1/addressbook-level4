package seedu.address.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.module.predicates.MarkedListPredicate;

public class ListingUnitTest {

    @Test
    public void test_setListingUnit() {

        // set current listing unit to be lesson
        ListingUnit.setCurrentListingUnit(ListingUnit.LESSON);
        assertTrue(ListingUnit.getCurrentListingUnit().equals(ListingUnit.LESSON));

        // set current listing unit to be location
        ListingUnit.setCurrentListingUnit(ListingUnit.LOCATION);
        assertTrue(ListingUnit.getCurrentListingUnit().equals(ListingUnit.LOCATION));

        // set current listing unit to be module
        ListingUnit.setCurrentListingUnit(ListingUnit.MODULE);
        assertTrue(ListingUnit.getCurrentListingUnit().equals(ListingUnit.MODULE));

        // set the current predicate to be marked list predicate.
        ListingUnit.setCurrentPredicate(new MarkedListPredicate());
        assertTrue(ListingUnit.getCurrentPredicate().equals(ListingUnit.getCurrentPredicate()));


    }
}
