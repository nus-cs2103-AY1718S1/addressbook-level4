package seedu.address.model;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class ListingUnitTest {

    @Test
    public void test_setListingUnit() {

        // set current listing unit to be person
        ListingUnit.setCurrentListingUnit(ListingUnit.PERSON);
        assertTrue(ListingUnit.getCurrentListingUnit().equals(ListingUnit.PERSON));

        // set current listing unit to be address
        ListingUnit.setCurrentListingUnit(ListingUnit.ADDRESS);
        assertTrue(ListingUnit.getCurrentListingUnit().equals(ListingUnit.ADDRESS));

        // set current listing unit to be email
        ListingUnit.setCurrentListingUnit(ListingUnit.EMAIL);
        assertTrue(ListingUnit.getCurrentListingUnit().equals(ListingUnit.EMAIL));

        // set current listing unit to be phone
        ListingUnit.setCurrentListingUnit(ListingUnit.PHONE);
        assertTrue(ListingUnit.getCurrentListingUnit().equals(ListingUnit.PHONE));

    }
}
