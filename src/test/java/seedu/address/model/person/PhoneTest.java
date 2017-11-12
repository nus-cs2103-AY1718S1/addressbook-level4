package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.person.Country.DEFAULT_COUNTRY_CODE;

import org.junit.Test;

public class PhoneTest {

    @Test
    public void isValidPhone() {
        // invalid phone numbers
        assertFalse(Phone.isValidPhone("")); // empty string
        assertFalse(Phone.isValidPhone(" ")); // spaces only
        assertFalse(Phone.isValidPhone("91")); // less than 3 numbers
        assertFalse(Phone.isValidPhone("phone")); // non-numeric
        assertFalse(Phone.isValidPhone("9011p041")); // alphabets within digits
        assertFalse(Phone.isValidPhone("9312 1534")); // spaces within digits
        assertFalse(Phone.isValidPhone("911")); // 3numbers
        //@@author icehawker
        assertFalse(Phone.isValidPhone("+10000 91234567")); // country code too many digits
        assertFalse(Phone.isValidPhone("+99 91234567")); // country code does not exist

        //@@author
        // valid phone numbers
        assertTrue(Phone.isValidPhone("9114")); // exactly 4 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
    }
    //@@author icehawker
    @Test
    public void getCountryCode() {
        // invalid codes
        assertFalse(Phone.isValidCode("99")); // no such country
        assertFalse(Phone.isValidCode("19191")); // too many digits
        // valid codes
        assertTrue(Phone.isValidCode("1")); // USA
        assertTrue(Phone.isValidCode("65")); // SG
        assertTrue(Phone.isValidCode("852")); // HK

    }
    @Test
    public void trimCode() {
        // invalid trims
        assertFalse(Phone.trimCode("91234567").equals("1")); // DEFAULT CODE not returned
        assertFalse(Phone.trimCode("+65 91234567").equals("10")); // wrong code
        assertFalse(Phone.trimCode("+65 91234567").equals("+65")); // plus sign not removed
        assertFalse(Phone.trimCode("+65 91234567").equals("65 ")); // whitespace not removed
        // valid trims
        assertTrue(Phone.trimCode("91234567").equals(DEFAULT_COUNTRY_CODE)); // No code returns default
        assertTrue(Phone.trimCode("+1 91234567").equals("1")); // 1 digit
        assertTrue(Phone.trimCode("+65 91234567").equals("65")); // 2 digit
        assertTrue(Phone.trimCode("+975 91234567").equals("975")); // 3 digit
    }
}
