package seedu.address.model.person;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import seedu.address.commons.exceptions.IllegalValueException;
//@@author RonakLakhotia
public class EmailSubjectTest {

    @Test
    public void isValidSubject() throws IllegalValueException {
        // invalid subject
        assertFalse(EmailSubject.isValidSubject("ss."));


        assertTrue(EmailSubject.isValidSubject("party"));
        assertTrue(EmailSubject.isValidSubject("birthday   party"));

        EmailSubject emailSubject = new EmailSubject("subject");
        assertFalse(emailSubject.equals(0));
        assertFalse(emailSubject.equals(null));
        assertTrue(emailSubject.equals(emailSubject));
    }
}
