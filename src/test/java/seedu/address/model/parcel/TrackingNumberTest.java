package seedu.address.model.parcel;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.parcel.TrackingNumber.MESSAGE_TRACKING_NUMBER_CONSTRAINTS;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class TrackingNumberTest {

    @Rule
    public ExpectedException expected = ExpectedException.none();

    @Test
    public void isValidTrackingNumber() throws Exception {
        assertFalse(TrackingNumber.isValidTrackingNumber("")); // empty string
        assertFalse(TrackingNumber.isValidTrackingNumber(" ")); // spaces only

        // missing parts
        assertFalse(TrackingNumber.isValidTrackingNumber("RR999999999")); // missing postfix 'SG'
        assertFalse(TrackingNumber.isValidTrackingNumber("999999999SG")); // missing prefix 'RR'
        assertFalse(TrackingNumber.isValidTrackingNumber("RRSG")); // missing digits
        assertFalse(TrackingNumber.isValidTrackingNumber("999999999")); // missing postfix and prefix

        // invalid parts
        assertFalse(TrackingNumber.isValidTrackingNumber("PE999999999SG")); // invalid prefix
        assertFalse(TrackingNumber.isValidTrackingNumber("RR999999999TW")); // invalid postfix
        assertFalse(TrackingNumber.isValidTrackingNumber("PE999999999TW")); // invalid prefix and postfix
        assertFalse(TrackingNumber.isValidTrackingNumber("PE9999999999SG")); // too long
        assertFalse(TrackingNumber.isValidTrackingNumber("RR99999999SG")); // too short
        assertFalse(TrackingNumber.isValidTrackingNumber("RR999!@#999SG")); // contain non-digit symbols
        assertFalse(TrackingNumber.isValidTrackingNumber("RR9999SG99999")); // wrong order
        assertFalse(TrackingNumber.isValidTrackingNumber("SG999999999RR")); // wrong order

        // valid email
        assertTrue(TrackingNumber.isValidTrackingNumber("RR999999999SG"));
        assertTrue(TrackingNumber.isValidTrackingNumber("RR123456789SG"));
        assertTrue(TrackingNumber.isValidTrackingNumber("RR001231230SG"));
    }

    @Test
    public void equals() throws Exception {
        TrackingNumber trackingNumber = new TrackingNumber("RR001231230SG");
        TrackingNumber sameTrackingNumber = new TrackingNumber("RR001231230SG");
        TrackingNumber differentTrackingNumber = new TrackingNumber("RR999999999SG");

        assertFalse(differentTrackingNumber.equals(trackingNumber));
        assertFalse(trackingNumber == null);

        assertEquals(trackingNumber, sameTrackingNumber);

        // check toString() equality
        assertFalse(trackingNumber.toString().equals(differentTrackingNumber.toString()));
        assertEquals(trackingNumber.toString(), sameTrackingNumber.toString());
        assertEquals(trackingNumber.toString(), "RR001231230SG");

        // check hashCode() equality
        assertFalse(trackingNumber.hashCode() == differentTrackingNumber.hashCode());
        assertTrue(trackingNumber.hashCode() == sameTrackingNumber.hashCode());
    }

    @Test
    public void testInvalidTrackingNumberInputThrowsExcpetion() throws IllegalValueException {
        expected.expect(IllegalValueException.class);
        expected.expectMessage(MESSAGE_TRACKING_NUMBER_CONSTRAINTS);
        new TrackingNumber(" "); // illegal tracking number
    }

}
