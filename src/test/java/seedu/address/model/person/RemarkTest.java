package seedu.address.model.person;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class RemarkTest {

    @Test
    public void isValidRemark() {
        // valid addresses
        assertTrue(Remark.isValidRemark("Owes me 10 dollars"));
        assertTrue(Remark.isValidRemark("-")); // one character
        assertTrue(Remark.isValidRemark("Friends on facebook; Friends on Snapchat")); // long remark
    }
}
