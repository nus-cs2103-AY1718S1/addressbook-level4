package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.person.Country.DEFAULT_COUNTRY;

import org.junit.Test;

//@@author icehawker
public class CountryTest {

    @Test
    public void equals() {
        Country one = new Country("65");
        Country two = new Country ("");

        // same values -> returns true
        Country oneCopy = new Country("65");
        assertTrue(oneCopy.equals(one));

        // different values -> returns false
        assertFalse(two.equals(one));

        // different types -> returns false
        assertFalse(one.equals(1));

        // null -> returns false
        assertFalse(two.equals(null));
    }

    @Test
    public void countryToString() {
        Country one = new Country("65");
        Country two = new Country ("");

        assertTrue(one.toString().equals("Singapore")); // 65 is mapped to Singapore
        assertTrue(two.toString().equals(DEFAULT_COUNTRY)); // empty string is mapped to default

        assertFalse(one.toString().equals("65")); // output not a code
        assertFalse(two.toString().equals("")); // no code =/= empty string return
    }

    @Test
    public void getName() {
        Country one = new Country("65");
        Country two = new Country ("");

        // static reference getName(code) should return same country as Country object toString().
        assertTrue(Country.getName("65").equals(one.toString()));
        assertTrue(Country.getName("").equals(two.toString()));

        // different codes
        assertFalse(Country.getName("99").equals(one.toString()));
        assertFalse(Country.getName("").equals(one.toString()));
    }
}
