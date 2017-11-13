package seedu.address.relationship;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.model.relationship.ConfidenceEstimate;

//@@author Xenonym
public class ConfidenceEstimateTest {

    @Test
    public void isValidConfidenceEstimate() {
        // non-number strings are not valid
        assertFalse(ConfidenceEstimate.isValidConfidenceEstimate("invalid"));

        // values below 0 and above 100 are not valid
        assertFalse(ConfidenceEstimate.isValidConfidenceEstimate("-1.0"));
        assertFalse(ConfidenceEstimate.isValidConfidenceEstimate("101.0"));

        // values between 0 and 100 inclusive are valid
        assertTrue(ConfidenceEstimate.isValidConfidenceEstimate("35.9"));
    }
}
