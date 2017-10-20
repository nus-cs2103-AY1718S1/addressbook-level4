package seedu.address.model.parcel;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.commons.exceptions.IllegalValueException;

public class StatusTest {

	@Rule
	public ExpectedException thrown = ExpectedException.none();

	@Test
	public void testToStringTest() {
		Parcel.Status delivered = Parcel.Status.DELIVERED;
		assertEquals("DELIVERED", delivered.toString());

		Parcel.Status delivering = Parcel.Status.DELIVERING;
		assertEquals("DELIVERING", delivering.toString());

		Parcel.Status pending = Parcel.Status.PENDING;
		assertEquals("PENDING", pending.toString());
	}

	@Test
	public void getStatusInstanceTest() throws IllegalValueException {
		// all uppercase
		Parcel.Status pending = Parcel.Status.getStatusInstance("PENDING");
		assertEquals(Parcel.Status.PENDING, pending);

		// all lowercase
		Parcel.Status delivering = Parcel.Status.getStatusInstance("delivering");
		assertEquals(Parcel.Status.DELIVERING, delivering);

		// mix of uppercase and lowercase characters
		Parcel.Status delivered = Parcel.Status.getStatusInstance("dEliVERed");
		assertEquals(Parcel.Status.DELIVERED, delivered);

		thrown.expect(IllegalValueException.class);
		thrown.expectMessage(Parcel.Status.MESSAGE_STATUS_CONSTRAINTS);
		Parcel.Status.getStatusInstance("asd1237fa&(&"); // weird characters
		Parcel.Status.getStatusInstance("JUMPING"); // not one of the possible values
	}

	@Test
	public void isValidStatusTest() {
		assertFalse(Parcel.Status.isValidStatus("INVALID"));

		// uppercase letters
		assertTrue(Parcel.Status.isValidStatus("PENDING"));
		assertTrue(Parcel.Status.isValidStatus("DELIVERING"));

		// lower case letters
		assertFalse(Parcel.Status.isValidStatus("pending"));
		assertFalse(Parcel.Status.isValidStatus("delivered"));

		// mix of upper and lower case
		assertFalse(Parcel.Status.isValidStatus("DelIVEred"));

		// random symbols
		assertFalse(Parcel.Status.isValidStatus("$!@HBJ123"));
	}

}
