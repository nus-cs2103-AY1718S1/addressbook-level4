package seedu.address.model.insurance;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.TypicalPersons.ALICE;
import static seedu.address.testutil.TypicalPersons.ELLE;
import static seedu.address.testutil.TypicalPersons.FIONA;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.person.ReadOnlyPerson;

//@@author Juxarius
public class LifeInsuranceTest {

    @Test
    public void constructors() {
        try {
            // creating with just Strings for name
            LifeInsurance insurance = new LifeInsurance("Amy", "Bob", "Bob",
                    523.34, "contract.pdf", "11 10 2013", "1 12 2019");
            LifeInsurance insurance2 = new LifeInsurance(ALICE, ELLE, FIONA, 500.0,
                    "Awesome Contract.pdf", "1 1 12", "13 Jan-17");
            LifeInsurance insurance3 = new LifeInsurance(insurance2);
            assertEqualsInsurance(insurance3, insurance2);
        } catch (IllegalValueException ive) {
            throw new IllegalArgumentException("Construction should not be failing");
        }
    }

    public void assertEqualsInsurance(ReadOnlyInsurance insurance1, ReadOnlyInsurance insurance2) {
        assertEquals(insurance1.getOwner(), insurance2.getOwner());
        assertEquals(insurance1.getBeneficiary(), insurance2.getBeneficiary());
        assertEquals(insurance1.getInsured(), insurance2.getInsured());
        assertEquals(insurance1.getPremium(), insurance2.getPremium());
        assertEquals(insurance1.getSigningDateString(), insurance2.getSigningDateString());
        assertEquals(insurance1.getExpiryDateString(), insurance2.getExpiryDateString());
    }
}
