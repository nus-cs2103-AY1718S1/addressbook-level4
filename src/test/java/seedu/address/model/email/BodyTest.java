//@@author tshradheya
package seedu.address.model.email;

import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class BodyTest {

    private Body body = new Body("gmail");
    private Body sameBody = new Body("gmail");
    private Body anotherBody = new Body("outlook");

    @Test
    public void service_equals() {
        assertTrue(body.equals(sameBody));

        assertFalse(body == null);

        assertTrue(body.equals(body));

        assertFalse(body.equals(anotherBody));

        assertEquals(body.toString(), sameBody.toString());

        assertEquals(body.hashCode(), sameBody.hashCode());

        assertNotEquals(body.hashCode(), anotherBody.hashCode());
    }
}
