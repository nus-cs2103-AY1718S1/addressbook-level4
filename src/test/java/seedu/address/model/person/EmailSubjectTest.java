package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class EmailSubjectTest {

    @Test
    public void isValidSubject() {
        // invalid subject
        assertFalse(EmailSubject.isValidSubject("ss."));


        assertTrue(EmailSubject.isValidSubject("party"));
        assertTrue(EmailSubject.isValidSubject("birthday   party"));
    }
}
