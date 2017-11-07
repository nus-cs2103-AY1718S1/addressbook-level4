//@@author tshradheya
package seedu.address.model.email;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class SubjectTest {

    private Subject subject = new Subject("message");
    private Subject sameSubject = new Subject("message");
    private Subject anotherSubject = new Subject("different message");

    @Test
    public void service_equals() {
        assertTrue(subject.equals(sameSubject));

        assertFalse(subject == null);

        assertTrue(subject.equals(subject));

        assertFalse(subject.equals(anotherSubject));
    }
}
