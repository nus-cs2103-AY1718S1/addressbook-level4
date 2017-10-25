package seedu.address.model.person;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

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

        // valid phone numbers
        assertTrue(Phone.isValidPhone("911")); // exactly 3 numbers
        assertTrue(Phone.isValidPhone("93121534"));
        assertTrue(Phone.isValidPhone("124293842033123")); // long phone numbers
    }

    @Test
    public void isPhoneFormattingCorrect() {
        assertEquals(Phone.formatPhone("911"), "911");

        assertEquals(Phone.formatPhone("6593121534"), "65-9312-1534"); //Singapore number
        assertEquals(Phone.formatPhone("9191121444"), "91-9112-1444"); //Indian number
        assertEquals(Phone.formatPhone("17651230101"), "176-5123-0101"); //US number
        assertEquals(Phone.formatPhone("447881234567"), "4478-8123-4567"); //UK number

        assertEquals(Phone.formatPhone("124293842033123"), "124-2938-4203-3123");
    }
}
